package tp.pr5.logica;

public class MovimientoConecta4 extends Movimiento{
	private int posicion;
	private Ficha ficha;
	private boolean movOk;
	
	public MovimientoConecta4(int donde, Ficha color) {
		posicion = donde;
		ficha = color;
	}

	public Ficha getJugador() {
		return ficha;
	}

	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean ok = true;
		int altura = 1;
		
		if((posicion < 1)||(posicion > tab.getAncho())) 
			ok = false;	
		
		movOk = ok;
		if(!ok) throw new MovimientoInvalido("Columna incorrecta. Debe estar entre 1 y " + tab.getColumnas()+".");
		else{
			//Si es la primera ficha que introduces en la columna mira esto
			if(tab.getCasilla(posicion,tab.getAlto()) == Ficha.VACIA) 
				tab.setCasilla(posicion,tab.getAlto(),ficha);	
			
			//Si no es la primera feche que introduces en la columna mira esto
			else {	
				//Recorre la columna buscando la primera posicion vacia
				do{
					if(tab.getCasilla(posicion,altura+1)== Ficha.VACIA) ++altura;
				}while(tab.getCasilla(posicion,altura+1) == Ficha.VACIA);	
				//Si la columna no esta llena se ejecuta el movimiento
				if(tab.getCasilla(posicion,1) == Ficha.VACIA) tab.setCasilla(posicion,altura,ficha);
				else throw new MovimientoInvalido("Columna llena.");
			}
		}
	}

	public void undo(Tablero tab) {
		int inicio = 1;
		while(tab.getCasilla(posicion,inicio) == Ficha.VACIA)
			inicio++;
		tab.setCasilla(posicion, inicio, Ficha.VACIA);
	}
	
	//devolvemos la ultima columna donde hemos puesto la ficha
	public int devPos(){
		return posicion;
	}
	
	public int devFila() {
		return 0;
	}

	@Override
	public boolean devOk() {
		return movOk;
	}
}




