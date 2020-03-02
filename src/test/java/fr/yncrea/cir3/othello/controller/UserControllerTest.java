package fr.yncrea.cir3.othello.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.StringContains;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository users;

	@org.junit.jupiter.api.Test
	public void testListUsers() throws Exception {
		List<User> us = new ArrayList<>();
		User u = new User();
		u.setFirstname("John");
		us.add(u);
		Page<User> pagedResponse = new PageImpl<>(us);
		
		Mockito.when(users.findAll(Mockito.any(Pageable.class))).thenReturn(pagedResponse);
		
		// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
		// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
		this.mockMvc.perform(get("/user"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"))
			.andExpect(content().string(StringContains.containsString("John")));
	}
}
