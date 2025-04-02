package com.kata.tennis.service.impl;

import com.kata.tennis.model.ScorePlayer;
import com.kata.tennis.model.TennisGame;
import com.kata.tennis.service.TennisService;
import com.kata.tennis.utils.TennisGameInitializer;
import org.springframework.stereotype.Service;

@Service
public class TennisServiceImpl implements TennisService {

    private TennisGame tennisGame = TennisGameInitializer.initializeTennisGame();

    @Override
    public void wonPoint(Long playerId) {
        if (!tennisGame.isRunning()) {
            return;
        }
        ScorePlayer scorePlayer = tennisGame.getScorePlayerById(playerId);
        scorePlayer.incrementPoint();
        ScorePlayer scorePlayerOne = tennisGame.getScorePlayerOne();
        ScorePlayer scorePlayerTwo = tennisGame.getScorePlayerTwo();
        int difference = scorePlayerOne.getScore() - scorePlayerTwo.getScore();
        if (difference == 0) {
            tennisGame.setDeuce(true);
            return;
        }
        if (scorePlayerOne.getScore() < 4 && scorePlayerTwo.getScore() < 4) {
            return;
        }

        if (Math.abs(difference) >= 2) {
            tennisGame.setRunning(false);
        }
    }
}
