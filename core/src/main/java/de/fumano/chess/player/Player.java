package de.fumano.chess.player;

import de.fumano.chess.*;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public abstract class Player implements Resetable, Supplier<Move> {

    private static final float START_SECONDS = 900;

    protected final Board board;
    protected final List<Move> legalMoves;
    protected King king;
    protected final Color color;
    protected float secondsRemaining;
    protected Move selectedMove;

    public Player(Board board, Color color) {
        this.board = board;
        this.legalMoves = new ArrayList<>();
        this.king = (King) board.getPieceAt(4, color.equals(Color.WHITE) ? 0: 7);
        this.color = color;
        this.secondsRemaining = Player.START_SECONDS;
    }

    public void setSelectedMove(Move move) {
        this.selectedMove = move;
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

    public abstract void update(float secondsElapsed);

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
    }

    @Override
    public Move get() {
        Move move = this.selectedMove;
        this.king = (King) board.getPieceAt(4, color.equals(Color.WHITE) ? 0: 7);
        this.selectedMove = null;
        return move;
    }
}
