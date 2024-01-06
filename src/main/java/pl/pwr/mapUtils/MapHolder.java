package pl.pwr.mapUtils;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapHolder {
    private static MapHolder instance;
    private TorusMap gameMap;
    private int rows;
    private int columns;
    private CopyOnWriteArrayList<TorusMap> dividedMaps;
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
        return dividedMaps;
    }

    public void setDividedMaps(CopyOnWriteArrayList<TorusMap> dividedMaps) {
        this.dividedMaps = new CopyOnWriteArrayList<>(dividedMaps);
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

    private MapHolder() {
    }

    public static synchronized MapHolder getInstance() {
        if (instance == null) {
            instance = new MapHolder();
        }
        return instance;
    }
}
