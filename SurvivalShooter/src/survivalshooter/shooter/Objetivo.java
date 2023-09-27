package survivalshooter.shooter;

import java.io.Serializable;
import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;

public abstract class Objetivo extends ObjetoJuego implements Animable, Serializable{
	private static final long serialVersionUID = 1L;
	
	protected double velRotacionRad;
	protected double anguloActual;
	protected int tamanyo;
	protected double velX;
	protected double velY;
	
	public Objetivo(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
	}
	public Objetivo(VentanaGrafica v) {
		super(v);
	}
	
	public abstract void puntuar();
	
	public double getVelRotacionRad() {
		return velRotacionRad;
	}
	public void setVelRotacionRad(double velRotacionRad) {
		this.velRotacionRad = velRotacionRad;
	}
	public double getAnguloActual() {
		return anguloActual;
	}
	public void setAnguloActual(double anguloActual) {
		this.anguloActual = anguloActual;
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
	
	public Objetivo chocaConObjeto(ArrayList<ObjetoJuego> listaObjetos) {
		return null;
	}
	
}
