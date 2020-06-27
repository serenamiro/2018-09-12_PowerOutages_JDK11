package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private PowerOutagesDAO dao;
	private List<Nerc> nercList;
	public Graph<Nerc, DefaultWeightedEdge> grafo;
	private Map<Integer, Nerc> mappa;
	private List<Adiacenza> adiacenze;
	private Simulatore sim;
	
	public Model() {
		dao = new PowerOutagesDAO();
		mappa = new HashMap<Integer, Nerc>();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<Nerc, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.nercList = dao.loadAllNercs(mappa);
		Graphs.addAllVertices(this.grafo, nercList);
		
		adiacenze = dao.getAdiacenze(mappa);
		for(Adiacenza a : adiacenze) {
			Integer peso = dao.getPesoAdiacenza(a.getN1(), a.getN2());
			if(peso!=0) {
				a.setPeso(peso);
				Graphs.addEdgeWithVertices(this.grafo, a.getN1(), a.getN2(), peso);	
			}
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getVicini(Nerc n){
		List<Nerc> lista = Graphs.neighborListOf(this.grafo, n);
		List<Adiacenza> res = new ArrayList<Adiacenza>();
		
		for(Nerc nerc : lista) {
			Adiacenza  a = new Adiacenza(n, nerc, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(n, nerc)));
			res.add(a);
		}
		
		Collections.sort(res);
		return res;
	}
	
	public List<PowerOutage> getPO(){
		return dao.getOutages(mappa);
	}
	
	public void doSimulazione(Integer k) {
		sim = new Simulatore(k, this.grafo, this.getPO(), this.mappa);
		sim.init();
		sim.run();
	}
	
	public Integer getCatastrofi() {
		return sim.getCatastrofi();
	}
	
	public Map<Nerc, Set<Nerc>> getBonus(){
		return sim.prestiti;
	}
}
