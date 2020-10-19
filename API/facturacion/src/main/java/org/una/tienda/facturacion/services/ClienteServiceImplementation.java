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
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.exceptions.ClienteConDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteConEmailException;
import org.una.tienda.facturacion.exceptions.ClienteConEstadoInactivoException;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoException;
import org.una.tienda.facturacion.repositories.IClienteRepository;
import org.una.tienda.facturacion.util.MapperUtils;

/**
 *
 * @author Andres
 */
@Service
public class ClienteServiceImplementation implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    private Optional<ClienteDTO> oneToDto(Optional<Cliente> one) {
        if (one.isPresent()) {
            ClienteDTO clienteDTO = MapperUtils.DtoFromEntity(one.get(), ClienteDTO.class);
            return Optional.ofNullable(clienteDTO);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findById(Long id) {

        return oneToDto(clienteRepository.findById(id));
    }

    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO ClienteDTO) throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {

        if (ClienteDTO.getTelefono() == null){
            throw new ClienteConTelefonoException("No se puede crear");
        }
        if(ClienteDTO.getEmail() == null) {
            throw new ClienteConEmailException("No se puede crear");
        }
        if(ClienteDTO.getDireccion() == null) {
            throw new ClienteConDireccionException("No se puede crear");
        }
        Cliente cliente = MapperUtils.EntityFromDto(ClienteDTO, Cliente.class);
        cliente = clienteRepository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, ClienteDTO.class);
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> update(ClienteDTO clienteDTO, Long id) throws ClienteConEstadoInactivoException{ 
       
        if (clienteRepository.findById(id).isPresent()) {
            if(clienteDTO.isEstado()==false){
            throw new ClienteConEstadoInactivoException("No se puede modificar");
        }
            Cliente cliente = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
            cliente = clienteRepository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, ClienteDTO.class));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

}
