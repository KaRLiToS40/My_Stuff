package survivalshooter.shooter;

import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;

public class DisparoGuiado extends Disparo implements Animable {
	
	private static final double TIEMPO_EXPLOSION = 50;
	private static final double VEL_DISPARO = 15;
	
	private int pasoAnimacion = 1;
	private double radianesDeRotacion;
	private double tiempoInterno = System.currentTimeMillis();
	private Objetivo objetivo;

	public DisparoGuiado(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
	}

	public double getRadianesDeRotacion() {
		return radianesDeRotacion;
	}

	public void setRadianesDeRotacion(double radianesDeRotacion) {
		this.radianesDeRotacion = radianesDeRotacion;
	}

	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}

	public void apuntar() {
		radianesDeRotacion = Math.atan2(objetivo.getX() - x, objetivo.getY() - y);
		velY = Math.cos(radianesDeRotacion) * VEL_DISPARO;
		velX = Math.sin(radianesDeRotacion) * VEL_DISPARO;
	}

	@Override
	public void eliminarAlChocarConBorde(VentanaGrafica v) {
		if (v.getAnchura() < x || 0 > x || v.getAltura() < y || 0 > y) {
			eliminado = true;
		}
	}

	@Override
	public boolean animacionTerminada() {
		return pasoAnimacion > 5;
	}
	
	public boolean duracionCambioFase() {
		if(System.currentTimeMillis() >= tiempoInterno + TIEMPO_EXPLOSION) return true;
		else return false;
	}

	/**
	 * Calcula la distancia con otro objeto, considerando sus círculos de colisión
	 * 
	 * @param o2 Objeto con el que calcular la distancia
	 * @return distancia entre los círculos de colisión de los objetos. Si están
	 *         solapados, su distancia es negativa.
	 */
	@Override
	public double calcDistancia(ObjetoJuego o2) {
		double distCentroACentro = Math.sqrt((x - o2.x) * (x - o2.x) + (y - o2.y) * (y - o2.y));
		return distCentroACentro - tamanyo/2 - o2.tamanyo/2;
	}

	@Override
	public boolean chocaConObjetivo(ObjetoJuego o) {
		if (calcDistancia(o) <= 10) // RADIO DE COLISIÓN AJUSTADO
			return true; 
		else
			return false;
	}

	@Override
	public void chocaConObjetivos(ArrayList<ObjetoJuego> listaObjetos) {
		for (ObjetoJuego o : listaObjetos) {
			if (o instanceof Objetivo) {
				Objetivo obj = (Objetivo) o;
				if (this.chocaConObjetivo(obj) == true) {
					obj.setEliminado(true);
					obj.puntuar();
					this.setEliminado(true);
				}
			}
		}
	}

	@Override
	public void dibujar(VentanaGrafica v) {
		if(eliminado) {
			v.dibujaImagen("../imagenes/explosion" + pasoAnimacion +".png", x, y, tamanyo*pasoAnimacion, tamanyo*pasoAnimacion, 1, 0, 1f);
			if( duracionCambioFase() ) {
				pasoAnimacion++;
				tiempoInterno = System.currentTimeMillis();
			}
			if(animacionTerminada()) listoParaSerBorrado = true;
		}else {
			apuntar();
			x = x + velX;
			y = y + velY;
			v.dibujaImagen("../imagenes/shot.png", x, y, tamanyo, tamanyo, 1, Math.PI -radianesDeRotacion, 1f);			
		}
	}
}
