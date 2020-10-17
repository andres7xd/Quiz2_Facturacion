/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.FacturaDTO;

/**
 *
 * @author Luis
 */
public interface IFacturaService {
    
    public Optional<FacturaDTO> findById(Long id);

    public FacturaDTO create(FacturaDTO facturaDTO);

    public void delete(Long id);

    public Optional<FacturaDTO> update(FacturaDTO facturaDTO, Long id);
}
