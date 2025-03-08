package de.fumano.chess.state;

import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.Move;
import de.fumano.chess.movement.move.Promotion;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.player.HumanPlayer;

import java.util.List;

public class TurnState implements State {

    private final HumanPlayer player;
    private Piece selectedPiece;

    public TurnState(HumanPlayer humanPlayer) {
        this.player = humanPlayer;
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
    public void handleClick(Vector2 spot) {
        Move move;
        Piece target;
       if (isPieceSelected() && (move = findMoveByDestination(spot)) != null) {
           if (move instanceof Promotion) {
               this.player.setState(new PromotionState(this.player, findAllPromotionMoves(spot)));
           } else {
               this.player.setSelectedMove(move);
           }
           this.selectedPiece = null;
        } else if ((target = this.player.getBoard().getPieceAt(spot)) == selectedPiece || target != null && !target.getColor().equals(this.player.getColor())) {
            this.selectedPiece = null;
        } else if (target == null || target.getColor().equals(this.player.getColor())){
            this.selectedPiece = target;
        }
    }
}
