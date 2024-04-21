package com.example.final_boss_spring;

import com.example.final_boss_spring.model.ApodData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class NASAControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void obtenerImagenDelDia_DeberiaRetornarApodData() throws Exception {
        // Realizar una solicitud GET a la ruta "/apod"
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/apod")
                        .param("api_key", "9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc")
                        .param("date", "2014-10-01")
                        .param("concept_tags", "False"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        // Obtener la respuesta como una cadena JSON
        String jsonResponse = result.getResponse().getContentAsString();

        // Convertir la respuesta JSON a un objeto ApodData
        ObjectMapper objectMapper = new ObjectMapper();
        ApodData apodData = objectMapper.readValue(jsonResponse, ApodData.class);

        // Verificar que el objeto ApodData no es nulo
        assertThat(apodData).isNotNull();

        // También puedes realizar más aserciones para verificar otros aspectos del objeto ApodData, como el título, la URL, etc.

        // Imprimir el objeto ApodData para ver su contenido (opcional)
        System.out.println(apodData);
    }
}

