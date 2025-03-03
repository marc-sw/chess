package de.fumano.chess.piece;

import de.fumano.chess.*;
import de.fumano.chess.move.Move;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    protected List<Move> findAllMoves(Board board) {
        return Movement.findMoves(board, this, Movement.DIAGONAL, Movement.INFINITE);
    }
}
