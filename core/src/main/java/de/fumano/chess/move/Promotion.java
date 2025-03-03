package de.fumano.chess.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.Pawn;
import de.fumano.chess.piece.Piece;

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

    public Promotion(Pawn promotingPawn, Piece promotedPiece) {
        this(promotingPawn, promotedPiece, null);
    }

    @Override
    public void execute(ChessGame chessGame) {
        chessGame.getBoard().updatePiece(promotedPiece, getDestination());
        chessGame.getBoard().unsetSpot(promotingPawn.getSpot());
        chessGame.switchColor();
    }

    @Override
    public void undo(ChessGame chessGame) {
        chessGame.getBoard().updatePiece(promotingPawn);
        if (optionalCapturedPiece == null) {
            chessGame.getBoard().unsetSpot(promotedPiece.getSpot());
        } else {
            chessGame.getBoard().updatePiece(optionalCapturedPiece);
        }
        chessGame.switchColor();
    }

    @Override
    public Vector2 getDestination() {
        return destination;
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
}
