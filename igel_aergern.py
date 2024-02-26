"""Main functions of the Igel Aergern game.

@author: Daniel Casper
@version: Fall 2023

I affirm that I have carried out my academic endeavors
with full academic honesty. [Daniel Casper]
"""


from board import Board
from igel_view import IgelView
from player_object import Players
from igel_turns_object import Turns
from igel_prompts_object import Inputs

COLORS = ['Y', 'R', 'G', 'P', 'B', 'O','']
PLAYER_COUNT = ''

class IgelAergern:

    def __init__(self):
        """Create an instance of a Igel Aergern game."""
        self.board = Board()
        self.view = IgelView()
        self.player_count = PLAYER_COUNT
        # Do NOT modify this method above this line.
        # You may add to this method below this line.
        self.inputs = Inputs()
    
    def run_game(self):
        """Run one full game of Igel Aergern."""
        # *** Do NOT modify this method! ***
        self.players=self._create_players()
        self._update_interface()
        self._start_up_phase()
        self._main_phase()

    def _create_players(self):
        """Create the list of players."""
        self.players=Players(self.inputs ,self.view)
        return(self.players)
    
    def _update_interface(self):
        """Update the board representation that the players see."""
        self.board.remove_lines()
        self.b_str=self.board.as_str()
        self.view.refresh(self.b_str)

    def _start_up_phase(self):
        """Begins the start up phase of the game.
        """
        self.game=Turns(self, self.board, self.players, self.inputs, self.view)

    def _main_phase(self):
        """Run the main phase of an Igel Aergern game."""
        self.winner=self.game.begin(self, self.players, self.board, self.inputs, self.view)


############
### Main ###

if __name__=='__main__':
    igel_game = IgelAergern()
    igel_game.run_game()