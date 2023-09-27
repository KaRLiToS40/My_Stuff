package survivalshooter.shooter;

import survivalshooter.ventanas.VentanaGrafica;

public interface Rebotable {
	void setVelocidadAlRebotar(VentanaGrafica v);
	public boolean chocaConBorde(VentanaGrafica v);
}
