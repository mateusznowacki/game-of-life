package pl.pwr.cell;

import pl.pwr.mapmodel.Map;

public class LifeCycle {

    public int countAliveNeighbours(CellState[][] map, int row, int column, int rows, int columns) {
        int aliveNeighbours = 0;

        // Sprawdzenie, czy komórka na danym indeksie jest żywa
        if (map[row][column] == CellState.ALIVE) {
            aliveNeighbours--;
        }

        // Iteracja po sąsiadach w zakresie torusa
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                // Uwzględnienie toroidalności mapy
                int torusRow = (i + rows) % rows;
                int torusCol = (j + columns) % columns;

                if (map[torusRow][torusCol] == CellState.ALIVE) {
                    aliveNeighbours++;
                }
            }
        }

        return aliveNeighbours;
    }

    public CellState[][] changeCellState(CellState[][] map, int row, int column, int rows, int columns) {
        Map mapSetter = new Map();
        int aliveNeighbours = countAliveNeighbours(map, row, column, rows, columns);

        // Zasady gry w życie
        if (map[row][column] == CellState.ALIVE) {
            if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                mapSetter.setCellState(map, row, column, CellState.DEAD);

            }
        } else {
            if (aliveNeighbours == 3) {
                mapSetter.setCellState(map, row, column, CellState.ALIVE);
            }
        }
        return map;
    }
}
