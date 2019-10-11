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
    
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado"),
    DOMINGO("Domingo");
    
    private final String label;
    
    private TipoDia(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}
