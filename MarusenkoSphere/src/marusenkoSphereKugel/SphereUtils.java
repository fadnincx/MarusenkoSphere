package marusenkoSphereKugel;

import java.util.Arrays;
import java.util.LinkedList;

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
	/**
	 * Gibt die zusätzlichen Informationen zur Drehung zurück
	 */
	public static int strategieStandartGetTurnOpt(int pol1Pos1, int pol1Pos2, int pol2){
		
		//Variable mit dem Pol auf welchem die Positionen liegen
		int pol1 = pol1Pos1/4;
		
		//LinkedList für die beiden Positionen
		LinkedList<Integer> ali = new LinkedList<Integer>();
		
		//Füge Position 1 der LinkedList hinzu
		ali.add(pol1Pos1);
		
		//Füge Position 2 der LinkedList hinzu
		ali.add(pol1Pos2);
		
		//Switche gemäss dem Pol auf welchem die Positionen liegen
		switch(pol1){
		
			//Wenn Pol = 0
			case 0:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 1, dann prüfe ob die Positionen 0 und 3 sind. Gibt je nachdem andere Werte zurück
					case 1: return (ali.contains(0)&&ali.contains(3)) ? 1:1;
					
					//Wenn Pol = 3, dann prüfe ob die Positionen 0 und 3 sind. Gibt je nachdem andere Werte zurück
					case 3: return (ali.contains(0)&&ali.contains(3)) ? 2:2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 0 und 1 sind. Gibt je nachdem andere Werte zurück
					case 4: return (ali.contains(0)&&ali.contains(1)) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 0 und 1 sind. Gibt je nachdem andere Werte zurück
					case 5: return (ali.contains(0)&&ali.contains(1)) ? 1:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 1
			case 1:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 4 und 7 sind. Gibt je nachdem andere Werte zurück
					case 0: return (ali.contains(4)&&ali.contains(7)) ? 1:1;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 4 und 7 sind. Gibt je nachdem andere Werte zurück
					case 2: return (ali.contains(4)&&ali.contains(7)) ? 2:2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 4 und 5 sind. Gibt je nachdem andere Werte zurück
					case 4: return (ali.contains(4)&&ali.contains(5)) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 4 und 5 sind. Gibt je nachdem andere Werte zurück
					case 5: return (ali.contains(4)&&ali.contains(5)) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 2
			case 2:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 1, dann prüfe ob die Positionen 8 und 11 sind. Gibt je nachdem andere Werte zurück
					case 1: return (ali.contains(8)&&ali.contains(11)) ? 1:1;
					
					//Wenn Pol = 3, dann prüfe ob die Positionen 8 und 11 sind. Gibt je nachdem andere Werte zurück
					case 3: return (ali.contains(8)&&ali.contains(11)) ? 2:2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 8 und 9 sind. Gibt je nachdem andere Werte zurück
					case 4: return (ali.contains(8)&&ali.contains(9)) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 8 und 9 sind. Gibt je nachdem andere Werte zurück
					case 5: return (ali.contains(8)&&ali.contains(9)) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 3
			case 3:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 12 und 15 sind. Gibt je nachdem andere Werte zurück
					case 0: return (ali.contains(12)&&ali.contains(15)) ? 1:2;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 12 und 15 sind. Gibt je nachdem andere Werte zurück
					case 2: return (ali.contains(12)&&ali.contains(15)) ? 2:1;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 12 und 13 sind. Gibt je nachdem andere Werte zurück
					case 4: return (ali.contains(12)&&ali.contains(13)) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 12 und 13 sind. Gibt je nachdem andere Werte zurück
					case 5: return (ali.contains(12)&&ali.contains(13)) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 4
			case 4:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 16 und 17 sind. Gibt je nachdem andere Werte zurück
					case 0: return (ali.contains(16)&&ali.contains(17)) ? 1:2;
					
					//Wenn Pol = 1, dann prüfe ob die Positionen 16 und 19 sind. Gibt je nachdem andere Werte zurück
					case 1: return (ali.contains(16)&&ali.contains(19)) ? 1:1;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 16 und 17 sind. Gibt je nachdem andere Werte zurück
					case 2: return (ali.contains(16)&&ali.contains(17)) ? 2:1;
					
					//Wenn Pol = 3, dann prüfe ob die Positionen 16 und 19 sind. Gibt je nachdem andere Werte zurück
					case 3: return (ali.contains(16)&&ali.contains(19)) ? 2:2;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 5
			case 5:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 20 und 21 sind. Gibt je nachdem andere Werte zurück
					case 0: return (ali.contains(20)&&ali.contains(21)) ? 1:2;
					
					//Wenn Pol = 1, dann prüfe ob die Positionen 20 und 23 sind. Gibt je nachdem andere Werte zurück
					case 1: return (ali.contains(20)&&ali.contains(23)) ? 1:1;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 20 und 21 sind. Gibt je nachdem andere Werte zurück
					case 2: return (ali.contains(20)&&ali.contains(21)) ? 2:1;
					
					//Wenn Pol = 3, dann prüfe ob die Positionen 20 und 23 sind. Gibt je nachdem andere Werte zurück
					case 3: return (ali.contains(20)&&ali.contains(23)) ? 2:2;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}

			//Ansonten liefer ein leeres Resultat zurück
			default:
				return 0;
		}
		
	}
	
	/**
	 * Liefert die Position zurück, auf welche die Farben verschoben werdem müssen
	 */
	public static int strategieStandardGetPosOnPol(int pol1Pos1, int pol1Pos2, int pol2){
		int pol1 = pol1Pos1/4;
		LinkedList<Integer> ali = new LinkedList<Integer>();
		ali.add(pol1Pos1);
		ali.add(pol1Pos2);
		switch(pol1){
		case 0:
			switch(pol2){
				case 1: return (ali.contains(0)&&ali.contains(3)) ? 5:4;
				case 3: return (ali.contains(0)&&ali.contains(3)) ? 13:12;
				case 4: return (ali.contains(0)&&ali.contains(1)) ? 19:16; 
				case 5: return (ali.contains(0)&&ali.contains(1)) ? 23:20;
				default: return -1;
			}
		case 1:
			switch(pol2){
				case 0: return (ali.contains(4)&&ali.contains(7)) ? 1:0;
				case 2: return (ali.contains(4)&&ali.contains(7)) ? 9:8;
				case 4: return (ali.contains(4)&&ali.contains(5)) ? 17:16;
				case 5: return (ali.contains(4)&&ali.contains(5)) ? 21:20;
				default: return -1;
			}
		case 2:
			switch(pol2){
				case 1: return (ali.contains(8)&&ali.contains(11)) ? 6:7;
				case 3: return (ali.contains(8)&&ali.contains(11)) ? 14:15;
				case 4: return (ali.contains(8)&&ali.contains(9)) ? 18:17;
				case 5: return (ali.contains(8)&&ali.contains(9)) ? 22:21;
				default: return -1;
			}
		case 3:
			switch(pol2){
				case 0: return (ali.contains(12)&&ali.contains(15)) ? 2:3;
				case 2: return (ali.contains(12)&&ali.contains(15)) ? 10:11;
				case 4: return (ali.contains(12)&&ali.contains(13)) ? 18:19;
				case 5: return (ali.contains(12)&&ali.contains(13)) ? 22:23;
				default: return -1;
			}
		case 4:
			switch(pol2){
				case 0: return (ali.contains(16)&&ali.contains(17)) ? 3:0;
				case 1: return (ali.contains(16)&&ali.contains(19)) ? 7:4;
				case 2: return (ali.contains(16)&&ali.contains(17)) ? 11:8;
				case 3: return (ali.contains(16)&&ali.contains(19)) ? 15:12;
				default: return -1;
			}
		case 5:
			switch(pol2){
			case 0: return (ali.contains(20)&&ali.contains(21)) ? 2:1;
			case 1: return (ali.contains(20)&&ali.contains(23)) ? 6:5;
			case 2: return (ali.contains(20)&&ali.contains(21)) ? 10:9;
			case 3: return (ali.contains(20)&&ali.contains(23)) ? 14:13;
			default: return -1;
			}
		default:
			return -1;
		}
	}
	public static int strategieStandardGetPolForDrehung(int pol1Pos1, int pol1Pos2, int pol2){
		int pol1 = pol1Pos1/4;
		LinkedList<Integer> ali = new LinkedList<Integer>();
		ali.add(pol1Pos1);
		ali.add(pol1Pos2);
		switch(pol1){
		case 0:
			switch(pol2){
				case 1: case 3: return (ali.contains(0)&&ali.contains(3)) ? 5:4;
				case 4: case 5: return (ali.contains(0)&&ali.contains(1)) ? 3:1; 
				default: return -1;
			}
		case 1:
			switch(pol2){
				case 0: case 2: return (ali.contains(4)&&ali.contains(7)) ? 5:4;
				case 4: case 5: return (ali.contains(4)&&ali.contains(5)) ? 2:0;
				default: return -1;
			}
		case 2:
			switch(pol2){
				case 1: case 3: return (ali.contains(8)&&ali.contains(11)) ? 5:4;
				case 4: case 5: return (ali.contains(8)&&ali.contains(9)) ? 3:1;
				default: return -1;
			}
		case 3:
			switch(pol2){
				case 0: case 2: return (ali.contains(12)&&ali.contains(15)) ? 5:4;
				case 4: case 5: return (ali.contains(12)&&ali.contains(13)) ? 2:0;
				default: return -1;
			}
		case 4:
			switch(pol2){
				case 0: case 2: return (ali.contains(16)&&ali.contains(17)) ? 3:1;
				case 1: case 3: return (ali.contains(16)&&ali.contains(19)) ? 2:0;
				default: return -1;
			}
		case 5:
			switch(pol2){
			case 0: case 2: return (ali.contains(20)&&ali.contains(21)) ? 3:1;
			case 1: case 3: return (ali.contains(20)&&ali.contains(23)) ? 2:0;
			default: return -1;
			}
		default:
			return -1;
		}
	}
	public static int[] getPoleForPosition(int pos){
		switch(pos){
		case 16:case 20:return new int[]{0,1};
		case 19:case 23:return new int[]{0,3};
		case 4: case 12:return new int[]{0,4};
		case 5: case 13:return new int[]{0,5};
		case 17:case 21:return new int[]{1,2};
		case 0: case 8: return new int[]{1,4};
		case 1: case 9: return new int[]{1,5};
		case 18:case 22:return new int[]{2,3};
		case 7: case 15:return new int[]{2,4};
		case 6: case 14:return new int[]{2,5};
		case 3: case 11:return new int[]{3,4};
		case 2: case 10:return new int[]{3,5};
		default: return new int[]{-1,-1};
		}
	}
	public static int getAnzFalseInArray(boolean[] array){
		int anz = array.length;
		int r = 0;
		for(int i = 0; i<anz; i++){
			if(!array[i]){
				r++;
			}
		}
		return r;
	}
	/**Welcher Pol ist gegenüber
	 * @param pol
	 * @return
	 */
	public static int polGegenuber(int pol){
		switch(pol){
			case 0: return 2;
			case 1: return 3;
			case 2: return 0;
			case 3: return 1;
			case 4: return 5;
			case 5: return 4;
			default: return -1;
		}
	}
	public static int[] crossOverPos(int p){
		switch(p){
			case 0: return new int[]{4,5};
			case 1: return new int[]{20,23};
			case 2: return new int[]{13,12};
			case 3: return new int[]{19,16};
			case 4: return new int[]{0,1};
			case 5: return new int[]{20,21};
			case 6: return new int[]{9,8};
			case 7: return new int[]{17,16};
			case 8: return new int[]{7,6};
			case 9: return new int[]{21,22};
			case 10: return new int[]{14,15};
			case 11: return new int[]{18,17};
			case 12: return new int[]{3,2};
			case 13: return new int[]{23,22};
			case 14: return new int[]{10,11};
			case 15: return new int[]{18,19};
			case 16: return new int[]{4,7};
			case 17: return new int[]{8,11};
			case 18: return new int[]{15,12};
			case 19: return new int[]{3,0};
			case 20: return new int[]{5,6};
			case 21: return new int[]{9,10};
			case 22: return new int[]{14,13};
			case 23: return new int[]{2,1};
			default: return new int[]{-1,-1};
		}
		
	}
	public static int getCrossOverPol(int p){
		switch(p){
			case 0: return 5;
			case 1: return 3;
			case 2: return 4;
			case 3: return 1;
			case 4: return 4;
			case 5: return 2;
			case 6: return 5;
			case 7: return 0;
			case 8: return 4;
			case 9: return 3;
			case 10: return 5;
			case 11: return 1;
			case 12: return 5;
			case 13: return 2;
			case 14: return 4;
			case 15: return 0;
			case 16: return 0;
			case 17: return 1;
			case 18: return 2;
			case 19: return 3;
			case 20: return 2;
			case 21: return 3;
			case 22: return 0;
			case 23: return 1;
			default: return -1;
			
		}
	}
	/**
	 * Gibt den Pol Rechts von pol1 an
	 * @param pol1: Pol
	 * @param pol2: Pol oben
	 * @return : Rechter Pol
	 */
	public static int change2PolPositionPR(int pol1, int pol2){
		/**
		 * 0 - 1 ==> 5
		 * 0 - 2 ==> ---
		 * 0 - 3 ==> 4
		 * 0 - 4 ==> 1
		 * 0 - 5 ==> 3
		 * 1 - 0 ==> 4
		 * 1 - 2 ==> 5
		 * 1 - 3 ==> ---
		 * 1 - 4 ==> 2
		 * 1 - 5 ==> 0
		 * 2 - 0 ==> ---
		 * 2 - 1 ==> 4
		 * 2 - 3 ==> 5
		 * 2 - 4 ==> 3
		 * 2 - 5 ==> 1
		 * 3 - 0 ==> 5
		 * 3 - 1 ==> ---
		 * 3 - 2 ==> 4
		 * 3 - 4 ==> 0
		 * 3 - 5 ==> 2
		 * 4 - 0 ==> 3
		 * 4 - 1 ==> 0
		 * 4 - 2 ==> 1
		 * 4 - 3 ==> 2
		 * 4 - 5 ==> ---
		 * 5 - 0 ==> 1
		 * 5 - 1 ==> 2
		 * 5 - 2 ==> 3
		 * 5 - 3 ==> 0
		 * 5 - 4 ==> ---
		 */
		if((pol1==1&&pol2==5)||(pol1==3&&pol2==4)||(pol1==4&&pol2==1)||(pol1==5&&pol2==3)){
			return 0;
		}else
		if((pol1==0&&pol2==4)||(pol1==2&&pol2==5)||(pol1==4&&pol2==2)||(pol1==5&&pol2==0)){
			return 1;
		}else
		if((pol1==1&&pol2==4)||(pol1==3&&pol2==5)||(pol1==4&&pol2==3)||(pol1==5&&pol2==1)){
			return 2;
		}else
		if((pol1==0&&pol2==5)||(pol1==2&&pol2==4)||(pol1==4&&pol2==0)||(pol1==5&&pol2==2)){
			return 3;
		}else
		if((pol1==0&&pol2==3)||(pol1==1&&pol2==0)||(pol1==2&&pol2==1)||(pol1==3&&pol2==2)){
			return 4;
		}else
		if((pol1==0&&pol2==1)||(pol1==1&&pol2==2)||(pol1==2&&pol2==3)||(pol1==3&&pol2==0)){
			return 5;
		}else
		if((pol1==0&&pol2==2)||(pol1==1&&pol2==3)){
			return 5;
		}else
		if((pol1==4&&pol2==5)){
			return 1;
		}
		
		//Bei einem Fehler...
		return -1;
	}
	
	
	public static boolean[] getPolBand(int p1, int p2){
		//System.out.println("PolRing: "+p1+":"+p2);
		int pol = p1/4;
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole1 = SphereUtils.getPoleForPosition(p1);
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole2 = SphereUtils.getPoleForPosition(p2);
				
		//System.out.println("PolRing gegeben: "+pole1[0]+","+pole1[1]+","+pole2[0]+","+pole2[1]);
		
		//Variable mit den beiden möglichen Polen
		int[] poleMoeglich = new int[2]; 
		
		LinkedList<Integer> pol1 = new LinkedList<Integer>();
		LinkedList<Integer> pol2 = new LinkedList<Integer>();
		
		pol1.add(pole1[0]);pol1.add(pole1[1]);
		pol2.add(pole2[0]);pol2.add(pole2[1]);
				
		//Gehe bei der ersten Position alle durch
		for(int i = 0; i<2; i++){
					
			if(!pol2.contains(pol1.get(i))){
				poleMoeglich[0]=pol1.get(i);
			}
			if(!pol1.contains(pol2.get(i))){
				poleMoeglich[1]=pol2.get(i);
			}					
		}
		//System.out.println("PolRing followed: "+poleMoeglich[0]+","+poleMoeglich[1]);
		
		LinkedList<Integer> polRing = new LinkedList<Integer>();
		//ist grundvoraussetzung gebenen
		int addPol = completePolBand(new int[]{pol, poleMoeglich[0], poleMoeglich[1]});
		//Dann gibt wahr zurück
		polRing.add(poleMoeglich[0]);
		polRing.add(poleMoeglich[1]);
		polRing.add(addPol);
		polRing.add(pol);
					
		boolean[] r = new boolean[6];
		for(int i = 0; i<6; i++){
			r[i] = polRing.contains(i);
		}
		
		return r;
	}

	public static int completePolBand(int[] pole){
		if(pole.length==3){
			Arrays.sort(pole);
			if((pole[0]==1&&pole[1]==2&&pole[2]==3)||(pole[0]==2&&pole[1]==4&&pole[2]==5)){
				return 0;
			}else
			if((pole[0]==0&&pole[1]==2&&pole[2]==3)||(pole[0]==3&&pole[1]==4&&pole[2]==5)){
				return 1;
			}else
			if((pole[0]==0&&pole[1]==1&&pole[2]==3)||(pole[0]==0&&pole[1]==4&&pole[2]==5)){
				return 2;
			}else
			if((pole[0]==0&&pole[1]==1&&pole[2]==2)||(pole[0]==1&&pole[1]==4&&pole[2]==5)){
				return 3;
			}else
			if((pole[0]==0&&pole[1]==2&&pole[2]==5)||(pole[0]==1&&pole[1]==3&&pole[2]==5)){
				return 4;
			}else
			if((pole[0]==0&&pole[1]==2&&pole[2]==4)||(pole[0]==1&&pole[1]==3&&pole[2]==4)){
				return 5;
			}
			return -1;
		}else{
			return -1;
		}
	}
	public static int verschiebeZuHilfPol(int p, int hilfspol){
		int pol = p/4;
		int pos = p%4;
		if(pol==1||pol==2||pol==4){
			pos=3-pos;
		}
		if(pol<4&&hilfspol<4){
			switch(pos){
				case 0:
					switch(hilfspol){
						case 0: return 1;
						case 1: return 6;
						case 2: return 10;
						case 3: return 13;
						default: return -1;
					}
				case 1:
					switch(hilfspol){
						case 0: return 0;
						case 1: return 7;
						case 2: return 11;
						case 3: return 12;
						default: return -1;
					}
				case 2:
					switch(hilfspol){
						case 0: return 3;
						case 1: return 4;
						case 2: return 8;
						case 3: return 15;
						default: return -1;
					}
				case 3:
					switch(hilfspol){
						case 0: return 2;
						case 1: return 5;
						case 2: return 9;
						case 3: return 14;
						default: return -1;
					}
				default: return -1;
			}
		}else if(pol>3){
			switch(p){
				case 16:
					switch(hilfspol){
						case 0: return 2;
						case 1: return 6;
						case 2: return 11;
						case 3: return 15;
						case 5: return 22;
						default: return -1;
					}
				case 17:
					switch(hilfspol){
						case 0: return 3;
						case 1: return 5;
						case 2: return 10;
						case 3: return 12;
						case 5: return 23;
						default:return -1;
					}
				case 18:
					switch(hilfspol){
						case 0: return 0;
						case 1: return 4;
						case 2: return 9;
						case 3: return 13;
						case 5: return 20;
						default: return -1;
					}
				case 19:
					switch(hilfspol){
						case 0: return 1;
						case 1: return 6;
						case 2: return 8;
						case 3: return 14;
						case 5: return 21;
						default: return -1;
					}
				case 20:
					switch(hilfspol){
						case 0: return 3;
						case 1: return 7;
						case 2: return 10;
						case 3: return 14;
						case 4: return 18;
						default: return -1;
					}
				case 21:
					switch(hilfspol){
						case 0: return 2;
						case 1: return 4;
						case 2: return 11;
						case 3: return 13;
						case 4: return 19;
						default: return -1;
					}
				case 22:
					switch(hilfspol){
						case 0: return 1;
						case 1: return 5;
						case 2: return 8;
						case 3: return 12;
						case 4: return 16;
						default: return -1;
					}
				case 23:
					switch(hilfspol){
						case 0: return 0;
						case 1: return 6;
						case 2: return 9;
						case 3: return 11;
						case 4: return 17;
						default: return -1;
					}
				default: return -1;
			}
		}else{
			switch(pos){
				case 0:
					switch(hilfspol){
						case 4: return 16+((2+pol)%4);
						case 5: return 20+((3+pol)%4);
						default: return -1;
					}
				case 1:
					switch(hilfspol){
						case 4: return 16+((3+pol)%4);
						case 5: return 20+((2+pol)%4);
						default: return -1;
					}
				case 2:
					switch(hilfspol){
						case 4: return 16+((0+pol)%4);
						case 5: return 20+((1+pol)%4);
						default: return -1;
					}
				case 3:
					switch(hilfspol){
						case 4: return 16+((1+pol)%4);
						case 5: return 20+((0+pol)%4);
						default: return -1;
					}
				default: return -1;
			}
		}

	}
}
