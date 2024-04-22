package com.example.final_boss_spring.service;

import com.example.final_boss_spring.model.ApodData;
import com.example.final_boss_spring.model.NeoWsData;
import org.springframework.stereotype.Service;
import com.example.final_boss_spring.exception.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class NasaService {
    //aqui se implementaran la logica para llamar a la api y manejar las respuestas

    private static final String API_KEY = "9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc";
    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod";

    private static final String BASE_URL2 = "https://api.nasa.gov/planetary/apod?api_key=9dgFAENW1dghiXqP2wPmdCDEOsCAISYbrm3XW2tc";


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


//
//    public MarsRoverData obtenerFotosMarsRovers(String rover, int sol) throws DataNotFoundException {
//        // Aquí va la lógica para obtener fotos de los Mars Rovers
//        return null;
//    }
//
//    public GalleryData buscarGaleria(String query) throws DataNotFoundException {
//        // Aquí va la lógica para buscar en la galería de imágenes y vídeos
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate date) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate startDate, LocalDate endDate) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte en un rango de fechas
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate date, String rover) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover específico
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate startDate, LocalDate endDate, String rover) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover específico en un rango de fechas
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate date, String rover, String camera) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover y cámara específicos
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate startDate, LocalDate endDate, String rover, String camera) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover y cámara específicos en un rango de fechas
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate date, String rover, String camera, boolean isAvailable) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover y cámara específicos, indicando si la cámara está disponible
//        return null;
//    }
//
//    public MarsWeatherData obtenerMarsWeather(LocalDate startDate, LocalDate endDate, String rover, String camera, boolean isAvailable) throws DataNotFoundException {
//        // Aquí va la lógica para obtener el clima en Marte para un rover y cámara específicos, indicando si la cámara está disponible, en un rango de fechas
//        return null;
//    }
//
//    public EpicData obtenerImagenesEPIC() throws DataNotFoundException {
//        // Aquí va la lógica para obtener imágenes de la Tierra desde el espacio (EPIC)
//        return null;
//    }
//
//    public EpicData obtenerImagenesEPIC(LocalDate date) throws DataNotFoundException {
//        // Aquí va la lógica para obtener imágenes de la Tierra desde el espacio (EPIC) en una fecha específica
//        return null;
//    }
//
//    public EpicData obtenerImagenesEPIC(LocalDate startDate, LocalDate endDate) throws DataNotFoundException {
//        // Aquí va la lógica para obtener imágenes de la Tierra desde el espacio (EPIC) en un rango de fechas
//        return null;
//    }
//
//    public EpicData obtenerImagenesEPIC(LocalDate date, String satellite) throws DataNotFoundException {
//        // Aquí va la lógica para obtener imágenes de la Tierra desde el espacio (EPIC) en una fecha específica y con un satélite específico
//        return null;
//    }
//
//    public EpicData obtenerImagenesEPIC(LocalDate startDate, LocalDate endDate, String satellite) throws DataNotFoundException {
//        // Aquí va la lógica para obtener imágenes de la Tierra desde el espacio (EPIC) en un rango de fechas y con un satélite específico
//        return null;
//    }
//
//    public ApodData obtenerImagenDiaNacimiento(LocalDate date) throws DataNotFoundException {
//        // Aquí va la lógica para obtener la imagen del día de nacimiento
//        return null;
//    }


}
