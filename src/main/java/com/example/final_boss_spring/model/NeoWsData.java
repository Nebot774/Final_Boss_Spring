package com.example.final_boss_spring.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Representa la respuesta completa de la API para los asteroides cercanos a la Tierra.
 * Utiliza clases internas para mapear estructuras anidadas y proporcionar un manejo claro de los datos complejos.
 */
@Data
public class NeoWsData {

    private Links links;
    private int element_count;
    private Map<String, List<NearEarthObject>> near_earth_objects;

    /**
     * Clase para almacenar los enlaces de navegación proporcionados en la respuesta de la API.
     */
    @Data
    public static class Links {
        private String next;
        private String prev;
        private String self;
    }

    /**
     * Representa un objeto cercano a la Tierra, encapsulando todos los datos específicos de un asteroide.
     */
    @Data
    public static class NearEarthObject {
        private Links links;
        private String id;
        private String neo_reference_id;
        private String name;
        private String nasa_jpl_url;
        private double absolute_magnitude_h;
        private EstimatedDiameter estimated_diameter;
        private boolean is_potentially_hazardous_asteroid;
        private List<CloseApproachData> close_approach_data;
        private boolean is_sentry_object;

        /**
         * Clase para describir el diámetro estimado en diferentes unidades de medida.
         */
        @Data
        public static class EstimatedDiameter {
            private Diameter kilometers;
            private Diameter meters;
            private Diameter miles;
            private Diameter feet;

            @Data
            public static class Diameter {
                private double estimated_diameter_min;
                private double estimated_diameter_max;
            }
        }

        /**
         * Clase para representar los datos de acercamiento cercano de un asteroide a la Tierra.
         */
        @Data
        public static class CloseApproachData {
            private String close_approach_date;
            private String close_approach_date_full;
            private long epoch_date_close_approach;
            private RelativeVelocity relative_velocity;
            private MissDistance miss_distance;
            private String orbiting_body;

            @Data
            public static class RelativeVelocity {
                private double kilometers_per_second;
                private double kilometers_per_hour;
                private double miles_per_hour;
            }

            @Data
            public static class MissDistance {
                private double astronomical;
                private double lunar;
                private double kilometers;
                private double miles;
            }
        }
    }
}

