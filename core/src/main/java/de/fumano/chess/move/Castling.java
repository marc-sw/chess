package de.fumano.chess.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Rook;

import java.util.Objects;

public class Castling implements Move {

    public final King king;
    public final Rook rook;

    public Castling(King king, Rook rook) {
        this.king = king;
        this.rook = rook;
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
        int y = king.getSpot().y;
        chessGame.getBoard().unsetSpot(king.getSpot());
        chessGame.getBoard().unsetSpot(rook.getSpot());

        if (rook.getSpot().x == 0) {
            chessGame.getBoard().updatePiece(king, new Vector2(2, y));
            chessGame.getBoard().updatePiece(rook, new Vector2(3, y));
        } else {
            chessGame.getBoard().updatePiece(king, new Vector2(6, y));
            chessGame.getBoard().updatePiece(rook, new Vector2(5, y));
        }
        chessGame.switchColor();
    }

    @Override
    public void undo(ChessGame chessGame) {
        king.setMoved(false);
        rook.setMoved(false);
        int y = king.getSpot().y;
        chessGame.getBoard().unsetSpot(king.getSpot());
        chessGame.getBoard().unsetSpot(rook.getSpot());
        chessGame.getBoard().updatePiece(king, new Vector2(4, y));

        if (king.getSpot().x == 2) {
            chessGame.getBoard().updatePiece(rook, new Vector2(0, y));
        } else {
            chessGame.getBoard().updatePiece(rook, new Vector2(7, y));
        }

        chessGame.switchColor();
    }

    @Override
    public Vector2 getDestination() {
        return rook.getSpot();
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
