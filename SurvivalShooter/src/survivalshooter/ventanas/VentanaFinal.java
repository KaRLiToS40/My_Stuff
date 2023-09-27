package survivalshooter.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import survivalshooter.gestor.GestorDatos;
import survivalshooter.gestor.Usuario;

public class VentanaFinal extends JFrame{
	private static final long serialVersionUID = 1L;
	//VARIABLES EVENTOS
	private JTextField textBox = new JTextField(15);
	private JLabel instrucciones = new JLabel("Escoja una opción de la lista");
	private static GestorDatos gestor = new GestorDatos();
	private static boolean hiloStop = false;
	private static boolean bHistoriaOn = false;

	//FUENTES
	private static Font Berlin = new Font("Berlin Sans FB Demi", Font.ITALIC, 40);
	private static Font Cooper = new Font("Cooper Black", Font.BOLD, 18);
	private static Font Eras = new Font("Eras Demi ITC", Font.PLAIN, 13);
	private static Font TW = new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 13);
	
	public static boolean isHiloStop() {
		return hiloStop;
	}

	public static void main(String[] args) {
		VentanaFinal v = new VentanaFinal();
		v.setVisible(true);
	}
	
	public VentanaFinal() {
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		
		JPanel panelN = new JPanel();
		JPanel panelO = new JPanel();
			JPanel panel_O1 = new JPanel();
			JPanel panel_O2 = new JPanel();
		JPanel panelS = new JPanel();
		JPanel panelE = new JPanel();
		JScrollPane scroll = new JScrollPane();
		
		JLabel tituloN = new JLabel(" SCORES ");
		JLabel img = new JLabel(new ImageIcon("src/survivalshooter/imagenes/nave.png"));
		JLabel img2 = new JLabel(new ImageIcon("src/survivalshooter/imagenes/nave.png"));
		JTextArea text = new JTextArea("Loading...");
		JComboBox<String> opciones = new JComboBox<>(new String[] {"Seleccione una opción","Ir a una linea determinada", "Buscar usuario", "Buscar usuario por score"});
		JButton[] numPad = new JButton[12];
		JButton cerrar = new JButton("Cerrar");
		JButton volverAJugar = new JButton("Volver a Jugar");
		JButton historia = new JButton("Mostrar Historia");
		JButton introNum = new JButton("INTRO");
		String nums = "123456789<0C";
		for(int i = 0; i < nums.length(); i++ ) {
			numPad[i] = new JButton (nums.substring(i, i+1));
			numPad[i].setFont(Cooper);
			numPad[i].setBackground(Color.YELLOW);
		}
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelN, BorderLayout.NORTH);
		getContentPane().add(panelS, BorderLayout.SOUTH);
		getContentPane().add(panelE, BorderLayout.EAST);
		getContentPane().add(panelO, BorderLayout.WEST);
		getContentPane().add(scroll, BorderLayout.CENTER);
		panelO.setLayout(new BorderLayout());
		
		panelN.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelN.add(img);
		panelN.add(tituloN);
		panelN.add(img2);

		tituloN.setFont(Berlin);
		tituloN.setForeground(Color.WHITE);
		panelN.setBackground(Color.BLACK);
		
		panelS.setBackground(Color.BLACK);
		panelS.setLayout(new FlowLayout(FlowLayout.CENTER, 50,10));
		panelS.add(volverAJugar);
		volverAJugar.setBackground(Color.GREEN);
		volverAJugar.setFont(TW);
		panelS.add(cerrar);
		cerrar.setBackground(Color.CYAN);
		cerrar.setFont(TW);
		panelS.add(historia);
		historia.setBackground(Color.MAGENTA);
		historia.setFont(TW);
		
		panelO.setLayout(new BoxLayout(panelO, BoxLayout.Y_AXIS));
		panelO.add(opciones);
		panelO.add(panel_O1);
		panelO.add(panel_O2);
		panelO.add(Box.createVerticalStrut(10));
		panelO.add(introNum);
		opciones.setBorder(new EmptyBorder(10, 10, 10, 10));
		opciones.setMaximumSize(new Dimension(200, 50));
		introNum.setAlignmentX(CENTER_ALIGNMENT);
		introNum.setBackground(Color.RED);
		introNum.setForeground(Color.YELLOW);

		panel_O1.setLayout(new GridLayout(4, 3));
		panel_O1.setMaximumSize(new Dimension(180, 180));
		for (JButton b : numPad) {
			panel_O1.add(b);
			b.addActionListener(new GridButtonListener());
		}
		
		panel_O2.setLayout(new BorderLayout());
		panel_O2.setMaximumSize(new Dimension(170, 70));
		panel_O2.setBorder(new EmptyBorder(20,0,0,0));
		panel_O2.add(instrucciones, BorderLayout.NORTH);
		panel_O2.add(textBox, BorderLayout.SOUTH);
		instrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		instrucciones.setFont(Eras);
		textBox.setBackground(Color.ORANGE);
		textBox.setMaximumSize(new Dimension(150,20));
		
		panelE.setBackground(Color.BLACK);
		scroll.setViewportView(text);
		scroll.setBackground(Color.WHITE);
		text.setAlignmentX(LEFT_ALIGNMENT);
		text.setBorder(new EmptyBorder(10,10,0,0));
		
		//GESTOR DE USUARIOS
		Thread hilo = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gestor.readScores(text);
			}
		};
		hilo.start();
		
		//EVENTOS
		opciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> opt = (JComboBox<String>) e.getSource();
				String seleccion = (String)opt.getSelectedItem();
				switch (seleccion) {
					case "Ir a una linea determinada":
						instrucciones.setText("Inserte la linea deseada");
						textBox.setText("");
					break;
					case "Buscar usuario":
						instrucciones.setText("Ponga el nombre de usuario");
						textBox.setText("");
						break;
					case "Buscar usuario por score":
						instrucciones.setText("Escriba la puntuacion");
						textBox.setText("");
						break;
					default: 
						instrucciones.setText("Escoja una opción de la lista");
						textBox.setText("");
						break;
				}
			}
		});
		cerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hiloStop = true;
				dispose();
			}
		});
		introNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowNum = 2;
				if (opciones.getSelectedItem().equals("Ir a una linea determinada")) {
					rowNum = Integer.parseInt(textBox.getText()) + 1;
				}else if (opciones.getSelectedItem().equals("Buscar usuario")) { //Devuelve el primer usuario con ese nombre
					rowNum = 2;
					for (Usuario usr : GestorDatos.getLecturaUsuarios()) {
						if(usr.getNombreUsuario().equals(textBox.getText())) break;
						rowNum++;
					}
				}else if(opciones.getSelectedItem().equals("Buscar usuario por score")) { //Devuelve el primer usuario con esa puntuacion
					rowNum = 2;
					for (Usuario usr : GestorDatos.getLecturaUsuarios()) {
						if(usr.getScore() == Integer.parseInt(textBox.getText())) break;
						rowNum++;
					}
					if(rowNum == text.getLineCount()) rowNum = text.getLineCount()+1;
				} else return;
				if(rowNum >= text.getLineCount()) instrucciones.setText("El usuario no existe");
				else {
					instrucciones.setText("Vuelva a elegir!");
					//Document contiene el documento entero / DefaultRootElement contiene los elementos secundarios (lineas), que empiezan en el 0
					if(text.getLineCount() > rowNum) {
						int offset = text.getDocument().getDefaultRootElement().getElement(rowNum - 1).getStartOffset();
						text.requestFocus();
						text.setCaretPosition(offset);
						int inicio = text.getDocument().getDefaultRootElement().getElement(rowNum - 1).getStartOffset();
						int end = text.getDocument().getDefaultRootElement().getElement(rowNum - 1).getEndOffset(); //No pongo -1 para que salte a la siguiente linea y no se obstruya la visión
						text.setSelectionStart(inicio);
						text.setSelectionEnd(end);
					}
				}
			}
		});
		textBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if((e.getKeyChar() < '0' || e.getKeyChar() > '9' ) && opciones.getSelectedItem().equals("Buscar usuario por score")) e.consume();
			}
		});
		
		historia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bHistoriaOn = !bHistoriaOn;
				if(bHistoriaOn) {
					introNum.setEnabled(false);
					instrucciones.setText("¡Disfrute de la historia!");
					JTextArea txt = new JTextArea();
					scroll.setViewportView(txt);
					txt.setBorder(new EmptyBorder(10,10,0,0));
					gestor.leerFicheroRaw(txt,"READ_ME.txt");
					txt.setCaretPosition(0);
				}else {
					scroll.setViewportView(text);
					introNum.setEnabled(true);
					instrucciones.setText("Escoja una opción de la lista");
				}
			}
		});
		
		volverAJugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaUsuarios v = new VentanaUsuarios();
				v.setVisible(true);
				dispose();
			}
		});
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				hiloStop = true;
			}
			
		});
	}
	
	class GridButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boton = (JButton) e.getSource();
			if(boton.getText().equals("<") && !textBox.getText().isEmpty()) {
				textBox.setText(textBox.getText().substring(0, textBox.getText().length()-1));
			}else if (boton.getText().equals("C")) {
				textBox.setText("");
			}else {
				textBox.setText(textBox.getText() + boton.getText());
			}
		}
	}
}
