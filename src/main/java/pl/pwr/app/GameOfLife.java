package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;
import pl.pwr.mapmodel.Map;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GameOfLife {

    private int numberOfThreads;
    private int iterations;
    private final CyclicBarrier barrier;
    private Thread[] threads;

    public GameOfLife(int numberOfThreads, int iterations) {
        this.numberOfThreads = numberOfThreads;
        this.iterations = iterations;
        this.barrier = new CyclicBarrier(numberOfThreads);
    }

    public void initializeGameData() {
        CurrentState currentState = CurrentState.getInstance();

        FileValidator fileValidator = new FileValidator();
        fileValidator.validateFile(currentState.getFilePath());

        DataParser dataParser = new DataParser();
        dataParser.parseData(currentState.getFilePath());

        ThreadManager threadManager = new ThreadManager(numberOfThreads);
        threads = threadManager.createThreads();
    }

    public void startGame() {
        Map map = CurrentState.getInstance().getMap();
        MapManager mapManager = new MapManager();
        CurrentState currentState = CurrentState.getInstance();

        for (int i = 0; i < iterations; i++) {
            ArrayList<Map> dividedMaps = mapManager.divideMapByThreads(map, currentState.getColumns(), currentState.getRows(), numberOfThreads);

            for (int j = 0; j < numberOfThreads; j++) {
                int index = j;
                threads[j] = new Thread(() -> evolveCells(dividedMaps.get(index)));
                threads[j].start();
            }
            // Czekanie na zakończenie ewolucji komórek w danym kroku
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Synchronizacja na barierze przed przejściem do następnego kroku
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }



            currentState.setMap(mapManager.mergeMaps(dividedMaps, currentState.getRows(), currentState.getColumns()));



            // Opcjonalnie: wypisanie stanu planszy po każdym kroku
//            currentMap = mapManager.mergeMaps(dividedMaps, currentMap.getRows(), currentMap.getColumns());
//            printMap(currentMap);
        }
    }

    private void evolveCells(Map map) {


    }


}

