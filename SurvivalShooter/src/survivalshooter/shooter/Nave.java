package survivalshooter.shooter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;
import survivalshooter.ventanas.VentanaUsuarios;

public class Nave extends ObjetoJuego implements Eliminador{
	
	private static final double DURACION_EFECTO_ESCUDO  = 3000;
	
	private int vidasNave;
	private double radianesDeRotacion;
	private double accel = 0.5;
	private double velX = 0;
	private double velY = 0;
	private final double VEL_DISPAROS = 20;
	private final double VEL_MAX_NAVE = 15;
	private boolean A, W, S, D = false;
	private boolean tieneEscudo = false;
	private double tiempoInterno = System.currentTimeMillis();
	
	public Nave(double x, double y, int tamanyo) {
		super(x, y, tamanyo);
		radianesDeRotacion = 0.0;
		vidasNave = 5;
	}

	public double getRadianesDeRotacion() {
		return radianesDeRotacion;
	}

	public void setRadianesDeRotacion(double radianesDeRotacion) {
		this.radianesDeRotacion = radianesDeRotacion;
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

	public boolean isTieneEscudo() {
		return tieneEscudo;
	}

	public void setTieneEscudo(boolean tieneEscudo) {
		this.tieneEscudo = tieneEscudo;
	}

	public int getVidasNave() {
		return vidasNave;
	}

	public void setVidasNave(int vidasNave) {
		this.vidasNave = vidasNave;
	}

	public void disparoNormal(ObjetosEspacio mundo) {
		Disparo disparoNormal = new Disparo(x,y,15);
		disparoNormal.setVelXY(Math.sin(radianesDeRotacion)*VEL_DISPAROS, -Math.cos(radianesDeRotacion)*VEL_DISPAROS);
		disparoNormal.setX(x);
		disparoNormal.setY(y);
		mundo.add(disparoNormal);
	}
	
	public void disparoEspecial(ObjetosEspacio mundo, Objetivo o) {
		if(o != null) {
			DisparoGuiado disparoGuiado = new DisparoGuiado(x, y, 30);
			disparoGuiado.setVelXY(Math.sin(radianesDeRotacion)*VEL_DISPAROS, -Math.cos(radianesDeRotacion)*VEL_DISPAROS);
			disparoGuiado.setX(x);
			disparoGuiado.setY(y);
			disparoGuiado.setObjetivo(o);
			mundo.add(disparoGuiado);
		}
	}
	
	@Override
	public void dibujar(VentanaGrafica v) {
		y = y + velY;
		x = x + velX;
		if(tieneEscudo) {
			v.dibujaImagen("../imagenes/burbuja.png", x, y, tamanyo*2, tamanyo*2, 1, 0, 1f);
			if(System.currentTimeMillis() >= tiempoInterno) {
				tiempoInterno = System.currentTimeMillis();
				tieneEscudo = false;
			}
		}
		if(velY == 0 && velX == 0) v.dibujaImagen(VentanaUsuarios.getNaveSeleccionada(), x, y, tamanyo, tamanyo, 1, radianesDeRotacion, 1f);
		else v.dibujaImagen(VentanaUsuarios.getNavePropulsor(), x, y, tamanyo, tamanyo, 1, radianesDeRotacion, 1f);
		
		for (int i = 1; i < vidasNave+1; i++) {
			v.dibujaImagen("../imagenes/heart.png", 10+i*25, 40, 20, 20, 1, 0, 1f);
		}				
	}
	
	/**
	 * Este método actualiza la posición y rotación de la nave con las teclas y el
	 * mouse
	 * @param ventana
	 */
	@Override
	public void eliminarAlChocarConBorde(VentanaGrafica ventana) {
		if( x < 0) x = ventana.getAnchura();
		else if (x > ventana.getAnchura()) x = 0;
		if( y < 0) y = ventana.getAltura();
		else if (y > ventana.getAltura()) y = 0;
		
		teclasPulsadas(ventana);
		
		if(A && velX > -VEL_MAX_NAVE) {
			 velX = velX - accel;
		 }
		if(D && velX < VEL_MAX_NAVE) {
			 velX = velX + accel;
		 } 
		if(W && velY > -VEL_MAX_NAVE) {
			 velY = velY - accel;
		 }
		  if(S && velY < VEL_MAX_NAVE) {
			 velY = velY + accel;
		 }
		  if(!A && !D) {
			  if(velX < 0) velX = velX + accel;
			  else if(velX > 0) velX = velX - accel;
		  }if(!W && !S) {
			  if(velY < 0) velY = velY + accel;
			  else if(velY > 0) velY = velY - accel;
		  }
		 radianesDeRotacion = -Math.atan2(x - ventana.getRatonMovido().getX(), y - ventana.getRatonMovido().getY());
	 }
	
	public void teclasPulsadas(VentanaGrafica ventana) {
		if(ventana.isTeclaPulsada(KeyEvent.VK_A)) A = true;
		else A = false;
		if(ventana.isTeclaPulsada(KeyEvent.VK_W)) W = true;
		else W = false;
		if(ventana.isTeclaPulsada(KeyEvent.VK_S)) S = true;
		else S = false;
		if(ventana.isTeclaPulsada(KeyEvent.VK_D)) D = true;
		else D = false;
	}

	@Override
	public boolean chocaConObjetivo(ObjetoJuego o) {
		if (calcDistancia(o) <= tamanyo)
			return true; // RADIO DE COLISIÓN AJUSTADO
		else
			return false;
	}

	@Override
	public void chocaConObjetivos(ArrayList<ObjetoJuego> listaObjetos) {
		for (ObjetoJuego o : listaObjetos) {
			if (o instanceof Escudo) {
				Escudo obj = (Escudo) o;
				if (this.chocaConObjetivo(obj) == true) {
					obj.setEliminado(true);
					tieneEscudo = true;
					vidasNave++;
					Shooter.escudosAdquiridos++;
					tiempoInterno = System.currentTimeMillis() + DURACION_EFECTO_ESCUDO;
				}
			}else if (o instanceof Objetivo) {
				Objetivo obj = (Objetivo) o;
				if (chocaConObjetivo(obj) == true && !obj.isEliminado()) {
					if (tieneEscudo) {
						obj.setEliminado(true);
						obj.puntuar();
					}
					else {
						obj.setEliminado(true);
						if (obj instanceof Estrella) vidasNave = vidasNave -2;
						else vidasNave--;
					}
				}
			}
		}
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

}
