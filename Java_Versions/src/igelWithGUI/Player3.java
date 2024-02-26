package igelWithGUI;

public class Player3 {
    public static final char[] COLORS = {'Y', 'R', 'G', 'P', 'B', 'O'};
    private char color;

    /**
     * Create a player. The player's tokens are of the given color. The color 
     * has to be one of Player.COLORS.
     * 
     * @param color
     */
    public Player3(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public String toString() {
        return Character.toString(color);
    }
}
