package tp.pr5.vista;

import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.control.*;
//Por hacer
public class VistaConsola implements Observador{
	private ControladorConsola controlador;
	private boolean terminado;
	private Scanner in;
	private String fallo;
	
	
	//Constructor de clase
	public VistaConsola(ControladorConsola c){
		controlador = c;
		c.addObservador(this);
		in = new Scanner(System.in);
		terminado = false;
	}
	
	//Ejecuta el juego
	public void run() throws MovimientoInvalido{
		String opcion,seleccion,texto;
		controlador.tableroInicial();
		do{
			opcion = in.next();
			seleccion = opcionMinusculas(opcion);	
			switch(seleccion){
				case "ayuda":{
					controlador.mostrarAyuda();
					controlador.tableroInicial();
					}break;
							
				case "poner": controlador.poner(); break;
							
				case "deshacer": controlador.deshacer();break; 
							
				case "reiniciar": controlador.reiniciar(); break;
							
				case "jugar":{
					texto = in.nextLine();
					controlador.cambiarJuego(texto);
				}break;
							
				case "jugador":{
					texto = in.nextLine();
					controlador.tipoJugador(texto);
					controlador.tableroInicial();
				}break;
				
				case "pasar": controlador.pasar(); break;
						
				case "salir":terminado = true; break;
								
				default:{ 
					fallo = in.nextLine();	//Sin esto cuando metes un comando erroneo de mas de una palabra ("hola que tal") se muestra dos veces el tablero
					System.err.println("No te entiendo.");
					controlador.tableroInicial();
				}
			}
		}while(!terminado);	
	}

	
	/*METODOS QUE USA EL OBSERVADOR*/
	
	
	//Muestra el tablero por pantalla
	public void juego(TableroInmutable tablero,Ficha turno){
		tablero.mostrar();
		if(!terminado){
			System.out.print("Juegan ");
			mostrarTurno(turno);
			System.out.print("Qué quieres hacer? ");
		}
		else
			mostrarGanador(turno);
	}
	
	//Muestra el turno por pantalla
	public void mostrarTurno(Ficha turno){
		if(turno.equals(Ficha.BLANCA))
			 System.out.println("blancas");
		else 
			System.out.println("negras");
	}
	
	//Muestra el ganador por pantalla
	public void mostrarGanador(Ficha ganador) {
		switch(ganador){
			case BLANCA: System.out.println("Ganan las blancas");break;
			case NEGRA: System.out.println("Ganan las negras");break;
			default:System.out.println("Partida terminada en tablas");
		}
	}
	
	//Pasa a minusculas un comando existente
	public String opcionMinusculas(String palabra){
		String minusculas = palabra;
		
		if(palabra.equals("Ayuda") || palabra.equals("AYUDA"))
			minusculas = "ayuda";
		
		else if(palabra.equals("Poner") || palabra.equals("PONER"))
			minusculas = "poner";
		
		else if(palabra.equals("Deshacer") || palabra.equals("DESHACER"))
			minusculas = "deshacer";
		
		else if(palabra == "Reiniciar" || palabra == "REINICIAR")
			minusculas = "reiniciar";
		
		else if(palabra.equals("Jugar") || palabra.equals("JUGAR"))
			minusculas = "jugar";
		
		else if(palabra.equals("Jugador") || palabra.equals("JUGADOR"))
			minusculas = "jugador";
		
		else if(palabra.equals("Salir") || palabra.equals("SALIR"))
			minusculas = "salir";
		else if(palabra.equals("Pasar") || palabra.equals("PASAR"))
			minusculas = "pasar";
		
		return minusculas;
	}
	
	/*METODOS DE LA INTERFAZ Observador*/
	
	
	//Reinicia el tablero
	public void onReset(TableroInmutable tablero, Ficha turno) {
		System.out.println("Partida reiniciada.");
		juego(tablero,turno);
	}

	//Termina con el juego al finalizar la partida
	public void onPartidaTerminada(TableroInmutable tablero, Ficha ganador) {
		terminado = true;
		juego(tablero,ganador);
	}

	//Cambia el tipo de juego y su tablero
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		System.out.println("Partida reiniciada.");
		juego(tablero,turno);
	}

	//Controla cuando es imposible deshacer un movimiento
	@Override
	public void onUndoNotPossible(TableroInmutable tablero, Ficha turno) {
		System.err.println("Imposible deshacer");
		juego(tablero,turno);	
	}

	//Controla cuando es posible deshacer un movimiento correctamente
	@Override
	public void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas) {
		juego(tablero,turno);
	}

	//Ejecuta un movimiento correctamente
	@Override
	public void onMovimientoEnd(TableroInmutable tablero, Ficha jugador,Ficha turno) {
		if(!terminado) 
			juego(tablero,turno);
	}

	//Coge un movimiento incorrecto
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		fallo = movimientoException.getMessage();
		System.err.println(fallo);
		controlador.tableroInicial();
	}

	/*Carga las dimensiones del tablero cargado por el comando del main
	 * o el tablero que ya tiene partida cuando introduces una opción 
	 * incorrecta por comando*/
	public void onTableroInicial(TableroInmutable tablero, Ficha turno) {
		juego(tablero,turno);	
	}

	//No se usa
	public void onIniciaHebra(Ficha turno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNumMovimientos(int movBlancas, int movNegras) {
		if(movBlancas != -1 && movNegras != -1){
			System.out.println("Blancas tienen " + movBlancas + " movimientos.");
			System.out.println("Negras tienen " + movNegras + " movimientos.");
		}
		else{
			System.out.println("Blancas tienen movimientos ilimitados.");
			System.out.println("Negras tienen movimientos ilimitados.");
		}
		System.out.println(" ");
	}
		
}
