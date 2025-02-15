package edu.duke.yx304.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ComputerPlayer extends TextPlayer {
    private final Random random;
    private ArrayList<Coordinate> attackQueue;

    public ComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputReader,
        PrintStream out, AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputReader, out, shipFactory);
        this.random = new Random();
        this.attackQueue = new ArrayList<>();
        generateAttackQueue();
    }

    private void generateAttackQueue() {
        attackQueue = new ArrayList<>();
        for (int r = 0; r < theBoard.getHeight(); r++) {
            for (int c = 0; c < theBoard.getWidth(); c++) {
                attackQueue.add(new Coordinate(r, c));
            }
        }
        Collections.shuffle(attackQueue);
    }

    @Override
    public void doPlacementPhase() throws IOException {
        for (String shipName : shipsToPlace) {
            placeShipRandomly(shipName);
        }
    }

    private void placeShipRandomly(String shipName) throws IOException {
        while (true) {
            try {
                int row = random.nextInt(theBoard.getHeight());
                int col = random.nextInt(theBoard.getWidth());
                char orientation = random.nextBoolean() ? 'H' : 'V';
                if (shipName.equals("Battleship") || shipName.equals("Carrier")) {
                    char[] orientations = {'U', 'R', 'D', 'L'};
                    orientation = orientations[random.nextInt(4)];
                }
                Placement p = new Placement(new Coordinate(row, col),orientation);
                Ship<Character> s = shipCreationFns.get(shipName).apply(p);
                if (theBoard.tryAddShip(s) == null) {
                    break;
                }
            } catch (IllegalArgumentException e) {}
        }
    }

    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        if (random.nextInt(10) < 2 && CountForMove > 0) {
            out.println("Player " + name + " used a special action");
            out.println("--------------------------------------------------------------------------------");
            CountForMove--;
            return;
        }

        if (random.nextInt(10) < 1 && CountForScan > 0) {
            out.println("Player " + name + " used a special action");
            out.println("--------------------------------------------------------------------------------");
            CountForScan--;
            return;
        }

        // Fire attack
        while (!attackQueue.isEmpty()) {
            Coordinate c = attackQueue.remove(0);
            if (!enemyBoard.hasBeenAttacked(c)) {
                Ship<Character> hitShip = enemyBoard.fireAt(c);
                if (hitShip != null) {
                    out.println("Player " + name + " hit your " + hitShip.getName().toLowerCase() + " at " + c + "!");
                }
                else{
                    out.println("player " + name + " missed!");
                    out.println("--------------------------------------------------------------------------------");
                }
                return;
            }
        }
    }
}