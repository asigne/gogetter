package GoGetter.iut.com;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Plateau implements Serializable, Cloneable {
	Case PlateauCase[][]; // plateau des Cases

	public Plateau() {
		PlateauCase = new Case[5][5]; // plateau des Cases
		initialisation();
	}

	public void setPlateauCase(Case[][] plateauCase) {
		PlateauCase = plateauCase;
	}

	public Case[][] getPlateauCase() {
		return PlateauCase;
	}

	public void setCase(Case maCase, int ligne, int colonne) {
		PlateauCase[ligne][colonne] = maCase;
	}

	public void initialisation() {
		{
			// creation des cases fixes
			PlateauCase[0][0] = new L(270);
			PlateauCase[4][4] = new L(90);
			PlateauCase[0][4] = new L(0);
			PlateauCase[4][0] = new L(180);
			PlateauCase[0][1] = new Depart(0);
			PlateauCase[0][2] = new Depart(0);
			PlateauCase[0][3] = new Depart(0);
			PlateauCase[4][1] = new Depart(180);
			PlateauCase[4][2] = new Depart(180);
			PlateauCase[4][3] = new Depart(180);
			PlateauCase[1][0] = new Depart(270);
			PlateauCase[2][0] = new Depart(270);
			PlateauCase[3][0] = new Depart(270);
			PlateauCase[1][4] = new Depart(90);
			PlateauCase[2][4] = new Depart(90);
			PlateauCase[3][4] = new Depart(90);
							
			for (int ligne=1;ligne<4;ligne++)
			{
				for (int colonne=1;colonne<4;colonne++)
				{
					setCase(null, ligne, colonne);
				}
			}
			
		}
	}

	
	public Case getCase(int ligne, int colonne) {
		return PlateauCase[ligne][colonne];
	}

	
	public String affiche() {
		String etatPlateau="";
		for (int ligne = 0; ligne < 5; ligne++) {
			for (int colonne = 0; colonne < 5; colonne++) {
				etatPlateau=etatPlateau+PlateauCase[ligne][colonne] + "   \t";
			}
			etatPlateau=etatPlateau+"\n";
		}
		return etatPlateau;
	}
}
