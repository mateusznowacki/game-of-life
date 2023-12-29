package pl.pwr.app;

import pl.pwr.mapmodel.Map;

import java.util.ArrayList;

public class MapManager {
    public ArrayList<Map> divideMapByThreads(Map map, int columns, int rows, int threadsNumber) {
        ArrayList<Map> dividedMaps = new ArrayList<>();

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
            // TODO: poprawić
            // int endIndex = startIndex + columnsPerThread + (i < remainingColumns ? 1 : 0);

            // Twórz podmapę dla bieżącego wątku
            Map subMap = new Map(rows, endIndex - startIndex);
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

    public Map mergeMaps(ArrayList<Map> dividedMaps, int rows, int columns) {
        Map mergedMap = new Map(rows, columns);

        int startIndex = 0;
        for (Map subMap : dividedMaps) {
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
