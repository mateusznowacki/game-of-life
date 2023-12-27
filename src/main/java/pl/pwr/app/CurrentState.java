package pl.pwr.app;

import pl.pwr.cell.CellState;
import pl.pwr.map.Map;

public class CurrentState {
    private static CurrentState instance;
    private Map map;
    private int rows;
    private int columns;
    private int iterations;
    private int liveCellsCount;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
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
