package de.fumano.chess;

public enum Direction {
    UP(new Vector2(0, 1)),
    UP_RIGHT(new Vector2(1, 1)),
    RIGHT(new Vector2(1, 0)),
    DOWN_RIGHT(new Vector2(1, -1)),
    DOWN(new Vector2(0,-1)),
    DOWN_LEFT(new Vector2(-1, -1)),
    LEFT(new Vector2(-1, 0)),
    UP_LEFT(new Vector2(-1, 1));

    public static final Direction[] PARALLEL = {UP, RIGHT, DOWN, LEFT};
    public static final Direction[] DIAGONAL = {UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT};
    public static final Direction[] ALL = Direction.values();

    public final Vector2 offset;

    Direction(Vector2 offset) {
        this.offset = offset;
    }

}
