package com.example.final_boss_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NeoWsNeoData {
    /**
     *Clases y atributos relacionados con la información general del objeto NEO (Near Earth Object)
     */
    private Links links;
    private String id;
    private String neo_reference_id;
    private String name;
    private String nasa_jpl_url;
    private double absolute_magnitude_h;
    private EstimatedDiameter estimated_diameter;
    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean isPotentiallyHazardousAsteroid;
    private List<CloseApproachData> close_approach_data;
    private boolean is_sentry_object;

    /**
     *Clase Links que contiene el enlace a la información del objeto NEO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private String self;
    }

    /**
     *Clase EstimatedDiameter que contiene la información del diámetro estimado del objeto NEO en diferentes unidades de medida
     */
    @Data
    public static class EstimatedDiameter {
        private Diameter kilometers;
        private Diameter meters;
        private Diameter miles;
        private Diameter feet;

        /**
         *Clase Diameter que contiene el diámetro mínimo y máximo estimado
         */
        @Data
        public static class Diameter {
            private double estimated_diameter_min;
            private double estimated_diameter_max;
        }
    }

    /**
     *Clase CloseApproachData que contiene la información de los acercamientos cercanos del objeto NEO
     */
    @Data
    public static class CloseApproachData {
        private String close_approach_date;
        private String close_approach_date_full;
        private long epoch_date_close_approach;
        private RelativeVelocity relative_velocity;
        private MissDistance miss_distance;
        private String orbiting_body;

        /**
         *Clase RelativeVelocity que contiene la velocidad relativa del objeto NEO en diferentes unidades de medida
         */
        @Data
        public static class RelativeVelocity {
            private double kilometers_per_second;
            private double kilometers_per_hour;
            private double miles_per_hour;
        }

        /**
         *Clase MissDistance que contiene la distancia de fallo del objeto NEO en diferentes unidades de medida
         */
        @Data
        public static class MissDistance {
            private double astronomical;
            private double lunar;
            private double kilometers;
            private double miles;
        }
    }
}