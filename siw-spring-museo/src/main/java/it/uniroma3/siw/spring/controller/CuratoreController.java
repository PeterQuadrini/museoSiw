package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.validator.CuratoreValidator;

@Controller
public class CuratoreController {
	
	@Autowired
	private CuratoreValidator curatoreValidator;
	
	@Autowired
	private CuratoreService curatoreService;
	
	@RequestMapping(value="/creaCuratore", method=RequestMethod.GET)
	public String creaCuratore(Model model) {
		model.addAttribute("curatore", new Curatore());
		return "creaCuratore.html";
	}
	
	@RequestMapping(value="/creaCuratore", method=RequestMethod.POST)
	public String creaCuratore(Model model, @ModelAttribute Curatore curatore, BindingResult bindingResult) {
		this.curatoreValidator.validate(curatore, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.curatoreService.save(curatore);
			return "gestisciCollezioni.html";
		}
		return "creaCuratore.html";
	}

}
