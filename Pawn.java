import java.util.ArrayList;
public class Pawn extends Piece {
	public boolean hasMoved;
        private int forwardInc;
	
	/**
	 * Creates a Pawn
	 * 
	 * @param color
	 * 		Color of the piece
	 * @param coord
	 * 		Coordinate of the piece
	 */ 
	public Pawn(Color color, Coordinate coord) {
		super(color, coord);
		hasMoved = false;
                forwardInc = -1;
                if (this.getColor() == Color.BLACK)
                    forwardInc = 1;
	}
	
	@Override
	public boolean isImpeded(Board board, Coordinate coord) { //implemented abstract method from Piece; returns true if Pawn is
		return (!board.isEmpty(new Coordinate(coord.getNum()+forwardInc,coord.getLetter()))); //impeded on it's path to the square
	}
	
	@Override
	public boolean testMove(Board board, Coordinate coord) { //I'll do this, as it has a weird move scheme
	        Location l = board.getLocAt(coord) ;
	        Piece p = l.getPiece();
	        int num = coord.getNum();
	        int letter = coord.getLetter();
		if (super.isSameColor(p)) //checks whether the piece is of the same color, A king check is not
		   	return false; //needed due to the fact that this would be an instance of checkmate
		else
			if (p == null)  //if location is empty
				if (super.getLetter() == letter) //If piece is on same letter rank
					if ((super.getNum() == num+(forwardInc*2)) && (!isImpeded(board,coord))) {
						return !hasMoved; //returns whether this Pawn has moved
                                        } else {
                                            return super.getNum() == num+forwardInc;
                                        }
				else if ((Math.abs(super.getLetter()-letter) == 1) && (super.getNum() == num-1)) //en passant check
					return (board.getWhitePlayer().getLastMove().substring(0,2).equals(Coordinate.notatedPos(num-1,letter)));
				else
					return false; //returns false atfer checking all possible cases
			else
				return ((Math.abs(super.getLetter()-letter) == 1) && (super.getNum() == num-1)); //take move
				
	}
	
	@Override
	public ArrayList<Coordinate> getMoveSpan() {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		coords.add(new Coordinate(super.getNum()+1,super.getLetter()+1));
		coords.add(new Coordinate(super.getNum()+1,super.getLetter()-1));
		coords.add(new Coordinate(super.getNum()+1,super.getLetter()));
		coords.add(new Coordinate(super.getNum()+2,super.getLetter()));
		return coords;
	}
	
	
	public boolean promoteCheck() {
		return getCoord().getNum() == 7;
	}
	
	public String toString() {
		return Coordinate.notatedPos(super.getCoord());
	}

	public void setHasMovedTrue() {
		hasMoved = true;
	}
}
