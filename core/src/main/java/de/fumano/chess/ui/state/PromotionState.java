package de.fumano.chess.ui.state;

import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.player.ClickStrategy;

import java.util.List;

public class PromotionState implements ClickState {

    public static final Vector2 firstSpot = new Vector2(2, 3);

    private final ClickStrategy strategy;
    private final List<Promotion> promotionMoves;

    public PromotionState(ClickStrategy strategy, List<Promotion> promotionMoves) {
        this.strategy = strategy;
        this.promotionMoves = promotionMoves;
    }

    public List<Promotion> getPromotionMoves() {
        return promotionMoves;
    }

    @Override
    public Move selectMove(Vector2 clickedTile, List<Move> moves) {
        for (int i = 0; i < promotionMoves.size(); i++) {
            if (clickedTile.x == firstSpot.x + i && clickedTile.y == firstSpot.y) {
                this.strategy.setClickState(new TurnState(this.strategy));
                return promotionMoves.get(i);
            }
        }
        return null;
    }
}
