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

public class Client extends Thread{//nc -v localhost 1028 -u

	final static int port = 1028; 
	final static int taille = 1024; 
	private Socket client;
	private String nom;


	public Client(Socket client){
		super();
		this.client = client;
		this.nom = null;
	}


	@Override
	public void run() {

		BufferedReader in = null;
		String msg;

		try {
			in = new BufferedReader( new InputStreamReader(client.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {

			do {
				msg = in.readLine();
				Application.bal.put(msg);
				Thread distri = new Thread(new Distributeur(this));
				distri.start();
				distri.join();
			}while(!msg.equalsIgnoreCase("bye"));

			Serveur.ListeClients.remove(this);

			for(Thread cli: Serveur.ListeClients){

				PrintStream out2;
				try {
					out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());
					out2.println(this.getNom() + " vient de se deconnecter ! ");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getNom() {
		return nom;
	}


	public Socket getSocket(){
		return this.client;
	}



}
