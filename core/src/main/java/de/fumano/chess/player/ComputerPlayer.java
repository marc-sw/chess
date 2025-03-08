package de.fumano.chess.player;

import com.badlogic.gdx.Gdx;

import java.util.Random;

public class ComputerPlayer extends Player {

    private static final Random random = new Random();
    private final float turnCooldown;
    private float secondsPerTurn;

    public ComputerPlayer(float turnCooldown) {
        this.turnCooldown = turnCooldown;
        this.secondsPerTurn = turnCooldown;
    }

    @Override
    public void update() {
        secondsPerTurn -= Gdx.graphics.getDeltaTime();
        if (secondsPerTurn <= 0) {
            this.secondsPerTurn = turnCooldown;
            int i = random.nextInt(this.legalMoves.size());
            this.setSelectedMove(this.legalMoves.get(i));
        }
    }
}
