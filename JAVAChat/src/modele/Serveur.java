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
	public static List<Thread> ListeClients;


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
				Thread connexion = new Thread(new Connexion(socketCli));
				connexion.start();				

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
