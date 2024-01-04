package pl.pwr.cellUtils;


public class CellEvolver {

    public int countAliveNeighbours(boolean[][] map, int row, int column, int rows, int columns) {
        int aliveNeighbours = 0;

        // Sprawdzenie, czy komórka na danym indeksie jest żywa
        if (map[row][column] == true) {
            aliveNeighbours--;
        }

        // Iteracja po sąsiadach w zakresie torusa
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                // Uwzględnienie toroidalności mapy
                int torusRow = (i + rows) % rows;
                int torusCol = (j + columns) % columns;

                if (map[torusRow][torusCol] == true) {
                    aliveNeighbours++;
                }
            }
        }

        return aliveNeighbours;
    }

}
