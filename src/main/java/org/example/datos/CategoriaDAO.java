package org.example.datos;
import org.example.dominio.Categoria;
import java.sql.*;
import java.util.*;

public class CategoriaDAO {
    public boolean insertar(Categoria c) throws SQLException {
        String sql = "INSERT INTO Categorias (NombreCategoria, Activo) VALUES (?, ?)";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombreCategoria()); ps.setBoolean(2, c.isActivo());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean modificar(Categoria c) throws SQLException {
        String sql = "UPDATE Categorias SET NombreCategoria=?, Activo=? WHERE IdCategoria=?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombreCategoria()); ps.setBoolean(2, c.isActivo()); ps.setInt(3, c.getIdCategoria());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Categorias WHERE IdCategoria=?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }
    public List<Categoria> listar() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Categorias")) {
            while (rs.next())
                lista.add(new Categoria(rs.getInt("IdCategoria"), rs.getString("NombreCategoria"), rs.getBoolean("Activo")));
        }
        return lista;
    }
}
