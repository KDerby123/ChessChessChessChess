import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Color color,Coordinate coord) {
        super(color,coord);
    }
    
    public boolean isImpeded(Board board, Coordinate coord) {
        return false; //Knights can't be impeded
    }
    
    
    public boolean testMove(Board b, Coordinate coord) { //Moves in a L fashion, 1 square vertically, 2 squares horizontally
        Location l = b.getLocAt(coord);
        Piece p = l.getPiece();
        int num = coord.getNum();
        int letter = coord.getLetter();
        if (super.isSameColor(p))
           return false;
        int numTest = Math.abs(super.getNum()-num);
        int letterTest = Math.abs(super.getLetter()-letter);
        return (((numTest == 1) && (letterTest == 2)) || ((numTest == 2) && (letterTest == 1))); 
    }
    
    public ArrayList<Coordinate> getMoveSpan() {
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
        int a = 2;
        int k = 1;
        // there was two i's here. Common
        for (int i = 0; i<4; i++) {
            if (i % 2 == 1)
                a *= -1;
            else if (i == 2)
                k *= -1;
            spanHelper(coords,i,k);
        }
        return coords;
    }
    
    public void spanHelper(ArrayList<Coordinate> coords,int i, int k) {
        coords.add(new Coordinate(super.getNum()+i,super.getLetter()+k));
        coords.add(new Coordinate(super.getNum()+k,super.getLetter()+i));
    }
    
    public String toString() {
        return "N" + Coordinate.notatedPos(super.getCoord());
    }
}
