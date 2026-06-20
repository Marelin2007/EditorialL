package org.example.datos;

import org.example.dominio.Usuario;
import java.sql.*;

public class UsuarioDAO {

    // Autentica: devuelve el Usuario si email+password son correctos, null si no
    public Usuario autenticar(String email, String password) throws SQLException {
        String sql = "SELECT IdUsuario, Nombre, Email, PasswordHash, Activo " +
                     "FROM Usuarios WHERE Email = ? AND Activo = 1";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashGuardado = rs.getString("PasswordHash");
                // Comparamos el hash MD5 del password ingresado con el guardado
                if (hashGuardado.equalsIgnoreCase(md5(password))) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("IdUsuario"));
                    u.setNombre(rs.getString("Nombre"));
                    u.setEmail(rs.getString("Email"));
                    u.setPasswordHash(hashGuardado);
                    u.setActivo(rs.getBoolean("Activo"));
                    return u;
                }
            }
        }
        return null; // credenciales incorrectas
    }

    // Hash MD5 simple para contraseñas
    public static String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular MD5", e);
        }
    }
}
