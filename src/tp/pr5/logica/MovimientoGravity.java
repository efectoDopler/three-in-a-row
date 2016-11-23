package tp.pr5.logica;

public class MovimientoGravity extends Movimiento{
	
	private int columna;
	private int fila;
	private Ficha ficha;
	private boolean movOk;
	
	public MovimientoGravity(int columna, int fila, Ficha color) {
		this.columna = columna;
		this.fila = fila;
		ficha = color;
	}

	public Ficha getJugador() {
		return ficha;
	}

	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean ok = true;
		int anchura = tab.getAncho();
		int altura = tab.getAlto();
		int menor, diferencia, distanciaFila, distanciaColumna;
		
		
			if((columna < 1)||(columna > anchura))
				ok = false;	
			if((fila < 1)||(fila > altura))
				ok = false;	
			
			movOk = ok;
			
			if(!ok) 
				throw new MovimientoInvalido("Posición incorrecta.");
			
			if(tab.getCasilla(columna, fila) != Ficha.VACIA) 
				throw new MovimientoInvalido("Casilla ocupada.");
			
			if(anchura <= altura) menor = anchura/2;
			else menor = altura/2;
			
			//Si no se dan las condiciones del if es que se encuantra en el centro del tablero 
			//y por lo tanto no se mueve
			if((fila*2)-1 != altura || (columna*2)-1 != anchura){
				
				diferencia = altura - fila;
				if(fila-1 <= diferencia)
					distanciaFila = fila-1;
				else distanciaFila = diferencia;
				
				diferencia = anchura - columna;
				if(columna-1 <= diferencia)
					distanciaColumna = columna-1;
				else distanciaColumna = diferencia;
				
				//Cae hacia arriba o abajo
				if(distanciaFila < distanciaColumna || (columna*2)-1 == anchura) {
					//hacia arriba
					/*if(fila <= menor) 
						caidaArriba(tab);*/
					if((fila*2)-1 < altura)
						caidaArriba(tab);
					//hacia abajo
					else 
						caidaAbajo(tab);
				}
				
				//Cae hacia uno de los laterales
				else if(distanciaFila > distanciaColumna || (fila*2)-1 == altura){
					//hacia izquierda
					/*if(columna <= menor) 
						caidaIzquierda(tab);*/
					if((columna*2)-1 < anchura)
						caidaIzquierda(tab);
					//hacia derecha
					else 
						caidaDerecha(tab);
				}
				
				//Cae en diagonal
				else {
					boolean diagonal = true;
					
					//Diagonal arriba
					if(fila <= menor) {
						//diagonal arriba-izquierda
						if(columna <= menor) {
							while(diagonal) {
								if((fila <= 1)||(columna <= 1))
									diagonal = false;
								else if(tab.getCasilla(columna-1, fila-1) != Ficha.VACIA)
									diagonal = false;
								else {
									columna--;
									fila--;
								}
							}
						}
						//diagonal arriba-derecha
						else{
							while(diagonal) {
								if((fila <= 1)||(columna >= tab.getAncho()))
									diagonal = false;
								else if(tab.getCasilla(columna+1, fila-1) != Ficha.VACIA)
									diagonal = false;
								else {
									columna++;
									fila--;
								}
							}
						}
					}
					//diagonal abajo
					else{
						//diagonal abajo-izquierda
						if(columna <= menor) {
							while(diagonal) {
								if((fila >= tab.getAlto())||(columna <= 1))
									diagonal = false;
								else if(tab.getCasilla(columna-1, fila+1) != Ficha.VACIA)
									diagonal = false;
								else {
									columna--;
									fila++;
								}
							}
						}
						//diagonal abajo-derecha
						else{
							while(diagonal) {
								if((fila >= tab.getAlto())||(columna >= tab.getAncho()))
									diagonal = false;
								else if(tab.getCasilla(columna+1, fila+1) != Ficha.VACIA)
									diagonal = false;
								else {
									columna++;
									fila++;
								}
							}
						}
					}
				}
				
			}
			tab.setCasilla(columna, fila, ficha);
	}
	
	public void caidaAbajo(Tablero tab) {
		while((tab.getCasilla(columna, fila+1) == Ficha.VACIA)&&(fila < tab.getAlto())) {
			fila++;
		}
	}
	
	public void caidaArriba(Tablero tab) {
		while((tab.getCasilla(columna, fila-1) == Ficha.VACIA)&&(fila > 1)) {
			fila--;
		}
	}
	
	public void caidaIzquierda(Tablero tab) {
		while((tab.getCasilla(columna-1, fila) == Ficha.VACIA)&&(columna > 1)) {
			columna--;
		}
	}
	
	public void caidaDerecha(Tablero tab) {
		while((tab.getCasilla(columna+1, fila) == Ficha.VACIA)&&(columna < tab.getAncho())) {
			columna++;
		}
	}

	public void undo(Tablero tab) {
		//A traves de partida le pasamos la posicion
		tab.setCasilla(columna, fila, Ficha.VACIA);
	}

	//devuelve la columna
	public int devPos() {
		return columna;
	}
	
	//devuelve la fila
	public int devFila() {
		return fila;
	}

	@Override
	public boolean devOk() {
		return movOk;
	}
}
