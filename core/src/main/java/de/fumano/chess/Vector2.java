package de.fumano.chess;

import java.util.Objects;

public class Vector2 {

    public final int x;
    public final int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this(0, 0);
    }

    public Vector2 addX(int x) {
        return new Vector2(this.x + x, this.y);
    }

    public Vector2 addY(int y) {
        return new Vector2(this.x, this.y + y);
    }

    public Vector2 add(int x, int y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 add(Direction direction) {
        return this.add(direction.offset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 spot = (Vector2) o;
        return x == spot.x && y == spot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Spot(%d, %d)".formatted(x, y);
    }
}
