package modele;

public class BAL {

	private String message; // boite aux lettres. Variable privÃ©e 


	public synchronized void put(String put_data) {

		// si un thread a Ã©crit un message on attend. 
		// Lorsque la mÃ©thode get aura vidÃ©
		// la variable data et que le thread sera rÃ©veillÃ©, 
		// l'exÃ©cution pourra reprendre
		while (message != null) {

			try {
				wait();

			} catch (InterruptedException e) {}
		}

		message = put_data+"/";

		// Une fois la donnÃ©e dÃ©posÃ©e on réveille les threads en attente
		notifyAll();
		return;

	}//fun de la méthode put    

	public synchronized String get() {
		// si aucun message n'a été envoyé, 
		// l'exécution du thread est suspendue, dès qu'un 
		// thread producteur aura écrit un message et 
		// que le thread sera réveillé l'exécution pourra reprendre
		while (message == null) {

			try {

				wait();

			} catch (InterruptedException e) { }
		}

		String get_data = message ;
		message = null ;

		// Une fois la boite aux lettres vide on réveille les threads en attente
		notifyAll();
		return get_data;

	}// fin de la méthode get

}	