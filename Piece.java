import java.util.ArrayList;

public abstract class Piece {

	private final Color color;
	private Coordinate coord;
	
	private boolean hasMoved;

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public Piece(Color color, int num, int letter) { //This isn't used. Wanna delete it?
		this.coord = new Coordinate(num, letter);
		this.color = color;
	}
	
	/**
	 * Constructs a Piece object
	 * 
	 * @param color
	 *            the Color (White or Black)
	 * @param coord
	 *            Coordinate of this object
	 */
	public Piece(Color color, Coordinate coord) {
		this.color = color;
		this.coord = coord;
	}

	/**
	 * Constructs a Piece object
	 * 
	 * @param color
	 *            the Color (White or Black)
	 * @param notation
	 *            the notation of the coordinate (ie a4 or d8)
	 */
	public Piece(Color color, String notation) { //This isn't used. Wanna delete it?
		this.color = color;
		this.coord = Coordinate.decode(notation);
	}

	/**
	 * Checks if move is impeded by other peices
	 * 
	 * @param Board
	 *            b board of move
	 * @param Coordinate
	 *            the coordinate of the move
	 * @return true if move is impeded, false otherwise
	 */
	protected abstract boolean isImpeded(Board b, Coordinate move);

	/**
	 * Checks if move is valid (exempt from check). SHOULD USE isImpeded(Board, Coordinate)
	 * 
	 * @param Board
	 *            b board of move
	 * @param Coordinate
	 *            the coordinate of the move
	 * @return true if move is valid, false if not
	 */
	protected abstract boolean testMove(Board b, Coordinate move);
	
	/**
	 * Returns all of the phyiscally possible Coordinate locations that this piece can move to in an ArrayList (exempting check).
	 * 
	 * @return ArrayList<Coordinate>
	 * 		ArrayList of all of the possible Coordinate locations this piece can move to.
	 */ 
	protected abstract ArrayList<Coordinate> getMoveSpan();

	public boolean hasMove(Board board, King king, Player oppPlayer) {
		ArrayList<Coordinate> coords = getMoveSpan();
		for (Coordinate coord : coords) {
			if ((Board.isValid(coord) && testMove(board, coord)
					&& ChessGame.testCheck(board, this.getCoord(), coord, oppPlayer)))
				return true;
		}
		return false;
	}

	/**
	 * Checks if opponent piece is at location (is of other color)
	 * 
	 * @param loc
	 *            the Location of the piece
	 */
	public boolean isSameColor(Location loc) {  //the other method is more useful and is actually implemented
		if (loc.getPiece() == null)
			return false;
		return loc.getPiece().getColor().equals(this.color);
	}
	
	/**
	 * Checks if opponent piece is at location (is of other color)
	 * 
	 * @param piece
	 *             The piece being checked.
	 * 
	 * @return false if piece == null or isn't the same color, true if so.
	 */
	public boolean isSameColor(Piece piece) { 
		if (piece == null)
			return false;
		return piece.getColor().equals(this.color);
	}
	
	/**
	* Generates an increment based on the inputted coords.  
	* 
	* @param n1
	* 		the first coordinate on one axis of a (slope) i.e, this would be x1
	* @param n2
	* 		the second coordinate on one axis of a (slope) i.e, this would be x2
	* 
	* @return -1 if n1 is more than n2, 0 if they are equal, 1 if n2 is more than n1
	*/
	public static int genInc(int n1, int n2) {
		if (n1 > n2)
			return -1;
		if (n1 == n2)
			return 0;
		return 1;
	}
	
	/**
	 * Returns the Color of this Piece
	 * 
	 * @return The color of the Piece
	 */ 
	public Color getColor() {
		return color;
	}
	
	
	/**
	 * Returns the Coordinate of this object
	 * 
	 * @return the Coordinate of this object
	 */
	public Coordinate getCoord() {
		return coord;
	}
	
	/**
	 * Returns the num field of the Piece's Coordinate
	 * 
	 * @return the num field of the Piece's Coordinate
	 */
	public int getNum() {
		return coord.getNum();
	}
	
	/**
	 * Returns the letter field of the Piece's Coordinate
	 * 
	 * @return the letter field of the Piece's Coordinate
	 */
	public int getLetter() {
		return coord.getLetter();
	}
	
	/**
	 * Sets the Coordinate field for this piece
	 */
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

}
