/**
 * Main body of the Igel Aergern game's code.
 * Performs all main operations of the game. 
 * 
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Daniel Casper]
 */

package igelWithGUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IgelAergern3{

    private Board3 board;
    public static int currentPlayer;
    public static JFrame frame;
    private IgelView3 view;
    private ArrayList<Player3> players;
    private Player3 winner;

    private final int MIN_PLAYERS = 3;
    private final int MAX_PLAYERS = 6;
    private final int PLAYER_TOKENS = 4;
    private final int DIE_TYPE = 6;

    private final Random rand = new Random();

    public IgelAergern3() {
        this.board = new Board3();
        this.view = new IgelView3();
        this.players = new ArrayList<>();
        this.winner = null;
    }

    public void play() throws InterruptedException {
        introduction();
        setupPhase();
        mainPhase();
        end();
    }
    
    private void introduction() {
        frame=new JFrame("Choose Players!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPlayers(); // DO NOT MODIFY THIS LINE
        view.refresh(null); // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    // DO NOT MODIFY THIS METHOD
    private void setupPhase() throws InterruptedException {
        view.nextChapter();
        view.refresh(board.toString());
        int currentPlayer = 0;
        for (int tokensPlaced = 0; tokensPlaced < players.size() * PLAYER_TOKENS; tokensPlaced++) {
            setupTurn(currentPlayer);
            view.refresh(board.toString());
            currentPlayer = nextPlayer(currentPlayer);
        }
    }

    private void mainPhase() throws InterruptedException {
        view.nextChapter();  // DO NOT MODIFY THIS LINE
        view.refresh(board.toString());  // DO NOT MODIFY THIS LINE
        // COMPLETE THIS METHOD
        int currentPlayer=0;
        while (winner==null) {
            int die=rand.nextInt(0,DIE_TYPE);
            board.refreshTraps();
            sideStep(currentPlayer, die);
            goForward(currentPlayer, die);
            for (int p=0; p<players.size(); p++){
                if (board.isWinner(players.get(p))){
                    winner=players.get(p);
                }
            }
            view.refresh(board.toString());
            currentPlayer = nextPlayer(currentPlayer);
        }
    }

    private void end() {
        view.nextChapter(); // DO NOT MODIFY THIS LINE
        view.refresh(board.toString(), winner.toString()); // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    private void createPlayers() {
       /** int numPlayers = getPlayerNumber();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(Player.COLORS[i]));
        }*/
        JDialog dialog=new JDialog(frame, "Choose Number of Players!", true);
        JLabel prompt=new JLabel("How many players? (" + MIN_PLAYERS + "-" + MAX_PLAYERS + ")");
        JTextField playerNum=new javax.swing.JTextField();
        JButton confirm=new JButton("Confirm:");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt, playerNum, dialog);
            }
        });
        playerNum.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridLayout layout=new GridLayout(3,3);
        dialog.setLayout(layout);
        dialog.add(new JPanel());
        dialog.add(new JPanel());
        dialog.add(new JPanel());
        dialog.add(prompt);
        dialog.add(playerNum);
        dialog.add(new JPanel());
        dialog.add(new JPanel());
        dialog.add(confirm);
        dialog.add(new JPanel());
        dialog.pack();
        dialog.setVisible(true);
    }

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt, JTextField playerNum, JDialog dialog) {
        int numPlayers = (int)(Double.parseDouble(playerNum.getText()));
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player3(Player3.COLORS[i]));
        }
        dialog.dispose();
    }
    
    private int nextPlayer(int current) {
        return (current + 1) % players.size();
    }

    private void setupTurn(int currentPlayer) throws InterruptedException {
        view.inform("Player " + players.get(currentPlayer).getColor() + ", your turn.");
        int row = getSetupMove(currentPlayer);
        board.placeToken(row, 0, players.get(currentPlayer).getColor());
    }

    private void sideStep(int currentPlayer, int die) throws InterruptedException {
        int optNum=1;
        ArrayList<String> list;
        ArrayList<ArrayList<String>> listLists=new ArrayList<ArrayList<String>>();
        view.inform("Player " + players.get(currentPlayer).toString() + " it is your turn.");
        view.inform2("Die roll: " + (die+1));
        for (int r=0; r<Board3.ROWS; r++){
            for (int c=0; c<Board3.COLUMNS; c++){
                if (board.stackHeight(r, c)!=0 && (board.canMoveSideways(r, c, players.get(currentPlayer)))) {
                    //view.inform(optNum + ") row " + (r+1) + " column " + (c+1));
                    list=new ArrayList<String>();
                        list.add(Integer.toString(optNum));
                        list.add((Integer.toString(r)));
                        list.add((Integer.toString(c)));
                    listLists.add(list);
                    optNum++;
                }
            }
        }
        optNum--;
        //view.inform("0) Pass");
        int m=0;
        while (m<1){
            ArrayList<String> answer=getSideChoice(listLists, Integer.toString(optNum));
            if (answer==null){
                view.inform("Invalid Input!");
            }
            else if (answer.get(0).equals("pass")) {
                view.refresh(board.toString());
                m++;
            }
            else {
                //ArrayList<Integer> chosen=listLists.get((answer-1));
                String row=answer.get(1);
                String col=answer.get(2);
                //int col=0;
                int dir=getDir(Integer.decode(row));
                board.moveSideways(Integer.decode(row), Integer.decode(col), dir);
                view.refresh(board.toString());
                m++;
            }
        }
    }

    private void goForward(int currentPlayer, int die) throws InterruptedException{
        view.inform("Player " + players.get(currentPlayer).toString() + " it is your turn.");
        view.inform2("Die roll: " + (die+1));
        if (board.emptyRow(die)){
            view.inform("No possible moves. Next player\\'s turn!! (Hit enter)");
        }
        else{
            int allowed=0;
            for (int c=0; c<(Board3.COLUMNS-1); c++){
                if (board.isPlayerTop(die, c, players.get(currentPlayer)) && (board.canMoveForward(die, c))) {
                    allowed++;
                }
            }
            if(allowed==0){
                int allowedOther=0;
                for (int c=0; c<(Board3.COLUMNS-1); c++) {
                    if (board.canMoveForward(die, c) && board.stackHeight(die, c)!=0){
                        allowedOther++;
                    }
                }
                int m=0;
                while (m<1){
                    view.inform("No available moves. Move another players piece!");
                    view.refresh(board.toString());
                    int col=getForChoice(die, allowedOther);
                    boolean isOk=isInRange(col, 1, (Board3.COLUMNS-1));
                    if (isOk && (board.canMoveForward(die, (col-1)))){
                            board.moveForward(die, (col-1));
                            m++;
                        }
                    else {
                        view.inform("Invalid Input!");
                    }
                }
            }
            else {
                int m=0;
                while (m<1){
                    view.refresh(board.toString());
                    int col=getForChoice(die, allowed);
                    boolean isOk=isInRange(col, 1, (Board3.COLUMNS-1));
                    if (isOk && board.isPlayerTop(die, (col-1), players.get(currentPlayer)) && (board.canMoveForward(die, (col-1)))){
                        board.moveForward(die, (col-1));
                        m++;
                    }
                    else {
                        view.inform("Invalid Input!");
                    }
                }
            }
        }
        view.refresh(board.toString());
    }

    /**** Input validation ****/
    private int getPlayerNumber() throws InterruptedException {
        // Prepare messages
        String requestMessage = "How many players? (" + MIN_PLAYERS + "-" + MAX_PLAYERS + ")";
        String errorMessage = "Please input a number between " + MIN_PLAYERS + " and " + MAX_PLAYERS + ".";
        // Request input (repeatedly until the player provides a valid answer)
        String numPlayersInput = view.requestInput(requestMessage);
        while (! isDigit(numPlayersInput) || ! isInRange(Integer.decode(numPlayersInput), MIN_PLAYERS, MAX_PLAYERS)) {
            numPlayersInput = view.requestInput(errorMessage);
        }
        return Integer.decode(numPlayersInput);
    }

    private int getSetupMove(int currentPlayer) throws InterruptedException {
        // Prepare messages
        String requestMessage = "In which row do you want to place your token? (1-6)  ";
        ArrayList<Integer> options = collectSetupOptions(currentPlayer);
        //ArrayList<Integer> options=new ArrayList<>();
        //options.add(1);
        //options.add(2);
        //options.add(3);
        //options.add(4);
        //options.add(5);
        //options.add(6);
        String optionsString = options.get(0).toString();
        for (int i = 1; i < options.size(); i++) {
            optionsString += ", " + (options.get(i));
        }
        String errorMessage = "Please input a valid row number: " + optionsString;
        // Request input (repeatedly until the player provides a valid answer)
        String rowInput = view.requestInput(requestMessage);
        while (! isDigit(rowInput) || ! options.contains(Integer.decode(rowInput))) {
            rowInput = view.requestInput(errorMessage);
        }
        return Integer.decode(rowInput) - 1;
    }

    private ArrayList<Integer> collectSetupOptions(int currentPlayer) {
        // Collect rows with lowest stacks -> options
        ArrayList<Integer> options = new ArrayList<>();
        ArrayList<Integer> allRows = new ArrayList<>();
        int minStackHeight = board.stackHeight(0, 0);;
        for (int r = 0; r < Board3.ROWS; r++) {
            int rStackHeight = board.stackHeight(r, 0);
            if (rStackHeight < minStackHeight) {
                options.clear();
                options.add(r+1);
                minStackHeight = rStackHeight;
            }
            else if (rStackHeight == minStackHeight) {
                options.add(r+1);
            }
            allRows.add(r+1);
        }
        // Check whether at least one of the options does not force player to block themselves
        boolean nonBlockingOptionExists = false;
        for (int r : options) {
            if (! board.isPlayerAt(r-1, 0, players.get(currentPlayer))) {
                nonBlockingOptionExists = true;
            }
        }
        if (nonBlockingOptionExists) {
            return options;
        }
        else {
            return allRows;
        }
    }

    private boolean isDigit(String str) {
        return str.matches("\\d");
    }

    private boolean isInRange(int numToTest, int start, int end) {
        return numToTest >= start && numToTest <= end;
    }

    private ArrayList<String> getSideChoice (ArrayList<ArrayList<String>> listLists, String optNum) throws InterruptedException{
        String sideStepChoice=view.requestInput("Go Sideways");
        String row;
        String col;
        if (sideStepChoice.length()==2){
            for (int i=0; i<Integer.decode(optNum); i++){
                ArrayList<String> option=listLists.get(i);
                row=Character.toString(sideStepChoice.charAt(0));
                col=Character.toString(sideStepChoice.charAt(1));
                if (!isDigit(row) && !isDigit(col)){
                    return null;
                }
                else if (row.equals(option.get(1)) && col.equals(option.get(2))) {
                    return option;
                }
                //else if (Integer.decode(sideStepChoice)==0){
                
                
                
                //}
            }
            
        }
        else if (sideStepChoice.equals("pass")){
            ArrayList<String> pass=new ArrayList<String>();
            pass.add("pass");
            return pass;
        }
        else {
            return null;
        }
        return null;
    }

    private int getDir(int row) throws InterruptedException{
        int repeat=0;
        if (row==0){
            return 1;
        }
        else if (row==5){
            return -1;
        }
        else{
            while (repeat<1) {
                view.refresh(board.toString());
                view.inform("Do you want to slide this hedgehog up or down?");
                view.inform2("Choose U or D:");
                String upDown=view.requestInput("Go Sideways");
                String rowNum=Character.toString(upDown.charAt(0));
                int upDownNum=Integer.parseInt(rowNum);
                if (upDownNum==(row-1)){
                    return -1;
                }
                else if (upDownNum==(row+1)){
                    return 1;
                }
                else {
                    view.inform("Invalid Input!");
                }
            }
            return 0;  
        } 
    }

    private int getForChoice(int die, int allowed) throws InterruptedException{
        view.inform("Which hedgehog from Row " + (die+1) + " do you want to move forward?");
        String answer=view.requestInput("Go Forwards");
        if (answer.length()==1){
            if (!isDigit(answer)){
                return (Board3.COLUMNS+1);
            }
            else if (Integer.decode(answer)>Board3.COLUMNS) {
                return (Board3.COLUMNS+1);
            }
            else if (Integer.decode(answer)==0){
                return (Board3.COLUMNS+1);
            }
            else {
                return (Integer.decode(answer));
            }
        }
        else{
            return 0;
        }
    }
}