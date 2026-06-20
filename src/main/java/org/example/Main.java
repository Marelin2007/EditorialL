package org.example;

import org.example.presentacion.LoginForm;
import org.example.presentacion.MainForm;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Apariencia del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);

            if (login.isLoginExitoso()) {
                new MainForm(login.getUsuarioLogueado()).setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
