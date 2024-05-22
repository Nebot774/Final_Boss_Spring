package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.service.NeoWsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/nasa")
public class NeowsController {
    // Servicio para operaciones relacionadas con NEO (Near Earth Objects)
    private final NeoWsService neoWsService;
    // Logger para registrar eventos
    private final Logger logger = LoggerFactory.getLogger(NeowsController.class);

    // Constructor que inyecta la dependencia del servicio NeoWsService
    public NeowsController(NeoWsService neoWsService) {
        this.neoWsService = neoWsService;
    }

    // Endpoint para obtener información sobre asteroides cercanos en un rango de fechas
    @GetMapping("/neows")
    public ResponseEntity<?> obtenerAsteroidesCercanos(@RequestParam String startDate, // Fecha de inicio del rango
                                                       @RequestParam String endDate) { // Fecha de fin del rango
        try {
            // Intenta obtener la información de asteroides cercanos y devuelve el resultado
            return ResponseEntity.ok().body(neoWsService.obtenerNeoWsPorFecha(startDate, endDate));
        } catch (DataNotFoundException e) {
            // Si no se encuentra la información, devuelve un error 404
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Si ocurre cualquier otro error, devuelve un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obtener información sobre un asteroide específico por su ID
    @GetMapping("/neowsid/{asteroideId}")
    public ResponseEntity<?> obtenerAsteroidePorId(@PathVariable String asteroideId) { // ID del asteroide
        try {
            // Intenta obtener la información del asteroide y devuelve el resultado
            return ResponseEntity.ok().body(neoWsService.obtenerNeoWsPorId(asteroideId));
        } catch (DataNotFoundException e) {
            // Si no se encuentra la información del asteroide, devuelve un error 404
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Si ocurre cualquier otro error, devuelve un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
