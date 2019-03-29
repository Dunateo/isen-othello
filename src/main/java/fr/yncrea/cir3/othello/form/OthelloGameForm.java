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
}
