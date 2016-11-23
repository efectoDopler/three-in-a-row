package tp.pr5.logica;

public abstract class comprobarSecuencias implements ReglasJuego{
	
	public boolean posiblePonerFicha(Ficha turno, Tablero t){
		return true;
	}
	
	public boolean comprobarFila(int colum, int alt, Ficha ficha,Tablero tablero) {
		boolean ok = true;
		int contador = 0;
		int fila = alt+1;

		while(contador<3 && ok){
			if((tablero.getCasilla(colum, fila)!=ficha)||(fila > tablero.getAlto()))
				ok = false;
			
			else{
				++contador;
				++fila;
			}	
		}
		return ok;	
	}

	public boolean comprobarColumna(int colum, int alt, Ficha ficha,Tablero tablero) {
		boolean ok = true;
		boolean izq = true;
		boolean der = true;
		int colIzq = colum-1;
		int colDer = colum+1;
		int contador = 0;
		
		while((contador < 3) && (ok)){
			
			while(der){
				
			  if((tablero.getCasilla(colDer,alt)!=ficha)||(colDer > tablero.getAncho()))
					der = false;
			  
			  else{
					++colDer;
					++contador;
			  }
			}
			while(izq){
				
				if((tablero.getCasilla(colIzq,alt) != ficha) || (colIzq < 1))
					izq = false;
				
				else{
					--colIzq;
					++contador;
				}
			}
			if(!der && !izq && contador <3)
				ok = false;
		}
		return ok;
	}

	public boolean comprobarDiagonalDer(int colum, int alt, Ficha ficha,Tablero tablero) {
		boolean ok = true;
		boolean izq = true;
		boolean der = true;
		int contador = 0;
		int col = colum+1;
		int fil = alt+1;
		int col2 = colum-1;
		int fil2 = alt-1;
		
		while(contador <3 && ok){
			while(der) {
				if(ficha == tablero.getCasilla(col,fil)) {
					++contador;
					++col;
					++fil;
				}
				else der = false;
			}
			while(izq) {
				if(ficha == tablero.getCasilla(col2,fil2)) {
					++contador;
					--col2;
					--fil2;
				}
				else izq = false;
			}
			if(!der && !izq && contador <3)
				ok = false;	
		}
		return ok;
	}

	public boolean comprobarDiagonalIzq(int colum, int alt, Ficha ficha,Tablero tablero) {
		boolean ok = true;
		boolean izq = true;
		boolean der = true;
		int contador = 0;
		int col = colum-1;
		int fil = alt+1;
		int col2 = colum+1;
		int fil2 = alt-1;
		
		while(contador <3 && ok){
			while(der) {
				if(ficha == tablero.getCasilla(col,fil)) {
					++contador;
					--col;
					++fil;
				}
				else der = false;
			}
			while(izq) {
				if(ficha == tablero.getCasilla(col2,fil2)) {
					++contador;
					++col2;
					--fil2;
				}
				else izq = false;
			}
		
			if(!der && !izq && contador <3)
				ok = false;	
		}
		return ok;
	}
}
