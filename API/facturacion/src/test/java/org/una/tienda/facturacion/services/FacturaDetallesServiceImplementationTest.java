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
    public void setup() {
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
        factura_DetallesEjemplo = new Factura_DetallesDTO() {
            {
                setUt_productos(productoPrueba);
                setCantidad(1);
                setDescuento_final(0.5);
            }

        };

        //wekjhndqooiw
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoEjemplo = productoService.create(productoEjemplo);

        productoExistenciaEjemplo = new Producto_ExistenciaDTO() {
            {
                setUt_productos(productoPrueba);
                setCantidad(1);
            }
        };
        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        productoPrecioEjemplo = new Producto_PrecioDTO() {
            {

                setUt_productos(productoEjemplo);
                setPrecio_colones(1000);
                setDescuento_maximo(10);
                setDescuento_promocional(2);
            }
        };
        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        clienteEjemplo = new ClienteDTO() {
            {
                setNombre("ClienteDePrueba");
            }
        };
        clienteEjemplo = clienteService.create(clienteEjemplo);

        facturaEjemplo = new FacturaDTO() {
            {
                setCaja(991);
                setUt_clientes(clienteEjemplo);
            }
        };
        facturaEjemplo = facturaService.create(facturaEjemplo);

        factura_DetallesEjemplo = new Factura_DetallesDTO() {
            {
                setCantidad(1);
                setUt_productos(productoEjemplo);
                setUt_facturas(facturaEjemplo);
                setDescuento_final(1);
            }
        };
    }

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
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
                setNombre("ClienteDePrueba");
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

    private void initDataForSePuedeCrearUnaFacturaDetallesCorrectamente() {

        clientePrueba = new ClienteDTO() {
            {
                setNombre("ClienteDePrueba");
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
        
        productoPrueba = new ProductoDTO(){
                {
                    setDescripcion("hola");
                    setImpuesto(0.25);
                }  
        };
        productoPrueba = productoService.create(productoPrueba);
    }

    @Test
    public void sePuedeCrearUnaFacturaDetallesCorrectamente() {
        
        initDataForSePuedeCrearUnaFacturaDetallesCorrectamente();

        try {
            factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo);
        } catch (Exception e) {
            fail(e);
        }

        System.out.println(factura_DetallesEjemplo);

        Optional<Factura_DetallesDTO> facturaDetallesEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());

        if (facturaDetallesEncontrado.isPresent()) {
            Factura_DetallesDTO facturaDetalles = facturaDetallesEncontrado.get();
            assertEquals(factura_DetallesEjemplo.getId(), facturaDetalles.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

//    @Test
//    public void sePuedeModificarUnaFacturaDetallesCorrectamente() {
//
//        Optional<Factura_DetallesDTO> facturDetails = facturaDetallesService.update(factura_DetallesEjemplo, factura_DetallesEjemplo.getId());
//
//        Optional<Factura_DetallesDTO> factura_DetallesEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());
//
//        if (factura_DetallesEncontrado.isPresent()) {
//            Factura_DetallesDTO factura_DetallesDTO = factura_DetallesEncontrado.get();
//            assertEquals(factura_DetallesEjemplo.getId(), factura_DetallesDTO.getId());
//
//        } else {
//            fail("No se encontro la información en la BD");
//        }
//    }
//    @Test
//    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
//        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();
//
//        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
//                () -> {
//                    facturaDetallesService.create(facturaDetallePruebaConExtraDescuento);
//                }
//        );
//    }
//    @Test
//    public void sePuedeEliminarUnaFacturaDetallesCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
//        
//         try {
//            factura_DetallesEjemplo = facturaDetallesService.create(factura_DetallesEjemplo); 
//        } catch (Exception e) {
//            fail(e);
//        }
//
//        facturaDetallesService.delete(factura_DetallesEjemplo.getId());
//
//        Optional<Factura_DetallesDTO> facturaEncontrado = facturaDetallesService.findById(factura_DetallesEjemplo.getId());
//
//        if (facturaEncontrado != null) {
//            fail("El producto no fue eliminado");
//        } else {
//            Assertions.assertTrue(true);
//            factura_DetallesEjemplo = null;
//        }
//    }
    @AfterEach
    public void tearDown() {
        if (factura_DetallesEjemplo != null) {
            facturaDetallesService.delete(factura_DetallesEjemplo.getId());
            factura_DetallesEjemplo = null;
        }

    }

}
