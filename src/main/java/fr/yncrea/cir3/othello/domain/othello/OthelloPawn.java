package fr.yncrea.cir3.othello.domain.othello;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class OthelloPawn {
	private int x;
	private int y;
	
	@Enumerated(EnumType.STRING)
	private OthelloPawnColor color;

	public OthelloPawn() {}
	
	public OthelloPawn(int x, int y, OthelloPawnColor color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}
}
