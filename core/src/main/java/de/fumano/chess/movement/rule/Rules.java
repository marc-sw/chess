package de.fumano.chess.movement.rule;

import de.fumano.chess.Board;
import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.Piece;

import java.util.List;

public class Rules {

    public static final CastlingRule Castling = new CastlingRule();
    public static final StepRule Step = new StepRule();
    public static final CaptureRule Capture = new CaptureRule();
    public static final EnPassantRule EnPassant = new EnPassantRule();
    public static final List<Rule> StepAndCapture = List.of(Step, Capture);
    public static final List<Rule> CaptureAndEnPassant = List.of(Capture, EnPassant);

    public static void apply(Piece piece, ChessGame chessGame, List<Vector2> directions, int range, List<Rule> rules, List<Move> moves) {
        for (Vector2 direction: directions) {
            boolean blocked = false;
            for (int i = 1; i <= range && !blocked; i++) {
                Vector2 destination = piece.getSpot().add(direction, i);
                if (Board.isWithin(destination)) {
                    rules.forEach(rule -> rule.apply(piece, destination, chessGame, moves));
                    if (chessGame.getBoard().isOccupied(destination.x, destination.y)) {
                        blocked = true;
                    }
                } else {
                    blocked = true;
                }
            }
        }
    }

    public static void apply(Piece piece, ChessGame chessGame, List<Vector2> directions, int range, Rule rule, List<Move> moves) {
        for (Vector2 direction: directions) {
            boolean blocked = false;
            for (int i = 1; i <= range && !blocked; i++) {
                Vector2 destination = piece.getSpot().add(direction, i);
                if (Board.isWithin(destination)) {
                    rule.apply(piece, destination, chessGame, moves);
                    if (chessGame.getBoard().isOccupied(destination.x, destination.y)) {
                        blocked = true;
                    }
                } else {
                    blocked = true;
                }
            }
        }
    }
}
