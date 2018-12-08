package modele;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{//nc -v localhost 1025 -u

	final static int port = 1028; 
	final static int taille = 1024; 
	static byte buffer[] = new byte[taille];
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
		PrintStream out = null;


		try {
			in = new BufferedReader( new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());

			String nom = in.readLine();
			this.nom = nom;
			
			notifyAll();


			for(int i =0; i<10;i++){

				out.println(i);

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
