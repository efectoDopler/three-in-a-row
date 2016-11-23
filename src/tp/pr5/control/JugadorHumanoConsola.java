package tp.pr5.control;
import tp.pr5.logica.*;
import java.util.Scanner;

public class JugadorHumanoConsola implements Jugador{
	public Scanner sc;
	public int columna;
	public int fila;
	public FactoriaTipoJuego fac;
		
	public JugadorHumanoConsola(){}
	public JugadorHumanoConsola(java.util.Scanner in){
		sc = in;
	}
	
	public void cargarFactoria(FactoriaTipoJuego factoria) {
		fac = factoria;
	}

	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		System.out.print("Introduce la columna ");
		columna = sc.nextInt();
		if(fac.devolverY() != 0 ){
			System.out.println("Introduce la fila: ");
			fila = sc.nextInt();
		}
		return fac.creaMovimiento(columna,fila, color);
	}
	
	public boolean ValidaNumerico(String cadena)  {
		try {
			int numero = Integer.parseInt(cadena);
			return true;
		}catch(NumberFormatException nfe) {
			System.err.println("Has introducido un caracter no numérico");
			return false;
		}
	}
}