package com.example.final_boss_spring;

import com.example.final_boss_spring.model.MarsRover;
import com.example.final_boss_spring.service.MarsRoverService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class MarsRoverServiceTest {

    @Test
    void getMarsRoverPhotosTest() {
        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crear una instancia del servicio con RestTemplate
        MarsRoverService marsRoverService = new MarsRoverService(restTemplate);

        // Llamar al método getMarsRoverPhotos con los parámetros de entrada
        List<MarsRover.MarsRoverPhoto> result = marsRoverService.getMarsRoverPhotos("opportunity", "2019-01-01", "rhaz");

        // Verificar que la respuesta es la esperada
        assertNotNull(result);

        // Imprimir los campos del objeto MarsRoverPhoto
        result.forEach(photo -> {
            System.out.println("ID: " + photo.getId());
            System.out.println("Sol: " + photo.getSol());
            System.out.println("Camera: " + (photo.getCamera() != null ? photo.getCamera().getFullName() : "null"));
            System.out.println("Image Source: " + photo.getImgSrc());
            System.out.println("Earth Date: " + photo.getEarthDate());
            System.out.println("Rover Name: " + photo.getRover().getName());
            System.out.println("Landing Date: " + photo.getRover().getLandingDate());
            System.out.println("Launch Date: " + photo.getRover().getLaunchDate());
            System.out.println("Status: " + photo.getRover().getStatus());
        });

        // Aquí puedes agregar más aserciones para verificar los campos específicos de MarsRoverPhoto
    }
}