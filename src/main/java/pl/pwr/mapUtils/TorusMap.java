package pl.pwr.mapUtils;

import pl.pwr.cellUtils.CellState;

import java.util.ArrayList;

public class TorusMap {
    private int rows;
    private int columns;
    private CellState[][] map;

    public TorusMap(TorusMap torusMap) {
        this.rows = torusMap.getRows();
        this.columns = torusMap.getColumns();
        this.map = torusMap.getMap();

    }


    public CellState[][] getMap() {
        return map;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public TorusMap(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.map = new CellState[rows][columns];
    }

    public TorusMap() {

    }

    public synchronized void setValue(int row, int col, CellState value) {
        map[row % rows][col % columns] = value;
    }

    public synchronized void setCellState(CellState[][] map, int row, int col, CellState value) {
        map[row % rows][col % columns] = value;
    }

    public CellState getValue(int row, int col) {
        return map[row % rows][col % columns];
    }

    public void initializeMap(ArrayList<CellCoordinates> coordinates) {
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
                if (getValue(i, j) == CellState.ALIVE) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }


}

