package tp.pr5.logica;

import tp.pr5.control.Jugador;
import tp.pr5.vista.*;

import java.util.ArrayList;


public class Partida {
	final int N = 10;
	private Tablero tablero;
	private TableroInmutable tableroInmutable;
	private Ficha turno;
	private Ficha ganador;
	private boolean terminada;
	private Movimiento movimiento;
	private ReglasJuego reglasJuego;
	private Movimiento[] undoStack;
	private int numUndo; 
	private ArrayList<Observador> Obs;
	private int maxMovimientos;
	private int movimientosNegras;
	private int movimientosBlancas;
	
	public Partida(ReglasJuego reglas){
		reglasJuego = reglas;
		tablero = reglas.iniciaTablero();
		tableroInmutable = tablero;
		turno = reglas.jugadorInicial();
		undoStack = new Movimiento[N];
		Obs =  new ArrayList<Observador>();
		numUndo = -1;
		terminada = false;
		maxMovimientos = reglas.numMovimientos();
		cargarNumMovs();
		
	}
	private void cargarNumMovs(){
		if(maxMovimientos != -1){
			movimientosNegras = maxMovimientos;
			movimientosBlancas = maxMovimientos;
		}
		else{
			movimientosNegras = -1;
			movimientosBlancas = -1;
		}
	}
	
	public void pasarTurno(){
		Ficha anterior = turno;
		turno = reglasJuego.siguienteTurno(turno,tablero);
		tableroInmutable = tablero;
		for (Observador o: Obs){
			o.onNumMovimientos(movimientosBlancas, movimientosNegras);
			o.onMovimientoEnd(tableroInmutable,anterior,turno);
		}
	}
	public void tableroInicial(){
		tableroInmutable = tablero;
		for(Observador o:Obs){
			o.onNumMovimientos(movimientosBlancas, movimientosNegras);
			o.onTableroInicial(tableroInmutable,turno);
		}
	}
	
	public void reset(ReglasJuego reglas){
		reglasJuego = reglas;
		maxMovimientos = reglas.numMovimientos();
		tablero = reglas.iniciaTablero();
		turno = reglas.jugadorInicial();
		numUndo = -1;
		tableroInmutable = tablero;
		terminada = false;
	}
	
	public Movimiento creaMovimiento(Jugador jugador){
		Movimiento mov = jugador.getMovimiento(tablero, turno);
		return mov;
	}
	
	public void reiniciar(){
		cargarNumMovs();
		tablero = reglasJuego.iniciaTablero();
		turno = reglasJuego.jugadorInicial();
		numUndo = -1;
		tableroInmutable = tablero;
		terminada = false;
		cargarNumMovs();
		for(Observador o:Obs){
			o.onNumMovimientos(movimientosBlancas, movimientosNegras);
			o.onReset(tableroInmutable,turno);
		}
	}
	
	public void cambiarJuego(){
		tableroInmutable = tablero;
		cargarNumMovs();
		for(Observador o:Obs){
			o.onNumMovimientos(movimientosBlancas, movimientosNegras);
			o.onCambioJuego(tableroInmutable,turno);
		}
	}
	
	public void ejecutaMovimiento(Movimiento mov) throws MovimientoInvalido{
		movimiento = mov;
		boolean terminada = false;
		boolean noMovs = false;
		 
		if((turno.equals(Ficha.BLANCA) && movimientosBlancas == 0) || (turno.equals(Ficha.NEGRA) && movimientosNegras == 0))
			noMovs = true;
		
		if(!noMovs){
			try{
				if(turno == movimiento.getJugador() && !terminada){
					movimiento.ejecutaMovimiento(tablero);
					
					if(numUndo < N-1){
						numUndo++;
						undoStack [numUndo] = movimiento;
					}
					else if(numUndo == N-1){
						if(undoStack [numUndo]== null) 
							undoStack [numUndo] = movimiento;
						
						else {
							for(int i = 0; i < numUndo; ++i) 
								undoStack[i] = undoStack[i+1];
							undoStack [numUndo] = movimiento;
						}
					}
					if(mov.devOk()){
						if(turno.equals(Ficha.BLANCA))
							movimientosBlancas--;
						else
							movimientosNegras--;
						Ficha anterior = turno;
						turno = reglasJuego.siguienteTurno(turno,tablero);
						tableroInmutable = tablero;
						terminada = isTerminada();
						if(!terminada){
								for (Observador o: Obs){
									o.onNumMovimientos(movimientosBlancas, movimientosNegras);
									o.onMovimientoEnd(tableroInmutable,anterior,turno);
								}
						}
					}
				}
				else{
					MovimientoInvalido movInv = new MovimientoInvalido("El turno no coincide con la ficha");
					for (Observador o: Obs){
						o.onNumMovimientos(movimientosBlancas, movimientosNegras);
						o.onMovimientoIncorrecto(movInv);
					}
				}
			}catch(MovimientoInvalido movInv){
				for (Observador o: Obs){
					o.onNumMovimientos(movimientosBlancas, movimientosNegras);
					o.onMovimientoIncorrecto(movInv);
				}	
			}
		}
		else{
			turno = reglasJuego.siguienteTurno(turno,tablero);
			MovimientoInvalido movInv = new MovimientoInvalido("Al jugador actual no le quedan mas turnos");
			for (Observador o: Obs){
				o.onNumMovimientos(movimientosBlancas, movimientosNegras);
				o.onMovimientoIncorrecto(movInv);
			}
		}
		
	}

	public boolean undo(){
		boolean ok = true;
		Movimiento mov = null;
		tableroInmutable = tablero;
		if(numUndo >= 0 && numUndo < N-1)
			mov = undoStack[numUndo];

		else if(numUndo == N-1) {
			mov = undoStack[numUndo];
			undoStack[numUndo] = null;
		}
			
		else{
			ok =  false;
			for (Observador o: Obs){
				o.onNumMovimientos(movimientosBlancas, movimientosNegras);
				o.onUndoNotPossible(tableroInmutable,turno);
			}
		}
			
		if (ok){
			boolean hayMas = false;
			mov.undo(tablero);
			numUndo--;
			if(numUndo != -1) 
				hayMas = true;
			turno = reglasJuego.siguienteTurno(turno, tablero);
			
			for (Observador o: Obs){
				o.onNumMovimientos(movimientosBlancas, movimientosNegras);
				o.onUndo(tableroInmutable,turno,hayMas);
			}
		}
	return ok;
	}
	
	public Ficha getGanador(){
		ganador = reglasJuego.hayGanador(movimiento, tablero, movimientosBlancas, movimientosNegras);
		return ganador;
	}
	
	public boolean isTerminada(){
		boolean tablas = false;
		Ficha ficha = Ficha.VACIA;
		
		if(numUndo >= 0) {
			ficha = reglasJuego.hayGanador(movimiento, tablero,movimientosBlancas, movimientosNegras);
			if(ficha == Ficha.VACIA)
				tablas = reglasJuego.tablas(ficha, tablero,movimientosBlancas, movimientosNegras);
		}
		
		if(ficha != Ficha.VACIA || tablas){
			terminada = true;
			tableroInmutable = tablero;
			for (Observador o:Obs){
				o.onPartidaTerminada(tableroInmutable,ficha);
			}
		}
		
		return terminada;
	}
	
	public Tablero getTablero(){
		return tablero;
	}
		
	public Ficha getTurno(){
		return turno;
	}
	
	public void iniciaHebra(){
		for(Observador o:Obs){
			o.onIniciaHebra(turno);
		}
	}
	
	public void addObservador(Observador v) {
		Obs.add(v);
	}
}





