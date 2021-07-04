package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Ticket;


public interface TicketRepository extends CrudRepository<Ticket, Long> {
	
	Ticket findTicketByLocalDate(LocalDate localDate);
	
}
