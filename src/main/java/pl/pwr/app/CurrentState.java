package pl.pwr.app;

import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;

public class CurrentState {
    private static CurrentState instance;
    private TorusMap map;
    private int rows;
    private int columns;
    private int iterations;
    private int liveCellsCount;
    private int numberOfThreads;
    private String filePath;
    private ArrayList<TorusMap> dividedMaps;

    public ArrayList<TorusMap> getDividedMaps() {
        return dividedMaps;
    }

    public void setDividedMaps(ArrayList<TorusMap> dividedMaps) {
        this.dividedMaps = dividedMaps;
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

    public TorusMap getMap() {
        return map;
    }

    public void setMap(TorusMap map) {
        this.map = map;
    }

    public int getLiveCellsCount() {
        return liveCellsCount;
    }

    public void setLiveCellsCount(int liveCellsCount) {
        this.liveCellsCount = liveCellsCount;
    }

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

    private CurrentState() {
    }
    public static synchronized CurrentState getInstance() {
        if (instance == null) {
            instance = new CurrentState();
        }
        return instance;
    }

}
