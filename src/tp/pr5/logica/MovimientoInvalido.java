package tp.pr5.logica;

@SuppressWarnings("serial")
public class MovimientoInvalido extends java.lang.Exception{
	
	public MovimientoInvalido(){
		
	}
	
	public MovimientoInvalido(String msg) {
		super(msg);
	}
	
	public MovimientoInvalido(String msg, java.lang.Throwable arg) {
		super(msg, arg);
	}
	
	public MovimientoInvalido(java.lang.Throwable arg) {
		super(arg);
	}
	
}
