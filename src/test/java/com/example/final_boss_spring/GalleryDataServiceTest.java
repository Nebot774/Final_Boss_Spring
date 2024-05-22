package com.example.final_boss_spring;

import static org.junit.jupiter.api.Assertions.*;

import com.example.final_boss_spring.controller.GalleryController;
import com.example.final_boss_spring.exception.DataNotFoundException;
import com.example.final_boss_spring.model.GalleryData;
import com.example.final_boss_spring.service.GalleryService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ServerErrorException;

public class GalleryDataServiceTest {

    private GalleryService nasaService = new GalleryService();

    @Test
    public void testBuscarGaleria() {
        try {
            // Llamar al método buscarGaleria con parámetros de prueba
            GalleryData result = nasaService.buscarGaleria("moon", "image", "", "",1);

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


    @Test
    public void testBuscarGaleriaPorDefecto() {

        GalleryService nasaService = new GalleryService ();  // Asegúrate de que nasaService esté inicializado correctamente

        try {
            // Llamar al método buscarGaleriaPorDefecto
            GalleryData result = nasaService.buscarGaleriaPorDefecto();

            // Verificar que el resultado y sus propiedades no son nulos o vacíos
            assertNotNull(result, "El resultado es nulo");
            assertNotNull(result.getCollection(), "La colección es nula");
            assertFalse(result.getCollection().getItems().isEmpty(), "La lista de ítems está vacía");

            // Verificar que se reciben datos y mostrarlos en consola
            for (GalleryData.Item item : result.getCollection().getItems()) {
                System.out.println("Ítem HREF: " + item.getHref()); // Imprime el enlace de la colección
                for (GalleryData.ItemData data : item.getData()) {
                    System.out.println("Centro: " + data.getCenter());
                    System.out.println("Fecha de creación: " + data.getDateCreated());
                    System.out.println("Descripción: " + data.getDescription());
                    System.out.println("Tipo de medio: " + data.getMediaType());
                    System.out.println("ID de NASA: " + data.getNasaId());
                    System.out.println("Título: " + data.getTitle());
                    if (data.getKeywords() != null && !data.getKeywords().isEmpty()) {
                        System.out.println("Palabras clave: " + String.join(", ", data.getKeywords()));
                    } else {
                        System.out.println("Palabras clave: nulas o vacías");
                    }
                }

                // Verificar que la lista de enlaces de imágenes no es nula ni está vacía
                assertNotNull(item.getImageLinks(), "La lista de enlaces de imágenes es nula");
                assertFalse(item.getImageLinks().isEmpty(), "La lista de enlaces de imágenes está vacía");
                // Imprime todos los enlaces de imágenes
                System.out.println("Enlaces de imágenes:");
                for (String link : item.getImageLinks()) {
                    System.out.println(link);
                }
            }
        } catch (DataNotFoundException e) {
            fail("No se pudo obtener la información de la galería de la NASA debido a: " + e.getMessage());
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }





}
}

