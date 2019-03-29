package fr.yncrea.cir3.othello.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import fr.yncrea.cir3.othello.form.UserForm;
import fr.yncrea.cir3.othello.repository.UserRepository;
import fr.yncrea.cir3.othello.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository users;
	
	@Autowired
	private UserService service;
	
	@GetMapping({ "", "/" })
	public String list(Model model, @PageableDefault(page=0, size=10, sort = "username") Pageable pageable) {
		model.addAttribute("users", users.findAll(pageable));
		return "user/list";
	}
	
	@GetMapping({ "/add", "edit/{id}" })
	public String edit(@PathVariable(required = false) Long id, Model model) {
		User object = null;
		if (id != null) {
			object = users.findById(id).orElse(null);
		}
		
		model.addAttribute("user", service.createForm(object));
		return "user/edit";
	}
	
	@PostMapping("/edit")
	public String addForm(@Valid @ModelAttribute("user") UserForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", form);
			return "user/edit";
		}

		User object = null;
		if (form.getId() != null) {
			object = users.findById(form.getId()).orElse(null);
		}

		users.save(service.update(object, form));

		return "redirect:/user";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.remove(id);

		return "redirect:/user";
	}
}
