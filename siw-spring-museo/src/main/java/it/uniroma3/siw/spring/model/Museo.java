package it.uniroma3.siw.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Museo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nome;
	private String indirizzo;
	private String citta;

	
}
