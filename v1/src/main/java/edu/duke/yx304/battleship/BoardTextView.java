package edu.duke.yx304.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the 
 * enemy's board.
 */
public class BoardTextView {

    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;

    /**
     * Constructs a BoardView, given the board it will display.
     * 
     * @param toDisplay is the Board to display
     * @throws IllegalArgumentException if the board is larger than 10x26.  
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
        throw new IllegalArgumentException(
            "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }

    public String displayMyOwnBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
    }

    public String displayEnemyBoard() {
        return displayAnyBoard(c -> toDisplay.whatIsAtForEnemy(c));
    }

    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder display = new StringBuilder();
        display.append(makeHeader());
        for (int row = 0; row < toDisplay.getHeight(); row++) {
            display.append((char) ('A' + row));

            Coordinate t1 = new Coordinate(row, 0);
            Character inf = getSquareFn.apply(t1);
            display.append(" ").append(inf != null ? inf : ' ');

            for (int col = 1; col < toDisplay.getWidth(); col++) {
                Coordinate temp = new Coordinate(row, col);
                Character info = getSquareFn.apply(temp);
                display.append("|").append(info != null ? info : ' ');
            }
            display.append(" ").append((char) ('A' + row)).append("\n");
        }
        display.append(makeHeader());
        return display.toString();
    }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     * 
     * @return the String that is the header line for the given board
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
        String sep=""; //start with nothing to separate, then switch to | to separate
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }

    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        int boardWidth = this.toDisplay.getWidth();
        int separatorStart = 2 * boardWidth + 19;
        
        String[] myLines = this.displayMyOwnBoard().split("\n");
        String[] enemyLines = enemyView.displayEnemyBoard().split("\n");
        
        StringBuilder header = new StringBuilder();
        header.append(" ".repeat(5))
            .append(myHeader)
            .append(" ".repeat(separatorStart - myHeader.length() - 5))
            .append(enemyHeader)
            .append("\n");
        
        StringBuilder merged = new StringBuilder(header);
        for (int i = 0; i < myLines.length; i++) {
            String leftLine = myLines[i];
            String rightLine = enemyLines[i];
            
            int spaceBetween = separatorStart - leftLine.length();
            if (spaceBetween < 0) spaceBetween = 0;
            
            merged.append(leftLine)
                .append(" ".repeat(spaceBetween))
                .append(rightLine)
                .append("\n");
        }
        merged.append("--------------------------------------------------------------------------------");
        
        return merged.toString();
    }

}
