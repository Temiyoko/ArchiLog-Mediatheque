package entities;

public class DVD extends Media {
	private boolean adulte;

	public DVD(int num, String titre, boolean adulte) {
		super(num, titre);
		this.adulte = adulte;
	}
	
	public DVD(int num, String titre, boolean adulte, Abonne abE, Abonne abR) {
		super(num, titre, abE, abR);
		this.adulte = adulte;
	}
	
	@Override
	public void reservationPour(Abonne ab) throws EmpruntException{
		// precondition : ni réservé ni emprunté 
        assert reserveur() != null;
		if(emprunteur() != null) {
        	System.out.println("Ce DVD est déjà emprunté");
        	return;
        }
		
		// EmpruntException si ab n’a pas le droit de réserver CE document
		try {
        	if(adulte && ab.getAge() <= 16) {
        		throw new EmpruntException();
        	}
        	super.reservationPour(ab);
		}
		catch(EmpruntException e) {
			System.out.println("Vous n'avez pas l'âge pour emprunter ce DVD");
		}
	}
	
	@Override
    public void empruntPar(Abonne ab) throws EmpruntException {
		// EmpruntException si ab n’a pas le droit d’emprunter CE document
		try {
        	if(adulte && ab.getAge() <= 16) {
        		throw new EmpruntException();
        	}
        	super.empruntPar(ab);
		}
		catch(EmpruntException e) {
			System.out.println("Vous n'avez pas l'âge pour emprunter ce DVD");
		}
	}

}
