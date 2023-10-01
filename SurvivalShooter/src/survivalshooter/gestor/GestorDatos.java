package survivalshooter.gestor;

import java.io.*;
import java.util.*;

import javax.swing.JTextArea;

import survivalshooter.ventanas.VentanaFinal;

public class GestorDatos {
	private static String nombreFichero = "scoresUsuarios.txt";
	private Usuario usr;
	private static TreeSet<Usuario> lecturaUsuarios = new TreeSet<>();
	
	public GestorDatos(Usuario usr){
	 this.usr = usr;
	}
	
	public GestorDatos() {
		usr = new Usuario();
	}
	
	public static TreeSet<Usuario> getLecturaUsuarios() {
		return lecturaUsuarios;
	}

	public static void setLecturaUsuarios(TreeSet<Usuario> lecturaUsuarios) {
		GestorDatos.lecturaUsuarios = lecturaUsuarios;
	}

	public void writeScores() {
		try (PrintStream ps = new PrintStream(new FileOutputStream(nombreFichero, true))){
			ps.println(usr.usuarioALinea());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void readScores(JTextArea txtArea) { 
		try (Scanner sc = new Scanner(new File(nombreFichero))){
			txtArea.setText("USUARIOS\tSCORE\tASTEROIDES\tESTRELLAS\tESCUDOS\tTIEMPO\n");
			while (sc.hasNextLine()) {
				System.out.println("HOLA");
				String[] linea = sc.nextLine().split("\t");
				lecturaUsuarios.add(traducirLineAUsuario(linea));
			}
			for (Usuario usr : lecturaUsuarios) {
				try {
					if (VentanaFinal.isHiloStop()) break;
					Thread.sleep(75);
					txtArea.append(usr.toString() + "\n");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			txtArea.setText("El archivo está vacío");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			txtArea.setText("El archivo no fue encontrado");
		}catch (IllegalFormatException e) {
			e.printStackTrace();
			txtArea.setText("El archivo tiene algún defecto en su estructura");
		}catch(@SuppressWarnings("hiding") IOException e) {
			e.printStackTrace();
		}
	}
	
	public void leerFicheroRaw(JTextArea txtArea, String fichero) {
		try (Scanner sc = new Scanner(new File(fichero))){
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				txtArea.append(linea +"\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			txtArea.setText("El archivo no fue encontrado");
		}catch (IllegalFormatException e) {
			e.printStackTrace();
			txtArea.setText("El archivo tiene algún defecto en su estructura");
		}catch(@SuppressWarnings("hiding") IOException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario traducirLineAUsuario(String[] linea) throws IllegalFormatException{
		return new Usuario(linea[0], Integer.parseInt(linea[1]), Integer.parseInt(linea[2]), Integer.parseInt(linea[3]), Integer.parseInt(linea[4]), Double.parseDouble(linea[5]));
	}
}
