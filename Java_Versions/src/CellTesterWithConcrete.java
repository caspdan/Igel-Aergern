/**
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

import igelWithConcrete.Cell1;
import igelWithConcrete.Igel1;
import igelWithConcrete.Player1;

public class CellTesterWithConcrete {

    private static void testConstructor() {
        Cell1 cell = new Cell1();
        String expected = "";
        String actual = cell.toString();
        Testing.assertEquals("Constructing a new empty cell.", expected, actual);
    }

    private static void testTrap() {
        Cell1 cell = new Cell1();
        cell.toggleTrap();
        String expected = "@";
        String actual = cell.toString();
        Testing.assertEquals("Making a newly created cell a trap. (toggleTrap)", expected, actual);
    }

    private static void testIsTrap() {
        Cell1 cell = new Cell1();
        boolean expected = false;
        boolean actual = cell.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
        Cell1 cell2 = new Cell1();
        cell2.toggleTrap();
        expected = true;
        actual = cell2.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
    }

    private static void testCreateTrap() {
        Cell1 cell = new Cell1();
        cell.createTrap(0, 3);
        String expected="@";
        String actual=cell.toString();
        Testing.assertEquals("Making a newly created cell a trap.", expected, actual);
    }

    private static void testAddToken() {
        Cell1 cell=new Cell1();
        Igel1 token = new Igel1('B');
        cell.addToken(token);
        String expected="B";
        String actual=cell.toString();
        Testing.assertEquals("Adding a token to an empty cell.", expected, actual);
        Igel1 token2=new Igel1('R');
        cell.addToken(token2);
        expected="BR";
        actual=cell.toString();
        Testing.assertEquals("Adding a token to a non-empty cell.", expected, actual);
    }

    private static void testPopToken(){
        Cell1 cell=new Cell1();
        Igel1 token = new Igel1('B');
        cell.addToken(token);
        cell.popToken();
        String expected="";
        String actual=cell.toString();
        Testing.assertEquals("Removing a token to an empty cell.", expected, actual);
    }

    private static void testGetTopToken(){
        Cell1 cell=new Cell1();
        Igel1 token = new Igel1('B');
        Igel1 token2 = new Igel1('R');
        cell.addToken(token);
        cell.addToken(token2);
        Igel1 expected=token2;
        Igel1 actual=cell.getTopToken();
        Testing.assertEquals("Checking the topmost token in a cell.", expected, actual);
    }

    private static void testIsEmpty(){
        Cell1 cell=new Cell1();
        boolean expected=true;
        boolean actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not an empty cell is empty.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token returns as empty.", expected, actual);
        Igel1 token = new Igel1('B');
        cell.addToken(token);
        expected=true;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token and a player token returns as empty.", expected, actual);
        cell.toggleTrap();
        expected=false;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, deactivated trap cell with a trap token and a player token returns as empty.", expected, actual);
        Cell1 cell2=new Cell1();
        cell2.addToken(token);
        expected=false;
        actual=cell2.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, non-trap cell with a player token returns as empty.", expected, actual);
    }

    private static void testStackHeight(){
        Cell1 cell=new Cell1();
        int expected=0;
        int actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty cell.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty trap cell.", expected, actual);
        Igel1 token = new Igel1('B');
        cell.addToken(token);
        expected=1;
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of a trap cell with a player token in it.", expected, actual);
        Cell1 cell2=new Cell1();
        cell2.addToken(token);
        Testing.assertEquals("Check the stack height of a normal cell with a player token in it.", expected, actual);
    }

    private static void testContains(){
        Cell1 cell=new Cell1();
        Player1 player=new Player1('B');
        boolean expected=false;
        boolean actual=cell.contains(player);
        Testing.assertEquals("Check if the empty cell contains one of Player B's tokens.", expected, actual);
        Igel1 token=new Igel1('B');
        cell.addToken(token);
        expected=true;
        actual=cell.contains(player);
        Testing.assertEquals("Check if the cell containing one of Player B's tokens contains one of Player B's tokens.", expected, actual);
    }



    public static void main(String[] args) {
        Testing.startTests();
        Testing.testSection("Cell1 class");
        testConstructor();
        testTrap();
        // ADD MORE TESTS FOR THE Cell1 CLASS HERE
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
