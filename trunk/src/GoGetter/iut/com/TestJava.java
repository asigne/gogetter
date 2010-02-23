package GoGetter.iut.com;

public class TestJava {

	public static void main(String args[]){
	
		Plateau monPlateau = new Plateau();
		
		monPlateau.affiche();
		
		fonction(1,0,monPlateau);
		
		System.out.println();
		
		monPlateau.affiche();
		
	}
	
	public static void fonction(int ligne, int colonne, Plateau monPlateau) {
		//System.out.println("debut fonction");
		Case maCase = monPlateau.getCase(ligne, colonne); // recupere la case à
		// traiter
		int L1 = 0, C1 = 0, S = 0, E=0;
		boolean Ok;
		System.out.println("debut fonction nouvelle ligne:"+ligne+" colonne:"+colonne+" "+maCase);
		
		
		monPlateau.getCase(ligne, colonne).setFlag(1); // met a 1 le flag de la
		// case en cours de
		// traitement
		for (int i = maCase.getSortie() + 1; i < 5; i++) // test de toutes les
			// sorties (haut 1,
			// droite 2, bas 3,
			// gauche 4)
		{
			Ok = false;
			if(maCase instanceof Pont){
				if(maCase.getEntree()==1)
				{
					i=3;
				}	
				else if(maCase.getEntree()==3)
				{
					i=1;
				}	
				else if(maCase.getEntree()==2)
				{
					i=4;
				}
				else if(maCase.getEntree()==4)
				{
					i=2;
				}
				else if(maCase.getEntree()==0)
				{
					i=5;
				}
				System.out.println("newi"+i);
				maCase.setEntree(0);
			}
			
			if(maCase instanceof Ldouble){
				if (maCase.getRotation()==0 || maCase.getRotation()==180){
					if(maCase.getEntree()==1)
					{
						i=2;
					}	
					else if(maCase.getEntree()==2)
					{
						i=1;
					}	
					else if(maCase.getEntree()==3)
					{
						i=4;
					}
					else if(maCase.getEntree()==4)
					{
						i=3;
					}
					else if(maCase.getEntree()==0)
					{
						i=5;
					}
					maCase.setEntree(0);
				}
				else
				{
					if(maCase.getEntree()==1)
					{
						i=4;
					}	
					else if(maCase.getEntree()==2)
					{
						i=3;
					}	
					else if(maCase.getEntree()==3)
					{
						i=2;
					}
					else if(maCase.getEntree()==4)
					{
						i=1;
					}
					else if(maCase.getEntree()==0)
					{
						i=5;
					}
					maCase.setEntree(0);
				}
			
			}
			int sortieInterdite=0;
			if(maCase.getEntree()==1){
				sortieInterdite=3;
			}
			else if(maCase.getEntree()==2){
				sortieInterdite=2;
			}
			else if(maCase.getEntree()==3){
				sortieInterdite=1;
			}
			else if(maCase.getEntree()==4){
				sortieInterdite=2;
			}
			else{
				sortieInterdite=100;
			}
			//System.out.println(sortieInterdite);
			switch (i) {
			case 1: // haut
				System.out.println("haut");
				if (ligne > 0
						&& maCase.getTabDroit(1) == true
						&& monPlateau.getCase(ligne - 1, colonne)
						.getTabDroit(3) == true
						&& monPlateau.getCase(ligne - 1, colonne).getSortie()!=sortieInterdite) {
					L1 = -1;
					C1 = 0;
					S = 1;
					E = 3;
					Ok = true;
				}
				break;
			case 2: // droite
				System.out.println("droite");
				if (colonne < 4
						&& maCase.getTabDroit(2) == true
						&& monPlateau.getCase(ligne, colonne + 1).getTabDroit(4) == true
						&& monPlateau.getCase(ligne, colonne + 1).getSortie()!=sortieInterdite) {
					L1 = 0;
					C1 = 1;
					S = 2;
					E = 4;
					Ok = true;
				}
				break;
			case 3: // bas
				System.out.println("bas");
				if (ligne < 4
						&& maCase.getTabDroit(3) == true
						&& monPlateau.getCase(ligne + 1, colonne)
						.getTabDroit(1) == true
						&& monPlateau.getCase(ligne + 1, colonne).getSortie()!=sortieInterdite) {
					L1 = 1;
					C1 = 0;
					S = 3;
					E = 1;
					Ok = true;
				}
				break;
			case 4: // gauche
				System.out.println("gauche");
				if (colonne > 0
						&& maCase.getTabDroit(4) == true
						&& monPlateau.getCase(ligne, colonne - 1)
						.getTabDroit(2) == true
						&& monPlateau.getCase(ligne, colonne - 1).getSortie()!=sortieInterdite) {
					L1 = 0;
					C1 = -1;
					S = 4;
					E = 2;
					Ok = true;
				}
				break;
			}
			if (Ok == true) // s'il est possible de sortir de la case en cours
				// de traitement
			{
				System.out.println("ok rentré");
				monPlateau.getCase(ligne, colonne).setSortie(S); // on indique
				// par ou on
				// sort
				// on change de case
				
				//System.out.println("ancienne ligne:"+ligne+" colonne:"+colonne+" "+maCase);
				//monPlateau.affiche();
				
				ligne = ligne + L1;
				colonne = colonne + C1;
				monPlateau.getCase(ligne, colonne).setEntree(E);
				
				fonction(ligne, colonne, monPlateau); // on applique la fonction
				// sur la nouvelle case
				// on revient à la case
				ligne = ligne - L1;
				colonne = colonne - C1;
			}
		}
		monPlateau.getCase(ligne, colonne).setSortie(0); // lorsque la fonction
		// se termine sur
		// une case on met
		// la sortie à 0
		System.out.println("fin fonction"+ligne+colonne);
	}
	
}	
	
	