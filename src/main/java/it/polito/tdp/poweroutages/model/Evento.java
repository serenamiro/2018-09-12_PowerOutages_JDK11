package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Evento implements Comparable<Evento>{

	public enum TipoEvento{
		INIZIO,
		FINE,
		CANCELLA_PRESTITO
	}
	
	private LocalDateTime data;
	private Nerc nerc;
	private TipoEvento tipo;
	private PowerOutage po;
	private Nerc donatore;
	
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;

	public Evento(LocalDateTime data, Nerc nerc, TipoEvento tipo, PowerOutage po, Nerc donatore,
			LocalDateTime dataInizio, LocalDateTime dataFine) {
		super();
		this.data = data;
		this.nerc = nerc;
		this.tipo = tipo;
		this.po = po;
		this.donatore = donatore;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	

	public LocalDateTime getData() {
		return data;
	}



	public void setData(LocalDateTime data) {
		this.data = data;
	}



	public Nerc getNerc() {
		return nerc;
	}



	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}



	public TipoEvento getTipo() {
		return tipo;
	}



	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}



	public PowerOutage getPo() {
		return po;
	}



	public void setPo(PowerOutage po) {
		this.po = po;
	}



	public Nerc getDonatore() {
		return donatore;
	}



	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}



	public LocalDateTime getDataInizio() {
		return dataInizio;
	}



	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}



	public LocalDateTime getDataFine() {
		return dataFine;
	}



	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}



	@Override
	public int compareTo(Evento o) {
		return this.data.compareTo(o.data);
	}
	
	
	
	
}
