package GoGetter.iut.com;



@SuppressWarnings("serial")
public class Ldouble extends Case {
	public Ldouble(int rotation) {
		super(1);
		this.tabDroit[1] = true;
		this.tabDroit[2] = true;
		this.tabDroit[3] = true;
		this.tabDroit[4] = true;
		this.tabDroit[0] = true;
		rotate(rotation);
	}

	@Override
	public String toString() {
		return "Ldouble " + super.toString();
	}
}
