package de.fumano.chess.piece;

import de.fumano.chess.*;
import de.fumano.chess.movement.Directions;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    public List<Move> calculateAllMoves(ChessGame chessGame) {
        List<Move> moves = new ArrayList<>();
        Rules.apply(this, chessGame, Directions.Diagonal, 7, Rules.StepAndCapture, moves);
        return moves;
    }
}
