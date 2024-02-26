package igelWithConcrete;

public class Igel1 {
    private char color;

    /**
     * Create an Igel token of the given color. The color has to be one of Player.COLORS. 
     */
    public Igel1(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public String toString() {
        return Character.toString(color);
    }
}
