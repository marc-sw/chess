package de.fumano.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Step;
import de.fumano.chess.piece.Pawn;
import de.fumano.chess.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ChessGame implements Resetable {

    private final List<Move> movesMade;

    private final List<Player> players;
    private final Board board;
    private int activePlayerIndex;
    private boolean over;

    public ChessGame(Player white, Player black) {
        this.board = new Board();
        this.players = List.of(white, black);
        this.movesMade = new ArrayList<>();
        this.activePlayerIndex = 0;
        this.over = false;
        white.init(board, Color.WHITE);
        black.init(board, Color.BLACK);
        this.getActivePlayer().updateLegalMoves(this);
    }

    public List<Move> getMovesMade() {
        return movesMade;
    }

    public boolean isOver() {
        return over;
    }

    public void selectNextPlayer() {
        this.activePlayerIndex = (this.activePlayerIndex + 1) % this.players.size();
    }

    public void selectPreviousPlayer() {
        this.activePlayerIndex = (this.activePlayerIndex - 1 + this.players.size()) % this.players.size();
    }

    public boolean didPawnDoubleMoveLastTurn(Pawn pawn) {
        if (movesMade.isEmpty()) {
            return false;
        }
        Move move = movesMade.getLast();
        if (move instanceof Step step) {
            return step.movingPiece.equals(pawn) && Math.abs(step.origin.y - step.destination.y) == 2;
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public void processMove(Move move) {
        move.execute(this);
        this.selectNextPlayer();
        this.movesMade.add(move);
        this.getActivePlayer().updateLegalMoves(this);
        this.over = this.getActivePlayer().getLegalMoves().isEmpty();
    }

    public void undoLastMove() {
        if (!this.movesMade.isEmpty()) {
            this.movesMade.removeLast().undo(this);
            this.selectPreviousPlayer();
            this.getActivePlayer().updateLegalMoves(this);
            if (this.over) {
                this.over = false;
            }
        }
    }

    public Player getWhitePlayer() {
        return this.players.getFirst();
    }

    public Player getBlackPlayer() {
        return this.players.getLast();
    }

    public Player getActivePlayer() {
        return this.players.get(this.activePlayerIndex);
    }

    public Player getOpponentPlayer() {
        return this.players.get((this.activePlayerIndex + 1) % this.players.size());
    }

    public void update(float secondsElapsed) {
        if (this.over) {
            return;
        }
        this.getActivePlayer().reduceTime(secondsElapsed);
        if (this.getActivePlayer().isTimerZero()) {
            this.over = true;
            return;
        }
        this.getActivePlayer().update();
        Move move = this.getActivePlayer().get();
        if (move != null) {
            this.processMove(move);
        }
    }

    public void reset() {
        this.movesMade.clear();
        this.board.reset();
        this.activePlayerIndex = 0;
        this.players.forEach(Player::reset);
        this.getActivePlayer().updateLegalMoves(this);
        this.over = false;
    }
}
