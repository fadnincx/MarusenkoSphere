package marusenkoSphereGUI;

import java.util.ArrayList;

import marusenkoSphereKugel.Kugel;

/**
 * Klasse welche Methoden enth�lt um die den aktuelle Zustand der Kugel gem�ss des L�sungsweg zu �ndern
 * 
 */
public class SetToState {
	
	/**
	 * Gibt die Kugel zum Endstatus zur�ck
	 * @param k
	 * @return
	 */
	public static Kugel getKugelFromArrayList(Kugel k){
		return getKugelFromArrayList(k, getSolvingLength(k.SolvingList)-1);
	}
	
	/**
	 * Gibt die Kugel zur Gegebenen Step zur�ck
	 * @param k
	 * @param step
	 * @return
	 */
	public static Kugel getKugelFromArrayList(Kugel k, int step){
		if(step<=0){
			step = 0;
		}else if(step>=getSolvingLength(k.SolvingList)){
			step = getSolvingLength(k.SolvingList)-1;
		}
		k.FillKugelFromStringWithoutSolvingList(k.SolvingList.get(step));
		return k;
	}
	
	/**
	 * Gibt zur�ck, wie lange der L�sungsweg der Kugel ist
	 * @param solvingList
	 * @return
	 */
	public static int getSolvingLength(ArrayList<String> solvingList){
		return solvingList.size();
	}
	
}
