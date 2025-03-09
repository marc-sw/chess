package de.fumano.chess.ui.renderer;

import com.badlogic.gdx.Gdx;
import de.fumano.chess.ChessGame;
import de.fumano.chess.Vector2;
import de.fumano.chess.movement.move.*;
import de.fumano.chess.piece.Piece;
import de.fumano.chess.player.HumanPlayer;
import de.fumano.chess.ui.state.PromotionState;
import de.fumano.chess.ui.state.TurnState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static de.fumano.chess.ui.state.PromotionState.firstSpot;

public class AnimatedRenderer implements Renderer {

    private final StaticRenderer staticRenderer;
    private int nextMoveIndex;
    private final float animationSeconds;
    private final List<AnimatedPiece> animatedPieces;

    public AnimatedRenderer(StaticRenderer staticRenderer, float animationSeconds) {
        this.staticRenderer = staticRenderer;
        this.animationSeconds = animationSeconds;
        this.animatedPieces = new ArrayList<>(4);
        this.nextMoveIndex = 0;
    }

    public void render(ChessGame chessGame) {
        this.update(chessGame);
        this.staticRenderer.useOnSpriteBatch(() -> {
            this.staticRenderer.renderEmptyBoard();
            this.staticRenderer.renderTimers(
                (int) chessGame.getWhitePlayer().getSecondsRemaining(),
                (int) chessGame.getBlackPlayer().getSecondsRemaining());


            if (chessGame.getActivePlayer() instanceof HumanPlayer humanPlayer && humanPlayer.getState() instanceof PromotionState state) {
                for (int i = 0; i < state.getPromotionMoves().size(); i++) {
                    this.staticRenderer.renderPiece(state.getPromotionMoves().get(i).promotedPiece, new Vector2(firstSpot.x + i, firstSpot.y));
                }
            } else {
                chessGame.getBoard().iterate(piece -> {
                    if (animatedPieces.stream().noneMatch(animatedPiece -> animatedPiece.piece.equals(piece))) {
                     this.staticRenderer.renderPiece(piece);
                    }
                });
                animatedPieces.forEach(animatedPiece -> {
                    this.staticRenderer.renderPiece(animatedPiece.piece, animatedPiece.position.x, animatedPiece.position.y);
                });
                if (chessGame.isOver()) {
                    this.staticRenderer.renderTextAtCenter("game over");
                }
            }
        });

        this.staticRenderer.useOnShapeRenderer(() -> {
            if (chessGame.getActivePlayer() instanceof HumanPlayer humanPlayer) {
                if (humanPlayer.getState() instanceof TurnState turnState) {
                    if (turnState.isPieceSelected()) {
                        this.staticRenderer.highlightPiece(turnState.getSelectedPiece());
                    }
                }
            }
        });
    }

    @Override
    public void dispose() {
        this.staticRenderer.dispose();
    }

    private void add(Move move) {
        if (move instanceof Step step) {
            animatedPieces.add(new AnimatedPiece(
                step.movingPiece,
                step.origin,
                step.destination,
                this.animationSeconds));
        } else if (move instanceof Capture capture) {
            animatedPieces.add(new AnimatedPiece(
                capture.capturingPiece,
                capture.capturingPieceSpot,
                capture.getDestination(),
                this.animationSeconds
            ));
            animatedPieces.add(new AnimatedPiece(
                capture.capturedPiece,
                capture.capturedPiece.getSpot(),
                capture.capturedPiece.getSpot(),
                this.animationSeconds * 0.9f
            ));
        } else if (move instanceof EnPassant enPassant) {
            animatedPieces.add(new AnimatedPiece(
                enPassant.capturingPawn,
                enPassant.capturingPawnSpot,
                enPassant.getDestination(),
                this.animationSeconds
            ));
            animatedPieces.add(new AnimatedPiece(
                enPassant.capturedPawn,
                enPassant.capturedPawn.getSpot(),
                enPassant.capturedPawn.getSpot(),
                this.animationSeconds * 0.9f
            ));
        } else if (move instanceof Castling castling) {
            animatedPieces.add(new AnimatedPiece(
                castling.king,
                castling.kingOrigin,
                castling.kingDestination,
                this.animationSeconds
            ));
            animatedPieces.add(new AnimatedPiece(
                castling.rook,
                castling.rookOrigin,
                castling.rookDestination,
                this.animationSeconds
            ));
        } else if (move instanceof Promotion promotion) {
            animatedPieces.add(new AnimatedPiece(
                promotion.promotingPawn,
                promotion.promotingPawn.getSpot(),
                promotion.getDestination(),
                this.animationSeconds
            ));

            if (promotion.optionalCapturedPiece != null) {
                animatedPieces.add(new AnimatedPiece(
                    promotion.optionalCapturedPiece,
                    promotion.getDestination(),
                    promotion.getDestination(),
                    this.animationSeconds * 0.9f
                ));
            }
        }
    }

    private void update(ChessGame chessGame) {
        if (chessGame.getMovesMade().size() > this.nextMoveIndex) {
            this.add(chessGame.getMovesMade().getLast());
            this.nextMoveIndex++;
        }
        float secondsElapsed = Gdx.graphics.getDeltaTime();
        Iterator<AnimatedPiece> iterator = animatedPieces.iterator();
        while (iterator.hasNext()) {
            AnimatedPiece animatedPiece = iterator.next();
            animatedPiece.update(secondsElapsed);
            if (animatedPiece.isDone()) {
                iterator.remove();
            }
        }
    }
    static class AnimatedPiece {
        private float secondsRemaining;
        private final com.badlogic.gdx.math.Vector2 position;
        private final com.badlogic.gdx.math.Vector2 direction;
        private final Piece piece;

        AnimatedPiece(
            Piece piece,
            Vector2 origin,
            Vector2 destination,
            float animationSeconds) {
            this.piece = piece;
            this.position = new com.badlogic.gdx.math.Vector2(origin.x, origin.y);
            this.direction = new com.badlogic.gdx.math.Vector2(destination.x, destination.y).sub(position);
            this.direction.scl(1 / animationSeconds);
            this.secondsRemaining = animationSeconds;
        }

        public boolean isDone() {
            return this.secondsRemaining <= 0;
        }

        public void update(float secondsElapsed) {
            this.secondsRemaining -= secondsElapsed;
            position.mulAdd(direction, secondsElapsed);
        }
    }
}
