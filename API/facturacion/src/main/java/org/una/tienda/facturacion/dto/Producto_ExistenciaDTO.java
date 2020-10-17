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
 * @author rache
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Producto_ExistenciaDTO {
    
    private Long id; 
    private double cantidad;
    private byte estado; 
    private Date fechaRegistro; 
    private Date fechaModificacion;
    private ProductoDTO ut_productos;

}
