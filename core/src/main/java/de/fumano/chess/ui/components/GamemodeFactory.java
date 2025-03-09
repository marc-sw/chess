package de.fumano.chess.ui.components;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.fumano.chess.Chess;
import de.fumano.chess.ChessGame;
import de.fumano.chess.ui.screen.GameScreen;

import java.util.function.Supplier;

public class GamemodeFactory {

    private final Screen screen;
    private final Table table;
    private final Chess chess;
    private final TextButton.TextButtonStyle style;

    public GamemodeFactory(Table table, Chess chess, Screen screen, TextButton.TextButtonStyle style) {
        this.table = table;
        this.chess = chess;
        this.style = style;
        this.screen = screen;
    }

    public TextButton createButton(Supplier<ChessGame> chessGameSupplier, String title) {
        TextButton button = new TextButton(title, this.style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chess.setScreen(new GameScreen(chess, chessGameSupplier.get()));
                screen.dispose();
            }
        });
        table.add(button).center().width(100).height(40);
        return button;
    }
}
