package tp.pr5.logica;

public class ReglasGravity extends comprobarSecuencias{
	private Tablero tablero;
	private int columTablero;
	private int filasTablero;
	private int movs;
	public ReglasGravity(int numCols, int numFilas, int movs) {
		this.movs = movs;
		columTablero = numCols;
		filasTablero = numFilas;
		tablero = iniciaTablero();
	}

	public Tablero iniciaTablero() {
		Tablero tablero = new Tablero(columTablero, filasTablero);
		return tablero;
	}

	public Ficha jugadorInicial() {
		return Ficha.BLANCA;
	}

	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t, int movimientosB, int movimientosN) {
		Ficha ganador = Ficha.VACIA;
		boolean valido = true;
		boolean noMovs = false;
		tablero = t;
		
		if(movimientosB == 0 && movimientosN == 0)
			noMovs = true;
		
		if(!noMovs){
			if((ultimoMovimiento.devPos() < 1) || (ultimoMovimiento.devPos() > columTablero))
				valido = false;
			
			else if((ultimoMovimiento.devFila() < 1) || (ultimoMovimiento.devFila() > filasTablero))
				valido = false;
			
			if(valido) {
				boolean secuencia = false;
				int altura = ultimoMovimiento.devFila();
				int columna = ultimoMovimiento.devPos();
				
				/*En la ultima ficha que hemos colocado comprobamos en todas las direcciones 
				 * si hay secuencia o no*/
				
				Ficha ficha = t.getCasilla(columna,altura);
				
				if(!secuencia) 
					secuencia = comprobarFila(columna,altura,ficha); 
					
				if(!secuencia)
					secuencia = comprobarColumna(columna,altura,ficha,tablero);
			
				if(!secuencia)
					secuencia = comprobarDiagonalDer(columna,altura,ficha,tablero);
				
				if(!secuencia)
					secuencia = comprobarDiagonalIzq(columna,altura,ficha,tablero);
			
			
				if(secuencia)
					ganador = ficha;
			}
		}
		return ganador;
	}

	public boolean tablas(Ficha ultimoEnPoner, Tablero t, int movimientoB, int movimientoN) {
		boolean lleno = true;
		boolean noMovs = false;
		int altura, anchura;
		altura = t.getAlto();
		anchura = t.getAncho();
		
		if(movimientoB == 0 && movimientoN == 0)
			noMovs = true;
		
		if(!noMovs){
			for(int auxColum = 1; auxColum <= anchura; auxColum++) {
				for(int auxFila = 1; auxFila <= altura; auxFila++) {
					if(t.getCasilla(auxColum, auxFila) == Ficha.VACIA) lleno = false;
				}
			}
		}

		return lleno;
	}

	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha aux = Ficha.VACIA;
		
		if(ultimoEnPoner == Ficha.BLANCA) 
			aux = Ficha.NEGRA;
		
		else if(ultimoEnPoner == Ficha.NEGRA)
			aux = Ficha.BLANCA;
		
		return aux;
	}

	public boolean comprobarFila(int colum, int alt, Ficha ficha) {
		boolean ok = true;
		boolean arriba = true;
		boolean abajo = true;
		int contador = 0;
		int filaAbajo = alt+1;
		int filaArriba = alt-1;

		while(contador<3 && ok){
			while(arriba) {
				if((tablero.getCasilla(colum, filaArriba)!=ficha)||(filaArriba < 1))
					arriba = false;
				else{
					++contador;
					--filaArriba;
				}
			}
			
			while(abajo){
				if((tablero.getCasilla(colum, filaAbajo)!=ficha)||(filaAbajo > tablero.getAlto()))
					abajo = false;
				else{
					++contador;
					++filaAbajo;
				}
			}
			
			if(!arriba && !abajo && contador <3)
				ok = false;
		}
		
		return ok;	
	}

	public boolean esGravity() {
		return true;
	}
	
	public int numMovimientos() {
		return movs;
	}
}




