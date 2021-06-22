package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	private static final int MAX_LENGTH_USERNAME = 15;
	private static final int MIN_LENGTH_USERNAME = 4;
	private static final int MAX_LENGTH_PASSWORD = 16;
	private static final int MIN_LENGTH_PASSWORD = 8;

	@Autowired
	private CredentialsService credentialsService;
	
	@Override
	public void validate(Object o, Errors errors) {
		Credentials credentials = (Credentials) o;
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (username.isEmpty())
			errors.rejectValue("username", "required");
		if (password.isEmpty())
			errors.rejectValue("password", "required");

		if (username.length() < MIN_LENGTH_USERNAME || username.length() > MAX_LENGTH_USERNAME)
			errors.rejectValue("username", "size");

		if (password.length() < MIN_LENGTH_PASSWORD || password.length() > MAX_LENGTH_PASSWORD)
			errors.rejectValue("password", "size");

		if(this.credentialsService.getCredentials(username) != null)
			errors.rejectValue("username", "duplicate");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}

}
