package it.uniroma3.siw.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Ticket;
import it.uniroma3.siw.spring.repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Transactional
	public List<Ticket> findAllTickets(){
		return (List<Ticket>) this.ticketRepository.findAll();
	}
	
	@Transactional
	public Ticket findById(Long id) {
		Optional<Ticket> ticket = this.ticketRepository.findById(id);
		return ticket.orElse(null);
	}
	
	@Transactional
	public void save(Ticket ticket) {
		this.ticketRepository.save(ticket);
	}
	
	@Transactional
	public void delete(Ticket ticket) {
		this.ticketRepository.delete(ticket);
	}
	
	@Transactional
	public Ticket findByDate(LocalDate localDate) {
		return this.ticketRepository.findTicketByLocalDate(localDate);
	}
	

}
