"""A terminal interface for playing Igel Aergern.

@author: Kristina Striegnitz
@version: Fall 2023
"""

import os

# width of a cell
CELL_WIDTH = 5

class IgelView:

    def __init__(self):
        self.cell_width = CELL_WIDTH

    def refresh(self, board_str, winner=None):
        """Prints a representation of the Igel Aergern board to the terminal.
        
        @param board_str: a representation of the board as a string. The tracks/rows 
            are separated from each other using // and cells within one track are
            separated using |. Empty cells are represented using an empty string.
            Otherwise they are represented as a sequence of the stacked colors. E.g.
            'RBO' represents a stack of red at the bottom, blue, and orange. 
            Here is an example of a valid board representation:
            'Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O'

        @param winner: the name of the winner, if there is one.
        """
        os.system("cls||clear")
        board_w = self._board_width(board_str)
        self._print_header(board_w)
        self._print_board(board_str)
        print()
        if winner:
            self._display_winner(winner, board_w)

    def request_input(self, message):
        """Requests an answer from the player and returns whatever string the player enters.
        
        @param message: the message to display to request input from the player.

        @return: a string
        """
        answer = input(message)
        while answer.strip()=="":
            answer = input("Please enter an answer.  ")
        return answer.strip()

    def inform(self, message):
        """Prints the given message to the terminal."""
        print(message)

    #################################
    ### Header and winner banners ###    

    def _print_header(self, width):
        print(self._center(" /~~~~~~~~~~~~~~\\", width))
        print(self._center(" | IGEL AERGERN |", width))
        print(self._center(" \\~~~~~~~~~~~~~~/", width))
        print()

    def _display_winner(self, name, width):
        message = name + " won!!"
        print()
        print(self._center(" ~~~ " + message + " ~~~", width))
        print()
        print()
    
    ############################
    ### Displaying the board ###

    def _print_board(self, board_str):
        rows = board_str.split("//")
        num_cols = len(rows[0].split("|"))
        self._print_start_goal_labels(num_cols)
        self._print_border(num_cols)
        for i, row in enumerate(rows):
            self._print_row(i+1, row)
            self._print_border(num_cols)
        self._print_col_labels(num_cols)
        
    def _print_row(self, i, row_str):
        cells = row_str.split("|")
        print("|", end="")
        for cell in cells:
            self._print_cell(cell)
            print("|", end="")
        print(" " + str(i), end="")
        print()

    def _print_cell(self, cell_str):
        if len(cell_str) < self.cell_width:
            diff = self.cell_width - len(cell_str)
            cell_str += " " * diff
        print(cell_str, end="")
        
    def _print_border(self, cols):
        print("-" * (self.cell_width * cols + cols + 1))

    def _print_start_goal_labels(self, cols):
        empty_cols = cols - 2
        space = " " * (self.cell_width * (empty_cols) + empty_cols) 
        print(" Start " + space + "Goal")          

    def _print_col_labels(self, cols):
        for j in range(cols):
            space = " " * (self.cell_width//2)
            print(" " + space, end="") # extra blank for '|'
            print(j+1, end="")
            print(space, end="")
        print()

    ########################
    ### Helper functions ###

    def _board_width(self, board_str):
        row_str = board_str.split("//")[0]
        col_count = len(row_str.split("|"))
        board_w = self.cell_width * col_count + col_count + 1 + 2
        return board_w

    def _center(self, a_string, board_w):
        diff = board_w - len(a_string)
        if diff > 0:
            pad_w = diff//2
            left_pad = " " * pad_w
            a_string = left_pad + a_string
        return a_string
    
###############
### Testing ###

# an empty board
TEST_1  = "||||||||//"
TEST_1 += "||||||||//"
TEST_1 += "||||||||//"
TEST_1 += "||||||||//"
TEST_1 += "||||||||//"
TEST_1 += "||||||||"

# some hedgehogs on the board
TEST_2  = "Y||||||||//"
TEST_2 += "||||||||Y//"
TEST_2 += "Y||R||B||O||//"
TEST_2 += "YR|RBO||RBOO|||||//"
TEST_2 += "||||||||//"
TEST_2 += "||BR||||||O"

if __name__=='__main__':
    view = IgelView()
    view.refresh(TEST_1)
    print()
    print()
    input("Enter to continue ...")
    view.refresh(TEST_2)
    
