package marusenkoSphereKugel;

import java.util.Arrays;

/**
 * Klasse welche Methoden enthält, welche beim Lösen der Kugel prüen, ob ein bestimmter Zustand bereits eingetroffen ist.
 *
 */

public class SolveCheck {

	/**
	 * überprüft, ob aller Werte in Array true ist.
	 * @return gibt true zurück, wenn alles true, sonst false
	 */
	public static boolean arrayIsFullyOk(boolean[] ok){
		
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
		
		//Gehe jeden Pol durch
		for(int i = 0; i<6; i++){
			
			//Wenn Pol nicht gelöst
			if(!isPolSolved(i,k)){
				
				//Gibt false zurück
				return false;
				
			}
			
		}
		
		//Ansonten true
		return true;
	}

	
	/**
	 * überprüft, ob ein Pole vollständig gelöst ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zurück, wenn Pole vollständig gelösst ist
	 */
	public static boolean isPolSolved(int polNr, Kugel k){
		
		//Anzahl korrekter Felder auf Pol = 0
		int anzKorr = 0;
		
		//Gehe jede Position durch
		for(int i = 0; i<4; i++){
			
			//Wenn Position gelöst ist
			if(isPositionSolved((polNr*4)+i,k)){
				
				//Zähle hoch
				anzKorr++;
				
			}
			
		}
		
		//Gib das Resultat zurück
		return anzKorr==4;
		
	}
	
	
	/**
	 * überprüft, ob eine Position/Dreieck gelöst ist
	 * @param p : welches Dreieck geprüft werden soll
	 * @return : gibt true zurück, wenn gelöst
	 */
	public static boolean isPositionSolved(int p, Kugel k){
		
		//Stimmt der Tri mit den Con überein?
		if(k.tri[p]==k.con[SphereUtils.findCorrectConIndexFromTri(p)]){
			
			//Gib true zurück
			return true;
			
		}
		
		//Sonst false
		return false;
		
	}
	
	/**
	 * Prüft, ob die anderen Positionen auf dem Pol bereits gelöst sind, oder nicht
	 * @param p1 : Position 1
	 * @param p2 : Position 2
	 * @param k : Kugel k
	 * @return : true für gelöst, false, für nicht gelöst
	 */
	public static boolean otherPositionsOnPoleSolved(int p1, int p2, Kugel k){
		
		//Speichere den Pol
		int pol = p1/4;
		
		//Speichere die Position von p1
		int pos1 = p1%4;
		
		//Speichere die Position von p2
		int pos2 = p2%4;
		
		//Erstelle Array für die weiteren Positionen
		int[] pos = new int[2];
		
		//Gehe alle 4 Positionen durch
		for(int i = 0, index = 0; i<4; i++){
			
			//Wenn nicht Position 1 und nicht 2
			if(pos1!=i&&pos2!=i){
				
				//Speichere den Index der Position
				pos[index] = i;
				
				//Die Index Variable hochzählen
				index++;
				
			}
			
		}
		
		//Gib das Resultat zurück
		return (isPositionSolved((pol*4+pos[0]), k)&&isPositionSolved((pol*4+pos[1]), k));
		
	}
	/**
	 * Gibt true zurück, wenn ein Pol alle benötigten Farben beinhaltet
	 */
	public static boolean areAllColorsOnPol(Kugel k, int polNr){
		
		//Array mit den Werten für tri
		int[] tris = new int[4];
		
		//Array mit den Werten von con
		int[] cons = new int[4];
		
		//Fülle Arrays mit Daten aus Kugel
		for(int i = 0; i<4; i++){
			tris[i]=k.tri[polNr*4+i];
			cons[i]=k.con[SphereUtils.findCorrectConIndexFromTri(polNr*4+i)];
		}

		//Sortiere die Arrays
		Arrays.sort(tris);
		Arrays.sort(cons);
		
		//Wenn die Arrays gleich sind, dann gib true zurück, sonst false
		return Arrays.equals(tris, cons);
		
	}
	
}
