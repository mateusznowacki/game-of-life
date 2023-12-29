package pl.pwr.mapmodel;

public class CellCoordinates {
    private int row;
    private int column;

    public CellCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
