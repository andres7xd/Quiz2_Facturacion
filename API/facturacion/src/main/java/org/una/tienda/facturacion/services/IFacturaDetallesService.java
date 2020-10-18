/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.Factura_DetallesDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author Luis
 */
public interface IFacturaDetallesService {
    
    public Optional<Factura_DetallesDTO> findById(Long id);

    public Factura_DetallesDTO create(Factura_DetallesDTO factura_DetallesDTO)throws ProductoConDescuentoMayorAlPermitidoException ;

    public void delete(Long id);

    public Optional<Factura_DetallesDTO> update(Factura_DetallesDTO factura_DetallesDTO, Long id);
}
