package fr.yncrea.cir3.othello.domain.othello;

import lombok.Getter;

@Getter
public enum OthelloGameSize {
	EIGHT(8, 8), SIX(6, 6), FOUR(4, 4);
	
	private int width;
	private int height;
	
	private OthelloGameSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return String.format("%dx%s", width, height);
	}
}
