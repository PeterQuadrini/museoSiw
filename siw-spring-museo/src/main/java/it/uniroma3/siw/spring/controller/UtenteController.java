package it.uniroma3.siw.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.service.UtenteService;

@Controller
public class UtenteController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private OperaService operaService;

	@Autowired
	private UtenteService utenteService;
	

	@RequestMapping(value = "/aggiungiPreferiti/{id}", method = RequestMethod.GET)
	public String addPreferiti(Model model, @PathVariable("id") Long id) {
		Utente utente = getUtente();
		Opera opera = this.operaService.findById(id);
		if (utente.getOperePreferite().contains(opera)) {
			return "/opere";
		}
		utente.addPreferita(opera);
		this.utenteService.save(utente);

		model.addAttribute("operePreferite", utente.getOperePreferite());
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "opere.html";
		
	}

	@RequestMapping(value = "/rimuoviPreferiti/{id}", method = RequestMethod.GET)
	public String rimuoviPreferiti(Model model, @PathVariable("id") Long id) {
		Utente utente = getUtente();
		Opera opera = this.operaService.findById(id);
		utente.remove(opera);
		this.utenteService.save(utente);

		model.addAttribute("operePreferite", utente.getOperePreferite());
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "/opere";
	}

	@RequestMapping(value = "/opere", method = RequestMethod.GET)
	public String goOpere(Model model) {
		Utente utente = getUtente();
		model.addAttribute("operePreferite", utente.getOperePreferite());
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "/opere";
	}

	
	
	
	
	
	
	@RequestMapping(value="/operePreferite", method=RequestMethod.GET)
	public String operePreferite(Model model) {
		Utente utente = getUtente();
		model.addAttribute("operePreferite", utente.getOperePreferite());
		return "operePreferite.html";
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
