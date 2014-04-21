package marusenkoSphereKugel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * Kugel-Datei
 * 
 * Kugel-Objekt, beinhaltet die Ganze Kugel f�r das ganze Programm, bringt den Zustand der Kugel jeweils von einem Objekt in ein n�chstes
 * 
 */
public class Kugel{
	/**
	 * tri ==> Array, welches die Dreiecke der Pole beinhaltet
	 * con ==> Array, welches die Verbindungsst�cke beinhaltet
	 */
	public int[] tri = new int[24]; //Arrays for Triangles
	public int[] con = new int[8]; //Arrays for Connectors
	public ArrayList<String> SolvingList = new ArrayList<String>();
	public int steps = 0;
	
	
	/**
	 * Funktion welche zum Initialisieren der Kugel aufgerufen wird
	 * 
	 * dabei wird den Array die Gr�sse zugewiesen
	 */
	public Kugel(){
		FillKugelRandom();
	}//#END Kugel()
	
	
	private void UpdateSolvingList(){
		this.SolvingList = new Solver().solve(this);
		String s = SolvingList.get(0);
		String[] sp = s.split("n");
		if(sp[0].length()==32&&sp.length==2){
			for(int i = 0; i<8;i++){
				con[i]=Integer.parseInt(s.substring(i, i+1));
			}
			for(int i = 0; i<24;i++){
				tri[i]=Integer.parseInt(s.substring(i+8, i+9));
			}
			steps = Integer.parseInt(sp[1]);
		}
	}
	
	/**
	 * Funktion, welche die Kugel fix f�llt
	 * 
	 * eine einfarbige Kugel, haupts�chlich f�rs Debugging der Grundfunktionen
	 */
	public void FillKugelFix(){
		/**
		 * F�llen der Verbingungsst�cke
		 */
		for(int i = 0; i<8;i++){
			con[i]=1;
		}
		/**
		 * F�llen der Dreiecke
		 */
		for(int i = 0; i<24;i++){
			tri[i]=1;
		}
		steps=0;
		UpdateSolvingList();
	}//#END FillKugelFix()
	public void FillKugelFromString(String s){
		FillKugelFromStringWithoutSolvingList(s);
		UpdateSolvingList();
	}
	public void FillKugelFromStringWithoutSolvingList(String s){
		String[] sp = s.split("n");
		if(sp[0].length()==32&&sp.length==2){
			for(int i = 0; i<8;i++){
				con[i]=Integer.parseInt(s.substring(i, i+1));
			}
			for(int i = 0; i<24;i++){
				tri[i]=Integer.parseInt(s.substring(i+8, i+9));
			}
			steps = Integer.parseInt(sp[1]);
		}
	}

	/**
	 * Funktion zum zuf�lligen F�llen der Kugel mit realistischen Werten
	 */
	public void FillKugelRandom(){
		/**
		 * Objekt rm als Zufallsobjekt, auswelcher dann die Zusatzzahl generiert wird
		 */
		Random rm = new Random();
		
		/**
		 * Alle Verbingungsst�cke werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben
		 */
		Arrays.fill(con, -1);
		/**
		 * Diesen 8 Verbingungsst�cken wird eine Farbe zu geteilt
		 */
		for(int i = 0; i<8;i++){
			/**
			 * Neue Zufallszahl
			 */
			int r = rm.nextInt(8-i);
			/**
			 * Finde das n�chste noch nicht einer Farbe zugeteilte Verbindungsst�ck
			 * 
			 * wenn gefunden, dann weise Farbe zu
			 */
			boolean found = false;
			while(!found){
				if(con[r]==-1){
					con[r] = i;
					found = true;
				}else{
					r++;
					r %= 8;
				}
			}
		}
		
		/**
		 * Alle Dreiecke werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben 
		 */
		Arrays.fill(tri, -1);
		/**
		 * Den 24 Dreiecken wird eine Farbe zugeteilt (jeweils 3 Dreiecken die selbe)
		 */
		for(int i = 0; i<24;i++){
			/**
			 * Neue Zufallszahl
			 */
			int r = rm.nextInt(24-i);
			/**
			 * Durch 3, damit 3 die selbe farbe kriegen
			 */
			int j = i/3;
			
			/**
			 * Finde das n�chste noch nicht einer Farbe zugeteilte Dreieck
			 * 
			 * wenn gefunden, dann weise Farbe zu
			 */
			boolean found = false;
			while(!found){
				if(tri[r]==-1){
					tri[r] = j;
					found = true;
				}else{
					r++;
					r %= 24;
				}
			}
		}	
		steps = 0;
		UpdateSolvingList();
	}//#END FillKugelRandom
	
	public String getSphere(){
		String out = "";
		for(int i = 0; i<8;i++){
			out+=con[i];
		}
		for(int i = 0; i<24;i++){
			out+=tri[i];
		}
		out += "n"+steps;
		return out;
	}
	
	
	/**
	 * Dreht die Halbe Kugel um pol im gegenUhrzeigersinn
	 * @param pole
	 * @param steps
	 */
	public void turnKugel(int pole, int steps){
		/**
		 * Aktion f�r Anzahl Schritte durch f�hren
		 */
		for(int i = 0; i<steps; i++){
			switch(pole){
			
			case 0:
				changePol(0,1);
				turn2Ring(0);
				turn3Ring(0);
				break;
			case 1:
				changePol(1,1);
				turn2Ring(1);
				turn3Ring(1);
				break;
			case 2:
				changePol(2,1);
				turn2Ring(2);
				turn3Ring(2);
				break;
			case 3:
				changePol(3,1);
				turn2Ring(3);
				turn3Ring(3);
				break;
			case 4:
				changePol(4,1);
				turn2Ring(4);
				turn3Ring(4);
				break;
			case 5:
				changePol(5,1);
				turn2Ring(5);
				turn3Ring(5);
				break;
			}
			
		}
	}
	
	/**
	 * HilfsFunktion, zum Drehen von Halber Kugel
	 * @param pole : Pol
	 */
	public void turn3Ring(int pole){
		/**
		 * Aktion f�r Anzahl Schritte durch f�hren
		 */
		switch(pole){
		
		case 0:
			 cPol(19, 16, 4, 5, 20, 23, 13, 12);
			break;
		case 1:
			 cPol(16, 17, 8, 9, 21, 20, 1, 0);
			break;
		case 2:
			 cPol(17, 18, 15, 14, 22, 21, 6, 7);
			break;
		case 3:
			 cPol(18, 19, 3, 2, 23, 22, 10, 11);
			break;
		case 4:
			 cPol(3, 0, 15, 12, 8, 11, 4, 7);
			break;
		case 5:
			 cPol(2, 1, 5, 6, 9, 10, 14, 13);
			break;
		}	
	}
	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols
	 * @param p1 : 1. Pol-Position
	 * @param p2 : 2. Pol-Position
	 * @param p3 : 3. Pol-Position
	 * @param p4 : 4. Pol-Position
	 * @param p5 : 5. Pol-Position
	 * @param p6 : 6. Pol-Position
	 * @param p7 : 7. Pol-Position
	 * @param p8 : 8. Pol-Position
	 */
	private void cPol(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8){
		int zs1 = tri[p1];
		int zs2 = tri[p2];
		tri[p1] = tri[p3];
		tri[p2] = tri[p4];
		tri[p3] = tri[p5];
		tri[p4] = tri[p6];
		tri[p5] = tri[p7];
		tri[p6] = tri[p8];
		tri[p7] = zs1;
		tri[p8] = zs2;
		
	}//#END cPol(8)
	
	/**
	 * HilfsFunktion, zum Drehen von Halber Kugel
	 * @param pole : Pol
	 */
	private void turn2Ring(int pole){
		/**
		 * Aktion f�r Anzahl Schritte durch f�hren
		 */
		int temp;
		switch(pole){
		
		case 0:
			temp =con[1];
			con[1] = con[2];
			con[2] = con[6];
			con[6] = con[5];
			con[5] = temp;
			break;
		case 1:
			temp =con[2];
			con[2] = con[3];
			con[3] = con[7];
			con[7] = con[6];
			con[6] = temp;
			break;
		case 2:
			temp =con[3];
			con[3] = con[0];
			con[0] = con[4];
			con[4] = con[7];
			con[7] = temp;
			break;
		case 3:
			temp =con[0];
			con[0] = con[1];
			con[1] = con[5];
			con[5] = con[4];
			con[4] = temp;
			break;
		case 4:
			temp =con[0];
			con[0] = con[3];
			con[3] = con[2];
			con[2] = con[1];
			con[1] = temp;
			break;
		case 5:
			temp =con[4];
			con[4] = con[5];
			con[5] = con[6];
			con[6] = con[7];
			con[7] = temp;
			break;
		}	
	}
	
	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols
	 * @param p1 : 1. Pol-Position
	 * @param p2 : 2. Pol-Position
	 * @param p3 : 3. Pol-Position
	 * @param p4 : 4. Pol-Position
	 */
	private void cPol(int p1, int p2, int p3, int p4){
		int zs = tri[p1];
		tri[p1] = tri[p2];
		tri[p2] = tri[p3];
		tri[p3] = tri[p4];
		tri[p4] = zs;
	}//#END cPol(4)
	
	/**
	 * Funktion welche cPol(int p1, int p2, int p3, int p4) korrekt aufruft, wenn PolNr und Anzahl der Drehungen gegeben werden  
	 * Gegen Uhrzeigersinn
	 * 
	 * @param polNr : welcher Pol gedreht wird (int)(Dreieck id / 4) 
	 * @param anz : um welche Anzahl soll gedreht werden
	 */
	public  void changePol(int polNr, int anz){
		for(int i = 0; i<anz;i++){
			if(polNr==1||polNr==2||polNr==4){
				cPol(4*polNr+3,4*polNr+2,4*polNr+1,4*polNr);
			}else{
				cPol(4*polNr,4*polNr+1,4*polNr+2,4*polNr+3);
			}
			
		}
	}//#END changePol
	
	/**
	 * Alias f�r findCons(int p, int i) wobei i = 0; 
	 * Gibt den con des tri[p] zur�ck
	 * @param p : tri[p] f�r con
	 * @return index von con
	 */
	public int findCons(int p){
		return findCons(p, 0);
	}
	/**
	 * Gibt den i-ten con des tri[p] zur�ck
	 * @param p : tri[p] f�r con
	 * @param i : den i-ten Pol
	 * @return index von con
	 */
	public int findCons(int p, int i){
		switch(i){
			case 0:
				switch(p){
					case 11: case 18: case 15: return 0;
					case 12: case 19: case 3:  return 1;
					case 0:  case 16: case 4:  return 2;
					case 7:  case 17: case 8:  return 3;
					case 10: case 22: case 14: return 4;
					case 13: case 23: case 2:  return 5;
					case 1:  case 20: case 5:  return 6;
					case 6:  case 21: case 9:  return 7;
					default: return -1;
				}//#End switch p
			case 1:
				switch(p){
					case 10: case 12: case 17: return 0;
					case 0:  case 13: case 18: return 1;
					case 1:  case 7:  case 19: return 2;
					case 6:  case 11: case 16: return 3;
					case 9:  case 15: case 21: return 4;
					case 3:  case 14: case 22: return 5;
					case 2:  case 4:  case 23: return 6;
					case 5:  case 8:  case 20: return 7;
					default: return -1;
				}//#End switch p
			case 2:
				switch(p){
					case 9:  case 13: case 16: return 0;
					case 1:  case 14: case 17: return 1;
					case 2:  case 6:  case 18: return 2;
					case 5:  case 10: case 19: return 3;
					case 8:  case 12: case 20: return 4;
					case 0:  case 15: case 21: return 5;
					case 3:  case 7:  case 22: return 6;
					case 4:  case 11: case 23: return 7;
					default: return -1;
				}//#End switch p
			case 3:
				switch(p){
					case 8:  case 14: case 19: return 0;
					case 2:  case 15: case 16: return 1;
					case 3:  case 5:  case 17: return 2;
					case 4:  case 9:  case 18: return 3;
					case 11: case 13: case 23: return 4;
					case 1:  case 12: case 20: return 5;
					case 0:  case 6:  case 21: return 6;
					case 7:  case 10: case 22: return 7;
					default: return -1;
				}//#End switch p
		}//#End switch i
		return -1;
	}
	/**
	 * Von con zu pol
	 * @param con : von welchem con
	 * @param i : der wievielte Pol
	 * @return int des i-ten Pol, -1 == ung�ltiger con, -2, ung�ltiges i (nur 0, 1 oder 2)
	 */
	public int con2pol(int con,int i){
		switch(con){
			case 0:
				switch(i){
					case 0: return 2;
					case 1: return 3;
					case 2: return 4;
					default: return -2;
				}//#End switch i
			case 1:
				switch(i){
					case 0: return 0;
					case 1: return 3;
					case 2: return 4;
					default: return -2;
				}//#End switch i
			case 2:
				switch(i){
					case 0: return 0;
					case 1: return 1;
					case 2: return 4;
					default: return -2;
				}//#End switch i
			case 3:
				switch(i){
					case 0: return 1;
					case 1: return 2;
					case 2: return 4;
					default: return -2;
				}//#End switch i
			case 4:
				switch(i){
					case 0: return 2;
					case 1: return 3;
					case 2: return 5;
					default: return -2;
				}//#End switch i
			case 5:
				switch(i){
					case 0: return 0;
					case 1: return 3;
					case 2: return 5;
					default: return -2;
				}//#End switch i
			case 6:
				switch(i){
					case 0: return 0;
					case 1: return 1;
					case 2: return 5;
					default: return -2;
				}//#End switch i
			case 7:
				switch(i){
					case 0: return 1;
					case 1: return 2;
					case 2: return 5;
					default: return -2;
				}//#End switch i
			default:
				return -1;
		}//#End switch con
		
	}

}