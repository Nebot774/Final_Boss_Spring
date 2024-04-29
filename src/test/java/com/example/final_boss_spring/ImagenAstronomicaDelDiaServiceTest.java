package com.example.final_boss_spring;

import com.example.final_boss_spring.model.ApodData;
import com.example.final_boss_spring.service.NasaService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImagenAstronomicaDelDiaServiceTest {

    @Test
    void obtenerAPODTest() {
        // Crear una instancia del servicio
        NasaService nasaService = new NasaService();

        // Llamar al método obtenerAPOD con una fecha de entrada
        ApodData result = nasaService.obtenerAPOD("2024-04-28");

        // Verificar que la respuesta es la esperada
        assertNotNull(result);

        // Imprimir los campos del objeto ApodData
        System.out.println("Date: " + result.getDate());
        System.out.println("Explanation: " + result.getExplanation());
        System.out.println("MediaType: " + result.getMediaType());
        System.out.println("ServiceVersion: " + result.getServiceVersion());
        System.out.println("Title: " + result.getTitle());
        System.out.println("Url: " + result.getUrl());
        System.out.println("Copyright: " + result.getCopyright());

        // Aquí puedes agregar más aserciones para verificar los campos específicos de ApodData
    }
}
