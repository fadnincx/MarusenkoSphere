package marusenkoSphereGUI;
/**
 * Queue - Manager : Ist für das Abarbeiten des Queue, welches die änderungsbefehle  enthält, zuständig
 * @author marcel
 *
 */
public class QueueManager {

	/**
	 * Statische Funktion um einen Schritt im Queue weiter zu gehen
	 * @param m  : Manager, an welcher die änderungsbefehle geschickt werden müssen
	 */
	public static void Queue(Manager m){
		
		//Setzte die Variable, dass Queue weiter gehen soll auf false
		Manager.doQueue = false;
		
		//Wenn die Variable bis zum Ende l�sen wahr ist
		if(Manager.doQuetoEnd){
			
			//Wenn am Ende Angekommen, dann setze die Variable auf false
			if(m.k.getStep()>=m.k.getMaxStep()){
				Manager.doQuetoEnd = false;
			}
			
			//Gehe einen Schritt weiter
			m.oneStep();
		}else
			//Wenn das Queue noch nicht leer ist, dann f�hre die Aktion aus
		if(m.toDoQueue.size()>0){
			
			//Bekomme den String aus dem Queue und l�sche ihn zugleich
			String s = m.toDoQueue.poll();
			
			//Gehe nach weiter oder zur�ck
			switch(s){
			case "x":
				//Weiter gehen
				m.oneStep();
				break;
			case "y":
				//Zur�ck gehen
				m.backStep();
				break;
			}
		}
		
	}
}