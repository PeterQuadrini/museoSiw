package it.uniroma3.siw.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Ticket;
import it.uniroma3.siw.spring.service.TicketService;

@Component
public class TicketValidator implements Validator {

	@Autowired
	private TicketService ticketService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ticket.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ticket ticket = (Ticket) o;

		if (ticket.getLocalDate() != null)
			if (ticket.getLocalDate().isBefore(LocalDate.now()))
				errors.rejectValue("localDate", "incompatibile");
		if (ticket.getLocalDate() == null)
			errors.rejectValue("localDate", "required");
		if (ticket.getLocalDate() != null)
			if (this.ticketService.findByDate(ticket.getLocalDate()) != null)
				errors.rejectValue("localDate", "exists");
		if (ticket.getNumero_posti() < 1)
			errors.rejectValue("numero_posti", "incompatibile");
	}

}
