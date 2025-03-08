package de.fumano.chess.movement.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.Piece;

import java.util.Objects;

public class Step implements Move {

    public final Piece movingPiece;
    public final Vector2 origin;
    public final Vector2 destination;
    public final boolean hasMoved;

    public Step(Piece movingPiece, Vector2 destination) {
        this.movingPiece = movingPiece;
        this.origin = movingPiece.getSpot();
        this.destination = destination;
        this.hasMoved = movingPiece.hasMoved();
    }

    /*
    Default Move
    Origin has a Piece
    Destination is not occupied
    Just moving a piece from origin to destination
     */

    public void execute(ChessGame chessGame) {
        movingPiece.setMoved(true);
        chessGame.getBoard().updatePiece(movingPiece, destination);
        chessGame.getBoard().unsetSpot(origin);
    }

    @Override
    public void undo(ChessGame chessGame) {
        this.movingPiece.setMoved(this.hasMoved);
        chessGame.getBoard().updatePiece(this.movingPiece, this.origin);
        chessGame.getBoard().unsetSpot(destination);
    }

    @Override
    public Vector2 getDestination() {
        return destination;
    }

    @Override
    public Piece getActor() {
        return movingPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return hasMoved == step.hasMoved && Objects.equals(movingPiece, step.movingPiece) && Objects.equals(origin, step.origin) && Objects.equals(destination, step.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movingPiece, origin, destination, hasMoved);
    }
}
