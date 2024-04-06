package entities;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;


public class Abonne {
	private int numero;
	private String nom;
	private Date dateNaiss;
	
	public Abonne(int num, String nom, Date naissance) {
		this.numero = num;
		this.nom = nom;
		this.dateNaiss = naissance;
	}
	
	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}
	
	public int getAge() {
        LocalDate birthDate = dateNaiss.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
	
	@Override
	public String toString() {
		return numero + " " + nom + " " + dateNaiss;
	}
}
