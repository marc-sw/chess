package de.fumano.chess.movement.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.piece.Rook;

import java.util.Objects;

public class Castling implements Move {

    public final Vector2 rookOrigin;
    public final Vector2 kingOrigin;
    public final Vector2 rookDestination;
    public final Vector2 kingDestination;
    public final King king;
    public final Rook rook;

    public Castling(King king, Rook rook) {
        this.king = king;
        this.rook = rook;
        this.rookOrigin = rook.getSpot();
        this.kingOrigin = king.getSpot();
        this.rookDestination = new Vector2(rook.getSpot().x == 0 ? 3: 5, king.getSpot().y);
        this.kingDestination = new Vector2(rook.getSpot().x == 0 ? 2: 6, king.getSpot().y);
    }

    /*
    Castling Move
    Origin has an unmoved King
    Destination has an unmoved Rook
    No check between them
    Swaps their positions
     */

    @Override
    public void execute(ChessGame chessGame) {
        king.setMoved(true);
        rook.setMoved(true);
        chessGame.getBoard().unsetSpot(king.getSpot());
        chessGame.getBoard().unsetSpot(rook.getSpot());
        chessGame.getBoard().updatePiece(king, kingDestination);
        chessGame.getBoard().updatePiece(rook, rookDestination);
    }

    @Override
    public void undo(ChessGame chessGame) {
        king.setMoved(false);
        rook.setMoved(false);
        chessGame.getBoard().unsetSpot(king.getSpot());
        chessGame.getBoard().unsetSpot(rook.getSpot());
        chessGame.getBoard().updatePiece(king, kingOrigin);
        chessGame.getBoard().updatePiece(rook, rookOrigin);
    }

    @Override
    public Vector2 getDestination() {
        return rookOrigin;
    }

    @Override
    public Piece getActor() {
        return king;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Castling castling = (Castling) o;
        return Objects.equals(king, castling.king) && Objects.equals(rook, castling.rook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(king, rook);
    }
}
