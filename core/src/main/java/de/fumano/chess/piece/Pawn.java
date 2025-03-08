package de.fumano.chess.piece;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Color;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.Directions;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private final int direction;

    public Pawn(Vector2 spot, Color color) {
        super(spot, color);
        this.direction = color == Color.WHITE ? 1: -1;
    }

    @Override
    public List<Move> calculateAllMoves(ChessGame chessGame) {
        List<Move> moves = new ArrayList<>();
        Rules.apply(this, chessGame, getStepDirections(), getStepRange(), Rules.Step, moves);
        Rules.apply(this, chessGame, getCaptureDirections(), 1, Rules.CaptureAndEnPassant, moves);
        return moves;
    }

    public int getDirection() {
        return direction;
    }

    public boolean promotesOnMove() {
        return spot.y + direction == 7 || spot.y + direction == 0;
    }

    public boolean onEnPassantLine() {
        return spot.y + direction * 3 == 7 || spot.y + direction * 3 == 0;
    }

    private List<Vector2> getCaptureDirections() {
        return color == Color.WHITE ? Directions.WhitePawnCapture : Directions.BlackPawnCapture;
    }

    private List<Vector2> getStepDirections() {
        return color == Color.WHITE ? Directions.WhitePawnStep : Directions.BlackPawnStep;
    }

    private int getStepRange() {
        return moved ? 1 : 2;
    }

}
