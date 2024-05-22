package com.example.final_boss_spring.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarsRover {
    private List<MarsRoverPhoto> photos;


    /**
     * Representa una foto individual tomada por el Mars Rover, encapsulando todos los datos específicos de una foto.
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MarsRoverPhoto {
        private Long id;
        private Integer sol;
        private Camera camera;

        @JsonProperty("img_src")
        private String imgSrc;

        @JsonProperty("earth_date")
        private String earthDate;

        private Rover rover;


        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }
    }


    /**
     * Representa la cámara que tomó una foto, encapsulando todos los datos específicos de una cámara.
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Camera {
        private Long id;
        private String name;

        @JsonProperty("rover_id")
        private Long roverId;

        @JsonProperty("full_name")
        private String fullName;
    }


    /**
     * Representa el rover que tomó una foto, encapsulando todos los datos específicos de un rover.
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rover {
        private Long id;
        private String name;

        @JsonProperty("landing_date")
        private String landingDate;

        @JsonProperty("launch_date")
        private String launchDate;

        private String status;

        @JsonProperty("max_sol")
        private Integer maxSol;

        @JsonProperty("max_date")
        private String maxDate;

        @JsonProperty("total_photos")
        private Integer totalPhotos;

        private List<Camera> cameras;
    }
}

