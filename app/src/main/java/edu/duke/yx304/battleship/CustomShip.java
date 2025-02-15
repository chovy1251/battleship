package edu.duke.yx304.battleship;

public class CustomShip<T> extends BasicShip<T> {
    public CustomShip(String name, Iterable<Coordinate> coords, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(name, coords, myDisplayInfo, enemyDisplayInfo);
    }
}