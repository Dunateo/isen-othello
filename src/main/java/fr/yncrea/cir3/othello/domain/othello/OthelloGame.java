package fr.yncrea.cir3.othello.domain.othello;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import fr.yncrea.cir3.othello.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "othello_game")
public class OthelloGame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime created = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private OthelloGameSize size;
	
	@ManyToOne(optional = false)
	private User white;
	
	@ManyToOne(optional = false)
	private User black;
	
	@Enumerated(EnumType.STRING)
	private OthelloPawnColor turn = OthelloPawnColor.BLACK;
	
	@Enumerated(EnumType.STRING)
	private OthelloGameStatus status = OthelloGameStatus.STARTED;
	
	private int whiteScore;
	private int blackScore;
	
	@ManyToOne
	private User winner;
	
	@OrderColumn
	@ElementCollection
	@CollectionTable(name = "othello_game_pawn")
	private List<OthelloPawn> pawns;
	
	public void addPawn(OthelloPawn pawn) {
		if (pawns == null) {
			pawns = new ArrayList<>();
		}
		
		pawns.add(pawn);
	}
	
	public OthelloPawn getPawnAt(int x, int y) {
		if (pawns == null) {
			return null;
		}
		
		for (OthelloPawn pawn : pawns) {
			if (pawn.getX() == x && pawn.getY() == y) {
				return pawn;
			}
		}
		
		return null;
	}
	
	public OthelloPawnColor getPawnColorAt(int x, int y) {
		OthelloPawn pawn = getPawnAt(x, y);
		if (pawn == null) {
			return null;
		}
		
		return pawn.getColor();
	}
	
	public void updateScore() {
		whiteScore = (int) pawns.stream().filter(e -> e.getColor() == OthelloPawnColor.WHITE).count();
		blackScore = (int) pawns.stream().filter(e -> e.getColor() == OthelloPawnColor.BLACK).count();
	}
}
