import org.junit.Assert;
import org.junit.Test;
import pl.pwr.mapUtils.CellCoordinates;
import pl.pwr.mapUtils.TorusMap;

import java.util.ArrayList;

public class TorusMapTest {
    TorusMap torusMap;

    public void setUp() {
        boolean[][] testMap = {
                {true, false, true},
                {false, true, false},
                {true, false, true}
        };
        torusMap = new TorusMap(3, 3, testMap);
    }

    @Test
    public void getMapTest() {
        // Arrange
        setUp();
        // Act
        boolean[][] result = torusMap.getMap();
        // Assert
        boolean[][] expected = {
                {true, false, true},
                {false, true, false},
                {true, false, true}
        };
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void getRowsTest() {
        // Arrange
        setUp();
        // Act
        int result = torusMap.getRows();
        // Assert
        Assert.assertEquals(3, result);
    }

    @Test
    public void getColumnsTest() {
        // Arrange
        setUp();
        // Act
        int result = torusMap.getColumns();
        // Assert
        Assert.assertEquals(3, result);
    }

    @Test
    public void setValueTest() {
        // Arrange
        setUp();
        // Act
        torusMap.setValue(5, 5, false);
        // Assert
        Assert.assertFalse(torusMap.getValue(2, 2));
    }

    @Test
    public void getValueTest() {
        // Arrange
        setUp();
        // Act
        boolean result = torusMap.getValue(5, 5);
        // Assert
        Assert.assertEquals(true, result);
    }

    @Test
    public void initializeMapTest() {
        // Arrange
        torusMap = new TorusMap(3, 3);
        ArrayList<CellCoordinates> coordinates = new ArrayList<>();

        coordinates.add(new CellCoordinates(0, 0));
        coordinates.add(new CellCoordinates(0, 1));
        coordinates.add(new CellCoordinates(0, 2));
        // Act
        torusMap.initializeMap(coordinates);
        // Assert
        boolean[][] expected = {
                {true, true, true},
                {false, false, false},
                {false, false, false}
        };
        Assert.assertArrayEquals(expected, torusMap.getMap());

    }

    @Test
    public void getArrayRowsTest() {
        // Arrange
        setUp();
        torusMap.getArrayRows();
        // Act
        int result = torusMap.getArrayRows();
        // Assert
        Assert.assertEquals(3, result);
    }

    @Test
    public void getArrayColumnsTest() {
        // Arrange
        setUp();
        torusMap.getArrayColumns();
        // Act
        int result = torusMap.getArrayColumns();
        // Assert
        Assert.assertEquals(3, result);
    }
}
