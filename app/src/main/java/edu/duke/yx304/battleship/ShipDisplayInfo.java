package edu.duke.yx304.battleship;

public interface ShipDisplayInfo<T> {
    public T getInfo(Coordinate where, boolean hit);
}
