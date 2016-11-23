package tp.pr5.control;
import java.util.Scanner;

import tp.pr5.logica.*;

public class FactoriaReversi implements FactoriaTipoJuego{

	public ReglasJuego creaReglas(int movs) {
		ReglasJuego reglas = new ReglasReversi(movs);
		return reglas;
	}

	public Movimiento creaMovimiento(int col, int fila, Ficha color) {
		Movimiento mov = new MovimientoReversi (col, fila, color);
		return mov;
	}

	public Jugador creaJugadorHumanoConsola(Scanner in) {
		Jugador jugadorHumano = new JugadorHumanoConsolaReversi(in);
		return jugadorHumano;
	}

	public Jugador creaJugadorAleatorio() {
		Jugador jugador = new JugadorAleatorioReversi();
		return jugador;
	}

	public int devolverY() {
		return 0;
	}

}
