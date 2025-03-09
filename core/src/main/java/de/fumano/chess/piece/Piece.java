package de.fumano.chess.piece;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Color;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected boolean moved;
    protected Vector2 spot;
    protected final Color color;
    private final List<Move> legalMoves;

    public Piece(Vector2 spot, Color color) {
        this.spot = spot;
        this.legalMoves = new ArrayList<>();
        this.moved = false;
        this.color = color;
    }

    public Vector2 getSpot() {
        return spot;
    }

    public void setSpot(Vector2 spot) {
        this.spot = spot;
    }

    public boolean hasMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isOpponent(Piece other) {
        return other != null && this.color != other.color;
    }

    public boolean canCaptureSpot(Vector2 spot) {
        return this.legalMoves.stream().anyMatch(move -> move.getDestination().equals(spot));
    }

    public boolean isPromotingPawn() {
        return this instanceof Pawn pawn && pawn.promotesOnMove();
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    @Override
    public String toString() {
        return color + "_" + this.getClass().getSimpleName();
    }

    public abstract List<Move> calculateAllMoves(ChessGame chessGame);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return moved == piece.moved && Objects.equals(spot, piece.spot) && color == piece.color && Objects.equals(legalMoves, piece.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moved, spot, color, legalMoves);
    }
}
