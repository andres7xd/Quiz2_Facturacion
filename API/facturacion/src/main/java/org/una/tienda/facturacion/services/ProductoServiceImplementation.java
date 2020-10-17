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
import org.una.tienda.facturacion.dto.ProductoDTO;
import org.una.tienda.facturacion.entities.Producto;
import org.una.tienda.facturacion.repositories.IProductoRepository;
import org.una.tienda.facturacion.util.Convertir;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author AndresI
 */
@Service
public class ProductoServiceImplementation implements IProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    private Optional<ProductoDTO> oneToDto(Optional<Producto> one) {
        if (one.isPresent()) {
            ProductoDTO ProductoDTO = MapperUtils.DtoFromEntity(one.get(),   ProductoDTO.class);
            return Optional.ofNullable(ProductoDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findById(Long id) {
        return oneToDto(productoRepository.findById(id));

    }

    @Override
    @Transactional
    public ProductoDTO create(ProductoDTO ProductoDTO) {
        Producto usuario = MapperUtils.EntityFromDto(ProductoDTO, Producto.class);
        usuario = productoRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, ProductoDTO.class);
    }

   @Override
    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

}
