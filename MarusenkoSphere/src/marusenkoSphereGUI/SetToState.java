package marusenkoSphereGUI;

import java.util.ArrayList;

import marusenkoSphereKugel.Kugel;

public class SetToState {
	
	/**
	 * Gibt die Kugel zum Endstatus zurück
	 * @param k
	 * @return
	 */
	public static Kugel getKugelFromArrayList(Kugel k){
		return getKugelFromArrayList(k, getSolvingLength(k.SolvingList)-1);
	}
	/**
	 * Gibt die Kugel zur Gegebenen Step zurück
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
		k.steps = step;
		return k;
	}
	/**
	 * Gibt zurück, wie lange der Lösungsweg der Kugel ist
	 * @param solvingList
	 * @return
	 */
	public static int getSolvingLength(ArrayList<String> solvingList){
		return solvingList.size();
	}
	
}
