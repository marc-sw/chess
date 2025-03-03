package de.fumano.chess;

import de.fumano.chess.move.Move;
import de.fumano.chess.move.Step;
import de.fumano.chess.piece.King;
import de.fumano.chess.piece.Pawn;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.state.EndState;
import de.fumano.chess.state.PromotionState;
import de.fumano.chess.state.State;
import de.fumano.chess.state.TurnState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChessGame {

    private final List<Move> movesMade;
    private final List<State> states;
    private final Board board;
    private Color activeColor;
    private State activeState;
    private float whiteSecondsRemaining;
    private float blackSecondsRemaining;

    public ChessGame() {
        this.board = new Board();
        this.board.iterate(piece -> {
            if (piece instanceof Pawn pawn) {
                pawn.setChessGame(this);
            }
        });
        this.activeColor = Color.WHITE;
        this.movesMade = new ArrayList<>();
        this.whiteSecondsRemaining = 900;
        this.blackSecondsRemaining = 900;
        this.states = List.of(new TurnState(this), new PromotionState(this), new EndState(this));
        this.setActiveState(TurnState.class);
        this.updateAllPieces();
        this.filterPossibleMoves();
    }

    private void update(Consumer<Piece> consumer) {
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                Piece piece = this.board.getPieceAt(x, y);
                if (piece == null) {
                    continue;
                }
                consumer.accept(piece);
            }
        }
        board.getBlackKing().setCheck(false);
        board.getWhiteKing().setCheck(false);
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                Piece piece = this.board.getPieceAt(x, y);
                if (piece == null) {
                    continue;
                }
                if (piece.getColor().equals(Color.WHITE) && piece.canCapture(board.getBlackKing())) {
                    board.getBlackKing().setCheck(true);
                } else if (piece.getColor().equals(Color.BLACK) && piece.canCapture(board.getWhiteKing())) {
                    board.getWhiteKing().setCheck(true);
                }
            }
        }
    }

    private void updateAllPieces() {
        update((p -> p.updateMoves(board)));
    }

    private void cacheUpdateAll() {
        update((p -> p.cacheUpdateMoves(board)));
    }

    private void revertUpdates() {
        update((Piece::revertUpdates));
    }

    public void updateActiveTimer(float secondsElapsed) {
        if (activeColor.equals(Color.WHITE)) {
            whiteSecondsRemaining -= secondsElapsed;
            if (whiteSecondsRemaining <= 0) {
                this.getState(EndState.class).setMessage("Black won by time");
                this.setActiveState(EndState.class);
            }
        } else {
            blackSecondsRemaining -= secondsElapsed;
            if (blackSecondsRemaining <= 0) {
                this.getState(EndState.class).setMessage("White won by time");
                this.setActiveState(EndState.class);
            }
        }
    }


    public <T extends State> void setActiveState(Class<T> cls) {
        this.activeState = this.states.stream().filter(state -> state.getClass().equals(cls)).findAny()
            .orElseThrow(() -> new ChessException("unknown state provided"));
    }

    @SuppressWarnings("unchecked")
    public <T extends State> T getState(Class<T> cls) {
        return (T) this.states.stream().filter(state -> state.getClass().equals(cls)).findAny()
            .orElseThrow(() -> new ChessException("unknown state provided"));
    }

    public void switchColor() {
        this.activeColor = this.activeColor == Color.WHITE ? Color.BLACK: Color.WHITE;
    }

    public int getBlackSecondsRemaining() {
        return (int) blackSecondsRemaining;
    }

    public int getWhiteSecondsRemaining() {
        return (int) whiteSecondsRemaining;
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

    public State getActiveState() {
        return activeState;
    }

    public Board getBoard() {
        return board;
    }

    public Color getActiveColor() {
        return this.activeColor;
    }

    public void processMove(Move move) {
        move.execute(this);
        this.movesMade.add(move);
        this.updateAllPieces();

        filterPossibleMoves();
        boolean canMove = hasActiveSidePossibleMoves();
        if (getActiveKing().isInCheck() && !canMove) {
            //end and show checkmate
            this.getState(EndState.class).setMessage("%s won by checkmate".formatted(getOpponent()));
            this.setActiveState(EndState.class);
        } else if (!canMove) {
            this.getState(EndState.class).setMessage("%s in stalemate".formatted(getActiveColor()));
            this.setActiveState(EndState.class);
        }
    }

    public King getActiveKing() {
        return this.activeColor.equals(Color.WHITE) ? this.board.getWhiteKing(): this.board.getBlackKing();
    }

    public boolean hasActiveSidePossibleMoves() {
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                Piece piece = this.board.getPieceAt(x, y);
                if (piece == null || !piece.getColor().equals(activeColor)) {
                    continue;
                }
                if (!piece.getAllMoves().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Color getOpponent() {
        return this.activeColor == Color.WHITE ? Color.BLACK: Color.WHITE;
    }

    public void undoLastMove() {
        if (!this.movesMade.isEmpty()) {
            Move lastMove = this.movesMade.removeLast();
            lastMove.undo(this);
            this.updateAllPieces();
        }
    }

    private void filterPossibleMoves() {
        King activeKing = this.getActiveKing();
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                Piece piece = this.board.getPieceAt(x, y);
                if (piece != null && !piece.isOpponent(activeKing)) {
                    for (int i = piece.getAllMoves().size() - 1; i >= 0; i--) {
                        piece.getAllMoves().get(i).execute(this);
                        cacheUpdateAll();
                        boolean isCheck = activeKing.isInCheck();
                        revertUpdates();
                        piece.getAllMoves().get(i).undo(this);
                        if (isCheck) {
                            piece.getAllMoves().remove(i);
                        }
                    }
                }
            }
        }
    }

    public void reset() {
        this.movesMade.clear();
    }
}
