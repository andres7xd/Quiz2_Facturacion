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
import org.una.tienda.facturacion.dto.Producto_PrecioDTO;
import org.una.tienda.facturacion.entities.Producto_Precio;
import org.una.tienda.facturacion.repositories.IProductoPrecioRepository;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author Andres
 */
@Service
public class ProductoPrecioServiceImplementation implements IProductoPrecioService {
    
      @Autowired
    private IProductoPrecioRepository producto_precio_repository;

    private Optional<Producto_PrecioDTO> oneToDto(Optional<Producto_Precio> one) {
        if (one.isPresent()) {
            Producto_PrecioDTO clienteDTO = MapperUtils.DtoFromEntity(one.get(),   Producto_PrecioDTO.class);
            return Optional.ofNullable(clienteDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto_PrecioDTO> findById(Long id) {
        return oneToDto(producto_precio_repository.findById(id));

    }

    @Override
    @Transactional
    public Producto_PrecioDTO create(Producto_PrecioDTO Producto_PrecioDTO) {
        Producto_Precio cliente = MapperUtils.EntityFromDto(Producto_PrecioDTO, Producto_Precio.class);
        cliente = producto_precio_repository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, Producto_PrecioDTO.class);
    }
    
    @Override
    @Transactional
    public Optional<Producto_PrecioDTO> update(Producto_PrecioDTO clienteDTO, Long id) {
        if (producto_precio_repository.findById(id).isPresent()) {
            Producto_Precio cliente = MapperUtils.EntityFromDto(clienteDTO, Producto_Precio.class);
            cliente = producto_precio_repository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, Producto_PrecioDTO.class));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        producto_precio_repository.deleteById(id);
    }
}
