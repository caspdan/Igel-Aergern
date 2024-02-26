"""Creates the players for the Igel Aergern game.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""

import os

COLORS = ['Y', 'R', 'G', 'P', 'B', 'O','']
PLAYER_COUNT=''

class Players:

    def __init__(self, inputs, view):
        """Initializes the player object

        Args:
            self (object): Object containing pertinent information about active players in an instance of the game
            inputs (object): Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.list=['','','','','','']
        self.players=self.create_players(inputs, view)

    def create_players(self, inputs, view):    
        """Creates a list of how many players there are in any game and assigns colors based on their choices.

        Args:
            inputs (object): Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        self.player_assign(inputs ,view)
        return(PLAYER_COUNT)

    def player_assign(self, inputs, view):
        """A helper function that contains the functionality of the while loop in create_players.

        Args:
            inputs (object): Object that inputs and outputs player interactions as well as checks validity and acceptibility of player inputs
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        answer_ok=False
        while answer_ok==False:
            self.PLAYER_COUNT=view.request_input('How many players? (1-6): ')
            answer_ok=inputs.checkOK(self.PLAYER_COUNT)                                  #check acceptability of player input (if not a number 1-6, then unacceptable)
            if answer_ok==True:
                self.PLAYER_COUNT=int(self.PLAYER_COUNT)
                self.color_assign(view)
            else:
                view.inform('Invalid Input!')

    def color_assign(self, view):
        """A helper function that contains the functionality of the while loop in playerAssign.

        Args:
            view (object): Object that refreshes the view of the board as well as prints messages and input requests
        """
        i=0
        self.act_players=[]
        while i < self.PLAYER_COUNT:
            os.system("cls||clear")
            color=view.request_input('Player ' + str(i+1) + ' Choose Your Color ' + str(COLORS)+':' )
            if color in COLORS:
                if len(COLORS)>1:
                    self.act_players+=COLORS.pop(COLORS.index(color))
                else:
                    self.list[5]=COLORS.pop(0)
                i=i+1
            else:
                view.inform('Invalid Input!')