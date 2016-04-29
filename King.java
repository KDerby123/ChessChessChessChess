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
                Coordinate selCoord;
                int letterInc = Piece.genInc(super.getLetter(),coord.getLetter());
                int selNum = super.getNum();
                int selLetter = super.getLetter() + letterInc;
                while (selLetter < coord.getLetter()) {
                    selCoord = new Coordinate(selNum,selLetter);
                    if (board.getPieceAtCoord(selCoord) != null)
                            return true;
                    selLetter += letterInc;
                }
                return false;
	}

	public boolean testMove(Board board, Coordinate coord) { //Tests whether the move is in a 1 square radius and a valid move.
        	Location l = board.getLocAt(coord); 
        	Piece p = l.getPiece();
	        int num = coord.getNum();
	        int letter = coord.getLetter();
	        int i;
        	if (super.isSameColor(p))
        		return false;
        	/* if (!hasMoved) {
        		if (super.getColor() == Color.WHITE) 
        			i = 7;
        		else
        			i = 0;
        		if (coord.equals(new Coordinate(i,6))) {
        			p = board.getPieceAtCoord(new Coordinate(i,7));
        			System.out.println(p);
        			return ((p != null) && (p instanceof Rook) && (!((Rook) p).hasMoved()) && !isImpeded(board,new Coordinate(i,7)));
        		} else if (coord.equals(new Coordinate(i,2))) {
        			p = board.getPieceAtCoord(new Coordinate(i,0));
        			return ((p != null) && (p instanceof Rook) && (!((Rook) p).hasMoved()) && !isImpeded(board, new Coordinate(i,0)));
        		}
        	} */
        	return ((Math.abs(super.getNum()-num) <= 1) && (Math.abs(super.getLetter()-letter) <= 1));
	}

	public boolean isInCheck(Board board, Player oppPlayer) {
		ArrayList<Piece> pieces = oppPlayer.getPieces(); // This method will check if the king is in check. It takes in a board
                Piece piece;
		for (int i = 0;i<pieces.size();i++) {    
                        piece = pieces.get(i);//and the opposing player's pieces and tests whether 
			if (piece != null && piece.testMove(board,super.getCoord())) {
                            System.out.println("Offending piece"+piece);// they can reach the king. It will return true if so.
                            return true;
                        }
		}
		return false;
	}
	
	
	public ArrayList<Coordinate> getMoveSpan() {
    		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
    		for (int i = -1;i<=1;i++) {
    			for (int k = -1;k<=1;k++) {
    				if ((i != 0) || (k != 0)) {
                                    spanHelper(coords,i,k);
                                }
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

