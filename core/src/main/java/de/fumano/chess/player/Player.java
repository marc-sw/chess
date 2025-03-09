package de.fumano.chess.player;

import de.fumano.chess.*;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player implements Resetable {

    private static final float START_SECONDS = 900;

    protected Board board;
    protected final List<Move> legalMoves;
    protected King king;
    protected Color color;
    protected float secondsRemaining;
    private final MoveStrategy moveStrategy;

    public Player(Board board, Color color, MoveStrategy moveStrategy) {
        this.color = color;
        this.board = board;
        this.king = (King) board.getPieceAt(4, color.equals(Color.WHITE) ? 0: 7);
        this.moveStrategy = moveStrategy;
        this.legalMoves = new ArrayList<>();
        this.secondsRemaining = Player.START_SECONDS;
    }

    public void reduceTime(float secondsElapsed) {
        this.secondsRemaining -= secondsElapsed;
    }

    public boolean isTimerZero() {
        return this.secondsRemaining <= 0;
    }

    public Color getColor() {
        return color;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    private void updateAllMoves(ChessGame chessGame) {
        this.legalMoves.clear();
        for (Piece piece: this.board.findPieces(this.color)) {
            List<Move> moves = piece.calculateAllMoves(chessGame);
            piece.getLegalMoves().clear();
            piece.getLegalMoves().addAll(moves);
            this.legalMoves.addAll(moves);
        }
    }

    public void updateLegalMoves(ChessGame chessGame) {
        Player opponent = chessGame.getOpponentPlayer();
        this.legalMoves.clear();
        for (Piece allyPiece: this.board.findPieces(this.color)) {
            List<Move> allPieceMoves = allyPiece.calculateAllMoves(chessGame);
            Iterator<Move> iterator = allPieceMoves.iterator();
            while (iterator.hasNext()) {
                Move move = iterator.next();
                move.execute(chessGame);
                chessGame.selectNextPlayer();
                opponent.updateAllMoves(chessGame);
                boolean check = opponent.canCapture(this.king.getSpot());
                move.undo(chessGame);
                chessGame.selectPreviousPlayer();
                if (check) {
                    iterator.remove();
                }
            }
            allyPiece.getLegalMoves().clear();
            allyPiece.getLegalMoves().addAll(allPieceMoves);
            this.legalMoves.addAll(allPieceMoves);
        }
        opponent.updateAllMoves(chessGame);
    }

    public boolean canCapture(Vector2 destination) {
        return this.legalMoves.stream().anyMatch(move -> move.getDestination().equals(destination));
    }

    public float getSecondsRemaining() {
        return this.secondsRemaining;
    }

    @Override
    public void reset() {
        this.secondsRemaining = Player.START_SECONDS;
        this.legalMoves.clear();
        this.moveStrategy.reset();
        this.king = (King) board.getPieceAt(4, color.equals(Color.WHITE) ? 0: 7);
    }

    public Move selectMove() {
        return moveStrategy.selectMove(this.legalMoves);
    }
}
