package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private int numero_posti;
	
	@Column
	private int posti_disponibili;
	
	@Column(unique=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@OrderColumn
	private LocalDate localDate;
	
	@OneToMany(mappedBy="ticket")
	private List<TicketPrenotato> ticketPrenotati; 
	
	
}
