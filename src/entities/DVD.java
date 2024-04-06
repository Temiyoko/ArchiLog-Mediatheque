package entities;

public class DVD extends Media {
	private boolean adulte;

	public DVD(int num, String titre, boolean adulte, Abonne abE, Abonne abR) {
		super(num, titre, abE, abR);
		this.adulte = adulte;
	}
	
	@Override
	public void reservationPour(Abonne ab) throws EmpruntException{
		// EmpruntException si ab n’a pas le droit de réserver CE document
		if(adulte && ab.getAge() <= 16) {
			throw new EmpruntException("Vous n'avez pas l'âge pour réserver ce DVD");
		}
		super.reservationPour(ab);
	}
	
	@Override
    public void empruntPar(Abonne ab) throws EmpruntException {
		// EmpruntException si ab n’a pas le droit d’emprunter CE document
		if(adulte && ab.getAge() <= 16) {
			throw new EmpruntException("Vous n'avez pas l'âge pour emprunter ce DVD");
		}
		super.empruntPar(ab);
	}

	@Override
	public String getClassName() {
		return "DVD";
	}
}
