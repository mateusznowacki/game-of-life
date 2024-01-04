package pl.pwr.app;

import pl.pwr.mapUtils.MapManager;
import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

public class ThreadManager {

    private final List<Thread> threads;
    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;
    private TorusMap sharedMap;
    private int iterations;
    private CopyOnWriteArrayList<TorusMap> dividedMaps;
    private int j = 0;

    public ThreadManager(int numberOfThreads, TorusMap initialMap, CopyOnWriteArrayList<TorusMap> dividedMaps, int iterations) {
        this.threads = new ArrayList<>();
        this.entryBarrier = new CyclicBarrier(numberOfThreads+1);
        this.exitBarrier = new CyclicBarrier(numberOfThreads+1);
        this.sharedMap = initialMap;
        this.dividedMaps = dividedMaps;
        this.iterations = iterations;

        initializeThreads(numberOfThreads);
    }

    private void initializeThreads(int numberOfThreads) {
        ///
        MapHolder.getInstance().getMap().printMap();
        ///
        for (int i = 0; i < numberOfThreads; i++) {
            GameLogic gameOfLife = new GameLogic(entryBarrier, exitBarrier, i, dividedMaps.get(i), sharedMap,iterations);
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
        System.out.println("Iteration: " + j++);
        mapHolder.getMap().printMap();

    }

}
