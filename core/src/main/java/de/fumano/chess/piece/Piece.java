package de.fumano.chess.piece;

import de.fumano.chess.Board;
import de.fumano.chess.Color;
import de.fumano.chess.Vector2;
import de.fumano.chess.move.Capture;
import de.fumano.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected boolean moved;
    protected Vector2 spot;
    protected final Color color;
    private final List<Move> allMoves;
    private final List<Move> lastMoves;


    public Piece(Vector2 spot, Color color) {
        this.spot = spot;
        this.allMoves = new ArrayList<>();
        this.lastMoves = new ArrayList<>();
        this.moved = false;
        this.color = color;
        //test asdas
    }

    public Vector2 getSpot() {
        return spot;
    }

    public void setSpot(Vector2 spot) {
        this.spot = spot;
    }

    public boolean isAtTopOrBottom() {
        return this.spot.y == 0 || this.spot.y == 7;
    }

    public boolean hasMoved() {
        return this.moved;
    }

    public boolean isKing() {
        return this instanceof King;
    }

    public boolean isPawn() {
        return this instanceof Pawn;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isOpponent(Piece other) {
        return this.color != other.color;
    }

    public void updateMoves(Board board) {
        this.allMoves.clear();
        this.allMoves.addAll(this.findAllMoves(board));
    }

    public void cacheUpdateMoves(Board board) {
        this.lastMoves.clear();
        this.lastMoves.addAll(this.allMoves);
        this.allMoves.clear();
        this.allMoves.addAll(this.findAllMoves(board));
    }

    public void revertUpdates() {
        this.allMoves.clear();
        this.allMoves.addAll(lastMoves);
    }

    public boolean canCapture(Piece piece) {
        return this.allMoves.stream().anyMatch(move -> move instanceof Capture capture && capture.capturedPiece.equals(piece));
    }

    public List<Move> getAllMoves() {
        return this.allMoves;
    }


    protected abstract List<Move> findAllMoves(Board board);

    @Override
    public String toString() {
        return color + "_" + this.getClass().getSimpleName();
    }
}
