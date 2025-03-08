package de.fumano.chess.player;

import de.fumano.chess.Board;
import de.fumano.chess.Color;
import de.fumano.chess.state.State;
import de.fumano.chess.state.TurnState;

public class HumanPlayer extends Player {

    private State state;

    public HumanPlayer(Board board, Color color) {
        super(board, color);
        this.selectedMove = null;
        this.state = new TurnState(this);
    }

    @Override
    public void update(float secondsElapsed) {
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
