package de.fumano.chess.movement.rule;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.Piece;

import java.util.List;

public interface Rule {
    void apply(Piece actor, Vector2 destination, ChessGame context, List<Move> moves);
}
