package com.example.final_boss_spring.service;

import com.example.final_boss_spring.model.TierraDesdeEspacio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TierraDesdeEspacioService {

    private static final String API_KEY = "9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc";
    private final RestTemplate restTemplate;

    // Constructor para inyectar RestTemplate
    public TierraDesdeEspacioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener datos de la Tierra desde el espacio
    public TierraDesdeEspacio fetchTierraDesdeEspacio(double lat, double lon, String date) {
        // Construcción de la URL con los parámetros latitud, longitud, fecha y API_KEY
        String url = String.format("https://api.nasa.gov/planetary/earth/assets?lon=%s&lat=%s&date=%s&api_key=%s", lon, lat, date, API_KEY);

        // Imprimir la URL construida para depuración
        System.out.println(url);

        // Llamada a la API de la NASA y mapeo de la respuesta al objeto TierraDesdeEspacio
        return restTemplate.getForObject(url, TierraDesdeEspacio.class);
    }
}
