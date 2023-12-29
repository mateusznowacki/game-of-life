package pl.pwr.inputs;

import pl.pwr.app.CurrentState;
import pl.pwr.mapUtils.CellCoordinates;
import pl.pwr.mapUtils.TorusMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataParser {
    public void parseData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            CurrentState currentState = CurrentState.getInstance();
            currentState.setRows(Integer.parseInt(reader.readLine().trim()));
            currentState.setColumns(Integer.parseInt(reader.readLine().trim()));
            currentState.setIterations(Integer.parseInt(reader.readLine().trim()));
            currentState.setLiveCellsCount(Integer.parseInt(reader.readLine().trim()));

            ArrayList<CellCoordinates> liveCells = new ArrayList<>();
            for (int i = 0; i < currentState.getLiveCellsCount(); i++) {
                String line = reader.readLine();
                String[] coordinates = line.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                liveCells.add(new CellCoordinates(x, y));

            }
            TorusMap map = new TorusMap(currentState.getRows(), currentState.getColumns());
            map.initializeMap(liveCells);
            currentState.setMap(map);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
