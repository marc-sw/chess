package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;
import de.fumano.chess.Timer;
import de.fumano.chess.movement.move.*;
import de.fumano.chess.piece.*;

import java.util.List;
import java.util.Random;

public class SmartStrategy implements MoveStrategy {

    private static final Random random = new Random();
    private static final List<Class<? extends Move>> movePriorities = List.of(Promotion.class, Capture.class, EnPassant.class, Castling.class, Step.class);
    private static final List<Class<? extends Piece>> capturePriorities = List.of(Queen.class, Rook.class, Bishop.class, Knight.class, Pawn.class, King.class);

    private final Timer timer;

    public SmartStrategy(float executionDelay) {
        this.timer = new Timer(executionDelay);
    }

    @Override
    public Move selectMove(List<Move> moves) {
        this.timer.update(Gdx.graphics.getDeltaTime());
        if (!this.timer.isDone()) {
            return null;
        }
        this.timer.reset();
        if (moves.isEmpty()) {
            return null;
        }
        Move bestMove = moves.get(random.nextInt(moves.size()));
        int capturePriority = Integer.MAX_VALUE;
        int movePriority = movePriorities.indexOf(bestMove.getClass());
        if (bestMove instanceof Capture capture) {
            capturePriority = capturePriorities.indexOf(capture.capturedPiece.getClass());
        }

        for (int i = 0; i < moves.size(); i++) {
            int priority = movePriorities.indexOf(moves.get(i).getClass());
            if (priority < movePriority) {
                movePriority = priority;
                bestMove = moves.get(i);
                if (bestMove instanceof Capture capture ) {
                    capturePriority = capturePriorities.indexOf(capture.capturedPiece.getClass());
                }
            } else if (priority == movePriority && moves.get(i) instanceof Capture capture) {
                int currentCapturePrio = capturePriorities.indexOf(capture.capturedPiece.getClass());
                if (currentCapturePrio < capturePriority) {
                    bestMove = moves.get(i);
                    capturePriority = currentCapturePrio;
                }
            }
        }
        return bestMove;
    }

    @Override
    public void reset() {
        this.timer.reset();
    }
}
