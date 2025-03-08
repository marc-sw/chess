package de.fumano.chess.movement.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Color;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.*;

import java.util.List;
import java.util.Objects;

public class Promotion implements Move {

    public final Vector2 destination;
    public final Piece optionalCapturedPiece;
    public final Pawn promotingPawn;
    public final Piece promotedPiece;

    public Promotion(Pawn promotingPawn, Piece promotedPiece, Piece optionalCapturedPiece) {
        this.promotingPawn = promotingPawn;
        this.promotedPiece = promotedPiece;
        this.optionalCapturedPiece = optionalCapturedPiece;
        this.destination = optionalCapturedPiece == null ?
            promotingPawn.getSpot().addY(promotingPawn.getDirection()) : optionalCapturedPiece.getSpot();
    }

    @Override
    public void execute(ChessGame chessGame) {
        chessGame.getBoard().updatePiece(promotedPiece, getDestination());
        chessGame.getBoard().unsetSpot(promotingPawn.getSpot());
    }

    @Override
    public void undo(ChessGame chessGame) {
        chessGame.getBoard().updatePiece(promotingPawn);
        if (optionalCapturedPiece == null) {
            chessGame.getBoard().unsetSpot(promotedPiece.getSpot());
        } else {
            chessGame.getBoard().updatePiece(optionalCapturedPiece);
        }
    }

    @Override
    public Vector2 getDestination() {
        return destination;
    }

    @Override
    public Piece getActor() {
        return promotingPawn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(destination, promotion.destination) && Objects.equals(optionalCapturedPiece, promotion.optionalCapturedPiece) && Objects.equals(promotingPawn, promotion.promotingPawn) && Objects.equals(promotedPiece, promotion.promotedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, optionalCapturedPiece, promotingPawn, promotedPiece);
    }

    public static void addMoves(Pawn promotingPawn, Piece optionalCapturedPiece, List<Move> moves) {
        Color color = promotingPawn.getColor();
        moves.add(new Promotion(promotingPawn, new Rook(Vector2.ZERO, color), optionalCapturedPiece));
        moves.add(new Promotion(promotingPawn, new Knight(Vector2.ZERO, color), optionalCapturedPiece));
        moves.add(new Promotion(promotingPawn, new Bishop(Vector2.ZERO, color), optionalCapturedPiece));
        moves.add(new Promotion(promotingPawn, new Queen(Vector2.ZERO, color), optionalCapturedPiece));
    }
}
