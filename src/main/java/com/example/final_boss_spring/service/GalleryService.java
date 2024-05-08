package com.example.final_boss_spring.service;

import com.example.final_boss_spring.config.NasaApiConfig;
import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.GalleryData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

@Service
public class GalleryService {
    private static final String API_KEY = NasaApiConfig.API_KEY;
    private static final String BASE_URL = NasaApiConfig.BASE_URL;

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

                // Imprimir la respuesta completa en consola
                System.out.println("Respuesta JSON recibida de la API de la NASA:");
                System.out.println(response.toString());  // Imprime el JSON completo

                // Convertir la respuesta JSON en un objeto GalleryData utilizando ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                GalleryData result = objectMapper.readValue(response.toString(), GalleryData.class);

                // Devolver el resultado sin procesar la colección de imágenes
                return result;
            } else {
                throw new DataNotFoundException("No se pudo obtener la galería por defecto. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            throw new DataNotFoundException("Error al comunicarse con la API de la NASA: " + e.getMessage());
        }
    }


}
