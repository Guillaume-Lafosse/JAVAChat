package modele;

import java.io.IOException;
import java.io.PrintStream;

import application.Application;

public class Distributeur implements Runnable{
	
	private Client client;
	
	public Distributeur(Client client) {
		
		this.client = client;

	}
	

	@Override
	public void run() {
		
		
		String tmp;
	
		tmp = Application.bal.get();
		
		for(Thread cli: Serveur.ListeClients){
			if(cli != client) {
			PrintStream out2;
			try {
				out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());
				out2.println(client.getNom()+ " : " + tmp);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		}
		
	}

}
