package marusenkoSphereKonsole;

import marusenkoSphereKugel.Kugel;

/**
 * Wird von der Main-Datei aufgerufen, wenn mit der Konsole gestartet wird...
 * 
 * Sehr primitive Methode um möglichst viele Kugeln zu lösen
 *
 */
public class Konsole {

	public Kugel k;
	
	/**
	 * Konstruktor der Konsole und einzige Funktion
	 * @param k
	 */
	public Konsole(Kugel k){
		//Setze Aktuelle Kugel
		this.k = k;
	
		//Gehe maximale Anzahl schlaufen durch ((2^63)-1) 
		for(long i = 0;i<9223372036854775807L;i++){
			//fülle Kugel neu, wobei sie sofort auch gelöst wird
			k.FillKugelRandom();
			//Gib aus, die wie vielte Kugel gelöst wurde
			System.out.println(i+". Kugel solved in "+k.getStep()+" steps");
		}
		
	}
}

