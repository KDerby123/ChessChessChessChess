
//A single game of chess
import java.util.ArrayList;

public class ChessGame {

	private Board board;
    private String result;
	private int movesWithoutAgress;
	private int moves;
	private boolean whiteTurn;
	Player white, black;

	ChessGameInterface gameInterface;

	public ChessGame(Player playerOne, Player playerTwo, ChessGameInterface gameInterface) {
		gameSetup(playerOne, playerTwo);
		this.gameInterface = gameInterface;
                result = "Continue";
	}

	/*
	 * public boolean replay() {
	 * // asks to see if a replay is wanted
	 * }
	 */

	public boolean process(Coordinate selectedCoord, Coordinate destinationCoord) {
		String displayResult = "";
		Player currentPlayer;
		System.out.println(result);
                if (result.equals("Continue")) {
                    if (whiteTurn)
                            currentPlayer = board.getWhitePlayer();
                    else
                            currentPlayer = board.getBlackPlayer();

                    if (!makeMove(currentPlayer, selectedCoord, destinationCoord)) {
                            gameInterface.displayResult1("Not Valid" );
                            return false;
                    } else {
                            gameInterface.displayResult1("");
                    }
                    whiteTurn = !whiteTurn;
                    result = checkGameOver();
                    if (result.equals("Continue")) {
                        if (whiteTurn)
                        	displayResult = "White Turn";
                        else
                        	displayResult = "Black Turn";
                    } else {
                        displayResult = result;
                    }
                    gameInterface.displayResult0(displayResult);
                    return true;
                }
                gameInterface.displayResult0(result);
		return false;
	}

	public boolean makeMove(Player currentPlayer, Coordinate selectedCoord, Coordinate destinationCoord) {

		Piece selectedPiece = board.getPieceAtCoord(selectedCoord);
		System.out.println(selectedPiece);
		if (selectedPiece == null || !currentPlayer.getColor().equals(selectedPiece.getColor())) 
			return false;
		Piece cappedPiece;
		if (selectedCoord.equals(destinationCoord))
			return false;
		boolean capture = false;
		if (!(selectedPiece.testMove(board, destinationCoord))) {
                        gameInterface.displayResult1("Not Valid" );
			return false;
                } else {
                        gameInterface.displayResult1("");  
                }
                if (ChessGame.testCheck(board, destinationCoord, selectedCoord, whiteTurn))
                        return false;

		/*if (selectedPiece instanceof King) {
			kingTest((King) selectedPiece, destinationCoord);
		} */
		if ((selectedPiece instanceof Rook) && (!((Rook) selectedPiece).hasMoved())) {
			((Rook) selectedPiece).setHasMoved(true);
		}
		cappedPiece = board.replace(destinationCoord, selectedPiece.getCoord());
		if (cappedPiece != null) {
			if (currentPlayer.getColor() == Color.WHITE) {
				getBlackPlayer().removePiece(cappedPiece);
				capture = true;
			} else {
				getWhitePlayer().removePiece(cappedPiece);
				capture = false;
			}
		}
		if(selectedPiece instanceof Pawn && !((Pawn) selectedPiece).hasMoved()) {
			((Pawn) selectedPiece).setHasMoved(true);
		}
		if ((selectedPiece instanceof Pawn) && ((Pawn) selectedPiece).promoteCheck()) {
			String pieceName = getPieceName();
			promotePawn(selectedPiece, pieceName);
		}
		
		currentPlayer.addMove(selectedPiece + destinationCoord.getNotation());
		if (currentPlayer.getColor() == Color.BLACK)
			moves++;
		if ((selectedPiece instanceof Pawn) || capture)
			movesWithoutAgress = 0;
		else
			movesWithoutAgress++;

		board.getLocAt(selectedCoord).setPiece(null);
		board.getLocAt(destinationCoord).setPiece(selectedPiece);
		selectedPiece.setCoord(destinationCoord);
		
		
		return true;
	}

	private void kingTest(King selectedPiece, Coordinate destinationCoord) {
		Piece rook;
		Coordinate nextDest;
		int num;
		if (selectedPiece.getColor() == Color.WHITE)
			num = 7;
		else
			num = 0;
		if (!selectedPiece.hasMoved()) {
			if (destinationCoord.equals(new Coordinate(num, 6))) {
				rook = board.getPieceAtCoord(new Coordinate(num, 7));
				nextDest = new Coordinate(num, 5);
				castleHelper(rook, nextDest);
			} else if (destinationCoord.equals(new Coordinate(num, 2))) {
				rook = board.getPieceAtCoord(new Coordinate(num, 0));
				nextDest = new Coordinate(num, 3);
				castleHelper(rook, nextDest);
			}
		}
		((King) selectedPiece).setHasMoved(true);
	}

	private void gameSetup(Player playerOne, Player playerTwo) {
		if (oneGoesFirst()) {
			white = playerOne;
			black = playerTwo;
		} else {
			white = playerTwo;
			black = playerOne;
		}
		board = new Board(white, black);
		whiteTurn = true;
		moves = 0;
		movesWithoutAgress = 0;
	}

	private String getPieceName() {
		// asks what piece the player wants
		// TODO finish this
		return "";
	}

	private boolean oneGoesFirst() {
		// asks player one if he/she wantsto go first, returns true if yes,
		// false if not.
		// TODO finish this
		return false;
	}

	private void castleHelper(Piece rook, Coordinate nextDest) {
		board.getLocAt(rook.getCoord()).setPiece(null);
		board.getLocAt(nextDest).setPiece(rook);
		rook.setCoord(nextDest);
		((Rook) rook).setHasMoved(true);
	}

	private String checkGameOver() {
		if (checkDraw())
			return "Draw";
		if (checkWin())
			return winningPlayer().getName() + " wins!";
		return "Continue";
	}

	private boolean checkWin() {
		Player white = getWhitePlayer();
		Player black = getBlackPlayer();
		if (whiteTurn && white.getKing().isInCheck(board, getBlackPlayer()))
			return checkMate(white);
		else if (!whiteTurn && black.getKing().isInCheck(board, getWhitePlayer()))
			return checkMate(black);
		return false; 
	}

	private boolean checkMate(Player currentPlayer) {
		ArrayList<Piece> pieces = currentPlayer.getPieces();
		for (Piece piece : pieces) {
			if (piece.hasMove(board,(King) currentPlayer.getKing(), whiteTurn))
				return false;
		}
		return true;
	}

	private boolean checkDraw() {
		// TODO fix this
		return false;
		/*
		 * if (fiftyMoveRule())
		 * return true;
		 * if (unwinnableGame())
		 * return true;
		 * // TODO finish this
		 * // if (stalemate(turn))
		 * // return true;
		 * if (threeMoveRule())
		 * return true;
		 * return false;
		 */
	}

	private boolean fiftyMoveRule() {
		return movesWithoutAgress == 50;
	}

	private boolean unwinnableGame() {
		return (!matingMater(board.getWhitePlayer()) && !matingMater(board.getBlackPlayer()));
	}

	private boolean matingMater(Player player) {
		Color squareColor;
		int bishopWhiteCount = 0;
		int bishopBlackCount = 0;
		int bishopSum;
		int knightCount = 0;
		ArrayList<Piece> pieces = player.getPieces();
		for (Piece piece : pieces) {
			if (piece instanceof Queen)
				return true;
			if (piece instanceof Rook)
				return true;
			if (piece instanceof Pawn)
				return true;
			if (piece instanceof Bishop) {
				squareColor = board.getLocAt(piece.getCoord()).getColor();
				if (squareColor == Color.WHITE)
					bishopWhiteCount++;
				else
					bishopBlackCount++;
			}
			if (piece instanceof Knight)
				knightCount++;
		}
		bishopSum = bishopWhiteCount + bishopBlackCount;
		if (bishopSum == 0)
			return knightCount >= 3;
		if ((bishopSum >= 1) && (knightCount >= 1))
			return true;
		return (bishopWhiteCount >= 1) && (bishopBlackCount >= 1);
	}

	public boolean stalemate(Color color) {
		ArrayList<Piece> pieces;
		if (color == Color.WHITE)
			pieces = board.getWhitePlayer().getPieces();
		else
			pieces = board.getBlackPlayer().getPieces();
		for (Piece piece : pieces) {
			if (piece.hasMove(board, getWhitePlayer().getKing(), whiteTurn))
				return false;
		}
		return true;
	}

	public boolean threeMoveRule() {
		ArrayList<String> whiteMoves = board.getWhitePlayer().getMoves();
		ArrayList<String> blackMoves = board.getBlackPlayer().getMoves();
		if ((whiteMoves.size() < 3) && (blackMoves.size() < 3))
			return false;
		return testLastMoves(whiteMoves) && testLastMoves(blackMoves);
	}

	private boolean testLastMoves(ArrayList<String> moves) {
		String move = moves.get(moves.size() - 3);
		for (int i = 2; i > 0; i--)
			if (!move.equals(moves.get(moves.size() - i)))
				return false;
		return true;
	}

	public static boolean testCheck(Board otherBoard, Coordinate to, Coordinate from, boolean turn) {
		Piece tempPiece = otherBoard.replace(to, from);
		Player player;
                Player oppPlayer;
		King king;
		boolean inCheck = false;
		if (turn) {
			player = otherBoard.getWhitePlayer();
                        oppPlayer = otherBoard.getBlackPlayer();
                } else {
			player = otherBoard.getBlackPlayer();
                        oppPlayer = otherBoard.getWhitePlayer();
                }
		king = player.getKing();
		if (tempPiece != null) {
                    oppPlayer.removePiece(tempPiece);
                }
		if (king.isInCheck(otherBoard, oppPlayer)) {
			inCheck = true;
		}
                otherBoard.getLocAt(to).getPiece().setCoord(from);
		otherBoard.getLocAt(from).setPiece(otherBoard.getLocAt(to).getPiece());
		otherBoard.getLocAt(to).setPiece(tempPiece);
                
                if (tempPiece != null)
                    oppPlayer.addPiece(tempPiece);
		return inCheck;
	}

	public void promotePawn(Piece piece, String pieceName) {
		Piece newPiece;
		newPiece = promotedPiece(piece.getCoord(), piece.getColor(), pieceName);
		while (newPiece == null)
			newPiece = promotedPiece(piece.getCoord(), piece.getColor(), pieceName);
		board.getLocAt(piece.getCoord()).setPiece(newPiece);
		if (piece.getColor() == Color.WHITE)
			board.getWhitePlayer().addPiece(newPiece);
		else
			board.getBlackPlayer().addPiece(newPiece);
	}

	public Piece promotedPiece(Coordinate coord, Color color, String piece) {
		if (piece.equals("Queen"))
			return new Queen(color, coord);
		if (piece.equals("Rook"))
			return new Rook(color, coord);
		if (piece.equals("Knight"))
			return new Knight(color, coord);
		if (piece.equals("Bishop"))
			return new Bishop(color, coord);
		return null;
	}

	public Player winningPlayer() {
		if (whiteTurn)
			return board.getBlackPlayer();
		else
			return board.getWhitePlayer();
	}

	public Player getWhitePlayer() {
		return board.getWhitePlayer();
	}

	public Player getBlackPlayer() {
		return board.getBlackPlayer();
	}

	public Board getBoard() {
		return board;
	}

}
