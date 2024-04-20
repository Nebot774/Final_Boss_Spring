package com.example.final_boss_spring.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/nasa")
public class NasaController {

    // Servicio inyectado para manejar la lógica de negocio
    private final NasaService nasaService;

    // Constructor con inyección de dependencias
    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    // Endpoint para obtener la Imagen del Día (APOD)
    @GetMapping("/apod")
    public ResponseEntity<?> obtenerImagenDelDia() {
        try {
            ApodData apodData = nasaService.obtenerAPOD();
            return ResponseEntity.ok(apodData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para obtener información sobre asteroides cercanos
    @GetMapping("/neows")
    public ResponseEntity<?> obtenerAsteroidesCercanos() {
        try {
            return ResponseEntity.ok().body(nasaService.obtenerNeoWs());
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para obtener fotos de los Mars Rovers
    @GetMapping("/mars-rovers/{rover}/photos")
    public ResponseEntity<?> obtenerFotosMarsRovers(@PathVariable String rover, @RequestParam int sol) {
        try {
            return ResponseEntity.ok().body(nasaService.obtenerFotosMarsRovers(rover, sol));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para buscar en la galería de imágenes y vídeos
    @GetMapping("/image-gallery")
    public ResponseEntity<?> buscarEnGaleria(@RequestParam String query) {
        try {
            return ResponseEntity.ok().body(nasaService.buscarEnGaleria(query));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para obtener imágenes de la Tierra desde el espacio (EPIC)
    @GetMapping("/epic")
    public ResponseEntity<?> obtenerImagenesTierraEspacio() {
        try {
            return ResponseEntity.ok().body(nasaService.obtenerImagenesEPIC());
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para obtener la imagen del día de nacimiento
    @GetMapping("/apod/birthday")
    public ResponseEntity<?> obtenerImagenDiaNacimiento(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        try {
            return ResponseEntity.ok().body(nasaService.obtenerImagenDiaNacimiento(fecha));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


