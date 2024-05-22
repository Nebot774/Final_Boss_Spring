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

    public GalleryData buscarGaleria(String query, String mediaType, String yearStart, String yearEnd, int numResults)
            throws DataNotFoundException, BadRequestException, ServerErrorException {

        String baseUrl = "https://images-api.nasa.gov/search";
        StringBuilder urlString = new StringBuilder(baseUrl);

        int maxPageSize = 100;  // Máximo número de resultados por página que soporta la API
        int pageSize = Math.min(numResults, maxPageSize);  // Determina el tamaño de página adecuado
        int page = (int) Math.ceil((double) numResults / maxPageSize);  // Calcula el número de página necesario

        // Marcador para saber si ya se ha añadido algún parámetro a la URL
        boolean firstParam = true;

        try {
            // Añade el parámetro de consulta si existe
            if (query != null && !query.isEmpty()) {
                urlString.append(firstParam ? "?q=" : "&q=").append(URLEncoder.encode(query, "UTF-8"));
                firstParam = false;
            }
            // Añade el parámetro del tipo de medio si existe
            if (mediaType != null && !mediaType.isEmpty()) {
                urlString.append(firstParam ? "?media_type=" : "&media_type=").append(mediaType);
                firstParam = false;
            }
            // Añade el parámetro del año de inicio si existe
            if (yearStart != null && !yearStart.isEmpty()) {
                urlString.append(firstParam ? "?year_start=" : "&year_start=").append(yearStart);
                firstParam = false;
            }
            // Añade el parámetro del año de fin si existe
            if (yearEnd != null && !yearEnd.isEmpty()) {
                urlString.append(firstParam ? "?year_end=" : "&year_end=").append(yearEnd);
                firstParam = false;
            }
            // Añade el número de página y tamaño de página a la URL
            urlString.append(firstParam ? "?page=" : "&page=").append(page);
            urlString.append(firstParam ? "?page_size=" : "&page_size=").append(pageSize);
        } catch (UnsupportedEncodingException e) {
            // Lanza una excepción si hay un error al codificar los parámetros
            throw new DataNotFoundException("Error al codificar los parámetros de la consulta.", e);
        }

        System.out.println("URL: " + urlString.toString());  // Imprime la URL completa

        try {
            // Conecta a la URL generada
            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lee la respuesta de la API
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.next());
                }
                scanner.close();

                // Mapea la respuesta JSON a la clase GalleryData
                ObjectMapper objectMapper = new ObjectMapper();
                GalleryData result = objectMapper.readValue(response.toString(), GalleryData.class);

                // Itera sobre los ítems de la galería
                for (GalleryData.Item item : result.getCollection().getItems()) {
                    for (GalleryData.ItemData data : item.getData()) {
                        // Imprime detalles de cada ítem
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

                        // Procesa los enlaces de las imágenes
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

                        // Mapea la respuesta de enlaces a una lista de strings
                        List<String> imageLinks = objectMapper.readValue(response2.toString(), new TypeReference<List<String>>(){});
                        String smallImageLink = null;
                        // Busca la imagen pequeña
                        for (String link : imageLinks) {
                            if (link.contains("~small.jpg")) {
                                smallImageLink = link;
                                break;
                            }
                        }

                        // Imprime el enlace de la imagen pequeña si existe
                        if (smallImageLink != null) {
                            System.out.println("Enlace de la imagen pequeña: " + smallImageLink);
                        } else {
                            System.out.println("No se encontró la imagen pequeña.");
                        }
                    }
                }
                return result;
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                // Maneja la respuesta de solicitud incorrecta
                throw new BadRequestException("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                // Maneja la respuesta de recurso no encontrado
                throw new DataNotFoundException("El recurso solicitado no existe.");
            } else if (responseCode >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
                // Maneja la respuesta de error en el servidor
                throw new RuntimeException("Ocurrió un error en el servidor.");
            } else {
                // Maneja cualquier otra respuesta inesperada
                throw new RuntimeException("Respuesta inesperada del servidor: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Lanza una excepción si hay un error de red
            throw new DataNotFoundException("No se pudo obtener la información de la galería de la NASA debido a un error de red.");
        }
    }


    public GalleryData buscarGaleriaPorDefecto() throws DataNotFoundException {
        // URL base de la galería de imágenes de la NASA, buscando solo imágenes
        String urlString = "https://images-api.nasa.gov/search?media_type=image";

        try {
            // Crear una URL a partir del string urlString
            URL url = new URL(urlString);
            // Abrir una conexión HTTP a la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establecer el método de solicitud a GET
            connection.setRequestMethod("GET");

            // Obtener el código de respuesta de la conexión
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta de la API usando un Scanner
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();

                // Leer cada línea de la respuesta y añadirla a response
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                // Cerrar el scanner y la conexión
                scanner.close();
                connection.disconnect();

                // Imprimir la respuesta completa en consola para depuración
                System.out.println("Respuesta JSON recibida de la API de la NASA:");
                System.out.println(response.toString());

                // Convertir la respuesta JSON en un objeto GalleryData utilizando ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                GalleryData result = objectMapper.readValue(response.toString(), GalleryData.class);

                // Devolver el resultado sin procesar la colección de imágenes
                return result;
            } else {
                // Lanza una excepción si el código de respuesta no es HTTP_OK
                throw new DataNotFoundException("No se pudo obtener la galería por defecto. Código de respuesta: " + responseCode);
            }
        } catch (IOException e) {
            // Lanza una excepción si hay un error de comunicación con la API
            throw new DataNotFoundException("Error al comunicarse con la API de la NASA: " + e.getMessage());
        }
    }



}
