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
public enum TipoFrecuencia {

    
    SEMANAL("SEMANAL"),
    QUINCENAL("QUINCENAL");

    private final String label;

    private TipoFrecuencia(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
