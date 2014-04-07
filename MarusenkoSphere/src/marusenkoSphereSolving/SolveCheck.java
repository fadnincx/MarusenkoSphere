package marusenkoSphereSolving;

import marusenkoSphereKugel.Kugel;


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
	 * Pr�ft, ob Kugel fertig gel�st wurde
	 * @return : wenn gel�st, dann true sonst false
	 */
	protected static boolean isKugelSolved(Kugel k){
		/**
		 * Pr�fe jeden Pol
		 */
		for(int i = 0; i<6; i++){
			if(!isPolSolved(i,k)){
				return false;
			}
		}
		
		return true;
	}//#END isKugelSolved
	
	/**
	 * �berpr�ft, ob ein Pole vollst�ndig gel�st ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zur�ck, wenn Pole vollst�ngig gel�sst ist
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
	}//isPositionSolved
	
}
