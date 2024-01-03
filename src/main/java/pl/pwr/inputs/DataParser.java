package pl.pwr.inputs;

import pl.pwr.app.CurrentGameData;
import pl.pwr.app.MapHolder;
import pl.pwr.mapUtils.CellCoordinates;
import pl.pwr.mapUtils.TorusMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataParser {
    public void parseData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            CurrentGameData currentGameData = CurrentGameData.getInstance();
            currentGameData.setRows(Integer.parseInt(reader.readLine().trim()));
            currentGameData.setColumns(Integer.parseInt(reader.readLine().trim()));
            currentGameData.setIterations(Integer.parseInt(reader.readLine().trim()));
           int liveCellsNumber = (Integer.parseInt(reader.readLine().trim()));

            ArrayList<CellCoordinates> liveCells = new ArrayList<>();
            for (int i = 0; i < liveCellsNumber; i++) {
                String line = reader.readLine();
                String[] coordinates = line.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                liveCells.add(new CellCoordinates(x, y));

            }
            TorusMap map = new TorusMap(currentGameData.getRows(), currentGameData.getColumns());
            map.initializeMap(liveCells);
           // currentGameData.setMap(map);
            MapHolder.getInstance().setMap(map);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
