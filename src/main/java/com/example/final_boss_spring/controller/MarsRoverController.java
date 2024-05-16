package com.example.final_boss_spring.controller;

import com.example.final_boss_spring.model.MarsRover;
import com.example.final_boss_spring.model.MissionManifest;
import com.example.final_boss_spring.service.MarsRoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarsRoverController {
    @Autowired
    private MarsRoverService marsRoverService;

    @GetMapping("/mars-rover/photos")
    public List<MarsRover.MarsRoverPhoto> getMarsRoverPhotos(@RequestParam String rover,
                                                             @RequestParam String date,
                                                             @RequestParam String camera) {
        return marsRoverService.getMarsRoverPhotos(rover, date, camera);
    }

    @GetMapping("/mars-rover/manifest")
    public MissionManifest.PhotoManifest getMissionManifest(@RequestParam String rover) {
        return marsRoverService.getMissionManifest(rover);
    }
}
