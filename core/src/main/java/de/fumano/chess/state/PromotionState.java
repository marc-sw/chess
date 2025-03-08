package de.fumano.chess.state;

import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.player.HumanPlayer;

import java.util.List;

public class PromotionState implements State {

    public static final Vector2 firstSpot = new Vector2(2, 3);

    private final HumanPlayer player;
    private final List<Promotion> promotionMoves;

    public PromotionState(HumanPlayer player, List<Promotion> promotionMoves) {
        this.player = player;
        this.promotionMoves = promotionMoves;
    }

    public List<Promotion> getPromotionMoves() {
        return promotionMoves;
    }

    @Override
    public void handleClick(Vector2 spot) {
        for (int i = 0; i < promotionMoves.size(); i++) {
            if (spot.x == firstSpot.x + i && spot.y == firstSpot.y) {
                this.player.setSelectedMove(promotionMoves.get(i));
                this.player.setState(new TurnState(this.player));
                break;
            }
        }
    }
}
