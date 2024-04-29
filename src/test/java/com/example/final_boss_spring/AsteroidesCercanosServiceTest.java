package com.example.final_boss_spring;

import com.example.final_boss_spring.model.NeoWsData;
import com.example.final_boss_spring.service.NasaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AsteroidesCercanosServiceTest {
    @Test
    public void obtenerNeoWsPorFechaTest() {
        NasaService nasaService = new NasaService();
        NeoWsData result = nasaService.obtenerNeoWsPorFecha("2022-01-01", "2022-01-02");
        assertNotNull(result);
        System.out.println(result.toString());
    }

    @Test
    public void obtenerAsteroidePorIdTest() {
        NasaService nasaService = new NasaService();
        NeoWsData result = nasaService.obtenerNeoWsPorId("2465633");
        assertNotNull(result);
        System.out.println(result.toString());
    }
}