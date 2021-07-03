package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public List<Artista> getAllArtisti(){
		return (List<Artista>) this.artistaRepository.findAll();

	}
	@Transactional
	public void save(Artista artista) {
		this.artistaRepository.save(artista);
	}

	@Transactional
    public Artista findById(Long id) {
		Optional<Artista> artista = this.artistaRepository.findById(id);
		return artista.orElse(null);
	}
	
	@Transactional
	public void remove(Artista artista) {
		this.artistaRepository.delete(artista);
	}
}
