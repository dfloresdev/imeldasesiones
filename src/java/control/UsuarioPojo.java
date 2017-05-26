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
public class UsuarioPojo {
    private Integer uid;
    private String unombre;
    private String ucontrasenia;

    public UsuarioPojo() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUnombre() {
        return unombre;
    }

    public void setUnombre(String unombre) {
        this.unombre = unombre;
    }

    public String getUcontrasenia() {
        return ucontrasenia;
    }

    public void setUcontrasenia(String ucontrasenia) {
        this.ucontrasenia = ucontrasenia;
    }    
}
