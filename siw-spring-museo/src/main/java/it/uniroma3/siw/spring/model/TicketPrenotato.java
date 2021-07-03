package it.uniroma3.siw.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"utente_id", "ticket_id"})})
public class TicketPrenotato {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private int posti_prenotati;
	
	@ManyToOne
	private Ticket ticket;
	
	@ManyToOne
	private Utente utente;
}
