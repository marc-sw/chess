package de.fumano.chess.piece;

import de.fumano.chess.*;
import de.fumano.chess.move.Move;

import java.util.List;

public class Queen extends Piece {

    public Queen(Vector2 spot, Color color) {
        super(spot, color);
    }

    @Override
    protected List<Move> findAllMoves(Board board) {
        return Movement.findMoves(board, this, Movement.ALL, Movement.INFINITE);
    }
}
