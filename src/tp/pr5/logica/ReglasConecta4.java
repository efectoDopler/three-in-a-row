package tp.pr5.logica;

public class ReglasConecta4 extends comprobarSecuencias{
	private Tablero tablero;
	final private int N = 7;
	final private int M = 6;
	private int movs;
	
	public ReglasConecta4(int movs) {
		this.movs = movs;
		tablero = iniciaTablero();
	}

	public Tablero iniciaTablero() {
		Tablero tablero = new Tablero(N, M);
		return tablero;
	}

	@Override
	public Ficha jugadorInicial() {
		return Ficha.BLANCA;
	}

	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t, int movimientoB, int movimientoN) {
		Ficha ganador = Ficha.VACIA;
		tablero = t;
		boolean secuencia = false;
		int altura = 1;
		int columna = ultimoMovimiento.devPos();

			if(columna >= 1 && columna <= t.getAncho()){
			/*En la ultima ficha que hemos colocado comprobamos en todas las direcciones 
			 * si hay secuencia o no*/
			while((t.getCasilla(columna, altura) == Ficha.VACIA) && (altura <= t.getAlto()))
				++altura;
			
			Ficha ficha = t.getCasilla(columna,altura);
			
			if(!secuencia) 
				secuencia = comprobarFila(columna,altura,ficha,tablero); 
				
			if(!secuencia)
				secuencia = comprobarColumna(columna,altura,ficha,tablero);
		
			if(!secuencia)
				secuencia = comprobarDiagonalDer(columna,altura,ficha,tablero);
			
			if(!secuencia)
				secuencia = comprobarDiagonalIzq(columna,altura,ficha,tablero);
		
		
			if(secuencia)
				ganador = ficha;

		}
		return ganador;
	}

	public boolean tablas(Ficha ultimoEnPoner, Tablero t, int movimientoB, int movimientoN) {
		boolean noMovs = false;
		boolean tablas = true;
		int i = 1;
		
		if(movimientoB == 0 && movimientoN == 0)
			noMovs = true;
		
		if(!noMovs){
			while((i <= t.getAncho()) &&(tablas)) {
				
				if(movimientoB == 0 && movimientoN == 0)
					tablas = true;
				else{
					if(t.getCasilla(i, 1) != Ficha.VACIA)
						i++;
					
					else
						tablas = false;
				}
			}	
		}
		return tablas;
	}

	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha aux = Ficha.VACIA;
		
		if(ultimoEnPoner == Ficha.BLANCA) 
			aux = Ficha.NEGRA;
		
		else if(ultimoEnPoner == Ficha.NEGRA)
			aux = Ficha.BLANCA;
		
		return aux;
	}
	
	public boolean esGravity() {
		return false;
	}
	
	public int numMovimientos() {
		return movs;
	}
}




