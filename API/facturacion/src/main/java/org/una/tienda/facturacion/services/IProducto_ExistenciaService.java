/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.Producto_ExistenciaDTO;
import org.una.tienda.facturacion.exceptions.ProductoExistenciaConEstadoInactivoException;

/**
 *
 * @author rache
 */
public interface IProducto_ExistenciaService {

    public Optional<Producto_ExistenciaDTO> findById(Long id);

    public Producto_ExistenciaDTO create(Producto_ExistenciaDTO producto_existenciaDTO);

    public void delete(Long id);

    public Optional<Producto_ExistenciaDTO> update(Producto_ExistenciaDTO producto_existenciaDTO, Long id) throws ProductoExistenciaConEstadoInactivoException;
}
