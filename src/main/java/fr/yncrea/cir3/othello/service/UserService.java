package fr.yncrea.cir3.othello.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.form.UserForm;
import fr.yncrea.cir3.othello.repository.OthelloGameRepository;
import fr.yncrea.cir3.othello.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository users;

	@Autowired
	private OthelloGameRepository games;

	public UserForm createForm(User user) {
		UserForm form = new UserForm();
		if (user == null) {
			return form;
		}

		form.setId(user.getId());
		form.setFirstname(user.getFirstname());
		form.setName(user.getName());
		form.setEmail(user.getEmail());
		form.setUsername(user.getUsername());

		return form;
	}

	public User update(User user, UserForm form) {
		if (user == null) {
			user = new User();
		}

		user.setFirstname(form.getFirstname());
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setUsername(form.getUsername());

		return user;
	}

	@Transactional
	public void remove(Long id) {
		Optional<User> userOpt = users.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			games.deleteByBlackOrWhite(user, user);
			users.delete(user);
		}
	}
}
