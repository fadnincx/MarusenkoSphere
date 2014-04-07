package marusenkoSphereSolving;

import marusenkoSphereKugel.Kugel;


public class SolveCheck {

	/**
	 * Überprüft, ob aller Werte in Array true ist.
	 * @return gibt true zurück, wenn alles true, sonst false
	 */
	protected static boolean ArrayIsFullyOk(boolean[] ok){
		//Gehe alle Dreiecke durch
		for(int i = 0; i<ok.length; i++){
			//Wenn eines false, dann return false
			if(!ok[i]){
				return false;
			}
		}
		//Wenn kein false, dann true
		return true;
	}
	
	
	/**
	 * Prüft, ob Kugel fertig gelöst wurde
	 * @return : wenn gelöst, dann true sonst false
	 */
	protected static boolean isKugelSolved(Kugel k){
		/**
		 * Prüfe jeden Pol
		 */
		for(int i = 0; i<6; i++){
			if(!isPolSolved(i,k)){
				return false;
			}
		}
		
		return true;
	}//#END isKugelSolved
	
	/**
	 * Überprüft, ob ein Pole vollständig gelöst ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zurück, wenn Pole vollstängig gelösst ist
	 */
	protected static boolean isPolSolved(int polNr, Kugel k){
		int anzKorr = 0;
		for(int i = 0; i<4; i++){
			if(isPositionSolved((polNr*4)+i,k)){
				anzKorr++;
			}
		}
		if(anzKorr==4){
			return true;
		}else{
			return false;
		}
	}//#END isPolSolved
	
	/**
	 * Überprüft, ob eine Position/Dreieck gelöst ist
	 * @param p : welches Dreieck geprüft werden soll
	 * @return : gibt true zurück, wenn gelöst
	 */
	protected static boolean isPositionSolved(int p, Kugel k){
		/**
		 * Prüfe, ob tri mit con stimmt	
		 * wenn ja, Return true	
		 */
		if(k.tri[p]==k.con[k.findCons(p)]){
			return true;
		}
		/**
		 * Wenn nicht abgebrochen durch return true, dann return false
		 */
		return false;
	}//isPositionSolved
	
}
