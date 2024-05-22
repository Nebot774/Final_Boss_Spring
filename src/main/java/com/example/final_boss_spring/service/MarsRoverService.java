package com.example.final_boss_spring.service;


import com.example.final_boss_spring.model.MarsRover;
import com.example.final_boss_spring.model.MissionManifest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class MarsRoverService {
    private final String API_KEY = "9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc";
    private final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1";

    private final RestTemplate restTemplate;

    // Constructor para inyectar RestTemplate
    public MarsRoverService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener fotos del rover de Marte
    public List<MarsRover.MarsRoverPhoto> getMarsRoverPhotos(String rover, String earthDate, String camera) {
        // Construcción de la URL usando UriComponentsBuilder
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("rovers", rover, "photos")
                .queryParam("earth_date", earthDate)
                .queryParam("camera", camera)
                .queryParam("api_key", API_KEY)
                .toUriString();

        // Llamada a la API de la NASA y mapeo de la respuesta al objeto MarsRover
        MarsRover response = restTemplate.getForObject(url, MarsRover.class);

        // Devolver la lista de fotos, o null si la respuesta es null
        return response != null ? response.getPhotos() : null;
    }

    // Método para obtener el manifiesto de la misión del rover
    public MissionManifest.PhotoManifest getMissionManifest(String rover) {
        // Construcción de la URL usando UriComponentsBuilder
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("manifests", rover)
                .queryParam("api_key", API_KEY)
                .toUriString();

        // Imprimir la URL construida para depuración
        System.out.println("Constructed URL: " + url);

        // Llamada a la API de la NASA y mapeo de la respuesta al objeto MissionManifest
        MissionManifest response = restTemplate.getForObject(url, MissionManifest.class);

        // Imprimir la respuesta mapeada para depuración
        System.out.println("Mapped Response: " + response);

        // Devolver el manifiesto de fotos, o null si la respuesta es null
        return response != null ? response.getPhotoManifest() : null;
    }
}


