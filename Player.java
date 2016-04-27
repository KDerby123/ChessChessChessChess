import java.util.ArrayList;
public class Player {
	private ArrayList<Piece> pieces;
	private ArrayList<String> moves;
	private Color color;
	private String name;
	private King king;
	
	public Player(String name) {
		this.name = name;
		pieces = new ArrayList<Piece>();
		moves = new ArrayList<String>();
	}
	
	
	public ArrayList<Piece> getPieces() { //gets the Player's pieces
		return pieces;
	}
	
	public ArrayList<String> getMoves() { //gets the players moves
		return moves;
	}
	
	public void addMove(String move) { //Adds a move to the player's list of moves
		moves.add(move);
	}
	
	public String getLastMove() { //gets the last move
		if (moves.size() > 0)
			return moves.get(moves.size()-1);
		return "No Moves";
	}
	
	public void removePiece(Piece p) {
		pieces.remove(p);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void addPiece(Piece piece) {
		pieces.add(piece);
	}
	
	public void setKing(King king) {
		this.king = king;
	}

	public King getKing() {
		return this.king;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
