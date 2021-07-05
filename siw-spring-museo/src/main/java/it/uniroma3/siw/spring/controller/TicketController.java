package it.uniroma3.siw.spring.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Ticket;
import it.uniroma3.siw.spring.model.TicketPrenotato;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.TicketPrenotatoService;
import it.uniroma3.siw.spring.service.TicketService;
import it.uniroma3.siw.spring.validator.TicketPrenotatoValidator;
import it.uniroma3.siw.spring.validator.TicketValidator;

@Controller
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private TicketPrenotatoService ticketPrenotatoService;

	@Autowired
	private TicketValidator ticketValidator;

	@Autowired
	private TicketPrenotatoValidator ticketPrenotatoValidator;

	@RequestMapping(value = "/prenotaTicket", method = RequestMethod.GET)
	public String prenotaTicket(Model model) {
		model.addAttribute("ticketDisponibili", this.ticketService.findAllTickets());
		model.addAttribute("dataOggi", LocalDate.now());
		return "prenotaTicket.html";
	}

	@RequestMapping(value = "/prenota/{id}", method = RequestMethod.GET)
	public String prenotaG(Model model, @PathVariable Long id) {
		Utente utente = getUtente();
		Ticket ticket = this.ticketService.findById(id);
		if (ticket.getPosti_disponibili() < 10)
			model.addAttribute("upper_bound", ticket.getPosti_disponibili());
		else
			model.addAttribute("upper_bound", 10);

		model.addAttribute("ticketPrenotato", new TicketPrenotato());
		model.addAttribute("utente", utente);
		model.addAttribute("ticket", ticket);
		return "prenotaForm.html";
	}

	@RequestMapping(value = "/prenota/{id}", method = RequestMethod.POST)
	public String prenotaP(Model model, @PathVariable Long id, @ModelAttribute("ticket") TicketPrenotato ticketP,
			BindingResult bindinResult) {
		Ticket ticket = this.ticketService.findById(id);
		ticketP.setTicket(ticket);
		this.ticketPrenotatoValidator.validate(ticketP, bindinResult);
		if (!bindinResult.hasErrors()) {
			Utente utente = getUtente();
			ticketP.setTicket(ticket);
			ticketP.setUtente(utente);
			ticket.setPosti_disponibili(ticket.getPosti_disponibili() - ticketP.getPosti_prenotati());
			this.ticketPrenotatoService.save(ticketP);
			model.addAttribute("ticketDisponibili", this.ticketService.findAllTickets());
			model.addAttribute("dataOggi", LocalDate.now());
			return "prenotaTicket.html";
		}
        Utente utente2 = getUtente();
		if (ticket.getPosti_disponibili() < 10)
			model.addAttribute("upper_bound", ticket.getPosti_disponibili());
		else
			model.addAttribute("upper_bound", 10);

		model.addAttribute("ticketPrenotato", new TicketPrenotato());
		model.addAttribute("utente", utente2);
		model.addAttribute("ticket", ticket);
		return "prenotaForm.html";

	}

	// ------------------------------------------ VISUALIZZA TICKET
	// -------------------------------------------------------

	@RequestMapping(value = "/viewTicket", method = RequestMethod.GET)
	public String viewTicket(Model model) {
		Utente utente = getUtente();
		model.addAttribute("ticketUtente", utente.getTicket());
		model.addAttribute("dataOggi", LocalDate.now());
		return "visualizzaTicket.html";
	}

	@RequestMapping(value = "/eliminaTicket/{id}", method = RequestMethod.GET)
	public String viewTicket(Model model, @PathVariable Long id) {
		Utente utente = getUtente();
		TicketPrenotato ticketP = this.ticketPrenotatoService.findById(id);
		ticketP.getTicket()
				.setPosti_disponibili(ticketP.getTicket().getPosti_disponibili() + ticketP.getPosti_prenotati());
		this.ticketPrenotatoService.delete(ticketP);
		model.addAttribute("ticketUtente", utente.getTicket());
		model.addAttribute("dataOggi", LocalDate.now());
		return "visualizzaTicket.html";
	}

	// ------------------------------------------ GESTISCI TICKET
	// -------------------------------------------------------

	@RequestMapping(value = "/gestisciTicket", method = RequestMethod.GET)
	public String goGestisciTicket(Model model) {
		return "gestisciTicket.html";
	}

	@RequestMapping(value = "/addTicket", method = RequestMethod.GET)
	public String addTicket(Model model) {
		model.addAttribute("ticket", new Ticket());
		return "addTicketForm.html";
	}

	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	public String addTicketP(Model model, @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult) {
		this.ticketValidator.validate(ticket, bindingResult);
		if (!bindingResult.hasErrors()) {
			ticket.setPosti_disponibili(ticket.getNumero_posti());
			this.ticketService.save(ticket);
			return "gestisciTicket.html";
		}
		return "addTicketForm.html";
	}

	@RequestMapping(value = "/rimuoviTicket", method = RequestMethod.GET)
	public String removeTicket(Model model) {
		model.addAttribute("ticketDisponibili", this.ticketService.findAllTickets());
		model.addAttribute("dataOggi", LocalDate.now());
		return "rimuoviTicket.html";
	}

	@RequestMapping(value = "/rimuovi/{id}", method = RequestMethod.GET)
	public String removeT(Model model, @PathVariable Long id) {
		Ticket ticket = this.ticketService.findById(id);
		this.ticketService.delete(ticket);
		model.addAttribute("ticket", this.ticketService.findAllTickets());
		return "rimuoviTicket.html";
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
