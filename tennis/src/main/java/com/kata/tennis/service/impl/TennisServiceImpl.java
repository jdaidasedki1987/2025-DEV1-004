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

        if (scorePlayerOne.getScore() < 3 && scorePlayerTwo.getScore() < 3) {
            return;
        }

        if (difference == 0) {
            tennisGame.setDeuce(true);
            tennisGame.setPlayerHasAdvantage(null);
            return;
        }
        if (scorePlayerOne.getScore() < 4 && scorePlayerTwo.getScore() < 4) {
            return;
        }

        if (Math.abs(difference) == 1) {
            tennisGame.setPlayerHasAdvantage(difference > 0 ? scorePlayerOne.getPlayer() : scorePlayerTwo.getPlayer());
            return;
        }

        if (Math.abs(difference) >= 2) {
            tennisGame.setPlayerWinner(difference > 0 ? scorePlayerOne.getPlayer() : scorePlayerTwo.getPlayer());
            tennisGame.setRunning(false);
        }

    }

    @Override
    public String getScore() {
        if (tennisGame.getPlayerWinner() != null) {
            return "The game is finished and the winner is : " + tennisGame.getPlayerWinner().getName();
        }
        if (tennisGame.getPlayerHasAdvantage() != null) {
            return "Advantage for the player : " + tennisGame.getPlayerHasAdvantage().getName();
        }
        if (tennisGame.isDeuce()) {
            return "Deuce";
        }

        return convertScorePlayer(tennisGame.getScorePlayerOne().getScore()) + " - " + convertScorePlayer(tennisGame.getScorePlayerTwo().getScore());

    }

    private String convertScorePlayer(int score) {
        return switch (score) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            default -> "40";
        };
    }
}
