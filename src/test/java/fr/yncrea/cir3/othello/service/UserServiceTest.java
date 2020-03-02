package fr.yncrea.cir3.othello.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.form.UserForm;
import fr.yncrea.cir3.othello.repository.OthelloGameRepository;
import fr.yncrea.cir3.othello.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository users;
	
	@Mock
	private OthelloGameRepository games;
	
	@InjectMocks
	private UserService service;
	
	@Test
	public void testCreateFormFromNull() {
		UserService service = new UserService();
		
		// call service
		UserForm result = service.createForm(null);
		
		// check result
		assertNotNull(result);
	}
	
	@Test
	public void testCreateFormFromUser() {
		UserService service = new UserService();
		
		// create sample user
		User u = new User();
		u.setId(3L);
		u.setEmail("sample@email.com");
		u.setFirstname("Jonh");
		u.setName("Doe");
		u.setUsername("Unknown");
		
		// call service
		UserForm result = service.createForm(u);
		
		// check result
		assertNotNull(result);
		assertEquals(new Long(3L), result.getId());
		assertEquals("sample@email.com", result.getEmail());
		assertEquals("Jonh", result.getFirstname());
		assertEquals("Doe", result.getName());
		assertEquals("Unknown", result.getUsername());
	}
	
	@Test
	public void testRemoveValidUser() {
		User u = new User();
		u.setId(1L);
		
		// Will return our user
		Mockito.when(users.findById(Mockito.anyLong())).thenReturn(Optional.of(u));
		
		service.remove(1L);
		// check that user is pulled
		Mockito.verify(users, Mockito.times(1)).findById(Mockito.eq(1L));
		// check that games are deleted for the user
		Mockito.verify(games, Mockito.times(1)).deleteByBlackOrWhite(Mockito.eq(u), Mockito.eq(u));
		// check that user is deleted
		Mockito.verify(users, Mockito.times(1)).delete(Mockito.eq(u));
	}
}

