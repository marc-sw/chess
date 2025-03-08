package de.fumano.chess.piece;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Color;
import de.fumano.chess.movement.Directions;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    public List<Move> calculateAllMoves(ChessGame chessGame) {
        List<Move> moves = new ArrayList<>();
        Rules.apply(this, chessGame, Directions.Knight, 1, Rules.StepAndCapture, moves);
        return moves;
    }
}
