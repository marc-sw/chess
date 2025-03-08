package de.fumano.chess.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fumano.chess.Chess;
import de.fumano.chess.ChessGame;

public class GameScreen implements Screen {

    private final ChessGame chessGame;
    private final Chess chess;

    public GameScreen(Chess chess, ChessGame chessGame) {
        this.chess = chess;
        this.chessGame = chessGame;
    }

    private void input() {
        if (this.chessGame.isOver() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            chess.setScreen(new MenuScreen(chess));
            dispose();
        }
    }

    private void update(float secondsElapsed) {
        this.chessGame.update(secondsElapsed);
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        this.chess.getRenderer().render(this.chessGame);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        update(Gdx.graphics.getDeltaTime());
        draw();
    }

    @Override
    public void resize(int width, int height) {
        chess.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
