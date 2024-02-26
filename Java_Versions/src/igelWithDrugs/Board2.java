/**
 * Board Class for the Doping variant of Igel Aergern. In this version
 * there are some extra methods that helps determine which player is the doper.
 * 
 * I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
 */

package igelWithDrugs;

import java.util.ArrayList;

public class Board2 {
    
    private Cell2[][] board;
    public static final int ROWS = 6;
    public static final int COLUMNS = 9;
    public static final int[] TRAPS = {3, 6, 4, 5, 2, 7};

    /**
     * Create an empty Igel Aergern board. The board has 6 rows and 9 columns. 
     * The traps are in colums 3, 6, 4, 5, 2, 7 (one per row from row 1 to row 6).
     */
    public Board2 () {
        this.board=new Cell2[ROWS][COLUMNS];
        for (int r=0; r<ROWS; r++){
            for (int c=0; c<COLUMNS; c++){
                Cell2 cell=new Cell2();
                cell.createTrap(r, c);
                board[r][c]=cell;
            }
        }
    }

    /**
     * Return a string representation of the board that is suitable to be passed 
     * to an IgelView interface.
     * 
     * The tracks/rows are separated from each other using // and cells within one 
     * track are separated using |. Empty cells are represented using an empty string. 
     * Otherwise they are represented as a sequence of the stacked colors. E.g. 'RBO' 
     * represents a stack of red at the bottom, blue, and orange.
     * 
     * Here is an example of a valid board representation:
     * "Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O"
     */
    public String toString() {
        String repr = "";
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                repr += board[r][c].toString();
                if (c < COLUMNS-1) {
                    repr += "|";
                }
            }
            if (r < ROWS-1) {
                repr += "//";
            }
        }
        return repr;
    }

    /**
     * Create a new token of the given color and place it on the board.
     * 
     * @param row row number where to place the token
     * @param col column number where to place the token
     * @param color character indicating the color; has to be from Players.COLORS
     */
    public void placeToken(int row, int col, char color) {
        Cell2 cell=board[row][col];
        Igel2 token=new Igel2(color);
        cell.addToken(token);
        board[row][col]=cell;
    }

    /**
     * Check whether the top most token in the given row and column belongs to the given player.
     *  
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean canMoveSideways(int row, int col, Player2 player) {
        if (board[row][col].trap){
            return false;
        }
        else{
            return (board[row][col].getTopToken().getColor()==player.getColor());
        }
    }

    /**
     * Move the top token in the cell indicated by the row and column numbers sideways.
     * 
     * @param row row number of token to move
     * @param col column number of token to move
     * @param dir a negative number indicates a sideways move to row-1; otherwise move to row+1
     */
    public void moveSideways(int row, int col, int dir) {
        if (dir==-1){
            Igel2 popped=board[row][col].popToken();
            board[(row+dir)][col].addToken(popped);
        }       
        else {
            Igel2 popped=board[row][col].popToken();
            board[(row+dir)][col].addToken(popped);
        }
    }

    /**
     * Check whether the top most token in the cell indicated by the row and column numbers
     * can move forward.
     * 
     * @param row
     * @param col
     * @return true if token can move forward; false otherwise or if cell is empty
     */
    public boolean canMoveForward(int row, int col) {
        if (board[row][col].trap) {
            boolean clear=checkBehind(ROWS, col);
            if (clear) {
                board[row][col].toggleTrap();
            }
        }
        if (board[row][col].isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Move the top token in the cell indicated by the row and column numbers 1 step forward.
     * 
     * Note: This method does no checking whether there is a token in that cell and whether 
     * it is legal to move it forward.
     * 
     * @param row row number of token to move
     * @param col column number of token to move
     */
    public void moveForward(int row, int col) {
        Igel2 player=board[row][col].popToken();
        col++;
        board[row][col].addToken(player);
    }

    /**
     * Check whether the given player has won, i.e. has 3 or more tokens in the last column.
     * 
     * @param player  
     * @return true if player has won; false otherwise
     */
    public boolean isWinner(Player2 player, Player2 doper) {
        if (countColumn(player, doper)==3){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Check whether the given player has a token in the cell indicated by row and col.
     * 
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean isPlayerAt(int row, int col, Player2 player) {
        return board[row][col].contains(player);
    }

    /**
     * Check whether the top most token in the cell indicated by row and col belongs 
     * to the given player.
     * 
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean isPlayerTop(int row, int col, Player2 player) {
        if (board[row][col].isEmpty()){
            return false;
        }
        else{
            return board[row][col].getTopToken().toString().equals(player.toString());
        }
    }

    /**
     * Return the number of tokens in the stack in the cell indicated by row and col.
     * @param row
     * @param col
     * @return
     */
    public int stackHeight(int row, int col) {
        return board[row][col].stackHeight();
    }

    /**
     * Return whether or not every cell in every row that is behind the indicated cell is empty. 
     * For use in determining when to toggle traps off.
     * @param rows
     * @param cols
     * @return
     */
    private boolean checkBehind(int rows, int cols) {
        for (int r=0; r<rows; r++){
            for (int c=0; c<cols; c++){
                if (!board[r][c].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check whether or not the indicated row is completely empty.
     * Ignores final column and active trap cells with tokens.
     * @param row
     * @return
     */
    public boolean emptyRow(int row){
        for (int c=0; c<(COLUMNS-1); c++) {
            if (!board[row][c].isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Counts how many of the indicated players' tokens are in the final row.
     * Used to determine if a player has won.
     * @param player
     * @return
     */
    private int countColumn(Player2 player, Player2 doper){
        int counter=0;
        for (int r=0; r<ROWS; r++){
            for(int i = 0; i<board[r][(COLUMNS-1)].toString().length(); i++) {
                char a = board[r][(COLUMNS-1)].toString().charAt(i);                
                if (player.getColor()==a){
                    counter++;
                }
            }
        }
        if (doper!=null && player.toString().equals(doper.toString())){
            counter--;
        }
        return counter;
    }

    /**
     * Toggles the trap in the indicated cell.
     * @param row
     * @param col
     */
    private void toggleTrap(int row, int col){
        board[row][col].toggleTrap();
    }

    /**
     * Checks whether or not the indicated cell is a trap.
     * @param row
     * @param col
     * @return
     */
    private boolean isTrap(int row, int col){
        return board[row][col].isTrap();
    }

    /**
     * Sweeps the entire board to determine which if any traps are able to be toggled off, 
     * and does so if yes
     * @param row
     * @param col
     * @return
     */
    public void refreshTraps(){
        for (int r=0; r<ROWS; r++){
            if (checkBehind(ROWS, TRAPS[r]) && isTrap(r, TRAPS[r])) {
                toggleTrap(r, TRAPS[r]);
            }
        }
    }

    /**
     * Counts the total number of tokens in column 9 regardless of color.
     * 
     * @return the calculated total
     */
    public int goalTotal(){
        int counter=0;
        for (int r=0; r<ROWS; r++){
            for(int i = 0; i<board[r][(COLUMNS-1)].toString().length(); i++) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * 
     * @return an Igel object with the color of the doper's tokens
     */
    public Igel2 getDoper(){
        Igel2 doper=null;
        for (int r=0; r<ROWS; r++){
            doper=board[r][COLUMNS-1].getTopToken();
            if (doper!=null){
                return doper;
            }
        }
        return doper;
    }

    /**
     * Checks whether or not the doper is the winner to determine if the dialogue stream should be initiated
     * 
     * @param player the player the game is checking
     * @param doper another player object that represents the color of the doper for the method to compare the player's color against
     * @return a boolean of whether or not the player's color matches the doper's color
     */
    public boolean isDopeingWinner(Player2 player, Player2 doper){
        if (doper!=null){
            if (player.toString().equals(doper.toString()) && countColumn(player, doper)==2){
                return true;
            }
            else{
                return false;
            } 
        }
        else{
            return false;
        }   
    }
}
