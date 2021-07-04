package it.uniroma3.siw.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.TicketPrenotato;
import it.uniroma3.siw.spring.repository.TicketPrenotatoRepository;

@Service
public class TicketPrenotatoService {
	
	@Autowired
	private TicketPrenotatoRepository tPrepository;
	
	@Transactional
	public void save(TicketPrenotato ticketP) {
		this.tPrepository.save(ticketP);
	}

	@Transactional
	public void delete(TicketPrenotato ticketP) {
		this.tPrepository.delete(ticketP);
	}
	
	@Transactional
	public TicketPrenotato findById(Long id) {
		Optional<TicketPrenotato> ticket = this.tPrepository.findById(id);
		return ticket.orElse(null);
	}
	
	@Transactional
	public TicketPrenotato findByUtenteId(Long id) {
		return this.tPrepository.findByUtenteId(id);
	}
}
