/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "alta")
@RequestScoped
public class AltaBean {

    
    public AltaBean() {
    }
    
    public void validaPagina () throws IOException{
        //HttpSession 
    }
    
}
