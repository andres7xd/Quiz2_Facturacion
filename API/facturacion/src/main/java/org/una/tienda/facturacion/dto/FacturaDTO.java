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
public class FacturaDTO {
    
    private Long id;
    private int caja;
    private double descuento_general;
    private byte estado;
    private Date fecha_registro;
    private Date fecha_modificacion;
    private ClienteDTO ut_clientes;
}
