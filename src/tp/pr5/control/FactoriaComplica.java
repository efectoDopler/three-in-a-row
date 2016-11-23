package tp.pr5.control;
import tp.pr5.logica.*;

public class FactoriaComplica implements FactoriaTipoJuego{

	public ReglasJuego creaReglas(int movs){
		ReglasJuego reglas = new ReglasComplica(movs);
		return reglas;
	}
	
	//Solo crea el movimiento del jugador humano
	public Movimiento creaMovimiento(int col,int fila,Ficha color){
		Movimiento mov = new MovimientoComplica (col,color);
		return mov;
	}
	
	//Crea el jugadorConsola, solo sirve para ver la opcion del juego que selecciona
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){
		Jugador jugadorHumano = new JugadorHumanoConsolaComplica(in);
		return jugadorHumano;
	}
	
	//Crea al jugadorAleatorio este implementa el movimiento a hacer
	public Jugador creaJugadorAleatorio(){
		Jugador jugador = new JugadorAleatorioComplica();
		return jugador;
	}

	@Override
	public int devolverY() {
		return 0;
	}
}
