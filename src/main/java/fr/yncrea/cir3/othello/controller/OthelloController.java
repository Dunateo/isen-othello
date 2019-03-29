package fr.yncrea.cir3.othello.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.domain.othello.OthelloGame;
import fr.yncrea.cir3.othello.domain.othello.OthelloGameSize;
import fr.yncrea.cir3.othello.domain.othello.OthelloPawn;
import fr.yncrea.cir3.othello.exception.othello.OthelloCreateGameException;
import fr.yncrea.cir3.othello.exception.othello.OthelloPlayException;
import fr.yncrea.cir3.othello.form.OthelloGameForm;
import fr.yncrea.cir3.othello.repository.OthelloGameRepository;
import fr.yncrea.cir3.othello.repository.UserRepository;
import fr.yncrea.cir3.othello.service.OthelloGameService;

@Controller
@RequestMapping("/othello")
public class OthelloController {
	@Autowired
	private OthelloGameService service;
	
	@Autowired
	private OthelloGameRepository games;
	
	@Autowired
	private UserRepository users;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault(page=0, size=10, sort = "created", direction = Direction.DESC) Pageable pageable) {
		model.addAttribute("games", games.findAll(pageable));
		
		return "othello/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("game", new OthelloGameForm());
		model.addAttribute("sizes", OthelloGameSize.values());
		model.addAttribute("users", users.findAll(Sort.by("username")));
		
		return "othello/create";
	}
	
	@PostMapping("/create")
	public String start(@Valid @ModelAttribute("game") OthelloGameForm form, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				throw new OthelloCreateGameException("Impossible de créer la partie");
			}
			
			User white = users.findById(form.getWhite()).orElse(null);
			User black = users.findById(form.getBlack()).orElse(null);
			OthelloGameSize size = OthelloGameSize.valueOf(form.getSize());
			
			OthelloGame game = service.create(white, black, size);
			games.save(game);
			
			return "redirect:/othello/play/" + game.getId();
		} catch (OthelloCreateGameException e) {
			model.addAttribute("game", form);
			model.addAttribute("sizes", OthelloGameSize.values());
			model.addAttribute("users", users.findAll(Sort.by("username")));
			model.addAttribute("message", e.getMessage());

			return "othello/create";
		}
	}
	
	@GetMapping("/play/{id}")
	public String create(@PathVariable Long id, Model model) {
		model.addAttribute("game", games.findById(id).orElseThrow(() -> new RuntimeException("Not found")));
		
		return "othello/play";
	}
	
	@GetMapping("/play/{id}/{x}/{y}")
	public String play(@PathVariable Long id, @PathVariable int x, @PathVariable int y, Model model) {
		OthelloGame game = games.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		
		try {
			service.play(game, new OthelloPawn(x, y, game.getTurn()));
			games.save(game);
			
			return "redirect:/othello/play/" + id;
		} catch (OthelloPlayException e) {
			model.addAttribute("game", game);
			model.addAttribute("message", e.getMessage());
			
			return "othello/play";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		games.deleteById(id);

		return "redirect:/othello/list";
	}
}
