/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.tienda.facturacion.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis
 */
@RestController
@RequestMapping("/tipo-cambio")
public class TipoCambioController {

    @GetMapping("/a-dolares/{monto}")
    public ResponseEntity<?> getMontoEnDolares(@PathVariable(value = "monto") double monto) {
        double conversionDolaresAColones = 610;
        return new ResponseEntity(monto / conversionDolaresAColones, HttpStatus.OK);

    }
}
