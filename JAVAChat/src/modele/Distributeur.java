package modele;

import java.io.IOException;
import java.io.PrintStream;

import application.Application;

public class Distributeur implements Runnable{
	

	@Override
	public void run() {
		
		String tmp;
	

		tmp = Application.bal.get();
		
		Thread client = Serveur.ListeClients.remove(Serveur.ListeClients.size()-1);
		
		for(Thread cli: Serveur.ListeClients){
			PrintStream out2;
			try {
				out2 = new PrintStream(((Client) cli).getSocket().getOutputStream());
				out2.println(tmp);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Serveur.ListeClients.add(client);
		
		}

}
