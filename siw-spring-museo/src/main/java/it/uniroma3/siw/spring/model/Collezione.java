package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Collezione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column
	private String url;
	
	@OneToMany(mappedBy="collezione")
	private List<Opera> opere;
	
	private String descrizione;
	
	public void add(Opera opera) {
		this.opere.add(opera);
	}
	
	public void remove(Opera opera) {
		this.opere.remove(opera);
	}
	
	public void svuota() {
		this.opere.clear();
	}
}
