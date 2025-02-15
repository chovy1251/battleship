package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {

    private Ship<Character> createHorizontalShip(Coordinate upperLeft, int width) {
        return new RectangleShip<>("TestShip", upperLeft, width, 1, 's', 'x');
    }

    private Ship<Character> createVerticalShip(Coordinate upperLeft, int height) {
        return new RectangleShip<>("TestShip", upperLeft, 1, height, 's', 'x');
    }

    @Test
    public void test_no_collision() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> existingShip = createHorizontalShip(new Coordinate(2, 3), 3);
        board.tryAddShip(existingShip);
        Ship<Character> newShip = createHorizontalShip(new Coordinate(5, 5), 2);
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertNull(checker.checkPlacement(newShip, board));
    }

    @Test
    public void test_full_overlap() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> existingShip = createHorizontalShip(new Coordinate(2, 3), 3);
        board.tryAddShip(existingShip);
        Ship<Character> newShip = createHorizontalShip(new Coordinate(2, 3), 3);
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkPlacement(newShip, board));
    }

    @Test
    public void test_partial_overlap() {
        Board<Character> board = new BattleShipBoard<>(10, 20);

        Ship<Character> existingShip = createHorizontalShip(new Coordinate(5, 5), 3);
        board.tryAddShip(existingShip);
        Ship<Character> newShip = createHorizontalShip(new Coordinate(5, 6), 3);
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkPlacement(newShip, board));
    }

    @Test
    public void test_multiple_existing_ships() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        board.tryAddShip(createVerticalShip(new Coordinate(1, 1), 4));  // (1,1)-(4,1)
        board.tryAddShip(createHorizontalShip(new Coordinate(3, 5), 5));// (3,5)-(3,9)

        Ship<Character> newShip = createVerticalShip(new Coordinate(3, 7), 3); // (3,7)-(5,7)
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkPlacement(newShip, board));
    }

    @Test
    public void test_edge_to_edge() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        board.tryAddShip(createHorizontalShip(new Coordinate(0, 0), 5));
        Ship<Character> rightShip = createHorizontalShip(new Coordinate(0, 5), 3);
        Ship<Character> downShip = createHorizontalShip(new Coordinate(1, 0), 3);
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertNull(checker.checkPlacement(rightShip, board));
        assertNull(checker.checkPlacement(downShip, board));
    }

    @Test
    public void test_diagonal_overlap() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        board.tryAddShip(new RectangleShip<>("BigShip", new Coordinate(2,2), 3, 3, 'b', 'x'));

        Ship<Character> topLeft = createHorizontalShip(new Coordinate(1, 1), 1);   // (1,1)
        Ship<Character> topRight = createHorizontalShip(new Coordinate(1, 5), 1);  // (1,5)
        Ship<Character> bottomLeft = createHorizontalShip(new Coordinate(5, 1), 1); // (5,1)
        Ship<Character> bottomRight = createHorizontalShip(new Coordinate(5, 5), 1);// (5,5)
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertNull(checker.checkPlacement(topLeft, board));
        assertNull(checker.checkPlacement(topRight, board));
        assertNull(checker.checkPlacement(bottomLeft, board));
        assertNull(checker.checkPlacement(bottomRight, board));

        Ship<Character> overlapCorner = createHorizontalShip(new Coordinate(2, 2), 1);
        assertEquals("That placement is invalid: the ship overlaps another ship.",checker.checkPlacement(overlapCorner, board));
    }
}