package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorHumanoConsolaReversi extends JugadorHumanoConsola{
	
		public JugadorHumanoConsolaReversi(java.util.Scanner in){
			sc = in;
		}
		
		public Movimiento getMovimiento(Tablero tab, Ficha color) {
			boolean filaCorrecta, columnaCorrecta;
			filaCorrecta = false;
			columnaCorrecta = false;
			String columAux, filaAux;
			while(!filaCorrecta && !columnaCorrecta) {
				System.out.print("Introduce la columna: ");
				columAux = sc.next();
				columnaCorrecta = ValidaNumerico(columAux);
				if(columnaCorrecta){
					do{
						columna = Integer.parseInt(columAux);
						System.out.println("Introduce la fila: ");
						filaAux = sc.next();
						filaCorrecta = ValidaNumerico(filaAux);
					}while(!filaCorrecta);
					fila = Integer.parseInt(filaAux);
				}
			}
			FactoriaTipoJuego fac = new FactoriaReversi();
			return fac.creaMovimiento(columna,fila, color);
		}
}
