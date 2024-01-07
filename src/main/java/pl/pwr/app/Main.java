package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;

import pl.pwr.mapUtils.MapHolder;
import pl.pwr.mapUtils.MapManager;

import java.util.concurrent.BrokenBarrierException;

import static pl.pwr.outputs.ConsolePrinter.*;

public class Main {

    //TODO: Należy zaimplementować kilka plików wejściowych demonstrujących działanie aplikacji
    // (stałe struktury, oscylatory, statki kosmiczne).


    public static void main(String[] args) {
        initializeGameData(args);
        runGame();
    }

    private static void initializeGameData(String[] args) {
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

    private static void runGame() {
        CurrentGameData currentData = CurrentGameData.getInstance();
        MapHolder mapHolder = MapHolder.getInstance();

        printConfigurationInfo(currentData.getNumberOfThreads());

        for (int i = 0; i < currentData.getIterations(); i++) {

            printCurrentIterationNumber(i + 1);
            printMap(mapHolder.getMap());

            ThreadManager threadManager = new ThreadManager(currentData.getNumberOfThreads(),
                    mapHolder.getMap(), mapHolder.getDividedMaps(), currentData.getIterations());
            threadManager.startThreads();

            try {
                threadManager.waitForThreads();

            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
