package com.kata.tennis.service;

public interface TennisService {
    public void wonPoint(Long playerId);
    public String getScore();

    public void resetGame();
}
