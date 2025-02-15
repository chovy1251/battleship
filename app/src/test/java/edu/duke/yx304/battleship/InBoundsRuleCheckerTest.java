package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {

    private Ship<Character> createTestShip(Coordinate... coords) {
        return new RectangleShip<>("TestShip", coords[0], 1, 1, 's', 'X');
    }

    @Test
    public void test_within_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(new Coordinate(5, 8));
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertNull(checker.checkPlacement(ship, board));
    }

    @Test
    public void test_top_out_of_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(new Coordinate(-1, 3));
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship goes off the top of the board.", 
                    checker.checkPlacement(ship, board));
    }

    @Test
    public void test_bottom_out_of_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(new Coordinate(20, 5));
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.", checker.checkPlacement(ship, board));
    }

    @Test
    public void test_left_out_of_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(new Coordinate(5, -3));
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship goes off the left of the board.", checker.checkPlacement(ship, board));
    }

    @Test
    public void test_right_out_of_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(new Coordinate(8, 20));
        
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship goes off the right of the board.", 
                    checker.checkPlacement(ship, board));
    }

    @Test
    public void test_multiple_out_of_bounds() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> ship = createTestShip(
            new Coordinate(-2, 5),
            new Coordinate(5, 25)
        );
        
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals("That placement is invalid: the ship goes off the top of the board.", checker.checkPlacement(ship, board));
    }

}