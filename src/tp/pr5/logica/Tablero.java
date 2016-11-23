package tp.pr5.logica;

public class Tablero implements TableroInmutable{
	private int ancho;
	private int alto;
	private Ficha[][] tablero;
	
	public Tablero(int tx,int ty) {
			
		if(tx < 1 || ty < 1) { 
			ancho = 1;
			alto = 1; 
		}	
		else {
			ancho = tx;
			alto = ty; 
		}	
		tablero = new Ficha[ancho][alto];
		for(int i = 1; i <= alto; ++i){
			for(int j = 1; j <= ancho; ++j)
				tablero[j-1][i-1] = Ficha.VACIA;
		}
	}

	public int getAlto(){
		return this.alto;
	}

	public int getAncho(){
		return this.ancho;
	}
	
	public Ficha getCasilla(int x,int y){
		Ficha fichaAux;
		if(((this.alto < y)|| (y < 1))||((this.ancho < x) || (x < 1)))
			fichaAux = Ficha.VACIA;	
		else
			fichaAux = this.tablero[x-1][y-1];
		
		return fichaAux;
	}
	
	public void setCasilla(int x, int y,Ficha color){
		if((x>=1 && x<= getAncho()) && (y>=1 && y<= getAlto()))
				tablero[x-1][y-1] = color;
	}
	
	public void mostrar(){
		int contador = 1;
		
		for(int i = 1; i<= alto; ++i){
			System.out.print("|");
			for(int j = 1; j<= ancho; ++j){
				if(tablero[j-1][i-1] == Ficha.VACIA)
					System.out.print(" ");
				else if(tablero[j-1][i-1] == Ficha.BLANCA)
					System.out.print("O");
				else if(tablero[j-1][i-1] == Ficha.NEGRA)
					System.out.print("X");
			}
			System.out.println("|");
		}
		System.out.print("+");
		for(int aux = 0; aux < this.ancho; ++aux) {
			System.out.print("-");
		}
		System.out.println("+");
		for(int aux = 0; aux < this.ancho+2; ++aux) {
			if((aux == 0) || (aux == this.ancho+1)) System.out.print(" ");
			else {
				if(contador > 9) contador = 0;
				System.out.print(contador);
				contador++;
			}
		}
		System.out.println();
		System.out.println();
	}

	@Override
	public int getFilas() {
		return alto;
	}

	@Override
	public int getColumnas() {
		return ancho;
	}
}
