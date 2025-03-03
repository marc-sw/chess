package de.fumano.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fumano.chess.move.Move;
import de.fumano.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Renderer {

    private final Sprite boardSprite;
    private final List<Texture> textures;
    private final HashMap<String, Sprite> pieceSprites;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final BitmapFont bitmapFont;
    private final BitmapFont bigFont;
    private final GlyphLayout glyphLayout;
    private de.fumano.chess.Color perspective;

    public Renderer(Viewport viewport) {
        this.viewport = viewport;
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.pieceSprites = new HashMap<>(12);
        this.textures = new ArrayList<>(13);
        this.boardSprite = new Sprite();
        this.bitmapFont = new BitmapFont();
        this.bigFont = new BitmapFont();
        this.bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.bitmapFont.getData().setScale(0.015f * Chess.WORLD_SCALE);
        this.bigFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.bigFont.getData().setScale(0.03f * Chess.WORLD_SCALE);
        this.glyphLayout = new GlyphLayout(bigFont, "");
        this.loadRessources();
    }

    private void loadRessources() {
        String path = "Chess/Wooden_2/Pieces/";
        String[] files = new String[] {
            "Black_Bishop.png",
            "Black_King.png",
            "Black_Knight.png",
            "Black_Pawn.png",
            "Black_Queen.png",
            "Black_Rook.png",
            "White_Bishop.png",
            "White_King.png",
            "White_Knight.png",
            "White_Pawn.png",
            "White_Queen.png",
            "White_Rook.png"
        };
        for (FileHandle fileHandle: Arrays.stream(files).map(f -> Gdx.files.internal(path + f)).toList()) {
            Texture texture = new Texture(fileHandle);
            Sprite sprite = new Sprite(texture);
            sprite.setSize(Chess.WORLD_SCALE, Chess.WORLD_SCALE);
            sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

            this.textures.add(texture);
            this.pieceSprites.put(fileHandle.nameWithoutExtension(), sprite);
        }

        Texture boardTexture = new Texture("Chess/Wooden_2/Boards/4.png");
        boardSprite.setRegion(boardTexture);
        boardSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        this.textures.add(boardTexture);
    }

    public void renderPiece(Piece piece, Vector2 spot) {
        Sprite sprite = pieceSprites.get(piece.toString());
        sprite.setPosition(spot.x * Chess.WORLD_SCALE + Chess.BOARD_OFFSET, spot.y * Chess.WORLD_SCALE + Chess.BOARD_OFFSET);
        sprite.draw(spriteBatch);
    }

    public void renderPiece(Piece piece) {
        renderPiece(piece, piece.getSpot());
    }

    public void renderTimers(int whiteSeconds, int blackSeconds) {
        bitmapFont.draw(spriteBatch, "%02d:%02d".formatted(whiteSeconds / 60, whiteSeconds % 60), Chess.WHITE_TIMER_X, Chess.WHITE_TIMER_Y);
        bitmapFont.draw(spriteBatch, "%02d:%02d".formatted(blackSeconds / 60, blackSeconds % 60), Chess.BLACK_TIMER_X, Chess.BLACK_TIMER_Y);
    }

    public void renderEmptyBoard() {
        boardSprite.draw(spriteBatch);
    }

    public void renderPieces(Board board) {
        board.iterate(this::renderPiece);
    }

    public void highlightPiece(Piece piece) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(piece.getSpot().x * Chess.WORLD_SCALE + Chess.BOARD_OFFSET,
            piece.getSpot().y * Chess.WORLD_SCALE + Chess.BOARD_OFFSET, Chess.WORLD_SCALE, Chess.WORLD_SCALE);
        shapeRenderer.setColor(Color.GREEN);
        for (Move move: piece.getAllMoves()) {
            shapeRenderer.rect(move.getDestination().x * Chess.WORLD_SCALE + Chess.BOARD_OFFSET,
                move.getDestination().y * Chess.WORLD_SCALE + Chess.BOARD_OFFSET, Chess.WORLD_SCALE, Chess.WORLD_SCALE);
        }
    }

    public void renderTextAtCenter(String text) {
        glyphLayout.setText(bigFont, text);
        bigFont.draw(spriteBatch, glyphLayout, Chess.WORLD_CENTER - glyphLayout.width/2, Chess.WORLD_CENTER + glyphLayout.height/2);
    }

    public void useOnSpriteBatch(Runnable runnable) {
        viewport.apply();
        this.spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        this.spriteBatch.begin();
        runnable.run();
        this.spriteBatch.end();
    }

    public void useOnShapeRenderer(Runnable runnable) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        runnable.run();
        shapeRenderer.end();
    }


    public void dispose() {
        textures.forEach(Texture::dispose);
        spriteBatch.dispose();
    }
}
