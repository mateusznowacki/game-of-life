package pl.pwr.app;

import pl.pwr.app.GameOfLife;
import pl.pwr.mapUtils.MapManager;
import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadManager {

    private final List<Thread> threads;
    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;
    private final TorusMap sharedMap;
    private int iterations;

    public ThreadManager(int numberOfThreads, TorusMap initialMap) {
        this.threads = new ArrayList<>();
        this.entryBarrier = new CyclicBarrier(numberOfThreads + 1);
        this.exitBarrier = new CyclicBarrier(numberOfThreads + 1);
        this.sharedMap = initialMap;

        initializeThreads(numberOfThreads);
    }

    private void initializeThreads(int numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            GameOfLife gameOfLife = new GameOfLife(entryBarrier, exitBarrier, i, sharedMap);
            Thread thread = new Thread(gameOfLife::run);
            threads.add(thread);
        }
    }

    public void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void waitForAllThreads() throws BrokenBarrierException, InterruptedException {
        entryBarrier.await();
        entryBarrier.reset();
        exitBarrier.await();

        MapManager mapManager = new MapManager();
        MapHolder mapHolder = MapHolder.getInstance();
        // łączenie zaktualizownaych map
        mapHolder.setMap(mapManager.mergeMaps(mapHolder.getDividedMaps(), mapHolder.getRows(), mapHolder.getColumns()));

        //podział zaktualizowanej mapy na wątki
        mapHolder.setDividedMaps(mapManager.divideMapByThreads(
                mapHolder.getMap(),
                mapHolder.getColumns(),
                mapHolder.getRows(),
                CurrentGameData.getInstance().getNumberOfThreads()
        ));
        // wypisanie map robocze
        System.out.println("Iteration: " + iterations++);
        mapHolder.getMap().printMap();
        for (TorusMap map : mapHolder.getDividedMaps()) {
            System.out.println("Thread: " + mapHolder.getDividedMaps().indexOf(map));
            map.printMap();
        }
        exitBarrier.reset();
    }


}
