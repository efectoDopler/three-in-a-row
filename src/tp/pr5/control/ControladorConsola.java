package tp.pr5.control;
import tp.pr5.logica.*;

import java.util.Scanner;

public class ControladorConsola {
	private FactoriaTipoJuego factoria;
	private ReglasJuego reglas;
	private Partida partida;
	private Scanner in;
	private Jugador jugador1;
	private Jugador jugador2;
	
	public ControladorConsola(FactoriaTipoJuego f, Partida p, java.util.Scanner in,int movimientos){
		factoria = f;
		reglas = f.creaReglas(movimientos);
		partida = p;
		this.in = in;
		jugador1 = factoria.creaJugadorHumanoConsola(in);
		jugador2 = factoria.creaJugadorHumanoConsola(in);
	}
	
	public void tableroInicial(){
		partida.tableroInicial();
	}
	
	//Muestra el texto de ayuda al pedir el comando correspondiente
	public void mostrarAyuda(){
		System.out.println("Los comandos disponibles son:");
		System.out.println("");
		System.out.println("PONER: utilízalo para poner la siguiente ficha.");
		System.out.println("DESHACER: deshace el último movimiento hecho en la partida.");
		System.out.println("REINICIAR: reinicia la partida.");
		System.out.println("JUGAR [c4|co|gr|rv] [tamX tamY]: cambia el tipo de juego.");
		System.out.println("JUGADOR [blancas|negras] [humano|aleatorio]: cambia el tipo de jugador.");
		System.out.println("SALIR: termina la aplicación.");
		System.out.println("AYUDA: muestra esta ayuda.");
		System.out.println("PASA: Pasa el turno al siguiente jugador.");
		System.out.println("");
	}
	
	public void pasar(){
		partida.pasarTurno();
	}
	//Deshace un movimiento del tablero al pedir el comando correspondiente
	public void deshacer() throws MovimientoInvalido{
		partida.undo(); 
	}
	
	//Reinicia una partida al pedir el comando correspondiente
	public void reiniciar(){
		partida.reiniciar();
		jugador1 = factoria.creaJugadorHumanoConsola(in);
		jugador2 = factoria.creaJugadorHumanoConsola(in);
	}
	
	//Cambia el tipo de juego
	public void cambiarJuego(String jug){
		String [] juego = jug.split((" "));
		String movs;
		int movimientos = -1;
		boolean ok = true;
		if(juego.length > 1){
			switch(juego[1]){
				case "c4":{
					factoria = new FactoriaConecta4();
					jugador1 = factoria.creaJugadorHumanoConsola(in);
					jugador2 = factoria.creaJugadorHumanoConsola(in);
					if(juego.length > 2){
						movs = juego[2];
						if(!movs.equals("0"))
							movimientos = Integer.parseInt(movs);
					}
				}break;
					
				case "co":{
					factoria = new FactoriaComplica();
					jugador1 = factoria.creaJugadorHumanoConsola(in);
					jugador2 = factoria.creaJugadorHumanoConsola(in);
					if(juego.length > 2){
						movs = juego[2];
						if(!movs.equals("0"))
							movimientos = Integer.parseInt(movs);
					}
				}break;
							
				case "gr":{
					if(juego.length > 3){
						int x = Integer.parseInt(juego[2]);
						int y = Integer.parseInt(juego[3]);
						if(x > 0 && y > 0) 
							factoria = new FactoriaGravity(x,y);
						else 
							factoria = new FactoriaGravity(1,1);
						jugador1 = factoria.creaJugadorHumanoConsola(in);
						jugador2 = factoria.creaJugadorHumanoConsola(in);
						if(juego.length > 4){
							movs = juego[4];
							if(!movs.equals("0"))
								movimientos = Integer.parseInt(movs);
						}
					}
					else{
						System.err.println("No te entiendo.");
						partida.tableroInicial();
						ok = false;
					}
				}break; 
				
				case "rv":{
					factoria = new FactoriaReversi();
					jugador1 = factoria.creaJugadorHumanoConsola(in);
					jugador2 = factoria.creaJugadorHumanoConsola(in);
					if(juego.length > 2){
						movs = juego[2];
						if(!movs.equals("0"))
							movimientos = Integer.parseInt(movs);
					}
				}break;
				
				default: {
					System.err.println("No te entiendo.");
					partida.tableroInicial();
					ok = false;
				}break;
			}
		}
		else{
			System.err.println("No te entiendo.");
			partida.tableroInicial();
			ok = false;
		}
		
		if(ok){
			reglas = factoria.creaReglas(movimientos);
			partida.reset(reglas);
			jugador1 = factoria.creaJugadorHumanoConsola(in);
			jugador2 = factoria.creaJugadorHumanoConsola(in);
			partida.cambiarJuego();
		}
	}
	
	//Alterna entre jugador humano/aleatorio para un tipo de ficha al pedir el comando correspondiente
	public void tipoJugador(String texto){
		//Si el string no viene vacio se mete aquí
		if ((texto != null) && (!texto.equals("")) ) {
			String [] jugador = texto.split((" "));
			String tipoJugador,colorFicha;
			colorFicha = jugador[1];
			if(jugador.length > 2){
				tipoJugador = jugador[2];
			switch(colorFicha){
				case "blancas": {
					if(tipoJugador.equals("aleatorio"))
						jugador1 = factoria.creaJugadorAleatorio();
					
					else if(tipoJugador.equals("humano"))
						jugador1 = factoria.creaJugadorHumanoConsola(in);
	
					else 
						System.err.println("No te entiendo.");	
				} break;
				
				case "negras":{
					if(tipoJugador.equals("aleatorio"))
						jugador2 = factoria.creaJugadorAleatorio();
	
					else if(tipoJugador.equals("humano"))
						jugador2 = factoria.creaJugadorHumanoConsola(in);
	
					else 
						System.err.println("No te entiendo.");	
				} break;
				
				default: System.err.println("No te entiendo.");
				}
			}
			else
				System.err.println("No te entiendo.");
		}
		//Si el string texto no tiene nada es que solo se ha escrito "jugador" desde la vista
		else
			System.err.println("No te entiendo.");

	}

	//Realiza un movimiento
	public void poner() throws MovimientoInvalido{
		Movimiento mov;
		
		if(partida.getTurno().equals(Ficha.BLANCA))
			mov = partida.creaMovimiento(jugador1);
		else
			mov = partida.creaMovimiento(jugador2);
			
		partida.ejecutaMovimiento(mov);
	}
	
	
	//Añade un observador a partida
	public void addObservador(Observador v) {
		partida.addObservador(v);
	}
	
}

