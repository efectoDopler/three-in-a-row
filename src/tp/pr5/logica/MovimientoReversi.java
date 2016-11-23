package tp.pr5.logica;

public class MovimientoReversi extends Movimiento{
	private int columna;
	private int fila;
	private Ficha ficha;
	//Color de la ficha que se ha cambiado en un movimiento
	private Ficha colorAnterior;
	private boolean movOk;
	//Array que guarda el numero de combinaciones que consigue un movimiento al cambiar fichas de color
	private int [] combinaciones;
	//Array que guarda el numero de casillas a deshacer por posicion
	private int [] voltear;
	private int [] vAux;
	//Tamaño del array combinaciones
	private final int N = 8;
	//Contador del array combinaciones
	private int contador;
	//Declaracion movimientos posibles
	private final int fiArriba = 0;
	private final int fiAbajo = 1;
	private final int colIzq = 2;
	private final int colDrc = 3;
	private final int dDrcArriba = 4;
	private final int dDrcAbajo = 5;
	private final int dIzqArriba = 6;
	private final int dIzqAbajo = 7;
	
	public MovimientoReversi(int columna, int fila, Ficha color) {
		this.columna = columna;
		this.fila = fila;
		ficha = color;
		combinaciones = new int[N];
		voltear = new int[N];
		vAux = new int[2];
		contador = 0;
		movOk = false;
		
		switch(ficha){
			case BLANCA: colorAnterior = Ficha.NEGRA;break;
			case NEGRA:  colorAnterior = Ficha.BLANCA;break;
			default:
		}
	}

	public Ficha getJugador() {
		return ficha;
	}

	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean ok = true;
		int anchura = tab.getAncho();
		int altura = tab.getAlto();
		
		if((columna < 1)||(columna > anchura))
			ok = false;	
		if((fila < 1)||(fila > altura))
			ok = false;	
		
		
		if(!ok) throw new MovimientoInvalido("Posición incorrecta.");
		
		if(tab.getCasilla(columna, fila) != Ficha.VACIA) 
			throw new MovimientoInvalido("Casilla ocupada.");
		
		else {
			if(compruebaFila(tab)) movOk = true;
			
			if(compruebaColumna(tab)) movOk = true;
			
			if(compruebaDiagonalDerecha(tab)) movOk = true;
			
			if(compruebaDiagonalIzquierda(tab)) movOk = true;
			
			if(movOk) tab.setCasilla(columna, fila, ficha);
			
			else throw new MovimientoInvalido("Posición incorrecta.");	
		}
	}
	
	//Comprueba si hay combinaciones en una fila
	private boolean compruebaFila(Tablero tab){
		boolean ok = false;
		boolean arriba = miraFilaArriba(tab);
		boolean abajo = miraFilaAbajo(tab);
		
		if(arriba || abajo)
			ok = true;
		
		if(arriba){
			combinaciones[contador] = fiArriba;
			voltear[contador] = vAux[0];
			contador++;
		}
		if(abajo){
			combinaciones[contador] = fiAbajo;
			voltear[contador] = vAux[1];
			contador++;
		}
		return ok;
	}
	
	//Comprueba si hay combinaciones en la parte superior de la fila
	private boolean miraFilaArriba(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false;
		int filaAux = fila-1;
		vAux[0] = 0;
		
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
			if(combinacion){
				for(int i = filaAux; i < fila; i++){
					tab.setCasilla(columna, i, ficha);
					vAux[0]++;
				}
			}
			if(vacio || !combinacion) ok = false;	
		}
		return ok;
	}
	
	//Comprueba si hay combinaciones en la parte inferior de la fila
	private boolean miraFilaAbajo(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false;
		int filaAux = fila +1;
		int colum = columna;
		vAux[1] = 0;
		
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
			
			if(combinacion){
				for(int i = filaAux; i > fila; i--){
					tab.setCasilla(colum, i, ficha);
					vAux[1]++;
				}
			}
			if(vacio || !combinacion)
				ok = false;
				
		}
		
		return ok;
	}
	
	//Comprueba si hay combinaciones en una columna
	private boolean compruebaColumna(Tablero tab){
		boolean ok = false;
		boolean izq = mirarColumnaIzq(tab);
		boolean drc = mirarColumnaDrc(tab);
		
		if(izq || drc)
			ok = true;
		if(izq){
			combinaciones[contador] = colIzq;
			voltear[contador] = vAux[0];
			contador++;
		}
		if(drc){
			combinaciones[contador] = colDrc;
			voltear[contador] = vAux[1];
			contador++;
		}
		return ok;
	}
	
	//Comprueba si hay combinaciones en la parte izquierda de la columna
	private boolean mirarColumnaIzq(Tablero tab){
		boolean ok = true, combinacion = false, vacio = false;
		int colum = columna-1;
		vAux[0] = 0;
		
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
			if(combinacion){
				for(int i = colum; i < columna; i++){
					tab.setCasilla(i, fila, ficha);
					vAux[0]++;
				}
			}
			if(vacio || !combinacion) ok = false;	
		}
		return ok;
	}
	
	//Comprueba si hay combinaciones en la parte derecha de la columna
	private boolean mirarColumnaDrc(Tablero tab){
		boolean ok = true, combinacion = false, vacio = false;
		int colum = columna+1;
		vAux[1] = 0;
		
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
			if(combinacion){
				for(int i = colum; i > columna; i--){
					tab.setCasilla(i, fila, ficha);
					vAux[1]++;
				}
			}
			if(vacio || !combinacion) ok = false;	
		}
		
		return ok;
	}

	//Comprueba si hay combinaciones en las diagonales derechas
	private boolean compruebaDiagonalDerecha(Tablero tab){
		boolean ok = false;
		boolean arriba = miraDiagonalDerArriba(tab);
		boolean abajo = miraDiagonalDerAbajo(tab);
		
		if(arriba||abajo)
			ok = true;
		
		if(arriba){
			combinaciones[contador] = dDrcArriba;
			voltear[contador] = vAux[0];
			contador++;
		}
		if(abajo){
			combinaciones[contador] = dDrcAbajo;
			voltear[contador] = vAux[1];
			contador++;
		}
			
		return ok;
	}
	
	//Comprueba si hay combinaciones en las diagonales derechas superiores
	private boolean miraDiagonalDerArriba(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false, vuelta = false;
		int filaAux = fila-1;
		int columAux = columna+1;
		vAux[0] = 0;
		
		if(tab.getCasilla(columAux, filaAux) == Ficha.VACIA || tab.getCasilla(columAux, filaAux) == ficha || (filaAux < 1 && columAux > tab.getAncho()))
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
			if(combinacion){
				while(!vuelta) {
					if(columAux == columna && filaAux == fila) vuelta = true;
					else {
						tab.setCasilla(columAux, filaAux, ficha);
						columAux--;
						filaAux++;
						vAux[0]++;
					}
					
				}
			}
			
			if(vacio || !combinacion) ok = false;	
		}
		
		return ok;
	}
	
	//Comprueba si hay combinaciones en las diagonales inferiores
	private boolean miraDiagonalDerAbajo(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false, vuelta = false;
		int filaAux = fila+1;
		int columAux = columna+1;
		vAux[1]=0;
		
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
			if(combinacion){
				while(!vuelta) {
					if(columAux == columna && filaAux == fila) vuelta = true;
					else {
						tab.setCasilla(columAux, filaAux, ficha);
						columAux--;
						filaAux--;
						vAux[1]++;
					}
					
				}
			}
			
			if(vacio || !combinacion) ok = false;	
		}
		
		return ok;
	}
	
	//Comprueba si hay combinaciones en las diagonales izquierdas
	private boolean compruebaDiagonalIzquierda(Tablero tab){
		boolean ok = false;
		boolean arriba = miraDiagonalIzqArriba(tab);
		boolean abajo = miraDiagonalIzqAbajo(tab);
		
		if(arriba||abajo)
			ok = true;
		
		if(arriba){
			combinaciones[contador] = dIzqArriba;
			voltear[contador] = vAux[0];
			contador++;
		}
		if(abajo){
			combinaciones[contador] = dIzqAbajo;
			voltear[contador] = vAux[1];
			contador++;
		}
		return ok;
	}
	
	//Comprueba si hay combinaciones en las diagonales izquierdas superiores
	private boolean miraDiagonalIzqArriba(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false, vuelta = false;
		int filaAux = fila-1;
		int columAux = columna-1;
		vAux[0] = 0;
		
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
			if(combinacion){
				while(!vuelta) {
					if(columAux == columna && filaAux == fila) vuelta = true;
					else {
						tab.setCasilla(columAux, filaAux, ficha);
						columAux++;
						filaAux++;
						vAux[0]++;
					}
					
				}
			}
			
			if(vacio || !combinacion) ok = false;	
		}
		
		return ok;
	}
	
	//Comprueba si hay combinaciones en las diagonales izquierdas inferiores
	private boolean miraDiagonalIzqAbajo(Tablero tab) {
		boolean ok = true, combinacion = false, vacio = false, vuelta = false;
		int filaAux = fila+1;
		int columAux = columna-1;
		vAux[1]= 0;
		
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
			if(combinacion){
				while(!vuelta) {
					if(columAux == columna && filaAux == fila) vuelta = true;
					else {
						tab.setCasilla(columAux, filaAux, ficha);
						columAux++;
						filaAux--;
						vAux[1]++;
					}
					
				}
			}
			
			if(vacio || !combinacion) ok = false;	
		}
		
		return ok;
	}
	
	public void undo(Tablero tab) {
		int filaAux,columnaAux;
		
		//Recorro el array de combinaciones efectuadas para deshacer bien el movimiento
		for(int i = 0; i < contador; i++){
			switch(combinaciones[i]){
			
				//Fila arriba
				case fiArriba:{
					filaAux = fila-1;
					while(voltear[i] >0){
						tab.setCasilla(columna, filaAux, colorAnterior);
						filaAux--;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columna, filaAux+1, ficha);
					}
				}break;
				
				//Fila abajo
				case fiAbajo:{
					filaAux = fila+1;
					while(voltear[i] >0){
						tab.setCasilla(columna, filaAux, colorAnterior);
						filaAux++;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columna, filaAux-1, ficha);
					}
				}break;
				
				//Columna izquierda
				case colIzq:{
					columnaAux = columna-1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, fila, colorAnterior);
						columnaAux--;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux+1, fila, ficha);
					}
				}break;
				
				//Columna derecha
				case colDrc:{
					columnaAux = columna+1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, fila, colorAnterior);
						columnaAux++;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux-1, fila, ficha);
					}
				}break;
				
				//Diagonal derecha arriba
				case dDrcArriba:{
					filaAux= fila-1;
					columnaAux = columna+1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, filaAux, colorAnterior);
						columnaAux++;
						filaAux--;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux-1, filaAux+1, ficha);
					}
				}break;
				
				//Diagonal derecha abajo
				case dDrcAbajo:{
					filaAux= fila+1;
					columnaAux = columna+1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, filaAux, colorAnterior);
						columnaAux++;
						filaAux++;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux-1, filaAux-1, ficha);
					}
				}break;
				
				//Diagonal izquierda arriba
				case dIzqArriba:{
					filaAux= fila-1;
					columnaAux = columna-1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, filaAux, colorAnterior);
						columnaAux--;
						filaAux--;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux+1, filaAux+1, ficha);
					}
				}break;
				
				//Diagonal izquierda abajo
				case dIzqAbajo:{
					filaAux= fila+1;
					columnaAux = columna-1;
					while(voltear[i] >0){
						tab.setCasilla(columnaAux, filaAux, colorAnterior);
						columnaAux--;
						filaAux++;
						voltear[i] = voltear[i]-1;
						if(voltear[i] == 0)
							tab.setCasilla(columnaAux+1, filaAux-1, ficha);
					}
				}break;
			}
			//Pongo la posición donde fue ubicada la ficha vacia
			tab.setCasilla(columna, fila, Ficha.VACIA);
		}
	}

	public int devPos() {
		return columna;
	}
	
	public int devFila() {
		return fila;
	}
	
	public boolean devOk() {
		return movOk;
	}

}
