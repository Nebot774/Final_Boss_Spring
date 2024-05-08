package com.example.final_boss_spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Data
@NoArgsConstructor // Generates a no-args constructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GalleryData {
    private Collection collection;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Collection {
        private String href;
        private List<Item> items;
        private List<Link> links;
        private Metadata metadata;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private List<ItemData> data;
        private String href;
        private List<String> imageLinks; // New field for image links
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItemData {
        private String center;
        private String dateCreated;
        private String description;
        private List<String> keywords;
        private String mediaType;
        private String nasaId;
        private String title;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Link {
        private String href;
        private String rel;
        private String prompt;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {
        private int totalHits;
    }
}