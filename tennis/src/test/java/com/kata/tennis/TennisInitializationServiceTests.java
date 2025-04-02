package com.kata.tennis;

import com.kata.tennis.model.Player;
import com.kata.tennis.model.ScorePlayer;
import com.kata.tennis.model.TennisGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kata.tennis.enums.TennisGameStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TennisInitializationServiceTests {

    private Player playerOne;
    private Player playerTwo;
    private TennisGame tennisGame;


    @BeforeEach
    void setUp() {
        playerOne = new Player(1L, "PlayerOne");
        playerTwo = new Player(2L, "PlayerTwo");
        tennisGame = new TennisGame(new ScorePlayer(playerOne), new ScorePlayer(playerTwo));
    }


    @Test
    void testPlayersInitialization() {
        assertEquals(1L, playerOne.getId());
        assertEquals(2L, playerTwo.getId());
        assertEquals("PlayerOne", playerOne.getName());
        assertEquals("PlayerTwo", playerTwo.getName());
    }

    @Test
    void testTennisGameInitialization() {
        assertNotNull(tennisGame.getScorePlayerOne());
        assertNotNull(tennisGame.getScorePlayerTwo());
        assertEquals(0, tennisGame.getScorePlayerOne().getScore());
        assertEquals(0, tennisGame.getScorePlayerTwo().getScore());
        assertEquals(STARTED, tennisGame.getStatus());
    }


}
