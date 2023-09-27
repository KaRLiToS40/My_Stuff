package survivalshooter.shooter;

import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;

public class ObjetosEspacio {
	private ArrayList<ObjetoJuego> lObjetos;
	
	public ObjetosEspacio() {
		lObjetos = new ArrayList<>();
	}
	
	public ArrayList<ObjetoJuego> getlObjetos() {
		return lObjetos;
	}

	public void setlObjetos(ArrayList<ObjetoJuego> lObjetos) {
		this.lObjetos = lObjetos;
	}

	public void dibujar(VentanaGrafica v) {
		v.borra();
		if (!lObjetos.isEmpty()) {
			for (ObjetoJuego o : lObjetos) {
				if(o instanceof Eliminador) {
					((Eliminador) o).eliminarAlChocarConBorde(v);
				}else if(o instanceof Rebotable) {
					Rebotable obj = (Rebotable) o;
					if(obj.chocaConBorde(v)) {
						obj.setVelocidadAlRebotar(v);
					}
				}
				o.dibujar(v);
			}
			v.getJFrame().revalidate();
			v.getJFrame().repaint();
		}
	}

	public void remove(ObjetoJuego o) {
		lObjetos.remove(o);
	}
	public void add(ObjetoJuego o) {
		lObjetos.add(o);
	}
}
