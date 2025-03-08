package de.fumano.chess.movement;

import de.fumano.chess.Vector2;

import java.util.List;
import java.util.stream.Stream;

public class Directions {

    public static final List<Vector2> Castling = List.of(new Vector2(-4, 0), new Vector2(3, 0));
    public static final List<Vector2> WhitePawnCapture = List.of(new Vector2(1, 1), new Vector2(-1, 1));
    public static final List<Vector2> BlackPawnCapture = List.of(new Vector2(1, -1), new Vector2(-1, -1));
    public static final List<Vector2> WhitePawnStep = List.of(new Vector2(0, 1));
    public static final List<Vector2> BlackPawnStep = List.of(new Vector2(0 , -1));

    public static final List<Vector2> Parallel = List.of(
        new Vector2(1, 0),
        new Vector2(-1, 0),
        new Vector2(0, 1),
        new Vector2(0, -1)
    );

    public static final List<Vector2> Diagonal = List.of(
        new Vector2(1, 1),
        new Vector2(1, -1),
        new Vector2(-1, 1),
        new Vector2(-1, -1)
    );

    public static final List<Vector2> All = Stream.concat(Parallel.stream(), Diagonal.stream()).toList();

    public static final List<Vector2> Knight = List.of(
        new Vector2(1, 2),
        new Vector2(1, -2),
        new Vector2(-1, 2),
        new Vector2(-1, -2),
        new Vector2(2, 1),
        new Vector2(2, -1),
        new Vector2(-2, 1),
        new Vector2(-2, -1)
    );
}
