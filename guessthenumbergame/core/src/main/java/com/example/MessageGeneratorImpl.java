package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MessageGeneratorImpl implements MessageGenerator {

    // == fields == //
    private final Game game;

    // == constructors == //
    @Autowired
    public MessageGeneratorImpl(Game game) {
        this.game = game;
    }

    // == init method ==//
    @PostConstruct
    public void reset(){
        log.info("logging value of game = {}",game);
    }


    // == public methods == //
    @Override
    public String getMainMessage() {
        return "Number is between " + game.getSmallest() +
                " and " + game.getBiggest() + ". Can you guess it?";
    }

    @Override
    public String getResultMessage() {
        if(game.isGameWon()){
            return "You guessed it right! The number was " + game.getNumber();
        }
        else if(game.isGameLost()){
            return "You lost! The number was " + game.getNumber();
        }
        else if(!game.isValidNumberRange()){
            return "Invalid number range!";
        }
        else if (game.getRemainingGuesses() == game.getGuessCount()){
            return "What's your first guess?";
        }
        else {
            String direction = "Lower";

            if (game.getGuess() < game.getNumber()) {
                direction = "Higher";
            }

            return direction + "! You have " + game.getRemainingGuesses() + " guesses left.";
        }
    }
}
