package com.kata.tennis.model;

import com.kata.tennis.enums.TennisGameStatus;
import com.kata.tennis.exceptions.PlayerNotFoundException;
import lombok.Data;

@Data
public class TennisGame {
    private ScorePlayer scorePlayerOne;
    private ScorePlayer scorePlayerTwo;

    private TennisGameStatus status = TennisGameStatus.STARTED;

    private Player playerHasAdvantage;

    private Player playerWinner;

    public TennisGame(ScorePlayer scorePlayerOne, ScorePlayer scorePlayerTwo) {
        this.scorePlayerOne = scorePlayerOne;
        this.scorePlayerTwo = scorePlayerTwo;
    }

    public ScorePlayer getScorePlayerById(Long playerId) {
        if (playerId == scorePlayerOne.getPlayer().getId()) {
            return scorePlayerOne;
        }
        if (playerId == scorePlayerTwo.getPlayer().getId()) {
            return scorePlayerTwo;
        }
        throw new PlayerNotFoundException("the player with id : " + playerId + " not found");
    }
}

