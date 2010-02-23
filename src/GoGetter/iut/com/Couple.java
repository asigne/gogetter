package GoGetter.iut.com;

public class Couple {
	Point origine;
	Point objectif;
	
	public Couple(Point point, Point point2){
		this.objectif=point2;
		this.origine=point;
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
