package tetris.game;

import java.util.Random;

import tetris.autoplay.AutoPlayer;
import tetris.game.pieces.PieceFactory;
import tetris.game.pieces.PieceFactoryImplementation;

public class MyTetrisFactory {

	/**
	 * The default number of rows.
	 */
	public static final int DEFAULT_ROWS = 20;

	/**
	 * The default number of columns.
	 */
	public static final int DEFAULT_COLUMNS = 10;

	/**
	 * Creates a new TetrisGame. It initializes the used PieceFactory with the
	 * given Random number generator. The size should be DEFAULT_ROWS x
	 * DEFAULT_COLUMNS.
	 *
	 * @param r
	 *            the random number generator to use for the PieceFactory.
	 */
	static public TetrisGame createTetrisGame(Random r) {
		// TODO Complete implementation
		TetrisGame newGame = new TetrisGameImplementation(createPieceFactory(r), createBoard(DEFAULT_ROWS, DEFAULT_COLUMNS));
		return newGame;
	}

	/**
	 * Create a new PieceFactory that can generate new pieces.
	 *
	 * @param r
	 *            the random number generator to use for the PieceFactory.
	 */
	static public PieceFactory createPieceFactory(Random r) {
		// TODO Complete implementation
		PieceFactory newPiece = new PieceFactoryImplementation(r);
		return newPiece;
	}

	/**
	 * Creates a new Board with given rows and columns.
	 *
	 * @param rows
	 *            the number of rows
	 * @param columns
	 *            the number of columns
	 */
	static public Board createBoard(int rows, int columns) {
		// TODO Complete implementation
		if(rows < 0 || columns < 0) throw new UnsupportedOperationException();
		return new BoardImplementation(rows, columns);
	}

	/**
	 * The AutoPlayer provides moves for a tetris game.
	 *
	 * @param game
	 *            the game for which the AutoPlayer should provide moves
	 */
	static public AutoPlayer createAutoPlayer(TetrisGameView game) {
		// TODO Complete implementation
		throw new UnsupportedOperationException();
	}
}
