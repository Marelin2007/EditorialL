package org.example.dominio;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String passwordHash;
    private boolean activo;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String email, String passwordHash, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.activo = activo;
    }

    public int getIdUsuario()            { return idUsuario; }
    public void setIdUsuario(int v)      { this.idUsuario = v; }
    public String getNombre()            { return nombre; }
    public void setNombre(String v)      { this.nombre = v; }
    public String getEmail()             { return email; }
    public void setEmail(String v)       { this.email = v; }
    public String getPasswordHash()      { return passwordHash; }
    public void setPasswordHash(String v){ this.passwordHash = v; }
    public boolean isActivo()            { return activo; }
    public void setActivo(boolean v)     { this.activo = v; }
}
