package it.uniroma3.siw.spring.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Curatore;

@Component
public class CuratoreValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Curatore.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Curatore curatore = (Curatore) o;
		if(curatore.getDataNascita()!=null)
			if(curatore.getDataNascita().isAfter(LocalDate.now()))
				errors.rejectValue("dataNascita", "incoerente");
		
	}

}
