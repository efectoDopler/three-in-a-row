package tp.pr5.control;
import java.util.Random;
import tp.pr5.logica.*;

public class JugadorAleatorioComplica implements Jugador{

	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		int valorDado;
		Random r = new Random();
		valorDado = r.nextInt(tab.getAncho())+1;
		FactoriaTipoJuego fac = new FactoriaComplica();
		return fac.creaMovimiento(valorDado,0,color);
	}

}
