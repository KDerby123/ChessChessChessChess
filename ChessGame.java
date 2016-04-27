
//A single game of chess
import java.util.ArrayList;

public class ChessGame {
	private Board board;
	private int movesWithoutAgress;
	private int moves;
	private boolean whiteTurn;
	Player white, black;

	ChessGameInterface gameInterface;

	public ChessGame(Player playerOne, Player playerTwo, ChessGameInterface gameInterface) {
		gameSetup(playerOne, playerTwo);
		this.gameInterface = gameInterface;
	}

	/*
	 * public boolean replay() {
	 * // asks to see if a replay is wanted
	 * }
	 */

	public boolean process(Coordinate selectedCoord, Coordinate destinationCoord) {
		String result = "";
		Player currentPlayer;
		if (whiteTurn)
			currentPlayer = board.getWhitePlayer();
		else
			currentPlayer = board.getBlackPlayer();

		if (!makeMove(currentPlayer, selectedCoord, destinationCoord))
			return false;

		result = checkGameOver();
		whiteTurn = !whiteTurn;
		if (whiteTurn)
			result = "White Turn";
		else
			result = "Black Turn";
		gameInterface.displayResult(result);
		return true;
	}

	public boolean makeMove(Player currentPlayer, Coordinate selectedCoord, Coordinate destinationCoord) {

		Piece selectedPiece = board.getPieceAtCoord(selectedCoord);
		System.out.println(selectedPiece);
		if (selectedPiece == null || !currentPlayer.getColor().equals(selectedPiece.getColor())) {
			gameInterface.displayResult("Not Valid");
			return false;
		}

		Piece cappedPiece;
		if (selectedCoord.equals(destinationCoord))
			return false;
		boolean capture = false;

		// capture = (board.getLocAt(destinationCoord).getPiece() != null);
		if (!(selectedPiece.testMove(board, destinationCoord)))
			return false;
		if (testCheck(board, destinationCoord, selectedCoord, currentPlayer))
			return false;

		if (selectedPiece instanceof King) {
			kingTest(selectedPiece, destinationCoord);
		}
		if (selectedPiece instanceof Rook) {
			selectedPiece.setHasMoved(true);
		}
		cappedPiece = board.replace(selectedPiece.getCoord(), destinationCoord);
		if (cappedPiece != null) {
			if (currentPlayer.getColor() == Color.WHITE) {
				getBlackPlayer().removePiece(cappedPiece);
				capture = true;
			} else {
				getWhitePlayer().removePiece(cappedPiece);
				capture = false;
			}
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

	private void kingTest(Piece selectedPiece, Coordinate destinationCoord) {
		Rook rook;
		Coordinate nextDest;
		int num;
		if (selectedPiece.getColor() == Color.WHITE)
			num = 0;
		else
			num = 7;
		if (!selectedPiece.hasMoved()) {
			if (destinationCoord.equals(new Coordinate(num, 6))) {
				rook = (Rook) board.getPieceAtCoord(new Coordinate(num, 7));
				nextDest = new Coordinate(num, 5);
				castleHelper(rook, nextDest);
			} else if (destinationCoord.equals(new Coordinate(num, 3))) {
				rook = (Rook) board.getPieceAtCoord(new Coordinate(num, 1));
				nextDest = new Coordinate(num, 4);
				castleHelper(rook, nextDest);
			}
		}
		selectedPiece.setHasMoved(true);
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

	private void castleHelper(Rook rook, Coordinate nextDest) {
		board.replace(rook.getCoord(), nextDest);
		rook.setHasMoved();
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
			return checkMate(white, black);
		else if (!whiteTurn && black.getKing().isInCheck(board, getWhitePlayer()))
			return checkMate(black, white);
		// what the hell do we return by defualt
		return false; // i put this here so it would compile
	}

	private boolean checkMate(Player currentPlayer, Player oppPlayer) {
		ArrayList<Piece> pieces = currentPlayer.getPieces();
		for (Piece piece : pieces) {
			if (piece.hasMove(board, currentPlayer.getKing(), oppPlayer))
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
		// YOU CANT START A METHOD NAME WITH A NUMBER
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
			if (piece.hasMove(board, getWhitePlayer().getKing(), getBlackPlayer()))
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

	// Still needs to remove piece, and ignore the piece taken
	public static boolean testCheck(Board board, Coordinate to, Coordinate from, Player oppPlayer) {
		Piece tempPiece = board.replace(from, to);
		Player player;
		King king;
		boolean inCheck = false;
		if (oppPlayer.getColor() == Color.WHITE)
			player = board.getBlackPlayer();
		else
			player = board.getWhitePlayer();

		king = player.getKing();
		if (tempPiece != null)
			player.removePiece(tempPiece);
		if (king.isInCheck(board, oppPlayer)) {
			inCheck = true;
		}
		board.getLocAt(from).setPiece(board.getLocAt(to).getPiece());
		board.getLocAt(to).setPiece(tempPiece);
		player.addPiece(tempPiece);
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
