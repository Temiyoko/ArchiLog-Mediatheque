package entities;

public class Livre extends Media {
	private int nombreDePages;

	public Livre(int num, String titre, int nbPages) {
		super(num, titre);
		this.nombreDePages = nbPages;
	}
	
	public Livre(int num, String titre, int nbPages, Abonne abE, Abonne abR) {
		super(num, titre, abE, abR);
		this.nombreDePages = nbPages;
	}
	
	@Override
    public void reservationPour(Abonne ab) throws EmpruntException {
    	// precondition : ni réservé ni emprunté 
        assert reserveur() != null;
        
        if(emprunteur() != null) {
        	System.out.println("Ce livre est déjà emprunté");
        	return;
        }
        
        reservationPour(ab);
    }

}
