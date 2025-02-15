package edu.duke.yx304.battleship;

public class Placement {
    private final Coordinate where;
    private final char orientation;

    public Placement(Coordinate where, char orientation) {
        this.where = where;
        this.orientation = Character.toUpperCase(orientation);
    }

    public Placement(String str) {
        if (str == null || str.length() != 3) {
            throw new IllegalArgumentException("Invalid placement: " + str);
        }
        this.where = new Coordinate(str.substring(0, 2)); 
        this.orientation = Character.toUpperCase(str.charAt(2));
    }

    public Coordinate getWhere() {
        return where;
    }

    public char getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return where.toString() + orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement = (Placement) o;
        return orientation == placement.orientation && where.equals(placement.where);
    }

    @Override
    public int hashCode() {
        return 37 * where.hashCode() + Character.toUpperCase(orientation);
    }
}
