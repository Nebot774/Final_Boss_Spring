package com.example.final_boss_spring.service;

import com.example.final_boss_spring.exception.MyServerErrorException;
import com.example.final_boss_spring.model.ApodData;
import com.example.final_boss_spring.model.GalleryData;
import com.example.final_boss_spring.model.NeoWsData;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import com.example.final_boss_spring.exception.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public GalleryData buscarGaleria(String query, String mediaType, String yearStart, String yearEnd) throws DataNotFoundException, BadRequestException, ServerErrorException {
        String baseUrl = "https://images-api.nasa.gov/search";

        try {
            // Construir la URL con parámetros de consulta
            String urlString = baseUrl + "?q=" + URLEncoder.encode(query, "UTF-8") +
                    (mediaType != null && !mediaType.isEmpty() ? "&media_type=" + mediaType : "") +
                    (yearStart != null && !yearStart.isEmpty() ? "&year_start=" + yearStart : "") +
                    (yearEnd != null && !yearEnd.isEmpty() ? "&year_end=" + yearEnd : "") +
                    "&api_key=" + API_KEY;

            // Iniciar la conexión y realizar la petición GET
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Comprobar el código de respuesta HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.next());
                }
                scanner.close();

                // Convertir la respuesta en cadena JSON a un objeto GalleryData utilizando ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), GalleryData.class);
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new BadRequestException("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new DataNotFoundException("El recurso solicitado no existe.");
            } else if (responseCode >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new MyServerErrorException("Ocurrió un error en el servidor.");
            } else {
                throw new RuntimeException("Respuesta inesperada del servidor: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataNotFoundException("No se pudo obtener la información de la galería de la NASA debido a un error de red.");
        }
    }

    public GalleryData buscarGaleriaPorDefecto() throws DataNotFoundException {
        // URL base de la galería de imágenes de la NASA
        String urlString = "https://images-api.nasa.gov/search?media_type=image";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();

                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }

                scanner.close();
                connection.disconnect();

                // Convertir la respuesta JSON en un objeto GalleryData utilizando ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), GalleryData.class);
            } else {
                throw new DataNotFoundException("No se pudo obtener la galería por defecto. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            throw new DataNotFoundException("Error al comunicarse con la API de la NASA: " + e.getMessage());
        }
    }



//
//    public MarsRoverData obtenerFotosMarsRovers(String rover, int sol) throws DataNotFoundException {
//        // Aquí va la lógica para obtener fotos de los Mars Rovers
//        return null;
//    }
//

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
