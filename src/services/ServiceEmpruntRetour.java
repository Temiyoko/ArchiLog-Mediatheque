package services;

import java.net.Socket;

import serveur.Service;

public class ServiceEmpruntRetour extends Service {
	private int numeroAb, numeroDoc;

	public ServiceEmpruntRetour(Socket socket, int idAb, int idDoc) {
		super(socket);
		this.numeroAb = idAb;
		this.numeroDoc = idDoc;
	}
	
	public ServiceEmpruntRetour(int idAb, int idDoc) {
		super();
		this.numeroAb = idAb;
		this.numeroDoc = idDoc;
	}

	@Override
	public void run() {

	}

}
