package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connexion implements Runnable{

	private Client client;//d�claration du client associ� � la connexion

	public Connexion(Socket socketCli ) {

		this.client = new Client(socketCli); //instanciation d'un nouveau client 
	}

	@Override
	public void run() {//lancement du Thread Connexion


		PrintStream out;
		try {
			out = new PrintStream(client.getSocket().getOutputStream()); //r�cup�ration de la sortie clavier

			out.println("Connexion au serveur:");
			out.println("Quel est votre nom?");

			BufferedReader in = new BufferedReader( new InputStreamReader(client.getSocket().getInputStream())); //r�cup�ration de l'entr�e clavier

			String nom;
			nom = in.readLine();//lecture du nom 
			client.setNom(nom);//attribution du nom au client pour l'identifier



			for(Thread cli: Serveur.ListeClients){//parcours de la liste des clients
				PrintStream out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());//affichage du message de connexion sur les autres terminaux
				out2.println(((Client) client).getNom() + " vient de se connecter!");

			}

			Serveur.ListeClients.add(client);//ajout du client � la liste des clients

			out.println("Bienvenue sur le serveur "+ ((Client) client).getNom() + " !" );//affichage du message de bienvenue du serveur sur le client

			client.start();//d�marrage du Thread client

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
