package de.fumano.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Chess extends ApplicationAdapter {

    /*
    TODO: Check / Check Mate, regulating movement by check, castling by check...
    TODO: Playing with timer...
    TODO: Playing over the network
     */

    private ChessGame chessGame;
    private Renderer renderer;
    private Viewport viewport;
    private com.badlogic.gdx.math.Vector2 touchPos;

    @Override
    public void create() {
        this.viewport = new FitViewport(900, 900);
        this.renderer = new Renderer(this.viewport);
        this.touchPos = new com.badlogic.gdx.math.Vector2();
        this.chessGame = new ChessGame();
    }

    @Override
    public void render() {
        handleInput();
        update(Gdx.graphics.getDeltaTime());
        draw();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void handleBasicInputs() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.chessGame.reset();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            this.chessGame.undoLastMove();
        }
    }

    private void handleInput() {
        handleBasicInputs();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            touchPos.x -= 50f;
            touchPos.y -= 50f;
            Vector2 spot = new Vector2((int) touchPos.x/100, (int) touchPos.y/100);
            if (touchPos.x >= 0 && touchPos.y >= 0 && touchPos.x < 800 && touchPos.y < 800) {
                chessGame.getActiveState().handleClick(spot);
            }
        }
    }

    private void update(float secondsElapsed) {
        this.chessGame.getActiveState().update(secondsElapsed);
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        this.chessGame.getActiveState().render(renderer);
    }
}
