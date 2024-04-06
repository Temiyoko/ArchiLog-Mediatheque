package entities;

public abstract class Media implements Document {
    private int numero;
    private String titre;
    private Abonne emprunteur;
    private Abonne reserveur;
    
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
    public synchronized Abonne emprunteur() {
    	// return null si pas emprunté
        return emprunteur; // Abonné qui a emprunté ce document 
    }

    @Override
    public synchronized Abonne reserveur() {
    	// return null si pas réservé 
        return reserveur; // Abonné qui a réservé ce document 
    }

    @Override
    public void reservationPour(Abonne ab) throws EmpruntException {
    	// precondition : ni réservé ni emprunté
        if (emprunteur() != null) {
            throw new EmpruntException("Le " + getClassName() + " est déjà emprunté.");
        }
        if (reserveur() != null) {
            throw new EmpruntException("Le " + getClassName() + " est déjà réservé.");
        }
        reserveur = ab;
    }

    @Override
    public synchronized void empruntPar(Abonne ab) throws EmpruntException {
    	// precondition : libre ou réservé par l’abonné qui vient emprunter 
        if (emprunteur() != null) {
            throw new EmpruntException("Le " + getClassName() + " est déjà emprunté.");
        }
        if (reserveur() != null && !reserveur.equals(ab)) {
            throw new EmpruntException("Le " + getClassName() + " est réservé par un autre abonné.");
        }
        emprunteur = ab;
        reserveur = null;
    }

    @Override
    public synchronized void retour() {
    	// retour d’un document ou annulation d‘une réservation 
        this.emprunteur = null;
        this.reserveur = null;
    }

    @Override
    public String toString() {
        return getClassName() + "{" +
                "numero=" + numero +
                ", titre='" + titre + '\'' +
                ", emprunteur=" + emprunteur +
                ", reserveur=" + reserveur +
                '}';
    }

    public abstract String getClassName();
}
