package tp.pr5;

import tp.pr5.control.*;
import tp.pr5.logica.*;
import tp.pr5.vista.*;

import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class Main {
	final static int N = 10;
	private static FactoriaTipoJuego factoria;
	private static int movimientos = -1;
	
	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		boolean okConsole = false;
		boolean okGUI = false;
		CommandLineParser parser = null;
		CommandLine cmdLine = null;
		Options options = new Options();
		String error = null;
		
		if(args.length == 0){
			factoria = new FactoriaConecta4();
			okConsole = true;	
		}
		
		//Comando -u --ui
			options.addOption(OptionBuilder.withLongOpt("ui")
						  .withDescription("Tipo de interfaz (console, window). Por defecto, console.")
						  .hasArg()
						  .withArgName("tipo")
						  .create("u"));
		//Comando -g --game
		options.addOption(OptionBuilder.withLongOpt("game")
						  .withDescription("Tipo de juego (c4, co, gr, rv). Por defecto, c4")
						  .hasArg()
						  .withArgName("game")
						  .create("g"));
		//Comando -h --help
		options.addOption(OptionBuilder.withLongOpt("help")
				  .withDescription("Muestra esta ayuda.")
				  .create("h"));
		//Comando -x --tamX
		options.addOption(OptionBuilder.withLongOpt("tamX")
				  .withDescription("Número de columnas del tablero (sólo para\nGravity). Por defecto, 10.")
				  .hasArg()
				  .withArgName("columnNumber")
				  .create("x"));
		//Comando -y --tamX
		options.addOption(OptionBuilder.withLongOpt("tamY")
				  .withDescription("Número de filas del tablero (sólo para\nGravity). Por defecto, 10.")
				  .hasArg()
				  .withArgName("rowNumber")
				  .create("y"));
		//Comando -n --numMovs
				options.addOption(OptionBuilder.withLongOpt("numMovs")
						  .withDescription("Número de movimientos disponibles, si pones 0 es ilimitado.")
						  .hasArg()
						  .withArgName("num-movs")
						  .create("n"));
	
		try {
			parser = new BasicParser();
			cmdLine = parser.parse(options, args);
			String []fallos = cmdLine.getArgs();
			int valor = fallos.length;
			
			if(valor > 0){
				error = "Argumentos no entendidos: ";
				for(int i = 0; i < valor; i++){
					error = error + fallos[i];
					if(i+1 < valor) error = error + " ";
				}
				throw new Exception(error);
			}
			
			if(cmdLine.hasOption("h")){
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("tp.pr3.Main [-g <game>] [-h] [-x <columnNumber>] [-y <rowNumber>]", options);
				System.exit(0);
				okConsole = false;
				okGUI = false;
			}
			else if(cmdLine.hasOption("u")){
				String vista = cmdLine.getOptionValue("u");
				switch(vista){
				
					case "console": okConsole = jugar(error,cmdLine); break;
					
					case "window": okGUI = jugar(error,cmdLine);break;
					
					default:{ 
						error = "Comando '" + vista + "' incorrecto.";
						throw new Exception(error);
					}
				}
			}
			else if(cmdLine.hasOption("g")){
				okConsole = jugar(error,cmdLine);
			}
			
		} catch (Exception e) {
			String fallo = e.getMessage();
			System.err.print("Uso incorrecto: ");
			System.err.println(fallo);
			System.err.println("Use -h|--help para más detalles.");
			System.exit(1);
			okConsole = false;
			okGUI = false;
		}
		
		ReglasJuego reglas = factoria.creaReglas(movimientos);
		Partida partida = new Partida(reglas);
		
		if(okConsole){
			ControladorConsola controladorC = new ControladorConsola(factoria,partida,in,movimientos);
			VistaConsola vistaC = new VistaConsola(controladorC);		
			vistaC.run();
		}
		
		else if(okGUI){
			ControladorGUI controladorGUI = new ControladorGUI (factoria,partida,movimientos);
			VistaGUI vistaG = new VistaGUI(controladorGUI);
			vistaG.setVisible(true);
		}
	}
	
	private static boolean jugar(String msj,CommandLine cmdLine) throws Exception{
		boolean ok = false;
		
		if(cmdLine.hasOption("g")){
			String juegoCom = cmdLine.getOptionValue("g");
			String movs = cmdLine.getOptionValue("n");
			if(movs != null && !movs.equals("0"))
				movimientos = Integer.parseInt(movs);
			
			switch(juegoCom){
				case "gr":{
					String colum = cmdLine.getOptionValue("x");
					String fila = cmdLine.getOptionValue("y");
					if(colum == null && fila == null){
						factoria = new FactoriaGravity(N,N);
						ok = true;		
					}
					
					else{
						int c = Integer.parseInt(colum);
						int f = Integer.parseInt(fila);
						if(c > 0 && f > 0) factoria = new FactoriaGravity(c,f);
						else factoria = new FactoriaGravity(N,N);
						ok = true;	
					}
				}break;
				
				case "c4":{
					factoria = new FactoriaConecta4();
					ok = true;	
				}break;
				
				case "co":{
					factoria = new FactoriaComplica();
					ok = true;	
				}break;
				
				case "rv":{
					factoria = new FactoriaReversi();
					ok = true;	
				}break;
				
				default:{
					msj = "Juego '" + juegoCom + "' incorrecto.";
					throw new Exception(msj);
				}
			}
		}
		else{
			factoria = new FactoriaConecta4();
			ok = true;	
		}
		return ok;
	}
}
