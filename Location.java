public class Location {

	private Piece piece;
	private final Coordinate coord; // coordinate doesn't move for Location
	private final Color color;

	/**
	 * Constructs a new Location
	 * 
	 * @param piece
	 *            the Piece at this location
	 * @param num
	 *            the number coordinate
	 * @param letter
	 *            the letter coordinate
	 */
	public Location(Piece piece, int num, int letter, Color color) {
		this.piece = piece;
		this.coord = new Coordinate(num, letter);
		this.color = color;
	}

	/**
	 * Constructs a new Location
	 * 
	 * @param piece
	 *            the Piece at this location
	 * @param coord
	 *            the Coordinate
	 */
	public Location(Piece piece, Coordinate coord, Color color) {
		this.piece = piece;
		this.coord = coord;
		this.color = color;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Coordinate getCoord() {
		return coord;
	}
	
	public Color getColor() {
		return color;
	}

}
