package survivalshooter.shooter;

import java.awt.Color;
import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;

public class Disparo extends ObjetoJuego implements Eliminador {
	protected double velX;
	protected double velY;
	public Disparo(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
	}
	
	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public void setVelXY(double velX, double velY) {
		this.velX = velX;
		this.velY = velY;
	}
	
	@Override
	public void eliminarAlChocarConBorde(VentanaGrafica v) {
		if(v.getAnchura() < x || 0 > x || v.getAltura() < y || 0 > y) {
			eliminado = true;
		}
	}
	
	/** Calcula la distancia con otro objeto, considerando sus círculos de colisión
	 * @param o2	Objeto con el que calcular la distancia
	 * @return	distancia entre los círculos de colisión de los objetos. Si están solapados, su distancia es negativa.
	 */
	@Override
	public double calcDistancia( ObjetoJuego o2 ) {
		double distCentroACentro = Math.sqrt( (x-o2.x)*(x-o2.x) + (y-o2.y)*(y-o2.y) );
		return distCentroACentro - tamanyo/2 - o2.tamanyo/2;
	}
	
	@Override
	public boolean chocaConObjetivo(ObjetoJuego o) {
		if(calcDistancia(o) <= 10 ) return true; //RADIO DE COLISIÓN AJUSTADO
		else return false;
	}
	@Override
	public void chocaConObjetivos(ArrayList<ObjetoJuego> listaObjetos) {
		for (ObjetoJuego o : listaObjetos) {
			if( o instanceof Objetivo) {
				Objetivo obj = (Objetivo) o;
				if(this.chocaConObjetivo(obj) == true) {
					obj.setEliminado(true);
					obj.puntuar();
					this.setListoParaSerBorrado(true);
				}
			}
		}
	}
	
	@Override
	public void dibujar(VentanaGrafica v) {
		x = x + velX;
		y = y + velY;
		v.dibujaCirculo( x, y, tamanyo/2, 2f, Color.RED, Color.YELLOW );
	}

	@Override
	public String toString() {
		return super.toString() + "Velocidad: " + velX + " " + velY;
	}
	
}
