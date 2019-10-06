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
public enum TipoId {
    CC("Cédula Ciudadanía"),
    CE("Cédula Extranjería"),
    NN("Sin Identificación");

    private final String label;

    private TipoId(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}
