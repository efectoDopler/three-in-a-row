package tp.pr5.control;

import tp.pr5.logica.*;

import java.util.Random;

public class JugadorAleatorioReversi implements Jugador{

	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		boolean ok = false;
		int columna, fila;
		ReglasReversi aux = new ReglasReversi(0);
		do{
			Random r = new Random();
			columna = r.nextInt(tab.getAncho())+1;
			fila = r.nextInt(tab.getAlto())+1;
			if(tab.getCasilla(columna, fila) == Ficha.VACIA){
				if(aux.compruebaFila(tab, columna, fila, color)) ok = true;
				
				else if(aux.compruebaColumna(tab, columna, fila, color)) ok = true;
				
				else if(aux.compruebaDiagonalDerecha(tab, columna, fila, color)) ok = true;
				
				else if(aux.compruebaDiagonalIzquierda(tab, columna, fila, color)) ok = true;
			}
			
		}while(!ok);
		FactoriaTipoJuego fac = new FactoriaReversi();
		return fac.creaMovimiento(columna,fila,color);
	}

}
