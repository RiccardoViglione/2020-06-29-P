package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Graph<Match,DefaultWeightedEdge>grafo;
	private Map<Integer,Match>idMap;
	public Model() {
		dao=new PremierLeagueDAO ();
		idMap=new HashMap<>();
		dao.listAllMatches(idMap);
		
	}
	public void creaGrafo(Month mese,int minuti) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	Graphs.addAllVertices(this.grafo, dao.getVertici(mese, idMap));
	for(Adiacenza a:dao.getArchi(mese, minuti, idMap)) {
		Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(),a.getPeso());
	}
	}
	public List<Match> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}public List<Adiacenza>getMassimo(){
		List<Adiacenza>result=new ArrayList<Adiacenza>();
		Integer max=Integer.MIN_VALUE;
		for(DefaultWeightedEdge e:grafo.edgeSet()) {
			int peso=(int)this.grafo.getEdgeWeight(e);
			if(peso>max) {
				result.clear();
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),peso));
			max=peso;
			}else if(peso==max) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),peso));
			}
			
		}
		return result;
		
	}
}
