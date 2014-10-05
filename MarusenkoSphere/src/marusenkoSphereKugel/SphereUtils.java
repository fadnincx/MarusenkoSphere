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
		
		//Bekomme den String
		s = getDrehungFromStringAsString(s);
		
		//Gib das Resultat zurück
		return new int[] {Integer.parseInt(s.substring(0,1)),Integer.parseInt(s.substring(1,2)),Integer.parseInt(s.substring(2,3))};
		
	}
	
	/**
	 * Drehung aus dem String bekommen und als String zurückgeben
	 */
	public static String getDrehungFromStringAsString(String s){
		
		//Schneide Leerzeichen ab
		s = s.trim();
		
		//Splite den String auf
		String[] splitString = s.split("n");
		
		//Gib das Resultat zurück
		return splitString[2];
		
	}

	/**
	 * Gibt den reinen Kugelcode zurück, ohne Step und Drehung
	 */
	public static String getPureSphereCode(String s){
		
		//Schneide Leerzeichen ab
		s = s.trim();
		
		//Splite den String auf
		String[] splitString = s.split("n");
		
		//Gib das Resultat zurück
		return splitString[0];
		
	}
	
	/**
	 * Bekomme die 4 Werte eines Pols aus dem String als int-Array
	 */
	public static int[] extractPolFromStringReturnAsIntArray(String s, int Pol){

		//Gib das Resultat zurück
		return new int[]{
				Integer.parseInt(s.substring(((Pol*4+0)+8),((Pol*4+0)+9))),
				Integer.parseInt(s.substring(((Pol*4+1)+8),((Pol*4+1)+9))),
				Integer.parseInt(s.substring(((Pol*4+2)+8),((Pol*4+2)+9))),
				Integer.parseInt(s.substring(((Pol*4+3)+8),((Pol*4+3)+9)))
		};
	}
	
	/**
	 * Gibt den passenden Con Index zum gegebenen Tri Index zurück
	 */
	public static int findCorrectConIndexFromTri(int tri){
		
		//Switche nach an welchem Con gegrenzt wird
		switch(tri){
		
			//Con == 0
			case 11: case 15: case 18: return 0;
			
			//Con == 1
			case 3: case 12: case 19:  return 1;
			
			//Con == 2
			case 0:  case 4: case 16:  return 2;
			
			//Con == 3
			case 7:  case 8: case 17:  return 3;
			
			//Con == 4
			case 10: case 14: case 22: return 4;
			
			//Con == 5
			case 2: case 13: case 23:  return 5;
			
			//Con = 6
			case 1:  case 5: case 20:  return 6;
			
			//Con == 7
			case 6:  case 9: case 21:  return 7;
			
			//Sonst return -1
			default: return -1;
			
		}
		
	}
	
	/**
	 * Bekomme die 3 angrenzenden Positionen eines Con als Int-Array
	 */
	public static int[] conToPos(int con){
		
		switch(con){
		
			//Con == 0
			case 0: return new int[]{11,15,18};
			
			//Con == 1
			case 1: return new int[]{3,12,19};
			
			//Con == 2
			case 2: return new int[]{0,4,16};
			
			//Con == 3
			case 3: return new int[]{7,8,17};
			
			//Con == 4
			case 4: return new int[]{10,14,22};
			
			//Con == 5
			case 5: return new int[]{2,13,23};
			
			//Con == 6
			case 6: return new int[]{1,5,20};
			
			//Con == 7
			case 7: return new int[]{6,9,21};
			
			//Sonst return -1
			default: return new int[]{-1,-1,-1};
			
		}
		
	}
	
	/**
	 * Gibt die zusätzlichen Informationen zur Drehung zurück
	 */
	public static int strategieStandartGetTurnOpt(int pol1Pos1, int pol1Pos2, int pol2){
		
		//Variable mit dem Pol auf welchem die Positionen liegen
		int pol1 = pol1Pos1/4;
		
		//Positionen zuweisen
		int p1 = pol1Pos1<pol1Pos2? pol1Pos1:pol1Pos2;
		int p2 = pol1Pos1>pol1Pos2? pol1Pos1:pol1Pos2;
		
		//Switche gemäss dem Pol auf welchem die Positionen liegen
		switch(pol1){
		
			//Wenn Pol = 0
			case 0:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 1, dann gib 1 zurück
					case 1: return 1;
					
					//Wenn Pol = 3, dann gib 2 zurück
					case 3: return 2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 0 und 1 sind. Gibt je nachdem andere Werte zurück
					case 4: return (p1==0&&p2==1) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 0 und 1 sind. Gibt je nachdem andere Werte zurück
					case 5: return (p1==0&&p2==1) ? 1:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 1
			case 1:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann gib 1 zurück
					case 0: return 1;
					
					//Wenn Pol = 2, dann gib 2 zurück
					case 2: return 2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 4 und 5 sind. Gibt je nachdem andere Werte zurück
					case 4: return (p1==4&&p2==5) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 4 und 5 sind. Gibt je nachdem andere Werte zurück
					case 5: return (p1==4&&p2==5) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 2
			case 2:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 1, dann gib 1 zurück
					case 1: return 1;
					
					//Wenn Pol = 3, dann gib 2 zurück
					case 3: return 2;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 8 und 9 sind. Gibt je nachdem andere Werte zurück
					case 4: return (p1==8&&p2==9) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 8 und 9 sind. Gibt je nachdem andere Werte zurück
					case 5: return (p1==8&&p2==9) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 3
			case 3:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 12 und 15 sind. Gibt je nachdem andere Werte zurück
					case 0: return (p1==12&&p2==15) ? 1:2;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 12 und 15 sind. Gibt je nachdem andere Werte zurück
					case 2: return (p1==12&&p2==15) ? 2:1;
					
					//Wenn Pol = 4, dann prüfe ob die Positionen 12 und 13 sind. Gibt je nachdem andere Werte zurück
					case 4: return (p1==12&&p2==13) ? 1:2;
					
					//Wenn Pol = 5, dann prüfe ob die Positionen 12 und 13 sind. Gibt je nachdem andere Werte zurück
					case 5: return (p1==12&&p2==13) ? 2:1;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 4
			case 4:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 16 und 17 sind. Gibt je nachdem andere Werte zurück
					case 0: return (p1==16&&p2==17) ? 1:2;
					
					//Wenn Pol = 1, dann gib 1 zurück
					case 1: return 1;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 16 und 17 sind. Gibt je nachdem andere Werte zurück
					case 2: return (p1==16&&p2==17) ? 2:1;
					
					//Wenn Pol = 3, dann gib 2 zurück
					case 3: return 2;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}
				
			//Wenn Pol = 5
			case 5:
				
				//Switche gemäss dem Pol, auf welchem die Farben liegen
				switch(pol2){
				
					//Wenn Pol = 0, dann prüfe ob die Positionen 20 und 21 sind. Gibt je nachdem andere Werte zurück
					case 0: return (p1==20&&p2==21) ? 1:2;
					
					//Wenn Pol = 1, dann gib 1 zurück
					case 1: return 1;
					
					//Wenn Pol = 2, dann prüfe ob die Positionen 20 und 21 sind. Gibt je nachdem andere Werte zurück
					case 2: return (p1==20&&p2==21) ? 2:1;
					
					//Wenn Pol = 3, dann gib 2 zurück
					case 3: return 2;
					
					//Ansonten liefer ein leeres Resultat zurück
					default: return 0;
				}

			//Ansonten liefer ein leeres Resultat zurück
			default:
				return 0;
		}
		
	}
	
	/**
	 * Gibt die Anzahl false in einem Array zurück
	 */
	public static int getAnzFalseInArray(boolean[] array){
		
		//Bekomme die länge des Arrays
		int length = array.length;
		
		//Return anzahl = 0
		int anzahl = 0;
		
		//Gehe die gesammte länge durch
		for(int i = 0; i<length; i++){
			
			//Wenn Position = false
			if(!array[i]){
				
				//Zähle hoch
				anzahl++;
				
			}
			
		}
		
		//Gib anzahl zurück
		return anzahl;
		
	}
	
	/**
	 * Welcher Pol lieget gegenüber des gegebenen
	 */
	public static int polGegenuber(int pol){
		//Switche gemäss Pol
		switch(pol){
		
			//Pol == 0
			case 0: return 2;
			
			//Pol == 1
			case 1: return 3;
			
			//Pol == 2
			case 2: return 0;
			
			//Pol  == 3
			case 3: return 1;
			
			//Pol == 4
			case 4: return 5;
			
			//Pol == 5
			case 5: return 4;
			
			//Ansonsten
			default: return -1;
			
		}
		
	}
	
	/**
	 * Gibt die Positionen beim crossOver zurück
	 * @param p
	 */
	public static int[] crossOverPos(int p){
		//Gib werte gemäss Tabelle zurück
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
	
	/**
	 * Gib den Pol für CrossOver zurück
	 */
	public static int getCrossOverPol(int p){
		
		//Switche nach return Pol
		switch(p){
		
			//Pol == 0
			case 7: case 15: case 16: case 22: return 0;
			
			//Pol == 1
			case 3: case 11: case 17: case 23: return 1;
			
			//Pol == 2
			case 5: case 13: case 18: case 20: return 2;
			
			//Pol == 3
			case 1: case 9:  case 19: case 21: return 3;
			
			//Pol == 4
			case 2: case 4:  case 8:  case 14: return 4;
			
			//Pol == 5
			case 0: case 6:  case 10: case 12: return 5;
			
			//Ansonsten
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
	
	
	/*
	 * Gib das PolBand zurück
	 */
	public static boolean[] getPolBand(int p1, int p2){

		//Speichere den Pol
		int pol = p1/4;
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole1 = SphereUtils.getPoleForPosition(p1);
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole2 = SphereUtils.getPoleForPosition(p2);
				
		
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
	
	/**
	 * Hilfsfunktion für PolBand
	 */
	private static int[] getPoleForPosition(int pos){
		
		//Gib werte gemäss Tabelle zurück
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

	/**
	 * Hilfsfunktion für PolBand
	 * @param pole
	 * @return
	 */
	private static int completePolBand(int[] pole){
		
		//Wenn 3 Werte in Array sind
		if(pole.length==3){
			
			//Sortiere Array
			Arrays.sort(pole);
			
			//Gibt zurück, gemäss dem Array
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
			
		}
		
		//Wenn hier ankommt, return -1
		return -1;
		
	}
	
	/**
	 * Gibt Werte zur drehung zurück
	 */
	public static int verschiebeZuHilfPol(int p, int hilfspol){
		
		//Speichere Pol von P
		int pol = p/4;
		
		//Speichere Position von P
		int pos = p%4;
		
		//Wenn einer der Verdrehten Pole
		if(pol==1||pol==2||pol==4){
			
			//Dann wert invertieren
			pos=3-pos;
			
		}
		
		//Wenn Pol <4 und Hilfpol <4
		if(pol<4&&hilfspol<4){
			
			//Switche nach Pos
			switch(pos){
			
				//Pos == 0
				case 0:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 1;
						
						//Hilfspol == 1
						case 1: return 6;
						
						//Hilfspol == 2
						case 2: return 10;
						
						//Hilfspol == 3
						case 3: return 13;
						
						//Ansonsten
						default: return -1;
						
					}
					
				//Pos == 1	
				case 1:
					
					//Switceh nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 0;
						
						//Hilfspol == 1
						case 1: return 7;
						
						//Hilfspol == 2
						case 2: return 11;
						
						//Hilfspol == 3
						case 3: return 12;
						
						//Ansonten
						default: return -1;
						
					}
					
				//Pos == 2	
				case 2:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 3;
						
						//Hilfspol == 1
						case 1: return 4;
						
						//Hilfspol == 2
						case 2: return 8;
						
						//Hilfspol == 3
						case 3: return 15;
						
						//Ansonten
						default: return -1;
						
					}
				
					//Pos == 3
				case 3:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 2;
						
						//Hilfspol == 1
						case 1: return 5;
						
						//Hilfspol == 2
						case 2: return 9;
						
						//Hilfspol == 3
						case 3: return 14;
						
						//Ansonten
						default: return -1;
						
					}
					
				//Ansonten	
				default: return -1;
				
			}
		
		//Wenn Pol >3
		}else if(pol>3){
			
			//Switche nach p
			switch(p){
			
				//p == 16
				case 16:
					
					//Switche nach Hilfspol
					switch(hilfspol){
						
						//Hilfspol == 0
						case 0: return 2;
						
						//Hilfspol == 1
						case 1: return 6;
						
						//Hilfspol == 2
						case 2: return 11;
						
						//Hilfspol == 3
						case 3: return 15;
						
						//Hilfspol == 5
						case 5: return 22;
						
						//Ansonten
						default: return -1;
						
					}
					
				//p == 17	
				case 17:
					
					//switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 3;
						
						//Hilfpol == 1
						case 1: return 5;
						
						//Hilfspol == 2
						case 2: return 10;
						
						//Hilfspol == 3
						case 3: return 12;
						
						//Hilfspol == 5
						case 5: return 23;
						
						//Ansonten
						default:return -1;
						
					}
					
				//p == 18	
				case 18:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 0;
						
						//Hilfspol == 1
						case 1: return 4;
						
						//Hilfspol == 2
						case 2: return 9;
						
						//Hilfspol == 3
						case 3: return 13;
						
						//Hilfspol == 5
						case 5: return 20;
						
						//Ansonsten
						default: return -1;
						
					}
					
				//p == 19	
				case 19:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 1;
						
						//Hilfspol == 1
						case 1: return 6;
						
						//Hilfspol == 2
						case 2: return 8;
						
						//Hilfspol == 3
						case 3: return 14;
						
						//Hilfspol == 5
						case 5: return 21;
						
						//Ansonten
						default: return -1;
						
					}
					
				//p == 20	
				case 20:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 3;
						
						//Hilfspol == 1
						case 1: return 7;
						
						//Hilfspol == 2
						case 2: return 10;
						
						//Hilfspol === 3
						case 3: return 14;
						
						//Hilfspol == 4
						case 4: return 18;
						
						//Ansonsten
						default: return -1;
						
					}
					
				//p == 21	
				case 21:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 2;
						
						//Hilfspol == 1
						case 1: return 4;
						
						//Hilfspol == 2
						case 2: return 11;
						
						//Hilfspol == 3
						case 3: return 13;
						
						//Hilfspol == 4
						case 4: return 19;
						
						//Ansonten
						default: return -1;
						
					}
					
				//p == 22	
				case 22:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 1;
						
						//Hilfspol == 1
						case 1: return 5;
						
						//Hilfspol == 2
						case 2: return 8;
						
						//Hilfspol == 3
						case 3: return 12;
						
						//Hilfspol == 4
						case 4: return 16;
						
						//Ansonten
						default: return -1;
						
					}
					
				//p == 23	
				case 23:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 0
						case 0: return 0;
						
						//Hilfspol == 1
						case 1: return 6;
						
						//Hilfspol == 2
						case 2: return 9;
						
						//Hilfspol == 3
						case 3: return 11;
						
						//Hilfspol == 4
						case 4: return 17;
						
						//Ansonten
						default: return -1;
						
					}
					
				//Ansonten	
				default: return -1;
				
			}
		
		//Ansonsten	
		}else{
			
			//Switche nach Position
			switch(pos){
			
				//Position == 0
				case 0:
					
					//Switche nach Hilfpol
					switch(hilfspol){
					
						//Hilfspol == 4
						case 4: return 16+((2+pol)%4);
						
						//Hilfspol == 5
						case 5: return 20+((3+pol)%4);
						
						//Ansonten
						default: return -1;
						
					}
					
				//Position == 1	
				case 1:
					
					//Switceh nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 4
						case 4: return 16+((3+pol)%4);
						
						//Hilfspol == 5
						case 5: return 20+((2+pol)%4);
						
						//Ansonten
						default: return -1;
						
					}
					
				//Position == 2	
				case 2:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 4
						case 4: return 16+((0+pol)%4);
						
						//Hilfspol == 5
						case 5: return 20+((1+pol)%4);
						
						//Ansonten
						default: return -1;
						
					}
				
				//Position == 3
				case 3:
					
					//Switche nach Hilfspol
					switch(hilfspol){
					
						//Hilfspol == 4
						case 4: return 16+((1+pol)%4);
						
						//Hilfspol == 5
						case 5: return 20+((0+pol)%4);
						
						//Ansonten
						default: return -1;
						
					}
					
				//Ansonten	
				default: return -1;
				
			}
			
		}

	}
	
	/**
	 * Gibt die Parameter zum drehen
	 * 
	 * Return Array: [PreDrehPosP2][PolRechts][AnzPolRechts][AnzPol1]
	 */
	public static int[] optionsChange2PosEqualDirect(int p1, int p2){
		
		//Switche Nach Position 1 
		switch(p1){
		
			//Position 1 == 0
			case 0:
				
				//Switche nach Position 2
				switch(p2){
					case 4: return new int[]{2,5,1,1};
					case 5: return new int[]{1,5,1,1};
					case 6: return new int[]{0,5,1,1};
					case 7: return new int[]{3,5,1,1};
					case 8: return new int[]{2,5,2,1};
					case 9: return new int[]{1,5,2,1};
					case 10:return new int[]{0,5,2,1};
					case 11:return new int[]{3,5,2,1};
					case 12:return new int[]{3,5,3,1};
					case 13:return new int[]{0,5,3,1};
					case 14:return new int[]{1,5,3,1};
					case 15:return new int[]{2,5,3,1};
					case 16:return new int[]{2,3,3,3};
					case 17:return new int[]{1,3,3,3};
					case 18:return new int[]{0,3,3,3};
					case 19:return new int[]{3,3,3,3};
					case 20:return new int[]{1,3,1,3};
					case 21:return new int[]{2,3,1,3};
					case 22:return new int[]{3,3,1,3};
					case 23:return new int[]{0,3,1,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 1	
			case 1:
				
				//Switche nach Position 2
				switch(p2){
					case 4: return new int[]{3,4,3,3};
					case 5: return new int[]{2,4,3,3};
					case 6: return new int[]{1,4,3,3};
					case 7: return new int[]{0,4,3,3};
					case 8: return new int[]{3,4,2,3};
					case 9: return new int[]{2,4,2,3};
					case 10:return new int[]{1,4,2,3};
					case 11:return new int[]{0,4,2,3};
					case 12:return new int[]{0,4,1,3};
					case 13:return new int[]{1,4,1,3};
					case 14:return new int[]{2,4,1,3};
					case 15:return new int[]{3,4,1,3};
					case 16:return new int[]{3,3,3,1};
					case 17:return new int[]{2,3,3,1};
					case 18:return new int[]{1,3,3,1};
					case 19:return new int[]{0,3,3,1};
					case 20:return new int[]{2,3,1,1};
					case 21:return new int[]{3,3,1,1};
					case 22:return new int[]{0,3,1,1};
					case 23:return new int[]{1,3,1,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 2	
			case 2:
				
				//Switche nach Position 2
				switch(p2){
					case 4: return new int[]{0,4,3,1};
					case 5: return new int[]{3,4,3,1};
					case 6: return new int[]{2,4,3,1};
					case 7: return new int[]{1,4,3,1};
					case 8: return new int[]{0,4,2,1};
					case 9: return new int[]{3,4,2,1};
					case 10:return new int[]{2,4,2,1};
					case 11:return new int[]{1,4,2,1};
					case 12:return new int[]{1,4,1,1};
					case 13:return new int[]{2,4,1,1};
					case 14:return new int[]{3,4,1,1};
					case 15:return new int[]{0,4,1,1};
					case 16:return new int[]{0,1,1,3};
					case 17:return new int[]{3,1,1,3};
					case 18:return new int[]{2,1,1,3};
					case 19:return new int[]{1,1,1,3};
					case 20:return new int[]{3,1,3,3};
					case 21:return new int[]{0,1,3,3};
					case 22:return new int[]{1,1,3,3};
					case 23:return new int[]{2,1,3,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 3	
			case 3:
				
				//Switche nach Position 2
				switch(p2){
					case 4: return new int[]{1,5,1,3};
					case 5: return new int[]{0,5,1,3};
					case 6: return new int[]{3,5,1,3};
					case 7: return new int[]{2,5,1,3};
					case 8: return new int[]{1,5,2,3};
					case 9: return new int[]{0,5,2,3};
					case 10:return new int[]{3,5,2,3};
					case 11:return new int[]{2,5,2,3};
					case 12:return new int[]{2,5,3,3};
					case 13:return new int[]{3,5,3,3};
					case 14:return new int[]{0,5,3,3};
					case 15:return new int[]{1,5,3,3};
					case 16:return new int[]{1,1,1,1};
					case 17:return new int[]{0,1,1,1};
					case 18:return new int[]{3,1,1,1};
					case 19:return new int[]{2,1,1,1};
					case 20:return new int[]{0,1,3,1};
					case 21:return new int[]{1,1,3,1};
					case 22:return new int[]{2,1,3,1};
					case 23:return new int[]{3,1,3,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 4
			case 4:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{2,5,3,3};
					case 1: return new int[]{3,5,3,3};
					case 2: return new int[]{0,5,3,3};
					case 3: return new int[]{1,5,3,3};
					case 8: return new int[]{1,5,1,3};
					case 9: return new int[]{0,5,1,3};
					case 10:return new int[]{3,5,1,3};
					case 11:return new int[]{2,5,1,3};
					case 12:return new int[]{2,5,2,3};
					case 13:return new int[]{3,5,2,3};
					case 14:return new int[]{0,5,2,3};
					case 15:return new int[]{1,5,2,3};
					case 16:return new int[]{2,2,1,1};
					case 17:return new int[]{1,2,1,1};
					case 18:return new int[]{0,2,1,1};
					case 19:return new int[]{3,2,1,1};
					case 20:return new int[]{3,2,3,1};
					case 21:return new int[]{0,2,3,1};
					case 22:return new int[]{1,2,3,1};
					case 23:return new int[]{2,2,3,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 5	
			case 5:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{1,4,1,1};
					case 1: return new int[]{2,4,1,1};
					case 2: return new int[]{3,4,1,1};
					case 3: return new int[]{0,4,1,1};
					case 8: return new int[]{0,4,3,1};
					case 9: return new int[]{3,4,3,1};
					case 10:return new int[]{2,4,3,1};
					case 11:return new int[]{1,4,3,1};
					case 12:return new int[]{1,4,2,1};
					case 13:return new int[]{2,4,2,1};
					case 14:return new int[]{3,4,2,1};
					case 15:return new int[]{0,4,2,1};
					case 16:return new int[]{1,2,1,3};
					case 17:return new int[]{0,2,1,3};
					case 18:return new int[]{3,2,1,3};
					case 19:return new int[]{2,2,1,3};
					case 20:return new int[]{2,2,3,3};
					case 21:return new int[]{3,2,3,3};
					case 22:return new int[]{0,2,3,3};
					case 23:return new int[]{1,2,3,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 6
			case 6:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{0,4,1,3};
					case 1: return new int[]{1,4,1,3};
					case 2: return new int[]{2,4,1,3};
					case 3: return new int[]{3,4,1,3};
					case 8: return new int[]{3,4,3,3};
					case 9: return new int[]{2,4,3,3};
					case 10:return new int[]{1,4,3,3};
					case 11:return new int[]{0,4,3,3};
					case 12:return new int[]{0,4,2,3};
					case 13:return new int[]{1,4,2,3};
					case 14:return new int[]{2,4,2,3};
					case 15:return new int[]{3,4,2,3};
					case 16:return new int[]{0,0,3,1};
					case 17:return new int[]{3,0,3,1};
					case 18:return new int[]{2,0,3,1};
					case 19:return new int[]{1,0,3,1};
					case 20:return new int[]{1,0,1,1};
					case 21:return new int[]{2,0,1,1};
					case 22:return new int[]{3,0,1,1};
					case 23:return new int[]{0,0,1,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 7	
			case 7:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{3,5,3,1};
					case 1: return new int[]{0,5,3,1};
					case 2: return new int[]{1,5,3,1};
					case 3: return new int[]{2,5,3,1};
					case 8: return new int[]{2,5,1,1};
					case 9: return new int[]{1,5,1,1};
					case 10:return new int[]{0,5,1,1};
					case 11:return new int[]{3,5,1,1};
					case 12:return new int[]{3,5,2,1};
					case 13:return new int[]{0,5,2,1};
					case 14:return new int[]{1,5,2,1};
					case 15:return new int[]{2,5,2,1};
					case 16:return new int[]{3,0,3,3};
					case 17:return new int[]{2,0,3,3};
					case 18:return new int[]{1,0,3,3};
					case 19:return new int[]{0,0,3,3};
					case 20:return new int[]{0,0,1,3};
					case 21:return new int[]{1,0,1,3};
					case 22:return new int[]{2,0,1,3};
					case 23:return new int[]{3,0,1,3};
					default:return new int[]{-1,-1,-1,-1};
				}
			
			//Position 1 == 8
			case 8:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{2,5,2,3};
					case 1: return new int[]{3,5,2,3};
					case 2: return new int[]{0,5,2,3};
					case 3: return new int[]{1,5,2,3};
					case 4: return new int[]{1,5,3,3};
					case 5: return new int[]{0,5,3,3};
					case 6: return new int[]{3,5,3,3};
					case 7: return new int[]{2,5,3,3};
					case 12:return new int[]{2,5,1,3};
					case 13:return new int[]{3,5,1,3};
					case 14:return new int[]{0,5,1,3};
					case 15:return new int[]{1,5,1,3};
					case 16:return new int[]{3,3,1,1};
					case 17:return new int[]{2,3,1,1};
					case 18:return new int[]{1,3,1,1};
					case 19:return new int[]{0,3,1,1};
					case 20:return new int[]{2,3,3,1};
					case 21:return new int[]{3,3,3,1};
					case 22:return new int[]{0,3,3,1};
					case 23:return new int[]{1,3,3,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 9	
			case 9:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{1,4,2,1};
					case 1: return new int[]{2,4,2,1};
					case 2: return new int[]{3,4,2,1};
					case 3: return new int[]{0,4,2,1};
					case 4: return new int[]{0,4,1,1};
					case 5: return new int[]{3,4,1,1};
					case 6: return new int[]{2,4,1,1};
					case 7: return new int[]{1,4,1,1};
					case 12:return new int[]{1,4,3,1};
					case 13:return new int[]{2,4,3,1};
					case 14:return new int[]{3,4,3,1};
					case 15:return new int[]{0,4,3,1};
					case 16:return new int[]{2,3,1,3};
					case 17:return new int[]{1,3,1,3};
					case 18:return new int[]{0,3,1,3};
					case 19:return new int[]{3,3,1,3};
					case 20:return new int[]{1,3,3,3};
					case 21:return new int[]{2,3,3,3};
					case 22:return new int[]{3,3,3,3};
					case 23:return new int[]{0,3,3,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 10	
			case 10:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{0,4,2,3};
					case 1: return new int[]{1,4,2,3};
					case 2: return new int[]{2,4,2,3};
					case 3: return new int[]{3,4,2,3};
					case 4: return new int[]{3,4,1,3};
					case 5: return new int[]{2,4,1,3};
					case 6: return new int[]{1,4,1,3};
					case 7: return new int[]{0,4,1,3};
					case 12:return new int[]{0,4,3,3};
					case 13:return new int[]{1,4,3,3};
					case 14:return new int[]{2,4,3,3};
					case 15:return new int[]{3,4,3,3};
					case 16:return new int[]{1,1,3,1};
					case 17:return new int[]{0,1,3,1};
					case 18:return new int[]{3,1,3,1};
					case 19:return new int[]{2,1,3,1};
					case 20:return new int[]{0,1,1,1};
					case 21:return new int[]{1,1,1,1};
					case 22:return new int[]{2,1,1,1};
					case 23:return new int[]{3,1,1,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 11	
			case 11:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{3,5,2,1};
					case 1: return new int[]{0,5,2,1};
					case 2: return new int[]{1,5,2,1};
					case 3: return new int[]{2,5,2,1};
					case 4: return new int[]{2,5,3,1};
					case 5: return new int[]{1,5,3,1};
					case 6: return new int[]{0,5,3,1};
					case 7: return new int[]{3,5,3,1};
					case 12:return new int[]{3,5,1,1};
					case 13:return new int[]{0,5,1,1};
					case 14:return new int[]{1,5,1,1};
					case 15:return new int[]{2,5,1,1};
					case 16:return new int[]{0,1,3,3};
					case 17:return new int[]{3,1,3,3};
					case 18:return new int[]{2,1,3,3};
					case 19:return new int[]{1,1,3,3};
					case 20:return new int[]{3,1,1,3};
					case 21:return new int[]{0,1,1,3};
					case 22:return new int[]{1,1,1,3};
					case 23:return new int[]{2,1,1,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 12	
			case 12:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{3,5,1,1};
					case 1: return new int[]{0,5,1,1};
					case 2: return new int[]{1,5,1,1};
					case 3: return new int[]{2,5,1,1};
					case 4: return new int[]{2,5,2,1};
					case 5: return new int[]{1,5,2,1};
					case 6: return new int[]{0,5,2,1};
					case 7: return new int[]{3,5,2,1};
					case 8: return new int[]{2,5,3,1};
					case 9: return new int[]{1,5,3,1};
					case 10:return new int[]{0,5,3,1};
					case 11:return new int[]{3,5,3,1};
					case 16:return new int[]{1,2,3,3};
					case 17:return new int[]{0,2,3,3};
					case 18:return new int[]{3,2,3,3};
					case 19:return new int[]{2,2,3,3};
					case 20:return new int[]{2,2,1,3};
					case 21:return new int[]{3,2,1,3};
					case 22:return new int[]{0,2,1,3};
					case 23:return new int[]{1,2,1,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 13	
			case 13:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{0,4,3,3};
					case 1: return new int[]{1,4,3,3};
					case 2: return new int[]{2,4,3,3};
					case 3: return new int[]{3,4,3,3};
					case 4: return new int[]{3,4,2,3};
					case 5: return new int[]{2,4,2,3};
					case 6: return new int[]{1,4,2,3};
					case 7: return new int[]{0,4,2,3};
					case 8: return new int[]{3,4,1,3};
					case 9: return new int[]{2,4,1,3};
					case 10:return new int[]{1,4,1,3};
					case 11:return new int[]{0,4,1,3};
					case 16:return new int[]{2,2,3,1};
					case 17:return new int[]{1,2,3,1};
					case 18:return new int[]{0,2,3,1};
					case 19:return new int[]{3,2,3,1};
					case 20:return new int[]{3,2,1,1};
					case 21:return new int[]{0,2,1,1};
					case 22:return new int[]{1,2,1,1};
					case 23:return new int[]{2,2,1,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 14	
			case 14:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{1,4,3,1};
					case 1: return new int[]{2,4,3,1};
					case 2: return new int[]{3,4,3,1};
					case 3: return new int[]{0,4,3,1};
					case 4: return new int[]{0,4,2,1};
					case 5: return new int[]{3,4,2,1};
					case 6: return new int[]{2,4,2,1};
					case 7: return new int[]{1,4,2,1};
					case 8: return new int[]{0,4,1,1};
					case 9: return new int[]{3,4,1,1};
					case 10:return new int[]{2,4,1,1};
					case 11:return new int[]{1,4,1,1};
					case 16:return new int[]{3,0,1,3};
					case 17:return new int[]{2,0,1,3};
					case 18:return new int[]{1,0,1,3};
					case 19:return new int[]{0,0,1,3};
					case 20:return new int[]{0,0,3,3};
					case 21:return new int[]{1,0,3,3};
					case 22:return new int[]{2,0,3,3};
					case 23:return new int[]{3,0,3,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 15	
			case 15:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{2,5,1,3};
					case 1: return new int[]{3,5,1,3};
					case 2: return new int[]{0,5,1,3};
					case 3: return new int[]{1,5,1,3};
					case 4: return new int[]{1,5,2,3};
					case 5: return new int[]{0,5,2,3};
					case 6: return new int[]{3,5,2,3};
					case 7: return new int[]{2,5,2,3};
					case 8: return new int[]{1,5,3,3};
					case 9: return new int[]{0,5,3,3};
					case 10:return new int[]{3,5,3,3};
					case 11:return new int[]{2,5,3,3};
					case 16:return new int[]{0,0,1,1};
					case 17:return new int[]{3,0,1,1};
					case 18:return new int[]{2,0,1,1};
					case 19:return new int[]{1,0,1,1};
					case 20:return new int[]{1,0,3,1};
					case 21:return new int[]{2,0,3,1};
					case 22:return new int[]{3,0,3,1};
					case 23:return new int[]{0,0,3,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 16	
			case 16:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{2,3,1,1};
					case 1: return new int[]{3,3,1,1};
					case 2: return new int[]{0,3,1,1};
					case 3: return new int[]{1,3,1,1};
					case 4: return new int[]{2,2,3,3};
					case 5: return new int[]{1,2,3,3};
					case 6: return new int[]{0,2,3,3};
					case 7: return new int[]{3,2,3,3};
					case 8: return new int[]{3,3,3,1};
					case 9: return new int[]{2,3,3,1};
					case 10:return new int[]{1,3,3,1};
					case 11:return new int[]{0,3,3,1};
					case 12:return new int[]{1,2,1,3};
					case 13:return new int[]{2,2,1,3};
					case 14:return new int[]{3,2,1,3};
					case 15:return new int[]{0,2,1,3};
					case 20:return new int[]{2,3,2,1};
					case 21:return new int[]{3,3,2,1};
					case 22:return new int[]{0,3,2,1};
					case 23:return new int[]{1,3,2,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 17	
			case 17:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{1,3,1,3};
					case 1: return new int[]{2,3,1,3};
					case 2: return new int[]{3,3,1,3};
					case 3: return new int[]{0,3,1,3};
					case 4: return new int[]{1,0,1,1};
					case 5: return new int[]{0,0,1,1};
					case 6: return new int[]{3,0,1,1};
					case 7: return new int[]{2,0,1,1};
					case 8: return new int[]{2,3,3,3};
					case 9: return new int[]{1,3,3,3};
					case 10:return new int[]{0,3,3,3};
					case 11:return new int[]{3,3,3,3};
					case 12:return new int[]{0,0,3,1};
					case 13:return new int[]{1,0,3,1};
					case 14:return new int[]{2,0,3,1};
					case 15:return new int[]{3,0,3,1};
					case 20:return new int[]{1,3,2,3};
					case 21:return new int[]{2,3,2,3};
					case 22:return new int[]{3,3,2,3};
					case 23:return new int[]{0,3,2,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 18	
			case 18:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{0,1,3,1};
					case 1: return new int[]{1,1,3,1};
					case 2: return new int[]{2,1,3,1};
					case 3: return new int[]{3,1,3,1};
					case 4: return new int[]{0,0,1,3};
					case 5: return new int[]{3,0,1,3};
					case 6: return new int[]{2,0,1,3};
					case 7: return new int[]{1,0,1,3};
					case 8: return new int[]{1,1,1,1};
					case 9: return new int[]{0,1,1,1};
					case 10:return new int[]{3,1,1,1};
					case 11:return new int[]{2,1,1,1};
					case 12:return new int[]{3,0,3,3};
					case 13:return new int[]{0,0,3,3};
					case 14:return new int[]{1,0,3,3};
					case 15:return new int[]{2,0,3,3};
					case 20:return new int[]{0,1,2,1};
					case 21:return new int[]{1,1,2,1};
					case 22:return new int[]{2,1,2,1};
					case 23:return new int[]{3,1,2,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 19	
			case 19:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{3,1,3,3};
					case 1: return new int[]{0,1,3,3};
					case 2: return new int[]{1,1,3,3};
					case 3: return new int[]{2,1,3,3};
					case 4: return new int[]{3,2,3,1};
					case 5: return new int[]{2,2,3,1};
					case 6: return new int[]{1,2,3,1};
					case 7: return new int[]{0,2,3,1};
					case 8: return new int[]{0,1,1,3};
					case 9: return new int[]{3,1,1,3};
					case 10:return new int[]{2,1,1,3};
					case 11:return new int[]{1,1,1,3};
					case 12:return new int[]{2,2,1,1};
					case 13:return new int[]{3,2,1,1};
					case 14:return new int[]{0,2,1,1};
					case 15:return new int[]{1,2,1,1};
					case 20:return new int[]{3,1,2,3};
					case 21:return new int[]{0,1,2,3};
					case 22:return new int[]{1,1,2,3};
					case 23:return new int[]{2,1,2,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 20	
			case 20:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{1,3,3,3};
					case 1: return new int[]{2,3,3,3};
					case 2: return new int[]{3,3,3,3};
					case 3: return new int[]{0,3,3,3};
					case 4: return new int[]{3,2,1,1};
					case 5: return new int[]{2,2,1,1};
					case 6: return new int[]{1,2,1,1};
					case 7: return new int[]{0,2,1,1};
					case 8: return new int[]{2,3,1,3};
					case 9: return new int[]{1,3,1,3};
					case 10:return new int[]{0,3,1,3};
					case 11:return new int[]{3,3,1,3};
					case 12:return new int[]{2,2,3,1};
					case 13:return new int[]{3,2,3,1};
					case 14:return new int[]{0,2,3,1};
					case 15:return new int[]{1,2,3,1};
					case 16:return new int[]{2,3,2,3};
					case 17:return new int[]{1,3,2,3};
					case 18:return new int[]{0,3,2,3};
					case 19:return new int[]{3,3,2,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 21	
			case 21:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{2,3,3,1};
					case 1: return new int[]{3,3,3,1};
					case 2: return new int[]{0,3,3,1};
					case 3: return new int[]{1,3,3,1};
					case 4: return new int[]{0,0,3,3};
					case 5: return new int[]{3,0,3,3};
					case 6: return new int[]{2,0,3,3};
					case 7: return new int[]{1,0,3,3};
					case 8: return new int[]{3,3,1,1};
					case 9: return new int[]{2,3,1,1};
					case 10:return new int[]{1,3,1,1};
					case 11:return new int[]{0,3,1,1};
					case 12:return new int[]{3,0,1,3};
					case 13:return new int[]{0,0,1,3};
					case 14:return new int[]{1,0,1,3};
					case 15:return new int[]{2,0,1,3};
					case 16:return new int[]{3,3,2,1};
					case 17:return new int[]{2,3,2,1};
					case 18:return new int[]{1,3,2,1};
					case 19:return new int[]{0,3,2,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 22	
			case 22:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{3,1,1,3};
					case 1: return new int[]{0,1,1,3};
					case 2: return new int[]{1,1,1,3};
					case 3: return new int[]{2,1,1,3};
					case 4: return new int[]{1,0,3,1};
					case 5: return new int[]{0,0,3,1};
					case 6: return new int[]{3,0,3,1};
					case 7: return new int[]{2,0,3,1};
					case 8: return new int[]{0,1,3,3};
					case 9: return new int[]{3,1,3,3};
					case 10:return new int[]{2,1,3,3};
					case 11:return new int[]{1,1,3,3};
					case 12:return new int[]{0,0,1,1};
					case 13:return new int[]{1,0,1,1};
					case 14:return new int[]{2,0,1,1};
					case 15:return new int[]{3,0,1,1};
					case 16:return new int[]{0,1,2,3};
					case 17:return new int[]{3,1,2,3};
					case 18:return new int[]{2,1,2,3};
					case 19:return new int[]{1,1,2,3};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			//Position 1 == 23	
			case 23:
				
				//Switche nach Position 2
				switch(p2){
					case 0: return new int[]{0,1,1,1};
					case 1: return new int[]{1,1,1,1};
					case 2: return new int[]{2,1,1,1};
					case 3: return new int[]{3,1,1,1};
					case 4: return new int[]{2,2,1,3};
					case 5: return new int[]{1,2,1,3};
					case 6: return new int[]{0,2,1,3};
					case 7: return new int[]{3,2,1,3};
					case 8: return new int[]{1,1,3,1};
					case 9: return new int[]{0,1,3,1};
					case 10:return new int[]{3,1,3,1};
					case 11:return new int[]{2,1,3,1};
					case 12:return new int[]{1,2,3,3};
					case 13:return new int[]{2,2,3,3};
					case 14:return new int[]{3,2,3,3};
					case 15:return new int[]{0,2,3,3};
					case 16:return new int[]{1,1,2,1};
					case 17:return new int[]{0,1,2,1};
					case 18:return new int[]{3,1,2,1};
					case 19:return new int[]{2,1,2,1};
					default:return new int[]{-1,-1,-1,-1};
				}
				
			}
		
		//Ansonsten
		return new int[]{-1,-1,-1,-1};	
		
	}
	
}
