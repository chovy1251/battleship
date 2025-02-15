package edu.duke.yx304.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {

    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    final String name;

    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader,
        PrintStream out, AbstractShipFactory<Character> shipFactory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputReader;
        this.out = out;
        this.shipFactory = shipFactory;

        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        setupShipCreationMap();
        setupShipCreationList();
    }

    public Placement readPlacement(String prompt) throws IOException {
        out.print(prompt);
        String s = inputReader.readLine();
        return new Placement(s);
    }

    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            try {
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
                Ship<Character> s = createFn.apply(p);
                String error = theBoard.tryAddShip(s);
                if (error != null) {
                    out.println(error);
                    continue;
                }
                out.println("--------------------------------------------------------------------------------");
                out.println("Current ocean:");
                out.print(view.displayMyOwnBoard());
                out.println("--------------------------------------------------------------------------------");
                break;
            } catch (IllegalArgumentException e) {
                out.println("Invalid placement: " + e.getMessage());
            }
        }
    }

    protected void setupShipCreationMap(){
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }

    protected void setupShipCreationList(){
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        // shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        // shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        // shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    public boolean hasLost() {
        return theBoard.allShipsSunk();
    }

    public void doPlacementPhase() throws IOException {
        out.println(view.displayMyOwnBoard());
        String instruction = String.format(
            "--------------------------------------------------------------------------------\n" +
            "Player %s: you are going to place the following ships (which are all\n" +
            "rectangular). For each ship, type the coordinate of the upper left\n" +
            "side of the ship, followed by either H (for horizontal) or V (for\n" +
            "vertical).  For example M4H would place a ship horizontally starting\n" +
            "at M4 and going to the right.  You have\n\n" +
            "2 \"Submarines\" ships that are 1x2 \n" +
            "3 \"Destroyers\" that are 1x3\n" +
            "3 \"Battleships\" that are 1x4\n" +
            "2 \"Carriers\" that are 1x6\n" +
            "--------------------------------------------------------------------------------",
            name
        );
        
        out.println(instruction);
        //doOnePlacement("Submarine", (p)->shipFactory.makeSubmarine(p));
        for (String shipName : shipsToPlace) {
            Function<Placement, Ship<Character>> createFn = shipCreationFns.get(shipName);
            doOnePlacement(shipName, createFn);
        }
    }

    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        while (true) {
            out.println("Player " + name + "'s turn:");
            String myHeader = "Your ocean";
            String enemyHeader = "Player " + enemyName + "'s ocean";
            String combinedDisplay = view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader);
            out.println(combinedDisplay);
    
            Coordinate attackCoord = readAttackCoordinate();
    
            if (enemyBoard.hasBeenAttacked(attackCoord)) {
                out.println("You have already attacked this coordinate. Please choose another.");
                continue;
            }
    
            Ship<Character> hitShip = enemyBoard.fireAt(attackCoord);
            if (hitShip != null) {
                out.println("--------------------------------------------------------------------------------");
                out.println("You hit a " + hitShip.getName().toLowerCase() + "!");
                out.println("--------------------------------------------------------------------------------");
            } else {
                out.println("--------------------------------------------------------------------------------");
                out.println("You missed!");
                out.println("--------------------------------------------------------------------------------");
            }
            //out.println();
            break;
        }
    }
    
    private Coordinate readAttackCoordinate() throws IOException {
        while (true) {
            out.print("Player " + name + ", enter coordinate to attack: ");
            String input = inputReader.readLine();
            try {
                return new Coordinate(input);
            } catch (IllegalArgumentException e) {
                out.println("Invalid coordinate: " + e.getMessage());
            }
        }
    }

}