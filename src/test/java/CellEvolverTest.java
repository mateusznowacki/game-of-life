import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.pwr.cellUtils.CellEvolver;
import pl.pwr.mapUtils.TorusMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CellEvolverTest {

    @Mock
    private TorusMap torusMap;

    private CellEvolver cellEvolver;

    @Before
    public void setUp() {
        torusMap = mock(TorusMap.class);
        cellEvolver = new CellEvolver();
    }

    @Test
    public void testCountAliveNeighbours() {
        // Arrange
        boolean[][] map = {
                {false, true, false},
                {true, false, true},
                {false, true, false}
        };

        when(torusMap.getMap()).thenReturn(map);
        when(torusMap.getArrayRows()).thenReturn(3);
        when(torusMap.getArrayColumns()).thenReturn(3);

        // Act
        int result = cellEvolver.countAliveNeighbours(0, 1, 1, torusMap);

        // Assert
        assertEquals(4, result);
    }

    @Test
    public void testCountAliveNeighboursEdgeCase() {
        // Testowanie dla brzegowego przypadku (komórka na krawędzi mapy)

        // Arrange
        boolean[][] map = {
                {false, true, false},
                {true, false, true},
                {false, true, false}
        };

        when(torusMap.getMap()).thenReturn(map);
        when(torusMap.getArrayRows()).thenReturn(3);
        when(torusMap.getArrayColumns()).thenReturn(3);

        // Act
        int result = cellEvolver.countAliveNeighbours(0, 0, 0, torusMap);

        // Assert
        assertEquals(4, result);
    }
}
