package com.example.final_boss_spring.service;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.NeoWsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.final_boss_spring.config.NasaApiConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class NeoWsService {
    private static final String API_KEY = NasaApiConfig.API_KEY;
    private static final String BASE_URL = NasaApiConfig.BASE_URL;


    public NeoWsData obtenerNeoWsPorFecha(String startDate, String endDate) throws DataNotFoundException {
        try {
            // Construir la URL con los parámetros de fecha proporcionados
            String urlString = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + startDate +
                    "&end_date=" + endDate + "&api_key=" + API_KEY;
            URL url = new URL(urlString);

            // Abrir la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud HTTP
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Analizar la respuesta JSON y convertirla en un objeto NeoWsData
            ObjectMapper objectMapper = new ObjectMapper();
            NeoWsData neoWsData = objectMapper.readValue(response.toString(), NeoWsData.class);

            // Retornar el objeto NeoWsData
            return neoWsData;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataNotFoundException("No se pudo obtener la información de asteroides cercanos.");
        }
    }

    public NeoWsData obtenerNeoWsPorId(String asteroideId) throws DataNotFoundException {
        try {
            // Construir la URL con el ID del asteroide
            String urlString = "https://api.nasa.gov/neo/rest/v1/neo/" + asteroideId + "?api_key=" + API_KEY;
            URL url = new URL(urlString);

            // Abrir la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud HTTP
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Analizar la respuesta JSON y convertirla en un objeto NeoWsData
            ObjectMapper objectMapper = new ObjectMapper();
            NeoWsData neoWsData = objectMapper.readValue(response.toString(), NeoWsData.class);

            // Retornar el objeto NeoWsData
            return neoWsData;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataNotFoundException("No se pudo obtener información sobre el asteroide con ID: " + asteroideId);
        }
    }


}
