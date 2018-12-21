package application;

import modele.BAL;
import modele.Distributeur;
import modele.Serveur;

public class Application {

	public static BAL bal=new BAL();//Boite au lettre permmettant l'échange de messages
	

	public static void main(String[] args) throws InterruptedException {//méthode de lancement
		

		Thread server = new Thread(new Serveur());

		server.start();//démarrage de l'application

	}
}
