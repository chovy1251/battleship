package edu.duke.yx304.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T>  {
    
    protected ShipDisplayInfo<T> myDisplayInfo;
    protected ShipDisplayInfo<T> enemyDisplayInfo;
    protected String name;//new
    protected HashMap<Coordinate, Boolean> myPieces;

    public BasicShip(String name, Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        this.name = name;
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
        myPieces = new HashMap<>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
    }

    @Override
    public String getName() {
        return name;
    }
    // Method to check if the ship occupies the given coordinate
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        //return myPieces.containsKey(where) && !myPieces.get(where);
        return myPieces.containsKey(where);
    }

    @Override
    public T getDisplayInfoAt(Coordinate where, boolean myShip) {
        checkCoordinateInThisShip(where);
        boolean hit = myPieces.get(where);
        ShipDisplayInfo<T> displayInfo = myShip ? myDisplayInfo : enemyDisplayInfo;
        return displayInfo.getInfo(where, hit);
    }

    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(c)) {
            throw new IllegalArgumentException("Coordinate " + c + " is not part of the ship");
        }
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    @Override
    public boolean isSunk() {
        for (boolean hit : myPieces.values()) {
            if (hit) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);
    }
    
    @Override
    public Iterable<Coordinate> getCoordinates() {
        return this.myPieces.keySet(); // Return an iterable using a lambda
    }
}
