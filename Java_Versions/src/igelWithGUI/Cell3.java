/**
 * Cell class for the base version of Igel Aergern. 
 * Represents and manipulates an individual cell of the board.
 * 
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Daniel Casper]
 */

package igelWithGUI;

import java.util.ArrayList;

public class Cell3 {

    public ArrayList<String> spot;
    private ArrayList<Igel3> contents;
    public boolean trap;


    /**
     * Create a single cell of an Igel Aergern board.
     */
    public Cell3() {
        this.contents=new ArrayList<Igel3>();
    }

    /**
     * Toggle whether or not this cell is a trap. If the cell is NOT a trap, 
     * call this method turns it into one. If the cell IS a trap, calling this
     * method turns it into a regular cell. 
     */
    public void toggleTrap() {
        if (trap) {
            trap=false;
        }
        else if (!trap) {
            trap=true;
        }
    }

    /**
     * Checks whether the cell is a trap.
     * 
     * @return
     */
    public boolean isTrap() {
        return trap;
    }

    /**
     * Adds the trap token to each of the six trap cells and toggles the trap boolean to true.
     * @param r
     * @param c
     */
    public void createTrap(int r, int c) {
        for (int t=0; t<Board3.TRAPS.length; t++) {
            if ((t==r) && (Board3.TRAPS[t]==c)) {
                Igel3 trapTok=new Igel3('@');
                this.contents.add(trapTok);
                toggleTrap();
            }
        }
    }

    /**
     * Return a string representation of the cell that is suitable for the IgelView
     * interface. E.g. 'RBO' represents a stack of red at the bottom, blue, and orange.
     * '@BY' represents a cell that is a trap with a stack of blue at the bottom and
     * yellow on top.
     */
    public String toString() {
        String repr;
        if (contents.size()==0) {
            repr="";
        }
        else {
            repr="";
            for (int i = 0; i < contents.size(); i++) {
                Igel3 piece=contents.get(i);
                repr+=piece.toString();     
            }
         }
        return repr;
    }

    /**
     * Add a token to the cell.
     * 
     * @param token token to add
     */
    public void addToken(Igel3 token) {
        contents.add(token);
    }

    /**
     * Remove the top most token from this cell's stack and return it.
     * 
     * @return the token that was removed; null if the cell is empty
     */
    public Igel3 popToken() {
        if (isEmpty()) {
            return null;
        }
        else {
            Igel3 popped=contents.get(contents.size()-1);
            contents.remove(contents.size()-1);
            return popped;
        }
    }

    /**
     * Return the top most token from this cell's stack (without removing it
     * from the stack.)
     * 
     * @return the top most token of the cell's stack
     */
    public Igel3 getTopToken() {
        if (contents.size()==0) {
            return null;
        }
        if (contents.size()==1 && contents.get(0).toString()=="@") {
            return null;
        }
        else {
           return contents.get(contents.size()-1);
        }
    }

    /**
     * Check whether there are any hedgehogs in this cell.
     * 
     * @return true if there are no hedgehogs in this cell; false otherwise.
     */
    public boolean isEmpty() {
        if (this.contents.size()==0) {
            return true;
        } 
        else if (contents.size()>=1 && contents.get(0).toString().equals("@") && trap) {
            return true;
        }
        else if (contents.size()==1 && contents.get(0).toString().equals("@")){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Return the height of the Igel stack in this cell.
     * 
     * @return
     */
    public int stackHeight() {
        if (contents.size()==0) {
            return 0;
        }
        else if (contents.get(0).toString().equals("@")) {
            return (contents.size()-1);
        }
        else {
            return contents.size();
        }
    }

    /**
     * Count how many tokens of the given player are in this cell.
     * 
     * @param player
     * @return
     */
    public int countTokens(Player3 player) {
        int count=0;
        for (int i=0; i<contents.size(); i++) {
            Igel3 piece=contents.get(i);
            if (piece.toString()==player.toString()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check whether there is a token that belongs to the given player is in this cell.
     * 
     * @param player
     * @return
     */
    public boolean contains(Player3 player) {
        for (int i=0; i<contents.size(); i++) {
            Igel3 piece=contents.get(i);
            if (piece.toString().equals(player.toString())) {
                return true;
            }
        }
        return false;
    }
    
}