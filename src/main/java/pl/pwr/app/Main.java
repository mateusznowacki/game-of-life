package pl.pwr.app;

import pl.pwr.outputs.ConsolePrinter;

public class Main {


    //TODO: wizuazlizacja w gui

    public static void main(String[] args) {
        CurrentState currentState = CurrentState.getInstance();
        currentState.setFilePath(args[0]);
        currentState.setNumberOfThreads(Integer.valueOf(args[1]));

        GameOfLife gameOfLife = new GameOfLife(currentState.getNumberOfThreads(), currentState.getIterations());
        gameOfLife.initializeGameData();
        gameOfLife.startGame();

        ;

    }
}