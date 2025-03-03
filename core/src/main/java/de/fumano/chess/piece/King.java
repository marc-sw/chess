package de.fumano.chess.piece;

import de.fumano.chess.*;
import de.fumano.chess.move.Castling;
import de.fumano.chess.move.Move;

import java.util.List;

public class King extends Piece {

    private boolean check;

    public King(Vector2 spot, Color color) {
        super(spot, color);
        this.check = false;
    }

    public boolean isInCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    protected List<Move> findAllMoves(Board board) {
        List<Move> moves = Movement.findMoves(board, this, Movement.ALL, 1);

        if (!this.moved) {
            Piece leftPiece = board.getPieceAt(0, this.spot.y);
            Piece rightPiece = board.getPieceAt(7, this.spot.y);

            // means they have not moved and at the same line as the king that has not moved, so they have to be rooks
            if (leftPiece instanceof Rook leftRook && !leftRook.hasMoved()) {
                boolean empty = true;
                for (int i = this.spot.x - 1; i > 0 && empty; i--) {
                    if (board.getPieceAt(i, this.spot.y) != null) {
                        empty = false;
                    }
                }
                if (empty) {
                    moves.add(new Castling(this, leftRook));
                }
            }

            if (rightPiece instanceof Rook rightRook && !rightRook.hasMoved()) {
                boolean empty = true;
                for (int i = this.spot.x + 1; i < 7 && empty; i++) {
                    if (board.getPieceAt(i, this.spot.y) != null) {
                        empty = false;
                    }
                }
                if (empty) {
                    moves.add(new Castling(this, rightRook));
                }
            }
        }

        return moves;
    }
}
