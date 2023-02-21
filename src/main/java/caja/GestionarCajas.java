package caja;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import caja.conezione.Jpa;
import caja.dao.CajaDao;
import caja.dao.impl.CajaDaoImple;
import caja.model.Caja;

public class GestionarCajas extends JFrame {
	Caja caja2 = new Caja();
	CajaDao cajaDao = new CajaDaoImple();
	private EntityManager em = Jpa.getEntityManagerFactory().createEntityManager();

	int idcaja = Cajas.idcaja_update;
	String cantidadstring;

	private JPanel contentPane;
	private JTextField tipo;
	private JTextField tamano;
	private JTextField cantidad;
	private JTextField usarcajas;
	private JTextField agregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionarCajas frame = new GestionarCajas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestionarCajas() {
		setAutoRequestFocus(false);

		idcaja = Cajas.idcaja_update;
		setTitle("Gestionar cajas");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestionarCajas.class.getResource("/imaganes/icon.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 454);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tipo = new JTextField();
		tipo.setHorizontalAlignment(SwingConstants.CENTER);
		tipo.setBounds(46, 96, 86, 20);
		contentPane.add(tipo);
		tipo.setColumns(10);

		tamano = new JTextField();
		tamano.setHorizontalAlignment(SwingConstants.CENTER);
		tamano.setBounds(229, 96, 86, 20);
		contentPane.add(tamano);
		tamano.setColumns(10);

		cantidad = new JTextField();
		cantidad.setHorizontalAlignment(SwingConstants.CENTER);
		cantidad.setEnabled(false);
		cantidad.setEditable(false);
		cantidad.setBounds(417, 96, 86, 20);
		contentPane.add(cantidad);
		cantidad.setColumns(10);

		JButton Eliminar = new JButton("Eliminar");
		Eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handle_Eliminar_actionPerformed(e);
			}
		});
		Eliminar.setBounds(46, 260, 89, 23);
		contentPane.add(Eliminar);

		JButton modificar = new JButton("Modificar");
		modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handle_modificar_actionPerformed(e);
			}
		});
		modificar.setBounds(226, 260, 89, 23);
		contentPane.add(modificar);

		JButton usar = new JButton("Usar cajas");
		usar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handle_usar_actionPerformed(e);
			}
		});
		usar.setBounds(417, 260, 100, 23);
		contentPane.add(usar);

		JLabel lblNewLabel_1 = new JLabel("Tipo");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(65, 71, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Tamaño");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(247, 71, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Cantidad disponible");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(406, 71, 111, 14);
		contentPane.add(lblNewLabel_3);

		usarcajas = new JTextField();
		usarcajas.setHorizontalAlignment(SwingConstants.CENTER);
		usarcajas.setBounds(417, 190, 86, 20);
		contentPane.add(usarcajas);
		usarcajas.setColumns(10);

		agregar = new JTextField();
		agregar.setHorizontalAlignment(SwingConstants.CENTER);
		agregar.setBounds(229, 190, 86, 20);
		contentPane.add(agregar);
		agregar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Agregar mas cajas");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(201, 156, 125, 20);
		contentPane.add(lblNewLabel_4);

		JButton Agregar = new JButton("Agregar cajas");
		Agregar.setEnabled(true);
		Agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handle_Agregar_actionPerformed(e);
			}
		});
		Agregar.setBounds(229, 324, 118, 23);
		contentPane.add(Agregar);

		JLabel lblNewLabel_5 = new JLabel("Cuantas cajas quieres usar");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(390, 159, 165, 14);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Aqui pudes agregar, usar cajas, modificar o eliminar un tipo de cajas ");
		lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(10, 11, 604, 35);
		contentPane.add(lblNewLabel_6);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setLabelFor(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(GestionarCajas.class.getResource("/imaganes/wallpaperPrincipal.jpg")));
		lblNewLabel.setBounds(0, 0, 624, 415);
		contentPane.add(lblNewLabel);

		caja2 = cajaDao.find(idcaja);
		cantidad.setText(caja2.getCantidad());
		tipo.setText(caja2.getTipo());
		tamano.setText(caja2.getTamano());
	}

	protected void handle_Eliminar_actionPerformed(ActionEvent e) {

		cajaDao.eliminare(idcaja);
		JOptionPane.showMessageDialog(null, "Caja eliminada");
		this.dispose();

	}

	protected void handle_modificar_actionPerformed(ActionEvent e) {
		int validacion = 0;
		String tipo1, tamano1;
		tipo1 = tipo.getText().trim();
		tamano1 = tamano.getText().trim();
		if (tipo1.equals("")) {
			tipo.setBackground(Color.RED);
			validacion++;
		}
		if (tamano1.equals("")) {
			tamano.setBackground(Color.RED);
			validacion++;
		}

		if (validacion == 0) {
			caja2.setTipo(tipo1);
			caja2.setTamano(tamano1);
			cajaDao.update(caja2);
			JOptionPane.showMessageDialog(null, "caja actualizada");
			this.dispose();

		} else {
			JOptionPane.showMessageDialog(null, "Debes llenar las casillas");
		}
	}

	protected void handle_usar_actionPerformed(ActionEvent e) {
		int validacion = 0;
		String usar1 = usarcajas.getText().trim();

		if (usar1.equals("")) {
			usarcajas.setBackground(Color.RED);
			validacion++;
		}
		if (validacion == 0) {
			int usar2 = Integer.parseInt(usar1);
			cajaDao.Usar(idcaja, usar2);
			JOptionPane.showMessageDialog(null, "Has usado  " + usar2 + " cajas");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Casilla vacia, tienes que indar un numero");
		}

	}

	protected void handle_Agregar_actionPerformed(ActionEvent e) {

		String usar1 = "";

		if (usar1.equals("")) {
			agregar.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Casilla vacia, tienes que indar un numero");

		} else {
			int usar2 = Integer.parseInt(usar1);
			cajaDao.agregar(idcaja, usar2);

			JOptionPane.showMessageDialog(null, "Has agregador  " + usar2 + " cajas");
			this.dispose();

		}
	}

}
