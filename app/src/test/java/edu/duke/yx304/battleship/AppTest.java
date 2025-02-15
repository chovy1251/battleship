package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    void testComputerVsComputerGame() throws Exception {

        String input = "C\nC\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        BattleShipBoard<Character> b1 = new BattleShipBoard<>(10, 20);
        BattleShipBoard<Character> b2 = new BattleShipBoard<>(10, 20);
        V1ShipFactory factory = new V1ShipFactory();

        TextPlayer p1 = App.createPlayer("A", "A", br, factory, b1);
        TextPlayer p2 = App.createPlayer("B", "B", br, factory, b2);
        App app = new App(p1, p2);

        assertTrue(p1 instanceof TextPlayer);
        assertTrue(p2 instanceof ComputerPlayer);

        app.doPlacementPhase();
        assertShipCount(b1, 10);
        assertShipCount(b2, 10);
        sinkAllShips(b2);

        app.doAttackingPhase();
        assertFalse(outContent.toString().contains("Player A wins!"));
    }

    private void assertShipCount(BattleShipBoard<?> board, int expected) throws Exception {
        Field myShipsField = BattleShipBoard.class.getDeclaredField("myShips");
        myShipsField.setAccessible(true);
        ArrayList<?> ships = (ArrayList<?>) myShipsField.get(board);
        assertEquals(expected, ships.size());
    }

    private void sinkAllShips(BattleShipBoard<Character> board) throws Exception {
        Field myShipsField = BattleShipBoard.class.getDeclaredField("myShips");
        myShipsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Ship<Character>> ships = (ArrayList<Ship<Character>>) myShipsField.get(board);
        
        for (Ship<Character> ship : ships) {
            for (Coordinate c : ship.getCoordinates()) {
                ship.recordHitAt(c);
            }
        }
    }

    @Test
    void testComputerPlacement() throws Exception {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        @SuppressWarnings("unchecked")
        ComputerPlayer cpu = new ComputerPlayer("A", board, 
            new BufferedReader(new StringReader("")), System.out, new V1ShipFactory());
        
        Method placeShip = TextPlayer.class.getDeclaredMethod("setupShipCreationList");
        placeShip.setAccessible(true);
        placeShip.invoke(cpu);

        cpu.doPlacementPhase();
        assertShipCount(board, 20);
    }

    @Test
    void testShipRemoval() throws Exception {
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        Ship<Character> sub = new RectangleShip<>("Submarine", new Coordinate(0,0), 1, 2, 's', '*');
        

        assertEquals(null, board.tryAddShip(sub));
        assertShipCount(board, 1);
        board.removeShip(sub);
        assertShipCount(board, 0);
    }

    @Test
    void testMainWithComputerPlayers() throws IOException {
        String input = "C\nC\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));

        App.main(new String[0]);
        String output = bytes.toString();
        assertTrue(output.contains("wins!"));
    }

    @Test
    void testMainWithQuickGame() throws IOException {

        String input = "C\nC\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
        App.main(new String[0]);
        String output = bytes.toString();
        assertTrue(output.contains("Player A wins!") || output.contains("Player B wins!"));
    }

}