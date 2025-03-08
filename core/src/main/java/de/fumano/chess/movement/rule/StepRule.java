package de.fumano.chess.movement.rule;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.movement.move.Step;
import de.fumano.chess.piece.*;

import java.util.List;

public class StepRule implements Rule {

    @Override
    public void apply(Piece actor, Vector2 destination, ChessGame context, List<Move> moves) {
        if (context.getBoard().getPieceAt(destination) == null) {
            if (actor.isPromotingPawn()) {
                Promotion.addMoves((Pawn) actor, null, moves);
            } else {
                moves.add(new Step(actor, destination));
            }
        }
    }
}
