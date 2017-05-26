/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import control.UserstempFacade;
import control.UserstempPojo;
import control.UsuarioPojo;
import control.UsuariosFacade;
import control.ValidaEntradas;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    private Integer id;
    private String login;
    private String pwd; // este no deberia ser llenado
    private String mysh;
    private String email;
    private String hash;
    
    public static boolean activado = false;
    private UserstempFacade userstempFacade = new UserstempFacade();
    private UserstempPojo userstempPojo;

    UsuariosFacade userFacade = new UsuariosFacade();
    UsuarioPojo userPojo;

    @Inject
    private ValidaEntradas val;

    public LoginBean() {
    }

    public String getMysh() {
        return mysh;
    }

    public void setMysh(String mysh) {
        this.mysh = mysh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        int error = val.validaCadena(login);
        if (error == 0) {
            this.login = login;
        } else {
            this.login = null;
        }
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        int error = val.validaCadena(pwd);
        if (error == 0) {
            this.pwd = pwd;
        } else {
            this.login = null;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    

    public String getMensajeBajaCuate() {
        String msg = "Pepita Gonzalez";
        String msg1 = "Departamento de finanzas";

        String template = "<p><b>Seguro que desea eliminra el registro?</b></p>\n"
                + "<hr/>\n"
                + "<div style='font-size: 120%%>\n"
                + "%s\n"
                + "\n</div>"
                + "<hr/>\n"
                + "<p>%s</p>";

        String confirmDialogMessage = String.format(template, msg, msg1);
        return (confirmDialogMessage);

    }

    public void submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (mysh.isEmpty()) {

            System.out.println("Nombre de usuario recibido: " + login);
            System.out.println("Contraseña recibida: " + pwd);
            if ((login == null) || (pwd == null)) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los datos no son válidos", "Error"));
            } else {
                activado = activarUsuario(email, hash, pwd); 
                if (activado){
                    userPojo = userFacade.buscarUsuario(login, pwd);
                if (userPojo != null) {
                    context.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario hallado", "Error"));
                } else {
                    context.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no hallado", "Error"));
                }                    
                }                
            }
        }else 
            context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esto es un bot", "Error"));
    }
    
    public boolean activarUsuario(String email, String hash, String pwd){
        //if (activado = activarUsuario(email, hash, pwd))
        
        return true;
    }
}
