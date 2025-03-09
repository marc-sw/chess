package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;
import de.fumano.chess.Timer;
import de.fumano.chess.movement.move.Move;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements MoveStrategy {

    private static final Random random = new Random();
    private final Timer timer;

    public RandomStrategy(float executionDelay) {
        this.timer = new Timer(executionDelay);
    }

    @Override
    public Move selectMove(List<Move> moves) {
        this.timer.update(Gdx.graphics.getDeltaTime());
        if (this.timer.isDone()) {
            this.timer.reset();
            int i = random.nextInt(moves.size());
            return moves.get(i);
        }
        return null;
    }

    @Override
    public void reset() {
        this.timer.reset();
    }
}
