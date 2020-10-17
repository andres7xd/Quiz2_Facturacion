/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.Producto_PrecioDTO;

/**
 *
 * @author Andres
 */
public interface IProductoPrecioService {
    
     public Optional<Producto_PrecioDTO> findById(Long id);

    public Producto_PrecioDTO create(Producto_PrecioDTO producto_precioDTO);

    public void delete(Long id);

    public Optional<Producto_PrecioDTO> update(Producto_PrecioDTO producto_precioDTO, Long id);
}
