package edu.duke.yx304.battleship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AbstractShipFactoryTest {
    @Test
    void testMakeSubmarine() {
        V1ShipFactory factory = new V1ShipFactory();
        Placement placement = new Placement("A0H");
        Ship<Character> submarine = factory.makeSubmarine(placement);
        assertNotNull(submarine);
        assertEquals("Submarine", submarine.getName());
    }

    @Test
    void testMakeBattleship() {
        V1ShipFactory factory = new V1ShipFactory();
        Placement placement = new Placement("A0H");
        Ship<Character> battleship = factory.makeBattleship(placement);
        assertNotNull(battleship);
        assertEquals("Battleship", battleship.getName());
    }
    
    @Test
    void testInvalidOrientation() {
        V1ShipFactory factory = new V1ShipFactory();
        Placement placement = new Placement("A0X");
        assertThrows(IllegalArgumentException.class, () -> factory.makeSubmarine(placement));
    }
}
