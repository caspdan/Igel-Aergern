"""Functions controlling the board of the Igel Aergern game.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""


from igel_stacks_object import Stacks

ROWS = 6
COLS = 9
TRAPS = [3, 6, 4, 5, 2, 7]

class Board:

    def __init__(self):
        """Initialize a Board object representing an empty board.
        """
        self.board=self.create_board()
        
    def create_board(self):
        """Creates an empty board list with traps

        Returns:
            nested list: a nested list that represents an empty board with traps
        """
        b_list=[]
        trap_pos=[3,6,4,5,2,7]
        for row in range (6): 
            row=[]
            for c in range (9):
                self.stacks=Stacks()
                if c==trap_pos[0]:
                    row.append(self.stacks.a_trap)
                else:
                    row.append(self.stacks.a_stack)
            b_list.append(row)
            trap_pos.pop(0)
        return b_list

    def as_str(self):
        """Return a string representation of the current state of the Board object.
           The string representation is suitable for being passed to the refresh
           method of a IgelView object.


        Returns:
            string: a cohesive string representation of the board for use by the igel_view module
        """
        self.b_str=''
        self.add_lines()
        for i in range (6):
            self.b_str+=''.join(self.board[i])
        return self.b_str

    def move_forward(self, row, col):
        """Move the token that is at the top of the stack in the given row and column one step forward.

        Args:
            row (int): the row number; between 1 and 6
            col (int): the column number; between 1 and 9
        """
        columns=1
        while columns<=8:
            if int(col)==columns:
                play_tok=row[columns-1][-1]
                row[columns-1]=row[columns-1][:-1]
                row[columns]+=play_tok
            columns+=1

    def move_sideways(self, row, col, dir):
        """Move the token that is at the top of the stack in the given row and
           column one step sideways in the given direction.

        Args:
            row (int): the row number; between 1 and 6
            col (int): the column number; between 1 and 9
            dir (int): either 1 (down) or -1 (up)
        """
        if dir==1:
            tok=self.board[row][col][-1]
            self.board[row][col]=self.board[row][col][:-1]
            self.board[row-1][col]+=tok
        elif dir==-1:
            tok=self.board[row][col][-1]
            self.board[row][col]=self.board[row][col][:-1]
            self.board[row+1][col]+=tok

    def get_winner(self):
        """Check whether there is a winner with the current state of the Board.
           If not, return None. If so, return the winning player's color. I.e.
           Y', 'R', 'G', etc.
        """
        winner=None
        color_count={'Y':0, 'R':0, 'G':0, 'P':0, 'B':0, 'O':0}
        column9=''
        for row in range (len(self.board)):
            column9+=self.board[row][16]
        for i in column9:
            color_count[i]+=1
        n=0
        for p in color_count:
            if color_count[p]>=3:
                winner=p
            n+=1
        return(winner)

    def add_tokens(self, answer, p, act_players):
        """Adds tokens to the board during the start up phase.

        Args:
            answer (string): a string from the game object that 
                             passses the user input for the start up phase
            p (int): an integer from the game object that passes 
                     the index of the current player in the act_players list
            act_players (list): an instance variable from the players object
                                that is a list of all the players in the current game instance
        """
        ansRow=self.board[int(answer)-1]
        ansRow[0]+=act_players[p]
    
    def remove_lines(self):
        """Removes lines from the list representation of the board.
        """
        for r in range (6):
            while '|' in self.board[r]:
                self.board[r].remove('|')
            while '//' in self.board[r]:
                self.board[r].remove('//')
    
    def add_lines(self):
        """Adds lines to the list representation of the board.
        """
        for i in range (6):
            v=1
            while v<=17:
                if v==17 and i!=5:
                    self.board[i].insert(v,'//')
                elif v<17:
                    self.board[i].insert(v,'|')
                v+=2

    def check_empty_row(self, dice, act_players):
        """Checks the given row for any player tokens. If there are none then the row is empty and the player's turn is over.

        Args:
            dice (int): random generated integer between 1 and 6 representing a rolled die.
            act_players (list): an instance variable from the players object
                                that is a list of all the players in the current game instance
        Returns:
            boolean: a boolean declaring whether or not the row is empty
        """
        self.remove_lines()
        for i in range ((len(self.board[dice-1]))-1):
            if self.board[dice-1][i]=='' or self.board[dice-1][i]=='@':
                empty_row=True
            elif self.board[dice-1][i][0]=='@' and len(self.board[dice-1][i])>1 and self.stacks.allow[i]=='Trapped':
                empty_row=True
            elif self.board[dice-1][i][-1] in act_players:
                empty_row=False
                return (empty_row)
        self.add_lines()
        return empty_row
