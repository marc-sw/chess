package de.fumano.chess.player;

import de.fumano.chess.Resetable;
import de.fumano.chess.movement.move.Move;

import java.util.List;

public interface MoveStrategy extends Resetable {

    Move selectMove(List<Move> moves);
}
