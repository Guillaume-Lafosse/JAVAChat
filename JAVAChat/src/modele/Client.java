package modele;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import application.Application;

public class Client extends Thread{

	final static int port = 1028; //déclaration du port
	private Socket client;//déclaration du socket client
	private String nom;//déclaration du nom


	public Client(Socket client){//constructeur du client
		super();
		this.client = client;
		this.nom = null;
	}


	@Override
	public void run() {//lancement du Thread

		BufferedReader in = null;//entrée clavier
		String msg;//message à envoyer

		try {
			in = new BufferedReader( new InputStreamReader(client.getInputStream()));//instanciation de l'entrée clavier
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		try {

			do {
				msg = in.readLine();//lecture du message à envoyer
				Application.bal.put(msg);//envoi du message à diffuser dans la boite aux lettres
				Thread distri = new Thread(new Distributeur(this));//instanciation du distributeur avec le client 
				distri.start();//démarrage du Thread de diffusion
				distri.join();//attente de la fin du Thread
			}while(!msg.equalsIgnoreCase("bye"));//condition pour quitter le serveur

			Serveur.ListeClients.remove(this);//suppression de ce client de la liste si jamais il écrit "bye"

			for(Thread cli: Serveur.ListeClients){//parcours de liste des clients

				PrintStream out2;
				try {
					out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());//affichage sur les autres terminaux clients la déconnexion du client 
					out2.println(this.getNom() + " vient de se deconnecter ! ");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client.close(); //fermeture du socket du client, il est déconnecté
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNom(String nom) {//attribution du nom au client
		this.nom = nom;
	}


	public String getNom() {//récupération du nom du client
		return nom;
	}


	public Socket getSocket(){//récupération du socket du client
		return this.client;
	}



}
