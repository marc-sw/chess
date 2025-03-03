package de.fumano.chess.piece;

import de.fumano.chess.Board;
import de.fumano.chess.Color;
import de.fumano.chess.Movement;
import de.fumano.chess.Vector2;
import de.fumano.chess.move.Move;

import java.util.List;

public class Knight extends Piece {

    public Knight(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    protected List<Move> findAllMoves(Board board) {
        return Movement.findMoves(board, this, Movement.KNIGHT_DIRECTIONS, 1);
    }
}
