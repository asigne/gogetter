package GoGetter.iut.com;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GoGetter extends Activity {
	
	TextView textObjectif;
	Button btnValider;
	int ligne, colonne;
	int xmin=30, xmax=288, ymin=30, ymax=288;
	RotateAnimation rotation0, rotation90, rotation180, rotation270;
	Partie maPartie;
	
    /** Called when the activity is first created. */
    @Override
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
    
 // methode permettant de g�rer les clic sur l'�cran
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Configuration c = getResources().getConfiguration();
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int x = (int) (event.getX());
				int y = (int) (event.getY());
				//textObjectif.setText(x+" "+y);
				//convertion du click sur l'ecran
					if (x > xmin && x <= xmax && y > ymin && y < ymax) {				
						// action sur une case du plateau
						ligne=(int) (y-30)/86;
						colonne=(int) (x-30)/86;
						actionPlateau(ligne,colonne);
					//	textObjectif.setText("plateau"+ligne+" "+colonne);
						return true;
					}
					
					if (x > 0 && x <= 235 && y > 318 && y < 413) {				
						// action sur une case a la liste
						if(y > 318 && y < 365)
						{
							ligne=0;
						}
						else
						{
							ligne=1;
						}
						colonne=(int) x/47;
				//		textObjectif.setText("listeCase"+ligne+" "+colonne);
						if(ligne!=1 || colonne!=4){
							selectionnerCase(ligne, colonne);
						}
						
						return true;
					}
					
					if (x > 240 && x <= 320 && y > 325 && y < 405) {				
						// action sur la case courante
				//		textObjectif.setText("actionCaseC"+x+" "+y);
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
			maPartie.caseCourante.rotate(90); // rotation de la carte
			afficheCaseCourante(150); // affichage de la carte
			afficheListeCases(0);
		}
	}
	
	public void actionPlateau(int ligne, int colonne){
		Plateau monPlateau=maPartie.getMonPlateau();
		if(monPlateau.getCase(ligne, colonne)==null) //si il y a aucune case a cet endroit
		{
			if(maPartie.getCaseCourante()!=null)
			{
				Case caseCourante=maPartie.getCaseCourante();
				if(!caseCourante.utilise)
				{
					caseCourante.setUtilise(true);
					monPlateau.setCase(caseCourante, ligne, colonne);
					//textObjectif.setText("azert"+caseCourante.getLigneOri()+caseCourante.getColonneOri());
					//maPartie.setCasesRestantes(caseCourante.getLigneOri(), caseCourante.getColonneOri(), null);
				}	
			}
		}
		else
		{
			Case caseARetirer=monPlateau.getCase(ligne, colonne);
			monPlateau.setCase(null, ligne, colonne);
			caseARetirer.setUtilise(false);
			maPartie.setCasesRestantes(caseARetirer.getLigneOri(), caseARetirer.getColonneOri(), caseARetirer);
		}
		affichePlateau(0);
		afficheListeCases(0);
		afficheCaseCourante(0);
	}
	
	public void MAJobjectif() {
		int numPremierObjectif=0x7f040002;
		int numObjectif=maPartie.getListeObjectif().indexOf(maPartie.getObjectifCourant());
		textObjectif.setText(numPremierObjectif+numObjectif);
	}
	
	public void affichePlateau(int duration){
		Case caseATraiter=null;
		int indice=0x7f050003;
		for (int ligne=0;ligne<3;ligne++)
		{
			for (int colonne=0;colonne<3;colonne++)
			{
					caseATraiter = maPartie.getMonPlateau().getCase(ligne, colonne);
					ImageView ICT = (ImageView) findViewById(indice);
					afficheICT(caseATraiter, ICT, duration, 86);
					indice++;
			}
			indice++;
		}
	}
	
	public void afficheListeCases(int duration) {
		Case caseATraiter=null;
		int indice=0x7f050024;
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
					ImageView ICT = (ImageView) findViewById(indice);
					if(!caseATraiter.utilise)
					{
						afficheICT(caseATraiter, ICT, duration, 47);
						//textObjectif.setText(""+indice);
					}
					else
					{										///il y aura un pb
						afficheICT(null, ICT, duration, 47);
					}
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
		if(!caseATraiter.utilise)
		{
			// application de la modificiation
			afficheICT(caseATraiter, ICT, duration, 80);
		}
		else
		{
			//caseATraiter=null; 												///il y aura un pb
			afficheICT(null, ICT, duration, 47);
		}
	}
	
	public void afficheICT(Case caseATraiter, ImageView imageCourante,
		int duration, int dimensionCase) {
		
		if(caseATraiter!=null)
		{
			int noImage = 0; // numero de l'image � affecter � la case en cours de
			// traitement
			int noImageCourante;
			// recuperation du numero de l'image a partir de l'intance de la case en
			// cours de traitement
			int indicePremiereImage=0x7f02000e;
			
			noImageCourante = caseATraiter.getNumImage();
	
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
			imageCourante.setImageDrawable(null);
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
		
	}
	
	

	public void fonction(int ligne, int colonne, Plateau monPlateau) {
		Case maCase = monPlateau.getCase(ligne, colonne); // recupere la case à
		// traiter
		int L1 = 0, C1 = 0, S = 0, E=0;
		boolean Ok;

		//monPlateau.getCase(ligne, colonne).setFlag(1); // met a 1 le flag de la
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
			}
			
			if(maCase instanceof Ldouble){
				if(maCase.getEntree()==1)
				{
					i=2;
				}	
				else if(maCase.getEntree()==2)
				{
					i=3;
				}	
				else if(maCase.getEntree()==3)
				{
					i=4;
				}
				else if(maCase.getEntree()==4)
				{
					i=4;
				}
			}
			
			switch (i) {
			case 1: // haut
					if (ligne > 0
							&& maCase.getTabDroit(1) == true
							&& monPlateau.getCase(ligne - 1, colonne)
							.getTabDroit(3) == true
							&& monPlateau.getCase(ligne - 1, colonne).getFlag() == 0) {
						L1 = -1;
						C1 = 0;
						S = 1;
						E = 3;
						Ok = true;
					}
					break;
			case 2: // droite
				if (colonne < 6
						&& maCase.getTabDroit(2) == true
						&& monPlateau.getCase(ligne, colonne + 1)
						.getTabDroit(4) == true
						&& monPlateau.getCase(ligne, colonne + 1).getFlag() == 0) {
					L1 = 0;
					C1 = 1;
					S = 2;
					E = 4;
					Ok = true;
				}
				break;
			case 3: // bas
				if (ligne < 6
						&& maCase.getTabDroit(3) == true
						&& monPlateau.getCase(ligne + 1, colonne)
						.getTabDroit(1) == true
						&& monPlateau.getCase(ligne + 1, colonne).getFlag() == 0) {
					L1 = 1;
					C1 = 0;
					S = 3;
					E = 1;
					Ok = true;
				}
				break;
			case 4: // gauche
				if (colonne > 0
						&& maCase.getTabDroit(4) == true
						&& monPlateau.getCase(ligne, colonne - 1)
						.getTabDroit(2) == true
						&& monPlateau.getCase(ligne, colonne - 1).getFlag() == 0) {
					L1 = 0;
					C1 = -1;
					S = 4;
					E = 2 ;
					Ok = true;
				}
				break;
			default:
			}
			if (Ok == true) // s'il est possible de sortir de la case en cours
				// de traitement
			{
				monPlateau.getCase(ligne, colonne).setEntree(E);
				monPlateau.getCase(ligne, colonne).setSortie(S); // on indique
				// par ou on
				// sort
				// on change de case
				ligne = ligne + L1;
				colonne = colonne + C1;

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
	}
    
	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
    
}