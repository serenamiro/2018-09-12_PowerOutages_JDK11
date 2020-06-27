package it.polito.tdp.poweroutages.model;

import java.util.HashSet;
import java.util.Set;

public class Nerc {
	private int id;
	private String value;
	
	private Set<Nerc> nercRiceventi;
	private long bonus;
	
	private Boolean staPrestando;

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
		nercRiceventi = new HashSet<>();
		bonus = 0;
		this.staPrestando = false;
	}

	public Boolean getStaPrestando() {
		return staPrestando;
	}

	public void setStaPrestando(Boolean staPrestando) {
		this.staPrestando = staPrestando;
	}
	
	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " - " + value;
	}

	public Set<Nerc> getNercRiceventi() {
		return nercRiceventi;
	}

	public void setNercRiceventi(Set<Nerc> nercRiceventi) {
		this.nercRiceventi = nercRiceventi;
	}
	
	
	
}
