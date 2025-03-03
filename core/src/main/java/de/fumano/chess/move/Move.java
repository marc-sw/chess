package de.fumano.chess.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;

public interface Move {
    void execute(ChessGame chessGame);
    void undo(ChessGame chessGame);
    Vector2 getDestination();
}
