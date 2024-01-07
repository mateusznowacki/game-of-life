package pl.pwr.cellUtils;


import pl.pwr.mapUtils.MapHolder;
import pl.pwr.mapUtils.TorusMap;

public class CellEvolver {

    public int countAliveNeighbours(int threadIndex, int dividedRowIndex, int dividedColumnIndex, TorusMap gameMap) {
        boolean[][] map = gameMap.getMap();
        int rows = gameMap.getArrayRows();
        int columns = gameMap.getArrayColumns();
        int row = dividedRowIndex;
        int column = dividedColumnIndex;

        //mapy są podzielone na części, więc trzeba dodać odpowiednią ilość kolumn
        for (int i = 0; i < threadIndex; i++) {
            column += MapHolder.getInstance().getDividedMaps().get(i).getArrayColumns();
        }

        int aliveNeighbours = 0;

        // Sprawdzenie, czy komórka na danym indeksie jest żywa
        if (map[row][column]) {
            // Jeśli tak, to odejmij ją od liczby żywych sąsiadów pętla doliczy ją ponownie
            aliveNeighbours = -1;
        }

        // Iteracja po sąsiadach w zakresie torusa
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                // Uwzględnienie toroidalności mapy
                int torusRow = (i + rows) % rows;
                int torusCol = (j + columns) % columns;

                if (map[torusRow][torusCol]) {
                    aliveNeighbours++;
                }
            }
        }

        return aliveNeighbours;
    }


}
