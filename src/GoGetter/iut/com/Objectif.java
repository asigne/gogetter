package GoGetter.iut.com;
import java.util.ArrayList;


public class Objectif {
	ArrayList<Couple> listeCouple;

	public Objectif() {
		listeCouple=new ArrayList<Couple>();
	}
	
	public void ajouterCouple(Couple newCouple){
		listeCouple.add(newCouple);
	}

	public ArrayList<Couple> getListeCouple() {
		return listeCouple;
	}

	public void setListeCouple(ArrayList<Couple> listeCouple) {
		this.listeCouple = listeCouple;
	}
	

	
	
}
