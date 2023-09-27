package survivalshooter.shooter;

import java.util.Random;

import survivalshooter.ventanas.VentanaGrafica;

public class Escudo extends ObjetoJuego {
	private Random rnd = new Random();
	private static final int TAMANYO = 30;
	private static final double TIEPMO_DE_VIDA = 2000;
	private double tiempoInterno = System.currentTimeMillis();

	public Escudo(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
	}

	public Escudo(VentanaGrafica v) {
		super(v);
		x = rnd.nextDouble()*v.getAnchura();
		y = rnd.nextDouble()*v.getAltura();
	}

	public boolean suicidarse() {
		if (System.currentTimeMillis() >= tiempoInterno + TIEPMO_DE_VIDA) return true;
		else return false;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		if( !suicidarse() && eliminado == false) {
			v.dibujaImagen("../imagenes/escudo.png", x, y, TAMANYO, TAMANYO, 1, 0, 1);
		}else {
			listoParaSerBorrado = true;
		}
	}
}
