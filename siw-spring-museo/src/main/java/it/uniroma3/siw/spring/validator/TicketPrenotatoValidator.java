package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.TicketPrenotato;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CredentialsService;

@Component
public class TicketPrenotatoValidator implements Validator {

	
	@Autowired
	private CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return TicketPrenotato.class.equals(clazz); 
	}

	@Override
	public void validate(Object o, Errors errors) {
		TicketPrenotato ticket = (TicketPrenotato) o;
		for(TicketPrenotato tic : getUtente().getTicket())
			if(tic.getTicket().getId() == ticket.getTicket().getId()) {
			errors.rejectValue("utente", "exists");
			}

	}
	
	
	
	private Utente getUtente() {
		// retrieve current user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		if (this.credentialsService.getCredentials(username) == null)
			return new Utente();
		Credentials c = this.credentialsService.getCredentials(username);
		Utente cliente = c.getUtente();
		return cliente;
	}

}
