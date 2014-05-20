package marusenkoSphereKugel;

/**
 * Klasse welche Methoden enth�lt, welche beim L�sen der Kugel pr�fen, ob ein bestimmter Zustand bereits eingetroffen ist.
 *
 */

public class SolveCheck {

	/**
	 * �berpr�ft, ob aller Werte in Array true ist.
	 * @return gibt true zur�ck, wenn alles true, sonst false
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
	 * Pr�t, ob Kugel fertig gel�st wurde
	 * @return : wenn gel�st, dann true sonst false
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
	}

	
	/**
	 * �berpr�ft, ob ein Pole vollst�ndig gel�st ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zur�ck, wenn Pole vollst�ndig gel�sst ist
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
	}
	
	
	/**
	 * �berpr�ft, ob eine Position/Dreieck gel�st ist
	 * @param p : welches Dreieck gepr�ft werden soll
	 * @return : gibt true zur�ck, wenn gel�st
	 */
	protected static boolean isPositionSolved(int p, Kugel k){
		/**
		 * Pr�fe, ob tri mit con stimmt	
		 * wenn ja, Return true	
		 */
		if(k.tri[p]==k.con[k.findCons(p)]){
			return true;
		}
		/**
		 * Wenn nicht abgebrochen durch return true, dann return false
		 */
		return false;
	}
	
}
