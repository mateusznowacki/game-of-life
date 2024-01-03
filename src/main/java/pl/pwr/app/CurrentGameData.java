package pl.pwr.app;

import pl.pwr.mapUtils.TorusMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CurrentGameData {
    private static CurrentGameData instance;
    private TorusMap map;
    private int rows;
    private int columns;
    private int iterations;
    //private int liveCellsCount;
    private int numberOfThreads;
    private String filePath;
    private CopyOnWriteArrayList<TorusMap> dividedMaps;

    public synchronized CopyOnWriteArrayList<TorusMap> getDividedMaps() {
        return dividedMaps;
    }

    public synchronized void setDividedMaps(List<TorusMap> dividedMaps) {
        this.dividedMaps = new CopyOnWriteArrayList<>(dividedMaps);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public synchronized TorusMap getMap() {
        return map;
    }

    public synchronized void setMap(TorusMap map) {
        this.map = map;
    }

//    public int getLiveCellsCount() {
//        return liveCellsCount;
//    }
//
//    public void setLiveCellsCount(int liveCellsCount) {
//        this.liveCellsCount = liveCellsCount;
//    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    private CurrentGameData() {
    }

    public static synchronized CurrentGameData getInstance() {
        if (instance == null) {
            instance = new CurrentGameData();
        }
        return instance;
    }

}
