package pl.pwr.app;


import pl.pwr.cellUtils.CellEvolver;
import pl.pwr.mapUtils.TorusMap;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GameOfLife implements Runnable {

    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;
    private int threadIndex;
    private TorusMap currentMap;
    private TorusMap nextMap;

    public GameOfLife(CyclicBarrier entryBarrier, CyclicBarrier exitBarrier, int threadIndex, TorusMap initialMap) {
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
        this.threadIndex = threadIndex;
        this.currentMap = initialMap;
        this.nextMap = new TorusMap(initialMap.getRows(), initialMap.getColumns());
    }

    @Override
    public void run() {
        try {
            for (int iteration = 0; iteration < CurrentGameData.getInstance().getIterations(); iteration++) {
                // Logika gry - ewolucja komórek
                evolveCells();

                // Oczekiwanie na rozpoczęcie i zakończenie iteracji przez wszystkie wątki
                entryBarrier.await();
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
        for (int i = 0; i < currentMap.getRows(); i++) {
            for (int j = 0; j < currentMap.getColumns(); j++) {
                currentMap.setValue(i, j, nextMap.getValue(i, j));
            }
        }
    }

    private void evolveCells() {
        CellEvolver cellEvolver = new CellEvolver();

        for (int i = 0; i < currentMap.getRows(); i++) {
            for (int j = 0; j < currentMap.getColumns(); j++) {
                int aliveNeighbours = cellEvolver.countAliveNeighbours(currentMap.getMap(), i, j, currentMap.getRows(), currentMap.getColumns());
                if (currentMap.getValue(i, j)) {
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        nextMap.setValue(i, j, true);
                    } else {
                        nextMap.setValue(i, j, false);
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        nextMap.setValue(i, j, true);
                    } else {
                        nextMap.setValue(i, j, false);
                    }
                }
            }
        }
    }
}
