package de.fumano.chess.movement.move;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.piece.Piece;

public interface Move {
    void execute(ChessGame chessGame);
    void undo(ChessGame chessGame);
    Vector2 getDestination();
    Piece getActor();
}
