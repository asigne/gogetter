package GoGetter.iut.com;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Case implements Serializable, Cloneable {
	boolean utilise;
	int rotation; // indice de rotation par rapport � la position originale
	boolean tabDroit[]; // tableau contenant les possibilit� de sortie de la
	// case

	// attributs utilis�s lors de la recherche de chemin possible
	int flag; // indique si la case est accessible ou non
	int flagEnEntrant; // indique les cases du chemin actuel

	// pour les attributs entree et sortie les valeurs possibles sont : 1 pour
	// haut, 2 pour droite, 3 pour bas et 4 pour gauche
	int entree;
	int sortie; // indique la sortie de la case
	
	int ligneOri;
	int colonneOri;

	public Case() {
		utilise=false;
		tabDroit = new boolean[5];
		rotation = 0;

		sortie = 0;
		flag = 0;	
	}

	public void setOri(int ligneOri, int colonneOri)
	{
		this.ligneOri=ligneOri;
		this.colonneOri=colonneOri;
	}
	
	@Override
	public String toString() {
		// return
		// tabDroit[1]+" "+tabDroit[2]+" "+tabDroit[3]+" "+tabDroit[4]+" "+this.rotation+" "+this.flag;
		// return ""+noImage+" "+rotation;//ListJoueur;
		return flag + " " + rotation+ " "+entree+" "+sortie+" "+tabDroit[1]+tabDroit[2]+tabDroit[3]+tabDroit[4];
		// return ListJoueur+" ";
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setFlagEnEntrant(int flagEnEntrant) {
		this.flagEnEntrant = flagEnEntrant;
	}

	public int getFlagEnEntrant() {
		return flagEnEntrant;
	}

	// methode retournant le droit associ� au rang fourni
	public boolean getTabDroit(int rang) {
		return tabDroit[rang];
	}

	// methode permettant de faire tourner la case de 'indice' degr�s
	// l'indice rotation et les droits de la case sont modifi�s
	public void rotate(int indice) {
		for (int i = 0; i < indice; i = i + 90) {
			tabDroit[0] = tabDroit[4]; // droit temporaire pour l'�change
			if (rotation < 270) {
				rotation = rotation + 90; // MaJ de rotation
			} else {
				rotation = 0; // 360� correspond a 0�

			}
			for (int j = 4; j > 0; j--) {
				this.tabDroit[j] = this.tabDroit[j - 1]; // MaJ des droits
			}
		}
	}

	public int getSortie() {
		return this.sortie;
	}

	public void setSortie(int sortie) {
		this.sortie = sortie;
	}

	
	
	public int getEntree() {
		return entree;
	}

	public void setEntree(int entree) {
		this.entree = entree;
	}

	public int getRotation() {
		return this.rotation;
	}

	public boolean isUtilise() {
		return utilise;
	}

	public void setUtilise(boolean utilise) {
		this.utilise = utilise;
	}

	public int getLigneOri() {
		return ligneOri;
	}

	public int getColonneOri() {
		return colonneOri;
	}
	
	
	
	
	
	
}
