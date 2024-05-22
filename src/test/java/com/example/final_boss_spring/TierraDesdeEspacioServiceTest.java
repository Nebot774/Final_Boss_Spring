package com.example.final_boss_spring;


import com.example.final_boss_spring.model.TierraDesdeEspacio;
import com.example.final_boss_spring.service.TierraDesdeEspacioService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TierraDesdeEspacioServiceTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private final TierraDesdeEspacioService tierraDesdeEspacioService = new TierraDesdeEspacioService(restTemplate);

    @Test
    public void testFetchTierraDesdeEspacio() {
        // Llamar al servicio con parámetros reales
        double lat = 29.78;
        double lon = -95.33;
        String date = "2018-01-01";

        TierraDesdeEspacio response = tierraDesdeEspacioService.fetchTierraDesdeEspacio(lat, lon, date);

        // Verificar que la respuesta no es nula y contiene los datos esperados
        assertNotNull(response);
        System.out.println("Fecha: " + response.getDate());
        System.out.println("URL: " + response.getUrl());
    }

}