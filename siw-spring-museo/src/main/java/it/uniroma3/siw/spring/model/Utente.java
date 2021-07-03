package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "opere_preferite")
	private List<Opera> operePreferite;
    @OneToMany(mappedBy="utente")
    private List<TicketPrenotato> ticket;
	
	public void addPreferita(Opera opera) {
		this.operePreferite.add(opera);
	}

	public void remove(Opera opera) {
		this.operePreferite.remove(opera);
	}

}
