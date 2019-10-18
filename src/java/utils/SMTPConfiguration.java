/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Raul A. Hernandez
 */
public final class SMTPConfiguration {
    
    public static final String SMTP_SERVER = "smtp.gmail.com";
    public static final String EMAIL_FROM = "sisgeho.no.replay@gmail.com";
    public static final String PASSWORD = "jhktzjhrarrlmjmv";
    public static final String EMAIL_SUBJECT = "Recuperación de contraseña. No contestar!";
    public static final String REAL_PATH = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
    
}
