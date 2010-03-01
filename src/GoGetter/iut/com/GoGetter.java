package GoGetter.iut.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GoGetter extends Activity {

	Button nouvellePartieAdulte, nouvellePartieEnfant, chargerPartie, quitterPartie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreen();
		setContentView(R.layout.accueil);

		nouvellePartieAdulte = (Button) findViewById(R.id.nouvellePartieAdulte);
		nouvellePartieEnfant = (Button) findViewById(R.id.nouvellePartieEnfant);
		chargerPartie = (Button) findViewById(R.id.chargerPartie);
		quitterPartie = (Button) findViewById(R.id.quitterPartie);

		nouvellePartieAdulte.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nouvellePartieAdulte();
			}
		});
		
		nouvellePartieEnfant.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nouvellePartieEnfant();
			}
		});

		chargerPartie.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chargerPartie();

			}
		});
		
		quitterPartie.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void nouvellePartieAdulte() {
		// creation de l'intent
		Intent defineIntent = new Intent(this, Jeu.class);
		Bundle objetbundle = new Bundle();
		objetbundle.putString("typePartie", "nouvellePartie");
		objetbundle.putString("difficulte", "adulte");
		defineIntent.putExtras(objetbundle);
		startActivity(defineIntent);
	}
	
	public void nouvellePartieEnfant() {
		// creation de l'intent
		Intent defineIntent = new Intent(this, Jeu.class);
		Bundle objetbundle = new Bundle();
		objetbundle.putString("typePartie", "nouvellePartie");
		objetbundle.putString("difficulte", "enfant");
		defineIntent.putExtras(objetbundle);
		startActivity(defineIntent);
	}

	public void chargerPartie() {
		// creation de l'intent
		Intent defineIntent = new Intent(this, Jeu.class);
		Bundle objetbundle = new Bundle();
		objetbundle.putString("typePartie", "sauvPartie");
		defineIntent.putExtras(objetbundle);
		startActivity(defineIntent);
	}

	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
