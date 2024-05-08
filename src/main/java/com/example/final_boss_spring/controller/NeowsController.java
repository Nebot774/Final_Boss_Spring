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
    private final NeoWsService neoWsService;
    private final Logger logger = LoggerFactory.getLogger(NeowsController.class);

    public NeowsController(NeoWsService neoWsService) {
        this.neoWsService = neoWsService;
    }

    // Métodos relacionados con NeoWs

    // Método para obtener información sobre asteroides cercanos en un rango de fechas
    @GetMapping("/neows")
    public ResponseEntity<?> obtenerAsteroidesCercanos(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            return ResponseEntity.ok().body(neoWsService.obtenerNeoWsPorFecha(startDate, endDate));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para obtener información sobre un asteroide específico por su ID
    @GetMapping("/neows/{asteroideId}")
    public ResponseEntity<?> obtenerAsteroidePorId(@PathVariable String asteroideId) {
        try {
            return ResponseEntity.ok().body(neoWsService.obtenerNeoWsPorId(asteroideId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
