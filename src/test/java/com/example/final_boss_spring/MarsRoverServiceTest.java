package com.example.final_boss_spring;

import com.example.final_boss_spring.model.MarsRover;
import com.example.final_boss_spring.service.MarsRoverService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.final_boss_spring.model.MissionManifest;
import org.junit.jupiter.api.Test;

public class MarsRoverServiceTest {
//
//    @Test
//    void getMarsRoverPhotosTest() {
//        // Crear una instancia de RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Crear una instancia del servicio con RestTemplate
//        MarsRoverService marsRoverService = new MarsRoverService(restTemplate);
//
//        // Llamar al método getMarsRoverPhotos con los parámetros de entrada
//        List<MarsRover.MarsRoverPhoto> result = marsRoverService.getMarsRoverPhotos("curiosity", "2022-01-01", "fhaz");
//
//        // Verificar que la respuesta es la esperada
//        assertNotNull(result);
//
//        // Imprimir los campos del objeto MarsRoverPhoto
//        result.forEach(photo -> {
//            System.out.println("ID: " + photo.getId());
//            System.out.println("Sol: " + photo.getSol());
//            System.out.println("Camera: " + (photo.getCamera() != null ? photo.getCamera().getFullName() : "null"));
//            System.out.println("Image Source: " + photo.getImgSrc());
//            System.out.println("Earth Date: " + photo.getEarthDate());
//            System.out.println("Rover Name: " + photo.getRover().getName());
//            System.out.println("Landing Date: " + photo.getRover().getLandingDate());
//            System.out.println("Launch Date: " + photo.getRover().getLaunchDate());
//            System.out.println("Status: " + photo.getRover().getStatus());
//        });
//    }

    @Test
    void getCuriosityMissionManifestTest() {
        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crear una instancia del servicio con RestTemplate
        MarsRoverService marsRoverService = new MarsRoverService(restTemplate);

        // Verificar el manifiesto de Curiosity
        MissionManifest.PhotoManifest curiosityManifest = marsRoverService.getMissionManifest("curiosity");
        assertNotNull(curiosityManifest);
        imprimirManifiesto(curiosityManifest);
    }

    @Test
    void getOpportunityMissionManifestTest() {
        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crear una instancia del servicio con RestTemplate
        MarsRoverService marsRoverService = new MarsRoverService(restTemplate);

        // Verificar el manifiesto de Opportunity
        MissionManifest.PhotoManifest opportunityManifest = marsRoverService.getMissionManifest("opportunity");
        assertNotNull(opportunityManifest);
        imprimirManifiesto(opportunityManifest);
    }

    @Test
    void getSpiritMissionManifestTest() {
        // Crear una instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crear una instancia del servicio con RestTemplate
        MarsRoverService marsRoverService = new MarsRoverService(restTemplate);

        // Verificar el manifiesto de Spirit
        MissionManifest.PhotoManifest spiritManifest = marsRoverService.getMissionManifest("spirit");
        assertNotNull(spiritManifest);
        imprimirManifiesto(spiritManifest);
    }

    private void imprimirManifiesto(MissionManifest.PhotoManifest manifest) {
        System.out.println("Rover Name: " + manifest.getName());
        System.out.println("Landing Date: " + manifest.getLandingDate());
        System.out.println("Launch Date: " + manifest.getLaunchDate());
        System.out.println("Status: " + manifest.getStatus());
        System.out.println("Max Sol: " + manifest.getMaxSol());
        System.out.println("Max Date: " + manifest.getMaxDate());
        System.out.println("Total Photos: " + manifest.getTotalPhotos());
        for (MissionManifest.PhotoManifest.SolInfo solInfo : manifest.getPhotos()) {
            System.out.println("Sol: " + solInfo.getSol());
            System.out.println("Earth Date: " + solInfo.getEarthDate());
            System.out.println("Total Photos: " + solInfo.getTotalPhotos());
            System.out.println("Cameras: " + solInfo.getCameras());
        }
    }
}