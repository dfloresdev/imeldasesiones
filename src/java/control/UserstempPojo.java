/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Gris Gomez
 */
public class UserstempPojo {
    private Integer id;
    private String ap;
    private String am;
    private String nombre;
    private String correo;
    private String hash;
    private int activo;
    private int estado;
    private String passwordTemp;

    public UserstempPojo() {
    }

    public UserstempPojo(String ap, String am, String nombre, String correo, String hash, int activo, int estado, String passwordTemp) {
        this.ap = ap;
        this.am = am;
        this.nombre = nombre;
        this.correo = correo;
        this.hash = hash;
        this.activo = activo;
        this.estado = estado;
        this.passwordTemp = passwordTemp;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getPasswordTemp() {
        return passwordTemp;
    }

    public void setPasswordTemp(String passwordTemp) {
        this.passwordTemp = passwordTemp;
    }
    
    
}
