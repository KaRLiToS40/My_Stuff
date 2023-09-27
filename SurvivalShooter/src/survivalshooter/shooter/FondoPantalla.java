package survivalshooter.shooter;

import survivalshooter.ventanas.VentanaGrafica;

public class FondoPantalla extends ObjetoJuego {
	private String imagen;
	private int tamanyo2;
	private double zoom;
	private double rotation;
	private float opacity;
	private double tiempoCreacion = System.currentTimeMillis();
	
	/**Método apto para imagenes rectangulares
	 * @param x centro de la imagen en el eje x
	 * @param y centro de la imagen en el eje y
	 * @param tamanyo pixeles del ancho del rectangulo
	 * @param tamanyo pixeles del alto del rectángulo
	 */
	public FondoPantalla(String imagen, double x, double y, int tamanyo, int tamanyo2, double zoom, double rotation, float opacity) {
		super(x, y, tamanyo);
		this.imagen = imagen;
		this.tamanyo2 = tamanyo2;
		this.zoom = zoom;
		this.rotation = rotation;
		this.opacity = opacity;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public double getTiempoCreacion() {
		return tiempoCreacion;
	}

	public void setTiempoCreacion(double tiempoCreacion) {
		this.tiempoCreacion = tiempoCreacion;
	}

	@Override
	public void dibujar(VentanaGrafica v) {
			v.dibujaImagen(imagen,x/2 , y/2, tamanyo, tamanyo2, zoom, rotation, opacity);
	}
}
