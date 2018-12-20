package modele;

public class BAL {

	private String message; // boite aux lettres. Variable privée 


	public synchronized void put(String put_data) {

		// si un thread a écrit un message on attend. 
		// Lorsque la méthode get aura vidé
		// la variable data et que le thread sera réveillé, 
		// l'exécution pourra reprendre
		while (message != null) {

			try {
				wait();

			} catch (InterruptedException e) {}
		}

		message = put_data+"/";

		// Une fois la donnée déposée on r�veille les threads en attente
		notifyAll();
		return;

	}//fun de la m�thode put    

	public synchronized String get() {
		// si aucun message n'a �t� envoy�, 
		// l'ex�cution du thread est suspendue, d�s qu'un 
		// thread producteur aura �crit un message et 
		// que le thread sera r�veill� l'ex�cution pourra reprendre
		while (message == null) {

			try {

				wait();

			} catch (InterruptedException e) { }
		}

		String get_data = message ;
		message = null ;

		// Une fois la boite aux lettres vide on r�veille les threads en attente
		notifyAll();
		return get_data;

	}// fin de la m�thode get

}	