// package edu.duke.yx304.battleship;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

// public class InBoundsRuleCheckerTest {

//     @Test
//     public void testInBounds() {
//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        
//         Ship<Character> ship = new RectangleShip<>(new Coordinate(5, 8), 's', '*');
//         assertTrue(checker.checkPlacement(ship, board));
//     }

//     @Test
//     public void testOutOfBoundsRow() {
//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        
//         Ship<Character> ship1 = new RectangleShip<>(new Coordinate(10, 5), 's', '*');
//         assertTrue(checker.checkPlacement(ship1, board));

//         Ship<Character> ship2 = new RectangleShip<>(new Coordinate(-1, 5), 's', '*');
//         assertFalse(checker.checkPlacement(ship2, board));
//     }

//     @Test
//     public void testOutOfBoundsColumn() {
//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        
//         Ship<Character> ship1 = new RectangleShip<>(new Coordinate(5, 20), 's', '*');
//         assertFalse(checker.checkPlacement(ship1, board));

//         Ship<Character> ship2 = new RectangleShip<>(new Coordinate(5, -1), 's', '*');
//         assertFalse(checker.checkPlacement(ship2, board));
//     }

//     @Test
//     public void testBoundaryCoordinates() {
//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        
//         Ship<Character> topLeft = new RectangleShip<>(new Coordinate(0, 0), 's', '*');
//         assertTrue(checker.checkPlacement(topLeft, board));

//         Ship<Character> bottomRight = new RectangleShip<>(new Coordinate(9, 19), 's', '*');
//         assertFalse(checker.checkPlacement(bottomRight, board));
//     }

//     @Test
//     public void testCombinedRules() {

//         PlacementRuleChecker<Character> checkerChain = 
//         new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
//         Board<Character> board = new BattleShipBoard<>(10, 20);


//         Ship<Character> validShip = new RectangleShip<>(new Coordinate(0, 0), 's', '*');
//         assertTrue(checkerChain.checkPlacement(validShip, board));

//         Ship<Character> outOfBoundsShip = new RectangleShip<>(new Coordinate(-1, 0), 's', '*');
//         assertFalse(checkerChain.checkPlacement(outOfBoundsShip, board));


//         board.tryAddShip(validShip);
//         Ship<Character> collidingShip = new RectangleShip<>(new Coordinate(0, 0), 's', '*');
//         assertFalse(checkerChain.checkPlacement(collidingShip, board));
//     }
// }