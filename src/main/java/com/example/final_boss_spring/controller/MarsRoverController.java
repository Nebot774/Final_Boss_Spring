package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.model.MarsRover;
import com.example.final_boss_spring.model.MissionManifest;
import com.example.final_boss_spring.service.MarsRoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Indica que esta clase es un controlador REST
@RestController
public class MarsRoverController {
    // Inyección de dependencias del servicio MarsRoverService
    @Autowired
    private MarsRoverService marsRoverService;

    // Endpoint para obtener las fotos del Mars Rover
    @GetMapping("/mars-rover/photos")
    public List<MarsRover.MarsRoverPhoto> getMarsRoverPhotos(@RequestParam String rover, // Nombre del rover
                                                             @RequestParam String date, // Fecha de las fotos
                                                             @RequestParam String camera) { // Cámara con la que se tomaron las fotos
        // Llama al servicio para obtener las fotos y devuelve el resultado
        return marsRoverService.getMarsRoverPhotos(rover, date, camera);
    }

    // Endpoint para obtener el manifiesto de la misión del Mars Rover
    @GetMapping("/mars-rover/manifest")
    public MissionManifest.PhotoManifest getMissionManifest(@RequestParam String rover) { // Nombre del rover
        // Llama al servicio para obtener el manifiesto de la misión y devuelve el resultado
        return marsRoverService.getMissionManifest(rover);
    }
}
