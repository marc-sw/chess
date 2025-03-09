package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;
import de.fumano.chess.movement.move.Move;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements MoveStrategy {

    private static final Random random = new Random();
    private final float executionDelay;
    private float timeTillExecution;

    public RandomStrategy(float executionDelay) {
        this.executionDelay = executionDelay;
        this.resetTimer();
    }

    private void resetTimer() {
        this.timeTillExecution = this.executionDelay;
    }

    private boolean isReady() {
        return this.timeTillExecution <= 0;
    }

    private void reduceTimer() {
        this.timeTillExecution -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public Move selectMove(List<Move> moves) {
        this.reduceTimer();
        if (isReady()) {
            resetTimer();
            int i = random.nextInt(moves.size());
            return moves.get(i);
        }
        return null;
    }

    @Override
    public void reset() {
        this.resetTimer();
    }
}
