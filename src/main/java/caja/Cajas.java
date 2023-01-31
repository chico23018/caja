package caja;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import java.awt.Font;

public class Cajas extends JFrame {
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

				String tipo1, tamano1, cantidad1;

				tipo1 = tipo.getText().trim();
				tamano1 = tamano.getText().trim();
				cantidad1 = cantidad.getText().trim();

				int validacion = 0;

				if (tipo1.equals("")) {
					tipo.setBackground(Color.red);
					validacion++;
				}
				if (tamano1.equals("")) {
					tamano.setBackground(Color.red);
					validacion++;
				}
				if (cantidad1.equals("")) {
					cantidad.setBackground(Color.red);
					validacion++;
				}

				try {
					Connection cn = Conezione.conetar();
					PreparedStatement ps = cn
							.prepareStatement("select tipo, tamano from caja where tipo = '" + tipo1 + "'");

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {

						JOptionPane.showMessageDialog(null, "tipo de caja ya excistente en el sistema");

					} else {

						if (validacion == 0) {

							Connection cn1 = Conezione.conetar();
							PreparedStatement ps1 = cn1.prepareStatement("insert into caja values(?,?,?,?)");
							ps1.setInt(1, 0);
							ps1.setString(2, tipo1);
							ps1.setString(3, tamano1);
							ps1.setString(4, cantidad1);
							ps1.executeUpdate();

						} else {
							JOptionPane.showMessageDialog(null, "debes llenar todas las casilla");
						}

					}

				} catch (SQLException e2) {
					System.err.print("error en cargar cajas " + e2);
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
				String query = "";

				model.setRowCount(0);
				model.setColumnCount(0);
				try {
					Connection cn = Conezione.conetar();

					if (seleccion.equals("")) {
						query = "select ID, tipo, tamano, cantidad from caja";

					} else {
						query = "select ID, tipo, tamano, cantidad from caja where tipo='" + seleccion + "'";
					}
					PreparedStatement ps = cn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					jTable_equipo = new JTable(model);
					jScrollPane_equipos_1.setViewportView(jTable_equipo);
					model.addColumn("");
					model.addColumn("tipo");
					model.addColumn("Tamaño");
					model.addColumn("Cantidad");

					while (rs.next()) {
						Object[] fila = new Object[4];
						for (int i = 0; i < 4; i++) {
							fila[i] = rs.getObject(i + 1);
						}
						model.addRow(fila);

					}
					cn.close();

				} catch (SQLException l) {
					System.err.println("Error en la coneccion  boton mostra " + l);
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

		try {
			Connection cn = Conezione.conetar();
			PreparedStatement ps = cn.prepareStatement("select ID, tipo, tamano, cantidad from caja");
			ResultSet rs = ps.executeQuery();

			model.addColumn("");
			model.addColumn("tipo");
			model.addColumn("Tamaño");
			model.addColumn("Cantidad");

			while (rs.next()) {
				Object[] fila = new Object[4];
				for (int i = 0; i < 4; i++) {
					fila[i] = rs.getObject(i + 1);

				}
				model.addRow(fila);

			}

			cn.close();
		} catch (SQLException e) {
			System.err.println("Errror en recupere gestionar de cliente " + e);
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
