package com.kata.tennis.utils;

import com.kata.tennis.model.Player;
import com.kata.tennis.model.ScorePlayer;
import com.kata.tennis.model.TennisGame;

/**
 * create singleton instance of tennis game
 */
public class TennisGameInitializer {

    private static TennisGame TENNIS_GAME = null;

    private TennisGameInitializer() {
    }

    public static TennisGame initializeTennisGame() {

        if (TENNIS_GAME == null) {
            Player playerOne = new Player(1L, "playerOne");
            Player playerTwo = new Player(2L, "playerTwo");
            ScorePlayer scorePlayerOne = new ScorePlayer(playerOne);
            ScorePlayer scorePlayerTwo = new ScorePlayer(playerTwo);
            TENNIS_GAME = new TennisGame(scorePlayerOne, scorePlayerTwo);
        }
        return TENNIS_GAME;
    }

    public static void reset() {
        TENNIS_GAME = null;
    }
}
