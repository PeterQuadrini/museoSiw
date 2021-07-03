package it.uniroma3.siw.spring.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Artista;

@Component
public class ArtistaValidator implements Validator {

	@Override
	public void validate(Object o, Errors errors) {
		Artista artista = (Artista) o;
		if (artista.getNome().isEmpty())
			errors.rejectValue("nomeArtista", "required");
		if (artista.getCognome().isEmpty())
			errors.rejectValue("cognomeArtista", "required");
		if (artista.getDataMorte() != null) {
			    if (artista.getDataNascita().isAfter(artista.getDataMorte()))
				    errors.rejectValue("dataMorte", "incompatibile");
		}
		if (artista.getDataNascita().isAfter(LocalDate.now()))
			errors.rejectValue("dataNascita", "incompatibile");

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.equals(clazz);
	}

}
