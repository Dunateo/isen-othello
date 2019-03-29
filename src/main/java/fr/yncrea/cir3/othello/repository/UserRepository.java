package fr.yncrea.cir3.othello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yncrea.cir3.othello.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
