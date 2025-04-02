package com.kata.tennis.controller;

import com.kata.tennis.service.TennisService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tennis")
public class TennisController {

    private final TennisService tennisService;

    public TennisController(TennisService tennisService) {
        this.tennisService = tennisService;
    }

    @PostMapping(path = "/wonPoint/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void wonPoint(@Valid @PathVariable("id") Long playerId) {
        tennisService.wonPoint(playerId);
    }

    @GetMapping(path = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getScore() {
        return tennisService.getScore();
    }

    @PostMapping(path = "/restartGame", produces = MediaType.APPLICATION_JSON_VALUE)
    public void restartGame() {
        tennisService.resetGame();
    }
}
