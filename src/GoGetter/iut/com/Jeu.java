package GoGetter.iut.com;

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
	String etatPlateau;
	TextView textObjectif;
	Button btnValider;
	int ligne, colonne;
	int xmin=40, xmax=288, ymin=40, ymax=288;
	RotateAnimation rotation0, rotation90, rotation180, rotation270;
	Partie maPartie;
	int indicePremiereCaseDispo=0x7f050027;
	int indicePremiereCaseTableau=0x7f050006;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.main);
               
		textObjectif = (TextView) findViewById(R.id.textObjectif);
		btnValider = (Button) findViewById(R.id.Valider);    
		
		maPartie=new Partie();
		maPartie.init();
		afficheListeCases(0);
		MAJobjectif();
		
		btnValider.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				valider();
			}
		});
		
    }
    
 // methode permettant de gérer les clic sur l'écran
	public boolean onTouchEvent(MotionEvent event) {
		//Configuration c = getResources().getConfiguration();
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int x = (int) (event.getX());
				int y = (int) (event.getY());
				//convertion du clique sur l'ecran
					if (x > xmin && x <= xmax && y > ymin && y < ymax) {				
						// action sur une case du plateau
						ligne=(int) (y-30)/86;
						colonne=(int) (x-30)/86;
						actionPlateau(ligne+1,colonne+1);
						return true;
					}
					if (x > 0 && x <= 235 && y > 318 && y < 413) {				
						// action sur une case de la liste
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
	
	public void selectionnerCase(int ligne, int colonne) {
		maPartie.setCaseCourante(maPartie.getCaseDispo(ligne, colonne));
		afficheCaseCourante(0);
	}

	public void actionCaseCourante() {
		if(maPartie.getCaseCourante()!=null)
		{
			maPartie.getCaseCourante().rotate(90); // rotation de la carte
			afficheCaseCourante(150); // affichage de la carte
			afficheCaseListe(maPartie.getCaseCourante());
		}
	}
	
	public void actionPlateau(int ligne, int colonne){
		Plateau monPlateau=maPartie.getMonPlateau();
		Case caseListe=null;
		
			if(monPlateau.getCase(ligne, colonne)==null) //si il y a aucune case a cet endroit
			{
				if(maPartie.getCaseCourante()!=null)
				{
					Case caseCourante=maPartie.getCaseCourante();
					if(!caseCourante.utilise)
					{
						caseCourante.setUtilise(true);
						monPlateau.setCase(caseCourante, ligne, colonne);
						maPartie.setCaseCourante(null);
					}	
					caseListe=maPartie.getMonPlateau().getCase(ligne, colonne);
					
				}
			}
			else 
			{
				Case caseARetirer=monPlateau.getCase(ligne, colonne);
				monPlateau.setCase(null, ligne, colonne);
				caseARetirer.setUtilise(false);
				maPartie.setCasesRestantes(caseARetirer.getLigneOri(), caseARetirer.getColonneOri(), caseARetirer);
				caseListe=caseARetirer;
			}
			afficheCasePlateau(maPartie.getMonPlateau().getCase(ligne, colonne), ligne, colonne);
			//affichePlateau(0);
			if(caseListe!=null)
			{
				afficheCaseListe(caseListe);
			}
			afficheCaseCourante(0);
		}
	
	public void MAJobjectif() {
		int numPremierObjectif=0x7f040002;
		int numObjectif=maPartie.getListeObjectif().indexOf(maPartie.getObjectifCourant());
		textObjectif.setText(numPremierObjectif+numObjectif);
	}
		
	public void afficheCasePlateau(Case caseATraiter, int ligneCase, int colonneCase){
		int indice=indicePremiereCaseTableau;
		ImageView ICT=null;
		for (int ligne=1;ligne<4;ligne++)
		{
			for (int colonne=1;colonne<4;colonne++)
			{
				ICT = (ImageView) findViewById(indice);
				if(ligne==ligneCase && colonne==colonneCase)
					{
						afficheICT(caseATraiter, ICT, 0, 80);
					}
				indice++;
			}
			indice++;
		}
		
	}
	
	public void affichePlateau(int duration){
		Case caseATraiter=null;
		int indice=indicePremiereCaseTableau;
		ImageView ICT=null;
		for (int ligne=1;ligne<4;ligne++)
		{
			for (int colonne=1;colonne<4;colonne++)
			{
				caseATraiter = maPartie.getMonPlateau().getCase(ligne, colonne);
				ICT = (ImageView) findViewById(indice);
				afficheICT(caseATraiter, ICT, 0, 80);
				indice++;
			}
			indice++;
		}
	}
	
	public void afficheCaseListe(Case maCase)
	{
		int indice=indicePremiereCaseDispo;
		ImageView ICT=null;
		int ligneOri=maCase.getLigneOri();
		int colonneOri=maCase.getColonneOri();
		for (int i=0;i<2;i++)
		{
			int max;
			if(i==0)
				max=5;
			else
				max=4;
			for (int j=0;j<max;j++)
			{
				ICT = (ImageView) findViewById(indice);
				if(i==ligneOri && j==colonneOri)
					{
						afficheICTCaseListe(maCase, ICT, 0, 48);
					}
				indice++;
			}
		indice++;
		}
	}

	public void afficheListeCases(int duration) {
		Case caseATraiter=null;
		int indice=indicePremiereCaseDispo;
		for (int i=0;i<2;i++)
		{
			int max;
			if(i==0)
				max=5;
			else
				max=4;
			for (int j=0;j<max;j++)
			{
					caseATraiter = maPartie.getCaseDispo(i, j);
					if(caseATraiter.isUtilise())
					{
						caseATraiter=null;
					}
					ImageView ICT = (ImageView) findViewById(indice);
					afficheICTCaseListe(caseATraiter, ICT, duration, 48);
					indice++;
			}
			indice++;
		}
	}

	public void afficheCaseCourante(int duration) {
		// recuperation de la case a traiter
		Case caseATraiter = maPartie.getCaseCourante();
		// recuperation de l'ImageView a traiter
		ImageView ICT = (ImageView) findViewById(R.id.CaseCourante);
		if(caseATraiter!=null)
		{
			if(caseATraiter.isUtilise())
			{
				caseATraiter=null;
			}
		}
		afficheICT(caseATraiter, ICT, duration, 70);
	}
	
	public void afficheICT(Case caseATraiter, ImageView imageCourante,
		int duration, int dimensionCase) {
		
		if(caseATraiter!=null)
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
	
	public void afficheICTCaseListe(Case caseATraiter, ImageView imageCourante,
			int duration, int dimensionCase) {
			
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
	
	public void valider(){
		Objectif objectifCourant=maPartie.getObjectifCourant();
		Plateau monPlateau=maPartie.getMonPlateau();
		boolean plateauRempli, condition;
		Point origine,objectif;
		origine=null;
		objectif=null;
		plateauRempli = true;
		condition=true;
			
		
		for(int i=0;i<objectifCourant.getListeCouple().size();i++)
		{
			Couple coupleATraiter=objectifCourant.getListeCouple().get(i);
			origine=coupleATraiter.getOrigine();
			objectif=coupleATraiter.getObjectif();		
		
			for (ligne = 0; ligne < 5; ligne++) {
				for (colonne = 0; colonne < 5; colonne++) {
					
					if(monPlateau.getCase(ligne, colonne)==null)
					{
						plateauRempli=false;
						notif("Le plateau doit être totalement rempli");
						return;
					}
					monPlateau.getCase(ligne, colonne).setFlag(0);
					monPlateau.getCase(ligne, colonne).setEntree(0);
					monPlateau.getCase(ligne, colonne).setSortie(0);
				}
			}
			
			if(plateauRempli)
			{
				fonction(origine.getLigne(),origine.getColonne(),monPlateau);
			}
		
			
			if((coupleATraiter.getaRealiser()==true && monPlateau.getCase(objectif.getLigne(), objectif.getColonne()).getFlag()==0)
				|| (coupleATraiter.getaRealiser()==false && monPlateau.getCase(objectif.getLigne(), objectif.getColonne()).getFlag()==1))
			{
				condition=false;
			}
		}
			if(condition==true)
			{
				notif("Bravo, vous avez réussi ce niveau !");
				textObjectif.setText("Gagne");
				maPartie.objectifSvt();
				MAJobjectif();
				maPartie.init();
				afficheListeCases(0);
				affichePlateau(0);
			}
			else
			{
				notif("Votre configuration ne permet pas de valider l'objectif");
			}
	}
	
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
				sortieInterdite=3;
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
		//monPlateau.getCase(ligne, colonne).setSortie(0); // lorsque la fonction
		// se termine sur
		// une case on met
		// la sortie à 0
		System.out.println("fin fonction"+ligne+colonne);
	}
    
	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
    
	public void notif(CharSequence text) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
	}
}