package GoGetter.iut.com;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Partie implements Serializable{
	ArrayList<Objectif> listeObjectif;
	Case caseCourante;
	Case[][] casesRestantes;
	Objectif objectifCourant;
	Plateau monPlateau;
	String difficulte;
	
	public Partie(String difficulte){
		this.difficulte=difficulte;
		listeObjectif=new ArrayList<Objectif>();	
		caseCourante=null;
		casesRestantes=new Case[2][5];
		objectifCourant=new Objectif();
		creationObjectifs();
		objectifCourant=listeObjectif.get(0);
		monPlateau=new Plateau();
	}
	
	public void creationObjectifs(){
		Objectif o1=new Objectif();
		o1.ajouterCouple(new Couple(new Point(4,1), new Point(3,4)));
		
		Objectif o2=new Objectif();
		o2.ajouterCouple(new Couple(new Point(0,2), new Point(1,0)));
		o2.ajouterCouple(new Couple(new Point(0,2), new Point(4,1)));
		
		Objectif o3=new Objectif();
		o3.ajouterCouple(new Couple(new Point(4,2), new Point(0,2)));
		o3.ajouterCouple(new Couple(new Point(2,4), new Point(0,2)));	
		
		Objectif o4=new Objectif();
		o4.ajouterCouple(new Couple(new Point(2,4), new Point(2,0)));
		o4.ajouterCouple(new Couple(new Point(4,2), new Point(1,4)));	
		
		Objectif o5=new Objectif();
		o5.ajouterCouple(new Couple(new Point(2,4), new Point(0,2)));
		o5.ajouterCouple(new Couple(new Point(0,2), new Point(1,0)));
		o5.ajouterCouple(new Couple(new Point(1,0), new Point(0,3)));
		
		Objectif o6=new Objectif();
		o6.ajouterCouple(new Couple(new Point(2,4), new Point(2,0)));
		o6.ajouterCouple(new Couple(new Point(0,2), new Point(4,1)));
		o6.ajouterCouple(new Couple(new Point(2,4), new Point(0,2), false));
		
		//# chat Ã  souris blanche, chat Ã  souris grise, souris blanche Ã  fromage gauche, souris grise Ã  fromage droite
		Objectif o7=new Objectif();
		o7.ajouterCouple(new Couple(new Point(0,2), new Point(4,1)));
		o7.ajouterCouple(new Couple(new Point(0,2), new Point(1,0)));
		o7.ajouterCouple(new Couple(new Point(4,1), new Point(3,4)));
		o7.ajouterCouple(new Couple(new Point(1,0), new Point(0,3)));

		//chien droit Ã  chat, souris grise Ã  souris blanche mais pas chien droit Ã  souris grise ni chat Ã  souris blanche
		Objectif o8=new Objectif();
		o8.ajouterCouple(new Couple(new Point(4,2), new Point(0,2)));
		o8.ajouterCouple(new Couple(new Point(1,0), new Point(4,1)));
		o8.ajouterCouple(new Couple(new Point(1,0), new Point(4,2),false));
		o8.ajouterCouple(new Couple(new Point(0,2), new Point(4,1),false));
		
		
		//# souris blanche et souris grise Ã  os petit, os grand, fromage gauche, fromage droite
		Objectif o9=new Objectif();
		o9.ajouterCouple(new Couple(new Point(4,1), new Point(0,1)));
		o9.ajouterCouple(new Couple(new Point(1,0), new Point(0,1)));
		o9.ajouterCouple(new Couple(new Point(4,1), new Point(4,3)));
		o9.ajouterCouple(new Couple(new Point(1,0), new Point(4,3)));
		o9.ajouterCouple(new Couple(new Point(4,1), new Point(0,3)));
		o9.ajouterCouple(new Couple(new Point(1,0), new Point(0,3)));
		o9.ajouterCouple(new Couple(new Point(4,1), new Point(3,4)));
		o9.ajouterCouple(new Couple(new Point(1,0), new Point(3,4)));
		
		//# chien droite Ã  niche rouge, os petit, os grand, chat, arbre
		Objectif o10=new Objectif();
		o10.ajouterCouple(new Couple(new Point(4,2), new Point(1,4)));
		o10.ajouterCouple(new Couple(new Point(4,2), new Point(0,1)));
		o10.ajouterCouple(new Couple(new Point(4,2), new Point(4,3)));
		o10.ajouterCouple(new Couple(new Point(4,2), new Point(0,2)));
		o10.ajouterCouple(new Couple(new Point(4,2), new Point(3,0)));
				
		listeObjectif.add(o1);
		listeObjectif.add(o2);
		listeObjectif.add(o3);
		listeObjectif.add(o4);
		listeObjectif.add(o5);
		listeObjectif.add(o6);		
		listeObjectif.add(o7);	
		listeObjectif.add(o8);	
		listeObjectif.add(o9);	
		listeObjectif.add(o10);	
	}

	public boolean objectifSvt(){
		if(getListeObjectif().indexOf(getObjectifCourant())+1<listeObjectif.size())
		{
			int ancienObj=getListeObjectif().indexOf(getObjectifCourant());
			objectifCourant=getListeObjectif().get(ancienObj+1);
			return true;
		}
		return false;
	}
	
	public Case getCaseCourante() {
		return caseCourante;
	}

	public void setCaseCourante(Case caseCourante) {
		this.caseCourante = caseCourante;
	}
	
	public Case getCaseDispo(int ligne, int colonne)
	{
		return casesRestantes[ligne][colonne];
	}
	
	public void setCasesRestantes(int ligne, int colonne, Case maCase) {
		casesRestantes[ligne][colonne] = maCase;
	}
	
	public String getDifficulte() {
		return difficulte;
	}

	public void init(){
		monPlateau=new Plateau();
		
		Case plus=new plus(0);
		Case L1=new L(0);
		Case L2=new L(0);
		Case L3=new L(0);
		Case T1=new T(0);
		Case T2=new T(0);
		Case pont=new Pont(0);
		Case Ldouble1 = new Ldouble(0);
		Case Ldouble2 = new Ldouble(0);
		
		casesRestantes[0][0]=L1;
		L1.setOri(0, 0);
		casesRestantes[0][1]=L2;
		L2.setOri(0, 1);
		casesRestantes[0][2]=L3;
		L3.setOri(0, 2);
		casesRestantes[0][3]=T1;
		T1.setOri(0, 3);
		casesRestantes[0][4]=T2;
		T2.setOri(0, 4);
		casesRestantes[1][0]=plus;
		plus.setOri(1, 0);
		casesRestantes[1][1]=pont;
		pont.setOri(1, 1);
		casesRestantes[1][2]=Ldouble1;
		Ldouble1.setOri(1, 2);
		casesRestantes[1][3]=Ldouble2;
		Ldouble2.setOri(1, 3);
		casesRestantes[1][4]=null;		
	}

	public Objectif getObjectifCourant() {
		return objectifCourant;
	}

	public void setObjectifCourant(Objectif objectifCourant) {
		this.objectifCourant = objectifCourant;
	}

	public ArrayList<Objectif> getListeObjectif() {
		return listeObjectif;
	}

	public void setListeObjectif(ArrayList<Objectif> listeObjectif) {
		this.listeObjectif = listeObjectif;
	}

	public Plateau getMonPlateau() {
		return monPlateau;
	}

	public void setMonPlateau(Plateau monPlateau) {
		this.monPlateau = monPlateau;
	}

	
	
	
	
	
}



 
