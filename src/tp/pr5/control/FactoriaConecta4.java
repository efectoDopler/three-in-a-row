package tp.pr5.control;
import tp.pr5.logica.*;

public class FactoriaConecta4 implements FactoriaTipoJuego{

	public ReglasJuego creaReglas(int movs){
		ReglasJuego reglas = new ReglasConecta4(movs);
		return reglas;
	}
	
	//Solo crea el movimiento del jugador humano
	public Movimiento creaMovimiento(int col,int fila,Ficha color){
		Movimiento mov = new MovimientoConecta4 (col,color);
		return mov;
	}
	
	//Crea el jugadorConsola, solo sirve para ver la opcion del juego que selecciona
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in){
		Jugador jugadorHumano = new JugadorHumanoConsolaConecta4(in);
		return jugadorHumano;
	}
	
	//Crea al jugadorAleatorio este implementa el movimiento a hacer
	public Jugador creaJugadorAleatorio(){
		Jugador jugadorAleatorio = new JugadorAleatorioConecta4();
		return jugadorAleatorio;
	}

	
	public int devolverY() {
		return 0;
	}
}
