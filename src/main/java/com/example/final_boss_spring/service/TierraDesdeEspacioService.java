package com.example.final_boss_spring.service;

import com.example.final_boss_spring.model.TierraDesdeEspacio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TierraDesdeEspacioService {

    private static final String API_KEY = "9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc";
    private final RestTemplate restTemplate;

    public TierraDesdeEspacioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TierraDesdeEspacio fetchTierraDesdeEspacio(double lat, double lon, String date) {
        String url = String.format("https://api.nasa.gov/planetary/earth/assets?lon=%s&lat=%s&date=%s&api_key=%s", lon, lat, date, API_KEY);
        return restTemplate.getForObject(url, TierraDesdeEspacio.class);
    }
}