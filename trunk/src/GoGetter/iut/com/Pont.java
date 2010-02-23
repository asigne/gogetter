package GoGetter.iut.com;


public class Pont extends Case {
	public Pont(int rotation) {
		super(4);
		this.tabDroit[1] = true;
		this.tabDroit[2] = true;
		this.tabDroit[3] = true;
		this.tabDroit[4] = true;
		this.tabDroit[0] = true;
		rotate(rotation);
	}

	@Override
	public String toString() {
		return "pont " + super.toString();
	}
}
