package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fumano.chess.Vector2;
import de.fumano.chess.state.State;
import de.fumano.chess.state.TurnState;

import static de.fumano.chess.Chess.*;

public class HumanPlayer extends Player {

    private State state;
    private final com.badlogic.gdx.math.Vector2 touchPos;
    private final Viewport viewport;

    public HumanPlayer(Viewport viewport) {
        this.viewport = viewport;
        this.selectedMove = null;
        this.state = new TurnState(this);
        this.touchPos = new com.badlogic.gdx.math.Vector2();
    }

    @Override
    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            touchPos.x -= BOARD_OFFSET;
            touchPos.y -= BOARD_OFFSET;
            Vector2 spot = new Vector2((int) touchPos.x/WORLD_SCALE, (int) touchPos.y/WORLD_SCALE);
            if (touchPos.x >= 0 && touchPos.y >= 0 && touchPos.x < BOARD_SIZE && touchPos.y < BOARD_SIZE) {
                this.state.handleClick(spot);
            }
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public void reset() {
        super.reset();
        this.state = new TurnState(this);
    }
}
