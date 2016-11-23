package tp.pr5.control;
import tp.pr5.logica.*;

public interface FactoriaTipoJuego {
	public ReglasJuego creaReglas(int movs);
	public Movimiento creaMovimiento(int col,int fila,Ficha color);
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in);
	public Jugador creaJugadorAleatorio();
	public int devolverY();
}
