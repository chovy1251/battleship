package edu.duke.yx304.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleShipBoardTest {

    @Test
    public void testBattleShipBoardConstructorValid() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());
    }

    @Test
    public void testBattleShipBoardConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0));
    }

    @Test
    public void testTryAddShip() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        Coordinate coordinate = new Coordinate("A0");
        Ship<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, 's', '*');
        String error = board.tryAddShip(ship);
        assertNull(error);
    }

    @Test
    public void testFireAtHit() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        Coordinate coordinate = new Coordinate("A0");
        Ship<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, 's', '*');
        board.tryAddShip(ship);
        Ship<Character> hitShip = board.fireAt(new Coordinate("A0"));
        assertNotNull(hitShip);
    }

    @Test
    public void testFireAtMiss() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        Coordinate coordinate = new Coordinate("A0");
        Ship<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, 's', '*');
        board.tryAddShip(ship);
        Ship<Character> hitShip = board.fireAt(new Coordinate("B0"));
        assertNotNull(hitShip);
    }

    @Test
    public void testHasBeenAttacked() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        Coordinate coordinate = new Coordinate("A0");
        Ship<Character> ship = new RectangleShip<>("TestShip", coordinate, 2, 2, 's', '*');
        board.tryAddShip(ship);
        board.fireAt(new Coordinate("A0"));
        assertTrue(board.hasBeenAttacked(new Coordinate("A0")));
        assertFalse(board.hasBeenAttacked(new Coordinate("B0")));
    }
}
