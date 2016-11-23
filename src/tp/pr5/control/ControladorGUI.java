package tp.pr5.control;
import tp.pr5.logica.*;
import tp.pr5.vista.*;

public class ControladorGUI {
	private FactoriaTipoJuego factoria;
	private ReglasJuego reglas;
	private Partida partida;
	private Jugador aleatorio;
	
	//Constructor de la clase
	public ControladorGUI(FactoriaTipoJuego f, Partida p, int movimientos){
		factoria = f;
		reglas = f.creaReglas(movimientos);
		partida = p;
		aleatorio = factoria.creaJugadorAleatorio();
	}
	
	public void tableroInicial(){
		partida.tableroInicial();
	}
	
	//Dado una coordenada x,y crea un movimiento y lo ejecuta
	public void poner(int y, int x) throws MovimientoInvalido{
		Movimiento movimiento = factoria.creaMovimiento(x, y, partida.getTurno());
		partida.ejecutaMovimiento(movimiento);
	}
	
	//Crea un movmiento aleatorio válido y lo ejecuta
	public void aleatorio() throws MovimientoInvalido{
		Movimiento movimiento = partida.creaMovimiento(aleatorio);
		partida.ejecutaMovimiento(movimiento);
	}
	
	//Cambia el tipo de juego
	public void cambioJuego(String juego,int i,int j, int movs){
		switch(juego) {
			case "Conecta4":{
				factoria = new FactoriaConecta4(); 
			}break;
			
			case "Complica":{
				factoria = new FactoriaComplica();
			}break;
			
			case "Gravity":{
				factoria = new FactoriaGravity(i,j);
			}break;
			
			case "Reversi":{
				factoria = new FactoriaReversi();
			}break;
		}
		if(movs == 0)
			movs = -1;
		aleatorio = factoria.creaJugadorAleatorio();
		reglas = factoria.creaReglas(movs);
		partida.reset(reglas);
		partida.cambiarJuego();
	}
	
	//Deshace un movimiento
	public void undo(){
		partida.undo();
	}
	
	//Reinicia una partida dejando el tablero vacio y el turno inicial
	public void reiniciar(){
		partida.reiniciar();
	}
	
	//Mira si la partida ha finalizado ¿Realmente es necesario?
	public void finalizar(){
		partida.isTerminada();
	}
	
	public void iniciaHebra(){
		partida.iniciaHebra();
	}
	
	//Añade un observador a partida
	public void addObservador(Observador v) {
		partida.addObservador(v);
	}
}
	
	