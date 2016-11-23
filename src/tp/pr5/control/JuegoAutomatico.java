package tp.pr5.control;

import tp.pr5.logica.MovimientoInvalido;

public class JuegoAutomatico implements Runnable{
	private ControladorGUI c;
	
	public JuegoAutomatico(ControladorGUI c){
		this.c = c;
	}
	
	public void run() {
			try {
				Thread.sleep(1000);
				c.aleatorio();
			} catch (MovimientoInvalido | InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
	}
}


