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
import org.una.tienda.facturacion.dto.Producto_ExistenciaDTO;

/**
 *
 * @author rache
 */
@SpringBootTest
public class Producto_ExistenciaServiceImplementationTests {
    
     @Autowired
    private IProducto_ExistenciaService producto_existenciaService;

    Producto_ExistenciaDTO producto_existenciaEjemplo;
    
    @BeforeEach
    public void setup() {
        producto_existenciaEjemplo = new Producto_ExistenciaDTO() {
            {
                setCantidad(2);
               
            }
        };
    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() {

        producto_existenciaEjemplo = producto_existenciaService.create(producto_existenciaEjemplo);

        Optional<Producto_ExistenciaDTO> producto_existenciaEncontrado = producto_existenciaService.findById(producto_existenciaEjemplo.getId());

        if (producto_existenciaEncontrado.isPresent()) {
            Producto_ExistenciaDTO producto_existencia = producto_existenciaEncontrado.get();
            assertEquals(producto_existenciaEjemplo.getId(), producto_existencia.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnClienteCorrectamente() {

        Optional<Producto_ExistenciaDTO> Product_existence = producto_existenciaService.update(producto_existenciaEjemplo, producto_existenciaEjemplo.getId());

        Optional<Producto_ExistenciaDTO> producto_existenciaEncontrado = producto_existenciaService.findById(producto_existenciaEjemplo.getId());

        if (producto_existenciaEncontrado.isPresent()) {
            Producto_ExistenciaDTO producto_existencia = producto_existenciaEncontrado.get();
            assertEquals(producto_existenciaEjemplo.getId(), producto_existencia.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() {

        producto_existenciaEjemplo = producto_existenciaService.create(producto_existenciaEjemplo);

        producto_existenciaService.delete(producto_existenciaEjemplo.getId());

        Optional<Producto_ExistenciaDTO> producto_existenciaEncontrado = producto_existenciaService.findById(producto_existenciaEjemplo.getId());

        if (producto_existenciaEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            producto_existenciaEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (producto_existenciaEjemplo != null) {
            producto_existenciaService.delete(producto_existenciaEjemplo.getId());
            producto_existenciaEjemplo = null;
        }

    }
}
