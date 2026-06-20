package org.example.presentacion;
import org.example.datos.PromocionDAO;
import org.example.dominio.Promocion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class FrmPromociones extends JFrame {
    private JTextField txtNombre, txtDescuento, txtCupon, txtFechaInicio, txtFechaFin;
    private JCheckBox chkActivo;
    private JButton btnGuardar, btnModificar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PromocionDAO dao = new PromocionDAO();
    private int idSeleccionado = -1;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmPromociones() {
        setTitle("Promociones de Libros");
        setSize(860, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        // Panel formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 6));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de Promocion"));
        txtNombre      = new JTextField();
        txtDescuento   = new JTextField();
        txtCupon       = new JTextField();
        txtFechaInicio = new JTextField("2026-01-01");
        txtFechaFin    = new JTextField("2026-12-31");
        chkActivo      = new JCheckBox();
        chkActivo.setSelected(true);
        panelForm.add(new JLabel("Nombre:"));           panelForm.add(txtNombre);
        panelForm.add(new JLabel("% Descuento:"));      panelForm.add(txtDescuento);
        panelForm.add(new JLabel("Codigo Cupon:"));     panelForm.add(txtCupon);
        panelForm.add(new JLabel("Fecha Inicio (yyyy-MM-dd):")); panelForm.add(txtFechaInicio);
        panelForm.add(new JLabel("Fecha Fin (yyyy-MM-dd):"));    panelForm.add(txtFechaFin);
        panelForm.add(new JLabel("Activo:"));           panelForm.add(chkActivo);

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
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "% Desc.", "Cupon", "Inicio", "Fin", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(22);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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
            for (Promocion p : dao.listar())
                modeloTabla.addRow(new Object[]{
                    p.getIdPromocion(), p.getNombre(), p.getPorcentajeDescuento(),
                    p.getCodigoCupon(), sdf.format(p.getFechaInicio()),
                    sdf.format(p.getFechaFin()), p.getStrActivo()});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage());
        }
    }

    private Promocion leerFormulario() throws Exception {
        if (txtNombre.getText().trim().isEmpty())
            throw new Exception("El nombre no puede estar vacio.");
        if (txtDescuento.getText().trim().isEmpty())
            throw new Exception("Ingrese el porcentaje de descuento.");
        Promocion p = new Promocion();
        p.setIdPromocion(idSeleccionado);
        p.setNombre(txtNombre.getText().trim());
        p.setPorcentajeDescuento(Double.parseDouble(txtDescuento.getText().trim()));
        p.setCodigoCupon(txtCupon.getText().trim());
        p.setFechaInicio(sdf.parse(txtFechaInicio.getText().trim()));
        p.setFechaFin(sdf.parse(txtFechaFin.getText().trim()));
        p.setActivo(chkActivo.isSelected());
        return p;
    }

    private void guardar() {
        try {
            if (dao.insertar(leerFormulario())) {
                JOptionPane.showMessageDialog(this, "Promocion guardada correctamente.");
                limpiar();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una promocion de la tabla.");
            return;
        }
        try {
            if (dao.modificar(leerFormulario())) {
                JOptionPane.showMessageDialog(this, "Promocion modificada correctamente.");
                limpiar();
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una promocion de la tabla.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Desea eliminar esta promocion?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (dao.eliminar(idSeleccionado)) {
                    JOptionPane.showMessageDialog(this, "Promocion eliminada correctamente.");
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
            txtDescuento.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCupon.setText((String) modeloTabla.getValueAt(fila, 3));
            txtFechaInicio.setText((String) modeloTabla.getValueAt(fila, 4));
            txtFechaFin.setText((String) modeloTabla.getValueAt(fila, 5));
            chkActivo.setSelected(modeloTabla.getValueAt(fila, 6).equals("Activo"));
        }
    }

    private void limpiar() {
        txtNombre.setText(""); txtDescuento.setText(""); txtCupon.setText("");
        txtFechaInicio.setText("2026-01-01"); txtFechaFin.setText("2026-12-31");
        chkActivo.setSelected(true); idSeleccionado = -1; tabla.clearSelection();
    }
}
