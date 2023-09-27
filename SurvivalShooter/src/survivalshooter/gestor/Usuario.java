package survivalshooter.gestor;

import survivalshooter.shooter.Shooter;
import survivalshooter.ventanas.VentanaUsuarios;

public class Usuario implements Comparable<Usuario>{
	private String nombreUsuario;
	private int score, asteroidesEliminados, estrellasEliminadas, escudosAdquiridos;
	private double tiempoDeJuego;
	
	public Usuario (String usuario, int score, int asteroidesEliminados, int estrellasEliminadas, int escudosAquiridos, double tiempodeJuego ) {
		this.nombreUsuario = usuario;
		this.score = score;
		this.asteroidesEliminados = asteroidesEliminados;
		this.estrellasEliminadas = estrellasEliminadas;
		this.escudosAdquiridos = escudosAquiridos;
		this.tiempoDeJuego = tiempodeJuego;
	}
	
	public Usuario() {
		nombreUsuario = VentanaUsuarios.getUsuario();
		score = Shooter.score;
		asteroidesEliminados = Shooter.asteroidesEliminados;
		estrellasEliminadas = Shooter.estrellasEliminadas;
		escudosAdquiridos = Shooter.escudosAdquiridos;
		tiempoDeJuego = Shooter.tiempoDeJuego;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String usuario) {
		this.nombreUsuario = usuario;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getAsteroidesEliminados() {
		return asteroidesEliminados;
	}

	public void setAsteroidesEliminados(int asteroidesEliminados) {
		this.asteroidesEliminados = asteroidesEliminados;
	}

	public int getEstrellasEliminadas() {
		return estrellasEliminadas;
	}

	public void setEstrellasEliminadas(int estrellasEliminadas) {
		this.estrellasEliminadas = estrellasEliminadas;
	}

	public int getEscudosAdquiridos() {
		return escudosAdquiridos;
	}

	public void setEscudosAdquiridos(int escudosAdquiridos) {
		this.escudosAdquiridos = escudosAdquiridos;
	}

	public double getTiempoDeJuego() {
		return tiempoDeJuego;
	}

	public void setTiempoDeJuego(double tiempoDeJuego) {
		this.tiempoDeJuego = tiempoDeJuego;
	}

	public String usuarioALinea() {
		return "" + nombreUsuario + "\t" + score + "\t" + asteroidesEliminados + "\t" + estrellasEliminadas + "\t" + escudosAdquiridos + "\t" + tiempoDeJuego;
	}

	@Override
	public String toString() {
		int horas = (int) Math.floor(tiempoDeJuego/(60*60*1000));
		int minutos = (int) Math.floor((tiempoDeJuego/(60*1000))%6000);
		int segundos = (int) Math.floor((tiempoDeJuego/1000)%60);
		return "" + nombreUsuario + "\t" + score + "\t" + asteroidesEliminados + "\t" + estrellasEliminadas + "\t" + escudosAdquiridos + "\t" + horas + " h : " + minutos + " min : " + segundos +" sec" ;
	}

	@Override
	public int compareTo(Usuario o) {
		if(score < o.score) return 1;
		else if (score > o.score) return -1;
		else {
			if (nombreUsuario.compareTo(o.nombreUsuario) != 0)
				return nombreUsuario.compareTo(o.nombreUsuario);
			else return 0;
		}
	}
}
