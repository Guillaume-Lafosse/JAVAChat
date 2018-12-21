package modele;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Serveur implements Runnable{

	private final static int port = 1028; //port du telnet
	public static List<Thread> ListeClients; //liste de clients connect�s


	public static int getPort() {//r�cup�ration du port 
		return port;
	}

	@Override
	public void run() {//lancement du Thread

		ListeClients = new ArrayList<Thread>();//instanciation de la liste

		Socket socketCli = null;//initialisation des sockets
		ServerSocket sockServ = null;

		try {
			sockServ = new ServerSocket(port);//cr�ation du socket du serveur
			System.out.println("La commande � saisir est telnet localhost " +sockServ.getLocalPort());
			while(true){
				socketCli = sockServ.accept();//initialisation du socket � chaque fois qu'un client se connecte
				Thread connexion = new Thread(new Connexion(socketCli));//instanciation d'une connexion gr�ce au socket r�cup�r�
				connexion.start(); //lancement du Thread connexion 		

			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				socketCli.close(); //fermeture des sockets
				sockServ.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}


	}
}
