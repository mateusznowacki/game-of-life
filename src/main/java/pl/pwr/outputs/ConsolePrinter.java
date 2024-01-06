package pl.pwr.outputs;

import com.diogonunes.jcolor.Attribute;
import pl.pwr.mapUtils.MapHolder;
import pl.pwr.mapUtils.TorusMap;

import static com.diogonunes.jcolor.Ansi.colorize;


public class ConsolePrinter {

    public static synchronized void printThreadInfo(int threadId, TorusMap currentMap, MapHolder gameMap) {
        int rowStart = 0;
        int rowEnd = currentMap.getArrayRows()-1;

        int columnStart = 0;
        int columnEnd = currentMap.getArrayColumns()-1;

        for (int i = 0; i < threadId; i++) {
            columnStart += gameMap.getDividedMaps().get(i).getArrayColumns();
            columnEnd += gameMap.getDividedMaps().get(i).getArrayColumns();
        }

        System.out.println("tid: " + threadId +
                " rows: " + rowStart + ":" + rowEnd + "(" + (rowEnd - rowStart) + ")" +
                " rolumns: " + columnStart + ":" + columnEnd + "(" + (columnEnd - columnStart+1) + ")");
    }


    public static synchronized void printMap(TorusMap map) {
        boolean[][] mapArray = map.getMap().clone();
        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[0].length; j++) {
                if (mapArray[i][j]) {
                    System.out.print(colorize("X ", Attribute.TEXT_COLOR(3,249,52)));
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
        System.out.println("------------------------------------------------");
        System.out.println("Current iteration number: " + iterationNumber);
    }


}
