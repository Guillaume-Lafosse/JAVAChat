package modele;

import java.io.IOException;
import java.io.PrintStream;

import application.Application;

public class Distributeur implements Runnable{

	private Client client; //déclaration du client associé au distributeur

	public Distributeur(Client client) {

		this.client = client; //instanciation du client

	}


	@Override
	public void run() { //lancement du distributeur


		String tmp; // déclaration du message à distribuer

		tmp = Application.bal.get(); //récupération du message courant dans la boite aux lettres
		tmp = tmp.substring(0 , tmp.length()-1); //enlever le caractère d'entrée de retour à la ligne ('/')

		for(Thread cli: Serveur.ListeClients){ //parcours de la liste des clients
			if(cli != client) { //si le client n'est pas celui qui envoie le message
				PrintStream out2;
				try {
					out2 = new PrintStream(((Client) cli).getSocket().getOutputStream()); //on affiche sur chaque terminal client le message envoyé
					out2.println(client.getNom()+ " : " + tmp);//avec le nom de l'émetteur

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
