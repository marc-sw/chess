package de.fumano.chess.movement.rule;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Capture;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.piece.*;

import java.util.List;

public class CaptureRule implements Rule {

    @Override
    public void apply(Piece actor, Vector2 destination, ChessGame context, List<Move> moves) {
        Piece target = context.getBoard().getPieceAt(destination);
        if (actor.isOpponent(target)) {
            if (actor.isPromotingPawn()) {
                Promotion.addMoves((Pawn) actor, target, moves);
            } else {
                moves.add(new Capture(actor, target));
            }
        }
    }
}
