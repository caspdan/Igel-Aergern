"""Functions for checking contents of a stack in an Igel Aergern game.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""

alpha_big={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}

class Stacks:

    def __init__(self):
        self.stacks=self.create_stacks()
        self.traps=self.create_traps()

    def create_stacks(self):
        self.a_stack=''

    def create_traps(self):
        self.a_trap='@'

    def checkStartStack (self, game, board):
        """Checks the first stack of each row for at least one token, if there is not one yet, a player must fill it before stacking in another row.

        Parameters
        ---
        b: list
            A nested list representation that accurately conveys the position of each player token as of the most recent player decisions.
        answer: string
            A string representation of the player's choice of which row to place their next token in.   
        """
        row_check=board.board[int(game.answer)-1]
        for row in board.board:
            if (len(row_check[0]))+1==(len(row[0])) or (len(row_check[0]))+1==(len(row[0]))+1:
                allowed=True
            else:
                allowed=False
                return(allowed)
        return(allowed)

    def check_top_stack(self, game, board, players, p=None, row=None):
        """Checks the topmost token of each stack in the indicated row.

        Parameters
        ---
        b: list
            A nested list representation that accurately conveys the position of each player token as of the most recent player decisions.
        actPlayers: list
            A list containing the representative player token for each active player, ordered to reflect the order of play.
        dice: random integer
            A random integer that represents a player's die roll.
        p: integer
            An iterated integer that allows for easy indexing of actPlayers.
        r: integer or None Object
            An integer that iterates through each row that allows checkTopStack to check each row's stacks.
        """
        self.allow=[]
        i=0
        board.remove_lines()
        while i<((len(board.board[0]))-1):
            if row==None:
                self.act_row=board.board[game.dice-1]
                self.row_num=game.dice-1
            else:
                self.act_row=board.board[row]
                self.row_num=row
            self.stack=self.act_row[i]
            if self.stack=='':
                self.allow.append(False)
                i+=1
            elif p==None:
                if self.stack[-1] in players.act_players:
                    self.trap_allowance(board, i)
                    i+=1
                else:
                    self.allow.append(False)
                    i+=1
            elif players.act_players[p]==self.stack[-1]:
                self.trap_allowance(board, i)
                i+=1
            elif self.stack[-1] in players.act_players and game.other_piece:
                self.trap_allowance(board, i)
                i+=1
            else:
                self.allow.append(False)
                i+=1
        board.add_lines()
        return self.allow
    
    def check_trap_stack(self, i):
        trap_pos=[3,6,4,5,2,7]
        if i==trap_pos[self.row_num]:
            return(True)
        else:
            return(False)
        
    def check_behind(self, board, i):
        for row in board.board:
            stack=0
            while stack<i:
                if row[stack]!='':
                    return(True)
                else:
                    stack+=1
        return(False)
    
    def trap_allowance(self, board, i):
        self.trapped=False
        on_trap=self.check_trap_stack(i)
        if on_trap:
            self.trapped=self.check_behind(board, i)
        if self.trapped:
            self.allow.append('Trapped')
        else:
            self.allow.append(True)