import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pwr.cellUtils.GameLogic;
import pl.pwr.mapUtils.TorusMap;

public class GameLogicTest {

    private GameLogic gameLogic;
    private TorusMap nextMap;
    private TorusMap map;

    @Before
    public void setUp() {

        boolean[][] testMap = {
                {true, false, true},
                {false, true, false},
                {true, false, true}
        };


        map = new TorusMap(3, 3);

        map.setTestMap(testMap);


        gameLogic = new GameLogic(null, null, 0, map, map, 1);
        nextMap = new TorusMap(3, 3);

    }

    @Test
    public void GameLogicTest() {
        // Act
        gameLogic.evolveCells();
        nextMap = gameLogic.copyNextMapToCurrentMap();
        // Assert
        boolean[][] expected = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };


        Assert.assertArrayEquals(expected, nextMap.getMap());


    }
}
