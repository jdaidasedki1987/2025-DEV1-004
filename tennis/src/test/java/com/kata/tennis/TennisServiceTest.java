package com.kata.tennis;

import com.kata.tennis.exceptions.GameFinishedException;
import com.kata.tennis.exceptions.PlayerNotFoundException;
import com.kata.tennis.model.TennisGame;
import com.kata.tennis.service.impl.TennisServiceImpl;
import com.kata.tennis.utils.TennisGameInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.kata.tennis.enums.TennisGameStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TennisServiceTest {

    @InjectMocks
    private TennisServiceImpl tennisService;

    TennisGame tennisGame = null;

    @BeforeEach
    void setUp() {
        tennisGame = TennisGameInitializer.initializeTennisGame();
    }

    @AfterEach
    void cleanup() {
        TennisGameInitializer.reset();
    }

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
        assertEquals(FINISHED, tennisGame.getStatus());
        //player one won point after game is finished
        Exception exception = assertThrows(GameFinishedException.class, () -> tennisService.wonPoint(1L));
        assertEquals("You can't add points when game is finished", exception.getMessage());
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
        assertNotEquals(DEUCE, tennisGame.getStatus());
        giveThreePointsForEachPlayer();
        assertEquals(DEUCE, tennisGame.getStatus());
    }

    @Test
    void shouldGiveAdvantageToPlayerOne_WhenTheyScoreAfterDeuce() {
        giveThreePointsForEachPlayer();
        //The player One have another point
        tennisService.wonPoint(1L);
        assertNotNull(tennisGame.getPlayerHasAdvantage());
        assertEquals("playerOne", tennisGame.getPlayerHasAdvantage().getName());
    }

    @Test
    void shouldGiveAdvantageToPlayerTwo_WhenTheyScoreAfterDeuce() {
        giveThreePointsForEachPlayer();
        //The player two have another point
        tennisService.wonPoint(2L);
        assertNotNull(tennisGame.getPlayerHasAdvantage());
        assertEquals("playerTwo", tennisGame.getPlayerHasAdvantage().getName());
    }

    @Test
    void shouldReturnToDeuce_WhenAdvantagePlayerLosesNextPoint() {
        giveThreePointsForEachPlayer();
        //the player two have advantage then player one
        tennisService.wonPoint(2L);
        //the player two loses advantage  and game returns to deuce
        tennisService.wonPoint(1L);
        assertNull(tennisGame.getPlayerHasAdvantage());
        assertEquals(DEUCE, tennisGame.getStatus());
    }

    @Test
    void shouldDeclarePlayerOneAsWinner_WhenTheyWinEnoughPoints() {
        giveThreePointsForEachPlayer();
        tennisService.wonPoint(1L);
        assertNull(tennisGame.getPlayerWinner());
        tennisService.wonPoint(1L);
        assertNotNull(tennisGame.getPlayerWinner());
        assertEquals("playerOne", tennisGame.getPlayerWinner().getName());
        assertEquals(FINISHED,tennisGame.getStatus());

    }

    @Test
    void shouldDeclarePlayerTwoAsWinner_WhenTheyWinEnoughPoints() {
        tennisService.wonPoint(2L);
        tennisService.wonPoint(2L);
        assertNull(tennisGame.getPlayerWinner());
        tennisService.wonPoint(2L);
        tennisService.wonPoint(2L);
        assertNotNull(tennisGame.getPlayerWinner());
        assertEquals("playerTwo", tennisGame.getPlayerWinner().getName());
        assertEquals(FINISHED,tennisGame.getStatus());
    }

    @Test
    void shouldContinueGame_WhenBothPlayersHaveHighScores() {
        giveThreePointsForEachPlayer();
        giveThreePointsForEachPlayer();
        assertEquals(6, tennisGame.getScorePlayerOne().getScore());
        assertEquals(6, tennisGame.getScorePlayerTwo().getScore());
        assertNotEquals(FINISHED,tennisGame.getStatus());

    }

    @Test
    void shouldNotReachDeuce_WhenBothPlayersHaveTwoPoints() {
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(2L);
        tennisService.wonPoint(2L);
        assertNotEquals(DEUCE,tennisGame.getStatus());
    }

    @Test
    void shouldThrowException_WhenPlayerIdIsInvalid() {
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> tennisService.wonPoint(3L));
        assertEquals("the player with id : 3 not found", exception.getMessage());
    }

    @Test
    void shouldReturnCorrectScore_WhenGameIsNotFinished() {
        tennisService.wonPoint(2L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        assertEquals(STARTED,tennisGame.getStatus());
        assertEquals("30 - 15", tennisService.getScore());
    }

    @Test
    void shouldReturnDeuce_WhenPlayersHaveEqualHighScores() {
        giveThreePointsForEachPlayer();
        assertEquals("Deuce", tennisService.getScore());
    }

    @Test
    void shouldShowAdvantageForPlayerOne_WhenTheyScoreAfterDeuce() {
        giveThreePointsForEachPlayer();
        tennisService.wonPoint(1L);
        assertEquals("Advantage for the player : playerOne", tennisService.getScore());
        assertEquals(ADVANTAGE,tennisGame.getStatus());

    }

    @Test
    void shouldShowAdvantageForPlayerTwo_WhenTheyScoreAfterDeuce() {
        giveThreePointsForEachPlayer();
        tennisService.wonPoint(2L);
        assertEquals("Advantage for the player : playerTwo", tennisService.getScore());
        assertEquals(ADVANTAGE,tennisGame.getStatus());
    }

    @Test
    void shouldDeclarePlayerOneAsWinner_WhenTheyWinGame() {
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(1L);
        assertEquals("The game is finished and the winner is : playerOne", tennisService.getScore());
        assertEquals(FINISHED,tennisGame.getStatus());
    }

    @Test
    void shouldDeclarePlayerTwoAsWinner_WhenTheyWinGame() {
        giveThreePointsForEachPlayer();
        tennisService.wonPoint(2L);
        tennisService.wonPoint(2L);
        assertEquals("The game is finished and the winner is : playerTwo", tennisService.getScore());
        assertEquals(FINISHED,tennisGame.getStatus());
    }

    @Test
    void shouldResetScores_WhenGameIsReset() {
        tennisService.wonPoint(1L);
        tennisService.wonPoint(2L);
        tennisService.wonPoint(1L);
        tennisService.wonPoint(2L);
        assertEquals("30 - 30", tennisService.getScore());
        tennisService.resetGame();
        assertEquals("0 - 0", tennisService.getScore());
    }


    /**
     * Each player will have three points
     */
    private void giveThreePointsForEachPlayer() {

        for (int i = 0; i < 3; i++) {
            tennisService.wonPoint(1L);
            tennisService.wonPoint(2L);
        }
    }
}
