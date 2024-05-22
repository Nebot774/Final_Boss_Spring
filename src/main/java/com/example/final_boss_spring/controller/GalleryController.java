package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.service.GalleryService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/nasa")
public class GalleryController {
    // Servicio para operaciones relacionadas con la galería
    private final GalleryService galleryService;
    // Logger para registrar eventos
    private final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    // Constructor que inyecta la dependencia del servicio GalleryService
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    // Endpoint para obtener la galería por defecto
    @GetMapping("/galeriadefecto")
    public ResponseEntity<?> buscarGaleriaPorDefecto() {
        try {
            // Intenta obtener la galería por defecto y devuelve el resultado
            return ResponseEntity.ok().body(galleryService.buscarGaleriaPorDefecto());
        } catch (DataNotFoundException e) {
            // Si no se encuentra la galería, devuelve un error 404
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Si ocurre cualquier otro error, devuelve un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para buscar en la galería con parámetros específicos
    @GetMapping("/galeria")
    public ResponseEntity<?> buscarGaleria(
            @RequestParam String query, // Parámetro de búsqueda
            @RequestParam(required = false) String mediaType, // Tipo de medio (opcional)
            @RequestParam(required = false) String yearStart, // Año de inicio (opcional)
            @RequestParam(required = false) String yearEnd, // Año de fin (opcional)
            @RequestParam(required = false, defaultValue = "25") int numResults) { // Número de resultados (opcional, por defecto 25)
        try {
            // Intenta buscar en la galería con los parámetros proporcionados y devuelve el resultado
            return ResponseEntity.ok().body(galleryService.buscarGaleria(query, mediaType, yearStart, yearEnd, numResults));
        } catch (BadRequestException e) {
            // Si la solicitud es incorrecta, devuelve un error 400
            return ResponseEntity.badRequest().body("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
        } catch (DataNotFoundException e) {
            // Si no se encuentra la galería, devuelve un error 404
            return ResponseEntity.notFound().build();
        } catch (ServerErrorException e) {
            // Si ocurre un error en el servidor, devuelve un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error en el servidor.");
        } catch (Exception e) {
            // Si ocurre cualquier otro error, devuelve un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
