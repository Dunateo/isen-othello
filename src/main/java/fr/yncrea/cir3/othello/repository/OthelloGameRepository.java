package fr.yncrea.cir3.othello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.domain.othello.OthelloGame;

@Repository
public interface OthelloGameRepository extends JpaRepository<OthelloGame, Long> {
	Long deleteByBlackOrWhite(User black, User white);
}
