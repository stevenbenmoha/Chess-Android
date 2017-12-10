/**
 * @author Colin Ackerley
 * @author Steven Benmoha
 */
package cs213.chessapp71;
public class Knight extends Piece
{
    private String color;
    /**
     * @param color String color representing which color the given Knight should be
     *              <p>
     *              Knight constructor
     */
    public Knight(String color)
    {
        this.color = color;
    }
    /**
     * Checks the validity of the current Knight's move
     *
     * @param board  the Board object 'board'
     * @param b      the 2d Piece array b
     * @param curRow the current row of the Piece
     * @param curCol the current column of the Piece
     * @param newRow the new row of the Piece
     * @param newCol the new column of the Piece
     * @return true if the proposed move is valid, false otherwise
     */
    public boolean checkMoveValidity(Board board, Piece[][] b, int curRow, int curCol, int newRow, int newCol)
    {
        if(b[newRow][newCol] != null)
            if(b[newRow][newCol].getColor().equalsIgnoreCase(b[curRow][curCol].getColor()))
                return false;
        int rowDiff = Math.abs(curRow - newRow);
        int colDiff = Math.abs(curCol - newCol);
        return rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2;
    }
    /**
     * Gets the color of the current Knight
     *
     * @return String with the color of the current Knight
     */
    public String getColor()
    {
        return this.color;
    }
    /**
     * Gets the string representation of the current Knight
     *
     * @return The string representation of the current Knight
     */
    public String toString()
    {
        return color.charAt(0) + "N";
    }
}
