package edu.duke.yx304.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class TextPlayerTest {

    @Test
    public void testHasLost() {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        TextPlayer player = new TextPlayer("A", board, new BufferedReader(new StringReader("")), System.out, new V1ShipFactory());
        assertFalse(player.hasLost());
    }

    @Test
    public void testDoPlacementPhase() throws IOException {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        TextPlayer player = new TextPlayer("A", board, new BufferedReader(new StringReader("A0H\n")), System.out, new V1ShipFactory());
        player.doPlacementPhase();
        assertTrue(board.getWidth() > 0);
        assertTrue(board.getHeight() > 0);
    }

    @Test
    public void testPlayOneTurn() throws IOException {
        Board<Character> board1 = new BattleShipBoard<>(10, 20);
        Board<Character> board2 = new BattleShipBoard<>(10, 20);
        BufferedReader input = new BufferedReader(new StringReader("A0\n"));
        V1ShipFactory factory = new V1ShipFactory();
        TextPlayer player1 = new TextPlayer("A", board1, input, System.out, factory);
        TextPlayer player2 = new TextPlayer("B", board2, input, System.out, factory);
        player1.playOneTurn(board2, new BoardTextView(board2), "B");
        player2.playOneTurn(board1, new BoardTextView(board1), "A");
    }
}
