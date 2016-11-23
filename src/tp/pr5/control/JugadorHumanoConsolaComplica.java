package tp.pr5.control;
import tp.pr5.logica.*;

public class JugadorHumanoConsolaComplica extends JugadorHumanoConsola{	
	//Introducimos la opcion que quiere tener el jugador 
	public JugadorHumanoConsolaComplica(java.util.Scanner in){
		sc = in;
	}
	
	//No se usa, solo usa el crearMovimiento de FactoriaJuego
	public Movimiento getMovimiento(Tablero tab, Ficha color) {
		System.out.print("Introduce la columna: ");
		String columAux = sc.next();
		if(ValidaNumerico(columAux))
			columna = Integer.parseInt(columAux);
		FactoriaTipoJuego fac = new FactoriaComplica();
		return fac.creaMovimiento(columna,0, color);
	}
}
