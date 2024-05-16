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

    public MarsRoverService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MarsRover.MarsRoverPhoto> getMarsRoverPhotos(String rover, String earthDate, String camera) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("rovers", rover, "photos")
                .queryParam("earth_date", earthDate)
                .queryParam("camera", camera)
                .queryParam("api_key", API_KEY)
                .toUriString();

        MarsRover response = restTemplate.getForObject(url, MarsRover.class);
        return response != null ? response.getPhotos() : null;
    }

    public MissionManifest.PhotoManifest getMissionManifest(String rover) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment("manifests", rover)
                .queryParam("api_key", API_KEY)
                .toUriString();

        System.out.println("Constructed URL: " + url);

        MissionManifest response = restTemplate.getForObject(url, MissionManifest.class);
        System.out.println("Mapped Response: " + response);

        return response != null ? response.getPhotoManifest() : null;
    }
}

