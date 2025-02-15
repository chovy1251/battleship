// package edu.duke.yx304.battleship;
// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

// public class NoCollisionRuleCheckerTest {

//     @Test
//     public void testNoCollision() {

//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
//         Ship<Character> ship1 = new RectangleShip<>(new Coordinate(2, 3), 's', '*');
//         board.tryAddShip(ship1);

//         Ship<Character> ship2 = new RectangleShip<>(new Coordinate(5, 5), 's', '*');
//         assertTrue(checker.checkPlacement(ship2, board));
//     }

//     @Test
//     public void testCollisionDetected() {
//         Board<Character> board = new BattleShipBoard<>(10, 20);
//         NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
//         Ship<Character> ship1 = new RectangleShip<>(new Coordinate(1, 1), 's', '*');
//         board.tryAddShip(ship1);


//         Ship<Character> collidingShip = new RectangleShip<>(new Coordinate(1, 1), 's', '*');
//         assertFalse(checker.checkPlacement(collidingShip, board));
//     }

//     @Test
//     public void testCombinedRules() {
//         PlacementRuleChecker<Character> checkerChain = 
//         new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
//         Board<Character> board = new BattleShipBoard<>(10, 20);

//         Ship<Character> validShip = new RectangleShip<>(new Coordinate(0, 0), 's', '*');
//         assertTrue(checkerChain.checkPlacement(validShip, board));
//         board.tryAddShip(validShip);


//         Ship<Character> overlappingShip = new RectangleShip<>(new Coordinate(0, 0), 's', '*');
//         assertFalse(checkerChain.checkPlacement(overlappingShip, board));
//         Ship<Character> outOfBoundsShip = new RectangleShip<>(new Coordinate(-1, 0), 's', '*');
//         assertFalse(checkerChain.checkPlacement(outOfBoundsShip, board));
//     }
// }