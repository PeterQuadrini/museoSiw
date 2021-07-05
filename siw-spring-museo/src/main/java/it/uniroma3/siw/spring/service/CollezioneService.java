package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {
	
	@Autowired
	private CollezioneRepository collezioneRepository;
	
	@Transactional
	public List<Collezione> getAllCollezioni(){
		return (List<Collezione>) this.collezioneRepository.findAll();
	}
	
	@Transactional
	public Collezione getCollezioneById(Long id) {
		Optional<Collezione> collezione = this.collezioneRepository.findById(id);
		return collezione.orElse(null);
	}
	
	@Transactional
	public void save(Collezione collezione) {
		this.collezioneRepository.save(collezione);
	}
	
	@Transactional
	public void remove(Collezione collezione) {
		this.collezioneRepository.delete(collezione);
	}
	
	@Transactional
	public void clear(Collezione collezione) {
	}

}
