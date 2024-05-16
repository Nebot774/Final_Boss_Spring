package com.example.final_boss_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionManifest {
    @JsonProperty("photo_manifest")
    private PhotoManifest photoManifest;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhotoManifest {
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
        private List<SolInfo> photos;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SolInfo {
            private Integer sol;
            @JsonProperty("earth_date")
            private String earthDate;
            @JsonProperty("total_photos")
            private Integer totalPhotos;
            private List<String> cameras;
        }
    }
}

