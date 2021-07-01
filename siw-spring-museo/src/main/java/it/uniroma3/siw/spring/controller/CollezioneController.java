package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CredentialsService;


@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CredentialsService credentialsService;

	
	@RequestMapping(value="/collezioni", method=RequestMethod.GET)
	public String goCollezioni(Model model) {
		model.addAttribute("collezioni", collezioneService.getAllCollezioni());
		return "collezioni.html";
	}
	
	@RequestMapping(value="/collezione/{id}", method=RequestMethod.GET)
	public String goCollezioneId(Model model, @PathVariable Long id) {
		Utente utente = getUtente();
		Collezione collezione = this.collezioneService.getCollezioneById(id);
		model.addAttribute("opere", collezione.getOpere());
		model.addAttribute("collezione", collezione);
		model.addAttribute("operePreferite",utente.getOperePreferite());
		return "OpereCollezione.html";
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
