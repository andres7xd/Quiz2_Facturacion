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
import org.una.tienda.facturacion.dto.FacturaDTO;

/**
 *
 * @author Luis
 */
@SpringBootTest
public class FacturaServiceImplementationTest {
    
    @Autowired
    private IFacturaService facturaService;

    FacturaDTO facturaEjemplo;
    
    @BeforeEach
    public void setup() {
        facturaEjemplo = new FacturaDTO() {
            {
                setCaja(1);
                setDescuento_general(0.5);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamente() {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnaFacturaCorrectamente() {

        Optional<FacturaDTO> factur = facturaService.update(facturaEjemplo, facturaEjemplo.getId());

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnaFacturaCorrectamente() {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaService.delete(facturaEjemplo.getId());

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            facturaEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (facturaEjemplo != null) {
            facturaService.delete(facturaEjemplo.getId());
            facturaEjemplo = null;
        }

    }
}
