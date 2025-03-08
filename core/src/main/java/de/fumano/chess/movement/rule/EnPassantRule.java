package de.fumano.chess.movement.rule;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.EnPassant;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.Pawn;
import de.fumano.chess.piece.Piece;

import java.util.List;

public class EnPassantRule implements Rule {

    @Override
    public void apply(Piece actor, Vector2 destination, ChessGame context, List<Move> moves) {
        if (!(actor instanceof Pawn pawn) || !(pawn.onEnPassantLine())) {
            return;
        }
        if (Math.abs(destination.x - actor.getSpot().x) != 1 || pawn.getSpot().y + pawn.getDirection() != destination.y) {
            return;
        }
        Piece target = context.getBoard().getPieceAt(destination.x, pawn.getSpot().y);
        if (target instanceof Pawn targetPawn && context.didPawnDoubleMoveLastTurn(targetPawn)) {
            moves.add(new EnPassant(pawn, targetPawn));
        }
    }
}
