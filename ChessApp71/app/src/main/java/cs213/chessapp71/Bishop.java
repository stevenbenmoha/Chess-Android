/**
 * @author Colin Ackerley
 * @author Steven Benmoha
 */
package cs213.chessapp71;
public class Bishop extends Piece
{
    private String color;
    /**
     * @param color String representing what color to make the Bishop
     *              <p>
     *              Bishop constructor
     */
    public Bishop(String color)
    {
        this.color = color;
    }
    /**
     * Checks the validity of the current Bishop's move
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
        if(rowDiff != colDiff)
            return false;
        int rowOffset, colOffset;
        if(newRow > curRow) // Determines if the piece is moving up or down the
        // rows
        {
            rowOffset = 1;
        }
        else
        {
            rowOffset = -1;
        }
        if(newCol > curCol) // Determines if the piece is moving left or right
        // in the columns
        {
            colOffset = 1;
        }
        else
        {
            colOffset = -1;
        }
        int col = curCol + colOffset;
        int row = curRow + rowOffset;
        while(row != newRow)
        {
            if(b[row][col] != null) // If space is occupied
            {
                return false;
            }
            row += rowOffset;
            col += colOffset;
        }
        return true;
    }
    /**
     * Gets the color of the Bishop piece
     *
     * @return String with the color of the current Bishop
     */
    public String getColor()
    {
        return this.color;
    }
    /**
     * Gets the string representation of the current Bishop
     *
     * @return The string representation of the current Bishop
     */
    public String toString()
    {
        return color.charAt(0) + "B";
    }
}
