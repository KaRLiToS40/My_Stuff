package survivalshooter.shooter;

import java.util.Random;

import survivalshooter.ventanas.VentanaGrafica;

public class Asteroide extends Objetivo implements Rebotable{
	
	private static final long serialVersionUID = 1L;
	public static final int VARIACION_MAX_TAMANYO = 50;
	public static final int TAMANYO_MIN = 30;
	public static final double VEL_ROT_MAX = 0.1;
	public static final int PUNTUACION = 100;
	
	private double disipacion = 1;
	private double tiempoInterno;
	
	public Asteroide(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
		inicializarValores();
	}

	public Asteroide(VentanaGrafica v) {
		super(v);
		x = new Random().nextInt(v.getAnchura());
		y = new Random().nextInt(v.getAltura());
		tamanyo = new Random().nextInt(VARIACION_MAX_TAMANYO) + TAMANYO_MIN;
		inicializarValores();
	}
	
	public void inicializarValores() { 
		velX = new Random().nextInt(25)-10;
		velY = new Random().nextInt(25)-10;
		velRotacionRad = new Random().nextDouble()*(VEL_ROT_MAX/2)+ (VEL_ROT_MAX/2);
		anguloActual = 0;
	}


	@Override
	public void setVelocidadAlRebotar(VentanaGrafica v) {
		if (v.getAnchura() < x || 0 > x) {
			velX = -velX;
		} if (v.getAltura() < y || 0 > y) {
			velY = -velY;
		}
	}
	
	@Override
	public boolean chocaConBorde(VentanaGrafica v) {
		if(v.getAnchura() < x || 0 > x || v.getAltura() < y || 0 > y) return true;
		else return false;
	}

	@Override
	public boolean animacionTerminada() {
		return System.currentTimeMillis() >= tiempoInterno + 1000;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		if(eliminado == false) {
			x = x +velX;
			y = y + velY;
			anguloActual = anguloActual + velRotacionRad;
			v.dibujaImagen("../imagenes/asteroid.png", x, y, tamanyo, tamanyo, 1, anguloActual, 1);
			tiempoInterno = System.currentTimeMillis();
		} else {
			disipacion = disipacion -0.1;
			v.dibujaImagen("../imagenes/asteroid-roto.png", x, y, tamanyo*2, tamanyo*2, disipacion, anguloActual, 1);
			if(animacionTerminada()) listoParaSerBorrado = true;
		}
	}

	@Override
	public void puntuar() {
		Shooter.asteroidesEliminados++;
	}
}
