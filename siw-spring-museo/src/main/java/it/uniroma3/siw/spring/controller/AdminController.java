package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class AdminController {
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping(value="/gestisciCollezioni", method=RequestMethod.GET)
	public String goGestisciCollezioni() {
		return "gestisciCollezioni.html";
	}
	
	@RequestMapping(value="/gestisciOpere", method=RequestMethod.GET)
	public String goGestisciOpere() {
		return "gestisciOpere.html";
	}
	
	
	
	
	
	@RequestMapping(value="/addOpera", method=RequestMethod.GET)
	public String addOpera(Model model) {
		Opera opera = new Opera();
		model.addAttribute("opera", opera);
		model.addAttribute("artisti", this.artistaService.getAllArtisti());
		return "aggiungiOpera.html";
	}
	
	@RequestMapping(value="/addOpera", method=RequestMethod.POST)
	public String addOperaPost(Model model, @ModelAttribute("opera") Opera opera) {
        
		this.operaService.save(opera);
		return "index.html";
	}
	
	@RequestMapping(value="/eliminaOpera", method=RequestMethod.GET)
	public String eliminaOpera(Model model) {
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "eliminaOpera.html";
	}
	@RequestMapping(value="/eliminaOpera/{id}", method=RequestMethod.GET)
	public String confermaEliminaOpera(Model model,@PathVariable Long id,
			@ModelAttribute("opera")Opera opera) {
		Opera o=this.operaService.findById(id);
		this.operaService.delete(o);
		return "gestisciOpere.html";
	}
	
	
	
	
	@RequestMapping(value="/addCollezione", method=RequestMethod.GET)
	public String getAddCollezioneForm(Model model) {
		Collezione collezione = new Collezione();
		model.addAttribute("collezione",collezione);
		return "aggiungiCollezione.html";
	}
	
	@RequestMapping(value="/addCollezione", method=RequestMethod.POST)
	public String postAddCollezioneForm(Model model, @ModelAttribute("collezione") Collezione collezione) {
		this.collezioneService.save(collezione);
		return "index.html";
	}
}
