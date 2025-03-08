package de.fumano.chess.player;

import de.fumano.chess.Board;
import de.fumano.chess.Color;

import java.util.Random;

public class ComputerPlayer extends Player {

    private static final Random random = new Random();
    private final float turnCooldown;
    private float secondsPerTurn;

    public ComputerPlayer(Board board, Color color, float turnCooldown) {
        super(board, color);
        this.turnCooldown = turnCooldown;
        this.secondsPerTurn = turnCooldown;
    }

    @Override
    public void update(float secondsElapsed) {
        secondsPerTurn -= secondsElapsed;
        if (secondsPerTurn <= 0) {
            this.secondsPerTurn = turnCooldown;
            int i = random.nextInt(this.legalMoves.size());
            this.setSelectedMove(this.legalMoves.get(i));
        }
    }
}
