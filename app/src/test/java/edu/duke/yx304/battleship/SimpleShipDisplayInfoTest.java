package edu.duke.yx304.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleShipDisplayInfoTest {

    @Test
    public void testGetInfo() {
        SimpleShipDisplayInfo<String> shipInfo = new SimpleShipDisplayInfo<>("Ship", "Hit");

        assertEquals("Ship", shipInfo.getInfo(new Coordinate(1, 1), false));
        assertEquals("Hit", shipInfo.getInfo(new Coordinate(1, 1), true));
    }
}
