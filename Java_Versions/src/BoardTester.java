/**
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

import igel.Board;
import igel.Player;


public class BoardTester {

    private static void testConstructor() {
        Board board = new Board();
        String expected = "|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Constructing a new empty board.", expected, actual);
    }

    private static void testPlaceToken(){
        Board board=new Board();
        board.placeToken(0, 0, 'B');
        String expected="B|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Place a new player token in a cell.", expected, actual);
    }

    private static void testCanMoveForward(){
        Board board=new Board();
        board.placeToken(0, 0, 'B');
        boolean expected=true;
        boolean actual=board.canMoveForward(0, 0);
        Testing.assertEquals("Check if the topmost token in a cell can move forward.", expected, actual);
        board=new Board();
        board.placeToken(0, 3, 'B');
        expected=true;
        actual=board.canMoveForward(0, 3);
        Testing.assertEquals("Check if the topmost token in a deactivated trap cell can move forward.", expected, actual);
        board=new Board();
        board.placeToken(0, 3, 'B');
        board.placeToken(0, 0, 'B');
        expected=false;
        actual=board.canMoveForward(0, 3);
        Testing.assertEquals("Check if the topmost token in an active trap cell can move forward.", expected, actual);
    }

    private static void testCanMoveSideways(){
        Board board=new Board();
        Player player=new Player('B');
        board.placeToken(0, 0, 'B');
        boolean expected=true;
        boolean actual=board.canMoveSideways(0, 0, player);
        Testing.assertEquals("Check if a player can move their token sideways (on top).", expected, actual);
        board.placeToken(0, 0, 'R');
        expected=false;
        actual=board.canMoveSideways(0, 0, player);
        Testing.assertEquals("Check if a player can move their token sideways (not on top).", expected, actual);
        board=new Board();
        board.placeToken(0, 3, 'B');
        actual=board.canMoveSideways(0, 3, player);
        Testing.assertEquals("Check if a player can move their token sideways from an active trap.", expected, actual);
        board.refreshTraps();
        expected=true;
        actual=board.canMoveSideways(0, 3, player);
        Testing.assertEquals("Check if a player can move their token sideways from a deactivated trap.", expected, actual);
    }

    private static void testEmptyRow(){
        Board board=new Board();
        boolean expected=true;
        boolean actual=board.emptyRow(0);
        Testing.assertEquals("Check if an empty row is empty or not.", expected, actual);
        board.placeToken(0, 0, 'B');
        expected=false;
        actual=board.emptyRow(0);
        Testing.assertEquals("Check if an occupied row is empty or not.", expected, actual);
        board=new Board();
        board.placeToken(0, 3, 'B');
        expected=true;
        actual=board.emptyRow(0);
        Testing.assertEquals("Check if a row with tokens in an active trap is empty or not.", expected, actual);
        board.refreshTraps();
        expected=false;
        actual=board.emptyRow(0);
        Testing.assertEquals("Check if a row with tokens in an inactive trap is empty or not.", expected, actual);
    }

    private static void testIsPlayerAt(){
        Board board=new Board();
        Player player=new Player('B');
        board.placeToken(3, 6, 'B');
        boolean expected=true;
        boolean actual=board.isPlayerAt(3, 6, player);
        Testing.assertEquals("Check whether or not the indicated player has a token in the cell. (positive case)", expected, actual);
        expected=false;
        actual=board.isPlayerAt(0, 0, player);
        Testing.assertEquals("Check whether or not the indicated player has a token in the cell. (negative case)", expected, actual);
    }

    private static void testIsPlayerTop(){
        Board board=new Board();
        Player player=new Player('B');
        board.placeToken(3, 6, 'B');
        boolean expected=true;
        boolean actual=board.isPlayerTop(3, 6, player);
        Testing.assertEquals("Check whether or not the indicated player has the topmost token in a cell. (positive case)", expected, actual);
        board.placeToken(3, 6, 'R');
        expected=false;
        actual=board.isPlayerTop(3, 6, player);
        Testing.assertEquals("Check whether or not the indicated player has the topmost token in the cell. (negative case)", expected, actual);
    }

    private static void testIsWinner(){
        Board board=new Board();
        Player player=new Player('B');
        board.placeToken(0, 8, 'B');
        board.placeToken(0, 8, 'B');
        board.placeToken(3, 8, 'B');
        boolean expected=true;
        boolean actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (multiple tokens in same cell)", expected, actual);
        board=new Board();
        board.placeToken(0, 8, 'B');
        board.placeToken(5, 8, 'B');
        board.placeToken(3, 8, 'B');
        expected=true;
        actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (all different cells)", expected, actual);
        board=new Board();
        board.placeToken(0, 8, 'B');
        expected=false;
        actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (no win scenario)", expected, actual);        
    }

    private static void testMoveForward(){
        Board board=new Board();
        board.placeToken(0, 0, 'B');
        board.moveForward(0, 0);
        String expected="|B||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Test moving a token forwards", expected, actual);
    }

    private static void testMoveSideways(){
        Board board=new Board();
        board.placeToken(0, 0, 'B');
        board.moveSideways(0, 0, 1);
        String expected="|||@|||||//B||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Test moving a token down", expected, actual);
        board=new Board();
        board.placeToken(3, 0, 'B');
        board.moveSideways(3, 0, -1);
        expected="|||@|||||//||||||@||//B||||@||||//|||||@|||//||@||||||//|||||||@|";
        actual=board.toString();
        Testing.assertEquals("Test moving a token up", expected, actual);
    }

    private static void testStackHeight(){
        Board board=new Board();
        int expected=0;
        int actual=board.stackHeight(0,0);
        Testing.assertEquals("Check the stack height of an empty cell.", expected, actual);
        actual=board.stackHeight(0,3);
        Testing.assertEquals("Check the stack height of an empty trap cell.", expected, actual);
        board.placeToken(0, 3, 'B');
        expected=1;
        actual=board.stackHeight(0,3);
        Testing.assertEquals("Check the stack height of a trap cell with a player token in it.", expected, actual);
        board.placeToken(0, 0, 'B');
        board.stackHeight(0, 0);
        Testing.assertEquals("Check the stack height of a normal cell with a player token in it.", expected, actual);
    }



    public static void main(String[] args) {
        Testing.startTests();
        Testing.testSection("Board class");
        testConstructor();
        testPlaceToken();
        testCanMoveForward();
        testCanMoveSideways();
        testEmptyRow();
        testIsPlayerAt();
        testIsPlayerTop();
        testIsWinner();
        testMoveForward();
        testMoveSideways();
        testStackHeight();
        Testing.finishTests();
    }
}
