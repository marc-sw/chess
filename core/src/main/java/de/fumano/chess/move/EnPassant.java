package de.fumano.chess.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.Pawn;

import java.util.Objects;

public class EnPassant implements Move {

    public final Vector2 destination;
    public final Vector2 capturingPawnSpot;
    public final Pawn capturingPawn;
    public final Pawn capturedPawn;

    public EnPassant(Pawn capturingPawn, Pawn capturedPawn) {
        this.capturingPawn = capturingPawn;
        this.capturedPawn = capturedPawn;
        this.capturingPawnSpot = capturingPawn.getSpot();
        this.destination = capturedPawn.getSpot().addY(capturingPawn.getDirection());
    }

    @Override
    public void execute(ChessGame chessGame) {
        chessGame.getBoard().updatePiece(capturingPawn, destination);
        chessGame.getBoard().unsetSpot(capturingPawnSpot);
        chessGame.getBoard().unsetSpot(capturedPawn.getSpot());
        chessGame.switchColor();
    }

    @Override
    public void undo(ChessGame chessGame) {
        chessGame.getBoard().unsetSpot(capturingPawn.getSpot());
        chessGame.getBoard().updatePiece(capturingPawn, capturingPawnSpot);
        chessGame.getBoard().updatePiece(capturedPawn);
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
        EnPassant enPassant = (EnPassant) o;
        return Objects.equals(destination, enPassant.destination) && Objects.equals(capturingPawnSpot, enPassant.capturingPawnSpot) && Objects.equals(capturingPawn, enPassant.capturingPawn) && Objects.equals(capturedPawn, enPassant.capturedPawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, capturingPawnSpot, capturingPawn, capturedPawn);
    }
}
