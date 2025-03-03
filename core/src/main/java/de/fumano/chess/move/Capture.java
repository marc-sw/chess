package de.fumano.chess.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.state.PromotionState;

import java.util.Objects;

public class Capture implements Move {

    public final Vector2 capturingPieceSpot;
    public final boolean capturingPieceMoved;
    public final Piece capturingPiece;
    public final Piece capturedPiece;

    public Capture(Piece capturingPiece, Piece capturedPiece) {
        this.capturingPiece = capturingPiece;
        this.capturedPiece = capturedPiece;
        this.capturingPieceMoved = capturingPiece.hasMoved();
        this.capturingPieceSpot = capturingPiece.getSpot();
    }

    /*
    Capture Move
    Origin has a Piece
    Destination has a Piece
    Origin Piece goes to Destination
    Destination Piece gets captured
     */

    @Override
    public void execute(ChessGame chessGame) {
        capturingPiece.setMoved(true);
        chessGame.getBoard().updatePiece(capturingPiece, capturedPiece.getSpot());
        chessGame.getBoard().unsetSpot(this.capturingPieceSpot);
        chessGame.switchColor();
    }

    @Override
    public void undo(ChessGame chessGame) {
        this.capturingPiece.setMoved(this.capturingPieceMoved);
        chessGame.getBoard().updatePiece(capturingPiece, this.capturingPieceSpot);
        chessGame.getBoard().updatePiece(capturedPiece);
        chessGame.switchColor();
    }

    @Override
    public Vector2 getDestination() {
        return capturedPiece.getSpot();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capture capture = (Capture) o;
        return capturingPieceMoved == capture.capturingPieceMoved && Objects.equals(capturingPieceSpot, capture.capturingPieceSpot) && Objects.equals(capturingPiece, capture.capturingPiece) && Objects.equals(capturedPiece, capture.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capturingPieceSpot, capturingPieceMoved, capturingPiece, capturedPiece);
    }
}
