package de.fumano.chess;

import de.fumano.chess.piece.*;

import java.util.function.Consumer;

public class Board {

    public static final int SIZE = 8;

    private final Piece[][] tiles;
    private final King whiteKing;
    private final King blackKing;

    public Board() {
        this.tiles = new Piece[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            this.update(new Pawn(new Vector2(i, 1), Color.WHITE));
            this.update(new Pawn(new Vector2(i, 6), Color.BLACK));
        }

        this.whiteKing = new King(new Vector2(4, 0), Color.WHITE);
        this.blackKing = new King(new Vector2(4,7), Color.BLACK);
        this.update(whiteKing);
        this.update(blackKing);

        this.update(new Rook(new Vector2(0, 0), Color.WHITE));
        this.update(new Knight(new Vector2(1, 0), Color.WHITE));
        this.update(new Bishop(new Vector2(2, 0), Color.WHITE));
        this.update(new Queen(new Vector2(3, 0), Color.WHITE));
        this.update(new Bishop(new Vector2(5,0 ), Color.WHITE));
        this.update(new Knight(new Vector2(6, 0), Color.WHITE));
        this.update(new Rook(new Vector2(7, 0), Color.WHITE));

        this.update(new Rook(new Vector2(0, 7), Color.BLACK));
        this.update(new Knight(new Vector2(1, 7), Color.BLACK));
        this.update(new Bishop(new Vector2(2, 7), Color.BLACK));
        this.update(new Queen(new Vector2(3, 7), Color.BLACK));
        this.update(new Bishop(new Vector2(5, 7), Color.BLACK));
        this.update(new Knight(new Vector2(6, 7), Color.BLACK));
        this.update(new Rook(new Vector2(7, 7), Color.BLACK));
    }

    public static boolean isWithin(int x, int y) {
        return x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE;
    }

    public static boolean isWithin(Vector2 spot) {
        return Board.isWithin(spot.x, spot.y);
    }

    private Piece get(int x, int y) {
        return this.tiles[y][x];
    }

    private void update(Piece piece) {
        this.tiles[piece.getSpot().y][piece.getSpot().x] = piece;
    }

    public Piece getPieceAt(int x, int y) {
        if (!Board.isWithin(x, y)) {
            throw new ChessException("illegal spot (%d, %d) not within board range (0-%d, 0-%d)"
                .formatted(x, y, Board.SIZE - 1, Board.SIZE - 1));
        }
        return this.get(x, y);
    }

    public Piece getPieceAt(Vector2 spot) {
        return this.getPieceAt(spot.x, spot.y);
    }

    public void updatePiece(Piece piece) {
        if (!Board.isWithin(piece.getSpot())) {
            throw new ChessException("illegal spot (%d, %d) not within board range (0-%d, 0-%d)"
                .formatted(piece.getSpot().x, piece.getSpot().y, Board.SIZE - 1, Board.SIZE - 1));
        }
        this.update(piece);
    }

    public void updatePiece(Piece piece, Vector2 spot) {
        piece.setSpot(spot);
        updatePiece(piece);
    }

    public void unsetSpot(int x, int y) {
        if (!Board.isWithin(x, y)) {
            throw new ChessException("illegal spot (%d, %d) not within board range (0-%d, 0-%d)"
                .formatted(x, y, Board.SIZE - 1, Board.SIZE - 1));
        }
        this.tiles[y][x] = null;
    }

    public void unsetSpot(Vector2 spot) {
        this.unsetSpot(spot.x ,spot.y);
    }

    public void iterate(Consumer<Piece> callback) {
        Piece piece;
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                piece = this.get(x, y);
                if (piece != null) {
                    callback.accept(this.get(x, y));
                }
            }
        }
    }

    public King getWhiteKing() {
        return whiteKing;
    }

    public King getBlackKing() {
        return blackKing;
    }
}
