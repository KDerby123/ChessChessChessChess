import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	private boolean hasMoved;

	public King(Color color, int num, int letter) {
		super(color, num, letter);
	}

	public King(Color color, Coordinate coord) {
		super(color, coord);
		hasMoved = false;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}
	public boolean isImpeded(Board board, Coordinate coord) { // skeleton
		return false; // Kings can't be impeded
	}

	public boolean testMove(Board board, Coordinate coord) { //Tests whether the move is in a 1 square radius and a valid move.
        	Location l = board.getLocAt(coord); 
        	Piece p = l.getPiece();
	        int num = coord.getNum();
	        int letter = coord.getLetter();
	        int i;
        	if (super.isSameColor(p))
        		return false;
        	if (!this.hasMoved()) {
        		if (super.getColor() == Color.WHITE) 
        			i = 0;
        		else
        			i = 7;
        		if (coord.equals(new Coordinate(i,6))) {
        			p = board.getLocAt(new Coordinate(i,7)).getPiece();
        			return ((p != null) && (p instanceof Rook) && (!((Rook) p).hasMoved()));
        		} else if (coord.equals(new Coordinate(i,3))) {
        			p = board.getLocAt(new Coordinate(i,1)).getPiece();
        			return ((p != null) && (p instanceof Rook) && (!((Rook) p).hasMoved()));
        		}
        	}
        	return ((Math.abs(super.getNum()-num) <= 1) && (Math.abs(super.getLetter()-letter) <= 1));
    	
	}

	public boolean isInCheck(Board board, Player player) {
		ArrayList<Piece> pieces = player.getPieces();// This method will check if the king is in check. It takes in a board
		for (Piece p : pieces) {                                 //and the opposing player's pieces and tests whether 
			if (p != null && p.testMove(board,super.getCoord())) // they can reach the king. It will return true if so.
				return true;
		}
		return player.getKing().testMove(board,super.getCoord());
	}
	
	
	public ArrayList<Coordinate> getMoveSpan() {
    		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
    		for (int i = -1;i<=1;i++) {
    			for(int k = -1;k<=1;k++) {
    				if ((i != 0) || (k != 0))
    					spanHelper(coords,i,k);
    			}
    		}
    		return coords;
    	}
    	
    	private void spanHelper( ArrayList<Coordinate> coords, int numInc, int letterInc) {
    		Coordinate coord = new Coordinate(super.getNum()+numInc, super.getLetter()+letterInc);
    		if (Board.isValid(coord))
    			coords.add(coord);
    	}
	
	public String toString() {
		return "K" + Coordinate.notatedPos(super.getCoord());
	}
	
	
}

