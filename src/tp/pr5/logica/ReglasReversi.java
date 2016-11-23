package tp.pr5.logica;

public class ReglasReversi extends comprobarSecuencias{
	private Tablero tablero;
	private boolean finalizado;
	final private int N = 8;
	final private int M = 8;
	private int movs;
	
	public ReglasReversi(int movs){
		this.movs = movs;
		tablero = iniciaTablero();
		finalizado = false;
	}
	
	public Tablero iniciaTablero(){
		Tablero tablero = new Tablero(N, M);
		tablero.setCasilla(N/2, N/2,Ficha.BLANCA);
		tablero.setCasilla(N/2, (N/2)+1,Ficha.NEGRA);
		tablero.setCasilla((N/2)+1, N/2,Ficha.NEGRA);
		tablero.setCasilla((N/2)+1, (N/2)+1,Ficha.BLANCA);
		return tablero;
	}

	
	public Ficha jugadorInicial() {
		return Ficha.NEGRA;
	}

	
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t, int movimientosB, int movimientosN) {
		boolean noMovs = false;
		Ficha ganador = Ficha.VACIA;
		
		if(movimientosB == 0 && movimientosN == 0)
			noMovs = true;
		
		if(!noMovs){
			if(tableroLleno(t) && finalizado)
				ganador = ganador(t);
		}
		else
			ganador = ganador(t);
		
		return ganador;
	}
	
	private Ficha ganador(Tablero t){
		Ficha ganador = Ficha.VACIA;
		int blancas = 0;
		int negras = 0;
		
		for(int i = 1; i <= t.getAncho(); i++){
			for(int j = 1; j <= t.getAlto(); j++){
				if(t.getCasilla(i, j) == Ficha.BLANCA) blancas++;
				else if(t.getCasilla(i, j) == Ficha.NEGRA) negras++;
			}
		}
		if(blancas > negras) 
			ganador = Ficha.BLANCA;
		else if(negras > blancas) 
			ganador = Ficha.NEGRA;
		else 
			ganador = Ficha.BLANCA;
		
		return ganador;
	}
	
	private boolean tableroLleno(Tablero t){
		boolean lleno = true;
		int i = 1, j = 1;
		while(lleno && i <= t.getAlto()){
			j = 1;
			while(lleno && j <= t.getAncho()){
				if(t.getCasilla(i, j) == Ficha.VACIA)
					lleno = false;
				else
					j++;
			}
			i++;
		}
		finalizado = lleno;
		return lleno;
	}
	
	public boolean tablas(Ficha ultimoEnPoner, Tablero t, int movimientoB, int movimientoN) {
		//la partida finaliza cuando todas la fichas del tablero son ocupadas
		return false;
	}

	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha aux = Ficha.VACIA;
		
		if(ultimoEnPoner == Ficha.BLANCA) {
			if(posiblePonerFicha(Ficha.NEGRA,t))
				aux = Ficha.NEGRA;
			else if(posiblePonerFicha(Ficha.BLANCA,t))
				aux = Ficha.BLANCA;
			else
				finalizado = true;
		}
		else if(ultimoEnPoner == Ficha.NEGRA){
			if(posiblePonerFicha(Ficha.BLANCA,t))
				aux = Ficha.BLANCA;
			else if(posiblePonerFicha(Ficha.NEGRA,t))
				aux = Ficha.NEGRA;
			else
				finalizado = true;
		}
		return aux;
	}

	public boolean esGravity() {
		return false;
	}
	
	
	public boolean posiblePonerFicha(Ficha turno, Tablero t) {
		boolean posible = false;
		int contFila = 1;
		int contColumna = 1;
		int alto, ancho;
		alto = t.getAlto();
		ancho = t.getAncho();
		
		while(!posible && contColumna <= ancho) {
			while(!posible && contFila <= alto) {
				
				if(t.getCasilla(contColumna, contFila) == Ficha.VACIA) {
					//Aqui introduzco todas las comprobaciones posibles
					if(compruebaFila(t, contColumna, contFila, turno)) posible = true;
					
					else if(compruebaColumna(t, contColumna, contFila, turno)) posible = true;
					
					else if(compruebaDiagonalDerecha(t, contColumna, contFila, turno)) posible = true;
					
					else if(compruebaDiagonalIzquierda(t, contColumna, contFila, turno)) posible = true;
				}
				
				contFila++;
			}
			contFila = 1;
			contColumna++;
		}
		
		return posible;
	}
	
		//Comprueba si hay combinaciones en una fila
		public boolean compruebaFila(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = false;
			boolean arriba = miraFilaArriba(tab, columna, fila, ficha);
			boolean abajo = miraFilaAbajo(tab, columna, fila, ficha);
			
			if(arriba || abajo)
				ok = true;
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en la parte superior de la fila
		private boolean miraFilaArriba(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false;
			int filaAux = fila-1;
			
			if(tab.getCasilla(columna, filaAux) == Ficha.VACIA || tab.getCasilla(columna, filaAux) == ficha || filaAux < 1)
				ok = false;
			else{
				filaAux--;
				while(!combinacion && filaAux > 0 && !vacio){
					if(tab.getCasilla(columna, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(columna, filaAux) == Ficha.VACIA){
						vacio = true;
					}
					else
						filaAux--;
				}
				if(vacio || !combinacion) ok = false;	
			}
			return ok;
		}
		
		//Comprueba si hay combinaciones en la parte inferior de la fila
		private boolean miraFilaAbajo(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false;
			int filaAux = fila +1;
			int colum = columna;

			//Mirar si es && o ||
			if(tab.getCasilla(colum, filaAux) == Ficha.VACIA || tab.getCasilla(colum, filaAux) == ficha || filaAux > tab.getAlto())
				ok = false;
			else{
				filaAux++;
				while(!combinacion && filaAux <= tab.getAlto() && !vacio){
					if(tab.getCasilla(colum, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(colum, filaAux) == Ficha.VACIA)
						vacio = true;
					else
						filaAux++;
				}
				
				if(vacio || !combinacion)
					ok = false;
					
			}
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en una columna
		public boolean compruebaColumna(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = false;
			boolean izq = mirarColumnaIzq(tab, columna, fila, ficha);
			boolean drc = mirarColumnaDrc(tab, columna, fila, ficha);
			
			if(izq || drc)
				ok = true;
			return ok;
		}
		
		//Comprueba si hay combinaciones en la parte izquierda de la columna
		private boolean mirarColumnaIzq(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = true, combinacion = false, vacio = false;
			int colum = columna-1;
			
			if(tab.getCasilla(colum, fila) == Ficha.VACIA || tab.getCasilla(colum, fila) == ficha || colum < 1)
				ok = false;
			else{
				colum--;
				while(!combinacion && colum > 0 && !vacio){
					if(tab.getCasilla(colum, fila) == ficha)
						combinacion = true;
					else if(tab.getCasilla(colum, fila) == Ficha.VACIA)
						vacio = true;
					else
						colum--;
				}
				if(vacio || !combinacion) ok = false;	
			}
			return ok;
		}
		
		//Comprueba si hay combinaciones en la parte derecha de la columna
		private boolean mirarColumnaDrc(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = true, combinacion = false, vacio = false;
			int colum = columna+1;
			
			if(tab.getCasilla(colum, fila) == Ficha.VACIA || tab.getCasilla(colum, fila) == ficha || colum > tab.getAncho())
				ok = false;
			else{
				colum++;
				while(!combinacion && colum <= tab.getAncho() && !vacio){
					if(tab.getCasilla(colum, fila) == ficha)
						combinacion = true;
					else if(tab.getCasilla(colum, fila) == Ficha.VACIA)
						vacio = true;
					else
						colum++;
				}
				if(vacio || !combinacion) ok = false;	
			}
			
			return ok;
		}

		//Comprueba si hay combinaciones en las diagonales derechas
		public boolean compruebaDiagonalDerecha(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = false;
			boolean arriba = miraDiagonalDerArriba(tab, columna, fila, ficha);
			boolean abajo = miraDiagonalDerAbajo(tab, columna, fila, ficha);
			
			if(arriba||abajo)
				ok = true;
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en las diagonales derechas superiores
		private boolean miraDiagonalDerArriba(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false, vuelta = false;
			int filaAux = fila-1;
			int columAux = columna+1;
			
			
			//Mirar si es && o ||
			if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA || tab.getCasilla(columAux, filaAux) == ficha ||(filaAux < 1 && columAux > tab.getAncho()))
				ok = false;
			else{
				filaAux--;
				columAux++;
				while(!combinacion && filaAux > 0 && !vacio){
					if(tab.getCasilla(columAux, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA){
						vacio = true;
					}
					else {
						filaAux--;
						columAux++;
					}
				}
				
				if(vacio || !combinacion) ok = false;	
			}
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en las diagonales inferiores
		private boolean miraDiagonalDerAbajo(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false, vuelta = false;
			int filaAux = fila+1;
			int columAux = columna+1;
			
			
			//Mirar si es && o ||
			if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA || tab.getCasilla(columAux, filaAux) == ficha || (filaAux > tab.getAlto() && columAux > tab.getAncho()))
				ok = false;
			else{
				filaAux++;
				columAux++;
				while(!combinacion && filaAux > 0 && !vacio){
					if(tab.getCasilla(columAux, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA){
						vacio = true;
					}
					else {
						filaAux++;
						columAux++;
					}
				}
				
				if(vacio || !combinacion) ok = false;	
			}
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en las diagonales izquierdas
		public boolean compruebaDiagonalIzquierda(Tablero tab, int columna, int fila, Ficha ficha){
			boolean ok = false;
			boolean arriba = miraDiagonalIzqArriba(tab, columna, fila, ficha);
			boolean abajo = miraDiagonalIzqAbajo(tab, columna, fila, ficha);
			
			if(arriba||abajo)
				ok = true;
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en las diagonales izquierdas superiores
		private boolean miraDiagonalIzqArriba(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false, vuelta = false;
			int filaAux = fila-1;
			int columAux = columna-1;
			
			
			//Mirar si es && o ||
			if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA || tab.getCasilla(columAux, filaAux) == ficha || (filaAux < 1 && columAux < 1))
				ok = false;
			else{
				filaAux--;
				columAux--;
				while(!combinacion && filaAux > 0 && !vacio){
					if(tab.getCasilla(columAux, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA){
						vacio = true;
					}
					else {
						filaAux--;
						columAux--;
					}
				}
				
				if(vacio || !combinacion) ok = false;	
			}
			
			return ok;
		}
		
		//Comprueba si hay combinaciones en las diagonales izquierdas inferiores
		private boolean miraDiagonalIzqAbajo(Tablero tab, int columna, int fila, Ficha ficha) {
			boolean ok = true, combinacion = false, vacio = false, vuelta = false;
			int filaAux = fila+1;
			int columAux = columna-1;
			
			
			//Mirar si es && o ||
			if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA || tab.getCasilla(columAux, filaAux) == ficha || (filaAux > tab.getAlto() && columAux < 1))
				ok = false;
			else{
				filaAux++;
				columAux--;
				while(!combinacion && filaAux > 0 && !vacio){
					if(tab.getCasilla(columAux, filaAux) == ficha)
						combinacion = true;
					else if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA){
						vacio = true;
					}
					else {
						filaAux++;
						columAux--;
					}
				}
				
				if(vacio || !combinacion) ok = false;	
			}
			
			return ok;
		}
	
		public int numMovimientos() {
			return movs;
		}
}




