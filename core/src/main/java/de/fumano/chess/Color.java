package de.fumano.chess;

public enum Color {
    WHITE {
        @Override
        public String toString() {
            return "White";
        }
    }, BLACK {
        @Override
        public String toString() {
            return "Black";
        }
    };

    public Color getOpponent() {
        return this.equals(Color.WHITE) ? Color.BLACK: Color.WHITE;
    }
}
