package edu.duke.yx304.battleship;

public abstract class PlacementRuleChecker<T> {
    private final PlacementRuleChecker<T> next;

    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

    public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
        String error = checkMyRule(theShip, theBoard);
        if (error != null) {
            return error;
        }
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        return null;
    }
}