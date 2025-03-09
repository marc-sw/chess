package de.fumano.chess.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fumano.chess.Chess;
import de.fumano.chess.ChessGame;
import de.fumano.chess.player.ClickStrategy;
import de.fumano.chess.player.SmartStrategy;
import de.fumano.chess.ui.components.GamemodeFactory;

public class MenuScreen implements Screen {

    private final Stage stage;

    public MenuScreen(Chess chess) {
        this.stage = new Stage(chess.getViewport());
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = chess.getBitmapFont();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        GamemodeFactory factory = new GamemodeFactory(table, chess, this, style);
        factory.createButton(
            () -> new ChessGame(new ClickStrategy(chess.getViewport()), new ClickStrategy(chess.getViewport())),
            "PvP"
            );
        factory.createButton(
            () -> new ChessGame(new ClickStrategy(chess.getViewport()), new SmartStrategy(Chess.COMPUTER_TURN_SECONDS)),
            "PvE"
        );
        factory.createButton(
            () -> new ChessGame(new SmartStrategy(Chess.COMPUTER_TURN_SECONDS), new SmartStrategy(Chess.COMPUTER_TURN_SECONDS)),
            "EvE"
        );

        Gdx.input.setInputProcessor(this.stage);
    }

    private void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
}
