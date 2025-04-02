package com.kata.tennis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class GameFinishedException extends RuntimeException {
    public GameFinishedException(String message) {
        super(message);
    }
}