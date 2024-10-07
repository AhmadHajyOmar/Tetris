package tetris.game;

import tetris.game.pieces.Piece;
import tetris.game.pieces.PieceFactory;
import tetris.game.pieces.PieceFactoryImplementation;
import tetris.game.pieces.PieceImplementation;

import java.util.Random;

public class TetrisGameImplementation implements TetrisGame {

    private Piece currentP;
    private Piece nextP;
    private Board board;
    private PieceFactory PFactory;
    private int completedRows;
    private int PColumn;
    private int PRow;
    private long Points;
    private int GameOver;
    private GameObserver[] observer;

    public TetrisGameImplementation(PieceFactory PFactory, Board board){
        super();
        this.PFactory = PFactory;
        this.board = board;
        nextP = PFactory.getNextRandomPiece();
        completedRows = 0;
        Points = 0;
        GameOver = 0;
        observer = new GameObserver[0];
    }

    @Override
    public Piece getCurrentPiece() {
        return currentP;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Piece getNextPiece() {
        return nextP;
    }

    @Override
    public int getNumberOfCompletedRows() {
        return completedRows;
    }

    @Override
    public int getPieceColumn() {
        return PColumn;
    }

    @Override
    public int getPieceRow() {
        return PRow;
    }

    public void setPieceRow(int PRow) {
        this.PRow = PRow;
    }

    public void setPieceColumn(int PColumn) {
        this.PColumn = PColumn;
    }

    @Override
    public long getPoints() {
        return Points;
    }

    @Override
    public boolean isGameOver() {
        if(board.canAddPiece(nextP, 2, board.getNumberOfColumns()/2) == false) return true;
        return false;
    }

    @Override
    public boolean moveDown() {
        if(!board.canRemovePiece(currentP, getPieceRow(), getPieceColumn())) return false;
        board.removePiece(currentP, getPieceRow(), getPieceColumn());
        if(!board.canAddPiece(currentP, getPieceRow()+1, getPieceColumn())) {
            board.addPiece(currentP, getPieceRow(), getPieceColumn());
            return false;
        }
        board.addPiece(currentP, getPieceRow()+1, getPieceColumn());
        setPieceRow(PRow + 1);
        for(int i = 0; i < observer.length; i++){
            observer[i].piecePositionChanged();
        }
        return true;
    }

    @Override
    public boolean moveLeft() {
        if(!board.canRemovePiece(currentP, getPieceRow(), getPieceColumn())) return false;
        board.removePiece(currentP, getPieceRow(), getPieceColumn());
        if(!board.canAddPiece(currentP, getPieceRow(), getPieceColumn()-1)) {
            board.addPiece(currentP, getPieceRow(), getPieceColumn());
            return false;
        }
        board.addPiece(currentP, getPieceRow(), getPieceColumn()-1);
        setPieceColumn(PColumn - 1);
        for(int i = 0; i < observer.length; i++){
            observer[i].piecePositionChanged();
        }
        return true;
    }

    @Override
    public boolean moveRight() {
        if(!board.canRemovePiece(currentP, getPieceRow(), getPieceColumn())) return false;
        board.removePiece(currentP, getPieceRow(), getPieceColumn());
        if(!board.canAddPiece(currentP, getPieceRow(), getPieceColumn()+1)) {
            board.addPiece(currentP, getPieceRow(), getPieceColumn());
            return false;
        }
        board.addPiece(currentP, getPieceRow(), getPieceColumn()+1);
        setPieceColumn(PColumn + 1);
        for(int i = 0; i < observer.length; i++){
            observer[i].piecePositionChanged();
        }
        return true;
    }

    @Override
    public boolean newPiece() {
        if(isGameOver()) return false;
        currentP = nextP;
        PRow = 2;
        PColumn = board.getNumberOfColumns()/2;
        nextP = PFactory.getNextRandomPiece();
        if(!board.canAddPiece(currentP, PRow, PColumn)) return false;
        board.addPiece(currentP, PRow, PColumn);
        int RR = board.deleteCompleteRows();
        completedRows =+ RR;
        Points =+ RR == 0 ? Points : (RR == 1 ? 100 : (RR == 2 ? 300 : (RR == 3 ? 500 : (RR == 4 ? 1000 : 666))));
        if(RR != 0) {
            for(int i = 0; i < observer.length; i++){
                observer[i].rowsCompleted();
            }
        }
        return true;
    }

    @Override
    public boolean rotatePieceClockwise() {
        Piece RotatedP = currentP.getClockwiseRotation();
        if(!board.canRemovePiece(currentP, getPieceRow(), getPieceColumn())) return false;
        board.removePiece(currentP, getPieceRow(), getPieceColumn());
        if(!board.canAddPiece(RotatedP, getPieceRow(), getPieceColumn())) {
            board.addPiece(currentP, getPieceRow(), getPieceColumn());
            return false;
        }
        board.addPiece(RotatedP, getPieceRow(), getPieceColumn());
        currentP = RotatedP;
        for(int i = 0; i < observer.length; i++){
            observer[i].piecePositionChanged();
        }
        return true;
    }

    @Override
    public boolean rotatePieceCounterClockwise() {
        Piece RotatedP = currentP.getCounterClockwiseRotation();
        if(!board.canRemovePiece(currentP, getPieceRow(), getPieceColumn())) return false;
        board.removePiece(currentP, getPieceRow(), getPieceColumn());
        if(!board.canAddPiece(RotatedP, getPieceRow(), getPieceColumn())) {
            board.addPiece(currentP, getPieceRow(), getPieceColumn());
            ;
            return false;
        }
        board.addPiece(RotatedP, getPieceRow(), getPieceColumn());
        currentP = RotatedP;
        for(int i = 0; i < observer.length; i++){
            observer[i].piecePositionChanged();
        }
        return true;
    }

    @Override
    public void setGameOver() {
        GameOver = 0;
        for(int i = 0; i < observer.length; i++){
            observer[i].gameOver();
        }
    }

    @Override
    public void step() {
        if(currentP == null) newPiece();
        else {if(moveDown()) ; else{
            for(int i = 0; i < observer.length; i++){
                observer[i].pieceLanded();
            }
            if(newPiece()) ; else setGameOver();}
        }
    }

    @Override
    public void addObserver(GameObserver observer) {
        GameObserver[] newList = new GameObserver[this.observer.length+1];
        for(int i = 0; i < this.observer.length; i++){
            newList[i] = this.observer[i];
        }
        newList[this.observer.length] = observer;
        this.observer = newList;
    }

    @Override
    public void removeObserver(GameObserver observer) {
        GameObserver[] newList = new GameObserver[this.observer.length-1];
        boolean flag = true;
        for(int i = 0; i < this.observer.length; i++){
            if(!this.observer[i].equals(observer) && flag){
                newList[i] = this.observer[i];
            } else {
                newList[i] = this.observer[i+1];
                flag = false;
            }
        }
        this.observer = newList;
    }
}
