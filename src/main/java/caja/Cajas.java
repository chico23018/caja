package caja;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import caja.conezione.Jpa;
import caja.dao.CajaDao;
import caja.dao.impl.CajaDaoImple;
import caja.model.Caja;

public class Cajas extends JFrame {
	Caja caja = new Caja();
	CajaDao cajaDao = new CajaDaoImple();
	DefaultTableModel model = new DefaultTableModel();

	private JPanel contentPane;
	private JTextField cantidad;
	private JTextField tipo;
	private JTable jTable_equipo;
	private JTextField tamano;
	public static int idcaja_update = 0;
	private JScrollPane jScrollPane_equipos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cajas frame = new Cajas();
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
	public Cajas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cajas.class.getResource("/imaganes/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 430);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane jScrollPane_equipos_1 = new JScrollPane();
		jScrollPane_equipos_1.setBounds(0, 212, 500, 179);
		contentPane.add(jScrollPane_equipos_1);
		jTable_equipo = new JTable(model);
		jScrollPane_equipos_1.setViewportView(jTable_equipo);

		tamano = new JTextField();
		tamano.setHorizontalAlignment(SwingConstants.RIGHT);
		tamano.setBounds(220, 66, 102, 28);
		contentPane.add(tamano);
		tamano.setColumns(10);

		tipo = new JTextField();
		tipo.setHorizontalAlignment(SwingConstants.RIGHT);
		tipo.setColumns(10);
		tipo.setBounds(50, 66, 102, 28);
		contentPane.add(tipo);

		cantidad = new JTextField();
		cantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		cantidad.setBounds(379, 66, 102, 28);
		contentPane.add(cantidad);
		cantidad.setColumns(10);

		JButton agregar = new JButton("agregar cajas");
		agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				caja.setTipo(tipo.getText().trim());
				caja.setTamano(tamano.getText().trim());
				caja.setCantidad(cantidad.getText().trim());
				int validacion = 0;

				if (caja.getTipo().equals("")) {
					tipo.setBackground(Color.red);
					validacion++;
				}
				if (caja.getTamano().equals("")) {
					tamano.setBackground(Color.red);
					validacion++;
				}
				if (caja.getCantidad().equals("")) {
					cantidad.setBackground(Color.red);
					validacion++;
				}

				if (validacion == 0) {

					cajaDao.inseri(caja);

				} else {
					JOptionPane.showMessageDialog(null, "debes llenar todas las casilla");
				}

				Limpiar();

			}
		});
		agregar.setHorizontalAlignment(SwingConstants.LEFT);
		agregar.setBounds(55, 146, 120, 23);
		contentPane.add(agregar);

		JButton actualizar = new JButton("actualizar tabla");
		actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String seleccion = tipo.getText();
				model.setRowCount(0);
				model.setColumnCount(0);

				jTable_equipo = new JTable(model);
				jScrollPane_equipos_1.setViewportView(jTable_equipo);
				model.addColumn("");
				model.addColumn("tipo");
				model.addColumn("Tamaño");
				model.addColumn("Cantidad");

				EntityManager em = Jpa.getEntityManagerFactory().createEntityManager();

				if (seleccion.equals("")) {

					for (Caja caja1 : cajaDao.allFind()) {
						Object[] fila = new Object[4];

						fila[0] = caja1.getId();
						fila[1] = caja1.getTipo();

						fila[2] = caja1.getTamano();
						fila[3] = caja1.getCantidad();

						model.addRow(fila);
					}
				} else {
					try {
						Query q = em.createQuery("SELECT c FROM Caja c WHERE c.tipo = ?2").setParameter(2, seleccion);
						Caja caja1 = (Caja) q.getSingleResult();

						Object[] fila = new Object[4];
						fila[0] = caja1.getId();
						fila[1] = caja1.getTipo();

						fila[2] = caja1.getTamano();
						fila[3] = caja1.getCantidad();
						model.addRow(fila);

					} catch (NoResultException nre) {

					}

				}

				ObtenerDatosTabla();

			}
		});
		actualizar.setHorizontalAlignment(SwingConstants.LEFT);
		actualizar.setBounds(206, 146, 140, 23);
		contentPane.add(actualizar);

		JLabel lblNewLabel = new JLabel("Tipo de caja");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(55, 27, 97, 28);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Tamaño");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(220, 27, 102, 28);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Cantidad");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(379, 27, 102, 28);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Clica en la linea de caja que quieres usar o modificar");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 180, 500, 32);
		contentPane.add(lblNewLabel_3);

		JLabel wallpaper = new JLabel("");
		wallpaper.setIcon(new ImageIcon(Cajas.class.getResource("/imaganes/wallpaperPrincipal.jpg")));
		wallpaper.setBounds(0, 0, 521, 391);
		contentPane.add(wallpaper);

		model.addColumn("");
		model.addColumn("tipo");
		model.addColumn("Tamaño");
		model.addColumn("Cantidad");
		EntityManager em = Jpa.getEntityManagerFactory().createEntityManager();
		

		for (Caja caja1 : cajaDao.allFind()) {
			Object[] fila = new Object[4];

			fila[0] = caja1.getId();
			fila[1] = caja1.getTipo();

			fila[2] = caja1.getTamano();
			fila[3] = caja1.getCantidad();

			model.addRow(fila);
		}

		ObtenerDatosTabla();

	}

	public void ObtenerDatosTabla() {
		jTable_equipo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila_point = jTable_equipo.rowAtPoint(e.getPoint());
				int columma_point = 0;
				if (fila_point > -1) {
					idcaja_update = (int) model.getValueAt(fila_point, columma_point);
					GestionarCajas cajas = new GestionarCajas();
					cajas.setVisible(true);

				}

			}

		});
	}

	public void Limpiar() {

		tipo.setText("");
		tamano.setText("");
		cantidad.setText("");

	}
}
