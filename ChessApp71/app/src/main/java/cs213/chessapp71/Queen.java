/**
 * @author Colin Ackerley
 * @author Steven Benmoha
 */
package cs213.chessapp71;
public class Queen extends Piece
{
    String color;
    public Queen(String color)
    {
        this.color = color;
    }
    /**
     * * Checks the validity of the current Queen's move
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
        if(new Rook(color).checkMoveValidity(board, b, curRow, curCol, newRow, newCol) || new Bishop(color).checkMoveValidity(board, b, curRow, curCol, newRow, newCol))
            return true;
        return false;
    }
    /**
     *
     *Gets the color of the current Queen
     *
     *@return String with the color of the current Queen
     */
    public String getColor()
    {
        return this.color;
    }
    /**
     * Gets the string representation of the current Queen
     * @return The string representation of the current Queen
     */
    public String toString()
    {
        return color.charAt(0)+"Q";
    }
}
