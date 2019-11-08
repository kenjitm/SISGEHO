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
public enum TipoRecurso {

    SALON("SALÓN"),
    SALA("SALA"),
    AUDITORIO("AUDITORIO"),
    LABORATORIO_FISICA("LABORATORIO DE FÍSICA"),
    LABORATORIO_ELECTRONICA("LABORATORIO DE ELECTRÓNICA"),
    LABORATORIO("LABORATORIO");

    private final String label;

    private TipoRecurso(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
