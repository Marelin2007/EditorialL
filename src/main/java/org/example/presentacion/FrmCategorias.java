package org.example.presentacion;
import org.example.datos.CategoriaDAO;
import org.example.dominio.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmCategorias extends JFrame {
    private JTextField txtNombre;
    private JCheckBox chkActivo;
    private JButton btnGuardar, btnModificar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CategoriaDAO dao = new CategoriaDAO();
    private int idSeleccionado = -1;

    public FrmCategorias() {
        setTitle("Categorias de Libros");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        // Panel formulario
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de Categoria"));
        panelForm.add(new JLabel("Nombre Categoria:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Activo:"));
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);
        panelForm.add(chkActivo);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        btnGuardar   = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar  = new JButton("Eliminar");
        btnLimpiar   = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Panel superior = formulario + botones apilados
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre Categoria", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(22);

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
    }

    private void cargarTabla() {
        try {
            modeloTabla.setRowCount(0);
            for (Categoria c : dao.listar())
                modeloTabla.addRow(new Object[]{c.getIdCategoria(), c.getNombreCategoria(), c.getStrActivo()});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage());
        }
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
            return;
        }
        try {
            if (dao.insertar(new Categoria(0, txtNombre.getText().trim(), chkActivo.isSelected()))) {
                JOptionPane.showMessageDialog(this, "Categoria guardada correctamente.");
                limpiar();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoria de la tabla.");
            return;
        }
        try {
            if (dao.modificar(new Categoria(idSeleccionado, txtNombre.getText().trim(), chkActivo.isSelected()))) {
                JOptionPane.showMessageDialog(this, "Categoria modificada correctamente.");
                limpiar();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoria de la tabla.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Desea eliminar esta categoria?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (dao.eliminar(idSeleccionado)) {
                    JOptionPane.showMessageDialog(this, "Categoria eliminada correctamente.");
                    limpiar();
                    cargarTabla();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
            txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
            chkActivo.setSelected(modeloTabla.getValueAt(fila, 2).equals("Activo"));
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        chkActivo.setSelected(true);
        idSeleccionado = -1;
        tabla.clearSelection();
    }
}
