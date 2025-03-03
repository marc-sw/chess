package de.fumano.chess.state;

import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.move.Promotion;
import de.fumano.chess.Renderer;

import java.util.List;

public class PromotionState extends State {

    private static final Vector2 firstSpot = new Vector2(2, 3);
    private List<Promotion> promotionMoves;

    public PromotionState(ChessGame chessGame) {
        super(chessGame);
    }

    public void setPromotionMoves(List<Promotion> promotionMoves) {
        this.promotionMoves = promotionMoves;
    }

    @Override
    public void handleClick(Vector2 spot) {
        for (int i = 0; i < promotionMoves.size(); i++) {
            if (spot.x == firstSpot.x + i && spot.y == firstSpot.y) {
                this.chessGame.processMove(promotionMoves.get(i));
                this.chessGame.setActiveState(TurnState.class);
                break;
            }
        }
    }

    @Override
    public void update(float secondsElapsed) {
        this.chessGame.updateActiveTimer(secondsElapsed);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.useOnSpriteBatch(() -> {
            renderer.renderEmptyBoard();
            for (int i = 0; i < promotionMoves.size(); i++) {
                renderer.renderPiece(promotionMoves.get(i).promotedPiece, new Vector2(firstSpot.x + i, firstSpot.y));
            }
            renderer.renderTimers(this.chessGame.getWhiteSecondsRemaining(), this.chessGame.getBlackSecondsRemaining());
        });
    }
}
