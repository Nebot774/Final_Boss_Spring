package com.example.final_boss_spring;

import com.example.final_boss_spring.model.NeoWsData;
import com.example.final_boss_spring.model.NeoWsNeoData;
import com.example.final_boss_spring.service.NeoWsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AsteroidesCercanosServiceTest {
    @Test
    public void obtenerNeoWsPorFechaTest() {
        NeoWsService nasaService = new NeoWsService();
        NeoWsData result = nasaService.obtenerNeoWsPorFecha("2022-01-01", "2022-01-02");
        assertNotNull(result);
        System.out.println(result.toString());
    }

    @Test
    public void obtenerAsteroidePorIdTest() {
        NeoWsService nasaService = new NeoWsService();
        NeoWsNeoData result = nasaService.obtenerNeoWsPorId("3542519");
        assertNotNull(result);
        System.out.println(result.toString());
    }

}
