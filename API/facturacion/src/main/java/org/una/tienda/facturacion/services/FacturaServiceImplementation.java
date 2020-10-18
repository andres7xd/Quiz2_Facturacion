/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.dto.Factura_DetallesDTO;
import org.una.tienda.facturacion.entities.Factura;
import org.una.tienda.facturacion.entities.Factura_Detalles;
import org.una.tienda.facturacion.repositories.IFacturaRepository;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author Luis
 */
@Service
public class FacturaServiceImplementation implements IFacturaService{
    
    @Autowired
    private IFacturaRepository facturaRepository;

    private Optional<FacturaDTO> oneToDto(Optional<Factura> one) {
        if (one.isPresent()) {
            FacturaDTO facturaDTO = MapperUtils.DtoFromEntity(one.get(),   FacturaDTO.class);
            return Optional.ofNullable(facturaDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDTO> findById(Long id) {
        return oneToDto(facturaRepository.findById(id));

    }

    @Override
    @Transactional
    public FacturaDTO create(FacturaDTO facturaDTO) {
        Factura factura = MapperUtils.EntityFromDto(facturaDTO, Factura.class);
        factura = facturaRepository.save(factura);
        return MapperUtils.DtoFromEntity(factura, FacturaDTO.class);
    }

    
    @Override
    @Transactional
    public Optional<FacturaDTO> update(FacturaDTO facturaDTO, Long id) {
        if (facturaRepository.findById(id).isPresent()) {
            Factura factura = MapperUtils.EntityFromDto(facturaDTO, Factura.class);
            factura = facturaRepository.save(factura);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(factura, FacturaDTO.class));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }
}
