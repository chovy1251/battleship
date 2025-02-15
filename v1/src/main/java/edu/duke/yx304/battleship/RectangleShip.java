package edu.duke.yx304.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{

    final String name;

    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    public String getName(){
        return this.name;
    }

    
    public static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> coords = new HashSet<>();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                coords.add(new Coordinate(upperLeft.getRow() + r, upperLeft.getColumn() + c));
            }
        }
        return coords;
    }
}
