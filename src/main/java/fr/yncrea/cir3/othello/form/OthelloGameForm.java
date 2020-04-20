package fr.yncrea.cir3.othello.form;

import javax.validation.constraints.NotNull;

import fr.yncrea.cir3.othello.domain.othello.OthelloGameSize;
import fr.yncrea.cir3.othello.service.validation.constraints.StringEnumeration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OthelloGameForm {
	@NotNull
	private Long white;
	
	@NotNull
	private Long black;
	
	@StringEnumeration(enumClass = OthelloGameSize.class, message = "Taille invalide")
	private String size;

	public Long getWhite() {
		return white;
	}

	public void setWhite(Long white) {
		this.white = white;
	}

	public Long getBlack() {
		return black;
	}

	public void setBlack(Long black) {
		this.black = black;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
