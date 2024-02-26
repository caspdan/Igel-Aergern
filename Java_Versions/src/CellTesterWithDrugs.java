/**
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

import igelWithDrugs.Cell2;
import igelWithDrugs.Igel2;
import igelWithDrugs.Player2;

public class CellTesterWithDrugs {

    private static void testConstructor() {
        Cell2 cell = new Cell2();
        String expected = "";
        String actual = cell.toString();
        Testing.assertEquals("Constructing a new empty cell.", expected, actual);
    }

    private static void testTrap() {
        Cell2 cell = new Cell2();
        cell.toggleTrap();
        String expected = "@";
        String actual = cell.toString();
        Testing.assertEquals("Making a newly created cell a trap. (toggleTrap)", expected, actual);
    }

    private static void testIsTrap() {
        Cell2 cell = new Cell2();
        boolean expected = false;
        boolean actual = cell.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
        Cell2 cell2 = new Cell2();
        cell2.toggleTrap();
        expected = true;
        actual = cell2.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
    }

    private static void testCreateTrap() {
        Cell2 cell = new Cell2();
        cell.createTrap(0, 3);
        String expected="@";
        String actual=cell.toString();
        Testing.assertEquals("Making a newly created cell a trap.", expected, actual);
    }

    private static void testAddToken() {
        Cell2 cell=new Cell2();
        Igel2 token = new Igel2('B');
        cell.addToken(token);
        String expected="B";
        String actual=cell.toString();
        Testing.assertEquals("Adding a token to an empty cell.", expected, actual);
        Igel2 token2=new Igel2('R');
        cell.addToken(token2);
        expected="BR";
        actual=cell.toString();
        Testing.assertEquals("Adding a token to a non-empty cell.", expected, actual);
    }

    private static void testPopToken(){
        Cell2 cell=new Cell2();
        Igel2 token = new Igel2('B');
        cell.addToken(token);
        cell.popToken();
        String expected="";
        String actual=cell.toString();
        Testing.assertEquals("Removing a token to an empty cell.", expected, actual);
    }

    private static void testGetTopToken(){
        Cell2 cell=new Cell2();
        Igel2 token = new Igel2('B');
        Igel2 token2 = new Igel2('R');
        cell.addToken(token);
        cell.addToken(token2);
        Igel2 expected=token2;
        Igel2 actual=cell.getTopToken();
        Testing.assertEquals("Checking the topmost token in a cell.", expected, actual);
    }

    private static void testIsEmpty(){
        Cell2 cell=new Cell2();
        boolean expected=true;
        boolean actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not an empty cell is empty.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token returns as empty.", expected, actual);
        Igel2 token = new Igel2('B');
        cell.addToken(token);
        expected=true;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token and a player token returns as empty.", expected, actual);
        cell.toggleTrap();
        expected=false;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, deactivated trap cell with a trap token and a player token returns as empty.", expected, actual);
        Cell2 cell2=new Cell2();
        cell2.addToken(token);
        expected=false;
        actual=cell2.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, non-trap cell with a player token returns as empty.", expected, actual);
    }

    private static void testStackHeight(){
        Cell2 cell=new Cell2();
        int expected=0;
        int actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty cell.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty trap cell.", expected, actual);
        Igel2 token = new Igel2('B');
        cell.addToken(token);
        expected=1;
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of a trap cell with a player token in it.", expected, actual);
        Cell2 cell2=new Cell2();
        cell2.addToken(token);
        Testing.assertEquals("Check the stack height of a normal cell with a player token in it.", expected, actual);
    }

    private static void testContains(){
        Cell2 cell=new Cell2();
        Player2 player=new Player2('B');
        boolean expected=false;
        boolean actual=cell.contains(player);
        Testing.assertEquals("Check if the empty cell contains one of Player2 B's tokens.", expected, actual);
        Igel2 token=new Igel2('B');
        cell.addToken(token);
        expected=true;
        actual=cell.contains(player);
        Testing.assertEquals("Check if the cell containing one of Player2 B's tokens contains one of Player2 B's tokens.", expected, actual);
    }



    public static void main(String[] args) {
        Testing.startTests();
        Testing.testSection("Cell2 class");
        testConstructor();
        testTrap();
        // ADD MORE TESTS FOR THE Cell2 CLASS HERE
        testIsTrap();
        testCreateTrap();
        testAddToken();
        testPopToken();
        testGetTopToken();
        testIsEmpty();
        testStackHeight();
        testContains();
        Testing.finishTests();
    }
}
