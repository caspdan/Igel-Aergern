/**
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

import igel.Cell;
import igel.Igel;
import igel.Player;

public class CellTester {

    private static void testConstructor() {
        Cell cell = new Cell();
        String expected = "";
        String actual = cell.toString();
        Testing.assertEquals("Constructing a new empty cell.", expected, actual);
    }

    private static void testTrap() {
        Cell cell = new Cell();
        cell.toggleTrap();
        String expected = "@";
        String actual = cell.toString();
        Testing.assertEquals("Making a newly created cell a trap. (toggleTrap)", expected, actual);
    }

    private static void testIsTrap() {
        Cell cell = new Cell();
        boolean expected = false;
        boolean actual = cell.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
        Cell cell2 = new Cell();
        cell2.toggleTrap();
        expected = true;
        actual = cell2.isTrap();
        Testing.assertEquals("Checking if cell is a trap", expected, actual);
    }

    private static void testCreateTrap() {
        Cell cell = new Cell();
        cell.createTrap(0, 3);
        String expected="@";
        String actual=cell.toString();
        Testing.assertEquals("Making a newly created cell a trap.", expected, actual);
    }

    private static void testAddToken() {
        Cell cell=new Cell();
        Igel token = new Igel('B');
        cell.addToken(token);
        String expected="B";
        String actual=cell.toString();
        Testing.assertEquals("Adding a token to an empty cell.", expected, actual);
        Igel token2=new Igel('R');
        cell.addToken(token2);
        expected="BR";
        actual=cell.toString();
        Testing.assertEquals("Adding a token to a non-empty cell.", expected, actual);
    }

    private static void testPopToken(){
        Cell cell=new Cell();
        Igel token = new Igel('B');
        cell.addToken(token);
        cell.popToken();
        String expected="";
        String actual=cell.toString();
        Testing.assertEquals("Removing a token to an empty cell.", expected, actual);
    }

    private static void testGetTopToken(){
        Cell cell=new Cell();
        Igel token = new Igel('B');
        Igel token2 = new Igel('R');
        cell.addToken(token);
        cell.addToken(token2);
        Igel expected=token2;
        Igel actual=cell.getTopToken();
        Testing.assertEquals("Checking the topmost token in a cell.", expected, actual);
    }

    private static void testIsEmpty(){
        Cell cell=new Cell();
        boolean expected=true;
        boolean actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not an empty cell is empty.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token returns as empty.", expected, actual);
        Igel token = new Igel('B');
        cell.addToken(token);
        expected=true;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a trap cell with a trap token and a player token returns as empty.", expected, actual);
        cell.toggleTrap();
        expected=false;
        actual=cell.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, deactivated trap cell with a trap token and a player token returns as empty.", expected, actual);
        Cell cell2=new Cell();
        cell2.addToken(token);
        expected=false;
        actual=cell2.isEmpty();
        Testing.assertEquals("Check whether or not a non-empty, non-trap cell with a player token returns as empty.", expected, actual);
    }

    private static void testStackHeight(){
        Cell cell=new Cell();
        int expected=0;
        int actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty cell.", expected, actual);
        cell.createTrap(0, 3);
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of an empty trap cell.", expected, actual);
        Igel token = new Igel('B');
        cell.addToken(token);
        expected=1;
        actual=cell.stackHeight();
        Testing.assertEquals("Check the stack height of a trap cell with a player token in it.", expected, actual);
        Cell cell2=new Cell();
        cell2.addToken(token);
        Testing.assertEquals("Check the stack height of a normal cell with a player token in it.", expected, actual);
    }

    private static void testContains(){
        Cell cell=new Cell();
        Player player=new Player('B');
        boolean expected=false;
        boolean actual=cell.contains(player);
        Testing.assertEquals("Check if the empty cell contains one of Player B's tokens.", expected, actual);
        Igel token=new Igel('B');
        cell.addToken(token);
        expected=true;
        actual=cell.contains(player);
        Testing.assertEquals("Check if the cell containing one of Player B's tokens contains one of Player B's tokens.", expected, actual);
    }



    public static void main(String[] args) {
        Testing.startTests();
        Testing.testSection("Cell class");
        testConstructor();
        testTrap();
        // ADD MORE TESTS FOR THE Cell CLASS HERE
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
