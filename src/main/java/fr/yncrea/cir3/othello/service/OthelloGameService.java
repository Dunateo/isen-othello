package fr.yncrea.cir3.othello.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.domain.othello.OthelloGame;
import fr.yncrea.cir3.othello.domain.othello.OthelloGameSize;
import fr.yncrea.cir3.othello.domain.othello.OthelloGameStatus;
import fr.yncrea.cir3.othello.domain.othello.OthelloPawn;
import fr.yncrea.cir3.othello.domain.othello.OthelloPawnColor;
import fr.yncrea.cir3.othello.exception.othello.OthelloCreateGameException;
import fr.yncrea.cir3.othello.exception.othello.OthelloPlayException;

@Service
public class OthelloGameService {
	public OthelloGame create(User white, User black, OthelloGameSize size) {
		if (white == null || black == null) {
			throw new OthelloCreateGameException("La partie doit contenir 2 joueurs");
		}

		if (Objects.equals(white.getId(), black.getId())) {
			throw new OthelloCreateGameException("Les 2 joueurs doivent être différents");
		}

		// create the new board
		OthelloGame game = new OthelloGame();
		game.setWhite(white);
		game.setBlack(black);
		game.setSize(size);

		// add first 4 pawn
		game.addPawn(new OthelloPawn(size.getWidth() / 2, size.getHeight() / 2, OthelloPawnColor.BLACK));
		game.addPawn(new OthelloPawn(size.getWidth() / 2 - 1, size.getHeight() / 2 - 1, OthelloPawnColor.BLACK));
		game.addPawn(new OthelloPawn(size.getWidth() / 2, size.getHeight() / 2 - 1, OthelloPawnColor.WHITE));
		game.addPawn(new OthelloPawn(size.getWidth() / 2 - 1, size.getHeight() / 2, OthelloPawnColor.WHITE));
		
		game.updateScore();

		return game;
	}

	public OthelloGame play(OthelloGame game, OthelloPawn pawn) {
		// get list of allowed moves
		List<OthelloPawn> allowedMoves = allowedMoves(game);

		isMoveValid(game, new OthelloPawn(3, 5, OthelloPawnColor.BLACK));

		// check if current move is listed
		if (!allowedMoves.stream().anyMatch(e -> e.equals(pawn))) {
			throw new OthelloPlayException("Coup non authorisé");
		}

		// play the move
		game.addPawn(pawn);
		getPawnToflip(game, pawn).forEach(e -> e.setColor(pawn.getColor()));

		// prepare for next player
		nextTurn(game);

		return game;
	}

	public List<OthelloPawn> allowedMoves(OthelloGame game) {
		List<OthelloPawn> moves = new ArrayList<>();

		// check all possibilities
		for (int x = 0; x < game.getSize().getWidth(); x++) {
			for (int y = 0; y < game.getSize().getWidth(); y++) {
				OthelloPawn pawn = new OthelloPawn(x, y, game.getTurn());

				// add valid moves
				if (isMoveValid(game, pawn)) {
					moves.add(pawn);
				}
			}
		}

		return moves;
	}

	public boolean isMoveValid(OthelloGame game, OthelloPawn pawn) {
		// cannot be on an other pawn
		if (game.getPawnColorAt(pawn.getX(), pawn.getY()) != null) {
			return false;
		}

		// must capture something
		if (getPawnToflip(game, pawn).isEmpty()) {
			return false;
		}

		// seems good
		return true;
	}

	private List<OthelloPawn> getPawnToflip(OthelloGame game, OthelloPawn pawn) {
		List<OthelloPawn> flipped = new ArrayList<>();

		// check all vectors
		for (int vx = -1; vx <= 1; vx++) {
			for (int vy = -1; vy <= 1; vy++) {
				flipped.addAll(flipCollect(game, pawn.getX(), pawn.getY(), pawn.getColor(), vx, vy));
			}
		}

		return flipped;
	}

	private Collection<OthelloPawn> flipCollect(OthelloGame game, int x, int y, OthelloPawnColor color, int vx,
			int vy) {
		List<OthelloPawn> pawns = new ArrayList<>();

		boolean done = false;
		do {
			x += vx;
			y += vy;

			OthelloPawn pawn = game.getPawnAt(x, y);
			if (pawn != null && pawn.getColor() == color) {
				// pawn of the same color, stop collecting
				done = true;
			} else if (pawn != null && pawn.getColor() != color) {
				// collect pawns of a different color
				pawns.add(pawn);
			} else {
				// no pawn found, empty collection and stop
				pawns.clear();
				done = true;
			}
		} while (!done);

		return pawns;
	}

	private void nextTurn(OthelloGame game) {
		// update scores
		game.updateScore();
		
		// test if new player can play
		game.setTurn(game.getTurn().next());
		if (!allowedMoves(game).isEmpty() ) {
			return;
		}

		// if not, switch again
		game.setTurn(game.getTurn().next());
		if (!allowedMoves(game).isEmpty()) {
			return;
		}
		
		// still nothing... then the party has ended
		// time to find a winner
		game.setTurn(null);
		game.setStatus(OthelloGameStatus.FINISHED);
		if (game.getWhiteScore() > game.getBlackScore()) {
			// white winner
			game.setWinner(game.getWhite());
		} else if (game.getWhiteScore() < game.getBlackScore()) {
			// black winner
			game.setWinner(game.getBlack());
		} else {
			// draw
			game.setWinner(null);
		}
	}
}
