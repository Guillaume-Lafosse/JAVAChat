package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connexion implements Runnable{
	
	private Client client;
	
	public Connexion(Client client) {
		
		this.client = client;
		
	}

	@Override
	public void run() {

		
		PrintStream out;
		try {
			out = new PrintStream(client.getSocket().getOutputStream());
			
			out.println("Connexion au serveur:");
			out.println("Quel est votre nom?");
			
			/*Thread connexion = new Thread(new Connexion(socketCli,));

			server.start();*/
			
			BufferedReader in = new BufferedReader( new InputStreamReader(client.getSocket().getInputStream()));

			String nom;
			nom = in.readLine();
			client.setNom(nom);
			
						
			
			for(Thread cli: Serveur.ListeClients){
				PrintStream out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());
				out2.println(((Client) client).getNom() + " vient de se connecter!");
				
			}

			Serveur.ListeClients.add(client);
			
			out.println("Bienvenue sur le serveur "+ ((Client) client).getNom() + " !" );
			
			client.start();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
