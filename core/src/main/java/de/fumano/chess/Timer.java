package de.fumano.chess;

public class Timer implements Resetable {

    private final float duration;
    private float remainingDuration;

    public Timer(float duration) {
        this.duration = duration;
        this.reset();
    }

    public float getRemainingDuration() {
        return this.remainingDuration;
    }

    public boolean isDone() {
        return this.remainingDuration <= 0;
    }

    public void update(float secondsElapsed) {
        this.remainingDuration -= secondsElapsed;
    }

    public void reset() {
        this.remainingDuration = this. duration;
    }
}
