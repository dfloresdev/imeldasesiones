/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "alta")
@RequestScoped
public class AltaBean {

    String ap;
    String am;
    String nombre;
    String email;
    
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    HttpSession session;
    public AltaBean() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    public void validaPagina () throws IOException{
        //HttpSession 
        
        session = (HttpSession) ec.getSession(false);
        
        ////esto es para abir directamente con el url en la pestaña del navegador
        if(session.getAttribute("activado") == null)
        {
            System.out.println("te voy a sacar por que no estas activado");
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        }
        
        //sin embargo el valor guardado en el objeto session se sigue conservando
        //ya que session sigue siendo la misma
        
        if(!(boolean)session.getAttribute("activado"))
        {
            
        }
        else
        {
            this.ap = (String) session.getAttribute("ap");
            this.am = (String) session.getAttribute("am");
            this.nombre = (String) session.getAttribute("nom");
            this.email = (String) session.getAttribute("co");
        }
        
    }
    
    
    
    
}
