package modele;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;



public class Serveur implements Runnable{//nc -v localhost 1025 -u

	private final static int port = 1028; 
	final static int taille = 1024; 
	static byte buffer[] = new byte[taille];
	private List<Thread> ListeClients;


	public static int getPort() {
		return port;
	}

	@Override
	public void run() {
		
		ListeClients = new ArrayList<Thread>();

		Socket socketCli = null;
		ServerSocket sockServ = null;

		try {
			sockServ = new ServerSocket(port);
			System.out.println(sockServ.getLocalPort());
			while(true){
				socketCli = sockServ.accept();
				Thread client = new Client(socketCli);

				
				System.out.println(socketCli.getInetAddress());
				PrintStream out = new PrintStream(socketCli.getOutputStream());
				
				out.println("Connexion au serveur:");
				out.println("Quel est votre nom?");
				
			
				BufferedReader in = new BufferedReader( new InputStreamReader(socketCli.getInputStream()));

				String nom = in.readLine();
				
				((Client) client).setNom(nom);
				
				for(Thread cli: ListeClients){
					PrintStream out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());
					out2.println(((Client) client).getNom() + " vient de se connecter!");
					
				}
				
				ListeClients.add(client);
				
				out.println("Bienvenue sur le serveur "+ ((Client) client).getNom() + " !" );

				


			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				socketCli.close();
				sockServ.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}


	}
}
