package pl.pwr.app;

import pl.pwr.cellUtils.CellState;
import pl.pwr.cellUtils.CellEvolver;
import pl.pwr.mapUtils.MapManager;
import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;
import pl.pwr.mapUtils.TorusMap;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class GameOfLife implements Runnable {

    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;
    private int threadIndex;

    public GameOfLife(CyclicBarrier entryBarrier, CyclicBarrier exitBarrier, int threadIndex) {
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
        this.threadIndex = threadIndex;
    }

    public GameOfLife() {
        this.entryBarrier = null;
        this.exitBarrier = null;
    }

    @Override
    public void run() {
        try {
            //  for (int iteration = 0; iteration < 100; iteration++) {
            // Oczekiwanie na pozostałe wątki przed rozpoczęciem pracy
            entryBarrier.await();

            // Tutaj wykonaj pracę na podstawie wprowadzonych zmian z poprzedniej iteracji
            // evolveCells(CurrentGameData.getInstance().getDividedMaps().get(threadIndex));
            evolveCells(MapHolder.getInstance().getDividedMaps().get(threadIndex));

            // Oczekiwanie na pozostałe wątki przed zakończeniem iteracji
            exitBarrier.await();
            //  }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void initializeGameData() {
        CurrentGameData currentGameData = CurrentGameData.getInstance();

        FileValidator fileValidator = new FileValidator();
        fileValidator.validateFile(currentGameData.getFilePath());

        DataParser dataParser = new DataParser();
        dataParser.parseData(currentGameData.getFilePath());

        MapManager mapManager = new MapManager();
        //currentGameData.setDividedMaps(mapManager.divideMapByThreads(currentGameData.getMap(), currentGameData.getColumns(),
        //    currentGameData.getRows(), currentGameData.getNumberOfThreads()));

        MapHolder mapHolder = MapHolder.getInstance();
        mapHolder.setDividedMaps(mapManager.divideMapByThreads(mapHolder.getMap(), currentGameData.getColumns(),
                currentGameData.getRows(), currentGameData.getNumberOfThreads()));

    }


    private synchronized void evolveCells(TorusMap map) {

        CellEvolver cellEvolver = new CellEvolver();
        int rows = map.getRows();
        int columns = map.getColumns();
        TorusMap updatedMap = new TorusMap(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; ++j) {
                int aliveNeighbours = cellEvolver.countAliveNeighbours(map.getMap(), i, j, rows, columns);
                if (map.getValue(i, j) == CellState.ALIVE) {
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        updatedMap.setValue(i, j, CellState.ALIVE);

                    } else {
                        updatedMap.setValue(i, j, CellState.DEAD);
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        updatedMap.setValue(i, j, CellState.ALIVE);
                    } else {
                        updatedMap.setValue(i, j, CellState.DEAD);
                    }
                }
            }
        }
        synchronized (MapHolder.getInstance().getDividedMaps()) {
            MapHolder.getInstance().getDividedMaps().set(threadIndex, updatedMap);
        }
      //  MapHolder.getInstance().getDividedMaps().set(threadIndex, updatedMap);
      //  CurrentGameData.getInstance().getDividedMaps().set(threadIndex, updatedMap);
        //  CurrentState.getInstance().setMap(updatedMap);
      //  updatedMap.printMap();

    }
}

