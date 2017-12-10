/**
 * @author Colin Ackerley
 * @author Steven Benmoha
 */
package cs213.chessapp71;
public abstract class Piece
{
    boolean hasMoved;
    /**
     * * Checks the validity of the current Piece's move
     *
     * @param board  the Board object 'board'
     * @param b      the 2d Piece array b
     * @param curRow the current row of the Piece
     * @param curCol the current column of the Piece
     * @param newRow the new row of the Piece
     * @param newCol the new column of the Piece
     * @return true if the proposed move is valid, false otherwise
     */
    public abstract boolean checkMoveValidity(Board board, Piece[][] b, int curRow, int curCol, int newRow, int newCol);
    /**
     * Gets the color of the Piece
     *
     * @return String representing the color of the current piece
     */
    public abstract String getColor();
}
