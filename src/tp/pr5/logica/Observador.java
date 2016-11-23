package tp.pr5.logica;
import tp.pr5.logica.*;

public interface Observador {
	void onReset(TableroInmutable tablero, Ficha turno);
	void onPartidaTerminada(TableroInmutable tablero, Ficha ganador);
	void onCambioJuego(TableroInmutable tablero, Ficha turno);
	void onUndoNotPossible(TableroInmutable tablero, Ficha turno);
	void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas);
	void onMovimientoEnd(TableroInmutable tablero,Ficha jugador, Ficha turno);
	void onMovimientoIncorrecto(MovimientoInvalido movimientoException);
	void onTableroInicial(TableroInmutable tablero, Ficha turno);
	void onIniciaHebra(Ficha turno);
	void onNumMovimientos(int movBlancas, int movNegras);
}