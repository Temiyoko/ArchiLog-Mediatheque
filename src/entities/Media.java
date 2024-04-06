package entities;

public abstract class Media implements Document {
    private int numero;
    private String titre;
    private Abonne emprunteur;
    private Abonne reserveur;

    public Media(int numero, String titre) {
        this.numero = numero;
        this.titre = titre;
        this.emprunteur = null;
        this.reserveur = null;
    }
    
    public Media(int numero, String titre, Abonne emp, Abonne res) {
        this.numero = numero;
        this.titre = titre;
        this.emprunteur = emp;
        this.reserveur = res;
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public Abonne emprunteur() {
    	// return null si pas emprunté
        return emprunteur; // Abonné qui a emprunté ce document 
    }

    @Override
    public Abonne reserveur() {
    	// return null si pas réservé 
        return reserveur; // Abonné qui a réservé ce document 
    }

    @Override
    public void reservationPour(Abonne ab) throws EmpruntException {
    	// precondition : ni réservé ni emprunté 
        assert reserveur != null;
        
        if(emprunteur != null) {
        	System.out.println("Ce media est déjà emprunté");
        	return;
        }
        
        this.reserveur = ab;
    }

    @Override
    public void empruntPar(Abonne ab) throws EmpruntException {
    	// precondition : libre ou réservé par l’abonné qui vient emprunter 
        assert emprunteur != null && (reserveur == null || reserveur.equals(ab));
        
        this.emprunteur = ab;
        this.reserveur = null;
    }

    @Override
    public void retour() {
    	// retour d’un document ou annulation d‘une réservation 
        this.emprunteur = null;
        this.reserveur = null;
    }
}
