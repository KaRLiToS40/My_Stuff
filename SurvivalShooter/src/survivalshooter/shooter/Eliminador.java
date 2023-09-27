package survivalshooter.shooter;

import java.util.ArrayList;

import survivalshooter.ventanas.VentanaGrafica;

public interface Eliminador {
	void eliminarAlChocarConBorde(VentanaGrafica v);
	double calcDistancia(ObjetoJuego o);
	boolean chocaConObjetivo(ObjetoJuego o);
	void chocaConObjetivos(ArrayList<ObjetoJuego> listaObjetos);
}
