package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.OperaRepository;

@Service
public class OperaService {
	
	@Autowired
	private OperaRepository operaRepository;
	
	@Transactional
	public List<Opera> tutteLeOpere(){
		return (List<Opera>) this.operaRepository.findAll();
	}
	
	@Transactional
	public Opera findById(Long id) {
		Optional<Opera> opera = this.operaRepository.findById(id);
		if(opera.isPresent()) {
			return opera.get();
		}
		return null;
	}

}
