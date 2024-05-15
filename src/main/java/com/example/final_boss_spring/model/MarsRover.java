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

        // El nombre de la propiedad debe coincidir con el campo en JSON
        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }
    }

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

