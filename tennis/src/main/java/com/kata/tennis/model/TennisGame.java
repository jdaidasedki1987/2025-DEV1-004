package com.kata.tennis.model;

import lombok.Data;

@Data
public class TennisGame {
    private ScorePlayer scorePlayerOne;
    private ScorePlayer scorePlayerTwo;

    private boolean isRunning = true;

    private boolean isDeuce = false;

    public TennisGame(ScorePlayer scorePlayerOne, ScorePlayer scorePlayerTwo) {
        this.scorePlayerOne = scorePlayerOne;
        this.scorePlayerTwo = scorePlayerTwo;
    }

}
