package edu.duke.yx304.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class TextPlayer {

    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    protected AbstractShipFactory<Character> shipFactory;
    final String name;

    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    protected int CountForMove;
    protected int CountForScan;

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
        this.CountForMove = 3;
        this.CountForScan = 3;
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
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
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

    public void doFire(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException{
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
                out.println("player " + name + " missed!");
                out.println("--------------------------------------------------------------------------------");
            }
            //out.println();
            break;
        }
    }


    public void doMove() throws IOException {
        if (CountForMove <= 0) {
            out.println("You have no remaining move actions.");
            return;
        }
    
        while (true) {
            Coordinate c = readCoordinate("Player " + name + ", enter a coordinate of the ship you want to move: ");
            Ship<Character> oldShip = theBoard.findShip(c);
            if (oldShip == null) {
                out.println("Invalid coordinate: no ship found. Please try again.");
                continue;
            }
    
            Placement newPlacement = readPlacement("Enter new placement for the " + oldShip.getName() + " (e.g., A0H): ");
            String shipType = oldShip.getName();
            Function<Placement, Ship<Character>> createFn = shipCreationFns.get(shipType);
            if (createFn == null) {
                out.println("Error: Unknown ship type. Cannot move.");
                return;
            }
    
            Ship<Character> newShip;
            try {
                newShip = createFn.apply(newPlacement);
            } catch (IllegalArgumentException e) {
                out.println("Invalid placement: " + e.getMessage());
                continue;
            }
            //theBoard.myShips.remove(oldShip);
            theBoard.removeShip(oldShip);
            String error = theBoard.tryAddShip(newShip);
            if (error != null) {
                theBoard.tryAddShip(oldShip);
                out.println(error);
                continue;
            }
    
            transferHits(oldShip, newShip);
            CountForMove--;
    
            out.println("--------------------------------------------------------------------------------");
            out.println("Current ocean after moving:");
            out.print(view.displayMyOwnBoard());
            out.println("--------------------------------------------------------------------------------");
            break;
        }
    }
    
    protected void transferHits(Ship<Character> oldShip, Ship<Character> newShip) {
        Iterable<Coordinate> oldIte = oldShip.getCoordinates();
        ArrayList<Coordinate> oldCoords = new ArrayList<>();
        for(Coordinate c : oldIte){
            oldCoords.add(c);
        }

        ArrayList<Integer> hitIndices = new ArrayList<>();
        for (int i = 0; i < oldCoords.size(); i++) {
            if (oldShip.wasHitAt(oldCoords.get(i))) {
                hitIndices.add(i);
            }
        }
    
        Iterable<Coordinate> newIte = newShip.getCoordinates();
        ArrayList<Coordinate> newCoords = new ArrayList<>();

        for(Coordinate c : newIte){
            newCoords.add(c);
        }

        for (int index : hitIndices) {
            if (index < newCoords.size()) {
                newShip.recordHitAt(newCoords.get(index));
            }
        }
    }
    
    private Coordinate readCoordinate(String prompt) throws IOException {
        while (true) {
            //out.print("Player " + name + ", enter a coordinate of the ship you want to move: ");
            out.print(prompt);
            String input = inputReader.readLine();
            try {
                return new Coordinate(input);
            } catch (IllegalArgumentException e) {
                out.println("Invalid coordinate: " + e.getMessage());
            }
        }
    }

    // 在 TextPlayer 类中添加以下方法
    public void doScan(Board<Character> enemyBoard) throws IOException {
        if (CountForScan <= 0) {
            out.println("You have no sonar scans remaining.");
            return;
        }

        Coordinate center = readCoordinate("Player " + name + ", enter the center coordinate for sonar scan: ");
        HashSet<Coordinate> scanArea = generateSonarCoords(center, 3);

        HashMap<String, Integer> counts = new HashMap<>();
        counts.put("Submarine", 0);
        counts.put("Destroyer", 0);
        counts.put("Battleship", 0);
        counts.put("Carrier", 0);

        int width = enemyBoard.getWidth();
        int height = enemyBoard.getHeight();

        for (Coordinate coord : scanArea) {
            int row = coord.getRow();
            int col = coord.getColumn();
            if (row < 0 || row >= height || col < 0 || col >= width) {
                continue;
            }
            Ship<Character> ship = enemyBoard.getShipAt(coord);
            if (ship != null) {
                String type = ship.getName();
                counts.put(type, counts.get(type) + 1);
            }
        }

        out.println("--------------------------------------------------------------------------------");
        out.println("Sonar scan results:");
        out.println("Submarines occupy " + counts.get("Submarine") + " squares");
        out.println("Destroyers occupy " + counts.get("Destroyer") + " squares");
        out.println("Battleships occupy " + counts.get("Battleship") + " squares");
        out.println("Carriers occupy " + counts.get("Carrier") + " squares");
        out.println("--------------------------------------------------------------------------------");
        
        CountForScan--;
    }

    protected HashSet<Coordinate> generateSonarCoords(Coordinate center, int radius) {
        HashSet<Coordinate> coords = new HashSet<>();
        int centerRow = center.getRow();
        int centerCol = center.getColumn();

        for (int dr = -radius; dr <= radius; dr++) {
            int maxDc = radius - Math.abs(dr);
            for (int dc = -maxDc; dc <= maxDc; dc++) {
                coords.add(new Coordinate(centerRow + dr, centerCol + dc));
            }
        }
        return coords;
    }

    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {

        if (this instanceof ComputerPlayer) {
            return;
        }

        if (this.CountForMove == 0 && this.CountForScan == 0) {
            doFire(enemyBoard, enemyView, enemyName);
            return;
        }
    
        out.println("Possible actions for Player "+ name +":\n");
        out.println(" F Fire at a square");
        out.println(" M Move a ship to another square (" + this.CountForMove + " remaining)");
        out.println(" S Sonar scan (" + this.CountForScan +" remaining)\n");
        out.println("Player " + name + ", what would you like to do?");
        out.println("--------------------------------------------------------------------------------");
    
        while (true) {
            String s = inputReader.readLine().toUpperCase();
            if (s.equals("F")) {
                doFire(enemyBoard, enemyView, enemyName);
                break;
            } else if (s.equals("M")) {
                if (CountForMove > 0) {
                    doMove();
                    break;
                } else {
                    out.println("No more moves remaining.");
                }
            } else if (s.equals("S")) {
                if (CountForScan > 0) {
                    doScan(enemyBoard);
                    break;
                } else {
                    out.println("No more sonar scans remaining.");
                }
            } else {
                out.println("Invalid input, please try again.");
            }
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