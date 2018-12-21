package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connexion implements Runnable{

	private Client client;//déclaration du client associé à la connexion

	public Connexion(Socket socketCli ) {

		this.client = new Client(socketCli); //instanciation d'un nouveau client 
	}

	@Override
	public void run() {//lancement du Thread Connexion


		PrintStream out;
		try {
			out = new PrintStream(client.getSocket().getOutputStream()); //récupération de la sortie clavier

			out.println("Connexion au serveur:");
			out.println("Quel est votre nom?");

			BufferedReader in = new BufferedReader( new InputStreamReader(client.getSocket().getInputStream())); //récupération de l'entrée clavier

			String nom;
			nom = in.readLine();//lecture du nom 
			client.setNom(nom);//attribution du nom au client pour l'identifier



			for(Thread cli: Serveur.ListeClients){//parcours de la liste des clients
				PrintStream out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());//affichage du message de connexion sur les autres terminaux
				out2.println(((Client) client).getNom() + " vient de se connecter!");

			}

			Serveur.ListeClients.add(client);//ajout du client à la liste des clients

			out.println("Bienvenue sur le serveur "+ ((Client) client).getNom() + " !" );//affichage du message de bienvenue du serveur sur le client

			client.start();//démarrage du Thread client

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
