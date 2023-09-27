package survivalshooter.shooter;

import java.awt.geom.Point2D;
import java.util.Random;

import survivalshooter.ventanas.VentanaGrafica;

public class Estrella extends Objetivo implements Rebotable {
	
	private static final long serialVersionUID = 1L;
	private static final int VEL_ESTRELLA = 15;
	public static final int TAMANYO= 40;
	public static final double VEL_ROT_MAX = 0.3;
	public static final int PUNTUACION = 100;
	private static Point2D.Double coordsNave;
	
	private Random rnd = new Random();
	private double tiempoInterno;
	private double disipacion = 1;
	
	public Estrella(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
		velRotacionRad = rnd.nextDouble()*(VEL_ROT_MAX/2)+ (VEL_ROT_MAX/2);
		anguloActual = 0;
		velX = rnd.nextDouble()*VEL_ESTRELLA;
		velY = rnd.nextDouble()*VEL_ESTRELLA;
	}

	public Estrella(VentanaGrafica v) {
		super(v);
		velX = rnd.nextDouble()*VEL_ESTRELLA;
		velY = rnd.nextDouble()*VEL_ESTRELLA;
		x = rnd.nextInt(v.getAnchura());
		y = rnd.nextInt(v.getAltura());
		tamanyo = TAMANYO;
		velRotacionRad = rnd.nextDouble()*(VEL_ROT_MAX/2)+ (VEL_ROT_MAX/2);
		anguloActual = 0;
	}

	public static Point2D.Double getCoordsNave() {
		return coordsNave;
	}

	public static void setCoordsNave(Point2D.Double coordsNave) {
		Estrella.coordsNave = coordsNave;
	}

	@Override
	public boolean animacionTerminada() {
		return System.currentTimeMillis() >= tiempoInterno + 500;
	}

	@Override
	public void setVelocidadAlRebotar(VentanaGrafica v) {
		double anguloApuntado = Math.atan2(coordsNave.getY()-y, coordsNave.getX()-x);
		velX = Math.cos(anguloApuntado) *  VEL_ESTRELLA;
		velY = Math.sin(anguloApuntado) * VEL_ESTRELLA;
	}

	@Override
	public boolean chocaConBorde(VentanaGrafica v) {
		if (v.getAnchura() < x || 0 > x || v.getAltura() < y || 0 > y) return true;
		else return false;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		if(eliminado == false) {
			if (chocaConBorde(v)) setVelocidadAlRebotar(v);
			x = x + velX;
			y = y + velY;
			anguloActual = anguloActual + velRotacionRad;
			v.dibujaImagen("../imagenes/star.png", x, y, tamanyo, tamanyo, disipacion, anguloActual, 1);
			tiempoInterno = System.currentTimeMillis();
		} else {
			disipacion = disipacion -0.3;
			v.dibujaImagen("../imagenes/stardust.png", x, y, tamanyo*2, tamanyo*2, disipacion, anguloActual, 1);
			if(animacionTerminada()) listoParaSerBorrado = true;
		}
	}
	
	@Override
	public void puntuar() {
		Shooter.estrellasEliminadas++;
	}
}
