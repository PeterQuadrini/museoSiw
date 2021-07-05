package it.uniroma3.siw.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.repository.CuratoreRepository;

@Service
public class CuratoreService {
	
	@Autowired
	private CuratoreRepository curatoreRepository;
	
	
	@Transactional
	public void save(Curatore curatore) {
		this.curatoreRepository.save(curatore);
		
	}
	
	@Transactional
	public List<Curatore> findAll(){
		return (List<Curatore>) this.curatoreRepository.findAll();
	}

}
