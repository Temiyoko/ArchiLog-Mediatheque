package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import entities.Abonne;
import entities.Document;
import entities.EmpruntException;
import serveur.Service;

public class ServiceReservation extends Service{
	private static List<Abonne> abonnes;
	private static List<Document> documents;
	private Timer timer = new Timer();

	public ServiceReservation(Socket socket) {
		super(socket);
	}

	public static void init(List<Abonne> ab, List<Document> doc){
		abonnes = ab;
		documents = doc;
	}

	@Override
	public void run() {
        PrintWriter out = null;
		BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(getClient().getInputStream()));
            out = new PrintWriter(getClient().getOutputStream(), true);

            out.println("Quel est votre numéro d'abonné ?");
            int numAbo = Integer.parseInt(in.readLine());

            Abonne ab = null;
            for (Abonne a : abonnes) {
                if (a.getNumero() == numAbo) {
                    ab = a;
                    out.println("Bienvenue " + a.getNom());
                }
            }

            if (ab == null) {
                throw new RuntimeException();
            }

            out.println("Quel document voulez-vous réserver ?");
            int numDoc = Integer.parseInt(in.readLine());

            Document doc = null;
            for (Document d : documents) {
                if (d.numero() == numDoc) {
                    doc = d;
                }
            }
            if (doc == null) {
                throw new ClassNotFoundException();
            }

            doc.reservationPour(ab);
			timer.schedule(new RetourTask(doc), 2 * 60 * 60 * 1000);

        } catch (IOException e) {
            if(out != null){ out.println("Erreur d'entrée / sortie"); }
        } catch (RuntimeException e) {
			if(out != null){ out.println("Numéro d'abonné incorrect"); }
        } catch (ClassNotFoundException e) {
            out.println("Numéro de document introuvable");
        } catch (EmpruntException e) {
            out.println(e.getMessage());
        }
    }

	private class RetourTask extends TimerTask {
		private final Document document;

		RetourTask(Document doc) {
			this.document = doc;
		}

		@Override
		public void run() {
			document.retour();
		}
	}
}
