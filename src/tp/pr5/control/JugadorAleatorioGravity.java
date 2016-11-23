package tp.pr5.control;
import java.util.Random;

import tp.pr5.logica.*;

public class JugadorAleatorioGravity implements Jugador{

	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		int columna, fila;
		do{
			Random r = new Random();
			columna = r.nextInt(tab.getAncho())+1;
			fila = r.nextInt(tab.getAlto())+1;
		}while(tab.getCasilla(columna, fila) != Ficha.VACIA);
		FactoriaTipoJuego fac = new FactoriaGravity(tab.getAncho(),tab.getAlto());
		return fac.creaMovimiento(columna,fila,color);
	}
}

