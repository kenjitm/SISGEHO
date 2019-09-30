/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author KTANAKA
 */
public class SessionUtils implements Serializable{
    public void add(String key, Object value){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
    public void get(String key){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
}
