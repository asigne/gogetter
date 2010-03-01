package GoGetter.iut.com;


@SuppressWarnings("serial")
public class Pont extends Case {

	public Pont(int rotation) {
		super();
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
