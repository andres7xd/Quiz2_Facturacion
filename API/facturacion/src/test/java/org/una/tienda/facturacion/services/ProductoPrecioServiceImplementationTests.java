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
import org.una.tienda.facturacion.dto.Producto_PrecioDTO;

/**
 *
 * @author Andres
 */
@SpringBootTest
public class ProductoPrecioServiceImplementationTests {
    
    @Autowired
    private IProductoPrecioService producto_precioService;

    Producto_PrecioDTO producto_precioEjemplo;
    
    @BeforeEach
    public void setup() {
        producto_precioEjemplo = new Producto_PrecioDTO() {
            {
                setDescuento_maximo(0.2);
                setDescuento_promocional(0.23);
                setPrecio_colones(300);
               
            }
        };
    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() {

        producto_precioEjemplo = producto_precioService.create(producto_precioEjemplo);

        Optional<Producto_PrecioDTO> producto_precioEncontrado = producto_precioService.findById(producto_precioEjemplo.getId());

        if (producto_precioEncontrado.isPresent()) {
            Producto_PrecioDTO producto_existencia = producto_precioEncontrado.get();
            assertEquals(producto_precioEjemplo.getId(), producto_existencia.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnClienteCorrectamente() {

        Optional<Producto_PrecioDTO> Product_price = producto_precioService.update(producto_precioEjemplo, producto_precioEjemplo.getId());

        Optional<Producto_PrecioDTO> producto_precioEncontrado = producto_precioService.findById(producto_precioEjemplo.getId());

        if (producto_precioEncontrado.isPresent()) {
            Producto_PrecioDTO producto_precio = producto_precioEncontrado.get();
            assertEquals(producto_precioEjemplo.getId(), producto_precio.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnClienteCorrectamente() {

        producto_precioEjemplo = producto_precioService.create(producto_precioEjemplo);

        producto_precioService.delete(producto_precioEjemplo.getId());

        Optional<Producto_PrecioDTO> producto_precioEncontrado = producto_precioService.findById(producto_precioEjemplo.getId());

        if (producto_precioEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            producto_precioEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (producto_precioEjemplo != null) {
            producto_precioService.delete(producto_precioEjemplo.getId());
            producto_precioEjemplo = null;
        }

    }
}
