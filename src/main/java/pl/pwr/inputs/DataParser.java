package pl.pwr.inputs;

import pl.pwr.app.CurrentState;
import pl.pwr.map.CellCoordinates;
import pl.pwr.map.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Map map = new Map(currentState.getRows(), currentState.getColumns());
            map.setMap(liveCells);
            currentState.setMap(map);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
