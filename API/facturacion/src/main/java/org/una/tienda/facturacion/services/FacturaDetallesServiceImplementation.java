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
import org.una.tienda.facturacion.dto.Factura_DetallesDTO;
import org.una.tienda.facturacion.dto.Producto_PrecioDTO;
import org.una.tienda.facturacion.entities.Factura_Detalles;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.repositories.IFacturaDetallesRepository;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author Luis
 */
@Service
public class FacturaDetallesServiceImplementation implements IFacturaDetallesService {

    @Autowired
    private IFacturaDetallesRepository facturaDetallesRepository;

    @Autowired
    private IProductoPrecioService productoPrecioService;

    private Optional<Factura_DetallesDTO> oneToDto(Optional<Factura_Detalles> one) {
        if (one.isPresent()) {
            Factura_DetallesDTO factura_DetallesDTO = MapperUtils.DtoFromEntity(one.get(), Factura_DetallesDTO.class);
            return Optional.ofNullable(factura_DetallesDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Factura_DetallesDTO> findById(Long id) {
        return oneToDto(facturaDetallesRepository.findById(id));

    }

    @Override
    @Transactional
    public Factura_DetallesDTO create(Factura_DetallesDTO facturaDetalle) throws ProductoConDescuentoMayorAlPermitidoException {

        Optional<Producto_PrecioDTO> productoPrecio = productoPrecioService.findById(facturaDetalle.getUt_productos().getIdproducto());

        if (productoPrecio.isEmpty()) {
            return null;
        }
        if (facturaDetalle.getDescuento_final() > productoPrecio.get().getDescuento_maximo()) {
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un producto con un descuento mayor al permitido");
        }
        
        Factura_Detalles usuario = MapperUtils.EntityFromDto(facturaDetalle, Factura_Detalles.class);
        usuario = facturaDetallesRepository.save(usuario);
        System.out.println(usuario);
        return MapperUtils.DtoFromEntity(usuario, Factura_DetallesDTO.class);
    }

    @Override
    @Transactional
    public Optional<Factura_DetallesDTO> update(Factura_DetallesDTO factura_DetallesDTO, Long id) {
        if (facturaDetallesRepository.findById(id).isPresent()) {
            Factura_Detalles factura_Detalles = MapperUtils.EntityFromDto(factura_DetallesDTO, Factura_Detalles.class);
            factura_Detalles = facturaDetallesRepository.save(factura_Detalles);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(factura_Detalles, Factura_DetallesDTO.class));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        facturaDetallesRepository.deleteById(id);
    }
}
