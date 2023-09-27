package survivalshooter.shooter;

import survivalshooter.ventanas.VentanaGrafica;

public abstract class ObjetoJuego {
	protected static VentanaGrafica ventana;
	
	protected double x;           // Coordenada x del centro del objeto
	protected double y;           // Coordenada y
	protected int tamanyo;        // Píxeles de tamaño del objeto (sobre base cuadrada: tamaño = altura = anchura)
	protected boolean listoParaSerBorrado = false;
	protected boolean eliminado = false;
	
	/**Crea un objeto del juego
	 * @param x	Coordenada x del objeto respecto a la ventana gráfica
	 * @param y	Coordenada y del objeto respecto a la ventana gráfica
	 * @param tamanyo		Tamaño del objeto
	 */
	public ObjetoJuego(double x, double y, int tamanyo) {
		this.x = x;
		this.y = y;
		this.tamanyo = tamanyo;
	}

	/**Crea un ObjetoJuego a partir de una ventana, las coordenadas por defecto son
	 * x = 0 e y = 0
	 * @param v
	 */
	public ObjetoJuego(VentanaGrafica v) {
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	
	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public boolean isListoParaSerBorrado() {
		return listoParaSerBorrado;
	}

	public void setListoParaSerBorrado(boolean listoParaSerBorrado) {
		this.listoParaSerBorrado = listoParaSerBorrado;
	}

	/** Dibuja el objeto
	 * @param v	Ventana en la que dibujarlo
	 */
	public abstract void dibujar( VentanaGrafica v );
	
	@Override
	public String toString() {
		return getClass().getName() + String.format( " (%.0f,%.0f)", x, y );
	}
}
