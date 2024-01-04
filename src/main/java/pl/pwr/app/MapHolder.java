package pl.pwr.app;

import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapHolder {
    private static MapHolder instance;
    private TorusMap gameMap;
    private int rows;
    private int columns;
    private CopyOnWriteArrayList<TorusMap> dividedMaps;
    private int liveCellsCount;
    private final ReadWriteLock mapLock = new ReentrantReadWriteLock();


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

    public CopyOnWriteArrayList<TorusMap> getDividedMaps() {
//        mapLock.r.lock();
//        try {
            return dividedMaps;
//        } finally {
//            mapLock.unlock();
//        }
    }

    public void setDividedMaps(CopyOnWriteArrayList<TorusMap> dividedMaps) {
//        mapLock.lock();
//        try {
            this.dividedMaps = new CopyOnWriteArrayList<>(dividedMaps);
//        } finally {
//            mapLock.unlock();
//        }
    }


    public TorusMap getMap() {
        try {
            mapLock.readLock().lock();
            return gameMap;
        } finally {
            mapLock.readLock().unlock();
        }

    }

    public void setMap(TorusMap map) {
        try {
            mapLock.writeLock().lock();
            this.gameMap = map;
        } finally {
            mapLock.writeLock().unlock();
        }

    }

    public int getLiveCellsCount() {
        return liveCellsCount;
    }

    public void setLiveCellsCount(int liveCellsCount) {
        this.liveCellsCount = liveCellsCount;
    }

    private MapHolder() {
    }

    public static synchronized MapHolder getInstance() {
        if (instance == null) {
            instance = new MapHolder();
        }
        return instance;
    }
}
