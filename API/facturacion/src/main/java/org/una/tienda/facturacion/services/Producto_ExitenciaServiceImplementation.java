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
import org.una.tienda.facturacion.dto.Producto_ExistenciaDTO;
import org.una.tienda.facturacion.entities.Producto_Existencia;
import org.una.tienda.facturacion.exceptions.ProductoExistenciaConEstadoInactivoException;
import org.una.tienda.facturacion.repositories.IProducto_ExistenciaRepository;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author rache
 */
@Service
public class Producto_ExitenciaServiceImplementation implements IProducto_ExistenciaService{
    
    @Autowired
    private IProducto_ExistenciaRepository producto_existenciaRepository;

    private Optional<Producto_ExistenciaDTO> oneToDto(Optional<Producto_Existencia> one) {
        if (one.isPresent()) {
            Producto_ExistenciaDTO clienteDTO = MapperUtils.DtoFromEntity(one.get(),   Producto_ExistenciaDTO.class);
            return Optional.ofNullable(clienteDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto_ExistenciaDTO> findById(Long id) {
        return oneToDto(producto_existenciaRepository.findById(id));

    }

    @Override
    @Transactional
    public Producto_ExistenciaDTO create(Producto_ExistenciaDTO Producto_ExistenciaDTO) {
        
        Producto_Existencia cliente = MapperUtils.EntityFromDto(Producto_ExistenciaDTO, Producto_Existencia.class);
        cliente = producto_existenciaRepository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, Producto_ExistenciaDTO.class);
    }
    
    @Override
    @Transactional
    public Optional<Producto_ExistenciaDTO> update(Producto_ExistenciaDTO productoexistenciaDTO, Long id) throws ProductoExistenciaConEstadoInactivoException {
        if (producto_existenciaRepository.findById(id).isPresent()) {
            
               if(productoexistenciaDTO.isEstado()== false){
            throw new ProductoExistenciaConEstadoInactivoException("No se puede modificar");
        }
            Producto_Existencia cliente = MapperUtils.EntityFromDto(productoexistenciaDTO, Producto_Existencia.class);
            cliente = producto_existenciaRepository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, Producto_ExistenciaDTO.class));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        producto_existenciaRepository.deleteById(id);
    }

}
