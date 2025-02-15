package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {

    @Test
    public void test_makeCoords() {
        Coordinate upperLeft = new Coordinate(2, 3);
        HashSet<Coordinate> coords = RectangleShip.makeCoords(upperLeft, 3, 2);

        assertEquals(6, coords.size());
        assertTrue(coords.contains(new Coordinate(2, 3)));
        assertTrue(coords.contains(new Coordinate(2, 4)));
        assertTrue(coords.contains(new Coordinate(2, 5)));
        assertTrue(coords.contains(new Coordinate(3, 3)));
        assertTrue(coords.contains(new Coordinate(3, 4)));
        assertTrue(coords.contains(new Coordinate(3, 5)));
    }

    @Test
    public void test_single_cell_ship() {
        Coordinate pos = new Coordinate(5, 5);
        RectangleShip<Character> ship = new RectangleShip<>("Submarine", pos, 1, 1, 's', 'h');
        assertTrue(ship.occupiesCoordinates(pos));
        assertFalse(ship.occupiesCoordinates(new Coordinate(5, 6)));
    }

    @Test
    public void test_constructor() {
        Coordinate upperLeft = new Coordinate(0, 0);
        RectangleShip<Character> ship = new RectangleShip<>("TestShip", upperLeft, 2, 3, 't', 'x');
        assertEquals("TestShip", ship.getName());
        assertTrue(ship.occupiesCoordinates(new Coordinate(0, 0)));
        assertTrue(ship.occupiesCoordinates(new Coordinate(2, 1))); // row 0+2=2, col 0+1=1
    }


    @Test
    public void test_display_info() {
        ShipDisplayInfo<Character> myInfo = new SimpleShipDisplayInfo<>('s', 'h');
        ShipDisplayInfo<Character> enemyInfo = new SimpleShipDisplayInfo<>(null, 's');
        RectangleShip<Character> ship = new RectangleShip<>("Sub", new Coordinate(3, 3), 2, 1, myInfo, enemyInfo);

        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(3, 3), true));
        assertEquals(null, ship.getDisplayInfoAt(new Coordinate(3, 3), false));
        ship.recordHitAt(new Coordinate(3, 4));
        assertEquals('h', ship.getDisplayInfoAt(new Coordinate(3, 4), true));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(3, 4), false));
    }

    @Test
    public void test_edge_coordinates() {
        Coordinate upperLeft = new Coordinate(0, 0);
        RectangleShip<Character> ship = new RectangleShip<>("EdgeShip", upperLeft, 10, 1, 'e', 'x');
        assertTrue(ship.occupiesCoordinates(new Coordinate(0, 9)));
        assertFalse(ship.occupiesCoordinates(new Coordinate(0, 10)));
    }

    @Test
    public void test_constructor_overload() {
        RectangleShip<Character> ship1 = new RectangleShip<>(new Coordinate(4, 4), 'c', 'h');
        assertEquals("testship", ship1.getName());
        assertTrue(ship1.occupiesCoordinates(new Coordinate(4, 4)));


        ShipDisplayInfo<Character> customInfo = new SimpleShipDisplayInfo<>('a', 'b');
        RectangleShip<Character> ship2 = new RectangleShip<>("Custom", new Coordinate(2,2), 3, 2, customInfo, customInfo);
        assertEquals(6, ship2.getCoordinates().spliterator().getExactSizeIfKnown());
    }


    @Test
    public void test_hit_persistence() {
        RectangleShip<Character> ship = new RectangleShip<>("Test", new Coordinate(1,1), 2, 2, 't', 'x');
        ship.recordHitAt(new Coordinate(1, 1));
        ship.recordHitAt(new Coordinate(2, 2));

        assertTrue(ship.wasHitAt(new Coordinate(1, 1)));
        assertTrue(ship.wasHitAt(new Coordinate(2, 2)));
        assertFalse(ship.wasHitAt(new Coordinate(1, 2)));
    }
}