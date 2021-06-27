package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;



public class Model {
	PremierLeagueDAO dao;
	Map<Integer,Match> idMap;
	Graph<Match,DefaultWeightedEdge> grafo;
	List<Match> best;
	public Model() {
		dao = new PremierLeagueDAO();
	}
	public void creaGrafo(int mese, int minuti) {
		idMap = new HashMap<Integer,Match>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.getVertici(idMap, mese);
    	Graphs.addAllVertices(grafo, idMap.values());
    	for(Collegamento c: dao.getArchi(minuti,idMap)) {
    		if(this.grafo.containsVertex(c.getMatch1()) && this.grafo.containsVertex(c.getMatch2())) {
    			DefaultWeightedEdge e = this.grafo.getEdge(c.getMatch1(),c.getMatch2());
    			if(e==null) {
    				Graphs.addEdgeWithVertices(grafo,c.getMatch1(),c.getMatch2(),c.getPeso());
    			}
    		}}
	}
	 public String infoGrafo() {
		 return "Grafo creato con "+ this.grafo.vertexSet().size()+ " vertici e " + this.grafo.edgeSet().size()+" archi\n";
	 }
	 public List<Collegamento> getPartitaMigliore(int minuti) {
			Collegamento collegamento = null;
			int max=-1;
			List<Collegamento> prova = new LinkedList<>();
			List<Collegamento> result = new LinkedList<>();
			for(DefaultWeightedEdge d : this.grafo.edgeSet()) {
				if(this.grafo.getEdgeWeight(d)>max || this.grafo.getEdgeWeight(d)==max ) {
					max= (int) this.grafo.getEdgeWeight(d);
					Match match1 = this.grafo.getEdgeSource(d);
					Match match2 = this.grafo.getEdgeTarget(d);
					collegamento = new Collegamento(match1,match2,max);
					prova.add(collegamento);
					System.out.println(collegamento);
				}
						
				}
			for(Collegamento c : prova) {
				if(c.getPeso()==max) {
					result.add(c);
				}
			}
					
			return result;
		}
	 public List<Match> getMatchVertici(){
		 List<Match> result = new LinkedList<>();
		 for(Match m: this.grafo.vertexSet()) {
			 result.add(m);
		 }
		 result.sort(new CollegamentoPerID());
		 return result;
	 }
	  public List<Match> camminoPesoMax(Match match1, Match match2){
	    	best= new LinkedList<Match>();
	    	List<Match> parziale = new LinkedList<Match>();
	    	parziale.add(match1);
	    	doCammino(parziale,match1,match2);
	    	//best.add(match2);
	    	return best;
	    }
	  private void doCammino(List<Match> parziale, Match match1, Match match2) {
			// TODO Auto-generated method stub
			
			if(calcolaPeso(parziale)>calcolaPeso(best) && parziale.get(parziale.size()-1).equals(match2) )
				best= new LinkedList<>(parziale);
			Match ultimo = parziale.get(parziale.size()-1);
			List<Match> adiacenti = Graphs.neighborListOf(this.grafo, ultimo);
			
			for(Match m : adiacenti) {
				if(!m.getTeamHomeID().equals(ultimo.getTeamHomeID()) && !m.getTeamAwayID().equals(ultimo.getTeamAwayID())) {
					parziale.add(m);
					doCammino(parziale,match1,match2);}
			}
		}
	  private double calcolaPeso(List<Match> parziale) {
			// TODO Auto-generated method stub
			double peso=0;
			for(Match m : parziale) {
				for(Match m2 : parziale) {
					if(this.grafo.containsVertex(m) && this.grafo.containsVertex(m2) && this.grafo.containsEdge(m, m2)) {
						DefaultWeightedEdge e = this.grafo.getEdge(m, m2);
						double n= grafo.getEdgeWeight(e);
						peso +=n;
					}
						
					}
				}
			return peso;
		}
}
