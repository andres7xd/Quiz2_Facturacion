/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Andres
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoDTO {
   
   private Long idproducto;
   private String descripcion;
   private boolean estado;
   private Date fecha_Modificacion;
   private Date fecha_Registro;
   private double impuesto;
   
}
