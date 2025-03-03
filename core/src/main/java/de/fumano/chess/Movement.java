package de.fumano.chess;

import de.fumano.chess.move.Capture;
import de.fumano.chess.move.Move;
import de.fumano.chess.move.Step;
import de.fumano.chess.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Movement {

    public static final int INFINITE = 7;

    public static final List<Vector2> PARALLEL = List.of(
        new Vector2(1, 0),
        new Vector2(-1, 0),
        new Vector2(0, 1),
        new Vector2(0, -1)
    );

    public static final List<Vector2> DIAGONAL = List.of(
        new Vector2(1, 1),
        new Vector2(1, -1),
        new Vector2(-1, 1),
        new Vector2(-1, -1)
    );

    public static final List<Vector2> ALL = Stream.concat(PARALLEL.stream(), DIAGONAL.stream()).toList();

    public static final List<Vector2> KNIGHT_DIRECTIONS = List.of(
        new Vector2(1, 2),
        new Vector2(1, -2),
        new Vector2(-1, 2),
        new Vector2(-1, -2),
        new Vector2(2, 1),
        new Vector2(2, -1),
        new Vector2(-2, 1),
        new Vector2(-2, -1)
    );

    public static List<Move> findMoves(Board board, Piece piece, List<Vector2> directions, int range) {
        List<Move> moves = new ArrayList<>();
        for (Vector2 direction: directions) {
            Vector2 destination = piece.getSpot();
            boolean blocked = false;
            for (int i = 1; i <= range && !blocked; i++) {
                destination = destination.add(direction);
                if (Board.isWithin(destination)) {
                    Piece optionalPiece = board.getPieceAt(destination);
                    if (optionalPiece == null) {
                        moves.add(new Step(piece, destination));
                    } else {
                        if (optionalPiece.isOpponent(piece)) {
                            moves.add(new Capture(piece, optionalPiece));
                        }
                        blocked = true;
                    }
                } else {
                    blocked = true;
                }
            }
        }
        return moves;
    }
}
