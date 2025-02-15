package edu.duke.yx304.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicShipTest {

    @Test
    public void testBasicShipConstructor() {
        Coordinate coordinate = new Coordinate("A0");
        ShipDisplayInfo<Character> myDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplayInfo = new SimpleShipDisplayInfo<>(null, 's');
        BasicShip<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, myDisplayInfo, enemyDisplayInfo);
        assertNotNull(ship);
    }

    @Test
    public void testOccupiesCoordinates() {
        Coordinate coordinate = new Coordinate("A0");
        ShipDisplayInfo<Character> myDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplayInfo = new SimpleShipDisplayInfo<>(null, 's');
        BasicShip<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, myDisplayInfo, enemyDisplayInfo);

        assertTrue(ship.occupiesCoordinates(new Coordinate("A0")));
        assertTrue(ship.occupiesCoordinates(new Coordinate("B0")));
    }

    @Test
    public void testWasHitAt() {
        Coordinate coordinate = new Coordinate("A0");
        ShipDisplayInfo<Character> myDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplayInfo = new SimpleShipDisplayInfo<>(null, 's');
        BasicShip<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, myDisplayInfo, enemyDisplayInfo);

        ship.recordHitAt(new Coordinate("A0"));
        assertTrue(ship.wasHitAt(new Coordinate("A0")));
        assertFalse(ship.wasHitAt(new Coordinate("B0")));
    }

    @Test
    public void testIsSunk() {
        Coordinate coordinate = new Coordinate("A0");
        ShipDisplayInfo<Character> myDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplayInfo = new SimpleShipDisplayInfo<>(null, 's');
        BasicShip<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, myDisplayInfo, enemyDisplayInfo);

        ship.recordHitAt(new Coordinate("A0"));
        ship.recordHitAt(new Coordinate("A1"));
        assertTrue(ship.isSunk());
    }

    @Test
    public void testRecordHitAt() {
        Coordinate coordinate = new Coordinate("A0");
        ShipDisplayInfo<Character> myDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplayInfo = new SimpleShipDisplayInfo<>(null, 's');
        BasicShip<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, myDisplayInfo, enemyDisplayInfo);

        ship.recordHitAt(new Coordinate("A0"));
        assertTrue(ship.wasHitAt(new Coordinate("A0")));
    }
}
