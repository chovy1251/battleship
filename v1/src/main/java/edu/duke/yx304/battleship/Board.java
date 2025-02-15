package edu.duke.yx304.battleship;

public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public T whatIsAtForSelf(Coordinate where);
  public Ship<T> fireAt(Coordinate c);
  public T whatIsAtForEnemy(Coordinate where);
  boolean allShipsSunk();
  boolean tryAttack(Coordinate c);
  boolean hasBeenAttacked(Coordinate c);
}