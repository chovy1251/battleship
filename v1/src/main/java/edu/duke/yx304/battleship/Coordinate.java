package edu.duke.yx304.battleship;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(String descr) {
      if (descr == null || descr.length() != 2) {
          throw new IllegalArgumentException("Invalid coordinate string: " + descr);
      }
  
      char rowChar = descr.charAt(0);
      rowChar = Character.toUpperCase(rowChar);
      if (rowChar < 'A' || rowChar > 'Z') {
          throw new IllegalArgumentException("Invalid row character: " + rowChar);
      }
      this.row = rowChar - 'A'; 
  
      char columnChar = descr.charAt(1);
      if (columnChar < '0' || columnChar > '9') {
          throw new IllegalArgumentException("Invalid column character: " + columnChar);
      }
      this.column = columnChar - '0'; 
  }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
        Coordinate c = (Coordinate) o;
        return row == c.row && column == c.column;
      }
      return false;
    }

    @Override
    public String toString() {
      return "("+row+", " + column+")";
    }

    @Override
    public int hashCode() {
      return toString().hashCode();
    }
}
