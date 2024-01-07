package pl.pwr.mapUtils;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TorusMap {
    private final int rows;
    private final int columns;
    private final boolean[][] map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public TorusMap(TorusMap torusMap) {
        this.rows = torusMap.getRows();
        this.columns = torusMap.getColumns();
        this.map = torusMap.getMap();

    }

    public boolean[][] getMap() {
        try {
            lock.readLock().lock();
            return map;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public TorusMap(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.map = new boolean[rows][columns];
    }


    public void setValue(int row, int col, boolean value) {
        lock.writeLock().lock();
        try {
            map[row % rows][col % columns] = value;
        } finally {
            lock.writeLock().unlock();
        }
    }


    public boolean getValue(int row, int col) {
        lock.readLock().lock();
        try {
            return map[row % rows][col % columns];
        } finally {
            lock.readLock().unlock();
        }
    }

    public void initializeMap(ArrayList<CellCoordinates> coordinates) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                setValue(i, j, false);
            }
        }
        for (CellCoordinates coordinate : coordinates) {
            setValue(coordinate.getRow(), coordinate.getColumn(), true);
        }
    }

    public int getArrayRows() {
        return map.length;
    }

    public int getArrayColumns() {
        return map[0].length;
    }


}

