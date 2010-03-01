package GoGetter.iut.com;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Couple implements Serializable {
	Point origine;
	Point objectif;
	Boolean aRealiser;
	
	public Couple(Point point, Point point2){
		this.objectif=point2;
		this.origine=point;
		this.aRealiser=true;
	}

	public Couple(Point point, Point point2, boolean aRealiser){
		this.objectif=point2;
		this.origine=point;
		this.aRealiser=aRealiser;
	}
	
	public Boolean getaRealiser() {
		return aRealiser;
	}

	public Point getOrigine() {
		return origine;
	}

	public void setOrigine(Point origine) {
		this.origine = origine;
	}

	public Point getObjectif() {
		return objectif;
	}

	public void setObjectif(Point objectif) {
		this.objectif = objectif;
	}
	
	
	
}
