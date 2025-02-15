package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlacementTest {

    @Test
    public void testPlacementConstructor() {
        Coordinate coordinate = new Coordinate("A1");
        Placement placement = new Placement(coordinate, 'H');
        assertEquals(coordinate, placement.getWhere());
        assertEquals('H', placement.getOrientation());
    }

    @Test
    public void testPlacementStringConstructor() {
        Placement placement = new Placement("A1H");
        assertEquals(new Coordinate("A1"), placement.getWhere());
        assertEquals('H', placement.getOrientation());
    }

    @Test
    public void testInvalidPlacementConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new Placement(null));
    }

    @Test
    public void testInvalidPlacementConstructorWithShortString() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1"));
    }

    @Test
    public void testInvalidPlacementConstructorWithLongString() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1HH"));
    }

    @Test
    public void testEqualsAndHashCode() {
        Placement p1 = new Placement(new Coordinate("A1"), 'H');
        Placement p2 = new Placement(new Coordinate("A1"), 'H');
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testEqualsWithDifferentOrientation() {
        Placement p1 = new Placement(new Coordinate("A1"), 'H');
        Placement p2 = new Placement(new Coordinate("A1"), 'V');
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testEqualsWithDifferentCoordinates() {
        Placement p1 = new Placement(new Coordinate("A1"), 'H');
        Placement p2 = new Placement(new Coordinate("B1"), 'H');
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testToString() {
        Placement placement = new Placement(new Coordinate("A1"), 'H');
        assertEquals("(0, 1)H", placement.toString());
    }

    @Test
    public void testHashCodeWithDifferentOrientation() {
        Placement p1 = new Placement(new Coordinate("A1"), 'H');
        Placement p2 = new Placement(new Coordinate("A1"), 'V');
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testHashCodeWithDifferentCoordinates() {
        Placement p1 = new Placement(new Coordinate("A1"), 'H');
        Placement p2 = new Placement(new Coordinate("B1"), 'H');
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}
