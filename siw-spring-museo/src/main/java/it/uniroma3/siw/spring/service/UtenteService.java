package it.uniroma3.siw.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.repository.UtenteRepository;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Transactional
    public Utente getUser(Long id) {
    	Optional<Utente> result = this.utenteRepository.findById(id);
    	return result.orElse(null);
    }
    
    @Transactional
    public void save(Utente utente) {
    	this.utenteRepository.save(utente);
    }
    
 
}
