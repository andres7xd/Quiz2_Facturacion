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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dto.ClienteDTO;
import org.una.tienda.facturacion.exceptions.ClienteConDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteConEmailException;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoException;

/**
 *
 * @author Luis
 */
@SpringBootTest
public class ClienteServiceImplementationTest {

    @Autowired
    private IClienteService clienteService;

    ClienteDTO clienteEjemplo;

    ClienteDTO clientePrueba;

    ClienteDTO clientePrueba2;

    ClienteDTO clientePrueba3;

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

    private void initDataClienteConTelefono() {
        clientePrueba = new ClienteDTO() {
            {
                setDireccion("Pérez");
                setEmail("@@@");
                setNombre("Luis");
            }
        };

    }

    private void initDataClienteConDirección() {
        clientePrueba2 = new ClienteDTO() {
            {
                setTelefono("123");
                setEmail("@@@");
                setNombre("Luis");
            }
        };
    }

    private void initDataClienteConEmail() {
        clientePrueba3 = new ClienteDTO() {
            {
                setDireccion("Pérez");
                setNombre("Luis");
                setTelefono("123");
            }
        };

    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {

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
    public void seEvitaCrearUnClienteSinTelefono() {

        initDataClienteConTelefono();

        assertThrows(ClienteConTelefonoException.class,
                () -> {
                    clienteService.create(clientePrueba);
                }
        );
    }

    @Test
    public void seEvitaCrearUnClienteSinDireccion() {

        initDataClienteConDirección();

        assertThrows(ClienteConDireccionException.class,
                () -> {
                    clienteService.create(clientePrueba2);
                }
        );
    }

    @Test
    public void seEvitaCrearUnClienteSinEmail() {

        initDataClienteConEmail();

        assertThrows(ClienteConEmailException.class,
                () -> {
                    clienteService.create(clientePrueba3);
                }
        );
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {

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
            if (clienteEjemplo.getIdclientes() != null) {
                clienteService.delete(clienteEjemplo.getIdclientes());
            }
            clienteEjemplo = null;
        }

    }
}
