package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class CollegamentoPerID implements Comparator<Match>{
	@Override
	public int compare(Match c1, Match c2){
		return   c1.getMatchID()-c2.getMatchID();
	}
}
