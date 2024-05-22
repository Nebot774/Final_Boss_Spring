package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.model.TierraDesdeEspacio;
import com.example.final_boss_spring.service.TierraDesdeEspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("nasa")
public class TierraDesdeEspacioController {

    // Inyección de dependencias del servicio TierraDesdeEspacioService
    @Autowired
    private TierraDesdeEspacioService tierraDesdeEspacioService;

    // Endpoint para obtener imágenes de la Tierra desde el espacio
    @GetMapping("/earth/imagery")
    public TierraDesdeEspacio getTierraDesdeEspacio(
            @RequestParam double lat, // Latitud del lugar a obtener la imagen
            @RequestParam double lon, // Longitud del lugar a obtener la imagen
            @RequestParam String date) { // Fecha de la imagen
        // Llama al servicio para obtener la imagen y devuelve el resultado
        return tierraDesdeEspacioService.fetchTierraDesdeEspacio(lat, lon, date);
    }
}

