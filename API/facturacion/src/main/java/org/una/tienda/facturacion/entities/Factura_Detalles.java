/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Luis
 */
@Entity

@Table(name = "ut_facturas_detalles")

@Data

@AllArgsConstructor

@NoArgsConstructor

@ToString
public class Factura_Detalles implements Serializable {
      
    @ManyToOne
    @JoinColumn(name = "facturas_id")
    private Factura ut_facturas;
    
    @ManyToOne
    @JoinColumn(name = "productos_id")
    private Factura ut_productos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cantidad")
    private double cantidad;

    @Column(name = "descuento_final")
    private double descuento_final;
    
    @Column(name = "estado")
    private byte estado;
    
    @Column(name = "fecha_registro", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fecha_registro;

    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.DATE)
    private Date fecha_modificacion;
    
    @PrePersist

    public void prePersist() {

        estado = 1;

        fecha_registro = new Date();

        fecha_modificacion = new Date();

    }

    @PreUpdate

    public void preUpdate() {

        fecha_modificacion = new Date();

    }
    
    
    
    
}
