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
import org.una.tienda.facturacion.dto.ProductoDTO;

/**
 *
 * @author rache
 */
@SpringBootTest
public class ProductoServiceImplementationTests {

    @Autowired
    private IProductoService productoService;

    ProductoDTO productoEjemplo;

    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoCorrectamente() {

        productoEjemplo = productoService.create(productoEjemplo);

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getIdproducto());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            assertEquals(productoEjemplo.getIdproducto(), producto.getIdproducto());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    public void sePuedeModificarUnProductoCorrectamente() {

        Optional<ProductoDTO> product = productoService.update(productoEjemplo, productoEjemplo.getIdproducto());

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getIdproducto());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            assertEquals(productoEjemplo.getIdproducto(), producto.getIdproducto());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeEliminarUnProductoCorrectamente() {

        productoEjemplo = productoService.create(productoEjemplo);

        productoService.delete(productoEjemplo.getIdproducto());

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getIdproducto());

        if (productoEncontrado != null) {
            fail("El producto no fue eliminado");
        } else {
            Assertions.assertTrue(true);
            productoEjemplo = null;
        }
    }

    @AfterEach
    public void tearDown() {
        if (productoEjemplo != null) {
            productoService.delete(productoEjemplo.getIdproducto());
            productoEjemplo = null;
        }

    }

}
