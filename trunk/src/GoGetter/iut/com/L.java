package GoGetter.iut.com;



@SuppressWarnings("serial")
public class L extends Case {
	public L(int rotation) {
		super(2);
		this.tabDroit[1] = true;
		this.tabDroit[2] = true;
		this.tabDroit[3] = false;
		this.tabDroit[4] = false;
		this.tabDroit[0] = false;
		rotate(rotation);
	}

	@Override
	public String toString() {
		return "L " + super.toString();
	}
}
