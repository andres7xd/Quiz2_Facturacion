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
import org.una.tienda.facturacion.dto.FacturaDTO;
import org.una.tienda.facturacion.dto.Factura_DetallesDTO;
import org.una.tienda.facturacion.dto.ProductoDTO;
import org.una.tienda.facturacion.dto.Producto_ExistenciaDTO;
import org.una.tienda.facturacion.dto.Producto_PrecioDTO;
import org.una.tienda.facturacion.exceptions.ClienteConDireccionException;
import org.una.tienda.facturacion.exceptions.ClienteConEmailException;
import org.una.tienda.facturacion.exceptions.ClienteConTelefonoException;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author Luis
 */
@SpringBootTest
public class FacturaDetallesServiceImplementationTest {

    @Autowired
    private IFacturaDetallesService facturaDetallesService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IProducto_ExistenciaService productoExistenciaService;

    @Autowired
    private IProductoPrecioService productoPrecioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IFacturaService facturaService;

    Factura_DetallesDTO factura_DetallesEjemplo;

    Factura_DetallesDTO facturaDetallePruebaConExtraDescuento;

    ProductoDTO productoPrueba;

    ProductoDTO productoEjemplo;

    Producto_ExistenciaDTO productoExistenciaPrueba;

    Producto_ExistenciaDTO productoExistenciaEjemplo;

    Producto_PrecioDTO productoPrecioPrueba;

    Producto_PrecioDTO productoPrecioEjemplo;

    ClienteDTO clientePrueba;

    ClienteDTO clienteEjemplo;

    FacturaDTO facturaPrueba;

    FacturaDTO facturaEjemplo;

    @BeforeEach
    public void setup() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {

        clienteEjemplo = new ClienteDTO() {
            {
                setDireccion("San Isidro");
                setEmail("@@@");
                setNombre("Luis");
                setTelefono("1234");
            }
        };
        clienteEjemplo = clienteService.create(clienteEjemplo);
        facturaEjemplo = new FacturaDTO() {
            {
                setCaja(21);
                setDescuento_general(2);
                setUt_clientes(clienteEjemplo);

            }
        };
        facturaEjemplo = facturaService.create(facturaEjemplo);

        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoEjemplo = productoService.create(productoEjemplo);
        factura_DetallesEjemplo = new Factura_DetallesDTO() {
            {
                setCantidad(200);
                setDescuento_final(10);
                setUt_facturas(facturaEjemplo);
                setUt_productos(productoEjemplo);
            }
        };
        productoExistenciaEjemplo = new Producto_ExistenciaDTO() {
            {
                setUt_productos(productoEjemplo);
                setCantidad(1);
            }
        };
        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);
        productoPrecioEjemplo = new Producto_PrecioDTO() {
            {
                setUt_productos(productoEjemplo);
                setPrecio_colones(1000);
                setDescuento_maximo(20);
                setDescuento_promocional(2);
            }
        };
        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

    }

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);

        productoExistenciaPrueba = new Producto_ExistenciaDTO() {
            {
                setUt_productos(productoPrueba);
                setCantidad(1);
            }
        };
        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);

        productoPrecioPrueba = new Producto_PrecioDTO() {
            {

                setUt_productos(productoPrueba);
                setPrecio_colones(1000);
                setDescuento_maximo(10);
                setDescuento_promocional(2);
            }
        };
        productoPrecioPrueba = productoPrecioService.create(productoPrecioPrueba);

        clientePrueba = new ClienteDTO() {
            {
                setDireccion("San Isidro");
                setEmail("@@@");
                setNombre("Luis");
                setTelefono("1234");
            }
        };
        clientePrueba = clienteService.create(clientePrueba);

        facturaPrueba = new FacturaDTO() {
            {
                setCaja(991);
                setUt_clientes(clientePrueba);
            }
        };
        facturaPrueba = facturaService.create(facturaPrueba);

        facturaDetallePruebaConExtraDescuento = new Factura_DetallesDTO() {
            {
                setCantidad(1);
                setUt_productos(productoPrueba);
                setUt_facturas(facturaPrueba);
                setDescuento_final(productoPrecioPrueba.getDescuento_maximo() + 1);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
        
        factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo);

        Optional<Factura_DetallesDTO> facturaEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            Factura_DetallesDTO facturaDetalle = facturaEncontrado.get();
            assertEquals(factura_DetallesEjemplo.getId(), facturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnaFacturaDetallesCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
  
        factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo);
        
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
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();

        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetallesService.create(facturaDetallePruebaConExtraDescuento);
                }
        );
    }
    @Test
    public void sePuedeEliminarUnaFacturaDetallesCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
        
         try {
            factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo); 
        } catch (Exception e) {
            fail(e);
        }

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
            if (factura_DetallesEjemplo.getId() != null) {
                facturaDetallesService.delete(factura_DetallesEjemplo.getId());
            }
            factura_DetallesEjemplo = null;
        }

    }

}
