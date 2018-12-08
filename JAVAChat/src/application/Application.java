package application;

import modele.BAL;
import modele.Distributeur;
import modele.Serveur;

public class Application {
	
	public static BAL bal=new BAL();

		public static void main(String[] args) throws InterruptedException {


			Thread distri = new Thread(new Distributeur(),"Distri");
			Thread server = new Thread(new Serveur());

			server.start();
			distri.start();

		}
}
