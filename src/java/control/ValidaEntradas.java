/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "validaEntradas")
@RequestScoped
public class ValidaEntradas {

    String patronNoValidos;
    
    public int validaCadena(String cadena){
        int error = 0;
        char c;
        if(cadena.isEmpty()){
            error = 9;
        }else {
            for (int i = 0; i < cadena.length(); i++) {
                c = cadena.charAt(i);
            }
        }
        return error;
    }
        
    public ValidaEntradas() {        
    }
    
}
