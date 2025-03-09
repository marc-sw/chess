package de.fumano.chess.ui.state;

import de.fumano.chess.Vector2;

public interface State {
    void handleClick(Vector2 spot);
}
