// package edu.duke.yx304.battleship;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.HashSet;

// public class RectangleShipTest {

//     private Coordinate upperLeft;
//     private int width;
//     private int height;
//     private RectangleShip<Character> ship;
//     private ShipDisplayInfo<Character> displayInfo;

//     @BeforeEach
//     public void setUp() {
//         upperLeft = new Coordinate(1, 1);
//         width = 2;
//         height = 2;
//         displayInfo = new SimpleShipDisplayInfo<>('*', 'X');
//         ship = new RectangleShip<>("submarine", upperLeft, width, height, displayInfo);
//     }

//     @Test
//     public void testMakeCoords() {
//         HashSet<Coordinate> expectedCoords = new HashSet<>();
//         expectedCoords.add(new Coordinate(1, 1));
//         expectedCoords.add(new Coordinate(1, 2));
//         expectedCoords.add(new Coordinate(2, 1));
//         expectedCoords.add(new Coordinate(2, 2));

//         HashSet<Coordinate> result = RectangleShip.makeCoords(upperLeft, width, height);

//         assertEquals(expectedCoords, result);
//     }

//     @Test
//     public void testOccupiesCoordinates() {
//         Coordinate inside = new Coordinate(1, 1);
//         Coordinate outside = new Coordinate(3, 3); 

//         assertTrue(ship.occupiesCoordinates(inside));
//         assertFalse(ship.occupiesCoordinates(outside));
//     }


//     @Test
//     public void testGetDisplayInfoAt() {
//         Coordinate inside = new Coordinate(1, 1); 
//         assertEquals('*', ship.getDisplayInfoAt(inside));
//     }

//     @Test
//     public void testRectangleShipConstructor() {
//         HashSet<Coordinate> expectedCoords = new HashSet<>();
//         expectedCoords.add(new Coordinate(1, 1));
//         expectedCoords.add(new Coordinate(1, 2));
//         expectedCoords.add(new Coordinate(2, 1));
//         expectedCoords.add(new Coordinate(2, 2));

//         for (Coordinate coord : expectedCoords) {
//             assertTrue(ship.occupiesCoordinates(coord));
//         }
//     }

//     @Test
//     public void testDefaultSizeConstructor() {
//         RectangleShip<Character> defaultShip = new RectangleShip<>(upperLeft, '*', 'X');
//         Coordinate inside = new Coordinate(1, 1); 
//         assertTrue(defaultShip.occupiesCoordinates(inside), "The coordinate (1, 1) should be part of the default ship.");
//     }

//     @Test
//     public void testHitTracking() {
//         Coordinate c = new Coordinate(1, 2);
//         RectangleShip<Character> ship = new RectangleShip<>(c, 's', '*');
        
//         assertFalse(ship.wasHitAt(c));
        
//         ship.recordHitAt(c);
//         assertTrue(ship.wasHitAt(c));

//         assertTrue(ship.isSunk());
//     }



//     @Test
//     public void testDisplayInfo() {
//         Coordinate c = new Coordinate(0,0);
//         RectangleShip<Character> ship = new RectangleShip<>(c, 's', '*');
//         assertEquals('s', ship.getDisplayInfoAt(c));
//     }
// }
