package edu.duke.yx304.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected=
      expectedHeader+
      "A  |  A\n"+
      "B  |  B\n"+
      expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    assertThrows(IllegalArgumentException.class, () -> {new BoardTextView(wideBoard); });
    assertThrows(IllegalArgumentException.class, () -> {new BoardTextView(tallBoard); });
  }

   private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }


  @Test
  public void test_display_empty_3by2(){
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\nB  |  B\nC  |  C\n";
    emptyBoardHelper(2, 3, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5(){
        String expectedHeader = "  0|1|2|3|4\n";
        String expectedBody = "A  | | | |  A\nB  | | | |  B\nC  | | | |  C\n";
        emptyBoardHelper(5, 3, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_4by3(){
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A  | |  A\nB  |s|  B\nC  | |  C\nD  | |  D\n";
        Board<Character> b1 = new BattleShipBoard<Character>(3, 4);
        BoardTextView view = new BoardTextView(b1);

        Coordinate c = new Coordinate(1,1);
        RectangleShip<Character> s = new RectangleShip<Character>(c,'s','*');
        b1.tryAddShip(s);

        assertEquals(expectedHeader+ expectedBody + expectedHeader, view.displayMyOwnBoard());
  }
}