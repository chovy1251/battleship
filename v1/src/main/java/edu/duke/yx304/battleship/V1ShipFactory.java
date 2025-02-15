package edu.duke.yx304.battleship;

@SuppressWarnings("rawtypes")
public class V1ShipFactory implements AbstractShipFactory{

    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) { 
        RectangleShip<Character> c;
        ShipDisplayInfo<Character> displayInfo = new SimpleShipDisplayInfo<>(letter, '*');

        //add in task19
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, letter);
        if (where.getOrientation() == 'V') {
            c = new RectangleShip<>(name, where.getWhere(), w, h, displayInfo, enemyDisplay);
        } else if (where.getOrientation() == 'H') {
            c = new RectangleShip<>(name, where.getWhere(), h, w, displayInfo, enemyDisplay);
        } else {
            throw new IllegalArgumentException("Invalid orientation: " + where.getOrientation() + ". Expected 'V' or 'H'.");
        }
        return c;
    }

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
      return createShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 1, 4, 'b', "Battleship");
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }
    
}
