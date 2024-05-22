package com.example.final_boss_spring;

import com.example.final_boss_spring.model.NeoWsData;
import com.example.final_boss_spring.model.NeoWsNeoData;
import com.example.final_boss_spring.service.NeoWsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AsteroidesCercanosServiceTest {

    @Test
    public void obtenerNeoWsPorFechaTest() {
        // Crear una instancia del servicio NeoWsService
        NeoWsService nasaService = new NeoWsService();
        // Llamar al método para obtener datos NEO WS por fecha
        NeoWsData result = nasaService.obtenerNeoWsPorFecha("2024-05-01", "2024-05-08");
        // Verificar que el resultado no sea nulo
        assertNotNull(result);
        // Imprimir el resultado en la consola
        System.out.println(result.toString());
    }

    @Test
    public void obtenerAsteroidePorIdTest() {
        // Crear una instancia del servicio NeoWsService
        NeoWsService nasaService = new NeoWsService();
        // Llamar al método para obtener datos NEO WS por ID
        NeoWsNeoData result = nasaService.obtenerNeoWsPorId("3542519");
        // Verificar que el resultado no sea nulo
        assertNotNull(result);
        // Imprimir el resultado en la consola
        System.out.println(result.toString());
    }

}

