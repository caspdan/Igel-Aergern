"""Functions for executing main functionalities of a singular players turn in an Igel Aergern game.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""

import random

class Turns:

    def __init__(self, main, board, players, inputs, view):
        """ Initializes the game (self) object and begins the start up phase. 
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        board: object
            Object containing methods and information relating to manipulation and creation of the board 
        players: object 
            Object containing pertinent information about active players in an instance of the game
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.i=0
        while self.i<(4*len(players.act_players)):            #iterates function 4 times, one for each token plaement
            self.i=self.take_turns(main, board, players, inputs, view)
        
    def take_turns(self, main, board, players, inputs, view):
        """Iterates through each players turn 4 times, once for each of their player tokens.
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        board: object
            Object containing methods and information relating to manipulation and creation of the board 
        players: object 
            Object containing pertinent information about active players in an instance of the game
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.p=0
        while self.p < (len(players.act_players)):
            inputs.placement_prompt(self, main, board, players, view)                                  #backsteps iteration of loop by one until acceptable player input
        return self.i

    def begin(self, main, players, board, inputs, view):
        '''Begins the main phase of the game.
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        players: object 
            Object containing pertinent information about active players in an instance of the game 
        board: object
            Object containing methods and information relating to manipulation and creation of the board 
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        '''
        self.winner=None
        self.row=None
        while '' in players.act_players:
            players.act_players.remove('')
        while self.winner==None:
            self.play_game(main, players, board, inputs, view)
            if self.winner!=None:
                winInd=players.act_players.index(self.winner)
        view.inform('The winner is Player ' + str(winInd+1) + '! Congrats!!!')

    def play_game(self, main, players, board, inputs, view):
        """"Loops main gameplay functions and checks for a winner after each turn. Loop ends when a winner is detected.
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        players: object 
            Object containing pertinent information about active players in an instance of the game
        board: object
            Object containing methods and information relating to manipulation and creation of the board
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.p=0
        while self.p < (len(players.act_players)):
            self.winner=board.get_winner()
            if self.winner!=None:
                self.p=100
            else:
                input('Roll the Dice Player ' + (str(self.p+1)) + ' (' + players.act_players[self.p] + '). (Hit enter)')
                self.dice=random.randint(1,6)
                view.inform('Die roll: ' + str(self.dice))
                self.play_moves(main, players, board, inputs, view)
                
    def play_moves(self, main, players, board, inputs, view):
        """Exectues the while in playGame for each player in a game.
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        players: object 
            Object containing pertinent information about active players in an instance of the game
        board: object
            Object containing methods and information relating to manipulation and creation of the board
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests    
        """
        self.k=0
        while self.k<1:
            self.ans_side=inputs.move_side_prompt(self, board, players, view)
            if self.ans_side==None:
                ans_ok=True
            else:
                ans_ok=inputs.checkOK(self.ans_side[0])
                if ans_ok and int(self.ans_side[0])<=self.ans_side[2]:
                    ans_ok=True
                else:
                    ans_ok=False
            if not ans_ok:
                view.inform('Invalid Input!')
            elif self.ans_side==None:
                self.continue_game(main, players, board, inputs, view)
            elif int(self.ans_side[0])>=int(self.ans_side[2]):
                view.inform('Invalid Input!')
            else:
                side_dict=self.ans_side[1]
                self.ans_side=self.ans_side[0]
                side_move=side_dict.get(int(self.ans_side))
                row_ind=(side_move[0]-1)
                cor_col=side_move[1]-1
                board.remove_lines()
                self.x=1
                if row_ind==0:
                    board.move_sideways(row_ind, cor_col, -1)
                elif row_ind>=1 and row_ind<=4:
                    while self.x<=1:
                        dir=view.request_input('Do you want to slide this hedgehog up or down? \n'
                                                'Choose U or D: ' )
                        if dir=='U':
                            dir=1
                            self.x=5
                        elif dir=='D':
                            dir=-1
                            self.x=5
                        else:
                            view.inform('Invalid Input!')
                    board.move_sideways(row_ind, cor_col, dir)
                elif row_ind==5:
                    board.move_sideways(row_ind, cor_col, 1)
                main._update_interface()
                self.continue_game(main, players, board, inputs, view)

    def continue_game(self, main, players, board, inputs, view):
        """Continues a players turn after the sidestep phase has been completed. 
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        players: object 
            Object containing pertinent information about active players in an instance of the game
        board: object
            Object containing methods and information relating to manipulation and creation of the board
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.row=self.dice-1
        self.allow=board.stacks.check_top_stack(self, board, players, self.p, self.row)
        self.go_for(main, players, board, inputs, view)
        self.p=self.p+1
        self.k=5

    def go_for(self, main, players, board, inputs, view):
        """Executes the final phase of a players turn, allowing them to move their chosen piece forward. 
        
        Parameters
        ---
        self: object
            Object in the Turns class that controls the start up and main phase of the Igel Aergern game
        main: object
            Instance of an Igel Aergern game
        players: object 
            Object containing pertinent information about active players in an instance of the game
        board: object
            Object containing methods and information relating to manipulation and creation of the board
        inputs: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        view: object
            Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.empty_row=board.check_empty_row(self.dice, players.act_players)
        if self.empty_row:
            input('No possible moves. Next player\'s turn!! (Hit enter)')
            main._update_interface()
        else:
            view.inform('Player ' + players.act_players[self.p] + '\'s turn  \n'
                        'Die roll: ' + str(self.dice) + '\n'
                        '')
            self.ans_for=inputs.move_prompt(self, board, players, view)
            act_row=board.board[self.dice-1]
            board.remove_lines()
            board.move_forward(act_row, self.ans_for)
            board.add_lines()
            main._update_interface()