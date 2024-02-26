/**
 * Main body of the Igel Aergern game's code.  
 * This version implements the doping variation of 
 * the game and offers a potential dialogue cutscene.
 * 
 * Changes from base game code can be found on lines 68-69, 71-74, and 83-114
 * 
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Daniel Casper]
 */

package igelWithDrugs;

import java.util.ArrayList;
import java.util.Random;

public class IgelAergern2 {

    private Board2 board;
    private IgelView2 view;
    private ArrayList<Player2> players;
    private Player2 winner;

    private final int MIN_PLAYERS = 3;
    private final int MAX_PLAYERS = 6;
    private final int PLAYER_TOKENS = 4;
    private final int DIE_TYPE = 6;

    private final Random rand = new Random();

    public IgelAergern2() {
        this.board = new Board2();
        this.view = new IgelView2();
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
        view.refresh(null); // DO NOT MODIFY THIS LINE
        createPlayers(); // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    // DO NOT MODIFY THIS METHOD
    private void setupPhase() {
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
        Player2 doper=null;
        int triggered=0;
        while (winner==null) {
            if (board.goalTotal()==1){
                Igel2 doping=board.getDoper();
                doper=new Player2(doping.getColor());
            }
            int die=rand.nextInt(0,DIE_TYPE);
            board.refreshTraps();
            sideStep(currentPlayer, die);
            goForward(currentPlayer, die);
            for (int p=0; p<players.size(); p++){
                if (board.isWinner(players.get(p), doper)){
                    winner=players.get(p);
                }
                else if (board.isDopeingWinner(players.get(p), doper) && triggered<1){
                    view.inform("The end.");
                    view.inform("Congratulations! Player " + doper.toString() + " has...");
                    for (int i=0; i<3; i++){
                        view.inform("...");
                        Thread.sleep(2500); // 10000ms = 10s
                    }
                    view.inform("Ladies and Gentlemen. Please stand by. The judges appear to debating the validity of this win.");
                    Thread.sleep(2500);
                    view.inform("Word from the field is...");
                    Thread.sleep(2500);
                    view.inform("Oh dear!!!! One of the players on the winning team is suspected of doping!!!");
                    Thread.sleep(2500);
                    view.inform("I, for one, certainly did not see this coming!");
                    Thread.sleep(2500);
                    view.inform("The team coaches are denying that they had any knowledge of this players actions.");
                    Thread.sleep(2500);
                    view.inform("This has never happened before in the sport. There is no precedent or procedure.");
                    Thread.sleep(2500);
                    view.inform("We are all eagerly awaiting the final decision. Will Player " + doper.toString() + "'s entire team be disqualified?");
                    Thread.sleep(2500);
                    view.inform("Or will just the doper be disqualified???");
                    for (int i=0; i<3; i++){
                        view.inform("...");
                        Thread.sleep(2500);
                    }
                    view.inform("Well folks... The judges have made their call.");
                    Thread.sleep(1000);
                    view.inform("Player " + doper.toString() + " may continue play but must now get the fourth member of their team to the goal in order to win.");
                    Thread.sleep(2500);
                    triggered++;
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
        int numPlayers = getPlayerNumber();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player2(Player2.COLORS[i]));
        }
    }

    private int nextPlayer(int current) {
        return (current + 1) % players.size();
    }

    private void setupTurn(int currentPlayer) {
        view.inform("Player " + players.get(currentPlayer).getColor() + ", your turn.");
        int row = getSetupMove(currentPlayer);
        board.placeToken(row, 0, players.get(currentPlayer).getColor());
    }

    private void sideStep(int currentPlayer, int die) {
        int optNum=1;
        ArrayList<Integer> list;
        ArrayList<ArrayList<Integer>> listLists=new ArrayList<ArrayList<Integer>>();
        view.inform("Player " + players.get(currentPlayer).toString() + " it is your turn.");
        view.inform("Die roll: " + (die+1));
        for (int r=0; r<Board2.ROWS; r++){
            for (int c=0; c<Board2.COLUMNS; c++){
                if (board.stackHeight(r, c)!=0 && (board.canMoveSideways(r, c, players.get(currentPlayer)))) {
                    view.inform(optNum + ") row " + (r+1) + " column " + (c+1));
                    list=new ArrayList<Integer>();
                        list.add(optNum);
                        list.add((r));
                        list.add((c));
                    listLists.add(list);
                    optNum++;
                }
            }
        }
        optNum--;
        view.inform("0) Pass");
        int m=0;
        while (m<1){
            int answer=getSideChoice(listLists, optNum);
            if (answer>optNum){
                view.inform("Invalid Input!");
            }
            else if (answer==0) {
                view.refresh(board.toString());
                m++;
            }
            else {
                ArrayList<Integer> chosen=listLists.get((answer-1));
                int row=chosen.get(1);
                int col=chosen.get(2);
                int dir=getDir(row);
                board.moveSideways(row, col, dir);
                view.refresh(board.toString());
                m++;
            }
        }
    }

    private void goForward(int currentPlayer, int die){
        view.inform("Player " + players.get(currentPlayer).toString() + " it is your turn.");
        view.inform("Die roll: " + (die+1));
        if (board.emptyRow(die)){
            view.inform("No possible moves. Next player\\'s turn!! (Hit enter)");
        }
        else{
            int allowed=0;
            for (int c=0; c<(Board2.COLUMNS-1); c++){
                if (board.isPlayerTop(die, c, players.get(currentPlayer)) && (board.canMoveForward(die, c))) {
                    allowed++;
                }
            }
            if(allowed==0){
                int allowedOther=0;
                for (int c=0; c<(Board2.COLUMNS-1); c++) {
                    if (board.canMoveForward(die, c) && board.stackHeight(die, c)!=0){
                        allowedOther++;
                    }
                }
                int m=0;
                while (m<1){
                    view.inform("No available moves. Move another players piece!");
                    int col=getForChoice(die, allowedOther);
                    boolean isOk=isInRange(col, 1, (Board2.COLUMNS-1));
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
                    int col=getForChoice(die, allowed);
                    boolean isOk=isInRange(col, 1, (Board2.COLUMNS-1));
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

    private int getPlayerNumber() {
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

    private int getSetupMove(int currentPlayer) {
        // Prepare messages
        String requestMessage = "In which row do you want to place your token? (1-6)  ";
        ArrayList<Integer> options = collectSetupOptions(currentPlayer);
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
        for (int r = 0; r < Board2.ROWS; r++) {
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

    private int getSideChoice (ArrayList<ArrayList<Integer>> listLists, int optNum){
        String sideStepChoice=view.requestInput("Enter your choice: ");
        if (sideStepChoice.length()==1){
            if (!isDigit(sideStepChoice)){
                return optNum+1;
            }
            else if (Integer.decode(sideStepChoice)>optNum) {
                return optNum+1;
            }
            else if (Integer.decode(sideStepChoice)==0){
                return 0;
            }
            else {
                return (Integer.decode(sideStepChoice));
            }
        }
        else {
            return optNum+1;
        }
    }

    private int getDir(int row){
        int repeat=0;
        if (row==0){
            return 1;
        }
        else if (row==5){
            return -1;
        }
        else{
            while (repeat<1) {
                view.inform("Do you want to slide this hedgehog up or down?");
                view.inform("Choose U or D:");
                String upDown=view.requestInput("Enter your choice: ");
                if (upDown.equals("U") || upDown.equals("u")){
                    return -1;
                }
                else if (upDown.equals("D") || upDown.equals("d")){
                    return 1;
                }
                else {
                    view.inform("Invalid Input!");
                }
            }
            return 0;  
        } 
    }

    private int getForChoice(int die, int allowed){
        view.inform("Which hedgehog from Row " + (die+1) + " do you want to move forward?");
        String answer=view.requestInput("Chose a column. One of " + allowed + ": ");
        if (answer.length()==1){
            if (!isDigit(answer)){
                return (Board2.COLUMNS+1);
            }
            else if (Integer.decode(answer)>Board2.COLUMNS) {
                return (Board2.COLUMNS+1);
            }
            else if (Integer.decode(answer)==0){
                return (Board2.COLUMNS+1);
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