package de.fumano.chess.state;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.Renderer;

public class EndState extends State {

    private String message = "";

    public EndState(ChessGame chessGame) {
        super(chessGame);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void handleClick(Vector2 spot) {

    }

    @Override
    public void update(float secondsElapsed) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.useOnSpriteBatch(() -> {
            renderer.renderEmptyBoard();
            renderer.renderPieces(this.chessGame.getBoard());
            renderer.renderTimers(this.chessGame.getWhiteSecondsRemaining(), this.chessGame.getBlackSecondsRemaining());
            renderer.renderTextAtCenter(this.message);
        });
    }
}
