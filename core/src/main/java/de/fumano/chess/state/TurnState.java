package de.fumano.chess.state;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.move.Move;
import de.fumano.chess.move.Promotion;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.Renderer;

import java.util.ArrayList;
import java.util.List;

public class TurnState extends State {

    private Piece selectedPiece;

    public TurnState(ChessGame chessGame) {
        super(chessGame);
    }

    private boolean isPieceSelected() {
        return this.selectedPiece != null;
    }

    private Move findMoveByDestination(Vector2 destination) {
        return this.selectedPiece.getAllMoves().stream().filter(move -> move.getDestination().equals(destination)).findAny().orElse(null);
    }

    private List<Promotion> findAllPromotionMoves(Vector2 destination) {
        List<Promotion> promotions = new ArrayList<>(4);
        for (Move move: this.selectedPiece.getAllMoves()) {
            if (move.getDestination().equals(destination) && move instanceof Promotion promotion) {
                promotions.add(promotion);
            }
        }
        return promotions;
    }

    @Override
    public void handleClick(Vector2 spot) {
        Move move;
        Piece target;
       if (isPieceSelected() && (move = findMoveByDestination(spot)) != null) {
           if (move instanceof Promotion) {
               this.chessGame.getState(PromotionState.class).setPromotionMoves(findAllPromotionMoves(spot));
               this.chessGame.setActiveState(PromotionState.class);
           } else {
               this.chessGame.processMove(move);
           }
           this.selectedPiece = null;
        } else if ((target = this.chessGame.getBoard().getPieceAt(spot)) == selectedPiece || target != null && !target.getColor().equals(this.chessGame.getActiveColor())) {
            this.selectedPiece = null;
        } else if (target == null || target.getColor().equals(this.chessGame.getActiveColor())){
            this.selectedPiece = target;
        }
    }

    @Override
    public void update(float secondsElapsed) {
        chessGame.updateActiveTimer(secondsElapsed);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.useOnSpriteBatch(() -> {
            renderer.renderEmptyBoard();
            renderer.renderPieces(this.chessGame.getBoard());
            renderer.renderTimers(this.chessGame.getWhiteSecondsRemaining(), this.chessGame.getBlackSecondsRemaining());
        });

        if (selectedPiece != null) {
            renderer.useOnShapeRenderer(() -> renderer.highlightPiece(selectedPiece));
        }
    }
}
