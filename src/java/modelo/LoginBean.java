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
import java.io.IOException;
import java.security.CryptoPrimitive;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    private boolean validado = false;

    UsuariosFacade userFacade = new UsuariosFacade();
    UsuarioPojo userPojo;

    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    HttpSession session;

    ////////////////////// De donde saco esto //////////////////////
//    @Inject
//    private CryptoLibrary crypto;
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

    public static boolean isActivado() {
        return activado;
    }

    public static void setActivado(boolean activado) {
        LoginBean.activado = activado;
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

    public void ValidaUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("Nombre de usuario recibido" + login);

        if (login != null)
            ; else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "lol", "mensaje"));
        }
        System.out.println("contraseña recibida " + pwd);
    }

    public String Registro() {
        session = (HttpSession) ec.getSession(false);
        session.setAttribute("preRegistro", true);
        sesionInactiva(180);
        String cadena = "/Nuevo/Registro?faces-redirect=true";
        return cadena;
    }

    private void sesionInactiva(int tiempoActivo) {
        session.setMaxInactiveInterval(tiempoActivo);
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
                if (activado) {
                    userPojo = userFacade.buscarUsuario(login, pwd);
                    if (userPojo != null) {

                        //////////////////////// nuevo codigo ////////////////////////
                        activado = false;

                        cambiaSesion();
                        validado = true;
                        session = (HttpSession) ec.getSession(false);
                        session.setAttribute("validado", validado);
                        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
                        System.out.println("-----------------------");
                        System.out.println("corroborando login y rol");

                        try {
                            System.out.println("-----------------");
                            System.out.println("solicito el metodo login");
                            request.login(login, pwd);
                            System.out.println("reconocio el login");
                            System.out.println("es jefe: " + request.isUserInRole("Admin"));
                            if (request.isUserInRole("Admin")) {
                                try {
                                    System.out.println("tratando de irme a la pagina");
                                    ec.redirect(ec.getRequestContextPath() + "/faces/Admin/BienvenidoAdmin.xhtml");
                                } catch (IOException es) {

                                }
                            } else {
                                if (request.isUserInRole("Empleado")) {
                                    try {
                                        ec.redirect(ec.getRequestContextPath() + "/faces/Empleado/BienvenidoE.xhtml");
                                    } catch (IOException ex) {

                                    }
                                } else {
                                    if (request.isUserInRole("Nuevo")) {
                                        try {
                                            ec.redirect(ec.getRequestContextPath() + "/faces/Nuevo");
                                        } catch (IOException ex) {

                                        }
                                    }
                                    else
                                    {
                                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Problemas con el usuario", "titulo"));
                                    }
                                }
                            }
                        } catch (ServletException se) {
                            
                            System.out.println("Se encontro en facade al usuario pero ....");
                            System.out.println("Ocurrio algo: " + se.getMessage());
                            context.addMessage(null, 
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, "algo del usuario", "titulo"));

                        }

                        ////////////////////////////////////////////////////////////////////////
//                        context.addMessage(null,
//                                new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario hallado", "Error"));
                    } else {
                        context.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no hallado", "Error"));
                    }
                }
            }
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esto es un bot", "Error"));
        }
    }

    public boolean activarUsuario(String email, String hash, String pwd) {
        
        System.out.println("Email: " + email);
        System.out.println("Hash: " + hash);

        if ((email == null) && (hash == null)) {
            return true;
        }
        if ((email != null) && (hash != null)) {
            userstempPojo = userstempFacade.buscaUserstemp(email);
            if (userstempPojo != null) {
                Encriptador e = new Encriptador();
                String clave = e.encripta(pwd);
                
                if(!clave.contentEquals(userstempPojo.getPasswordTemp()))
                {
                    return true;
                }
                else if(!hash.contentEquals(userstempPojo.getHash()))
                {
                    return true;
                }
                else
                {
                    userstempPojo.setActivo(1);
                    userstempFacade.actualizaUserstempActivo(userstempPojo);
                    
                    String ap = userstempPojo.getAp();
                    String am = userstempPojo.getAm();
                    String nom = userstempPojo.getNombre();
                    String co = userstempPojo.getCorreo();
//
//                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//                    String ap = userstempPojo.getAp();
//                    String am = userstempPojo.getAm();
//                    String nom = userstempPojo.getNombre();
//                    String co = userstempPojo.getCorreo();
//
//                    try {
//                        String parametros = "?ap=" + ap + "&am=" + am + "&nom=" + nom + "&co=" + co;
//                        ec.redirect(ec.getRequestContextPath() + "/faces/alta.xhtml" + parametros);
//                        ec.redirect(ec.getRequestContextPath() + "/faces/registro.xhtml" + parametros);
//
//                    } catch (IOException e) {
//
//                    }
                    
                    try
                    {
                        cambiaSesion();
                        session.setAttribute("activado", true);
                        System.out.println("Valor de activado: " + session.getAttribute("activado"));
                        session.setAttribute("ap", ap);
                        session.setAttribute("am", am);
                        session.setAttribute("nom", nom);
                        session.setAttribute("co", co);
                        
                        System.out.println("me voy a alta");
//                        ec.redirect(ec.getRequestContextPath() + "/faces/Nuevo/Alta.xhtml");
                        ec.redirect(ec.getRequestContextPath() + "/faces/Nuevo/Registro.xhtml");
                    }catch(IOException ex)
                    {
                        
                    }
                    
                }
            }else
            {
                return true;
            }
        }

        return false;
    }
    
    private void cambiaSesion()
    {
        session = (HttpSession) ec.getSession(false);
        
        System.out.println("Sesion nueva" + session.isNew());
        System.out.println("id sesion: " + session.getId());
        session.invalidate();
        session = (HttpSession) ec.getSession(true);
        
        System.out.println("Sesion nueva: " + session.isNew());
        System.out.println("id sesion en login: " + session.getId());
    }
    
    public void Continuar()
    {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Bienvenido de regreso",
                "continua con tu trabajo"));
    }
    
    public void logout() throws IOException
    {
        FacesContext.getCurrentInstance().addMessage(
                null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Has sido desconectado!",
                "Debes iniciar sesión, nuevamente"));
        ec.getFlash().setKeepMessages(true);
        
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
    }
    
}
