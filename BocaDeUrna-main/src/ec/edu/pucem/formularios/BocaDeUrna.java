package ec.edu.pucem.formularios;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import ec.edu.pucem.dominios.Prefecto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;

public class BocaDeUrna extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel model;
	public List<Prefecto> prefectos;
	private JPanel panel;
	private JButton btnCancelar;
	private JButton btnNewButton;

	public BocaDeUrna(List<Prefecto> prefectos) {
		this.prefectos = prefectos;
		setTitle("BOCA DE URNA - REGISTRO");
		setBounds(100, 100, 600, 427);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 115, 566, 224);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(model.getValueAt(0, 0));
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre", "Votos" }));
		scrollPane.setViewportView(table);

		panel = new JPanel();
		panel.setBounds(12, 12, 566, 84);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(93, 361, 117, 25);
		getContentPane().add(btnCancelar);
		
		btnNewButton = new JButton("Selecionnar ganador ");
		btnNewButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            JOptionPane.showMessageDialog(null,"El ganador es lista : " + selectedRow);
		        } else {
		        	JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna fila");
		        }
		    }
		});
			
		btnNewButton.setBounds(267, 362, 189, 23);
		getContentPane().add(btnNewButton);

		model = (DefaultTableModel) table.getModel();
		cargarCandidatos();
		llenarTabla();
	}

	private void cargarCandidatos() {
		int x = 0;
		for (Prefecto prefecto : prefectos) {
			JButton btnPrefecto = new JButton(prefecto.getNombre());
			btnPrefecto.setBounds(x * 155, 0, 150, 80);
			btnPrefecto.addActionListener(this);
			panel.setLayout(null);
			panel.add(btnPrefecto);
			x++;
		}
	}

	private void llenarTabla() {
        model.setRowCount(0);
        for (Prefecto prefecto : prefectos) {
            Object[] fila = new Object[2];
            fila[0] = prefecto.getNombre();
            fila[1] = prefecto.getVotos();
            model.addRow(fila);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            dispose();
        }
        String textoBotonPulsado = e.getActionCommand();
        for (Prefecto prefecto : prefectos) {
            if (textoBotonPulsado.equals(prefecto.getNombre())) {
                prefecto.setVotos(prefecto.getVotos() + 1);
                llenarTabla();
                break;
            }
        }

    }

	public List<Prefecto> getPrefectos() {
		return prefectos;
	}

	public void setPrefectos(List<Prefecto> prefectos) {
		this.prefectos = prefectos;
	}
}
