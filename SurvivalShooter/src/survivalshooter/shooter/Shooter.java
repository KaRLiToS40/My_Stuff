package survivalshooter.shooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import survivalshooter.gestor.GestorDatos;
import survivalshooter.gestor.Usuario;
import survivalshooter.ventanas.VentanaFinal;
import survivalshooter.ventanas.VentanaGrafica;
import survivalshooter.ventanas.VentanaUsuarios;

public class Shooter {
	private static VentanaGrafica ventana;
	private static VentanaFinal ventanaFinal ;
	private static GestorDatos gestor;
	
	private static final int tamanyo = 40;
	private static final int X_INICIAL = 500;
	private static final int Y_INICIAL = 250;
	private static final int ESPERA_ENTRE_FRAMES = 25;
	private static final double SPAWN_ASTEROIDES = 1500;
	private static final double SPAWN_ESTRELLAS = 7000;
	private static final double SPAWN_ESCUDOS = 1000;
	private static final int MARGEN_DE_APUNTADO = 80;

	private static final Font TEXTO_INICIAL = new Font("Arial", Font.BOLD, 24);

	private static ObjetosEspacio mundo = new ObjetosEspacio();
	private static ArrayList<ObjetoJuego> listaObjetos = new ArrayList<>();
	private static Point2D.Double coordsNave = new Point2D.Double();
	
	public static int asteroidesEliminados;
	public static int estrellasEliminadas;
	public static int escudosAdquiridos;
	public static int score;
	public static double tiempoDeJuego;

	private static double contadorAsteroides;
	private static double contadorEstrellas;
	private static double contadorEscudos;
	
	public static Nave nave = new Nave(X_INICIAL, Y_INICIAL, tamanyo);
	
	public static void main(String[] args) {
		VentanaUsuarios v = new VentanaUsuarios();
		v.setVisible(true);
	}

	public static void shooterGame() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		inicio();
		while (!ventana.estaCerrada() && !ventana.isTeclaPulsada(KeyEvent.VK_ESCAPE) && nave.getVidasNave() > 0) {
			updateJuego();
			chequearObjetosEliminados();

			mundo.dibujar(ventana);
			borrarObjetosEliminados();
			ventana.espera(ESPERA_ENTRE_FRAMES);
		}
		actualizarPuntuaciones();
		endGame();
	}

	/**
	 * Sistema de inicio del juego
	 */
	private static void inicio() {
		ventana = new VentanaGrafica(1000, 500, "Survival Shooter");
		ventana.getJFrame().setAlwaysOnTop(true);
		tiempoDeJuego = System.currentTimeMillis();
		nave.setVidasNave(5);
		ventana.setColorFondo(Color.BLACK);
		boolean esperar = true;
		ventana.dibujaTextoCentrado(100, ventana.getAltura() / 2 - 75, 800, 100,"Pulsa con el ratón para comenzar la batalla. Los controles son: ", TEXTO_INICIAL, Color.BLACK);
		ventana.dibujaTextoCentrado(90, ventana.getAltura() / 2 - 50, 800, 100,"AWSD -- Movimiento, Click Izq -- Disparo normal, Click + Space -- Disparo Guiado", TEXTO_INICIAL, Color.BLACK);
		while (esperar && !ventana.estaCerrada()) {
			if (ventana.getRatonClicado() != null) {
				esperar = false;
			}
		}
		if(VentanaUsuarios.isJugarConFondo()) mundo.add(new FondoPantalla("../imagenes/universe.jpg",1000, 500, 1100, 600, 1.0, 0, 1.0f));
		mundo.add(nave);
		
		contadorAsteroides = System.currentTimeMillis();
		contadorEstrellas = System.currentTimeMillis();
		contadorEscudos = System.currentTimeMillis();
	}
	
	/**
	 * Termina el juego y saca los datos nuevos a fichero
	 */
	private static void endGame() {
		ventana.dibujaTextoCentrado(0, 150, 1000, 200, "GAME OVER", TEXTO_INICIAL, Color.WHITE);
		ventana.espera(2000);
		ventana.acaba();
		gestor = new GestorDatos(new Usuario());
		gestor.writeScores();
		ventanaFinal = new VentanaFinal();
		ventanaFinal.setVisible(true);
		mundo.getlObjetos().clear();
	}

	/**Este método selecciona el objetivo que se marca con el ratón
	 * esto se emplea para la clase Disparo Guiado
	 * @param p Punto {@link Point} del click
	 * @return {@link Objetivo} seleccionado
	 */
	public static Objetivo objetivoSeleccionado(Point p) {
		for (ObjetoJuego o : listaObjetos) {
			if (o instanceof Objetivo && p != null) {
				double dist = Math.sqrt((p.getX() - o.x) * (p.getX() - o.x) + (p.getY() - o.y) * (p.getY() - o.y));
				if (dist < MARGEN_DE_APUNTADO) { //El margen hace que sea más facil seleccionar objetivos
					return (Objetivo) o;
				}
			}
		}
		return null;
	}

	/**
	 * Actualiza la nave e introduce nuevos objetos
	 */
	public static void updateJuego() {
		//ACTUALIZAR LA NAVE
		coordsNave = new Point2D.Double(nave.getX(), nave.getY());
		Estrella.setCoordsNave(coordsNave);
		
		//DIASPAROS DE LA NAVE
		if (ventana.isTeclaPulsada(KeyEvent.VK_SPACE) ) {
			nave.disparoEspecial(mundo, objetivoSeleccionado(ventana.getRatonClicado()));
		} else if (ventana.getRatonClicado() != null) {
			nave.disparoNormal(mundo);
		}
		
		// INTRODUCIR ASTEROIDES
		if (System.currentTimeMillis() >= contadorAsteroides + SPAWN_ASTEROIDES) {
			mundo.add(new Asteroide(ventana));
			contadorAsteroides = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() >= contadorEstrellas + SPAWN_ESTRELLAS) {
			Estrella.setCoordsNave(coordsNave);
			mundo.add(new Estrella(ventana));
			contadorEstrellas = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() >= contadorEscudos + SPAWN_ESCUDOS) {
			mundo.add(new Escudo(ventana));
			contadorEscudos = System.currentTimeMillis();
		}
	}

	/**
	 * Este método actualiza el estado de los objetos, todos tienen un estado de {@link #eliminado} por
	 * lo que no aparecen en la pantalla, pero siguen estando en las estructuras de datos para
	 * realizar las animaciones, por lo que puede que no sean visibles.
	 * 
	 * Solo los objetos {@link Eliminador} pueden eliminar objetos, y solo los objetos {@link Rebotable}
	 * pueden rebotar y ser eliminados por los eliminadores (Nave y disparos)
	 */
	public static void chequearObjetosEliminados() {
		listaObjetos = new ArrayList<>(mundo.getlObjetos());
		for (ObjetoJuego o : listaObjetos) {
			if (o instanceof Eliminador) {
				Eliminador el = (Eliminador) o;
				el.chocaConObjetivos(listaObjetos);
			}
		}
	}

	/**
	 * Este método es el que elimina de las estructuras de datos a todos los objetos que estén
	 * {@link #listoParaSerBorrado} una vez estos lo consideren oportuno.
	 */
	public static void borrarObjetosEliminados() {
		listaObjetos = new ArrayList<>(mundo.getlObjetos());
		for (ObjetoJuego o : listaObjetos) {
			if (o.isListoParaSerBorrado()) {
				mundo.remove(o);
			}
		}
	}
	
	public static void actualizarPuntuaciones() {
		tiempoDeJuego =  System.currentTimeMillis() - tiempoDeJuego;
		score = asteroidesEliminados * Asteroide.PUNTUACION + estrellasEliminadas * Estrella.PUNTUACION;
	}
}