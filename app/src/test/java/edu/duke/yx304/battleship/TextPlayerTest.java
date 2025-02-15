package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;

public class TextPlayerTest {
    private BufferedReader createInput(String input) {
        return new BufferedReader(new StringReader(input));
    }

    private ByteArrayOutputStream captureOutput() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
        return bytes;
    }

    @Test
    void testInitialization() throws IOException {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        @SuppressWarnings("unchecked")
        TextPlayer player = new TextPlayer("Test", board, 
            createInput(""), System.out, new V1ShipFactory());
        
        assertEquals(3, player.CountForMove);
        assertEquals(3, player.CountForScan);
        assertEquals(10, player.shipsToPlace.size()); // 2+3+3+2
    }

    @Test
    void testFireHit() throws IOException {
        Board<Character> enemyBoard = new BattleShipBoard<>(10, 20);
        Ship<Character> sub = new RectangleShip<>("Submarine", new Coordinate(0,0), 1, 2, 's', '*');
        enemyBoard.tryAddShip(sub);
        
        ByteArrayOutputStream bytes = captureOutput();
        @SuppressWarnings("unchecked")
        TextPlayer player = new TextPlayer("Test", new BattleShipBoard<>(10, 20), 
            createInput("A0\n"), System.out, new V1ShipFactory());
        
        player.doFire(enemyBoard, new BoardTextView(enemyBoard), "Enemy");
        assertTrue(bytes.toString().contains("hit a submarine"));
    }

    @Test
    void testShipMove() throws IOException {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        @SuppressWarnings("unchecked")
        TextPlayer player = new TextPlayer("Test", board, 
            createInput("A0\nB0H\n"), System.out, new V1ShipFactory());
        
        // 放置初始船只
        Ship<Character> sub = player.shipFactory.makeSubmarine(new Placement("A0H"));
        board.tryAddShip(sub);
        
        // 记录伤害
        sub.recordHitAt(new Coordinate(0,0));
        
        player.doMove();
        Ship<Character> newShip = board.findShip(new Coordinate(1,0));
        assertNotNull(newShip);
        assertTrue(newShip.wasHitAt(new Coordinate(1,0)));
    }

    @Test
    void testSonarScan() throws IOException {
        BattleShipBoard<Character> enemyBoard = new BattleShipBoard<>(10, 20);
        enemyBoard.tryAddShip(new RectangleShip<>("Submarine", new Coordinate(2,2), 1, 2, 's', '*'));
        
        ByteArrayOutputStream bytes = captureOutput();
        @SuppressWarnings("unchecked")
        TextPlayer player = new TextPlayer("Test", new BattleShipBoard<>(10, 20), 
            createInput("C3\n"), System.out, new V1ShipFactory());
        
        player.doScan(enemyBoard);
        assertTrue(bytes.toString().contains("Submarines occupy 2 squares"));
        assertEquals(2, player.CountForScan);
    }

    @Test
    void testAttackPhaseDisplay() throws IOException {
        Board<Character> enemyBoard = new BattleShipBoard<>(10, 20);
        ByteArrayOutputStream bytes = captureOutput();
        @SuppressWarnings("unchecked")
        TextPlayer player = new TextPlayer("Test", new BattleShipBoard<>(10, 20), 
            createInput("F\nA0\n"), System.out, new V1ShipFactory());
        
        player.playOneTurn(enemyBoard, new BoardTextView(enemyBoard), "Enemy");
        assertTrue(bytes.toString().contains("Possible actions"));
        assertTrue(bytes.toString().contains("missed!"));
    }
}