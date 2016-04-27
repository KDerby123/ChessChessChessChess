import java.util.ArrayList;

//Moves diagonally, vertically, and horizontally
public class Queen extends Piece {

	public Queen(Color color,Coordinate coord) {
		super(color,coord);
    	}
	
	public boolean isImpeded(Board board,Coordinate coord) {
		int selNum = super.getNum();
		int selLetter = super.getLetter();
		int num = coord.getNum();
		int letter = coord.getLetter();
		int numInc = Piece.genInc(selNum,num);
		int letterInc = Piece.genInc(selLetter,letter);
    		while ((selNum != num) || (selLetter != letter)) {
    			if (!board.isEmpty(super.getCoord()))
    				return true;
    			selNum += numInc;
    			selLetter += letterInc;
    		}
    		return false;
    	}
	
	public boolean testMove(Board board, Coordinate coord) { //takes in a Board, a x value, and a y checks if the move is valid
        	Location loc = board.getLocAt(coord);
        	Piece p = loc.getPiece(); //
        	int num = coord.getNum();
        	int letter = coord.getLetter();
        	double slope;
        	if (super.isSameColor(p))
        		return false;
        	if (super.getLetter()-letter != 0)
        		slope = (Math.abs(super.getNum()-num) * (1.0))/Math.abs(super.getLetter()-num);
        	else
        		slope = 0.0;
       		if ((slope == 0.0) || (slope == 1.0))
       			return isImpeded(board, coord);
       		return false;
	}
	
	public ArrayList<Coordinate> getMoveSpan() {
    		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
    		spanDiagonalHelper(coords,1,1);
    		spanDiagonalHelper(coords,1, -1);
    		spanDiagonalHelper(coords,-1,-1);
    		spanDiagonalHelper(coords,-1,1);
    		spanHorizHelper(coords,1,0);
    		spanHorizHelper(coords,-1,0);
    		spanHorizHelper(coords,0,1);
    		spanHorizHelper(coords,0,-1);
    		return coords;
    	}
    	
    	private void spanDiagonalHelper(ArrayList<Coordinate> coords,int numInc,int letterInc) {
    		Coordinate coord = new Coordinate(super.getNum()+numInc,super.getLetter()+letterInc);
    		while (Board.isValid(coord)) {
    			coords.add(coord);
    			coord = (new Coordinate(coord.getNum()+numInc,coord.getLetter()+letterInc));
    		}
    	}
    	
    	private void spanHorizHelper(ArrayList<Coordinate> coords,int i, int k) {
    		int numInc = i;
    		int letterInc = k;
    		Coordinate coord = new Coordinate(super.getNum()+numInc,super.getLetter()+letterInc);
    		while (Board.isValid(coord)) {
    			coords.add(coord);
    			coord = new Coordinate(super.getNum()+numInc,super.getLetter()+letterInc);
    		}
    	}
	
	public String toString() {
		return "Q" + Coordinate.notatedPos(super.getCoord());	
	}
	
}
