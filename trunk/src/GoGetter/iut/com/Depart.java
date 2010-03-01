package GoGetter.iut.com;



@SuppressWarnings("serial")
public class Depart extends Case {
	public Depart(int rotation) {
		super();
		this.tabDroit[1] = false;
		this.tabDroit[2] = false;
		this.tabDroit[3] = true;
		this.tabDroit[4] = false;
		this.tabDroit[0] = false;
		rotate(rotation);
	}

	@Override
	public String toString() {
		return "Dep " + super.toString();
	}
}
