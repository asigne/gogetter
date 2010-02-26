package GoGetter.iut.com;


import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Plateau implements Serializable, Cloneable {
	boolean PlateauBool[][]; // plateau de booleen
	Case PlateauCase[][]; // plateau des Cases
	ArrayList<Case> ListCase; // liste de toutes les cases mobiles
	int comptI; // nb de I sans image
	int comptLimage; // nb de L avec image
	int comptL; // nb de L sans image
	int comptTimage; // nb de T avec image
	int ligne, colonne; // indice pour le parcours du plateau
	int rotationAleatoire; // variable pour d�terminer la rotation al�atoire des
	// cases
	int caseAleatoire; // variable pour choisir une case aleatoire dans la liste
	// "ListCase"

	public Plateau() {
		PlateauBool = new boolean[5][5]; // plateau de booleen
		PlateauCase = new Case[5][5]; // plateau des Cases
		initialisation();
	}

	public boolean[][] getPlateauBool() {
		return PlateauBool;
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
			// initialisation du PlateauBool � false
			for (ligne = 0; ligne < 5; ligne++) {
				for (colonne = 0; colonne < 5; colonne++) {
					PlateauBool[ligne][colonne] = true;
				}
			}
			// creation des cases fixes
			PlateauBool[0][0] = true;
			PlateauCase[0][0] = new L(270);
			PlateauBool[4][4] = true;
			PlateauCase[4][4] = new L(90);
			PlateauBool[0][4] = true;
			PlateauCase[0][4] = new L(0);
			PlateauBool[4][0] = true;
			PlateauCase[4][0] = new L(180);
			PlateauBool[0][1] = true;
			PlateauCase[0][1] = new Depart(0);
			PlateauBool[0][2] = true;
			PlateauCase[0][2] = new Depart(0);
			PlateauBool[0][3] = true;
			PlateauCase[0][3] = new Depart(0);
			PlateauBool[4][1] = true;
			PlateauCase[4][1] = new Depart(180);
			PlateauBool[4][2] = true;
			PlateauCase[4][2] = new Depart(180);
			PlateauBool[4][3] = true;
			PlateauCase[4][3] = new Depart(180);
			PlateauBool[1][0] = true;
			PlateauCase[1][0] = new Depart(270);
			PlateauBool[2][0] = true;
			PlateauCase[2][0] = new Depart(270);
			PlateauBool[3][0] = true;
			PlateauCase[3][0] = new Depart(270);
			PlateauBool[1][4] = true;
			PlateauCase[1][4] = new Depart(90);
			PlateauBool[2][4] = true;
			PlateauCase[2][4] = new Depart(90);
			PlateauBool[3][4] = true;
			PlateauCase[3][4] = new Depart(90);
			
			
			
			PlateauCase[1][1] = new plus(0);
			PlateauCase[1][2] = new L(180);
			PlateauCase[1][3] = new T(270);
			
			PlateauCase[2][1] = new L(0);
			PlateauCase[2][2] = new Pont(0); //pont
			PlateauCase[2][3] = new Ldouble(0); //Ldouble
			
			PlateauCase[3][1] = new L(90);
			PlateauCase[3][2] = new Ldouble(90);//Ldouble
			PlateauCase[3][3] = new T(180);
			
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
		for (ligne = 0; ligne < 5; ligne++) {
			for (colonne = 0; colonne < 5; colonne++) {
				etatPlateau=etatPlateau+PlateauCase[ligne][colonne] + "   \t";
			}
			etatPlateau=etatPlateau+"\n";
		}
		return etatPlateau;
	}
}
