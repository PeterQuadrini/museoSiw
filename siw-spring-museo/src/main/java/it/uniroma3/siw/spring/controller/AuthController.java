package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.validator.CredentialsValidator;
import it.uniroma3.siw.spring.validator.UtenteValidator;

@Controller
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CredentialsValidator credentialValidator;
	
	@Autowired
	private UtenteValidator utenteValidator;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String goRegister(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("utente", new Utente());
		return "register.html";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String getRegisterForm(Model model, @ModelAttribute("credentials") Credentials credentials,
			BindingResult credBindingResult, @ModelAttribute("utente") Utente utente,
			BindingResult utBindingResult) {
		
		this.credentialValidator.validate(credentials, credBindingResult);
		this.utenteValidator.validate(utente, utBindingResult);
		
		if(!utBindingResult.hasErrors() && !credBindingResult.hasErrors()) {
			credentials.setUtente(utente);
			this.credentialsService.saveCredentials(credentials);
			return "index.html";
		}

 
		return "register.html";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutProcess() {
		return "index.html";
	}
	
	@RequestMapping(value="/default", method=RequestMethod.GET)
	public String defaultPage() {
		return "index.html";
	}
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String goLogin(Model model) {
		return "login.html";
	}
}
