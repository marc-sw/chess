package de.fumano.chess.ui.state;

import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;

import java.util.List;

public interface ClickState {
    Move selectMove(Vector2 clickedTile, List<Move> moves);
}
