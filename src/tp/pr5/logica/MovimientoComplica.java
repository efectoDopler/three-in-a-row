package tp.pr5.logica;

public class MovimientoComplica extends Movimiento{
	
	private int posicion;
	private Ficha ficha;
	private Ficha [] movidas;
	private int numMovidas;
	private boolean movOk;
	
	public MovimientoComplica(int donde, Ficha color) {
		posicion = donde;
		ficha = color;
		movidas = new Ficha [3];
		numMovidas = 0;
	}

	public Ficha getJugador() {
		return ficha;
	}

	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean ok = true;
		movOk = true;
		int altura = 1;
		
		//Comprobamos si la posicion es valida para ejecutar movimiento
		if((posicion < 1)||(posicion > tab.getAncho()))
			ok = false;
			
		movOk = ok;
			
		if(!ok) 
			throw new MovimientoInvalido("Columna incorrecta. Debe estar entre 1 y " + tab.getColumnas()+".");
			
		//Si es la primera ficha que introduces en la columna mira esto
		if(tab.getCasilla(posicion,tab.getAlto()) == Ficha.VACIA)
			tab.setCasilla(posicion,tab.getAlto(),ficha);
			
		//Si no es la primera feche que introduces en la columna mira esto
		else {
			//Recorre la columna buscando la primera posicion vacia
			do{
				if(tab.getCasilla(posicion,altura+1)== Ficha.VACIA)
					++altura;
			}while(tab.getCasilla(posicion,altura+1) == Ficha.VACIA);
				
			//Si la columna no esta llena se ejecuta el movimiento
			if(tab.getCasilla(posicion,1) == Ficha.VACIA)
				tab.setCasilla(posicion,altura,ficha);
				
			//Si la columna esta llena desplaza todo hacia abajo y hace hueco arriba
			else{
					
				if(numMovidas <= 9) {
					movidas[numMovidas] = tab.getCasilla(posicion,tab.getAlto());
					numMovidas++;
				}
				else if(numMovidas > 9){
					for(int j = 0; j < 1; j++) {
						movidas[j] = movidas[j+1];
					}
					movidas[numMovidas] = tab.getCasilla(posicion, tab.getAlto());
				}
					
				//Desplazo toda la columna hacia abajo y hago hueco
				for(int i = tab.getAlto();i > 1;--i){
					Ficha aux = tab.getCasilla(posicion, i-1);
					tab.setCasilla(posicion, i, aux);
				}
					
				//Meto en el hueco la ficha
				tab.setCasilla(posicion, 1, ficha);
			}
		}
	}

	public void undo(Tablero tab) {
		int inicio = 1;
		Ficha Aux;
		
		/*Como en tablero le restabamos 1 en alto y ancho siempre para 
		  evitar los problemas con el JUNIT inicio tablero a 1 y sumo 1 
		  a posicion
		 */
		while(tab.getCasilla(posicion,inicio) == Ficha.VACIA)
			inicio++;
		
		tab.setCasilla(posicion, inicio, Ficha.VACIA);
		
		if(numMovidas > 0) {
			for(int alt = 1; alt < tab.getAlto(); alt++) {
				Aux = tab.getCasilla(posicion, alt+1);
				tab.setCasilla(posicion, alt, Aux);
			}
			Aux = movidas[numMovidas-1];
			tab.setCasilla(posicion, tab.getAlto(), Aux);
			numMovidas--;
		}
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








