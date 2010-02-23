package GoGetter.iut.com;



@SuppressWarnings("serial")
public class T extends Case {
	public T(int rotation) {
		super(5);
		this.tabDroit[1] = false;
		this.tabDroit[2] = true;
		this.tabDroit[3] = true;
		this.tabDroit[4] = true;
		this.tabDroit[0] = false;
		rotate(rotation);
	}

	@Override
	public String toString() {
		return "T " + super.toString();
	}
}
