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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

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

    public GalleryData buscarGaleriaQuery(String query) throws DataNotFoundException, BadRequestException, ServerErrorException {
        String baseUrl = "https://images-api.nasa.gov/search";

        try {
            // Codificar los parámetros de la consulta
            String encodedQuery = URLEncoder.encode(query, "UTF-8");


            // Construir la URL con los parámetros de consulta codificados
            String urlString = baseUrl + "?q=" + encodedQuery  + "&api_key=" + API_KEY;
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


    public GalleryData buscarGaleria(String query, String mediaType, String yearStart, String yearEnd, int numResults) throws DataNotFoundException, BadRequestException, ServerErrorException {
        String baseUrl = "https://images-api.nasa.gov/search";
        StringBuilder urlString = new StringBuilder(baseUrl);

        int maxPageSize = 100;  // Máximo número de resultados por página que soporta la API
        int pageSize = Math.min(numResults, maxPageSize);  // Número de resultados por página
        int page = (int) Math.ceil((double) numResults / maxPageSize);  // Número de página

        // Agregamos un marcador para saber si ya se ha añadido algún parámetro
        boolean firstParam = true;

        try {
            if (query != null && !query.isEmpty()) {
                urlString.append(firstParam ? "?q=" : "&q=").append(URLEncoder.encode(query, "UTF-8"));
                firstParam = false;
            }
            if (mediaType != null && !mediaType.isEmpty()) {
                urlString.append(firstParam ? "?media_type=" : "&media_type=").append(mediaType);
                firstParam = false;
            }
            if (yearStart != null && !yearStart.isEmpty()) {
                urlString.append(firstParam ? "?year_start=" : "&year_start=").append(yearStart);
                firstParam = false;
            }
            if (yearEnd != null && !yearEnd.isEmpty()) {
                urlString.append(firstParam ? "?year_end=" : "&year_end=").append(yearEnd);
                firstParam = false;
            }
            urlString.append(firstParam ? "?page=" : "&page=").append(page);
            urlString.append(firstParam ? "?page_size=" : "&page_size=").append(pageSize);
        } catch (UnsupportedEncodingException e) {
            throw new DataNotFoundException("Error al codificar los parámetros de la consulta.", e);
        }

        System.out.println("URL: " + urlString.toString());  // Imprime la URL completa

        try {
            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.next());
                }
                scanner.close();

                ObjectMapper objectMapper = new ObjectMapper();
                GalleryData result = objectMapper.readValue(response.toString(), GalleryData.class);



                for (GalleryData.Item item : result.getCollection().getItems()) {
                    for (GalleryData.ItemData data : item.getData()) {
                        System.out.println("Center: " + data.getCenter());
                        System.out.println("Date Created: " + data.getDateCreated());
                        System.out.println("Description: " + data.getDescription());
                        System.out.println("Media Type: " + data.getMediaType());
                        System.out.println("Nasa ID: " + data.getNasaId());
                        System.out.println("Title: " + data.getTitle());
                        if (data.getKeywords() != null) {
                            System.out.println("Keywords: " + String.join(", ", data.getKeywords()));
                        } else {
                            System.out.println("Keywords: null");
                        }

                        String href = item.getHref();
                        URL url2 = new URL(href);
                        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                        connection2.setRequestMethod("GET");
                        Scanner scanner2 = new Scanner(connection2.getInputStream());
                        StringBuilder response2 = new StringBuilder();
                        while (scanner2.hasNextLine()) {
                            response2.append(scanner2.nextLine());
                        }
                        scanner2.close();

                        // Parte de tu método donde procesas los enlaces
                        List<String> imageLinks = objectMapper.readValue(response2.toString(), new TypeReference<List<String>>(){});
                        String smallImageLink = null;
                        for (String link : imageLinks) {
                            if (link.contains("~small.jpg")) { // Esta condición solo añade enlaces que contengan "~small.jpg"
                                smallImageLink = link;
                                break;
                            }
                        }

                        if (smallImageLink != null) {
                            System.out.println("Enlace de la imagen pequeña: " + smallImageLink);
                        } else {
                            System.out.println("No se encontró la imagen pequeña.");
                        }


                    }
                }
                return result;
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new BadRequestException("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new DataNotFoundException("El recurso solicitado no existe.");
            } else if (responseCode >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new RuntimeException("Ocurrió un error en el servidor.");
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
                GalleryData result = objectMapper.readValue(response.toString(), GalleryData.class);

                for (GalleryData.Item item : result.getCollection().getItems()) {
                    for (GalleryData.ItemData data : item.getData()) {
                        System.out.println("Center: " + data.getCenter());
                        System.out.println("Date Created: " + data.getDateCreated());
                        System.out.println("Description: " + data.getDescription());
                        System.out.println("Media Type: " + data.getMediaType());
                        System.out.println("Nasa ID: " + data.getNasaId());
                        System.out.println("Title: " + data.getTitle());
                        if (data.getKeywords() != null) {
                            System.out.println("Keywords: " + String.join(", ", data.getKeywords()));
                        } else {
                            System.out.println("Keywords: null");
                        }

                        String href = item.getHref();
                        URL url2 = new URL(href);
                        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                        connection2.setRequestMethod("GET");
                        Scanner scanner2 = new Scanner(connection2.getInputStream());
                        StringBuilder response2 = new StringBuilder();
                        while (scanner2.hasNextLine()) {
                            response2.append(scanner2.nextLine());
                        }
                        scanner2.close();

                        List<String> imageLinks = objectMapper.readValue(response2.toString(), new TypeReference<List<String>>(){});

                        String smallImageLink = null;
                        for (String link : imageLinks) {
                            if (link.contains("~small.jpg")) {
                                smallImageLink = link;
                                break;
                            }
                        }

                        if (smallImageLink != null) {
                            System.out.println("Enlace de la imagen pequeña: " + smallImageLink);
                            data.setSmallImageUrl(smallImageLink); // Añade esta línea
                        } else {
                            System.out.println("No se encontró la imagen pequeña.");
                        }
                    }
                }
                return result;
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
