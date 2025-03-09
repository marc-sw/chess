package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.ui.state.ClickState;
import de.fumano.chess.ui.state.TurnState;

import java.util.List;

import static de.fumano.chess.Chess.*;
import static de.fumano.chess.Chess.BOARD_SIZE;

public class ClickStrategy implements MoveStrategy {

    private ClickState clickState;
    private final Viewport viewport;
    private final Vector2 touchPos;

    public ClickStrategy(Viewport viewport) {
        this.viewport = viewport;
        this.touchPos = new Vector2();
        this.clickState = new TurnState(this);
    }

    public void setClickState(ClickState clickState) {
        this.clickState = clickState;
    }

    public ClickState getClickState() {
        return clickState;
    }

    @Override
    public Move selectMove(List<Move> moves) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            touchPos.x -= BOARD_OFFSET;
            touchPos.y -= BOARD_OFFSET;
            de.fumano.chess.Vector2 spot = new de.fumano.chess.Vector2((int) touchPos.x/WORLD_SCALE, (int) touchPos.y/WORLD_SCALE);
            if (touchPos.x >= 0 && touchPos.y >= 0 && touchPos.x < BOARD_SIZE && touchPos.y < BOARD_SIZE) {
                return this.clickState.selectMove(spot, moves);
            }
        }
        return null;
    }

    @Override
    public void reset() {
        this.clickState = new TurnState(this);
    }
}
