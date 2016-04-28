import java.util.ArrayList;

public class Bishop extends Piece {
	
	public Bishop(Color color, Coordinate coord) {
        	super(color,coord);
     	}
	
	@Override
     	public boolean isImpeded(Board board, Coordinate coord) { //skeleton
     		int selNum = super.getNum();
     		int selLetter = super.getLetter();
     		int num = coord.getNum();
     		int letter = coord.getLetter();
     		int numInc = Piece.genInc(selNum,num);
     		int letterInc = Piece.genInc(selLetter,letter);
     		while ((selNum != num) && (selLetter != letter)) {
     			if (!board.isEmpty(new Coordinate(selNum,selLetter)))
     				return true;
     		}
     		return false;
     	}	
     
     	@Override
     	public boolean testMove(Board board, Coordinate coord) { //Moves diagonally and vertically
        	Location loc = board.getLocAt(coord);
        	Piece p = loc.getPiece();
        	int num = coord.getNum();
        	int letter = coord.getLetter();
        	if (super.isSameColor(p))
        		return false;
       		double slope = (1.0 * Math.abs(super.getNum()-num))/Math.abs(super.getLetter()-letter);
       		if (slope == 1.0)
       			return isImpeded(board,coord);
       		return false;
    	}
    	
     	@Override
    	public ArrayList<Coordinate> getMoveSpan() {
    		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
    		spanHelper(coords,1,1);
    		spanHelper(coords,1,-1);
    		spanHelper(coords,-1,-1);
    		spanHelper(coords,-1,1);
    		return coords;
    	}
    	
    	private void spanHelper( ArrayList<Coordinate> coords,int numInc,int letterInc) {
    		Coordinate coord = new Coordinate(super.getNum()+numInc,super.getLetter()+letterInc);
    		while (Board.isValid(coord)) {
    			coords.add(coord);
    			coord = (new Coordinate(coord.getNum()+numInc,coord.getLetter()+letterInc));
    		}
    	}
    	
    	public String toString() {
    		return "B" + Coordinate.notatedPos(super.getCoord());
    	}
}
