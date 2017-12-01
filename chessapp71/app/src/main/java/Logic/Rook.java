/**
 * @author Colin Ackerley
 * @author Steven Benmoha
 */
package Logic;
public class Rook extends Piece
{
    private String color;
    /**
     *@param color String representing what color to make the Rook
     *
     *Rook constructor
     *
     */
    public Rook(String color)
    {
        this.color = color;
    }
    /**
     * * Checks the validity of the current Rook's move
     * 
     *@param board   the Board object 'board'
     *@param b     the 2d Piece array b
     *@param curRow    the current row of the Piece
     *@param curCol        the current column of the Piece
     *@param newRow    the new row of the Piece
     *@param newCol        the new column of the Piece
     *@return true if the proposed move is valid, false otherwise
     */
    public boolean checkMoveValidity(Board board, Piece[][] b, int curRow, int curCol, int newRow, int newCol)
    {
        int rowDiff = Math.abs(curRow-newRow);
        int colDiff = Math.abs(curCol-newCol);
        if(b[newRow][newCol] != null)
            if(b[newRow][newCol].getColor().equalsIgnoreCase(b[curRow][curCol].getColor()))
                return false;
        if(rowDiff != 0 && colDiff != 0)
            return false;
        if(rowDiff == 0)
        {
            int offset;
            if(curCol < newCol)
                offset = 1;
            else
                offset = -1;
            for(int i = curCol+offset; i != newCol; i += offset)
                if(b[curRow][i] != null)
                    return false;
        }
        if(colDiff == 0)
        {
            int offset;
            if(curRow < newRow)
                offset = 1;
            else
                offset = -1;
            for(int i = curRow+offset; i != newRow; i += offset)
                if(b[i][curCol] != null)
                    return false;
        }
        hasMoved = true;
        return true;
    }
    /**
     * Gets the color of the current Rook
     *@return String with the color of the current Rook
     */
    public String getColor()
    {
        return this.color;
    }
    /**
     *Gets the string representation of the current Rook
     *@return The string representation of the current Rook
     */
    public String toString()
    {
        return color.charAt(0)+"R";
    }
}
