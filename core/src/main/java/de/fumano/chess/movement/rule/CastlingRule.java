package de.fumano.chess.movement.rule;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Castling;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.piece.Rook;

import java.util.List;

public class CastlingRule implements Rule {

    @Override
    public void apply(Piece actor, Vector2 destination, ChessGame context, List<Move> moves) {
        if (!(actor instanceof King) || actor.hasMoved()) {
            return;
        }
        Piece target = context.getBoard().getPieceAt(destination);
        if (!(target instanceof Rook)|| target.hasMoved() || actor.isOpponent(target)) {
            return;
        }

        int horizontalDirection = destination.x - actor.getSpot().x;
        horizontalDirection /= Math.abs(horizontalDirection);
        for (int i = actor.getSpot().x + horizontalDirection; i != destination.x; i += horizontalDirection) {
            if (context.getBoard().isOccupied(i, actor.getSpot().y) ||
                context.getOpponentPlayer().canCapture(new Vector2(i, actor.getSpot().y))) {
                return;
            }

        }
        moves.add(new Castling((King) actor, (Rook) target));
    }
}
