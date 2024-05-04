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

    @Test
    public void testBuscarGaleria() {
        try {
            // Llamar al método buscarGaleria con parámetros de prueba
            GalleryData result = nasaService.buscarGaleria("moon", "image", "", "");

            // Verificar que el resultado no es null
            assertNotNull(result);

            // Verificar que la colección no es null
            assertNotNull(result.getCollection());

            // Verificar que la lista de items no está vacía
            assertFalse(result.getCollection().getItems().isEmpty());

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
                    System.out.println("Enlace de la imagen pequeña: " + item.getHref());
                    System.out.println("------------------------------------------------");
                }
            }
        } catch (BadRequestException e) {
            fail("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
        } catch (ServerErrorException e) {
            fail("Ocurrió un error en el servidor.");
        } catch (DataNotFoundException e) {
            fail("No se pudo obtener la información de la galería de la NASA.");
        }
    }

 //   @Test
//    public void testBuscarGaleriaQuery() {
//        try {
//            // Llamar al método buscarGaleriaQuery con parámetros de prueba
//            GalleryData result = nasaService.buscarGaleriaQuery("apollo 11");
//
//            // Verificar que el resultado no es null
//            assertNotNull(result);
//
//            // Verificar que la colección no es null
//            assertNotNull(result.getCollection());
//
//            // Verificar que la lista de items no está vacía
//            assertFalse(result.getCollection().getItems().isEmpty());
//
//            // Verificar que se reciben datos y mostrar en consola
//            for (GalleryData.Item item : result.getCollection().getItems()) {
//                for (GalleryData.ItemData data : item.getData()) {
//                    System.out.println("Center: " + data.getCenter());
//                    System.out.println("Date Created: " + data.getDateCreated());
//                    System.out.println("Description: " + data.getDescription());
//                    System.out.println("Media Type: " + data.getMediaType());
//                    System.out.println("Nasa ID: " + data.getNasaId());
//                    System.out.println("Title: " + data.getTitle());
//                    if (data.getKeywords() != null) {
//                        System.out.println("Keywords: " + String.join(", ", data.getKeywords()));
//                    } else {
//                        System.out.println("Keywords: null");
//                    }
//                    System.out.println("------------------------------------------------");
//                }
//            }
//        } catch (BadRequestException e) {
//            fail("La solicitud es incorrecta, a menudo debido a la falta de un parámetro requerido.");
//        } catch (ServerErrorException e) {
//            fail("Ocurrió un error en el servidor.");
//        } catch (DataNotFoundException e) {
//            fail("No se pudo obtener la información de la galería de la NASA.");
//        }
//    }

//    @Test
//    public void testBuscarGaleriaPorDefecto() {
//        try {
//            // Llamar al método buscarGaleriaPorDefecto
//            GalleryData result = nasaService.buscarGaleriaPorDefecto();
//
//            // Check if result or its properties are null or empty
//            assertNotNull(result, "Result is null");
//            assertNotNull(result.getCollection(), "Collection is null");
//            assertFalse(result.getCollection().getItems().isEmpty(), "Items list is empty");
//
//            // Verificar que se reciben datos y mostrar en consola
//            for (GalleryData.Item item : result.getCollection().getItems()) {
//                for (GalleryData.ItemData data : item.getData()) {
//                    System.out.println("Center: " + data.getCenter());
//                    System.out.println("Date Created: " + data.getDateCreated());
//                    System.out.println("Description: " + data.getDescription());
//                    System.out.println("Media Type: " + data.getMediaType());
//                    System.out.println("Nasa ID: " + data.getNasaId());
//                    System.out.println("Title: " + data.getTitle());
//                    if (data.getKeywords() != null) {
//                        System.out.println("Keywords: " + String.join(", ", data.getKeywords()));
//                    } else {
//                        System.out.println("Keywords: null");
//                    }
//
//                    // Verificar que el enlace de la imagen no es nulo y contiene "~small.jpg"
//                    assertNotNull(item.getHref(), "Image link is null");
//                    assertTrue(item.getHref().contains("~small.jpg"), "Image link does not contain '~small.jpg'");
//                    System.out.println("Enlace de la imagen pequeña: " + item.getHref());
//                }
//            }
//        } catch (DataNotFoundException e) {
//            fail("No se pudo obtener la información de la galería de la NASA.");
//        }
//    }


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