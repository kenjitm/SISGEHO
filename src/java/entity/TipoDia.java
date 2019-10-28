/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Raul A. Hernandez
 */
public enum TipoDia {
    
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIERCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SABADO"),
    DOMINGO("DOMINGO");
    
    private final String label;
    
    private TipoDia(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}
