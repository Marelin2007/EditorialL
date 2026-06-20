package org.example.presentacion;

import org.example.dominio.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainForm extends JFrame {

    private Usuario usuarioLogueado;

    public MainForm(Usuario usuario) {
        this.usuarioLogueado = usuario;
        setTitle("Editorial - Sistema de Gestión");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // ===== MENÚ BAR =====
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(33, 82, 145));
        menuBar.setBorder(new EmptyBorder(2, 5, 2, 5));

        // --- Menú Mantenimiento ---
        JMenu menuMantenimiento = new JMenu("⚙  Mantenimiento");
        menuMantenimiento.setForeground(Color.WHITE);
        menuMantenimiento.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JMenuItem itemCategorias  = new JMenuItem("📂  Categorías");
        JMenuItem itemPromociones = new JMenuItem("🏷  Promociones");

        itemCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemPromociones.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        menuMantenimiento.add(itemCategorias);
        menuMantenimiento.add(itemPromociones);

        // --- Menú Perfil ---
        JMenu menuPerfil = new JMenu("👤  " + usuarioLogueado.getNombre());
        menuPerfil.setForeground(Color.WHITE);
        menuPerfil.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JMenuItem itemMiPerfil = new JMenuItem("Mi perfil");
        itemMiPerfil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuPerfil.add(itemMiPerfil);

        // --- Menú Salida ---
        JMenu menuSalida = new JMenu("🚪  Salir");
        menuSalida.setForeground(new Color(255, 180, 180));
        menuSalida.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar sesión");
        JMenuItem itemSalir        = new JMenuItem("Salir del sistema");
        itemCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuSalida.add(itemCerrarSesion);
        menuSalida.addSeparator();
        menuSalida.add(itemSalir);

        menuBar.add(menuMantenimiento);
        menuBar.add(Box.createHorizontalGlue()); // empuja Perfil y Salida hacia la derecha
        menuBar.add(menuPerfil);
        menuBar.add(menuSalida);
        setJMenuBar(menuBar);

        // ===== PANEL CENTRAL - BIENVENIDA =====
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(245, 247, 250));

        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BoxLayout(panelBienvenida, BoxLayout.Y_AXIS));
        panelBienvenida.setOpaque(false);
        panelBienvenida.setBorder(new EmptyBorder(60, 0, 0, 0));

        JLabel lblIcono = new JLabel("📚", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBienvenido = new JLabel("Bienvenido, " + usuarioLogueado.getNombre() + "!");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBienvenido.setForeground(new Color(33, 82, 145));
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Seleccione una opción del menú para comenzar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(120, 120, 120));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBienvenida.add(lblIcono);
        panelBienvenida.add(Box.createVerticalStrut(15));
        panelBienvenida.add(lblBienvenido);
        panelBienvenida.add(Box.createVerticalStrut(8));
        panelBienvenida.add(lblSubtitulo);

        // Accesos directos
        JPanel panelAccesos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelAccesos.setOpaque(false);
        panelAccesos.setBorder(new EmptyBorder(30, 0, 0, 0));

        JButton btnAccCat  = crearBotonAcceso("📂", "Categorías");
        JButton btnAccProm = crearBotonAcceso("🏷", "Promociones");
        panelAccesos.add(btnAccCat);
        panelAccesos.add(btnAccProm);

        panelBienvenida.add(panelAccesos);
        panelCentral.add(panelBienvenida, BorderLayout.CENTER);

        // Barra de estado
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(220, 230, 245));
        panelEstado.setBorder(new EmptyBorder(4, 12, 4, 12));
        JLabel lblEstado = new JLabel("Sistema listo  |  Usuario: " + usuarioLogueado.getNombre());
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(new Color(80, 80, 100));
        panelEstado.add(lblEstado, BorderLayout.WEST);
        panelCentral.add(panelEstado, BorderLayout.SOUTH);

        setContentPane(panelCentral);

        // ===== EVENTOS =====
        itemCategorias.addActionListener(e  -> new FrmCategorias().setVisible(true));
        itemPromociones.addActionListener(e -> new FrmPromociones().setVisible(true));
        btnAccCat.addActionListener(e       -> new FrmCategorias().setVisible(true));
        btnAccProm.addActionListener(e      -> new FrmPromociones().setVisible(true));

        itemMiPerfil.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Nombre : " + usuarioLogueado.getNombre() + "\nEmail  : " + usuarioLogueado.getEmail(),
            "Mi Perfil", JOptionPane.INFORMATION_MESSAGE));

        itemCerrarSesion.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginForm login = new LoginForm();
                    login.setVisible(true);
                    if (login.isLoginExitoso())
                        new MainForm(login.getUsuarioLogueado()).setVisible(true);
                    else
                        System.exit(0);
                });
            }
        });

        itemSalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                "¿Desea salir del sistema?", "Salir",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        });
    }

    private JButton crearBotonAcceso(String icono, String texto) {
        JButton btn = new JButton("<html><center>" + icono + "<br><b>" + texto + "</b></center></html>");
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(140, 90));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(33, 82, 145));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 235), 2),
            new EmptyBorder(8, 12, 8, 12)));
        return btn;
    }
}
