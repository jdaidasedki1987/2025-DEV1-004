package com.kata.tennis.model;


import lombok.Data;

@Data
public class ScorePlayer {
    private int score;
    private Player player;

    public ScorePlayer(Player player) {
        this.player = player;
    }

    public void incrementPoint() {
        score++;
    }
}
