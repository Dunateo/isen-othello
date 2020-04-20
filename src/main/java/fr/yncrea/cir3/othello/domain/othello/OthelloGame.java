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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public OthelloGameSize getSize() {
		return size;
	}

	public void setSize(OthelloGameSize size) {
		this.size = size;
	}

	public User getWhite() {
		return white;
	}

	public void setWhite(User white) {
		this.white = white;
	}

	public User getBlack() {
		return black;
	}

	public void setBlack(User black) {
		this.black = black;
	}

	public OthelloPawnColor getTurn() {
		return turn;
	}

	public void setTurn(OthelloPawnColor turn) {
		this.turn = turn;
	}

	public OthelloGameStatus getStatus() {
		return status;
	}

	public void setStatus(OthelloGameStatus status) {
		this.status = status;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
	}

	public int getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
	}

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
	}

	public List<OthelloPawn> getPawns() {
		return pawns;
	}

	public void setPawns(List<OthelloPawn> pawns) {
		this.pawns = pawns;
	}
}
