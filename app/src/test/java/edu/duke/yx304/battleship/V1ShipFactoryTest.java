package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
    private Set<Coordinate> coordinateSet(Iterable<Coordinate> coords) {
        return StreamSupport.stream(coords.spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Test
    public void testMakeSubmarine() {
        V1ShipFactory factory = new V1ShipFactory();
        
        // Horizontal placement
        Placement hPlacement = new Placement("A0H");
        Ship<Character> hSub = factory.makeSubmarine(hPlacement);
        Set<Coordinate> expectedH = new HashSet<>();
        expectedH.add(new Coordinate(0, 0));
        expectedH.add(new Coordinate(0, 1));
        assertEquals(expectedH, coordinateSet(hSub.getCoordinates()));

        // Vertical placement
        Placement vPlacement = new Placement("B2V");
        Ship<Character> vSub = factory.makeSubmarine(vPlacement);
        Set<Coordinate> expectedV = new HashSet<>();
        expectedV.add(new Coordinate(2, 2));
        expectedV.add(new Coordinate(1, 2));
        assertEquals(expectedV, coordinateSet(vSub.getCoordinates()));
    }

    @Test
    public void testMakeDestroyer() {
        V1ShipFactory factory = new V1ShipFactory();
        
        Placement hPlacement = new Placement("C3H");
        Ship<Character> hDestroyer = factory.makeDestroyer(hPlacement);
        Set<Coordinate> hCoords = coordinateSet(hDestroyer.getCoordinates());
        assertEquals(3, hCoords.size());
        assertTrue(hCoords.contains(new Coordinate(2, 3)));
        assertTrue(hCoords.contains(new Coordinate(2, 4)));
        assertTrue(hCoords.contains(new Coordinate(2, 5)));

        Placement vPlacement = new Placement("D4V");
        Ship<Character> vDestroyer = factory.makeDestroyer(vPlacement);
        Set<Coordinate> vCoords = coordinateSet(vDestroyer.getCoordinates());
        assertTrue(vCoords.contains(new Coordinate(4, 4)));
        assertTrue(vCoords.contains(new Coordinate(5, 4)));
        assertTrue(vCoords.contains(new Coordinate(3, 4)));
    }

    @Test
    public void testMakeBattleshipOrientations() {
        V1ShipFactory factory = new V1ShipFactory();
        
        Placement up = new Placement("A0U");
        Ship<Character> bUp = factory.makeBattleship(up);
        Set<Coordinate> actualUp = coordinateSet(bUp.getCoordinates());
        Set<Coordinate> expectedUp = new HashSet<>();
        expectedUp.add(new Coordinate(0, 1));
        expectedUp.add(new Coordinate(1, 0));
        expectedUp.add(new Coordinate(1, 1));
        expectedUp.add(new Coordinate(1, 2));
        assertEquals(expectedUp, actualUp);

        Placement right = new Placement("B1R");
        Ship<Character> bRight = factory.makeBattleship(right);
        Set<Coordinate> actualRight = coordinateSet(bRight.getCoordinates());
        Set<Coordinate> expectedRight = new HashSet<>();
        expectedRight.add(new Coordinate(1, 1));
        expectedRight.add(new Coordinate(2, 1));
        expectedRight.add(new Coordinate(3, 1));
        expectedRight.add(new Coordinate(2, 2));
        assertEquals(expectedRight, actualRight);
    }
}