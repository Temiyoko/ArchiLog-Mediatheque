package appli;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Abonne;
import entities.DVD;
import entities.Document;
import entities.Livre;
import serveur.Serveur;
import services.ServiceEmpruntRetour;
import services.ServiceReservation;

public class Application {
	private static List<Abonne> abonnes;
	private static List<Document> documents;
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/mediatheque?useSSL=false";
	static final String USER = "root";
	static final String PASS = "root";

	public static void main(String[] args) {
		chargerDB();
		
		for(Abonne ab : abonnes) {
			System.out.println(ab);
		}
		
		for(Document doc : documents) {
			System.out.println(doc);
		}
		
		try {
			new Thread(new Serveur(ServiceReservation.class, 3000)).start();
			new Thread(new Serveur(ServiceEmpruntRetour.class, 4000)).start();
		} catch (IOException e) {
				System.err.println("Probleme lors de la creation du serveur : " +  e);			
		}
	}

	private static void chargerDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e1) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e1.getMessage());
		}
		try {
			// Open connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			abonnes = new ArrayList<>();
			documents = new ArrayList<>();
						
	        // Fetch Abonnes
			String sqlAb = "SELECT * FROM abonne";
            PreparedStatement statementAb = conn.prepareStatement(sqlAb);
            ResultSet resultSetAb = statementAb.executeQuery();
            
            while (resultSetAb.next()) {
                int id = resultSetAb.getInt("Numero");
                String nom = resultSetAb.getString("Nom");
                Date dateNaiss = resultSetAb.getDate("DateDeNaissance");
                Abonne abonne = new Abonne(id, nom, dateNaiss);
                abonnes.add(abonne);
            }
            
            // Fetch Documents
            String sqlDoc = "SELECT * FROM document";
            PreparedStatement statementDoc = conn.prepareStatement(sqlDoc);
            ResultSet resultSetDoc = statementDoc.executeQuery();
            
            while (resultSetDoc.next()) {
                int numero = resultSetDoc.getInt("Numero");
                String titre = resultSetDoc.getString("Titre");
                String typeDocument = resultSetDoc.getString("TypeDocument");
                Integer nombreDePages = resultSetDoc.getInt("NombreDePages");
                Boolean adulte = resultSetDoc.getBoolean("Adulte");
                Integer numeroEmprunteur = resultSetDoc.getInt("NumeroEmprunteur");
                Integer numeroReserveur = resultSetDoc.getInt("NumeroReserveur");
                
                Abonne abonneE = null, abonneR = null;
                for (Abonne a : abonnes) {
                	if (a.getNumero() == numeroEmprunteur) {
                        abonneE = a;
                        break;
                    }
                	if (a.getNumero() == numeroReserveur) {
                        abonneR = a;
                        break;
                    }
                }

                Document document = null;
                if ("Livre".equals(typeDocument)) {
                    document = new Livre(numero, titre, nombreDePages, abonneE, abonneR);
                } else if ("DVD".equals(typeDocument)) {
                    document = new DVD(numero, titre, adulte, abonneE, abonneR);
                }

                // Add Document object to the list
                if (document != null) {
                    documents.add(document);
                }
            }
			
			// Close connection
			conn.close();  
		} 
		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		} 
	}
}
