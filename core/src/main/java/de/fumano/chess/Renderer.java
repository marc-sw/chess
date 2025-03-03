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
        this.bitmapFont.getData().setScale(1.5f);
        this.bigFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.bigFont.getData().setScale(3.0f);
        this.glyphLayout = new GlyphLayout(bigFont, "");
        this.loadRessources();
    }

    private void loadRessources() {
        for (FileHandle fileHandle: Gdx.files.internal("Chess/Wooden_2/Pieces").list()) {
            Texture texture = new Texture(fileHandle);
            Sprite sprite = new Sprite(texture);
            sprite.setSize(100, 100);

            this.textures.add(texture);
            this.pieceSprites.put(fileHandle.nameWithoutExtension(), sprite);
        }

        Texture boardTexture = new Texture("Chess/Wooden_2/Boards/3.png");
        boardSprite.setRegion(boardTexture);
        boardSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        this.textures.add(boardTexture);
    }

    public void renderPiece(Piece piece, Vector2 spot) {
        Sprite sprite = pieceSprites.get(piece.toString());
        sprite.setPosition(spot.x * 100 + 50, spot.y * 100 + 50);
        sprite.draw(spriteBatch);
    }

    public void renderPiece(Piece piece) {
        renderPiece(piece, piece.getSpot());
    }

    public void renderTimers(int whiteSeconds, int blackSeconds) {
        bitmapFont.draw(spriteBatch, "%02d:%02d".formatted(whiteSeconds / 60, whiteSeconds % 60), 830, 33);
        bitmapFont.draw(spriteBatch, "%02d:%02d".formatted(blackSeconds / 60, blackSeconds % 60), 830, 883);
    }

    public void renderEmptyBoard() {
        boardSprite.draw(spriteBatch);
    }

    public void renderPieces(Board board) {
        board.iterate(this::renderPiece);
    }

    public void highlightPiece(Piece piece) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(piece.getSpot().x * 100 + 50f, piece.getSpot().y * 100 + 50f, 100, 100);
        shapeRenderer.setColor(Color.GREEN);
        for (Move move: piece.getAllMoves()) {
            shapeRenderer.rect(move.getDestination().x * 100 + 50f, move.getDestination().y * 100 + 50f, 100, 100);
        }
    }

    public void renderTextAtCenter(String text) {
        glyphLayout.setText(bigFont, text);
        bigFont.draw(spriteBatch, glyphLayout, 450 - glyphLayout.width/2, 450 + glyphLayout.height/2);
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
