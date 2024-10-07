package tetris.game.pieces;

public class PieceImplementation implements Piece {

    private boolean[][] body;
    private int height;
    private int width;
    private Point rotationPoint;
    private PieceType type;
    private PieceImplementation[] Pieces = new PieceImplementation[4];
    public int rotations = 0;

    public PieceImplementation(){}
    public PieceImplementation(boolean[][] body, int height, int width, Point rotationPoint, PieceType type, int rotations) {
        this.body = body;
        this.height = height;
        this.width = width;
        this.rotationPoint = rotationPoint;
        this.type = type;
        this.rotations = rotations;
        if (rotations == 0 && Pieces[0] == null) Pieces[rotations] = this;
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean[][] getBody() {
        return body;
    }

    @Override
    public Piece getClockwiseRotation() {
        if(Pieces[(Pieces[0].rotations+1) % 4] != null) {
            Pieces[0].rotations = (Pieces[0].rotations+1) % 4;
            return Pieces[Pieces[0].rotations];
        }

        int height = getHeight();
        int width = getWidth();
        Point newPoint = new Point(rotationPoint.getColumn(), height - rotationPoint.getRow() - 1);
        boolean[][] newBody = new boolean[width][height];

        for(int w = 0 ; w < width; w++) {
            for (int h = 0; h < height; h++) {
                newBody[w][h] = body[height - h - 1][w];
                ;
            }
        }

        Pieces[0].rotations = (Pieces[0].rotations+1) % 4;
        PieceImplementation tmp = new PieceImplementation(newBody, width, height, newPoint, type, Pieces[0].rotations);
        Pieces[Pieces[0].rotations] = tmp;
        tmp.setPieces(Pieces);
        return tmp;
    }

    @Override
    public Piece getCounterClockwiseRotation() {
        if(Pieces[((((Pieces[0].rotations-1) % 4) + 4) % 4)] != null) {
            Pieces[0].rotations = ((((Pieces[0].rotations-1) % 4) + 4) % 4);
            return Pieces[Pieces[0].rotations];
        }

        int height = getHeight();
        int width = getWidth();
        Point newPoint = new Point(height - rotationPoint.getColumn() - 1, rotationPoint.getRow());
        boolean[][] newBody = new boolean[width][height];

        for(int w = 0 ; w < width; w++) {
            for (int h = 0; h < height; h++) {
                newBody[w][h] = body[h][width - w - 1];
            }
        }

        Pieces[0].rotations = ((((Pieces[0].rotations-1) % 4) + 4) % 4);
        PieceImplementation tmp = new PieceImplementation(newBody, width, height, newPoint, type, Pieces[0].rotations);
        Pieces[Pieces[0].rotations] = tmp;
        tmp.setPieces(Pieces);
        return tmp;
    }

    @Override
    public Point getRotationPoint() {
        return rotationPoint;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Piece clone() {
        boolean[][] body = new boolean[height - 1][width - 1];
        for(int w = 0 ; w < width; w++){
            for(int h = 0; h < height; h++){
                body[h][w] = this.body[h][w];
            }
        }

        Point rotationPoint = new Point(this.rotationPoint.getRow(),this.rotationPoint.getColumn());
        int height = this.height;
        int width = this.width;
        PieceType type = this.type;
        return new PieceImplementation(body, height, width, rotationPoint, type, Pieces[0].rotations);
    }

    public void setPieces(PieceImplementation[] Pieces){
        this.Pieces = Pieces;
    }
}
