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
public class Producto_PrecioDTO {

    private Long id;
    private double descuento_maximo;
    private double descuento_promocional;
    private double precio_colones;
    private byte estado;
    private Date fechaRegistro;
    private Date fechaModificacion;
    //private ProductoDTO producto;

}
