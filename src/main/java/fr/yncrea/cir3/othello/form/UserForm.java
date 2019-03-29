package fr.yncrea.cir3.othello.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
	private Long id;
	
	@NotEmpty
	@Size(min = 0, max = 30)
	private String firstname;
	
	@NotEmpty
	@Size(min = 0, max = 30)
	private String name;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 2, max = 30)
	private String username;
}
