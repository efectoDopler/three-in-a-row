package tp.pr5.control;
import tp.pr5.logica.*;

import java.util.Scanner;

@SuppressWarnings("unused")
public class JugadorHumanoConsolaConecta4 extends JugadorHumanoConsola{
	public JugadorHumanoConsolaConecta4(java.util.Scanner in){
		sc = in;
	}
	
	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		System.out.print("Introduce la columna: ");
		String columAux = sc.next();
		if(ValidaNumerico(columAux))
			columna = Integer.parseInt(columAux);
		FactoriaTipoJuego fac = new FactoriaConecta4();
		return fac.creaMovimiento(columna,0, color);
	}
}
