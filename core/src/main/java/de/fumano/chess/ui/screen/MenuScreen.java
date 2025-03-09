package de.fumano.chess.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fumano.chess.Chess;
import de.fumano.chess.ChessGame;
import de.fumano.chess.player.ComputerPlayer;
import de.fumano.chess.player.HumanPlayer;

public class MenuScreen implements Screen {

    private final Stage stage;

    public MenuScreen(Chess chess) {
        this.stage = new Stage(chess.getViewport());
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = chess.getBitmapFont();
        TextButton freePlay = new TextButton("Free Play", style);
        TextButton againstComputer = new TextButton("Play vs. Computer", style);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(freePlay).center().width(300).height(50);
        table.add(againstComputer).center().width(300).height(50);

        freePlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                chess.setScreen(new GameScreen(chess, new ChessGame(new HumanPlayer(chess.getViewport()), new HumanPlayer(chess.getViewport()))));
                dispose();
            }
        });

        againstComputer.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                chess.setScreen(new GameScreen(chess, new ChessGame(new HumanPlayer(chess.getViewport()), new ComputerPlayer(1f))));
                dispose();
            }
        });

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
