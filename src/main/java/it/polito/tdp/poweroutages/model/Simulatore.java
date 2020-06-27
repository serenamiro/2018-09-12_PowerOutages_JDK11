package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.poweroutages.model.Evento.TipoEvento;

public class Simulatore {
	
	// MODELLO DEL MONDO
	Graph<Nerc, DefaultWeightedEdge> grafo;
	Map<Integer, Nerc> mappa; // mappa dei nerc
	List<PowerOutage> po;
	Map<Nerc, Set<Nerc>> prestiti;
	
	// INPUT (parametro di simulazione)
	private Integer K; //numero di mesi
	
	// OUTPUT
	private Integer catastrofi;
	private Map<Nerc, Long> bonus;
	
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Evento> queue;
	
	public Simulatore(Integer K, Graph<Nerc, DefaultWeightedEdge> grafo, List<PowerOutage> po, Map<Integer, Nerc> mappa) {
		this.K = K;
		this.grafo = grafo;
		this.po = po;
		this.mappa = mappa;
		
		this.bonus = new HashMap<>();
		this.prestiti = new HashMap<>();
	}
	
	public void init() {
		for(Nerc nerc : mappa.values()) {
			this.bonus.put(nerc, Long.valueOf(0));
			this.prestiti.put(nerc, new HashSet<Nerc>());
		}
		
		this.catastrofi = 0;
		this.queue = new PriorityQueue<Evento>();
		
		for(PowerOutage p : po) {
			Nerc nerc = mappa.get(p.getNercId().getId());
			Evento e1 = new Evento(p.getDate_event_began(), nerc, Evento.TipoEvento.INIZIO,
					p, null, p.getDate_event_began(), p.getDate_event_finished());
			queue.add(e1);
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		switch(e.getTipo()) {
	
		case INIZIO:
			System.out.println("Sto processando l'evento "+e.toString()+"\n");	
			
			Nerc nerc = e.getNerc();
			Nerc scelto = null;
			if(this.prestiti.get(nerc).size()>0) {
				// SCELGO TRA I MIEI DEBITORI
				double min = Long.MAX_VALUE;
				for(Nerc n : this.prestiti.get(nerc)) {
					DefaultWeightedEdge edge = this.grafo.getEdge(nerc, n);
					if(this.grafo.getEdgeWeight(edge)<min) {
						if(!n.getStaPrestando()) {
							scelto = n;
							min = this.grafo.getEdgeWeight(edge);
						}
					}
				}} else {
					// SCELGO QUELLO CON PESO MINORE
					double min = Long.MAX_VALUE;
					List<Nerc> neighbors = Graphs.neighborListOf(this.grafo, nerc);
					for(Nerc n : neighbors) {
						DefaultWeightedEdge edge = this.grafo.getEdge(nerc, n);
						if(this.grafo.getEdgeWeight(edge)<min) {
							if(!n.getStaPrestando()) {
								scelto = n;
								min = this.grafo.getEdgeWeight(edge);
							}
						}
					}
				}
				if(scelto!=null) {
					System.out.println("Ho trovato un donatore: "+scelto.toString()+"\n");
					scelto.setStaPrestando(true);
					Evento e2 = new Evento(e.getDataFine(), e.getNerc(), Evento.TipoEvento.FINE, e.getPo(),
							scelto, e.getDataInizio(), e.getDataFine());
					queue.add(e2);
					this.prestiti.get(scelto).add(e.getNerc());
					Evento cancella = new Evento(e.getData().plusMonths(this.K), e.getNerc(), 
							Evento.TipoEvento.CANCELLA_PRESTITO, e.getPo(), scelto, e.getDataInizio(), e.getDataFine());
					queue.add(cancella);
				} else {
					System.out.println("Non ho trovato alcun donatore: CATASTROFE\n");
					this.catastrofi++;
				}
				break;
			
		case FINE:
			System.out.println("Sto processando l'evento "+e.toString()+"\n");	
			if(e.getDonatore()!=null) {
				this.bonus.put(e.getDonatore(), bonus.get(e.getDonatore()) + 
						Duration.between(e.getDataInizio(), e.getDataFine()).toDays());
				e.getDonatore().setStaPrestando(false);
			}
			break; 
			
		case CANCELLA_PRESTITO:
			System.out.println("CANCELLAZIONE PRESTITO: " + e.getDonatore() + "-" + e.getNerc());
			this.prestiti.get(e.getDonatore()).remove(e.getNerc());
			break;
		}
	}

	public Integer getCatastrofi() {
		return catastrofi;
	}

	public void setCatastrofi(Integer catastrofi) {
		this.catastrofi = catastrofi;
	}

	

}
