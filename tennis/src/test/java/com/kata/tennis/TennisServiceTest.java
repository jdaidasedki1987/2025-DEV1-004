package com.kata.tennis;

import com.kata.tennis.model.TennisGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TennisServiceTest {

    @InjectMocks
    private TennisServiceImpl tennisService;

    TennisGame tennisGame = TennisGameInitializer.getInstance();


    @Test
    void testWonPoint_GameNotRunning() {
        //player won 4 points
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        assertEquals(4, tennisGame.getScorePlayerOne().getScore());
        assertEquals(0, tennisGame.getScorePlayerTwo().getScore());
        //Game must be finished
        assertFalse(tennisGame.isRunning());
        //player one won point after game is finished
        tennisService.wonPoint(1L);
        assertEquals(4, tennisGame.getScorePlayerOne().getScore());
    }

    @Test
    void shouldIncreaseScore_WhenPlayersWinPoints() {
        //player one won point
        tennisService.wonPoint(1L);
        assertEquals(1, tennisGame.getScorePlayerOne().getScore());
        assertEquals(0, tennisGame.getScorePlayerTwo().getScore());
        //player two won point
        tennisService.wonPoint(2L);
        assertEquals(1, tennisGame.getScorePlayerOne().getScore());
        assertEquals(1, tennisGame.getScorePlayerTwo().getScore());
        //player one won point
        tennisService.wonPoint(1L);
        assertEquals(2, tennisGame.getScorePlayerOne().getScore());
        assertEquals(1, tennisGame.getScorePlayerTwo().getScore());
    }

    @Test
    void shouldReachDeuce_WhenBothPlayersHaveThreePoints() {
        assertFalse(tennisGame.isDeuce());
        for (int i = 0; i < 3; i++) {
            tennisService.wonPoint(1L);
            tennisService.wonPoint(2L);
        }
        assertTrue(tennisGame.isDeuce());
    }

}
