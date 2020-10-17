/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

@Table(name = "ut_facturas")

@Data

@AllArgsConstructor

@NoArgsConstructor

@ToString
public class Factura {
    
//    @ManyToOne
//    @JoinColumn(name = "cliente_id")
//    private Cliente cliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ut_facturas") 
    private List<Factura_Detalles> factura_Detalles = new ArrayList<>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "caja")
    private int caja;
    
    @Column(name = "descuento_general")
    private double descuento_general;
    
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
