package de.fumano.chess.piece;

import de.fumano.chess.*;
import de.fumano.chess.movement.Directions;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    public List<Move> calculateAllMoves(ChessGame chessGame) {
        List<Move> moves = new ArrayList<>();
        Rules.apply(this, chessGame, Directions.All, 1, Rules.StepAndCapture, moves);
        Rules.apply(this, chessGame, Directions.Castling, 1, Rules.Castling, moves);
        return moves;
    }
}
