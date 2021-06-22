package it.uniroma3.siw.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Utente;

@Component
public class UtenteValidator implements Validator {

    private static final int MAX_LENGTH_NOME_COGNOME = 15;
    private static final int MIN_LENGTH_NOME_COGNOME = 4;

	
	@Override
	public void validate(Object o, Errors errors) {
		Utente utente = (Utente) o;
		String nome = utente.getNome();
		String cognome = utente.getCognome();
		
		if(nome.isEmpty())
			errors.rejectValue("nome", "required");
		if(cognome.isEmpty())
			errors.rejectValue("cognome", "required");
		
		if(nome.length() < MIN_LENGTH_NOME_COGNOME || nome.length() > MAX_LENGTH_NOME_COGNOME)
			errors.rejectValue("nome", "size");
		
		if(cognome.length() < MIN_LENGTH_NOME_COGNOME || cognome.length() > MAX_LENGTH_NOME_COGNOME)
			errors.rejectValue("cognome", "size");
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}
}
