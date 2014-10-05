package marusenkoSphereGUI;
/**
 * Queue - Manager : Ist für das Abarbeiten des Queue, welches die Änderungsbefehle  enthält, zuständig
 * @author marcel
 *
 */
public class QueueManager {

	/**
	 * Statische Funktion um einen Schritt im Queue weiter zu gehen
	 * @param m  : Manager, an welcher die änderungsbefehle geschickt werden müssen
	 */
	protected static void Queue(Manager m){
		
		//Setzte die Variable, dass Queue weiter gehen soll auf false
		Manager.setAnimationFinished(false);
		
		//Wenn die Variable bis zum Ende lösen wahr ist
		if(Manager.getRunAnimationToEnd()){

			//Gehe einen Schritt weiter
			m.addOneStep();
			
		}else
			
		//Wenn das Queue noch nicht leer ist, dann führe die Aktion aus
		if(m.isElementInSolvingQueue()){
			
			//Bekomme den String aus dem Queue und lösche ihn zugleich
			char s = m.pollElementFromSolvingQueue();
			
			//Gehe nach weiter oder zurück
			switch(s){
				case 'x':
					
					//Einen Schritt weiter gehen
					m.addOneStep();
					
					break;
					
				case 'y':
					
					//Einen Schritt zurück gehen
					m.subOneStep();
					
					break;
					
			}
			
		}
		
	}
	
}
