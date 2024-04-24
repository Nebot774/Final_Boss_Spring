package com.example.final_boss_spring;

import static org.junit.jupiter.api.Assertions.*;

import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.GalleryData;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ServerErrorException;
import com.example.final_boss_spring.service.NasaService;
import java.net.HttpURLConnection;
import java.net.URL;

public class GalleryDataServiceTest {

    private NasaService nasaService = new NasaService();

//    @Test
//    public void testBuscarGaleriaSuccess() {
//        try {
//            // Asumiendo que la llamada es exitosa y se recibe un objeto GalleryData
//            GalleryData result = nasaService.buscarGaleria("planets", "image", "2010", "2020");
//            assertNotNull(result);
//
//            // Verificar que se reciben datos y mostrar en consola
//            if (result != null && result.getCollection() != null && !result.getCollection().getItems().isEmpty()) {
//                for (GalleryData.Item item : result.getCollection().getItems()) {
//                    for (GalleryData.ItemData data : item.getData()) {
//                        System.out.println("Center: " + data.getCenter());
//                        System.out.println("Date Created: " + data.getDateCreated());
//                        System.out.println("Description: " + data.getDescription());
//                        System.out.println("Media Type: " + data.getMediaType());
//                        System.out.println("Nasa ID: " + data.getNasaId());
//                        System.out.println("Title: " + data.getTitle());
//                        System.out.println("Keywords: " + String.join(", ", data.getKeywords()));
//                        System.out.println("------------------------------------------------");
//                    }
//                }
//            }
//        } catch (BadRequestException e) {
//            fail("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
//        }
//    }

    @Test
    public void testBuscarGaleriaPorDefecto() {
        try {
            // Llamar al método buscarGaleriaPorDefecto
            GalleryData result = nasaService.buscarGaleriaPorDefecto();

            // Check if result or its properties are null or empty
            if (result == null) {
                System.out.println("Result is null");
            } else if (result.getCollection() == null) {
                System.out.println("Collection is null");
            } else if (result.getCollection().getItems().isEmpty()) {
                System.out.println("Items list is empty");
            } else {
                // Verificar que se reciben datos y mostrar en consola
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
                        System.out.println("------------------------------------------------");
                    }
                }
            }
        } catch (DataNotFoundException e) {
            fail("No se pudo obtener la información de la galería de la NASA.");
        }
    }

//    @Test
//    public void testBuscarGaleriaBadRequest() {
//        // Test para simular una respuesta con código HTTP 400
//        assertThrows(BadRequestException.class, () -> {
//            nasaService.buscarGaleria("", "image", "2010", "2020");
//        });
//    }
//
//    @Test
//    public void testBuscarGaleriaNotFound() {
//        // Test para simular una respuesta con código HTTP 404
//        assertThrows(DataNotFoundException.class, () -> {
//            nasaService.buscarGaleria("nonexistentquery", "image", "2010", "2020");
//        });
//    }
//
//    @Test
//    public void testBuscarGaleriaServerError() {
//        // Test para simular una respuesta con código HTTP 500
//        assertThrows(ServerErrorException.class, () -> {
//            nasaService.buscarGaleria("planets", "image", "2010", "2020");
//        });
//    }
//
//    @Test
//    public void testBuscarGaleriaNetworkError() {
//        // Test para simular un error de red, como un IOException
//        assertThrows(DataNotFoundException.class, () -> {
//            nasaService.buscarGaleria("planets", "image", "2010", "2020");
//        });
//    }
}