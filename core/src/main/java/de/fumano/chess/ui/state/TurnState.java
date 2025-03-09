package de.fumano.chess.ui.state;

import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.player.ClickStrategy;

import java.util.List;

public class TurnState implements ClickState {

    private final ClickStrategy strategy;
    private Piece selectedPiece;

    public TurnState(ClickStrategy strategy) {
        this.strategy = strategy;
        this.selectedPiece = null;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isPieceSelected() {
        return this.selectedPiece != null;
    }

    private Move findMoveByDestination(Vector2 destination) {
        return this.selectedPiece.getLegalMoves().stream().filter(move -> move.getDestination().equals(destination)).findAny().orElse(null);
    }

    private List<Promotion> findAllPromotionMoves(Vector2 destination) {
        return this.selectedPiece.getLegalMoves().stream().filter(move -> move.getDestination().equals(destination) && move instanceof Promotion).map(Promotion.class::cast).toList();
    }

    @Override
    public Move selectMove(Vector2 clickedTile, List<Move> moves) {
        Move move;
        Piece target;
        if (isPieceSelected() && (move = findMoveByDestination(clickedTile)) != null) {
            this.selectedPiece = null;
            if (move instanceof Promotion) {
                this.strategy.setClickState(new PromotionState(this.strategy, findAllPromotionMoves(clickedTile)));
            } else {
                return move;
            }
        } else {
            if (isPieceSelected() && selectedPiece.getSpot().equals(clickedTile)) {
                this.selectedPiece = null;
            } else {
                Move found = moves.stream().filter(m -> m.getActor().getSpot().equals(clickedTile)).findAny().orElse(null);
                if (found != null) {
                    this.selectedPiece = found.getActor();
                }
            }
        }
        return null;
    }
}
