package edu.duke.yx304.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    private final TextPlayer player1;
    private final TextPlayer player2;

    public App(TextPlayer player1, TextPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void doPlacementPhase() throws IOException {
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }

    public void doAttackingPhase() throws IOException {
        while (true) {
            player1.playOneTurn(player2.theBoard, player2.view, "B");
            if (player2.hasLost()) {
                System.out.println("Player A wins!");
                return;
            }
            player2.playOneTurn(player1.theBoard, player1.view, "A");
            if (player1.hasLost()) {
                System.out.println("Player B wins!");
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    static TextPlayer createPlayer(String prompt, String defaultName, BufferedReader input, V1ShipFactory factory, Board<Character> board) throws IOException {
        System.out.print(prompt + " (H/C)? ");
        while (true) {
            String type = input.readLine().toUpperCase();
            //Board<Character> board = new BattleShipBoard<>(10, 20);
    
            if (type.equals("H")) {
                return new TextPlayer(defaultName, board, input, System.out, factory);
            } 
            else if (type.equals("C")) {
                return new ComputerPlayer(defaultName, board, input, System.out, factory);
            }
            else{
                System.out.println("Invalid input, please try again.");
            }    
        }
    }

    public static void main(String[] args) throws IOException {
        Board<Character> b1 = new BattleShipBoard<>(10, 20);
        Board<Character> b2 = new BattleShipBoard<>(10, 20);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        V1ShipFactory factory = new V1ShipFactory();

        TextPlayer p1 = createPlayer("Is Player A human or computer", "A", input, factory, b1);
        TextPlayer p2 = createPlayer("Is Player B human or computer", "B", input, factory, b2);
        App app = new App(p1, p2);
        
        app.doPlacementPhase();
        app.doAttackingPhase();
    }
}