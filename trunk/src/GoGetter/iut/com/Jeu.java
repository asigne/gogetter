package GoGetter.iut.com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Jeu extends Activity {
	TextView textObjectif;
	Button btnValider;
	int xmin=40, xmax=288, ymin=40, ymax=288;
	RotateAnimation rotation0, rotation90, rotation180, rotation270;
	Partie maPartie;
	int indicePremiereCaseDispo=R.id.bl11;
	int indicePremiereCaseTableau=R.id.l0_c0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.main);
               
		textObjectif = (TextView) findViewById(R.id.textObjectif);
		btnValider = (Button) findViewById(R.id.Valider);    
		
		Bundle objetParametre = this.getIntent().getExtras();
		String typePartie =objetParametre.getString("typePartie");
		String difficulte =objetParametre.getString("difficulte");
		
		maPartie=new Partie(difficulte);
		maPartie.init();
		if(typePartie.equals("sauvPartie"))
		{
			Context lecontext = getBaseContext();
			maPartie = chargerPartie(lecontext);
			affichePlateau();
		}
		
		afficheListeCases(0);
		MAJobjectif();
		
		btnValider.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				valider();
			}
		});
		
    }
    
    public void onStop() {
    	Context lecontext = getBaseContext();
    	sauvegarderPartie(lecontext, maPartie);
    	super.onStop();
    }
	
    //methode pour charger la partie
	public Partie chargerPartie(Context context) {

		ObjectInputStream deserialise = null;
		Partie partie = null;
		try {
			deserialise = new ObjectInputStream(context.openFileInput("sauvMaPartie"));
			partie = (Partie) deserialise.readObject();
		}

		catch (NotSerializableException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		} finally {
			try {
				deserialise.close();
			}
			catch (IOException e) {
			}
		}
		return partie;
	}
	
	//methode pour sauvegarder la partie
	public void sauvegarderPartie(Context context, Partie data) {
		FileOutputStream fOut = null;
		ObjectOutputStream oos = null;
		try {
			fOut = context.openFileOutput("sauvMaPartie", MODE_WORLD_WRITEABLE);
			oos = new ObjectOutputStream(fOut);
			oos.writeObject(maPartie);
			oos.flush();
		} catch (IOException e) {
		} finally {
			try {
				oos.close();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}
	
	// methode permettant de gérer les clics sur l'écran
	public boolean onTouchEvent(MotionEvent event) {
		int ligne, colonne;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int x = (int) (event.getX());
				int y = (int) (event.getY());
				//convertion du clic sur l'ecran
					if (x > xmin && x <= xmax && y > ymin && y < ymax) {				
						// action sur une case du plateau 
						//convertion en ligne, colonne
						ligne=(int) (y-30)/86;
						colonne=(int) (x-30)/86;
							actionPlateau(ligne+1,colonne+1);
						return true;
					}
					if (x > 0 && x <= 235 && y > 318 && y < 413) {				
						// action sur une case de la liste
						//convertion en ligne, colonne
						if(y > 318 && y < 365)
						{
							ligne=0;
						}
						else
						{
							ligne=1;
						}
						colonne=(int) x/47;
						if(ligne!=1 || colonne!=4){
							selectionnerCase(ligne, colonne);
						}
						return true;
					}
					if (x > 240 && x <= 320 && y > 325 && y < 405) {				
						// action sur la case courante
						actionCaseCourante();
						return true;
					}
			}
			return false;
	}
	
	//methode lors d'un clic sur les cases restantes
	public void selectionnerCase(int ligne, int colonne) {
		maPartie.setCaseCourante(maPartie.getCaseDispo(ligne, colonne));
		afficheCaseCourante(0);
	}
	
	//methode lors d'un clic sur la case courante
	public void actionCaseCourante() {
		if(maPartie.getCaseCourante()!=null)
		{
			maPartie.getCaseCourante().rotate(90); // rotation de la carte
			afficheCaseCourante(150); // affichage de la case dans son nouvel état de rotation
			afficheCaseListe(maPartie.getCaseCourante());	//pour supprimer l'affichage de la case dans la liste des cases restantes;
		}
	}
	
	//methode lors d'un clic sur le plateau
	public void actionPlateau(int ligne, int colonne){
		Plateau monPlateau=maPartie.getMonPlateau();
		Case caseListe=null;
			if(monPlateau.getCase(ligne, colonne)==null) //si il y a aucune case a cet endroit
			{
				if(maPartie.getCaseCourante()!=null)
				{
					Case caseCourante=maPartie.getCaseCourante();	//recuperation de la case courante de la partie
					if(!caseCourante.utilise)
					{
						caseCourante.setUtilise(true);				//la case est donc utilisée
						monPlateau.setCase(caseCourante, ligne, colonne);	//ajout de la case dans le plateau
						maPartie.setCaseCourante(null);			//supression de la case dans la case courante de la partie
					}	
					caseListe=maPartie.getMonPlateau().getCase(ligne, colonne);	//recuperation de la case qui vient d'etre placée
					
				}
			}
			else //s'il y a une case sur la plateau
			{
				Case caseARetirer=monPlateau.getCase(ligne, colonne);	//recuperation de la case du tableau a enlever
				monPlateau.setCase(null, ligne, colonne);	//suppression de la case du tableau
				caseARetirer.setUtilise(false);			//la case n'est plus utilisée
				maPartie.setCasesRestantes(caseARetirer.getLigneOri(), caseARetirer.getColonneOri(), caseARetirer);	//ajout de la case aux cases restantes
				caseListe=caseARetirer;	//recuperation de la case retirée
			}
			afficheCasePlateau(ligne, colonne);	//mise a jour de l'affichage du plateau
			if(caseListe!=null)
			{
				afficheCaseListe(caseListe);	//mise a jour de l'affichage des cartes restantes
			}
			afficheCaseCourante(0);	//mise a jour de l'affichage de la case courante
		}
	
	//methode pour mettre a jour l'affichage de l'objectif
	public void MAJobjectif() {
		int numPremierObjectif=R.string.oa;	//id de l'objectif 1
		int numObjectif=maPartie.getListeObjectif().indexOf(maPartie.getObjectifCourant()); //recuperation de la position de l'objectif courant dans la liste des obj
		textObjectif.setText(numPremierObjectif+numObjectif); //mise a jour de l'affichage de l'objectif courant
	}
		
	//methode pour mettre a jour l'affichage d'une case du plateau
	public void afficheCasePlateau(int ligneCase, int colonneCase){
		int indice=indicePremiereCaseTableau; //id de la premiere case du plateau
		ImageView ICT=null;
		Case caseATraiter = maPartie.getMonPlateau().getCase(ligneCase, colonneCase);	//recuperation de la case a traiter
		for (int ligne=1;ligne<4;ligne++)
		{
			for (int colonne=1;colonne<4;colonne++)
			{
				ICT = (ImageView) findViewById(indice);	//recuperation de l'ImageView de la case a traiter
				if(ligne==ligneCase && colonne==colonneCase)
					{
						afficheICT(caseATraiter, ICT, 0, 80);		//mise a jour de l'affichage
					}
				indice++;
			}
			indice++;
		}
		
	}
	
	//methode pour mettre a jour l'affichage du plateau
	public void affichePlateau(){
		for (int ligne=1;ligne<4;ligne++)
		{
			for (int colonne=1;colonne<4;colonne++)
			{
				afficheCasePlateau(ligne, colonne);	//pour chaque case, on met a jour l'affichage de celle-ci
			}
		}
	}
	
	//methode pour mettre a jour l'affichage d'une case restante
	public void afficheCaseListe(Case maCase)
	{
		int indice=indicePremiereCaseDispo;
		ImageView ICT=null;
		int ligneOri=maCase.getLigneOri();
		int colonneOri=maCase.getColonneOri();
		for (int i=0;i<2;i++)	//parcours des lignes
		{
			//traitement obligatoire car 5cases sur la ligne1 et 4cases sur la ligne2
			int max;
			if(i==0)
				max=5;
			else
				max=4;
			for (int j=0;j<max;j++) //parcours des colonnes
			{
				ICT = (ImageView) findViewById(indice);	//recuperation de l'ImageView de la case a traiter
				if(i==ligneOri && j==colonneOri)
					{
						afficheICTCaseListe(maCase, ICT, 0, 48);	//mise a jour de l'affichage
					}
				indice++;
			}
		indice++;
		}
	}
	
	//methode pour mettre a jour l'affichage des cases restantes
	public void afficheListeCases(int duration) {
		Case caseATraiter;
		for (int i=0;i<2;i++)	//parcours des lignes
		{
			//traitement obligatoire car 5cases sur la ligne1 et 4cases sur la ligne2
			int max;
			if(i==0)
				max=5;
			else
				max=4;
			for (int j=0;j<max;j++) //parcours des colonnes
			{
					caseATraiter = maPartie.getCaseDispo(i, j);	//recuperation de la case a traiter
					afficheCaseListe(caseATraiter);	//mise a jour de l'affichage de cette case
			}
		}
	}

	//methode pour mettre a jour l'affichage de la case courante
	public void afficheCaseCourante(int duration) {
		Case caseATraiter = maPartie.getCaseCourante();			// recuperation de la case a traiter
		ImageView ICT = (ImageView) findViewById(R.id.CaseCourante); // recuperation de l'ImageView a traiter
		if(caseATraiter!=null)	
		{
			if(caseATraiter.isUtilise())
			{
				caseATraiter=null;
			}
		}
		afficheICT(caseATraiter, ICT, duration, 70);	//mise a jour de l'affichage de la case courante
	}
	
	//methode pour afficher les images avec android
	
	public void afficheICT(Case caseATraiter, ImageView imageCourante, int duration, int dimensionCase) {
		
		if(caseATraiter!=null)
		{
			int noImage = 0; // numero de l'image a affecter a la case en cours de traitement
			int indicePremiereImage=R.drawable.zl;
			
			// on teste l'instance de la case
			if (caseATraiter instanceof L)
				{
					noImage = indicePremiereImage ;
				}
			else if (caseATraiter instanceof Ldouble)
				{
					noImage = indicePremiereImage + 1;
				} 
			else if (caseATraiter instanceof plus)
				{
					noImage = indicePremiereImage + 2;
				}
			else if (caseATraiter instanceof Pont)
				{
					noImage = indicePremiereImage + 3;
				} 
			else if (caseATraiter instanceof T) 
				{
					noImage = indicePremiereImage + 4;
				}
			
			// affichage de l'image
			imageCourante.setImageDrawable(getResources().getDrawable(noImage));
	
			// initialisation des rotations
			initRotation(dimensionCase, duration);
	
			// rotation de l'image
			int indiceRotation;
			indiceRotation = (caseATraiter.getRotation());
			switch (indiceRotation) {
			case 0:
				imageCourante.setAnimation(rotation0);
				break;
			case 90:
				imageCourante.setAnimation(rotation90);
				break;
			case 180:
				imageCourante.setAnimation(rotation180);
				break;
			case 270:
				imageCourante.setAnimation(rotation270);
				break;
			}
		}
		else
		{
			imageCourante.setImageDrawable(getResources().getDrawable(R.drawable.fondplateau));	//affichage du fond si aucune image
		}
	}
	
	public void afficheICTCaseListe(Case caseATraiter, ImageView imageCourante, int duration, int dimensionCase) {
			
				if(!caseATraiter.isUtilise())
				{
					int noImage = 0; // numero de l'image � affecter � la case en cours de
					// traitement
					int indicePremiereImage=0x7f020010;
					
					// selection de l'image a afficher
					if (caseATraiter instanceof L)
						{
							noImage = indicePremiereImage ;
						}
					else if (caseATraiter instanceof Ldouble)
						{
							noImage = indicePremiereImage + 1;
						} 
					else if (caseATraiter instanceof plus)
						{
							noImage = indicePremiereImage + 2;
						}
					else if (caseATraiter instanceof Pont)
						{
							noImage = indicePremiereImage + 3;
						} 
					else if (caseATraiter instanceof T) 
						{
							noImage = indicePremiereImage + 4;
						}
					
					
					// affichage de l'image
					imageCourante.setImageDrawable(getResources().getDrawable(noImage));
			
					// initialisation des rotations
					initRotation(dimensionCase, duration);
			
					// rotation de l'image
					int indiceRotation;
					indiceRotation = (caseATraiter.getRotation());
					switch (indiceRotation) {
					case 0:
						imageCourante.setAnimation(rotation0);
						break;
					case 90:
						imageCourante.setAnimation(rotation90);
						break;
					case 180:
						imageCourante.setAnimation(rotation180);
						break;
					case 270:
						imageCourante.setAnimation(rotation270);
						break;
					}
				}
				else
				{
					imageCourante.setImageDrawable(getResources().getDrawable(0x7f020005));
				}
		}
	
	//initialisation des rotations
	public void initRotation(int tailleImage, int duree) {
		// rotation 0� / 360�
		int centre = tailleImage / 2;
		rotation0 = new RotateAnimation(0, 360, centre, centre);
		rotation0.setDuration(duree);
		rotation0.setFillAfter(true);

		// rotation 90�
		rotation90 = new RotateAnimation(0, 90, centre, centre);
		rotation90.setDuration(duree);
		rotation90.setFillAfter(true);

		// rotation 180�
		rotation180 = new RotateAnimation(0, 180, centre, centre);
		rotation180.setDuration(duree);
		rotation180.setFillAfter(true);

		// rotation 270�
		rotation270 = new RotateAnimation(0, 270, centre, centre);
		rotation270.setDuration(duree);
		rotation270.setFillAfter(true);
	}
	
	//traitement de l'action sur le bouton valider
	public void valider(){
		Objectif objectifCourant=maPartie.getObjectifCourant();	//recuperation de l'objectif courant
		Plateau monPlateau=maPartie.getMonPlateau();	//recuperation du plateau de la partie
		Point origine=null,objectif=null;

		//test pour savoir si le plateau est totalement rempli
		for (int ligne = 0; ligne < 5; ligne++) {
			for (int colonne = 0; colonne < 5; colonne++) {
				if(monPlateau.getCase(ligne, colonne)==null)
				{
					notif("Le plateau doit être totalement rempli");
					return;
				}
			}
		}
		
		//test pour savoir si avec le mode "adulte", tous les chemins sont corrects
		if(!testCheminCorrect() && maPartie.getDifficulte().equals("adulte"))
		{
			notif("Vous avez choisi le mode 'Adulte'," +
					"veuillez faire des chemins correts");
			return;
		}
		
		//verification que chaque couple de l'objectif sont possibles
		for(int i=0;i<objectifCourant.getListeCouple().size();i++)
		{
			Couple coupleATraiter=objectifCourant.getListeCouple().get(i);	//recuperation du couple a traiter
			origine=coupleATraiter.getOrigine();		//recuperation de l'origine du couple a traiter
			objectif=coupleATraiter.getObjectif();		//recuperation de l'objectif du couple a traiter
		
			testDesObjectifs(origine);	//fonction testant la possibilité d'atteindre l'ojectif depuis l'origine
			
			//si cela n'est pas possible, on sort de la fonction
			if((coupleATraiter.getaRealiser()==true && monPlateau.getCase(objectif.getLigne(), objectif.getColonne()).getFlag()==0)
				|| (coupleATraiter.getaRealiser()==false && monPlateau.getCase(objectif.getLigne(), objectif.getColonne()).getFlag()==1))
			{
				notif("Votre configuration ne permet pas de valider l'objectif");
				return;
			}
		}
		notif("BRAVO, vous avez réussi ce niveau !");	
		maPartie.objectifSvt();	//modification de l'objectif
		MAJobjectif();	//mise a jour de l'affichage de l'objectif
		maPartie.init();	//remise a zero de plateau
		afficheListeCases(0);	//mise a jour de l'affichage des cartes restantes
		affichePlateau();	//mise a jour de l'affichage du plateau 

	}
	
	//application d'une fonction permettant la validation de l'objectif
	public void testDesObjectifs(Point origine)
	{
		Plateau monPlateau=maPartie.getMonPlateau(); //recuperation du plateau de la partie
		for (int ligne = 0; ligne < 5; ligne++) {
			for (int colonne = 0; colonne < 5; colonne++) {
				monPlateau.getCase(ligne, colonne).setFlag(0);
				monPlateau.getCase(ligne, colonne).setEntree(0);
				monPlateau.getCase(ligne, colonne).setSortie(0);
			}
		}
		fonction(origine.getLigne(),origine.getColonne(),monPlateau);
	}
	
	//fonction testant si les chemins sont corrects
	public boolean testCheminCorrect(){
		Plateau monPlateau=maPartie.getMonPlateau();
		Case caseEnTest=null;
		boolean condition=true;
		for (int ligne = 1; ligne < 4; ligne++) {
			for (int colonne = 1; colonne < 4; colonne++) {
				caseEnTest=maPartie.getMonPlateau().getCase(ligne, colonne);
				if(caseEnTest!=null)
				{
					for(int i=1; i<=4;i++)
					{
						if(caseEnTest.getTabDroit(i))
						{
							switch (i){
								case 1:
									if(!(monPlateau.getCase(ligne - 1, colonne).getTabDroit(3)))
									{
										condition=false;
									}
									break;
								case 2:
									if(!(monPlateau.getCase(ligne, colonne + 1).getTabDroit(4)))
									{
										condition=false;
									}
									break;
								case 3:
									if(!(monPlateau.getCase(ligne + 1, colonne).getTabDroit(1)))
									{
										condition=false;
									}
									break;
								case 4:
									if(!(monPlateau.getCase(ligne , colonne -1 ).getTabDroit(2)))
									{
										condition=false;
									}
									break;
							}
									
						}
					}
				}
			}
		}
		if (!condition)
		{
			return false;
		}
		return true;
	}
	
	//fonction recursive permettant de savoir toutes les cases accessibles depuis une case
	public void fonction(int ligne, int colonne, Plateau monPlateau) {
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
				}
				else if (maCase.getRotation()==90 || maCase.getRotation()==270)
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
				}
					maCase.setEntree(0);			
			}
			int sortieInterdite=0;
			if(maCase.getEntree()==1){
				sortieInterdite=3; //sortie de la case de laquelle on vient
			}
			else if(maCase.getEntree()==2){
				sortieInterdite=4;
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
    
	//suppression des barres android
	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
    
	//methode pour les notications
	public void notif(CharSequence text) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
	}
}