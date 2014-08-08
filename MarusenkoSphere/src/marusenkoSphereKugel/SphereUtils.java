package marusenkoSphereKugel;

/**
 * SphereUtils
 * Enthält viele nützliche Methoden mit allgemeinen Infos zur Kugel
 */
public class SphereUtils {
	
	/**
	 * Drehung aus dem String bekommen und als int-Array zurückgeben
	 */
	public static int[] getDrehungFromStringAsIntArray(String s){
		s = getDrehungFromStringAsString(s);
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
	 */
	public static String getDrehungFromStringAsString(String s){
		s = s.trim();
		String[] sp = s.split("n");
		return sp[2];
	}

	/**
	 * Gibt den reinen Kugelcode zurück, ohne Step und Drehung
	 */
	public static String getPureSphereCode(String s){
		s = s.trim();
		String[] sp = s.split("n");
		return sp[0];
	}
	
	/**
	 * Bekomme die 4 Werte eines Pols aus dem String als int-Array
	 */
	public static int[] extractPolFromStringReturnAsIntArray(String s, int Pol){
		int[] i = new int[4];
		i[0] = Integer.parseInt(s.substring(((Pol*4+0)+8),((Pol*4+0)+9)));
		i[1] = Integer.parseInt(s.substring(((Pol*4+1)+8),((Pol*4+1)+9)));
		i[2] = Integer.parseInt(s.substring(((Pol*4+2)+8),((Pol*4+2)+9)));
		i[3] = Integer.parseInt(s.substring(((Pol*4+3)+8),((Pol*4+3)+9)));
		return i;
	}
	
	
	/**
	 * Gibt den passenden Con Index zum gegebenen Tri Index zurück
	 */
	public static int findCorrectConIndexFromTri(int tri){
		switch(tri){
			case 11: case 18: case 15: return 0;
			case 12: case 19: case 3:  return 1;
			case 0:  case 16: case 4:  return 2;
			case 7:  case 17: case 8:  return 3;
			case 10: case 22: case 14: return 4;
			case 13: case 23: case 2:  return 5;
			case 1:  case 20: case 5:  return 6;
			case 6:  case 21: case 9:  return 7;
			default: return -1;
		}
	}
	
	/**
	 * Bekomme die 3 angrenzenden Positionen eines Con als Int-Array
	 */
	public static int[] conToPos(int con){
		switch(con){
			case 0: return new int[]{11,15,18};
			case 1: return new int[]{3,12,19};
			case 2: return new int[]{0,4,16};
			case 3: return new int[]{7,8,17};
			case 4: return new int[]{10,14,22};
			case 5: return new int[]{2,13,23};
			case 6: return new int[]{1,5,20};
			case 7: return new int[]{6,9,21};
			default: return new int[]{-1,-1,-1};
		}
	}
}
