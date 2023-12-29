package pl.pwr.app;

import pl.pwr.cellUtils.CellState;
import pl.pwr.cellUtils.CellEvolver;
import pl.pwr.mapUtils.MapManager;
import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;
import pl.pwr.mapUtils.TorusMap;
import pl.pwr.outputs.ConsolePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

public class GameOfLife {

    private int numberOfThreads;
    private final int iterations;
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

        currentState.setDividedMaps(new ArrayList<>());

        ThreadManager threadManager = new ThreadManager(numberOfThreads);
        threads = threadManager.createThreads();
    }

    public void startGame() {

        MapManager mapManager = new MapManager();
        CurrentState currentState = CurrentState.getInstance();
        ConsolePrinter printer = new ConsolePrinter();

        printer.printConfigurationInfo(numberOfThreads);

        for (int i = 0; i < 100; i++) {
            printer.printCurrentIterationNumber(i);
            ArrayList<TorusMap> dividedMaps = mapManager.divideMapByThreads(currentState.getMap(), currentState.getColumns(), currentState.getRows(), numberOfThreads);

            for (int j = 0; j < numberOfThreads; j++) {
                int index = j;
                threads[j] = new Thread(() -> evolveCells(dividedMaps.get(index), currentState, printer, index));
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


            currentState.setMap(mapManager.mergeMaps(dividedMaps, currentState.getMap().getRows(), currentState.getMap().getColumns()));


        }
    }

    private synchronized void evolveCells(TorusMap map, CurrentState currentState, ConsolePrinter printer, int index) {
        // ArrayList<TorusMap> dividedMaps = new ArrayList<>(currentState.getDividedMaps());


        List<TorusMap> mapsCopy = currentState.getDividedMaps().stream()
                .collect(Collectors.toList());
        mapsCopy.remove(index);
        currentState.getDividedMaps().clear();

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
        printer.printThreadInfo((int) Thread.currentThread().getId(), rows, rows, columns, columns);

        mapsCopy.add(updatedMap);
        //dodanie zaktualizowanej mapy do listy
        currentState.getDividedMaps().addAll(mapsCopy);
    }

}

