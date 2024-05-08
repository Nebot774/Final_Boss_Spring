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
@RequestMapping("/nasa/gallery")
public class GalleryController {
    private final GalleryService galleryService;
    private final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    // Métodos relacionados con la galería
    // Método para buscar en la galería de imágenes y vídeos
    @GetMapping("/galeriadefecto")
    public ResponseEntity<?> buscarGaleriaPorDefecto() {
        try {
            return ResponseEntity.ok().body(galleryService.buscarGaleriaPorDefecto());
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/galeria")
    public ResponseEntity<?> buscarGaleria(
            @RequestParam String query,
            @RequestParam(required = false) String mediaType,
            @RequestParam(required = false) String yearStart,
            @RequestParam(required = false) String yearEnd,
            @RequestParam(required = false, defaultValue = "25") int numResults) {
        try {
            return ResponseEntity.ok().body(galleryService.buscarGaleria(query, mediaType, yearStart, yearEnd, numResults));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error en el servidor.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
