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
 * @author Luis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Factura_DetallesDTO {

    private Long id;
    private double cantidad;
    private double descuento_final;
    private byte estado;
    private Date fecha_registro;
    private Date fecha_modificacion;
    private FacturaDTO ut_facturas;
//    private Factura ut_productos;
}
