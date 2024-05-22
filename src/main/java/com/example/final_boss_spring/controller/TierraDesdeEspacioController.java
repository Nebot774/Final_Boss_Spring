package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.model.TierraDesdeEspacio;
import com.example.final_boss_spring.service.TierraDesdeEspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("nasa")
public class TierraDesdeEspacioController {

    @Autowired
    private TierraDesdeEspacioService tierraDesdeEspacioService;

    @GetMapping("/earth/imagery")
    public TierraDesdeEspacio getTierraDesdeEspacio(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam String date) {
        return tierraDesdeEspacioService.fetchTierraDesdeEspacio(lat, lon, date);
    }
}

