package tp.pr5.vista;

import tp.pr5.control.*;
import tp.pr5.logica.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class VistaGUI extends JFrame implements Observador{
	private Container panelPrincipal;   // Contenedor que integra panelIzquierdo, derecho e inferior
	private JPanel panelIzquierdo; 		// Panel que integra tablero, turno y aleatorio
	private JPanel panelDerecho;		// Panel que integra partida y CJuego
	private JPanel panelInferior;		// Panel que integra tablero y turnoActual
	private JPanel panelTablero;		// Panel donde esta el tablero 		        (esta dentro de panelInferior)
	private JPanel panelPartida;    	// Panel que controla deshacer/reiniciar	(esta dentro de panelDerecho)
	private JPanel panelCJuego;     	// PanelCambio de Juego						(esta dentro de panelDerecho)
	private JPanel panelFilCol;         // Selecciona fila/Columna 					(panelCJuego y solo para gravity)
	private JPanel panelGUser;			// Selecciona el tipo de jugador de blancas/negras (esta dentro de panelDerecho)
	private JPanel panelBlancas;		// Gestiona el tipo jugador que es blancas (esta dentro de panelGUser)
	private JPanel panelNegras;			// Gestiona el tipo jugador que es negras (esta dentro de panelGUser)
	private JButton [][] bMatriz;		// Matriz de botones que hace de tablero	(esta dentro de panelTablero)
	private JButton bSalir; 			// Boton Salir								(esta dentro de panelInferior)
	private JButton bAleatorio; 	    // Boton jugadorAleatorio					(esta dentro de panelInferior)
	private JButton bDeshacer;      	// Boton Deshacer							(esta dentro de panelPartida)
	private JButton bReiniciar; 		// Boton Reiniciar							(esta dentro de panelPartida)
	private JButton bCambiar; 			// Boton Cambiar							(esta dentro de CJuego)
	@SuppressWarnings("rawtypes")
	private JComboBox tipoJuego;		// Selector de juego						(esta dentro de CJuego)
	private JComboBox tipoBlancas;		// Tipo de jugador blancas					(esta dentro de panelBlancas)
	private JComboBox tipoNegras;		// Tipo de jugador negras					(esta dentro de panelNegras)
	private JLabel fil;					// Texto fila 								(panelCJuego y solo para gravity)
	private JLabel col;					// Texto columna 							(panelCJuego y solo para gravity)
	private JLabel blancas;				// Texto blancas 							(panelBlancas)
	private JLabel negras;				// Texto negras								(panelNegras)
	private JTextField fila;			// Campo fila 								(panelCJuego y solo para gravity)
	private JTextField columna;			// Campo columna 							(panelCJuego y solo para gravity)
	private JTextField turnoActual;		// Muestra el turno
	
	private ControladorGUI controlador; 		// Controlador
	private int N;								// Filas	para bMatriz[][]
	private int M;								// Columnas para bMatriz[][]
	private boolean blancasAuto;
	private boolean negrasAuto;
	private Thread hebraBlanca;
	private Thread hebraNegra;
	
	private JPanel cb;
	private JPanel cn;
	private Color color1;
	private Color color2;
	private JLabel colB;
	private JLabel colN;
	private JComboBox colorB;		// Selector color blancas						
	private JComboBox colorN;		// Selector color negras
	private JPanel panelColores;        
	
	//Constructor de clase
	public VistaGUI(ControladorGUI c){
		super("Práctica 5"); //Texto cabecera de la ventana
		controlador = c;
		controlador.addObservador(this);
		blancasAuto = false;
		negrasAuto = false;
		iniciarGUI();
		configurarEventos();
		poner();
	}
	
	
	/*CONFIGURACIÓN DE TODOS LOS EVENTOS DE LA INTERFAZ*/

	
	//Eventos de los botones de la interfaz
	private void configurarEventos() {
		
		//Evento reiniciar partida
		bReiniciar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				controlador.reiniciar();
			}
		});
		
		//Evento deshacer movimiento
		bDeshacer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				controlador.undo();
			}
		});
		
		//Evento pulsar boton Salir
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Opciones disponibles en la caja que saldrá
				Object[] opciones = {"Si","No"};
				//Configuro un panel de opciones y vinculo el resultado seleccionado a un int para luego ver que hacer
				int opcion = JOptionPane.showOptionDialog(panelPrincipal,
						"¿De verdad deseas salir?",							
						"Salir del programa",								
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, opciones,
						opciones[0]);
				if(opcion == 0)	//Como la opción 0 corresponde al "Si" llamo a finalizar
					System.exit(0);
			}
		});
		
		//Evento cambiar de juego
		bCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				boolean filaOk = false, colOk = false;
				Object e = tipoJuego.getSelectedItem(); 
				String valor = String.valueOf(e);
				String nFila = null, nColumna = null;
				int i = N, j = M;
				if(valor == "Gravity"){
					nFila = fila.getText();
					filaOk = validaNumerico(nFila);
					nColumna = columna.getText();
					colOk = validaNumerico(nColumna);
					//Si ambos datos son int cambiará de juego
					if(filaOk && colOk){
						i = Integer.parseInt(nFila);
						j = Integer.parseInt(nColumna);
						controlador.cambioJuego(valor, i, j);
					}
					//Si metes una letra en fila/columna te saldrá un mensaje de error
					else{
						Object[] opciones = {"Aceptar"};
						JOptionPane.showOptionDialog(panelPrincipal,
						"Fallo al intentar cambiar de juego",							
						"Cambio de juego erroneo",								
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, opciones,
						opciones[0]);
					}
				}
				else controlador.cambioJuego(valor, i, j);
			}
		});
		
		//Evento para poner visible/no visible el selector fila/col para gr
		tipoJuego.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String tipo = (String) tipoJuego.getSelectedItem();	
				switch(tipo){
					case "Gravity": panelFilCol.setVisible(true); break;
					default: panelFilCol.setVisible(false);
				}
			}
		});
	
		//Evento para hacer una jugada aleatoria
		bAleatorio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try {
					controlador.aleatorio();
				} catch (MovimientoInvalido e) {
					e.printStackTrace();
				}
			}
		});	
		
		 tipoBlancas.addActionListener (new ActionListener(){
			 public void actionPerformed(ActionEvent arg0){
				 String tipo = (String)tipoBlancas.getSelectedItem();	
					switch(tipo){
						case "Automático":{ 
							blancasAuto = true; 
							controlador.iniciaHebra();
						}break;
						case "Humano":{ 
							blancasAuto = false; 
							activarBotones();
						}break;
					}
				 
			 }
			 
		 });
		 
		 tipoNegras.addActionListener (new ActionListener(){
			 public void actionPerformed(ActionEvent arg0){
				 String tipo = (String)tipoNegras.getSelectedItem();	
					switch(tipo){
						case "Automático":{ 
							negrasAuto = true; 
							controlador.iniciaHebra();
						}break;
						case "Humano":{ 
							negrasAuto = false;
							activarBotones();
						}break;
					}
				 
			 }
			 
		 });
		 
		 //Cambia el color físico de la ficha blanca
		 colorB.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent arg0){
				 String tipo = (String)colorB.getSelectedItem();	
					switch(tipo){
						case "Naranja":{ 
							if(color2!= java.awt.Color.orange)
								color1 = java.awt.Color.orange;
							else
								mensajeErrorColor();
							
						}break;
						case "Amarillo":{ 
							if(color2!= java.awt.Color.yellow)
								color1 = java.awt.Color.yellow;
							else
								mensajeErrorColor();

						}break;
						
						case "Rojo":{ 
							if(color2!= java.awt.Color.red)
								color1 = java.awt.Color.red;
							else
								mensajeErrorColor();
						}break;
						
						case "Rosa":{ 
							if(color2!= java.awt.Color.pink)
								color1 = java.awt.Color.pink;
							else
								mensajeErrorColor();
						}break;
						
						case "Blanco":{ 
								color1 = java.awt.Color.white;
						}break;
					}
			 }
			 
		 });
		 
		//Cambia el color físico de la ficha negra
		 colorN.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent arg0){
				 String tipo = (String)colorN.getSelectedItem();	
					switch(tipo){
						case "Naranja":{ 
							if(color1!= java.awt.Color.orange)
								color2 = java.awt.Color.orange;
							else
								mensajeErrorColor();
							
						}break;
						case "Amarillo":{ 
							if(color1!= java.awt.Color.yellow)
								color2 = java.awt.Color.yellow;
							else
								mensajeErrorColor();

						}break;
						
						case "Rojo":{ 
							if(color1!= java.awt.Color.red)
								color2 = java.awt.Color.red;
							else
								mensajeErrorColor();
						}break;
						
						case "Rosa":{ 
							if(color1!= java.awt.Color.pink)
								color2 = java.awt.Color.pink;
							else
								mensajeErrorColor();
						}break;
						
						case "Negro":{ 
								color1 = java.awt.Color.black;
						}break;
					}
			 }
			 
		 });
	}

	//Mensaje que se muestra al seleccionar un color que ya esta siendo usado por el otro jugador
	private void mensajeErrorColor(){
		Object[] opciones = {"Aceptar"};
		JOptionPane.showOptionDialog(panelPrincipal,
		"Color ya en uso",							
		"Cambio de color erroneo",								
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE, null, opciones,
		opciones[0]);
	}
	//Captura las coordenadas x,y de la matriz de botones que pasan al controlador para ejecutar el movimiento
	private void poner(){
		for(int i = 0; i < N; i++){
			final int co = i;
			for(int j = 0; j < M; j++){
				final int fi = j;
				bMatriz[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent org0) {
							try {
								controlador.poner(co+1,fi+1);
							} catch (MovimientoInvalido e) {
								e.printStackTrace();
							}
					}		
				});
			}
		}
	}
	
	private boolean validaNumerico(String cadena)  {
		int numero;
		try {
			numero = Integer.parseInt(cadena);
			return true;
		}catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	
	/*CONFIGURACIÓN PANELES DE LA INTERFAZ GRÁFICA*/
	
	
	//Inicia la ventana de juego correctamente cargando todos sus paneles
	private void iniciarGUI(){
		controlador.tableroInicial();
		panelPrincipal = this.getContentPane();
		color1 = java.awt.Color.white;
		color2 = java.awt.Color.black;
		panelDerecho();
		panelInferior();
		panelPrincipal();
	}
			
	//Panel derecho donde se montara el mini panel de partida y el de cambio de juego
	private void panelDerecho(){
		//Inicio el panelDerecho (parte derecha de panelPrincipal)
		panelDerecho = new JPanel();
		panelDerecho.setLayout(new BorderLayout());
		//Cargo los subpaneles partida y CJuego
		partida();
		CJuego();
		tipoJugadores();
		cambiarColores();
		//Añado los subpaneles y el botón salir a panelDerecho
		panelDerecho.add(panelPartida,BorderLayout.NORTH);
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BorderLayout());
		panelAux.add(panelGUser,BorderLayout.NORTH);
		panelAux.add(panelColores,BorderLayout.SOUTH);
		panelDerecho.add(panelAux,BorderLayout.CENTER);
		panelDerecho.add(panelCJuego,BorderLayout.SOUTH);
	}	
	
	private void cambiarColores(){
		panelColores = new JPanel();
		Border bordeColores = new TitledBorder(new EtchedBorder(), "Gestión colores");
		panelColores.setBorder(bordeColores);
		panelColores.setLayout(new BorderLayout());
		colB = new JLabel ("Color de blancas");
		colorB = new JComboBox();
		colorB.addItem("Blanco");
		colorB.addItem("Amarillo");
		colorB.addItem("Rojo");
		colorB.addItem("Rosa");
		colorB.addItem("Naranja");
		cb = new JPanel();
		cb.add(colB);
		cb.add(colorB);
		colN = new JLabel ("Color de negras");
		colorN = new JComboBox();
		colorN.addItem("Negro");
		colorN.addItem("Amarillo");
		colorN.addItem("Rojo");
		colorN.addItem("Rosa");
		colorN.addItem("Naranja");
		cn = new JPanel();
		cn.add(colN);
		cn.add(colorN);
		panelColores.add(cb,BorderLayout.NORTH);
		panelColores.add(cn,BorderLayout.SOUTH);
	}
	
	private void tipoJugadores(){
		panelGUser = new JPanel();
		Border bordeUser = new TitledBorder(new EtchedBorder(), "Gestión de jugadores");
		panelGUser.setBorder(bordeUser);
		panelGUser.setLayout(new BorderLayout());
		tBlancas();
		tNegras();
		panelGUser.add(panelBlancas,BorderLayout.NORTH);
		panelGUser.add(panelNegras,BorderLayout.CENTER);
	}
	
	private void tBlancas(){
		panelBlancas = new JPanel();
		panelBlancas.setLayout(new FlowLayout());
		blancas = new JLabel ("Jugador de blancas");
		tipoBlancas = new JComboBox();
		tipoBlancas.addItem("Humano");
		tipoBlancas.addItem("Automático");
		panelBlancas.add(blancas);
		panelBlancas.add(tipoBlancas);
		
	}
	
	private void tNegras(){
		panelNegras = new JPanel();
		panelNegras.setLayout(new FlowLayout());
		negras = new JLabel ("Jugador de negras ");
		tipoNegras = new JComboBox();
		tipoNegras.addItem("Humano");
		tipoNegras.addItem("Automático");
		panelNegras.add(negras);
		panelNegras.add(tipoNegras);
	}
	//Botones de partida que va en panelDerecho
	private void partida(){
		ImageIcon iconoReiniciar = new ImageIcon("imagenes/reiniciar.png");
		ImageIcon iconodeshacer = new ImageIcon("imagenes/deshacer.png");
		
		//Inicio el panel (es un subPanel de panelDerecho)
		panelPartida = new JPanel();
		Border bordePartida = new TitledBorder(new EtchedBorder(), "Partida");
		panelPartida.setBorder(bordePartida);
		panelPartida.setLayout(new FlowLayout());
		//Creo el botón de deshacer movimiento y no lo enabilito aun, eso se encargará el observador pertinente
		bDeshacer = new JButton("Deshacer", iconodeshacer); 
		bDeshacer.setEnabled(false);
		panelPartida.add(bDeshacer);
		//Creo el botón reiniciar
		bReiniciar = new JButton("Reiniciar",  iconoReiniciar); 
		panelPartida.add(bReiniciar);
	}
	
	//Cambio de juego que va en panelDerecho
	@SuppressWarnings("rawtypes")
	private void CJuego(){
		ImageIcon iconoCambiar = new ImageIcon("imagenes/cambiar.png");
		//Inicio el panel (es un subPanel de panelDerecho)
		panelCJuego = new JPanel();
		Border bordeCJuego = new TitledBorder(new EtchedBorder(), "Cambio de juego");
		panelCJuego.setBorder(bordeCJuego);
		panelCJuego.setLayout(new BorderLayout());
		//Creo el comboBox que me permite seleccionar mediante un clic el tipo de juego
		tipoJuego = new JComboBox();
		//Defino las opciones del comboBox disponibles
		definirJuegos();
		cogerFilCol();
		//Creo el botón que permite cambiar de juego
		bCambiar = new JButton("Cambiar", iconoCambiar); //Boton
		panelCJuego.add(tipoJuego,BorderLayout.NORTH);
		panelCJuego.add(panelFilCol,BorderLayout.CENTER);
		panelCJuego.add(bCambiar,BorderLayout.SOUTH);
	}
	
	//Define las opciones dentro del ComboBox de CJuego
	@SuppressWarnings("unchecked")
	private void definirJuegos(){
		tipoJuego.addItem("Conecta4");
		tipoJuego.addItem("Complica");
		tipoJuego.addItem("Gravity");
		tipoJuego.addItem("Reversi");
	}
	
	//Muestra la caja de fila/columna al cambiar a gravity
	private void cogerFilCol(){
		panelFilCol = new JPanel();
		panelFilCol.setLayout(new FlowLayout());
		//Creo los textos que indica fila y columna cuando cambias a juego gravity
		fil = new JLabel("Fila");		   
		col = new JLabel (" Columna");		
		//Creo las cajas de texto donde rellenaras los datos de fila y columna para gravity
		fila = new JTextField(3);			
		columna = new JTextField(3);	
		panelFilCol.add(fil);
		panelFilCol.add(fila);
		panelFilCol.add(col);
		panelFilCol.add(columna);
		//Este panel no será visible hasta que la interfaz no detecte que se selecciona el tipo de juego gravity
		panelFilCol.setVisible(false);
	}
	
	//Panel Izquierdo de la ventana que contiene Tablero, turno actual
	private void panelIzquierdo(TableroInmutable tablero,Ficha turno){
		//Inicio el panelIzquierdo asignandole dimensiones y bordes
		panelIzquierdo = new JPanel();
		Border bordeIzquierdo = new TitledBorder(new EtchedBorder(), "");
		panelIzquierdo.setBorder(bordeIzquierdo);
		panelIzquierdo.setLayout(new BorderLayout());
		//Creo el subpanel tablero donde va la matriz de botones
		panelTablero(tablero);
		panelIzquierdo.add(panelTablero,BorderLayout.CENTER);
		//Creo la caja de texto que muestra el turno (solo el inicial blanco)
		turnoActual = new JTextField(10);
		muestraTurno(turno);
		turnoActual.setBackground(Color.white);	// Cambia el color del fondo (Cuando no es editable la caja se pone gris, ahora es blanco)
		turnoActual.setForeground(Color.blue);	// Cambia el color de la letra (azul)
		turnoActual.setEditable(false);			// Así no se puede modificar la caja de texto manualmente
		panelIzquierdo.add(turnoActual,BorderLayout.SOUTH);
	}

	//Panel que inicia la matriz de botones que va dentro del panelIzquierdo
	private void panelTablero(TableroInmutable tablero){
		panelTablero = new JPanel();
		cargarTablero(tablero);
	}
	
	//Panel inferior que contiene el boton aleatorio y salir
	private void panelInferior(){
		ImageIcon iconoSalir = new ImageIcon("imagenes/exit.png");
		ImageIcon iconoAleatorio = new ImageIcon("imagenes/aleatorio.png");
		panelInferior = new JPanel();
		panelInferior.setLayout(new BorderLayout());
		bSalir = new JButton("Salir", iconoSalir); 
		//bSalir.setIcon(iconoSalir);
		bAleatorio = new JButton("Poner Aleatorio", iconoAleatorio);
		panelInferior.add(bSalir,BorderLayout.EAST);
		panelInferior.add(bAleatorio,BorderLayout.WEST);
	}
	
	//Inserta los JPanel panelDerecho,Izquierdo e inferior en el JPanel principal
	private void panelPrincipal(){
		//Añado ambos paneles al panel principal
		panelPrincipal.add(panelDerecho, BorderLayout.EAST);
		panelPrincipal.add(panelIzquierdo,BorderLayout.WEST);
		panelPrincipal.add(panelInferior,BorderLayout.SOUTH);	
		//Doy un tamaño y una ubicación al panel principal
		this.setLocation(100,10);
		this.pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE); //La aplicación acaba al cerrar la ventana
	}

	
	/*METODOS QUE USA EL OBSERVADOR*/

	
	//Modifica el tamaño del tablero
	private void modificarTamanyo(TableroInmutable tablero){
		panelTablero.removeAll();
		cargarTablero(tablero);
		panelTablero.revalidate();
		pack();
	}
	
	private void cargarTablero(TableroInmutable tablero){
		bMatriz = new JButton[N][M];
		panelTablero.setLayout(new GridLayout(N,M));
		//Recorro la matriz de botones asignando a cada botón el color verde y añadiendolo al panel tablero
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				bMatriz[i][j] = new JButton();
				if(tablero.getCasilla(j+1, i+1).equals(Ficha.VACIA))
					bMatriz[i][j].setBackground(java.awt.Color.green);
				else if(tablero.getCasilla(j+1,i+1).equals(Ficha.BLANCA) )
					bMatriz[i][j].setBackground(color1);
				else
					bMatriz[i][j].setBackground(color2);
				bMatriz[i][j].setPreferredSize(new Dimension(40,40)); 
				panelTablero.add(bMatriz[i][j]);
			}
		}
	}
	//Cambia el color del tablero asignandole a cada matriz del botón un color segun el estado de tableroInmutable
	private void colorTablero(TableroInmutable tableroInmutable){
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				JButton temp = bMatriz[i][j];
				if(tableroInmutable.getCasilla(j+1, i+1) == Ficha.BLANCA)
					temp.setBackground(color1);
				else if(tableroInmutable.getCasilla(j+1, i+1) == Ficha.NEGRA)
					temp.setBackground(color2);
				else
					temp.setBackground(java.awt.Color.green);
			}
		}
	}
	
	//Muestra el turno actual
	private void muestraTurno(Ficha turno){
		if(turno.equals(Ficha.BLANCA))
			turnoActual.setText(" Juegan las blancas");
		else
			turnoActual.setText(" Juegan las negras");
	}
		
	//Muestra quien ha ganado la partida o si hay empate
	private void muestraGanador(Ficha ganador){
		switch(ganador){
			case BLANCA: turnoActual.setText(" Ganan las blancas"); break;
			case NEGRA: turnoActual.setText(" Ganan las negras"); break;
			default: turnoActual.setText(" Partida terminada en tablas");
		}		
	}
		
	//Cambia el enable de todos los botones a false salvo el de salir y reiniciar
	private void desactivarBotones(){
		//Bucle que deshabilita los botones de la matriz que forma el tablero
		for(int i = 0; i<N;i++){
			for(int j = 0; j<M;j++)
				bMatriz[i][j].setEnabled(false);
		}
		//Se deshabilitan el resto de botones salvo salir y reiniciar
		bDeshacer.setEnabled(false);
		bAleatorio.setEnabled(false);
		bCambiar.setEnabled(false);
		tipoJuego.setEnabled(false);
	}
	
	//Cambia el enable de todos los botones a true al reiniciar una partida cuando has terminado una anterior
	private void activarBotones(){
		//Bucle que habilita la matriz de botones al reiniciar una partida finalizada
		for(int i = 0; i<N;i++){
			for(int j = 0; j<M;j++)
				bMatriz[i][j].setEnabled(true);
		}
		//Se habilitan el resto de botones al reiniciar una partida finalizada
		bAleatorio.setEnabled(true);
		bCambiar.setEnabled(true);
		tipoJuego.setEnabled(true);
	}
	
	private void jugarHebra(Ficha turno){
		if(turno.equals(Ficha.NEGRA) && negrasAuto){
			hebraNegra= new Thread (new JuegoAutomatico(controlador));
			for(int i = 0; i<N;i++){
				for(int j = 0; j<M;j++)
					bMatriz[i][j].setEnabled(false);
			}
			bDeshacer.setEnabled(false);
			bAleatorio.setEnabled(false);
			hebraNegra.start();
		}
		else if(turno.equals(Ficha.BLANCA) && blancasAuto){
			hebraBlanca= new Thread (new JuegoAutomatico(controlador));
			for(int i = 0; i<N;i++){
				for(int j = 0; j<M;j++)
					bMatriz[i][j].setEnabled(false);
			}
			bDeshacer.setEnabled(false);
			bAleatorio.setEnabled(false);
			hebraBlanca.start();
		}
		else activarBotones();
	}
	/*METODOS DE LA INTERFAZ Observador*/
	
	
	//Reinicia el tablero al pulsar el botón reiniciar
	public void onReset(TableroInmutable tablero, Ficha turno) {
		final TableroInmutable t = tablero;
		final Ficha tu = turno;
		if(blancasAuto)
			hebraBlanca.interrupt();
		else if(negrasAuto)
			hebraNegra.interrupt();
		SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
				colorTablero(t);
				bDeshacer.setEnabled(false);
				muestraTurno(tu);
				activarBotones();
				jugarHebra(tu);
		      }
		 });
	}

	//Bloquea el tablero de juego cuando hay un empate o un ganador
	public void onPartidaTerminada(TableroInmutable tablero, Ficha ganador) {
		colorTablero(tablero);
		desactivarBotones();
		muestraGanador(ganador);
	}

	//Cambia el tipo de juego y su tablero
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		final TableroInmutable t = tablero;
		final Ficha tu = turno;
		if(blancasAuto)
			hebraBlanca.interrupt();
		else if(negrasAuto)
			hebraNegra.interrupt();
		SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
				N = t.getFilas();
				M = t.getColumnas();
				modificarTamanyo(t);
				poner();
				bDeshacer.setEnabled(false);
				muestraTurno(tu);
				jugarHebra(tu);
		      }
		});
	}

	//Controla cuando es imposible deshacer un movimiento
	public void onUndoNotPossible(TableroInmutable tablero, Ficha turno) {
		bDeshacer.setEnabled(false);
	}
	
	//Controla cuando es posible deshacer un movimiento correctamente
	public void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas) {
		final TableroInmutable t = tablero;
		final boolean h = hayMas;
		final Ficha tu = turno;
		if(blancasAuto)
			hebraBlanca.interrupt();
		else if(negrasAuto)
			hebraNegra.interrupt();
		SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
				colorTablero(t);
				bDeshacer.setEnabled(h);
				muestraTurno(tu);
				jugarHebra(tu);
		      }
	    });
	}

	//Ejecuta un movimiento correctamente
	public void onMovimientoEnd(TableroInmutable tablero, Ficha jugador, Ficha turno) {
		final TableroInmutable t = tablero;
		final Ficha j = jugador;
		final Ficha tu = turno;
		SwingUtilities.invokeLater(new Runnable(){
	      public void run(){
	    		colorTablero(t);
	    		muestraTurno(tu);
	    		bDeshacer.setEnabled(true);
	    		if(j.equals(Ficha.BLANCA)){
	    			if(blancasAuto) {
	    				hebraBlanca.interrupt();
	    				bDeshacer.setEnabled(true);
	    			}
	    			jugarHebra(tu);
	    		}	
	    		if(j.equals(Ficha.NEGRA)){
	    			if(negrasAuto) {
	    				hebraNegra.interrupt();
	    				bDeshacer.setEnabled(true);
	    			}
	    			jugarHebra(tu);
	    		}	
	      }
	    });
	}
	
	//Captura una excepción generada por un movimientoIncorrecto en partida
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		String fallo = movimientoException.getMessage();
		Object[] opciones = {"Aceptar"};
		//Configuro un panel de opciones para mostrar la excepción
		JOptionPane.showOptionDialog(panelPrincipal, fallo,							
		"Movimiento inválido",JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
	}


	//Carga las dimensiones del tablero cargado por el comando del main
	public void onTableroInicial(TableroInmutable tablero, Ficha turno) {
		M = tablero.getColumnas();
		N = tablero.getFilas();
		panelIzquierdo(tablero,turno);
	}


	@Override
	public void onIniciaHebra(Ficha turno) {
		jugarHebra(turno);
	}


	@Override
	public void onNumMovimientos(int movBlancas, int movNegras) {
		// TODO Auto-generated method stub
		
	}
}