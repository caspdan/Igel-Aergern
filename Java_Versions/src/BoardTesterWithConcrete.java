/**
 * Test suite for the Board Class
 * 
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

import igelWithConcrete.Board1;
import igelWithConcrete.Player1;


public class BoardTesterWithConcrete {

    private static void testConstructor() {
        Board1 board = new Board1();
        String expected = "|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Constructing a new empty board.", expected, actual);
    }

    private static void testPlaceToken(){
        Board1 board=new Board1();
        board.placeToken(0, 0, 'B');
        String expected="B|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Place a new player token in a cell.", expected, actual);
    }

    private static void testCanMoveForward(){
        Board1 board=new Board1();
        board.placeToken(0, 0, 'B');
        boolean expected=true;
        boolean actual=board.canMoveForward(0, 0);
        Testing.assertEquals("Check if the topmost token in a cell can move forward.", expected, actual);
        board=new Board1();
        board.placeToken(0, 2, 'B');
        expected=false;
        actual=board.canMoveForward(0, 2);
        Testing.assertEquals("Check if a token one cell before a trap can move forward.", expected, actual);
        board=new Board1();
    }

    private static void testCanMoveSideways(){
        Board1 board=new Board1();
        Player1 player=new Player1('B');
        board.placeToken(0, 0, 'B');
        boolean expected=true;
        boolean actual=board.canMoveSideways(0, 0, player);
        Testing.assertEquals("Check if a player can move their token sideways (on top).", expected, actual);
        board.placeToken(0, 0, 'R');
        expected=false;
        actual=board.canMoveSideways(0, 0, player);
        Testing.assertEquals("Check if a player can move their token sideways (not on top).", expected, actual);
        board=new Board1();
        board.placeToken(1, 3, 'B');
        actual=board.canMoveSideways(1, 3, player);
        expected=true;
        Testing.assertEquals("Check if a player can move their token sideways onto a trap.", expected, actual);
    }

    private static void testEmptyRow(){
        Board1 board=new Board1();
        boolean expected=true;
        boolean actual=board.emptyRow(0);
        Testing.assertEquals("Check if an empty row is empty or not.", expected, actual);
        board.placeToken(0, 0, 'B');
        expected=false;
        actual=board.emptyRow(0);
        Testing.assertEquals("Check if an occupied row is empty or not.", expected, actual);
        board=new Board1();
    }

    private static void testIsPlayerAt(){
        Board1 board=new Board1();
        Player1 player=new Player1('B');
        board.placeToken(3, 6, 'B');
        boolean expected=true;
        boolean actual=board.isPlayerAt(3, 6, player);
        Testing.assertEquals("Check whether or not the indicated player has a token in the cell. (positive case)", expected, actual);
        expected=false;
        actual=board.isPlayerAt(0, 0, player);
        Testing.assertEquals("Check whether or not the indicated player has a token in the cell. (negative case)", expected, actual);
    }

    private static void testIsPlayerTop(){
        Board1 board=new Board1();
        Player1 player=new Player1('B');
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
        Board1 board=new Board1();
        Player1 player=new Player1('B');
        board.placeToken(0, 8, 'B');
        board.placeToken(0, 8, 'B');
        board.placeToken(3, 8, 'B');
        boolean expected=true;
        boolean actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (multiple tokens in same cell)", expected, actual);
        board=new Board1();
        board.placeToken(0, 8, 'B');
        board.placeToken(5, 8, 'B');
        board.placeToken(3, 8, 'B');
        expected=true;
        actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (all different cells)", expected, actual);
        board=new Board1();
        board.placeToken(0, 8, 'B');
        expected=false;
        actual=board.isWinner(player);
        Testing.assertEquals("Check if the indicated player has won. (no win scenario)", expected, actual);        
    }

    private static void testMoveForward(){
        Board1 board=new Board1();
        board.placeToken(0, 0, 'B');
        board.moveForward(0, 0);
        String expected="|B||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Test moving a token forwards", expected, actual);
    }

    private static void testMoveSideways(){
        Board1 board=new Board1();
        board.placeToken(0, 0, 'B');
        board.moveSideways(0, 0, 1);
        String expected="|||@|||||//B||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual=board.toString();
        Testing.assertEquals("Test moving a token down", expected, actual);
        board=new Board1();
        board.placeToken(3, 0, 'B');
        board.moveSideways(3, 0, -1);
        expected="|||@|||||//||||||@||//B||||@||||//|||||@|||//||@||||||//|||||||@|";
        actual=board.toString();
        Testing.assertEquals("Test moving a token up", expected, actual);
    }

    private static void testStackHeight(){
        Board1 board=new Board1();
        int expected=0;
        int actual=board.stackHeight(0,0);
        Testing.assertEquals("Check the stack height of an empty cell.", expected, actual);
        actual=board.stackHeight(0,3);
        Testing.assertEquals("Check the stack height of an empty trap cell.", expected, actual);
        board.placeToken(0, 0, 'B');
        board.stackHeight(0, 0);
        Testing.assertEquals("Check the stack height of a normal cell with a player token in it.", expected, actual);
    }



    public static void main(String[] args) {
        Testing.startTests();
        Testing.testSection("Board1 class");
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
