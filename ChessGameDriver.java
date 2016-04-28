import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ChessGameDriver extends JFrame implements MouseMotionListener, MouseListener, ChessGameInterface {
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;

	Coordinate selectedCoord, destinationCoord;
	JLabel output0, output1;

	final java.awt.Color brown = new java.awt.Color(136, 67, 8);

	ChessGame game;

	public ChessGameDriver() {
		Dimension boardSize = new Dimension(600, 600);
		Dimension windowSize = new Dimension(600, 630);

		// Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(windowSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane

		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(9, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground(i % 2 == 0 ? brown : java.awt.Color.white);
			else
				square.setBackground(i % 2 == 0 ? java.awt.Color.white : brown);
		}

		// add output text
		output0 = new JLabel("White turn", JLabel.LEFT);
		chessBoard.add(output0);
		output1 = new JLabel("", JLabel.RIGHT);
		chessBoard.add(output1);

		// Add black pieces to the board
		int letter;
		for (letter = 0; letter <= 7; letter += 7)
			addPiece("BlackRook.png", 0, letter);
		for (letter = 1; letter <= 6; letter += 5)
			addPiece("BlackKnight.png", 0, letter);
		for (letter = 2; letter <= 5; letter += 3)
			addPiece("BlackBishop.png", 0, letter);
		for (letter = 0; letter <= 7; ++letter)
			addPiece("BlackPawn.png", 1, letter);
		addPiece("BlackQueen.png", 0, 3);
		addPiece("BlackKing.png", 0,4);

		// white pieces to the board
		for (letter = 0; letter <= 7; letter += 7)
			addPiece("Rook.png", 7, letter);
		for (letter = 1; letter <= 6; letter += 5)
			addPiece("Knight.png", 7, letter);
		for (letter = 2; letter <= 5; letter += 3)
			addPiece("Bishop.png", 7, letter);
		for (letter = 0; letter <= 7; ++letter)
			addPiece("Pawn.png", 6, letter);
		addPiece("Queen.png", 7, 3);
		addPiece("King.png", 7, 4);

		game = new ChessGame(new Player("Jack"), new Player("Jill"), this);

	}

	private void addPiece(ImageIcon ico, int num, int letter) {
		Coordinate coord = new Coordinate(num, letter);
		addPiece(ico, coord);
	}

	private void addPiece(ImageIcon ico, Coordinate coord) {
		JLabel piece = new JLabel(ico);
		JPanel panel = (JPanel) chessBoard.getComponent(coordToComponentIndex(coord));
		panel.add(piece);
	}

	private void addPiece(String ico, int num, int letter) {
		addPiece(new ImageIcon(ico), num, letter);
	}

	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());

		if (c instanceof JPanel || c == output0 || c == output1)
			return;

		selectedCoord = componentIndexToCoord(chessBoard.getComponentZOrder(c.getParent()));

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	// Move the chess piece around

	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null)
			return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}

	// Drop the chess piece back onto the chess board

	public void mouseReleased(MouseEvent e) {
		if (chessPiece == null)
			return;

		
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		if (c == output0 || c == output1)
			return;

		chessPiece.setVisible(false);
		Container parent;
		boolean isPiece = c instanceof JLabel;
		if (isPiece)
			parent = c.getParent();
		else
			parent = (Container) c;
		
		destinationCoord = componentIndexToCoord(chessBoard.getComponentZOrder(parent));
		System.out.println("Selected Coord: "  + selectedCoord);
		System.out.println("Destination Coord: " + destinationCoord);
		boolean result = game.process(selectedCoord, destinationCoord);
		if(!result) {
			JPanel prev = (JPanel)chessBoard.getComponent(coordToComponentIndex(selectedCoord));
			prev.add(chessPiece);
			chessPiece.setVisible(true);
			resetCoords();
			return;
		}
		resetCoords();
		if(isPiece)
			parent.remove(0);
		
		parent.add(chessPiece);
		

		chessPiece.setVisible(true);

	}
	
	private void resetCoords() {
		selectedCoord = null;
		destinationCoord = null;
		
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public static Coordinate componentIndexToCoord(int component) {
		int row = component / 8; // aka num
		int col = component % 8; // aka letter
		return new Coordinate(row, col);
	}

	public static int coordToComponentIndex(Coordinate coord) {
		
		return 8 * coord.num + coord.letter;
	}

	public static void main(String[] args) {
		JFrame frame = new ChessGameDriver();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@Override
	public void displayResult0(String result) {
		output0.setText(result);
	}


	@Override
	public void displayResult1(String result) {
		output1.setText(result);
	}

}
