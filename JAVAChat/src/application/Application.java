package application;

import modele.Serveur;

public class Application {

		public static void main(String[] args) throws InterruptedException {


			Thread server = new Thread(new Serveur());

			server.start();
			

		}
}
