/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Setter;

/**
 *
 * @author rache
 */
public class Producto_Existencia {
//    @ManyToOne
//    @JoinColumn(name = "Producto_id")
//    private Producto producto;
    
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    
    @Column(name = "cantidad")

    private double cantidad;

    @Column

    private byte estado;

    @Column(name = "fecha_registro", updatable = false)

    @Temporal(TemporalType.DATE)

    @Setter(AccessLevel.NONE)

    private Date fechaRegistro;

    @Column(name = "fecha_modificacion")

    @Setter(AccessLevel.NONE)

    @Temporal(TemporalType.DATE)

    private Date fechaModificacion;

    private static final long serialVersionUID = 1L;

    @PrePersist

    public void prePersist() {

        estado = 1;
        
        fechaRegistro = new Date();

        fechaModificacion = new Date();

    }

    @PreUpdate

    public void preUpdate() {

        fechaModificacion = new Date();

    }

}
