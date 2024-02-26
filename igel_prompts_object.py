"""Interacts and prompts the player.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""


COLORS = ['Y', 'R', 'G', 'P', 'B', 'O','']
alpha={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}
alphaBig={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}
otherChar={'`','~','!','@','#','$','%','^','&','*',',','(',')','-','_','=','+','{','[',']','}','\\','|','\'','\"', ':',';',',','<','>','.','/','?'}

class Inputs:

    def __init__(self):
        """ Initializes the inputs (self) object which controls all player interactions. 
        
        Parameters
        ---
        self: object
            Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
        """
        self.init=''
    
    def placement_prompt(self, game, main, board, players, view):
        """Prompts the player on placing their tokens in the start up phase

        Args:
            game (object): Object in the Turns class that controls the start up and main phase of the Igel Aergern game
            main (object): Instance of an Igel Aergern game
            board (object): Object containing methods and information relating to manipulation and creation of the board
            players (object): Object containing pertinent information about active players in an instance of the game
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        game.i=game.i+1
        game.answer=view.request_input('Player ' + str(game.p+1) + ' Which row do you want to place your hedgehog in? (1-6) ') 
        answerOK=self.checkOK(game.answer)
        if answerOK==True:
            game.allowed=board.stacks.checkStartStack(game, board)
            if game.allowed==True:
                board.add_tokens(game.answer, game.p, players.act_players)
                main._update_interface()
                game.p=game.p+1
            else:
                view.inform('Not allowed! Fill other slots first.')
                game.i=game.i-1                               #backsteps iteration of loop by one until acceptable player input  
        else:
            view.inform('Invalid Input!')
            game.i=game.i-1

    def move_side_prompt (self, game, board, players, view):
        """Prompts a player at the start of their turn on which, if any, of their tokens they would like to slide sideways.

        Args:
            game (object): Object in the Turns class that controls the start up and main phase of the Igel Aergern game
            board (object): Object containing methods and information relating to manipulation and creation of the board
            main (object): Instance of an Igel Aergern game
            players (object): Object containing pertinent information about active players in an instance of the game
            view (object): Object that refreshes the view of the board as well as prints messages and input requests_
        """
        game.row=0
        game.other_piece=False
        option=1
        side_dict={}     #a dictionary that stores the option number, row number, and column number for each possible sidestep
        view.inform('Which hedgehog do you want to slide sideways?')
        while game.row < len(board.board):
            x=0
            allow=board.stacks.check_top_stack(game, board, players, game.p, game.row)   #returns booleans of which stacks in a row a player has a token on top
            cop_allow=allow.copy()       #copy of the allow list to manipulate and remove elements
            for i in allow:
                if i==True:
                    x=x+1       #informs following while loop how many times to iterate
            if x==0: 
                pass
            else:
                j=1
                while j<=x:
                    col=cop_allow.index(True)
                    view.inform(str(option) + ') row ' + str(game.row+1) + ' column ' + str(col+1))
                    side_dict[option]=[(game.row+1),col+1]
                    cop_allow[col]=False
                    option=option+1
                    j=j+1
            game.row=game.row+1
        view.inform('0) Pass')
        game.ans_side=view.request_input('Enter your choice: ')
        if game.ans_side=='0':
            game.ans_side=None
        else:
            game.ans_side=[game.ans_side, side_dict, option]
        return(game.ans_side)

    def move_prompt(self, game, board, players, view):
        """Determines whether the player has any valid moves with their own token in the active row or if they must move another players token.

        Args:
            game (object): Object in the Turns class that controls the start up and main phase of the Igel Aergern game
            board (object): Object containing methods and information relating to manipulation and creation of the board
            main (object): Instance of an Igel Aergern game
            players (object): Object containing pertinent information about active players in an instance of the game
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        x=0
        for i in game.allow:
            if i:
                x=x+1
        if x==0:
            game.other_piece=True        
            view.inform('No available moves. Move another players piece!')
            game.allow=board.stacks.check_top_stack(game, board, players, game.p, game.row)
            prompt=self.forward_prompt(game, x, board, view)
        else:
            game.other_piece=False
            prompt=self.forward_prompt(game, x, board, view)
        return(prompt)

    def forward_prompt(self, game, x, board, view):
        """Prompts the player on which column they would like to move a token forward from.

        Args:
            game (object): Object in the Turns class that controls the start up and main phase of the Igel Aergern game
            x (int): integer informing the prompt how many movement options they have
            board (object): Object containing methods and information relating to manipulation and creation of the board
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        m=0
        while m<1:
            prompt=view.request_input('Which hedgehog from Row ' + str(game.dice) + ' do you want to move forward? \n'
                                        'Chose a column. One of ' + str(x) + ': ')
            answer_ok=self.checkOkFor(prompt, game, board)
            if answer_ok==True:
                m=m+1
            else:
                view.inform('Invalid Input!')
        return(prompt)
    
    def checkOK(self, answer):
        """Checks validity of an answer. If not 1,2,3,4,5, or 6, the answer is declared invalid.

        Args:
            answer (string): Player input of which row they would like to place their tokens in the start up phase.

        Returns:
            boolean: a boolean that informs the system whether or not the player input an acceptable answer
        """
        if answer in alpha or answer in alphaBig or answer in otherChar:
            answer_ok=False
        elif len(answer)>1:
            answer_ok=False
        elif int(answer)>=1 and int(answer)<=6:
            answer_ok=True
        else:
            answer_ok=False
        return answer_ok

    def checkOkFor(self, prompt, game, board):
        """Checks validity of an answer. If not 1,2,3,4,5,6,7, or 8 the answer is declared invalid.

        Args:
            answer (string): Player input of which row they would like to place their tokens in the start up phase.
            board (object): Object containing methods and information relating to manipulation and creation of the board

        Returns:
            boolean: a boolean that informs the system whether or not the player input an acceptable answer
        """
        board.remove_lines()
        if prompt in alpha or prompt in alphaBig or prompt in otherChar:     
            answer_ok=False
            game.other_piece=False
        elif len(prompt)>1:                     
            answer_ok=False
            game.other_piece=False
        elif int(prompt)>8 or int(prompt)<1:                     
            answer_ok=False
        elif (game.allow[int(prompt)-1])==False or (game.allow[int(prompt)-1])=='Trapped':
            answer_ok=False
        elif (board.board[game.dice-1][int(prompt)-1])=='@':
            answer_ok=False
        else:
            if not game.other_piece:
                answer_ok=True
                game.other_piece=False
        if game.other_piece and (board.board[game.dice-1][int(prompt)-1])!='' and (game.allow[int(prompt)-1])!='Trapped':
            if (board.board[game.dice-1][int(prompt)-1])=='@':
                answer_ok=False
            else:
                answer_ok=True
        board.add_lines()
        return answer_ok

    def checkColor(self, color):
        """Checks validity of an answer when players are choosing their colors. If not Y,R,G,B,O, or P the answer is declared invalid.

        Args:
            color (string): Player input of which color a player would like to represent them on the board. 
        """
        if color in COLORS:         #checks list of existing color choices 
            real_color=True
        else:
            real_color=False         #if player input is not within list of currently available color choices, returns False and requests a different input 
        return real_color
