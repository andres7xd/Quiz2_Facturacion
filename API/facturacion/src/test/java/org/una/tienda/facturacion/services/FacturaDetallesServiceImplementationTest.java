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
import org.una.tienda.facturacion.exceptions.FacturaConCantidadCeroException;
import org.una.tienda.facturacion.exceptions.FacturaConPrecioCeroException;
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
    
    Factura_DetallesDTO factura_DetallesEjemplo2;
    
    Factura_DetallesDTO factura_DetallesEjemplo3;

    Factura_DetallesDTO facturaDetallePruebaConExtraDescuento;

    ProductoDTO productoPrueba;

    ProductoDTO productoEjemplo;

    ProductoDTO productoEjemplo2;

    ProductoDTO productoEjemplo3;

    Producto_ExistenciaDTO productoExistenciaPrueba;

    Producto_ExistenciaDTO productoExistenciaEjemplo;

    Producto_ExistenciaDTO productoExistenciaEjemplo2;

    Producto_ExistenciaDTO productoExistenciaEjemplo3;

    Producto_PrecioDTO productoPrecioPrueba;

    Producto_PrecioDTO productoPrecioEjemplo;

    Producto_PrecioDTO productoPrecioEjemplo2;

    Producto_PrecioDTO productoPrecioEjemplo3;

    ClienteDTO clientePrueba;

    ClienteDTO clienteEjemplo;

    ClienteDTO clienteEjemplo2;

    ClienteDTO clienteEjemplo3;

    FacturaDTO facturaPrueba;

    FacturaDTO facturaEjemplo;

    FacturaDTO facturaEjemplo2;

    FacturaDTO facturaEjemplo3;

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

    private void initDataForSeEvitaFacturarUnProductoConPrecioCero() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {

        clienteEjemplo2 = new ClienteDTO() {
            {
                setDireccion("San Isidro");
                setEmail("@@@");
                setNombre("Luis");
                setTelefono("1234");
            }
        };
        clienteEjemplo2 = clienteService.create(clienteEjemplo2);
        facturaEjemplo2 = new FacturaDTO() {
            {
                setCaja(21);
                setDescuento_general(2);
                setUt_clientes(clienteEjemplo2);

            }
        };
        facturaEjemplo2 = facturaService.create(facturaEjemplo2);

        productoEjemplo2 = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoEjemplo2 = productoService.create(productoEjemplo2);
        factura_DetallesEjemplo2 = new Factura_DetallesDTO() {
            {
                setCantidad(200);
                setDescuento_final(10);
                setUt_facturas(facturaEjemplo2);
                setUt_productos(productoEjemplo2);
            }
        };
        productoExistenciaEjemplo2 = new Producto_ExistenciaDTO() {
            {
                setUt_productos(productoEjemplo2);
                setCantidad(1);
            }
        };
        productoExistenciaEjemplo2 = productoExistenciaService.create(productoExistenciaEjemplo2);
        productoPrecioEjemplo2 = new Producto_PrecioDTO() {
            {
                setUt_productos(productoEjemplo);
                setPrecio_colones(0);
                setDescuento_maximo(20);
                setDescuento_promocional(2);
            }
        };
        productoPrecioEjemplo2 = productoPrecioService.create(productoPrecioEjemplo2);

    }
    
    private void initDataForSeEvitaFacturarUnProductoConCantidadCero() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException{
        clienteEjemplo3 = new ClienteDTO() {
            {
                setDireccion("San Isidro");
                setEmail("@@@");
                setNombre("Luis");
                setTelefono("1234");
            }
        };
        clienteEjemplo3 = clienteService.create(clienteEjemplo3);
        facturaEjemplo3 = new FacturaDTO() {
            {
                setCaja(21);
                setDescuento_general(2);
                setUt_clientes(clienteEjemplo3);

            }
        };
        facturaEjemplo3 = facturaService.create(facturaEjemplo3);

        productoEjemplo3 = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);

            }
        };
        productoEjemplo3 = productoService.create(productoEjemplo3);
        factura_DetallesEjemplo3 = new Factura_DetallesDTO() {
            {
                setCantidad(0);
                setDescuento_final(10);
                setUt_facturas(facturaEjemplo3);
                setUt_productos(productoEjemplo3);
            }
        };
        productoExistenciaEjemplo3 = new Producto_ExistenciaDTO() {
            {
                setUt_productos(productoEjemplo3);
                setCantidad(1);
            }
        };
        productoExistenciaEjemplo3 = productoExistenciaService.create(productoExistenciaEjemplo3);
        productoPrecioEjemplo3 = new Producto_PrecioDTO() {
            {
                setUt_productos(productoEjemplo);
                setPrecio_colones(1000);
                setDescuento_maximo(20);
                setDescuento_promocional(2);
            }
        };
        productoPrecioEjemplo3 = productoPrecioService.create(productoPrecioEjemplo3);
    }

    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, FacturaConCantidadCeroException, FacturaConPrecioCeroException {

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
    public void sePuedeModificarUnaFacturaDetallesCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException, FacturaConCantidadCeroException, FacturaConPrecioCeroException {

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
    public void seEvitaFacturarUnProductoConPrecioCero() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {
        initDataForSeEvitaFacturarUnProductoConPrecioCero();

        assertThrows(FacturaConPrecioCeroException.class,
                () -> {
                    facturaDetallesService.create(factura_DetallesEjemplo2);
                }
        );
    }
    
    @Test
    public void seEvitaFacturarUnProductoConCantidadCero() throws ClienteConTelefonoException, ClienteConEmailException, ClienteConDireccionException {
        initDataForSeEvitaFacturarUnProductoConCantidadCero();

        assertThrows(FacturaConCantidadCeroException.class,
                () -> {
                    facturaDetallesService.create(factura_DetallesEjemplo3);
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
