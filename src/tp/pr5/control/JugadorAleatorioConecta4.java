package tp.pr5.control;
import tp.pr5.logica.*;
import java.util.Random;

public class JugadorAleatorioConecta4 implements Jugador{

	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		int valorDado;
		do{
			Random r = new Random();
			valorDado = r.nextInt(tab.getAncho())+1;
		}while(tab.getCasilla(valorDado, 1) != Ficha.VACIA);
		FactoriaTipoJuego fac = new FactoriaConecta4();
		return fac.creaMovimiento(valorDado,0,color);
	}
}

