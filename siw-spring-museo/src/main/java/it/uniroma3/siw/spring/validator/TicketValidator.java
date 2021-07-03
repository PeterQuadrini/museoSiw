package it.uniroma3.siw.spring.validator;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Ticket;

public class TicketValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
        return Ticket.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ticket ticket = (Ticket) o;
		
		if(ticket.getLocalDate().isAfter(LocalDate.now()))
			errors.rejectValue("date", "incompatibile");
		
	}

}
