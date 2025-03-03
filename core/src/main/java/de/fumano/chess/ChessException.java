package de.fumano.chess;

public class ChessException extends RuntimeException {

    public ChessException(String message) {
        super(message);
    }

    public static ChessException illegalMove(Vector2 origin, Vector2 destination, String reason) {
        return new ChessException("illegal move from %s to %s - %s".formatted(origin, destination, reason));
    }
}
