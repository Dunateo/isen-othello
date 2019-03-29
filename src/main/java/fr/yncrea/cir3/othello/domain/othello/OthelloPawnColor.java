package fr.yncrea.cir3.othello.domain.othello;

public enum OthelloPawnColor {
	WHITE, BLACK;
	
	public OthelloPawnColor next() {
		switch (this) {
		case BLACK:
			return WHITE;
		case WHITE:
			return BLACK;
		default:
			return null;
		}
	}
}
