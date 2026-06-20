package org.example.datos;
import org.example.dominio.Promocion;
import java.sql.*;
import java.util.*;

public class PromocionDAO {
    public boolean insertar(Promocion p) throws SQLException {
        String sql = "INSERT INTO Promociones (Nombre, PorcentajeDescuento, CodigoCupon, FechaInicio, FechaFin, Activo) VALUES (?,?,?,?,?,?)";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre()); ps.setDouble(2, p.getPorcentajeDescuento());
            ps.setString(3, p.getCodigoCupon()); ps.setTimestamp(4, new Timestamp(p.getFechaInicio().getTime()));
            ps.setTimestamp(5, new Timestamp(p.getFechaFin().getTime())); ps.setBoolean(6, p.isActivo());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean modificar(Promocion p) throws SQLException {
        String sql = "UPDATE Promociones SET Nombre=?, PorcentajeDescuento=?, CodigoCupon=?, FechaInicio=?, FechaFin=?, Activo=? WHERE IdPromocion=?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre()); ps.setDouble(2, p.getPorcentajeDescuento());
            ps.setString(3, p.getCodigoCupon()); ps.setTimestamp(4, new Timestamp(p.getFechaInicio().getTime()));
            ps.setTimestamp(5, new Timestamp(p.getFechaFin().getTime())); ps.setBoolean(6, p.isActivo());
            ps.setInt(7, p.getIdPromocion()); return ps.executeUpdate() > 0;
        }
    }
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Promociones WHERE IdPromocion=?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }
    public List<Promocion> listar() throws SQLException {
        List<Promocion> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Promociones")) {
            while (rs.next())
                lista.add(new Promocion(rs.getInt("IdPromocion"), rs.getString("Nombre"),
                    rs.getDouble("PorcentajeDescuento"), rs.getString("CodigoCupon"),
                    rs.getTimestamp("FechaInicio"), rs.getTimestamp("FechaFin"), rs.getBoolean("Activo")));
        }
        return lista;
    }
}
