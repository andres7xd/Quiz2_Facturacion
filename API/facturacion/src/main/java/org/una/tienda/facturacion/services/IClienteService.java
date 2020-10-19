/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteConDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteConEmailException;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoException;

/**
 *
 * @author Andres
 */
public interface IClienteService {

    public Optional<ClienteDTO> findById(Long id);

    public ClienteDTO create(ClienteDTO clienteDTO)throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException;

    public void delete(Long id);

    public Optional<ClienteDTO> update(ClienteDTO clienteDTO, Long id);
}
