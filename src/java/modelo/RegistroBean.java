/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import control.UserstempFacade;
import control.UserstempPojo;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "registro")
@RequestScoped
public class RegistroBean {

    private String mySh;
    private String ap;
    private String am;
    private String nombre;
    private String email;
    private UserstempFacade userFacade = new UserstempFacade();
    private UserstempPojo userPojo;

    @Resource(name = "mail/micorreo")
    private javax.mail.Session mailSession;

    public RegistroBean() {
    }

    private String generaHash() {
        String cadena = null;
        Random rd = new Random();
        int numAleatorio = (int) (rd.nextDouble() * 100000);
        String numAlea = Integer.toString(numAleatorio);
        MessageDigest digest;
        byte[] hash;
        int l = 0;
        StringBuilder hexString = new StringBuilder();
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(numAlea.getBytes());
            hash = digest.digest();
            l = hash.length;
            for (int i = 0; i < l; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            cadena = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        };
        return cadena;
    }

    private String numAleatorio() {
        SecureRandom random = new SecureRandom();
        int numAleatorio = random.nextInt(500000) + 5000;
        String numAlea = Integer.toString(numAleatorio);
        return numAlea;
    }

    private String generaPwdTemp(String numAlea) {
        String cadena = null;
        MessageDigest digest;
        byte[] hash;
        int l;

        StringBuffer hexString = new StringBuffer();

        try {
            digest = MessageDigest.getInstance("SHA-256");
            try {
                hash = digest.digest(numAlea.getBytes("UTF-8"));
                l = hash.length;

                for (int i = 0; i < l; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
            } catch (UnsupportedEncodingException ex) {
            }
            cadena = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        };
        return cadena;
    }

    public String registar() {
        System.out.println("metodo de registro");
        FacesContext context = FacesContext.getCurrentInstance();
        String cadena = null;
        userPojo = userFacade.buscaUserstemp(email);

        if (userPojo == null) {
            String hash = generaHash();
            String numAlea = numAleatorio();
            String pwd = generaPwdTemp(numAlea);

            userPojo = new UserstempPojo(ap, am, nombre, email, hash, 0, 0, pwd);

            userFacade.crearUserstemp(userPojo);
            context.addMessage(cadena, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha creado el registro, se le enviara correo", "Registrado"));

            enviarCorreo(email, numAlea, hash);
        } else {
            context.addMessage(cadena, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Ya existe un usuario con ese correo, \n solicite olvidar contraseña en index", "error"));
        }
        return cadena;
    }

    private void enviarCorreo(String to, String pwd, String hash) {
        String subject = "Confirmacion de pre-registro de empleado";
        String msg = to;
        String msg1 = pwd;
        String msg2 = "http://localhost:8080/ProyectoFinal/faces/index.xhtml?email=" + to + "&hash=" + hash;
        String template
                = "Gracias por registrarte. \n"
                + "Tu cuenta ha sido creada, podras acceder a ella haciendo clic "
                + "en la siguiente liga de abajo y usando las siguientes credenciales: \n"
                + "________________________________________ \n"
                + "\n"
                + " usuario: " + "%s\n"
                + " contraseña: " + "%s\n"
                + ">________________________________________\n"
                + "\n"
                + "Por favor haz clic en la siguiente liga para activar y acceder a tu cuenta \n"
                + "%s.\n";

        String body = String.format(template, msg, msg1, msg2);
        MimeMessage message = new MimeMessage(mailSession);

        try {
            message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public String getMySh() {
        return mySh;
    }

    public void setMySh(String mySh) {
        this.mySh = mySh;
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
}
