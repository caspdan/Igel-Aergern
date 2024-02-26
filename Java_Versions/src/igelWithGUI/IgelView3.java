package igelWithGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IgelView3 {

    private String[] chapters = {"Intro", "setup", "main", "end"};
    private int chapter;
    private final int WIDTH = 60;
    private final int CELL_WIDTH = 5;
    private JFrame frame;
    private JPanel banner;
    private JLabel bannerLabel1;
    private JLabel bannerLabel2;
    private JPanel labelPanel;
    private JPanel boardPanel;
    private Component box;

    public IgelView3() {
        this.chapter = 0; // "Intro";
        frame=new JFrame("Igel Aergern");
    }

    /**
     * Prints a representation of the Igel Aergern board to the terminal.
     * 
     * @param board a representation of the board as a string. The tracks/rows
     *      are separated from each other using // and cells within one track are
     *      separated using |. Empty cells are represented using an empty string.
     *      Otherwise they are represented as a sequence of the stacked colors. E.g.
     *      'RBO' represents a stack of red at the bottom, blue, and orange. 
     *      Here is an example of a valid board representation:
     *      'Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O'
     */
    public void refresh(String board) {
        refresh(board, null);
    }

    /**
     * Prints a representation of the Igel Aergern board to the terminal.
     * 
     * @param board a representation of the board as a string. The tracks/rows
     *      are separated from each other using // and cells within one track are
     *      separated using |. Empty cells are represented using an empty string.
     *      Otherwise they are represented as a sequence of the stacked colors. E.g.
     *      'RBO' represents a stack of red at the bottom, blue, and orange. 
     *      Here is an example of a valid board representation:
     *      'Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O'
     * @param winner the name of the winner, if there is one; null otherwise.
     */
    public void refresh(String board, String winner) {
        switch (chapter) {
            case 0:
                showIntroduction();
                break;
            case 1:
                showSetupPhase(board);
                break;
            case 2:
                showMainPhase(board);
                break;
            default:
                showEnd(board, winner);
        }
    }

    /**
     * Advances the interface to the next "chapter" of the game. There are 
     * four chapters: introduction, setup phase, main phase, end.
     */
    public void nextChapter() {
        if (chapter < chapters.length-1) {
            chapter++;
        }
    }

    /**
     * Requests an answer from the player and returns whatever string the player enters.
     * 
     * @param message the message to display to request input from the player.
     * @return the player's input
     * @throws InterruptedException
     */
    public String requestInput(String message) throws InterruptedException {
        String number=null;
        String pass=null;
        number=null;
        pass=null;
            //number=getCoor.rowCoor;
            while (number==null && pass==null){
                number=getCoor3.coor;
                pass=passAction3.pass;
                Thread.sleep(100);
            }
        if (chapter==1){
            while (number==null){
                number=getCoor3.coor;
                Thread.sleep(100);

            }
            number=Character.toString(getCoor3.coor.charAt(0)+1);
            //number=null;
            ////number=getCoor.rowCoor;
            //while (number==null){
            //    number=getCoor2.coor;
            //    Thread.sleep(100);
            //}
            ////System.out.println(number);
        }
        else{
            if (message.equals("Go Sideways") && pass!=null){
                return pass;
                //number=getCoor2.coor;
                //number=Character.toString(getCoor2.coor.charAt(0)+1);
            }
            else if (message.equals("Go Forwards")){
                while (number==null){
                    number=getCoor3.coor;
                    Thread.sleep(100);
                }
                number=Character.toString(number.charAt(1)+1);
            }
        }
        return number;
    }

    /**
     * Prints the given message to the terminal.
     * 
     * @param message
     */
    public void inform(String message) {
            bannerLabel1.setText(message);
    }

    public void inform2(String message1) {
            bannerLabel2.setText(message1);
    }

    /** The displays for the four game "chapters". **/
    
    private void showIntroduction() {
        clear();
        printHeader();
        JFrame frame=new JFrame("Choose Players!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.println("Welcome. Let's play!");
    }

    private void showSetupPhase(String board) {
        clear();
        printHeader();
        printBoard(board);
    }
    
    private void showMainPhase(String board) {
        clear();
        printHeader();
        printBoard(board);
    }

    private void showEnd(String board, String winner) {
        clear();
        printHeader();
        printCentered("The end.", WIDTH);
        if (winner != null) {
            printCentered("Congratulations! Player " + winner + " has won.", WIDTH);
        }
        else {
            printCentered("Nobody has won.", WIDTH);
        }
        System.out.println();
    }

    private void printHeader() {
        printCentered("/~~~~~~~~~~~~~~\\", WIDTH);
        printCentered("| IGEL AERGERN |", WIDTH);
        printCentered("\\~~~~~~~~~~~~~~/", WIDTH);
        System.out.println();
    }

    /** Displaying the board **/

    private void printBoard(String board) {
        //Dimension dimension=new Dimension(503, 343);

        if (banner!=null){
            frame.remove(banner);
            frame.remove(labelPanel);
            frame.remove(boardPanel);
            frame.remove(box);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        banner=new JPanel();
        labelPanel=new JPanel();
        boardPanel=new JPanel();
        bannerLabel1=new JLabel();
        bannerLabel2=new JLabel();
        JButton passButton=new JButton("No Sidestep");
        banner.add(bannerLabel1);
        banner.add(bannerLabel2);
        banner.add(passButton);
        passButton.addActionListener(new passAction3());
        frame.add(banner);
        frame.add(labelPanel);
        frame.add(boardPanel);
        box=Box.createVerticalStrut(40);
        frame.add(box);

        String[] rows = board.split("//");
        int numOfRows=rows.length;
        int numOfCols = rows[0].split("\\|", -1).length;
            JPanel[][] startGoalLabel = new JPanel[1][numOfCols+1];
            GridLayout labelLayout=new GridLayout(1,numOfCols+1);
        startGoalLabels(numOfCols, labelPanel, startGoalLabel, labelLayout);
            JPanel[][] cells = new JPanel[numOfRows][numOfCols+1];
            GridLayout layout=new GridLayout(numOfRows,numOfCols+1);
        boardPanel(boardPanel, layout, numOfCols, numOfRows, cells, rows);
        frame.pack();
        frame.setVisible(true);
    }

    private void startGoalLabels(int numOfCols,JPanel labelPanel, JPanel[][] startGoalLabel, GridLayout labelLayout) {
        labelPanel.setLayout(labelLayout);
        for (int c=0; c<numOfCols+1; c++){
            if (c==0){
                startGoalLabel[0][c] = new JPanel();
                startGoalLabel[0][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel label=new JLabel("Start");
                startGoalLabel[0][c].add(label);
                labelPanel.add(startGoalLabel[0][c]);
            }
            else if (c==numOfCols){
                startGoalLabel[0][c] = new JPanel();
                startGoalLabel[0][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel label=new JLabel("Goal");
                startGoalLabel[0][c].add(label);
                labelPanel.add(startGoalLabel[0][c]);
            }
            else{
                labelPanel.add(new JPanel());
            }
        }
    }

    private void boardPanel(JPanel boardPanel, GridLayout layout, int numOfCols, int numOfRows, JPanel[][] cells, String[] rows){
        boardPanel.setLayout(layout);
        for (int r = 0; r < rows.length; r++) {
            for (int c=0; c<numOfCols; c++){
                cells[r][c] = new JPanel();
                cells[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                String[] cellArray=rows[r].split("\\|", numOfCols);
                JButton button=new JButton(formatRow(c, cellArray[c]));
                button.setName(Integer.toString(r)+Integer.toString(c));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                //if (chapter==1){
                //    button.addActionListener(new getCoor());
                //}
                //else if (chapter==2){
                //    button.addActionListener(new getCoor2());
                //}
                button.addActionListener(new getCoor3());
                cells[r][c].add(button);
                boardPanel.add(cells[r][c]);
            }
        }
    }

    private String formatRow(int i, String row) {
        //String rowString = String.valueOf(i+1) + " |";
        String rowString="";
        String[] cols = row.split("\\|", -1);
        for (String cell : cols) {
            rowString += cell;
            rowString += " ";
            //center(cell, CELL_WIDTH);
        //    rowString += "|";
        }
        return rowString;
        //center(rowString, WIDTH);
    }

    private String columnLabels(int numOfCols) {
        String columnLabelString = "     " + " ".repeat(CELL_WIDTH/2);
        for (int i = 1; i < numOfCols + 1; i++) {
            columnLabelString += i + " ".repeat(CELL_WIDTH);
        }
        columnLabelString += " ".repeat(CELL_WIDTH/2);
        return columnLabelString;
    }

    /** Helper methods **/

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
    }

    private void printCentered(String toPrint, int width) {
        System.out.println(center(toPrint, width));
    }

    private String center(String toCenter, int width) {
        int printWidth = (width - toCenter.length()) / 2 + toCenter.length();
        if (printWidth > 0) {
            String formatTemplate = "%" + printWidth + "s";
            toCenter = String.format(formatTemplate, toCenter);
        }
        return toCenter;
    }

    private String leftAlign(String toAlign, int width) {
        if (toAlign.length() < width) {
            String formatTemplate = "%-" + width + "s";
            toAlign = String.format(formatTemplate, toAlign);
        }
        return toAlign;
    }
}
