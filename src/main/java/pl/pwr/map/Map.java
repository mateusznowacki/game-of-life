package pl.pwr.map;

import pl.pwr.cell.CellState;

import java.util.ArrayList;

public class Map {
    private int rows;
    private int columns;
    private CellState[][] map;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

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
                if(getValue(i,j) == CellState.ALIVE){
                    System.out.print("X ");}
                else{
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }



}

