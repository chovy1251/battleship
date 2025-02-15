// package edu.duke.yx304.battleship;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class V1ShipFactoryTest {

//     private V1ShipFactory shipFactory;
//     private Coordinate upperLeft;

//     @BeforeEach
//     public void setUp() {
//         shipFactory = new V1ShipFactory();
//         upperLeft = new Coordinate(1, 1);
//     }

//     private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
//         assertEquals(expectedName, testShip.getName(), "The ship name should match.");
        
//         for (Coordinate loc : expectedLocs) {
//             assertTrue(testShip.occupiesCoordinates(loc), "The ship should occupy coordinate " + loc);
//         }

//         for (Coordinate loc : expectedLocs) {
//             assertEquals(expectedLetter, testShip.getDisplayInfoAt(loc), "The display info at " + loc + " should match the expected letter.");
//         }
//     }

//     @Test
//     public void testMakeSubmarine() {
//         Placement where = new Placement(upperLeft, 'V');
//         Ship<Character> submarine = shipFactory.makeSubmarine(where);
        
//         checkShip(submarine, "Submarine", 's', new Coordinate(1, 1), new Coordinate(2, 1));
//     }

//     @Test
//     public void testMakeBattleship() {
//         Placement where = new Placement(upperLeft, 'V');
//         Ship<Character> battleship = shipFactory.makeBattleship(where);
        
//         checkShip(battleship, "Battleship", 'b', new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1));
//     }

//     @Test
//     public void testMakeCarrier() {
//         Placement where = new Placement(upperLeft, 'H');
//         Ship<Character> carrier = shipFactory.makeCarrier(where);
//         checkShip(carrier, "Carrier", 'c', new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 6));
//     }

//     @Test
//     public void testMakeDestroyer() {
//         Placement where = new Placement(upperLeft, 'H');
//         Ship<Character> destroyer = shipFactory.makeDestroyer(where);
        
//         checkShip(destroyer, "Destroyer", 'd', new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3));
//     }
// }
