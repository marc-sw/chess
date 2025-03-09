package de.fumano.chess.ui.renderer;

import com.badlogic.gdx.utils.Disposable;
import de.fumano.chess.ChessGame;

public interface Renderer extends Disposable {
    void render(ChessGame chessGame);
}
