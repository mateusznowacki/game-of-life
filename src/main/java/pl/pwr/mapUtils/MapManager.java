package pl.pwr.mapUtils;

import java.util.concurrent.CopyOnWriteArrayList;

public class MapManager {
    public synchronized CopyOnWriteArrayList<TorusMap> divideMapByThreads(TorusMap map, int columns, int rows, int threadsNumber) {
        CopyOnWriteArrayList<TorusMap> dividedMaps = new CopyOnWriteArrayList<>();

        // Oblicz liczbę kolumn na każdy wątek
        int columnsPerThread = columns / threadsNumber;
        int remainingColumns = columns % threadsNumber;

        // Podziel mapę między wątki
        int startIndex = 0;
        for (int i = 0; i < threadsNumber; i++) {

            // Jeśli pozostały kolumny to przypisuje każdą kolumnę w iteracji
            int endIndex = startIndex + columnsPerThread;
            if (remainingColumns > 0) {
                endIndex++;
                remainingColumns--;
            }

            // Twórz podmapę dla bieżącego wątku
            TorusMap subMap = new TorusMap(rows, endIndex - startIndex);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < subMap.getColumns(); col++) {
                    subMap.setValue(row, col, map.getValue(row, startIndex + col));
                }
            }

            // Dodaj podmapę do listy
            dividedMaps.add(subMap);

            // Przesuń indeks początkowy do następnej pozycji
            startIndex = endIndex;
        }

        return dividedMaps;
    }

    public synchronized TorusMap mergeMaps(CopyOnWriteArrayList<TorusMap> dividedMaps, int rows, int columns) {
        TorusMap mergedMap = new TorusMap(rows, columns);

        int startIndex = 0;
        for (TorusMap subMap : dividedMaps) {
            int subMapColumns = subMap.getColumns();

            // Skopiuj zawartość podmapy do docelowej mapy
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < subMapColumns; col++) {
                    mergedMap.setValue(row, startIndex + col, subMap.getValue(row, col));
                }
            }

            // Przesuń indeks początkowy do następnej pozycji
            startIndex += subMapColumns;
        }

        return mergedMap;
    }


}
