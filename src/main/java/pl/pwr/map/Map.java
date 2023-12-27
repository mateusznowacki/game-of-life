package pl.pwr.map;

import pl.pwr.cell.CellState;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private int rows;
    private int columns;
    private CellState[][] map;

    public Map(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.map = new CellState[rows][columns];
    }

    public void setValue(int row, int col, CellState value) {
        map[row % rows][col % columns] = value;
    }

    public CellState getValue(int row, int col) {
        return map[row % rows][col % columns];
    }

    public void setMap(ArrayList<CellCoordinates> coordinates) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                setValue(i, j, CellState.DEAD);
            }
        }
        for (CellCoordinates coordinate : coordinates) {
            setValue(coordinate.getRow(), coordinate.getColumn(), CellState.ALIVE);
        }
    }

    public void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.println("x: " + i + ", y: " + j + ", wartość: " + map[i][j]);
            }
        }
    }

}

