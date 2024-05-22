package com.example.final_boss_spring.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Esta clase representa un objeto de tipo ApodData, que es el objeto que se obtiene de la API de la NASA
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApodData {
    private String date;
    private String explanation;
    private String mediaType;
    private String serviceVersion;
    private String title;
    private String url;
    private String copyright;

}