package org.example.dominio;
import java.util.Date;

public class Promocion {
    private int idPromocion;
    private String nombre;
    private double porcentajeDescuento;
    private String codigoCupon;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean activo;

    public Promocion() {}
    public Promocion(int idPromocion, String nombre, double porcentajeDescuento,
                     String codigoCupon, Date fechaInicio, Date fechaFin, boolean activo) {
        this.idPromocion = idPromocion; this.nombre = nombre;
        this.porcentajeDescuento = porcentajeDescuento; this.codigoCupon = codigoCupon;
        this.fechaInicio = fechaInicio; this.fechaFin = fechaFin; this.activo = activo;
    }
    public int getIdPromocion() { return idPromocion; }
    public void setIdPromocion(int idPromocion) { this.idPromocion = idPromocion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double v) { this.porcentajeDescuento = v; }
    public String getCodigoCupon() { return codigoCupon; }
    public void setCodigoCupon(String codigoCupon) { this.codigoCupon = codigoCupon; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public String getStrActivo() { return activo ? "Activo" : "Inactivo"; }
}
