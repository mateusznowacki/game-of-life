package pl.pwr.app;

import pl.pwr.mapUtils.TorusMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CurrentGameData {
    private static CurrentGameData instance;
    private int rows;
    private int columns;
    private int iterations;
    private int numberOfThreads;
    private String filePath;


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

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
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

    private CurrentGameData() {
    }

    public static synchronized CurrentGameData getInstance() {
        if (instance == null) {
            instance = new CurrentGameData();
        }
        return instance;
    }

}
