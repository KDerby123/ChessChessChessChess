import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
	private boolean hasMoved;
	
	public Rook(Color color, Coordinate coord) {
		super(color, coord);
		hasMoved = false;
	}

	public boolean isImpeded(Board board, Coordinate coord) { // skeleton
		int selNum = super.getNum();
		int selLetter = super.getLetter();
		int num = coord.getNum();
		int letter = coord.getLetter();
		int numInc = Piece.genInc(selNum,num);
		int letterInc = Piece.genInc(selLetter,letter);
		while ((selNum != num) || (selLetter != letter)) {
			if (!board.isEmpty(new Coordinate(selNum,selLetter)))
				return true;
			selNum += numInc;
			selLetter += letterInc;
		}
		return false;
	}

	public boolean testMove(Board board, Coordinate coord) {
		// Moves horizontally and vertically
		Location l = board.getLocAt(coord);
		Piece p = l.getPiece();
		int num = coord.getNum();
		int letter = coord.getLetter();
		if (super.isSameColor(p))
			return false;
		if ((super.getNum() != num) && (super.getLetter() != letter))
			return false;
		return !isImpeded(board, coord);
	}

	public ArrayList<Coordinate> getMoveSpan() {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		spanHelper(coords, 1, 0);
		spanHelper(coords, -1, 0);
		spanHelper(coords, 0, 1);
		spanHelper(coords, 0, -1);
		return coords;
	}

	public void spanHelper(List<Coordinate>coords ,int i, int k) {
    		int numInc = i;
    		int letterInc = k;
    		Coordinate coord = new Coordinate(super.getNum()+numInc,super.getLetter()+letterInc);
    		while (Board.isValid(coord)) {
    			coords.add(coord);
    			coord = new Coordinate(coord.getNum()+numInc,coord.getLetter()+letterInc);
    		}
    	}

	public String toString() {
		return "R" + Coordinate.notatedPos(super.getCoord());
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved() {
		hasMoved = false;
	}
}
