/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.entities;

import java.io.Serializable;
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
 * @author Andres
 */
@Entity
@Table(name = "ut_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Producto implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ut_productos")
    private List<Producto_Existencia> producto_existencia = new ArrayList<>();
    
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "ut_productos")
    private List<Producto_Precio> producto_precio = new ArrayList<>();
     
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ut_productos")
    private List<Factura_Detalles> factura_detalle = new ArrayList<>();
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idproducto;
    
    @Column(name = "descripcion", length = 100)
    private String descripcion;
    
    @Column (name = "estado")
    private byte estado;
   
    @Column(name = "fecha_registro", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fecha_registro;
    
    @Column(name = "fecha_modificacion", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fecha_modificacion;
    
     @Column(name = "impuesto")
    private double impuesto;
    
    private static final long serialVersionUID = 1L;
    @PrePersist

    public void prePersist() {

        estado = 1;
        fecha_registro = new Date();
        fecha_modificacion = new Date();

    }
    
   
}
