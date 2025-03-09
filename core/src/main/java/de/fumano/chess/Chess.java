package de.fumano.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fumano.chess.ui.renderer.AnimatedRenderer;
import de.fumano.chess.ui.renderer.Renderer;
import de.fumano.chess.ui.screen.MenuScreen;
import de.fumano.chess.ui.renderer.StaticRenderer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Chess extends Game {

    public static final int WORLD_SCALE = 100;
    public static final int WORLD_SIZE = 9 * WORLD_SCALE;
    public static final float BOARD_OFFSET = 0.5f * WORLD_SCALE;
    public static final int BOARD_SIZE = 8 * WORLD_SCALE;
    public static final float WORLD_CENTER = WORLD_SIZE / 2.0f;
    public static final float WHITE_TIMER_X = 8.3f * WORLD_SCALE;
    public static final float WHITE_TIMER_Y = 0.33f * WORLD_SCALE;
    public static final float BLACK_TIMER_X = 8.3f * WORLD_SCALE;
    public static final float BLACK_TIMER_Y = 8.83f * WORLD_SCALE;

    private Renderer renderer;
    private Viewport viewport;
    private BitmapFont bitmapFont;

    public Renderer getRenderer() {
        return renderer;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    @Override
    public void create() {
        this.viewport = new FitViewport(WORLD_SIZE, WORLD_SIZE);
        this.bitmapFont = new BitmapFont();
        this.renderer = new AnimatedRenderer(new StaticRenderer(this.viewport, bitmapFont), 0.25f);

        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        bitmapFont.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
