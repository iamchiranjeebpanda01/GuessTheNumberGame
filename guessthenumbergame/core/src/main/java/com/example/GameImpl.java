package com.example;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
@Getter
public class GameImpl implements Game {

    // == fields == //
    @Getter(AccessLevel.NONE)
    private final NumberGenerator numberGenerator;
    private final int guessCount;
    private int number;
    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange = true;

    @Setter
    private int guess;

    // == constructors == //
    @Autowired
    public GameImpl(NumberGenerator numberGenerator, @GuessCount int guessCount){
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;

    }

    // == init method == //
    @PostConstruct //Bean using annotation to invoke init method
    @Override
    public void reset() {
        this.smallest = numberGenerator.getMinNumber();
        this.guess = numberGenerator.getMinNumber();
        this.remainingGuesses = guessCount;
        this.biggest = numberGenerator.getMaxNumber();
        this.number = numberGenerator.next();
        log.debug("The number is {}", this.number);
    }

    @PreDestroy
    public void preDestroy(){ //Bean using annotation to invoke preDestroy method
        log.info("in game preDestroy()");
    }

    @Override
    public void check() {
       checkValidNumberRange();

        if(validNumberRange){
            if(guess>number){
                this.biggest = guess-1;
            }

            if(guess<number){
                this.smallest = guess+1;
            }

            this.remainingGuesses--;

        }
    }

    @Override
    public boolean isGameWon() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return ((!isGameWon()) && (this.remainingGuesses <= 0));
    }

    // == private methods == //
    private void checkValidNumberRange(){
        this.validNumberRange = ((guess>=smallest) && (guess<=biggest));
    }
}
