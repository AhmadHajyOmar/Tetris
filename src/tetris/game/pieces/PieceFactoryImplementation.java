package tetris.game.pieces;

import java.util.Random;

public class PieceFactoryImplementation implements PieceFactory {

    private Random r;

    public PieceFactoryImplementation(){}
    public PieceFactoryImplementation(Random r){
        this.r = r;
    }

    @Override
    public Piece getIPiece() {
        boolean[][] body = {{true},{true},{true},{true}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.I;
        PieceImplementation I = new PieceImplementation(body, body.length,body[0].length, new Point(1,0), type, 0);
        return I;
    }

    @Override
    public Piece getJPiece() {
        boolean[][] body = {{false, true}, {false, true}, {true, true}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.J;
        PieceImplementation J = new PieceImplementation(body, body.length,body[0].length, new Point(1,1), type, 0);
        return J;
    }

    @Override
    public Piece getLPiece() {
        boolean[][] body = {{true, false}, {true, false}, {true, true}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.L;
        PieceImplementation L = new PieceImplementation(body, body.length,body[0].length, new Point(1,0), type, 0);
        return L;
    }

    @Override
    public Piece getOPiece() {
        boolean[][] body = {{true, true}, {true, true}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.O;
        PieceImplementation O = new PieceImplementation(body, body.length,body[0].length, new Point(1,1), type, 0);
        return O;
    }

    @Override
    public Piece getSPiece() {
        boolean[][] body = {{false, true, true}, {true, true, false}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.S;
        PieceImplementation S = new PieceImplementation(body, body.length,body[0].length, new Point(1,1), type, 0);
        return S;
    }

    @Override
    public Piece getZPiece() {
        boolean[][] body = {{true, true, false}, {false, true, true}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.Z;
        PieceImplementation Z = new PieceImplementation(body, body.length,body[0].length, new Point(1,1), type, 0);
        return Z;
    }

    @Override
    public Piece getTPiece() {
        boolean[][] body = {{true, true, true}, {false, true, false}};
        PieceImplementation.PieceType type = PieceImplementation.PieceType.T;
        PieceImplementation T = new PieceImplementation(body, body.length,body[0].length, new Point(0,1), type, 0);
        return T;
    }

    @Override
    public Piece getNextRandomPiece() {
        int n = r.nextInt(7);
        if(n == 0) return getIPiece();
        if(n == 1) return getJPiece();
        if(n == 2) return getZPiece();
        if(n == 3) return getOPiece();
        if(n == 4) return getSPiece();
        if(n == 5) return getTPiece();
        if(n == 6) return getLPiece();
        return null;
    }
}

