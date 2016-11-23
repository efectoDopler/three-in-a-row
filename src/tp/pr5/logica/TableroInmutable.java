package tp.pr5.logica;

public interface TableroInmutable {
	int getFilas();
	int getColumnas();
	Ficha getCasilla(int fila, int col);
	void mostrar();
}

