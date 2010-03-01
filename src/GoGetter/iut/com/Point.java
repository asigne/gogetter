package GoGetter.iut.com;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Point implements Serializable {
	int ligne;
	int colonne;
		
	Point(int x, int y){
		ligne=x;
		colonne=y;
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}
	
	
}
