package GoGetter.iut.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GoGetter extends Activity {

	Button nouvellePartie, chargerPartie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreen();
		setContentView(R.layout.accueil);

		nouvellePartie = (Button) findViewById(R.id.nouvellePartie);
		chargerPartie = (Button) findViewById(R.id.chargerPartie);
		// rejMulti = (Button) findViewById(R.id.rejMulti);

		nouvellePartie.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nouvellePartie();

			}
		});

		chargerPartie.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chargerPartie();

			}
		});
	}

	public void nouvellePartie() {
		// creation de l'intent
		Intent defineIntent = new Intent(this, Jeu.class);
		startActivity(defineIntent);
	}

	public void chargerPartie() {
		// creation de l'intent
		Intent defineIntent = new Intent(this, Jeu.class);
		startActivity(defineIntent);
	}

	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
