package org.example.presentacion;

import org.example.datos.UsuarioDAO;
import org.example.dominio.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginForm extends JDialog {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSalir;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private boolean loginExitoso = false;
    private Usuario usuarioLogueado = null;

    public LoginForm() {
        setTitle("Iniciar Sesión - Editorial");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // === ENCABEZADO ===
        JPanel panelHeader = new JPanel(new BorderLayout(5, 5));
        panelHeader.setOpaque(false);

        JLabel lblIcono = new JLabel("📚", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel lblTitulo = new JLabel("Editorial", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 82, 145));

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(120, 120, 120));

        panelHeader.add(lblIcono, BorderLayout.NORTH);
        panelHeader.add(lblTitulo, BorderLayout.CENTER);
        panelHeader.add(lblSubtitulo, BorderLayout.SOUTH);

        // === FORMULARIO ===
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setOpaque(false);
        panelForm.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel lblEmail = new JLabel("Correo electrónico:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtEmail = new JTextField(22);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                new EmptyBorder(5, 8, 5, 8)));
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPassword.setBorder(new EmptyBorder(10, 0, 0, 0));

        txtPassword = new JPasswordField(22);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                new EmptyBorder(5, 8, 5, 8)));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelForm.add(lblEmail);
        panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtEmail);
        panelForm.add(lblPassword);
        panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtPassword);

        // === BOTONES ===
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);

        btnLogin = new JButton("Ingresar");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(new Color(33, 82, 145));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setContentAreaFilled(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalir.setBackground(new Color(200, 60, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setOpaque(true);
        btnSalir.setContentAreaFilled(true);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelBotones.add(btnLogin);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelForm,   BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // === EVENTOS ===
        btnLogin.addActionListener(e -> login());
        btnSalir.addActionListener(e -> System.exit(0));
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String email    = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese email y contraseña.",
                    "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = usuarioDAO.autenticar(email, password);
            if (usuario != null) {
                loginExitoso    = true;
                usuarioLogueado = usuario;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Email o contraseña incorrectos.",
                        "Login", JOptionPane.WARNING_MESSAGE);
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al conectar: " + ex.getMessage(),
                    "Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isLoginExitoso()    { return loginExitoso; }
    public Usuario getUsuarioLogueado(){ return usuarioLogueado; }
}

