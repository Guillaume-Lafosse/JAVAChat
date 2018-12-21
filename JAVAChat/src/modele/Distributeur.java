package modele;

import java.io.IOException;
import java.io.PrintStream;

import application.Application;

public class Distributeur implements Runnable{

	private Client client; //d�claration du client associ� au distributeur

	public Distributeur(Client client) {

		this.client = client; //instanciation du client

	}


	@Override
	public void run() { //lancement du distributeur


		String tmp; // d�claration du message � distribuer

		tmp = Application.bal.get(); //r�cup�ration du message courant dans la boite aux lettres
		tmp = tmp.substring(0 , tmp.length()-1); //enlever le caract�re d'entr�e de retour � la ligne ('/')

		for(Thread cli: Serveur.ListeClients){ //parcours de la liste des clients
			if(cli != client) { //si le client n'est pas celui qui envoie le message
				PrintStream out2;
				try {
					out2 = new PrintStream(((Client) cli).getSocket().getOutputStream()); //on affiche sur chaque terminal client le message envoy�
					out2.println(client.getNom()+ " : " + tmp);//avec le nom de l'�metteur

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
