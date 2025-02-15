package edu.duke.yx304.battleship;

import java.util.HashSet;

@SuppressWarnings("rawtypes")
public class V1ShipFactory implements AbstractShipFactory {

    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        char orientation = where.getOrientation();
        if (orientation != 'H' && orientation != 'V') {
            throw new IllegalArgumentException("Invalid orientation: " + orientation + ". Expected 'H' or 'V'.");
        }
        int width = orientation == 'H' ? h : w;
        int height = orientation == 'V' ? h : w;
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>(letter, '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, letter);
        return new RectangleShip<>(name, where.getWhere(), width, height, myDisplay, enemyDisplay);
    }

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        char orientation = where.getOrientation();
        if (orientation != 'U' && orientation != 'R' && orientation != 'D' && orientation != 'L') {
            throw new IllegalArgumentException("Invalid orientation for Battleship: " + orientation);
        }
        Coordinate upperLeft = where.getWhere();
        HashSet<Coordinate> coords = generateBattleshipCoords(upperLeft, orientation);
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('b', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 'b');
        return new CustomShip<>("Battleship", coords, myDisplay, enemyDisplay);
    }

    private HashSet<Coordinate> generateBattleshipCoords(Coordinate upperLeft, char orientation) {
        int[][] offsets;
        switch (orientation) {
            case 'U':
                offsets = new int[][]{{0, 1}, {1, 0}, {1, 1}, {1, 2}};
                break;
            case 'R':
                offsets = new int[][]{{0, 0}, {1, 0}, {2, 0}, {1, 1}};
                break;
            case 'D':
                offsets = new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 1}};
                break;
            case 'L':
                offsets = new int[][]{{0, 1}, {1, 0}, {1, 1}, {2, 1}};
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation for Battleship: " + orientation);
        }
        HashSet<Coordinate> coords = new HashSet<>();
        for (int[] offset : offsets) {
            int row = upperLeft.getRow() + offset[0];
            int col = upperLeft.getColumn() + offset[1];
            coords.add(new Coordinate(row, col));
        }
        return coords;
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        char orientation = where.getOrientation();
        if (orientation != 'U' && orientation != 'R' && orientation != 'D' && orientation != 'L') {
            throw new IllegalArgumentException("Invalid orientation for Carrier: " + orientation);
        }
        Coordinate upperLeft = where.getWhere();
        HashSet<Coordinate> coords = generateCarrierCoords(upperLeft, orientation);
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('c', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 'c');
        return new CustomShip<>("Carrier", coords, myDisplay, enemyDisplay);
    }

    private HashSet<Coordinate> generateCarrierCoords(Coordinate upperLeft, char orientation) {
        int[][] offsets;
        switch (orientation) {
            case 'U':
                offsets = new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, 1}, {3, 0}, {3, 1}, {4, 1}};
                break;
            case 'R':
                offsets = new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 0}, {1, 1}, {1, 2}};
                break;
            case 'D':
                offsets = new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 0}, {2, 1}, {3, 1}, {4, 1}};
                break;
            case 'L':
                offsets = new int[][]{{0, 2}, {0, 3}, {0, 4}, {1, 0}, {1, 1}, {1, 2}, {1, 3}};
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation for Carrier: " + orientation);
        }
        HashSet<Coordinate> coords = new HashSet<>();
        for (int[] offset : offsets) {
            int row = upperLeft.getRow() + offset[0];
            int col = upperLeft.getColumn() + offset[1];
            coords.add(new Coordinate(row, col));
        }
        return coords;
    }
}