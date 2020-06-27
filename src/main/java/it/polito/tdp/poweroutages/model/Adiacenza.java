package it.polito.tdp.poweroutages.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Nerc n1;
	private Nerc n2;
	private Integer peso;
	
	public Adiacenza(Nerc n1, Nerc n2, Integer peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}
	
	public Nerc getN1() {
		return n1;
	}
	public void setN1(Nerc n1) {
		this.n1 = n1;
	}
	public Nerc getN2() {
		return n2;
	}
	public void setN2(Nerc n2) {
		this.n2 = n2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return -(peso-o.getPeso());
	}

	@Override
	public String toString() {
		return n1.getId() + " - " + n2.getId() + " (peso: " + peso + ")\n";
	}
	
	
}
