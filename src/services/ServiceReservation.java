package services;

import java.net.Socket;

import serveur.Service;

public class ServiceReservation extends Service{
	private int numeroAb, numeroDoc;

	public ServiceReservation(Socket socket, int idAb, int idDoc) {
		super(socket);
		this.numeroAb = idAb;
		this.numeroDoc = idDoc;
	}
	
	public ServiceReservation(int idAb, int idDoc) {
		super();
		this.numeroAb = idAb;
		this.numeroDoc = idDoc;
	}
	
	@Override
	public void run() {
		
		
	}

}
