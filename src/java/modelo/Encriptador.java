/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Gris Gomez
 */
public class Encriptador {

    public String encripta(String p) {
        String llave = null;
        MessageDigest digest;
        byte[] hash;
        int l = 0;
        StringBuffer hexString = new StringBuffer();
        try {
            digest = MessageDigest.getInstance("SHA-256");
            try {
                hash = digest.digest(p.getBytes("UTF-8"));
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
            llave = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return llave;
    }
    
    public static void main(String[] args) {
        Encriptador obj = new Encriptador();
        String pwd = obj.encripta("gris123");
        System.out.println(pwd);
    }
}