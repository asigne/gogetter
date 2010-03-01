package GoGetter.iut.com;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Objectif implements Serializable {
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
