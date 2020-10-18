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
import org.una.tienda.facturacion.dto.Factura_DetallesDTO;

/**
 *
 * @author Luis
 */
@SpringBootTest
public class FacturaDetallesServiceImplementationTest {
    
    @Autowired
    private IFacturaDetallesService facturaDetallesService;

    Factura_DetallesDTO factura_DetallesEjemplo;
    
    @BeforeEach
    public void setup() {
        factura_DetallesEjemplo = new Factura_DetallesDTO() {
            {
                setCantidad(1);
                setDescuento_final(0.5);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaDetallesCorrectamente() {

        factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo);

        Optional<Factura_DetallesDTO> facturaDetallesEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());

        if (facturaDetallesEncontrado.isPresent()) {
            Factura_DetallesDTO facturaDetalles = facturaDetallesEncontrado.get();
            assertEquals(factura_DetallesEjemplo.getId(), facturaDetalles.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnaFacturaDetallesCorrectamente() {

        Optional<Factura_DetallesDTO> facturDetails = facturaDetallesService.update(factura_DetallesEjemplo, factura_DetallesEjemplo.getId());

        Optional<Factura_DetallesDTO> factura_DetallesEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());

        if (factura_DetallesEncontrado.isPresent()) {
            Factura_DetallesDTO factura_DetallesDTO = factura_DetallesEncontrado.get();
            assertEquals(factura_DetallesEjemplo.getId(), factura_DetallesDTO.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnaFacturaDetallesCorrectamente() {

        factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo);

        facturaDetallesService.delete(factura_DetallesEjemplo.getId());

        Optional<Factura_DetallesDTO> facturaEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());

        if (facturaEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            factura_DetallesEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (factura_DetallesEjemplo != null) {
            facturaDetallesService.delete(factura_DetallesEjemplo.getId());
            factura_DetallesEjemplo = null;
        }

    }
}
