package tp.pr5.logica;

public interface ReglasJuego {

	Tablero iniciaTablero();
	
	Ficha jugadorInicial();
	
	Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t, int movimientoB, int movimientoN);
	
	boolean tablas(Ficha ultimoEnPoner, Tablero t, int movimientoB, int movimientoN);
	
	Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t);
	
	public boolean esGravity();
	
	public boolean posiblePonerFicha(Ficha turno, Tablero t);
	
	public int numMovimientos();
}
