package edu.duke.yx304.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>  {
    private final int width;
    final ArrayList<Ship<T>> myShips;
    private final int height;
    private final PlacementRuleChecker<T> placementChecker;
    HashSet<Coordinate> enemyMisses;
    final T missInfo;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    
    public BattleShipBoard(int w, int h) {
        this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), (T) Character.valueOf('X'));
    }

    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker) {
        this(w, h, placementChecker, (T) Character.valueOf('X'));
    }

   /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = placementChecker;
        this.enemyMisses = new HashSet<>();
        this.missInfo = missInfo;
    }

    public String tryAddShip(Ship<T> toAdd) {
        String error = placementChecker.checkPlacement(toAdd, this);
        if (error != null) {
            return error;
        }
        myShips.add(toAdd);
        return null;
    }

    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where, isSelf);
            }
        }
        if (!isSelf && enemyMisses.contains(where)) {
            return missInfo;
        }
        return null;
    }

    public Ship<T> fireAt(Coordinate c) {
        for (Ship<T> ship : myShips) {
            if (ship.occupiesCoordinates(c)) {
                ship.recordHitAt(c);
                return ship;
            }
        }
        enemyMisses.add(c);
        return null;
    }

    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    @Override
    public boolean allShipsSunk() {
        for (Ship<T> ship : myShips) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean tryAttack(Coordinate c) {
        for (Ship<T> ship : myShips) {
            if (ship.occupiesCoordinates(c)) {
                ship.recordHitAt(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasBeenAttacked(Coordinate c) {
        if (enemyMisses.contains(c)) {
            return true;
        }
        for (Ship<T> ship : myShips) {
            if (ship.occupiesCoordinates(c) && ship.wasHitAt(c)) {
                return true;
            }
        }
        return false;
    }
}