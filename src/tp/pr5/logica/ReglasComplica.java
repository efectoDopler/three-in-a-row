package tp.pr5.logica;

public class ReglasComplica extends comprobarSecuencias{
	private Tablero tablero;
	final private int N = 4;
	final private int M = 7;
	final private int movs;
	
	public ReglasComplica(int movs) {
		this.movs = movs;
		tablero = iniciaTablero();
	}

	public Tablero iniciaTablero() {
		Tablero tablero = new Tablero(N, M);
		return tablero;
	}

	public Ficha jugadorInicial() {
		return Ficha.BLANCA;
	}

	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t, int movimientosB, int movimientosN) {
		Ficha ganador = Ficha.VACIA;
		Ficha ficha = Ficha.VACIA;
		Ficha aux = Ficha.VACIA;
		boolean secOpuestas, secuencia, comprobar;
		boolean noMovs = false;
		secOpuestas = false;
		secuencia = false;
		int columna = ultimoMovimiento.devPos();
		int altura = t.getAlto();
		int contAlt = 1;
		tablero = t;
		
		if(movimientosB == 0 && movimientosN == 0)
			noMovs = true;
		
		if(!noMovs){
			if(columna >= 1 && columna <= t.getAncho()){
				while((contAlt <= altura)&&(!secOpuestas)) {
					ficha = t.getCasilla(columna,contAlt);
					comprobar = false;
					
					if(ficha != Ficha.VACIA) {
						if(!comprobar) 
							comprobar = comprobarFila(columna,contAlt,ficha,tablero); 
							
						if(!comprobar)
							comprobar = comprobarColumna(columna,contAlt,ficha,tablero);
					
						if(!comprobar)
							comprobar = comprobarDiagonalDer(columna,contAlt,ficha,tablero);
						
						if(!comprobar)
							comprobar = comprobarDiagonalIzq(columna,contAlt,ficha,tablero);
					}
					
					/*Compruebo cada fila en busca de secuencias de fichas, si solo hay de un solo color
					 * doy por ganadora a esa ficha pero en caso de que halla secuencias de ambos colores no hay ganador*/
					if(comprobar) {
						if((ficha != aux)&&(aux != Ficha.VACIA)) {
							secOpuestas = true;
							secuencia = false;
						}
						else {
							secuencia = true;
							aux = ficha;
						}
					}
					contAlt++;
				}
				
				if(secuencia) 
					ganador = aux;
			}
		}
		return ganador;
	}

	//Complica no puede terminar en tablas
	public boolean tablas(Ficha ultimoEnPoner, Tablero t, int movimientosB, int movimientosN) {
		boolean tablas = false;
		if(movimientosB == 0 && movimientosN == 0)
			tablas = true;
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

	@Override
	public int numMovimientos() {
		return movs;
	}
}



