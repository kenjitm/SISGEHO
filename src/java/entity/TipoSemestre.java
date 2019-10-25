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
public enum TipoSemestre {
    
    PRIMERO(1),
    SEGUNDO(2),
    TERCERO(3),
    CUARTO(4),
    QUINTO(5),
    SEXTO(6),
    SEPTIMO(7),
    OCTAVO(8),
    NOVENO(9),
    DECIMO(10);
    
    private Integer label;
    
    private TipoSemestre(Integer label) {
        this.label = label;
    }

    public Integer getLabel() {
        return label;
    }
    
}
