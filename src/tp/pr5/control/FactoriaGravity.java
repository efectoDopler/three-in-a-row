package tp.pr5.control;
import tp.pr5.logica.*;

public class FactoriaGravity implements FactoriaTipoJuego{
	private int valorX;
	private int valorY;
	
	//Es el unico que necesita un constructor donde meterle valores para crear un tablero de tama�o variable
	public FactoriaGravity(int x, int y){
		valorX = x;
		valorY = y;
	}
	
	//Crea las reglas con el tama�o de tablero establecido por el constructor
	public ReglasJuego creaReglas(int movs){
		ReglasJuego reglas = new ReglasGravity(valorX,valorY,movs);
		return reglas;
	}
	
	//Solo crea el movimiento del jugador humano
	public Movimiento creaMovimiento(int col, int fila, Ficha color){
		Movimiento mov = new MovimientoGravity (col, fila, color);
		return mov;
	}
	
	//Crea el jugadorConsola, solo sirve para ver la opcion del juego que selecciona
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){
		Jugador jugadorHumano = new JugadorHumanoConsolaGravity(in);
		return jugadorHumano;
	}
	
	//Crea al jugadorAleatorio este implementa el movimiento a hacer
	public Jugador creaJugadorAleatorio(){
		Jugador jugador = new JugadorAleatorioGravity();
		return jugador;
	}

	@Override
	public int devolverY() {
		return valorY;
	}
}
