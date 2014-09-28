package marusenkoSphereKugel;

/**
 * Klasse welche Methoden enthält, welche beim Lösen der Kugel prüen, ob ein bestimmter Zustand bereits eingetroffen ist.
 *
 */

public class SolveCheck {

	/**
	 * überprüft, ob aller Werte in Array true ist.
	 * @return gibt true zurück, wenn alles true, sonst false
	 */
	public static boolean ArrayIsFullyOk(boolean[] ok){
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
	 * Prüft, ob Kugel fertig gelöt wurde
	 * @return : wenn gelöst, dann true sonst false
	 */
	public static boolean isKugelSolved(Kugel k){
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
	 * überprüft, ob ein Pole vollständig gelöst ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zurück, wenn Pole vollständig gelösst ist
	 */
	public static boolean isPolSolved(int polNr, Kugel k){
		int anzKorr = 0;
		for(int i = 0; i<4; i++){
			if(isPositionSolved((polNr*4)+i,k)){
				anzKorr++;
			}
		}
		return anzKorr==4 ? true : false;
	}
	
	
	/**
	 * überprüft, ob eine Position/Dreieck gelöst ist
	 * @param p : welches Dreieck geprüft werden soll
	 * @return : gibt true zurück, wenn gelöst
	 */
	public static boolean isPositionSolved(int p, Kugel k){
		/**
		 * Prüfe, ob tri mit con stimmt	
		 * wenn ja, Return true	
		 */
		if(k.tri[p]==k.con[SphereUtils.findCorrectConIndexFromTri(p)]){
			return true;
		}
		/**
		 * Wenn nicht abgebrochen durch return true, dann return false
		 */
		return false;
	}
	public static boolean otherPositionsOnPoleSolved(int p1, int p2, Kugel k){
		int pol = p1/4;
		int pos1 = p1%4;
		int pos2 = p2%4;
		
		int[] pos = new int[2];
		
		for(int i = 0, index = 0; i<4; i++){
			if(pos1!=i&&pos2!=i){
				pos[index] = i;
				index++;
			}
		}
		return (isPositionSolved((pol*4+pos[0]), k)&&isPositionSolved((pol*4+pos[1]), k));
		
	}
	
}
