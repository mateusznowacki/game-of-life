package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;

public class Main {


    //TODO: wizuazlizacja

    public static void main(String[] args) {
        CurrentState currentState = CurrentState.getInstance();
        currentState.setFilePath(args[0]);
        currentState.setNumberOfThreads(Integer.valueOf(args[1]));


        GameOfLife gameOfLife = new GameOfLife(currentState.getNumberOfThreads(), currentState.getIterations());
        gameOfLife.initializeGameData();
        gameOfLife.startGame();


    }
}