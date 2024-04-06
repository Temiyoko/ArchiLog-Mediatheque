package entities;

public class Livre extends Media {
	private int nombreDePages;
	
	public Livre(int num, String titre, int nbPages, Abonne abE, Abonne abR) {
		super(num, titre, abE, abR);
		this.nombreDePages = nbPages;
	}

	@Override
	public String getClassName() {
		return "livre";
	}
}
