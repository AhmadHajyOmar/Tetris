package tetris.game;

import tetris.game.pieces.Piece;

public class BoardImplementation implements Board {

    private int rows;
    private int columns;
    private Piece.PieceType[][] board;

    public BoardImplementation(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new Piece.PieceType[rows][columns];
    }


    @Override
    public Piece.PieceType[][] getBoard() {
        return board;
    }

    @Override
    public int getNumberOfRows() {
        return rows;
    }

    @Override
    public int getNumberOfColumns() {
        return columns;
    }

    @Override
    public void addPiece(Piece piece, int row, int column) {
        if (canAddPiece(piece, row, column)) {
            int leftMost = column - piece.getRotationPoint().getColumn();
            int upperMost = row - piece.getRotationPoint().getRow();
            int height = piece.getHeight();
            int width = piece.getWidth();

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    if (piece.getBody()[h][w] == true) board[upperMost + h][leftMost + w] = piece.getPieceType();
                }
            }
        } else throw new IllegalArgumentException();
    }

    @Override
    public boolean canAddPiece(Piece piece, int row, int column) {
        if (piece == null) throw new IllegalArgumentException();
        int height = piece.getHeight();
        int width = piece.getWidth();
        int leftMost = column - piece.getRotationPoint().getColumn();
        int upperMost = row - piece.getRotationPoint().getRow();

        if (column < 0 || column >= getNumberOfColumns()) return false;
        if (leftMost < 0) return false;
        if ((width - 1 - piece.getRotationPoint().getColumn() + column) >= getNumberOfColumns()) return false;

        if (row < 0 || row >= getNumberOfRows()) return false;
        if (upperMost < 0) return false;
        if ((height - 1 - piece.getRotationPoint().getRow() + row) >= getNumberOfRows()) return false;

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (board[upperMost + h][leftMost + w] != null && piece.getBody()[h][w] == true) return false;
            }
        }
        return true;
    }

    @Override
    public void removePiece(Piece piece, int row, int column) {
        if (canRemovePiece(piece, row, column)) {
            int leftMost = column - piece.getRotationPoint().getColumn();
            int upperMost = row - piece.getRotationPoint().getRow();
            int height = piece.getHeight();
            int width = piece.getWidth();

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    if (piece.getBody()[h][w] == true) board[upperMost + h][leftMost + w] = null;
                }
            }
         } else throw new IllegalArgumentException();
        }

        @Override
        public boolean canRemovePiece (Piece piece,int row, int column){
            if (piece == null) throw new IllegalArgumentException();

            int leftMost = column - piece.getRotationPoint().getColumn();
            int upperMost = row - piece.getRotationPoint().getRow();
            int height = piece.getHeight();
            int width = piece.getWidth();

            if (row < 0 || row >= getNumberOfRows()) return false;
            if (upperMost < 0) return false;
            if ((height - 1 - piece.getRotationPoint().getRow() + row) >= getNumberOfRows()) return false;

            if (column < 0 || column >= getNumberOfColumns()) return false;
            if (leftMost < 0) return false;
            if ((width - 1 - piece.getRotationPoint().getColumn() + column) >= getNumberOfColumns()) return false;

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    if (board[upperMost + h][leftMost + w] != piece.getPieceType() && piece.getBody()[h][w] == true) return false;
                }
            }
            return true;
        }

        @Override
        public int deleteCompleteRows () {
            int counter = 0;
            boolean k;

            for (int r = 0; r < getNumberOfRows(); r++) {
                k = true;
                for (int c = 0; c < getNumberOfColumns(); c++) {
                    if (board[r][c] == null) {
                        k = false;
                        break;
                    }
                }
                if (k) {
                    for (int c = 0; c < getNumberOfColumns(); c++) {
                        board[r][c] = null;
                    }
                    for (int rT = r - 1; rT >= 0; rT--) {
                        for (int c = 0; c < getNumberOfColumns(); c++) {
                            board[rT + 1][c] = board[rT][c];
                            board[rT][c] = null;
                        }
                    }
                    counter++;
                }
            }
            return counter;
        }

        @Override
        public Board clone () {
            int height = getNumberOfRows();
            int width = getNumberOfColumns();
            BoardImplementation B = new BoardImplementation(getNumberOfRows(), getNumberOfColumns());
            Piece.PieceType[][] newBoard = new Piece.PieceType[height][width];

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    newBoard[h][w] = board[h][w];
                }
            }

            B.setBoard(newBoard);
            return B;
        }

        public void setBoard (Piece.PieceType[][]board){
            this.board = board;
        }
    }

