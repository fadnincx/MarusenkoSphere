package marusenkoSphereGUI;

import marusenkoSphereKugel.Kugel;

/**
 * Klasse welche Methoden enthält um die den aktuelle Zustand der Kugel gemäss des Lösungsweg zu ändern
 * 
 */
public class SetToState {
	
	/**
	 * Gibt die Kugel zum Endstatus zurück
	 * @param k
	 * @return
	 */
	public static Kugel getKugelFromArrayList(Kugel k){
		return getKugelFromArrayList(k, k.SolvingList.size()-1);
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
		}else if(step>=k.SolvingList.size()){
			step = k.SolvingList.size()-1;
		}
		if(Manager.doQuetoEnd&&step==k.SolvingList.size()-1){
			Manager.doQuetoEnd = false;
		}
		k.FillKugelFromStringWithoutSolvingList(k.SolvingList.get(step));
		return k;
	}
	
}
