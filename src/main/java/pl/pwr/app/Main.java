package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;
import pl.pwr.map.Map;

import java.util.List;

public class Main {

    //TODO: ewolucja komórek
    //TODO: synchronizacja wątków CyclicBarrier
    //TODO: wizuazlizacja

    public static void main(String[] args) {
        CurrentState currentState = CurrentState.getInstance();
        currentState.setFilePath(args[0]);
        currentState.setNumberOfThreads(Integer.valueOf(args[1]));

        FileValidator fileValidator = new FileValidator();
       fileValidator.validateFile(currentState.getFilePath());
        DataParser dataParser = new DataParser();
        dataParser.parseData(currentState.getFilePath());

        MapDivider mapDivider = new MapDivider();
        currentState.setDividedMaps(mapDivider.divideMapByThreads(currentState.getMap(), currentState.getColumns(),
                currentState.getRows(), currentState.getNumberOfThreads()));


        // Wydrukuj zawartość każdej podzielonej mapy
        for (int i = 0; i < currentState.getDividedMaps().size(); i++) {
            System.out.println("Map " + (i + 1) + ":");
            currentState.getDividedMaps().get(i).printMap();
            System.out.println();
        }
    }


}