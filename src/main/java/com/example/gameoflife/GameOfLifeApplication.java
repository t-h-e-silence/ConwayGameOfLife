package com.example.gameoflife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameOfLifeApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(GameOfLifeApplication.class, args);
        LifeRunner.startGame();
    }
}
