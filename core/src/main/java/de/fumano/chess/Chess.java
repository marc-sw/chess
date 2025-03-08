package de.fumano.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fumano.chess.player.ComputerPlayer;
import de.fumano.chess.player.HumanPlayer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Chess extends ApplicationAdapter {

    public static final int WORLD_SCALE = 100;
    public static final int WORLD_SIZE = 9 * WORLD_SCALE;
    public static final float BOARD_OFFSET = 0.5f * WORLD_SCALE;
    public static final int BOARD_SIZE = 8 * WORLD_SCALE;
    public static final float WORLD_CENTER = WORLD_SIZE / 2.0f;
    public static final float WHITE_TIMER_X = 8.3f * WORLD_SCALE;
    public static final float WHITE_TIMER_Y = 0.33f * WORLD_SCALE;
    public static final float BLACK_TIMER_X = 8.3f * WORLD_SCALE;
    public static final float BLACK_TIMER_Y = 8.83f * WORLD_SCALE;

    private ChessGame chessGame;
    private Renderer renderer;
    private Viewport viewport;

    @Override
    public void create() {
        this.viewport = new FitViewport(WORLD_SIZE, WORLD_SIZE);
        this.renderer = new Renderer(this.viewport);
        this.chessGame = new ChessGame(new HumanPlayer(viewport), new ComputerPlayer(1f));
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
    }

    private void update(float secondsElapsed) {
        this.chessGame.update(secondsElapsed);
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        this.renderer.render(this.chessGame);
    }
}
