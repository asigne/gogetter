package GoGetter.iut.com;


public class Pont extends Case {
	private static final long serialVersionUID = 1L;

	public Pont(int rotation) {
		super(4);
		this.tabDroit[1] = true;
		this.tabDroit[2] = true;
		this.tabDroit[3] = true;
		this.tabDroit[4] = true;
		this.tabDroit[0] = true;
		rotate(rotation);
	}

	public String toString() {
		return "pont " + super.toString();
	}
}
