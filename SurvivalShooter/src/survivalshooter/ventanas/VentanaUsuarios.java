package survivalshooter.ventanas;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import survivalshooter.shooter.Shooter;

@SuppressWarnings("serial")
public class VentanaUsuarios extends JFrame{
	
	private static String usuario = "<Nombre de Usuario>";
	private static boolean jugarConFondo = false;
	private static String naveSeleccionada;
	private static String navePropulsor;
	public static JLabel instruccion = new JLabel("Seleccione la nave que mas le guste");

	public VentanaUsuarios() {
		setLookAndFeel();
		
		//FONTS
		Font arial = new Font("Arial", Font.ITALIC, 13);
		Font eras = new Font("Eras Demi ITC", Font.BOLD, 18);
		
		//SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300,250);
		setAlwaysOnTop(true);
		setLocationRelativeTo( null );
		
		//COMPONENTES
		JPanel panelLabel1 = new JPanel();
		JPanel panelTextBox = new JPanel();
		JPanel panelAceptar = new JPanel();
		JPanel panelLabel2 = new JPanel();
		JPanel pImgs = new JPanel();
		JLabel titulo = new JLabel("Inserte su nombre de usuario");
		ImageIcon nave1 = new ImageIcon("src/survivalshooter/imagenes/nave.png");
		ImageIcon nave2 = new ImageIcon("src/survivalshooter/imagenes/nave2.png");
		ImageIcon nave3 = new ImageIcon("src/survivalshooter/imagenes/nave3.png");
		JLabel img1 = new JLabel(nave1);
		JLabel img2 = new JLabel(nave2);
		JLabel img3 = new JLabel(nave3);
		JTextField textBox = new JTextField("<Nombre de Usuario>",15);
		JButton BAceptar = new JButton("Aceptar");
		
		//ALINEAMIENTO
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(panelLabel1);
		getContentPane().add(panelTextBox);
		getContentPane().add(panelLabel2);
		getContentPane().add(pImgs);
		getContentPane().add(panelAceptar);
		
		//COLORES DE FONDO
		panelLabel1.setBackground(Color.ORANGE);
		pImgs.setBackground(new Color(255, 229, 103));
		panelAceptar.setBackground(Color.ORANGE);
		panelTextBox.setBackground(new Color(255, 222, 90));
		panelLabel2.setBackground(new Color(255, 222, 90));
		BAceptar.setBackground(Color.RED);
		BAceptar.setForeground(Color.YELLOW);
		
		panelLabel1.add(titulo);
		panelTextBox.add(textBox);
		panelLabel2.add(instruccion);
		panelAceptar.add(BAceptar);
		
		pImgs.setLayout(new FlowLayout(FlowLayout.CENTER));
		pImgs.add(img1);
		pImgs.add(img2);
		pImgs.add(img3);
		titulo.setAlignmentY(CENTER_ALIGNMENT);
		instruccion.setAlignmentY(CENTER_ALIGNMENT);
		instruccion.setFont(arial);
		titulo.setFont(eras);
		textBox.setHorizontalAlignment(SwingConstants.CENTER);
		BAceptar.setAlignmentX(CENTER_ALIGNMENT);
		BAceptar.setAlignmentY(CENTER_ALIGNMENT);
		
		img1.addMouseListener(new NaveSeleccionada(nave1));
		img2.addMouseListener(new NaveSeleccionada(nave2));
		img3.addMouseListener(new NaveSeleccionada(nave3));
		
		textBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textBox.setSelectionStart(0);
				textBox.setSelectionEnd(textBox.getText().length());
			}
		});
		
		textBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) BAceptar.requestFocus();
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if(textBox.getText().length() > 20) e.consume();
			}
		});
		
		BAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usuario = textBox.getText();
				if( !usuario.equals("<Nombre de Usuario>") && naveSeleccionada != null) {
					int option = JOptionPane.showConfirmDialog(BAceptar, "Quieres jugar con un fondo?\nEsto puede hacer que el rendimiento baje bastante", "Fondo?", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) jugarConFondo = true;
					else jugarConFondo = false;
					Thread hilo = new Thread() {
						@Override
						public void run() {
							Shooter.shooterGame();
						}
					};
					hilo.start();
					dispose();
				}else {
					instruccion.setText("Introduzca un usuario y seleccione una nave");
				}
			}
		});
		BAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				usuario = textBox.getText();
				if( !usuario.equals("<Nombre de Usuario>") && naveSeleccionada != null &&e.getKeyCode() == KeyEvent.VK_ENTER) {
					int option = JOptionPane.showConfirmDialog(BAceptar, "Quieres jugar con un fondo?\nEsto puede hacer que el rendimiento baje bastante", "Fondo?", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) jugarConFondo = true;
					else jugarConFondo = false;
					Thread hilo = new Thread() {
						@Override
						public void run() {
							Shooter.shooterGame();
						}
					};
					hilo.start();
					dispose();
				}else {
					instruccion.setText("Introduzca un usuario y seleccione una nave");
				}
			}
		});
	}
	
	public static String getNaveSeleccionada() {
		return naveSeleccionada;
	}

	public static void setNaveSeleccionada(String naveSeleccionada) {
		VentanaUsuarios.naveSeleccionada = naveSeleccionada;
	}

	public static String getNavePropulsor() {
		return navePropulsor;
	}

	public static void setNavePropulsor(String navePropulsor) {
		VentanaUsuarios.navePropulsor = navePropulsor;
	}

	public static boolean isJugarConFondo() {
		return jugarConFondo;
	}

	public static JLabel getInstruccion() {
		return instruccion;
	}

	public static boolean isLookAndFeelIntentado() {
		return lookAndFeelIntentado;
	}

	class NaveSeleccionada extends MouseAdapter{
		private ImageIcon img;
		public NaveSeleccionada(ImageIcon img) {
			this.img = img;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			switch(img.getDescription()){
			case "src/survivalshooter/imagenes/nave.png":
				VentanaUsuarios.setNaveSeleccionada("../imagenes/nave.png");
				VentanaUsuarios.setNavePropulsor("../imagenes/naveP.png");
				instruccion.setText("Nave azul seleccionada");
				break;
			case "src/survivalshooter/imagenes/nave2.png":
				VentanaUsuarios.setNaveSeleccionada("../imagenes/nave2.png");
				VentanaUsuarios.setNavePropulsor("../imagenes/nave2P.png");
				instruccion.setText("Nave roja seleccionada");
				break;
			case "src/survivalshooter/imagenes/nave3.png":
				VentanaUsuarios.setNaveSeleccionada("../imagenes/nave3.png");
				VentanaUsuarios.setNavePropulsor("../imagenes/nave3P.png");
				instruccion.setText("Nave verde seleccionada");
				break;
			default: break;
			}
		}
	}
	
	private static boolean lookAndFeelIntentado = false;
	private void setLookAndFeel() {
		if (lookAndFeelIntentado) return;
		lookAndFeelIntentado = true;
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            return;
		        }
		    }
		} catch (Exception e) {} // Si no está disponible nimbus, no se hace nada
	}
	
	/** Espera un tiempo y sigue
	 * @param milis	Milisegundos a esperar
	 */
	public void espera( long milis ) {
		try {
			Thread.sleep( milis );
		} catch (InterruptedException e) {
		}
	}

	public static String getUsuario() {
		return usuario;
	}
}
