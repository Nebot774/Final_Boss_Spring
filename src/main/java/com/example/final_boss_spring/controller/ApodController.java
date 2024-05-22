package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.ApodData;
import com.example.final_boss_spring.service.ApodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

// Controlador para las operaciones relacionadas con APOD (Astronomy Picture of the Day)
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/nasa")
public class ApodController {
    private final ApodService apodService;
    private final Logger logger = LoggerFactory.getLogger(ApodController.class);

    // Inyección de dependencias del servicio ApodService
    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    // Endpoint para obtener la Imagen del Día (APOD)
    @GetMapping("/apod")
    public ResponseEntity<?> obtenerImagenDelDia() {
        try {
            logger.info("Iniciando la obtención de la imagen del día");
            // Obtener la fecha actual en el formato YYYY-MM-DD
            String fechaActual = LocalDate.now().toString();

            // Llamar al método obtenerAPOD() con la fecha actual
            ApodData apodData = apodService.obtenerAPOD(fechaActual);

            logger.info("Imagen del día obtenida con éxito");
            // Devolver la respuesta con los datos obtenidos
            return ResponseEntity.ok(apodData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obtener la Imagen del Día (APOD) de una fecha específica
    @GetMapping("/apod/{fecha}")
    public ResponseEntity<?> obtenerImagenDelDia(@PathVariable String fecha) {
        try {
            logger.info("Iniciando la obtención de la imagen del día");

            // Llamar al método obtenerAPOD() con la fecha proporcionada
            ApodData apodData = apodService.obtenerAPOD(fecha);

            logger.info("Imagen del día obtenida con éxito");
            // Devolver la respuesta con los datos obtenidos
            return ResponseEntity.ok(apodData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}