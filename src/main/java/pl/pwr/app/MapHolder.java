package pl.pwr.app;

import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapHolder {
    private static MapHolder instance;
    private TorusMap gameMap;
    private ArrayList<TorusMap> dividedMaps;
    private int liveCellsCount;
    private final Lock mapLock = new ReentrantLock();

    public List<TorusMap> getDividedMaps() {
        mapLock.lock();
        try {
            return dividedMaps;
        } finally {
            mapLock.unlock();
        }
    }

    public void setDividedMaps(List<TorusMap> dividedMaps) {
        mapLock.lock();
        try {
            this.dividedMaps = new ArrayList<>(dividedMaps);
        } finally {
            mapLock.unlock();
        }
    }

//    public List<TorusMap> getDividedMaps() {
//        return dividedMaps;
//    }
//
//    public void setDividedMaps(ArrayList<TorusMap> dividedMaps) {
//        this.dividedMaps = dividedMaps;
//    }

    public TorusMap getMap() {
        try {
            mapLock.lock();
            return gameMap;
        } finally {
            mapLock.unlock();
        }
       // return gameMap;
    }

    public void setMap(TorusMap map) {
        try {
            mapLock.lock();
            this.gameMap = map;
        } finally {
            mapLock.unlock();
        }
      //  this.gameMap = map;
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
