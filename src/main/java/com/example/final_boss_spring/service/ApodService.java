package com.example.final_boss_spring.service;

import com.example.final_boss_spring.config.NasaApiConfig;
import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.ApodData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class ApodService {
    private static final String API_KEY = NasaApiConfig.API_KEY;
    private static final String BASE_URL = NasaApiConfig.BASE_URL;

    public ApodData obtenerAPOD(String fecha) throws DataNotFoundException {
        try {
            // Construir la URL con la fecha proporcionada
            URL url = new URL(BASE_URL + "?api_key=" + API_KEY + "&date=" + fecha);
            //URL url = new URL(BASE_URL2 );

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

            // Analizar la respuesta JSON y convertirla en un objeto ApodData
            ObjectMapper objectMapper = new ObjectMapper();
            ApodData apodData = objectMapper.readValue(response.toString(), ApodData.class);

            // Retornar el objeto ApodData
            return apodData;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataNotFoundException("No se pudo obtener la imagen del día.");
        }
    }




}
