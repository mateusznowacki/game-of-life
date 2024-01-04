package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;

import pl.pwr.mapUtils.MapManager;

import java.util.concurrent.BrokenBarrierException;

public class Main {

    //argumenty: scieżka do pliku oscilator.txt liczba wątków

    public static void main(String[] args) {
        initializeGameData(args);
        runGame();
    }

    private static void runGame() {
        CurrentGameData currentGameData = CurrentGameData.getInstance();
        ThreadManager threadManager = new ThreadManager(currentGameData.getNumberOfThreads(), MapHolder.getInstance().getMap());
        threadManager.startThreads();

        // Oczekiwanie na zakończenie wszystkich wątków
        try {
            threadManager.waitForAllThreads();

        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public static void initializeGameData(String[] args) {
        CurrentGameData currentGameData = CurrentGameData.getInstance();
        currentGameData.setFilePath(args[0]);
        currentGameData.setNumberOfThreads(Integer.valueOf(args[1]));

        FileValidator fileValidator = new FileValidator();
        fileValidator.validateFile(currentGameData.getFilePath());

        DataParser dataParser = new DataParser();
        dataParser.parseData(currentGameData.getFilePath());

        MapManager mapManager = new MapManager();
        MapHolder mapHolder = MapHolder.getInstance();
        mapHolder.setDividedMaps(mapManager.divideMapByThreads(
                mapHolder.getMap(),
                mapHolder.getColumns(),
                mapHolder.getRows(),
                currentGameData.getNumberOfThreads()
        ));
    }
}
