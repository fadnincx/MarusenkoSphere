package marusenkoSphereKugel;

public class SphereUtils {
	/**
	 * Drehung aus dem String bekommen und als int-Array zurückgeben
	 * @param s
	 * @return
	 */
	public static int[] SplitDrehungFromSphere(String s){
		s = SplitDrehungFromSphereAsS(s);
		int[] i = new int[3];
		if(s.length()==3){
			i[0] = Integer.parseInt(s.substring(0,1));
			i[1] = Integer.parseInt(s.substring(1,2));
			i[2] = Integer.parseInt(s.substring(2,3));
		}
		return i;
	}
	/**
	 * Drehung aus dem String bekommen und als String zurückgeben
	 * @param s
	 * @return
	 */
	public static String SplitDrehungFromSphereAsS(String s){
		s = s.trim();
		String[] sp = s.split("n");
		return sp[2];
	}

	/**
	 * Bekomme Kugel ohne Drehung und Step
	 * @param s
	 * @return
	 */
	
	public static String SphereWithoutDrehungAndStep(String s){
		s = s.trim();
		String[] sp = s.split("n");
		return sp[0];
	}
	/**
	 * Bekomme die 4 Werte eines Pols aus dem String als int-Array
	 * @param s
	 * @param Pol
	 * @return
	 */
	public static int[] GetPolFromSphereString(String s, int Pol){
		int[] i = new int[4];
		i[0] = Integer.parseInt(s.substring(((Pol*4+0)+8),((Pol*4+0)+9)));
		i[1] = Integer.parseInt(s.substring(((Pol*4+1)+8),((Pol*4+1)+9)));
		i[2] = Integer.parseInt(s.substring(((Pol*4+2)+8),((Pol*4+2)+9)));
		i[3] = Integer.parseInt(s.substring(((Pol*4+3)+8),((Pol*4+3)+9)));
		return i;
	}
}
