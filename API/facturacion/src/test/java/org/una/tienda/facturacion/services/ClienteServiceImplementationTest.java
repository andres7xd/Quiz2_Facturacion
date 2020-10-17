/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ClienteDTO;

/**
 *
 * @author Luis
 */
@SpringBootTest
public class ClienteServiceImplementationTest {
    
    @Autowired
    private IClienteService clienteService;

    ClienteDTO clienteEjemplo;
    
    @BeforeEach
    public void setup() {
        clienteEjemplo = new ClienteDTO() {
            {
                setDireccion("Pérez");
                setEmail("@@@");
                setNombre("Luis");
                setTelefono("123"); 
            }
        };
    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() {

        clienteEjemplo = clienteService.create(clienteEjemplo);

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getIdclientes());

        if (clienteEncontrado.isPresent()) {
            ClienteDTO cliente = clienteEncontrado.get();
            assertEquals(clienteEjemplo.getIdclientes(), cliente.getIdclientes());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnClienteCorrectamente() {

        Optional<ClienteDTO> client = clienteService.update(clienteEjemplo, clienteEjemplo.getIdclientes());

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getIdclientes());

        if (clienteEncontrado.isPresent()) {
            ClienteDTO cliente = clienteEncontrado.get();
            assertEquals(clienteEjemplo.getIdclientes(), cliente.getIdclientes());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() {

        clienteEjemplo = clienteService.create(clienteEjemplo);

        clienteService.delete(clienteEjemplo.getIdclientes());

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getIdclientes());

        if (clienteEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            clienteEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (clienteEjemplo != null) {
            clienteService.delete(clienteEjemplo.getIdclientes());
            clienteEjemplo = null;
        }

    }
}
