package de.fumano.chess.state;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.Renderer;

public abstract class State {

    protected final ChessGame chessGame;

    public State(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public abstract void handleClick(Vector2 spot);
    public abstract void update(float secondsElapsed);
    public abstract void render(Renderer renderer);
}
