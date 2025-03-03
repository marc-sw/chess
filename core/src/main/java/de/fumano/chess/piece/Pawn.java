package de.fumano.chess.piece;

import de.fumano.chess.Board;
import de.fumano.chess.ChessGame;
import de.fumano.chess.Color;
import de.fumano.chess.Vector2;
import de.fumano.chess.move.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private ChessGame chessGame;
    private final int direction;

    public Pawn(Vector2 spot, Color color) {
        super(spot, color);
        this.direction = color == Color.WHITE ? 1: -1;
    }

    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    protected List<Move> findAllMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Piece destinationPiece;
        Vector2 destination = this.spot.add(1, direction);
        if (Board.isWithin(destination)) {
            destinationPiece = board.getPieceAt(destination);
            if (destinationPiece != null && this.isOpponent(destinationPiece)) {
                if (destination.y == 0 ||destination.y == 7) {
                    moves.add(new Promotion(this, new Rook(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Knight(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Bishop(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Queen(this.spot, this.color), destinationPiece));
                } else {
                    moves.add(new Capture(this, destinationPiece));
                }
            }
        }

        destination = destination.addX(-2);
        if (Board.isWithin(destination)) {
            destinationPiece = board.getPieceAt(destination);
            if (destinationPiece != null && this.isOpponent(destinationPiece)) {
                if (destination.y == 0 || destination.y == 7) {
                    moves.add(new Promotion(this, new Rook(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Knight(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Bishop(this.spot, this.color), destinationPiece));
                    moves.add(new Promotion(this, new Queen(this.spot, this.color), destinationPiece));
                } else {
                    moves.add(new Capture(this, destinationPiece));
                }
            }
        }

        destination = destination.addX(1);
        if (Board.isWithin(destination) && board.getPieceAt(destination) == null) {
            if (destination.y == 0 || destination.y == 7) {
                moves.add(new Promotion(this, new Rook(this.spot, this.color)));
                moves.add(new Promotion(this, new Knight(this.spot, this.color)));
                moves.add(new Promotion(this, new Bishop(this.spot, this.color)));
                moves.add(new Promotion(this, new Queen(this.spot, this.color)));
            } else {
                moves.add(new Step(this, destination));

                destination = destination.addY(direction);
                if (!moved && board.getPieceAt(destination) == null) {
                    moves.add(new Step(this, destination));
                }
            }
        }

        if (this.spot.y == 4 && this.getColor().equals(Color.WHITE) ||
            this.spot.y == 3 && this.getColor().equals(Color.BLACK)) {
            destination = this.spot.addX(1);
            if (Board.isWithin(destination)) {
                destinationPiece = board.getPieceAt(destination);
                if (destinationPiece instanceof Pawn pawn && destinationPiece.isOpponent(this) && chessGame.didPawnDoubleMoveLastTurn(pawn)) {
                    moves.add(new EnPassant(this, pawn));
                }
            }
            destination = this.spot.addX(-1);
            if (Board.isWithin(destination)) {
                destinationPiece = board.getPieceAt(destination);
                if (destinationPiece instanceof Pawn pawn && destinationPiece.isOpponent(this) && chessGame.didPawnDoubleMoveLastTurn(pawn)) {
                    moves.add(new EnPassant(this, pawn));
                }
            }
        }

        return moves;
    }
}
