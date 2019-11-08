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
public enum TipoRol {
    ROLE_ADMIN("ADMINISTRADOR"),
    ROLE_DOCENTE("DOCENTE"),
    ROLE_COORDINACION("COORDINACIÓN ACADÉMICA"),
    ROLE_DIRECTOR("DIRECTOR DE PROGRAMA"),
    ROLE_DECANO("DECANO"),
    ROLE_USUARIO("USUARIO");

    private final String label;

    private TipoRol(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
