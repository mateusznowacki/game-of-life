package pl.pwr.app;

import pl.pwr.mapUtils.MapManager;
import pl.pwr.outputs.ConsolePrinter;

import java.util.concurrent.BrokenBarrierException;

public class Main {


    //TODO: wizuazlizacja w gui
    // TODO: synchronizacja arraylisty

    public static void main(String[] args) {
        CurrentGameData currentGameData = CurrentGameData.getInstance();
        currentGameData.setFilePath(args[0]);
        currentGameData.setNumberOfThreads(Integer.valueOf(args[1]));

        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.initializeGameData();

        ThreadManager threadManager = new ThreadManager(currentGameData.getNumberOfThreads());
        threadManager.startThreads();

        ConsolePrinter printer = new ConsolePrinter();


// Główna pętla gry
        for (int i = 0; i < 100; i++) {
            printer.printCurrentIterationNumber(i);


            // Oczekiwanie na rozpoczęcie iteracji przez wszystkie wątki
            try {
                threadManager.waitForIterationStart();
                threadManager.waitForIterationEnd();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Oczekiwanie na zakończenie iteracji przez wszystkie wątki
            MapManager mapManager = new MapManager();

            MapHolder.getInstance().setMap(mapManager.mergeMaps(MapHolder.getInstance().getDividedMaps(),
                    currentGameData.getRows(), currentGameData.getColumns()));

            MapHolder.getInstance().getMap().printMap();
        }

// Oczekiwanie na zakończenie pracy wszystkich wątków po zakończeniu wszystkich iteracji
        try {
            threadManager.waitForAllThreads();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}