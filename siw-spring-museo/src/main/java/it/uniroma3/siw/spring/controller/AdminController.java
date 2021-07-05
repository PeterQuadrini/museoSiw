package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.service.UtenteService;
import it.uniroma3.siw.spring.validator.ArtistaValidator;

@Controller
public class AdminController {
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	
	@Autowired
	private CuratoreService curatoreService;
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	
	
	
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
	public String confermaEliminaOpera(Model model,@PathVariable Long id) {
		Utente utente = getUtente();
		Opera o=this.operaService.findById(id);
		utente.remove(o);
		this.utenteService.save(utente);
		this.operaService.delete(o);
		return "gestisciOpere.html";
	}
	
	
	
	
	
	
	
	
//	----------------------------- GESTIONE COLLEZIONI --------------------------------
	
	
	@RequestMapping(value="/gestisciCollezioni", method=RequestMethod.GET)
	public String goGestisciCollezioni() {
		return "gestisciCollezioni.html";
	}
	
	@RequestMapping(value="/addCollezione", method=RequestMethod.GET)
	public String getAddCollezioneForm(Model model) {
		Collezione collezione = new Collezione();
		model.addAttribute("curatori",this.curatoreService.findAll());
		model.addAttribute("collezione",collezione);
		
		return "aggiungiCollezione.html";
	}
	
	@RequestMapping(value="/addCollezione", method=RequestMethod.POST)
	public String postAddCollezioneForm(Model model, @ModelAttribute("collezione") Collezione collezione) {
		this.collezioneService.save(collezione);
		return "gestisciCollezioni.html";
	}
	
	@RequestMapping(value="/eliminaCollezione", method=RequestMethod.GET)
	public String goEliminaCollezione(Model model) {
		model.addAttribute("collezioni",this.collezioneService.getAllCollezioni());
		return "eliminaCollezione.html";
	}
	
	@RequestMapping(value="/eliminaCollezione/{id}", method=RequestMethod.GET)
	public String confirmEliminaCollezione(Model model, @PathVariable Long id) {
		Collezione collezione = this.collezioneService.getCollezioneById(id);
        if(this.operaService.tutteLeOpere().size()!=0) {
        	for(Opera o : this.operaService.tutteLeOpere())
        		o.setCollezione(null);
        }
		this.collezioneService.remove(collezione);
		return "gestisciCollezioni.html";
	}
	
	@RequestMapping(value="/addOperaCollezione", method=RequestMethod.GET)
	public String selectCollezione(Model model) {
		model.addAttribute("collezioni", this.collezioneService.getAllCollezioni());
		return "selezionaCollezione.html";
	}
	@RequestMapping(value="/modificaCollezione/{id}", method=RequestMethod.GET)
	public String modifyCollezione(Model model, @PathVariable Long id) {
		Collezione collezione = this.collezioneService.getCollezioneById(id);
		model.addAttribute("collezione",collezione);
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "aggiungiOperaCollezione.html";
	}
	
	@RequestMapping(value="/aggiungiACollezione/{id1}/{id2}", method=RequestMethod.GET)
	public String addOperaACollezione(Model model, @PathVariable("id1") Long id1,
			@PathVariable("id2")Long id2) {
		Collezione collezione = this.collezioneService.getCollezioneById(id1);
		Opera opera = this.operaService.findById(id2);
		opera.setCollezione(collezione);
		collezione.add(opera);
		this.operaService.save(opera);
		return "gestisciCollezioni.html";
	}
	
	
	@RequestMapping(value="/remOperaCollezione", method=RequestMethod.GET)
	public String selectCollezioneR(Model model) {
		model.addAttribute("collezioni", this.collezioneService.getAllCollezioni());
		return "selezionaCollezioneR.html";
	
	}


	@RequestMapping(value="/rimuoviCollezioneOpera/{id}", method=RequestMethod.GET)
	public String selezionaOperaRemove(Model model, @PathVariable Long id) {
		Collezione collezione = this.collezioneService.getCollezioneById(id);
		model.addAttribute("collezione",collezione);
		model.addAttribute("opere", this.operaService.tutteLeOpere());
		return "rimuoviOperaCollezione.html";
	}
	

	@RequestMapping(value="/rimuoviACollezione/{id1}/{id2}", method=RequestMethod.GET)
	public String remOperaACollezione(Model model, @PathVariable("id1") Long id1,
			@PathVariable("id2")Long id2) {
		Collezione collezione = this.collezioneService.getCollezioneById(id1);
		Opera opera = this.operaService.findById(id2);
		opera.setCollezione(null);
		collezione.remove(opera);
		this.operaService.save(opera);
		return "gestisciCollezioni.html";
	}
	
//	------------------------------------------ GESTISCI ARTISTI ------------------------------------------------------
	
	
	@RequestMapping(value="/gestisciArtisti", method=RequestMethod.GET)
	public String goGestisciArtisti(Model model) {
		return "gestisciArtisti.html";
	}
	
	@RequestMapping(value="/addArtista", method=RequestMethod.GET)
	public String addArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "newArtistaForm.html";
	}
	
	@RequestMapping(value="/addArtista", method=RequestMethod.POST)
	public String addArtistaP(Model model, @ModelAttribute("artista") Artista artista,
			BindingResult bindingResult) {
		
		this.artistaValidator.validate(artista, bindingResult);
		if(!bindingResult.hasErrors()) {
		this.artistaService.save(artista);
		return "gestisciArtisti.html";}
		
		return "default";
	}
	
	@RequestMapping(value="/eliminaArtista", method=RequestMethod.GET)
	public String eliminaArtista(Model model) {
		model.addAttribute("artisti", this.artistaService.getAllArtisti());
		return "eliminaArtistaId.html";
	}
	
	@RequestMapping(value="/eliminaArtista/{id}", method=RequestMethod.GET)
	public String eliminaArtistaID(Model model, @PathVariable Long id) {
		Artista artista = this.artistaService.findById(id);
		this.artistaService.remove(artista);
		return "gestisciArtisti.html";
	}
	
	
	
//   ----------------------------------------- GET UTENTE --------------------------------------
	
	
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
