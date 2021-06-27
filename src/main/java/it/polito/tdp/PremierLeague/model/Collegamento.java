package it.polito.tdp.PremierLeague.model;

public class Collegamento {
    
	Match match1;
	Match match2;
	int peso;
	public Collegamento(Match match1, Match match2, int peso) {
		this.match1 = match1;
		this.match2 = match2;
		this.peso = peso;
	}
	public Match getMatch1() {
		return match1;
	}
	public void setMatch1(Match match1) {
		this.match1 = match1;
	}
	public Match getMatch2() {
		return match2;
	}
	public void setMatch2(Match match2) {
		this.match2 = match2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return match1 + " "+ match2 + " " + peso;
	}
	
}
