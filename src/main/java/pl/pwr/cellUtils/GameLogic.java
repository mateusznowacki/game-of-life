package pl.pwr.cellUtils;


import pl.pwr.app.CurrentGameData;
import pl.pwr.cellUtils.CellEvolver;
import pl.pwr.mapUtils.MapHolder;
import pl.pwr.mapUtils.MapManager;
import pl.pwr.mapUtils.TorusMap;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static pl.pwr.outputs.ConsolePrinter.printThreadInfo;

public class GameLogic implements Runnable {

    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;
    private final int threadIndex;
    private final TorusMap gameMap;
    private final TorusMap currentMap;
    private final TorusMap nextMap;
    private final int iterations;


    public GameLogic(CyclicBarrier entryBarrier, CyclicBarrier exitBarrier, int threadIndex, TorusMap initialMap, TorusMap gameMap, int iterations) {
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
        this.threadIndex = threadIndex;
        this.currentMap = initialMap;
        this.nextMap = new TorusMap(initialMap.getArrayRows(), initialMap.getArrayColumns());
        this.gameMap = gameMap;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iterations; i++) {
                // Logika gry - ewolucja komórek
                entryBarrier.await();
                evolveCells();
                printThreadInfo(threadIndex, currentMap, MapHolder.getInstance());
                // Oczekiwanie na rozpoczęcie i zakończenie iteracji przez wszystkie wątki

                exitBarrier.await();

                // Kopiowanie stanu do mapy dla następnej iteracji
                copyNextMapToCurrentMap();
                //podmiana mapy na zaktualizowaną
                MapHolder.getInstance().getDividedMaps().set(threadIndex, currentMap);

            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void copyNextMapToCurrentMap() {
        for (int i = 0; i < currentMap.getArrayRows(); i++) {
            for (int j = 0; j < currentMap.getArrayColumns(); j++) {
                currentMap.setValue(i, j, nextMap.getValue(i, j));
            }
        }
    }

    public void evolveCells() {
        CellEvolver cellEvolver = new CellEvolver();
        for (int i = 0; i < currentMap.getRows(); i++) {
            for (int j = 0; j < currentMap.getColumns(); j++) {
                int aliveNeighbours = cellEvolver.countAliveNeighbours(threadIndex, i, j, gameMap);
                if (currentMap.getValue(i, j)) {
                    nextMap.setValue(i, j, aliveNeighbours == 2 || aliveNeighbours == 3);
                } else {
                    nextMap.setValue(i, j, aliveNeighbours == 3);
                }
            }
        }
    }

}
