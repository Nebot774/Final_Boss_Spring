package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.ApodData;
import com.example.final_boss_spring.service.NasaService;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/nasa")
public class NasaController {

    private final Logger logger = LoggerFactory.getLogger(NasaController.class);
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
            logger.info("Iniciando la obtención de la imagen del día");
            // Obtener la fecha actual en el formato YYYY-MM-DD
            String fechaActual = LocalDate.now().toString();

            // Llamar al método obtenerAPOD() con la fecha actual
            ApodData apodData = nasaService.obtenerAPOD(fechaActual);

            logger.info("Imagen del día obtenida con éxito");
            // Devolver la respuesta con los datos obtenidos
            return ResponseEntity.ok(apodData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/apod/{fecha}")
    public ResponseEntity<?> obtenerImagenDelDia(@PathVariable String fecha) {
        try {
            logger.info("Iniciando la obtención de la imagen del día");

            // Llamar al método obtenerAPOD() con la fecha proporcionada
            ApodData apodData = nasaService.obtenerAPOD(fecha);

            logger.info("Imagen del día obtenida con éxito");
            // Devolver la respuesta con los datos obtenidos
            return ResponseEntity.ok(apodData);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Método para obtener información sobre asteroides cercanos en un rango de fechas
    @GetMapping("/neows")
    public ResponseEntity<?> obtenerAsteroidesCercanos(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            return ResponseEntity.ok().body(nasaService.obtenerNeoWsPorFecha(startDate, endDate));
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
            return ResponseEntity.ok().body(nasaService.obtenerNeoWsPorId(asteroideId));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para buscar en la galería de imágenes y vídeos
    @GetMapping("/galeriadefecto")
    public ResponseEntity<?> buscarEnGaleria(@RequestParam String query, @RequestParam(required = false) String mediaType, @RequestParam(required = false) String yearStart, @RequestParam(required = false) String yearEnd, @RequestParam(required = false, defaultValue = "25") int numResults) {
        try {
            return ResponseEntity.ok().body(nasaService.buscarGaleria(query, mediaType, yearStart, yearEnd, numResults));
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

    @GetMapping("/galeria")
    public ResponseEntity<?> buscarGaleria(
            @RequestParam String query,
            @RequestParam(required = false) String mediaType,
            @RequestParam(required = false) String yearStart,
            @RequestParam(required = false) String yearEnd,
            @RequestParam(required = false, defaultValue = "25") int numResults) {
        try {
            return ResponseEntity.ok().body(nasaService.buscarGaleria(query, mediaType, yearStart, yearEnd, numResults));
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



//
//    // Método para obtener fotos de los Mars Rovers
//    @GetMapping("/mars-rovers/{rover}/photos")
//    public ResponseEntity<?> obtenerFotosMarsRovers(@PathVariable String rover, @RequestParam int sol) {
//        try {
//            return ResponseEntity.ok().body(nasaService.obtenerFotosMarsRovers(rover, sol));
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//

//
//    // Método para obtener imágenes de la Tierra desde el espacio (EPIC)
//    @GetMapping("/epic")
//    public ResponseEntity<?> obtenerImagenesTierraEspacio() {
//        try {
//            return ResponseEntity.ok().body(nasaService.obtenerImagenesEPIC());
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Método para obtener la imagen del día de nacimiento
//    @GetMapping("/apod/birthday")
//    public ResponseEntity<?> obtenerImagenDiaNacimiento(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
//        try {
//            return ResponseEntity.ok().body(nasaService.obtenerImagenDiaNacimiento(fecha));
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


}


