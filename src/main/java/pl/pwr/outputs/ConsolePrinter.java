package pl.pwr.outputs;

import pl.pwr.app.MapHolder;
import pl.pwr.mapUtils.TorusMap;

public class ConsolePrinter {

    public static void printThreadInfo(int threadId, TorusMap currentMap, MapHolder gameMap) {
        int rowStart = 0;
        int rowEnd = currentMap.getArrayRows();

        int columnStart = 0;
        int columnEnd = currentMap.getArrayColumns();

        for (int i = 0; i < threadId; i++) {
            columnStart += gameMap.getDividedMaps().get(i).getArrayColumns();
            columnEnd += gameMap.getDividedMaps().get(i).getArrayColumns();
        }

        System.out.println("Thread ID: " + threadId +
                " Rows: " + rowStart + ":" + rowEnd + "(" + (rowEnd - rowStart) + ")" +
                " Columns: " + (columnStart) + ":" + columnEnd + "(" + (columnEnd - columnStart) + ")");
    }


    public static void printMap(TorusMap map) {
        boolean[][] mapArray = map.getMap();
        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[0].length; j++) {
                if (map.getValue(i, j)) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    public static void printConfigurationInfo(int numberOfThreads) {
        System.out.println("Number of available threads: " + numberOfThreads + " column-based partitioning");
    }

    public static void printCurrentIterationNumber(int iterationNumber) {
        System.out.println("Current iteration number: " + iterationNumber);
    }


}
