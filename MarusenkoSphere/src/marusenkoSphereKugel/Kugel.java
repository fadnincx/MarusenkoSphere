package marusenkoSphereKugel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * Kugel-Datei
 * 
 * Kugel-Objekt, beinhaltet die ganze Kugel für das ganze Programm, bringt den Zustand der Kugel jeweils von einem Objekt in ein nächstes
 * 
 */
public class Kugel{
	
	//Array mit dem aktuellen Status von allen Dreiecken (Triangles)
	public int[] tri = new int[24];
	
	//Array mit dem aktuellen Status von allen Verbindungsstücken (Connectors)
	public int[] con = new int[8];
	
	//ArrayList, welche den gesamten Lösungsweg beinhaltet
	private ArrayList<String> solvingList = new ArrayList<String>(); //Arraylist mit dem Lösungsweg
	
	//Variable mit der Info, bei welcher Position im SolvingArray wir stehen (NICHT INDEX!!!)
	protected int step = 0;
	
	//Der AnimationsManager für die Animationen
	public AnimationsManager animationManager;
	
	/**
	 * Initialisiert den AnimationsManager und füllt die Kugel mit einer gelösten Kugel, ohne diese zu lösen
	 */
	public Kugel(){
		
		//Initialisieren des Animationsmanager
		animationManager = new AnimationsManager();
		
		//gelöste Kugel füllen
		SetSphereToString("61023457054105722736143602615734n0n000");
	}
	
	/**
	 * Setzte die Kugel gemäss eines Debugstrings
	 * @param solving : ob Kugel gelöst werden soll
	 */
	public void FillKugelFromDebugString(String s,boolean solving){
		
		//Führe die Funktion ohne lösen der Kugel durch
		SetSphereToString(s);
		
		//Wenn gelöst werden soll
		if(solving){
			
			//Löse die Kugel
			UpdateSolvingList();
			
		//Sonst lösche gesammte solvingList und füge nur aktuelle Kugel hinzu
		}else{
			solvingList.clear();
			solvingList.add(getSphere()+"n0n000");
		}
	}
	
	/**
	 * Übernimmt die Kugel aus dem Editor
	 */
	public void FillKugelFromEditor(){
		resetStep();
		FillKugelFromDebugString(getSphere(),true);
	}

	/**
	 * Übernimmt die Kugel aus der ArrayList zur gegebenen Step
	 */
	public void setKugelToStateFromList(int step){
		SetSphereToString(solvingList.get(step));
	}
	
	/**
	 * Setzt den aktuellen Kugelstatus zum gegebenen String s
	 */
	private void SetSphereToString(String s){
		
		//Trim den String um allfällige leerzeichen ab zu trennen
		s = s.trim();
		
		//Teile den String bei "n" auf ("n" ist das Trennzeichen im String)
		String[] sp = s.split("n");
		
		//Prüfe ob String der erwarteten Form entspricht, ansonsten ErrorLog
		//3 Sedimente, 1. 32 Zeichen Kugel, 2. Step, 3. 3Stelliger Drehcode 
		if(sp[0].length()==32&&sp.length==3&&sp[2].length()==3){
			
			//Setzte den aktuellen Status gemäss String
			for(int i = 0; i<8;i++){
				con[i]=Integer.parseInt(s.substring(i, i+1));
			}
			for(int i = 0; i<24;i++){
				tri[i]=Integer.parseInt(s.substring(i+8, i+9));
			}
			
			//Gib dem AnimationsManager die neue Drehung
			animationManager.setNewDrehung(s,solvingList);
			
			//Den Step aktuallisieren
			step = Integer.parseInt(sp[1]);
		
			
		//Wenn der String nicht die erwartete Form hat
		}else{
			
			//Ausgeben, dass String nicht in ordnung ist, jedoch nichts weiters
			System.out.println("String ist nicht wie erwartet formatiert oder inkorrekt: '"+s+"'");
		}
	}

	public void mixRandom(){
		Random rml = new Random();
		Random rmPol = new Random();
		Random rmMode = new Random();
		Random rmStep = new Random();
		int l = 1;
		
		while(l!=0){
			//Sehr lange Mischen
			l = rml.nextInt(10000);
			
			int pol = rmPol.nextInt(6);//(0-5)
			int mode = rmMode.nextInt(2);//(0-1)
			int step = rmStep.nextInt(4);//(0-3)
			
			
			switch(mode){
			case 0:
				changePol(pol, step);
				break;
			case 1:
				turnKugel(pol, step);
				break;
			}
			
			
		}
		
	}
	/**
	 * Funktion zum zufälligen Füllen der Kugel mit realistischen Werten
	 */
	public void FillRandom(){
		
		//Objekt rm als Zufallsobjekt, aus welchem dann die Zusatzzahl generiert wird
		Random rm = new Random();
		
		//Alle Verbingungsstücke werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben
		Arrays.fill(con, -1);
		
		//Diesen 8 Verbingungsstücken wird eine Farbe zu geteilt
		for(int i = 0; i<8;i++){
			
			//Neue Zufallszahl
			int r = rm.nextInt(8-i);
			
			//Finde das nächste noch nicht einer Farbe zugeteilte Verbindungsstück --> wenn gefunden, dann weise Farbe zu
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
		
		//Alle Dreiecke werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben 
		Arrays.fill(tri, -1);
		
		//Den 24 Dreiecken wird eine Farbe zugeteilt (jeweils 3 Dreiecken die selbe)
		for(int i = 0; i<24;i++){
			
			// Neue Zufallszahl
			int r = rm.nextInt(24-i);
			
			// Durch 3, damit 3 die selbe farbe kriegen
			int j = i/3;
			
			//Finde das nächste noch nicht einer Farbe zugeteilte Dreieck --> wenn gefunden, dann weise Farbe zu
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
		step = 0;
		
		//Löse die Kugel
		UpdateSolvingList();
	
	}//#END FillKugelRandom
	
	
	/**
	 * Getter-Methode für den Step
	 * @return
	 */
	public int getStep(){
		return this.step;
	}
	/**
	 * Getter-Methode für die Maximale Anzahl an Schritten
	 */
	public int getMaxStep(){
		return solvingList.size()-1;
	}
	/**
	 * Resetet den Step zu 0 --> für nach dem Kugel manuell eingegeben wurde
	 */
	public void resetStep(){
		this.step = 0;
	}
	/**
	 * Methode, welche die Kugel als String ausgibt
	 * @return
	 */
	public String getSphere(){
		return getSphere("000");
	}
	public int getSolvingListSize(){
		return solvingList.size();
	}
	/**
	 * Methode, welche die Kugel als String ausgibt mit zusätzlicher Drehung
	 * @return
	 */
	protected String getSphere(String dreh){
		//Output = leer
		String out = "";
		//Füge die Zahlenwerte der Verbindungsstücke ein
		for(int i = 0; i<8;i++){
			out+=con[i];
		}
		//Füge die Zahlenwerte der Pole ein
		for(int i = 0; i<24;i++){
			out+=tri[i];
		}
		//Füge "n" als Trennzeichen hinzu, sowie den Step
		out += "n"+step+"n"+dreh;
		return out;
	}
	
	/**
	 * Methode zum Updaten der Liste beim Lösen, also die Methode, welche das Lösen startet und das Ergebnis verwaltet.
	 */
	private void UpdateSolvingList(){
		//Die SolvingList ist das Resultat eines neuen Objekts Solver und dessen Methode solve mit dieser Kugel als Argument 
		this.solvingList = new Solver().solve(this);
		//Setzte die Kugel in den Zustand zu beginn
		//System.out.println(SolvingList.toString());
		SetSphereToString(solvingList.get(0));
		//System.out.println(getSphere());
	}
	
	
	/**********************************************************************************************************************************************
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 * Die Folgenden Funktionen werden für das Lösen der Kugel benötigt                                                                           *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 **********************************************************************************************************************************************/
	
	
	
	
	/**
	 * Dreht die Halbe Kugel um den gegebenen Pol im gegenUhrzeigersinn
	 * @param pole
	 * @param steps
	 */
	protected void turnKugel(int pole, int steps){
		//Aktion für Anzahl Schritte durch führen --> 3x im GegenUhrzeigersinn = 1x im Uhrzeigersinn 
		
		for(int i = 0; i<steps; i++){
			//Dreht den Pol
			changePol(pole,1);
			//Dreht die Verbindungsstücke
			turn2Ring(pole);
			//Dreht die Pole am üquator
			turn3Ring(pole);
		}
	}
	
	
	/**
	 * HilfsFunktion, zum Drehen von Halber Kugel
	 * @param pole : Pol
	 */
	private void turn3Ring(int pole){
		//Die Tri's in der Unten gegebenen Reihenfolge mit der cPol funktion durch wechseln 
		switch(pole){	
		case 0: change8Tri(19, 16, 4, 5, 20, 23, 13, 12); break;
		case 1: change8Tri(16, 17, 8, 9, 21, 20, 1, 0);   break;
		case 2: change8Tri(17, 18, 15, 14, 22, 21, 6, 7); break;
		case 3: change8Tri(18, 19, 3, 2, 23, 22, 10, 11); break;
		case 4: change8Tri(3, 0, 15, 12, 8, 11, 4, 7);    break;
		case 5: change8Tri(2, 1, 5, 6, 9, 10, 14, 13);    break;
		}	
	}
	
	/**
	 * HilfsFunktion, zum Drehen von Halber Kugel
	 * @param pole : Pol
	 */
	private void turn2Ring(int pole){
		switch(pole){
		case 0: change4Con(1,2,6,5); break;
		case 1: change4Con(2,3,7,6); break;
		case 2: change4Con(3,0,4,7); break;
		case 3: change4Con(0,1,5,4); break;
		case 4: change4Con(0,3,2,1); break;
		case 5: change4Con(4,5,6,7); break;
		}	
	}
	
	
	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols --> Hilfsfunktion von turn3Ring
	 * @param p1 : 1. Pol-Position
	 * @param p2 : 2. Pol-Position
	 * @param p3 : 3. Pol-Position
	 * @param p4 : 4. Pol-Position
	 * @param p5 : 5. Pol-Position
	 * @param p6 : 6. Pol-Position
	 * @param p7 : 7. Pol-Position
	 * @param p8 : 8. Pol-Position
	 */
	private void change8Tri(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8){
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
		
	}
	
	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols --> Hilfsfunktion von turn2Ring
	 * @param p1 : 1. Pol-Position
	 * @param p2 : 2. Pol-Position
	 * @param p3 : 3. Pol-Position
	 * @param p4 : 4. Pol-Position
	 */
	private void change4Con(int p1, int p2, int p3, int p4){
		int zs =con[p1];
		con[p1] = con[p2];
		con[p2] = con[p3];
		con[p3] = con[p4];
		con[p4] = zs;
	}

	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols
	 * @param p1 : 1. Pol-Position
	 * @param p2 : 2. Pol-Position
	 * @param p3 : 3. Pol-Position
	 * @param p4 : 4. Pol-Position
	 */
	private void change4Tri(int p1, int p2, int p3, int p4){
		int zs = tri[p1];
		tri[p1] = tri[p2];
		tri[p2] = tri[p3];
		tri[p3] = tri[p4];
		tri[p4] = zs;
	}
	
	/**
	 * Funktion welche change4Tri(int p1, int p2, int p3, int p4) korrekt aufruft, wenn PolNr und Anzahl der Drehungen gegeben werden  
	 * Gegen Uhrzeigersinn mit Korrektur
	 * 
	 * @param polNr : welcher Pol gedreht wird (int)(Dreieck id / 4) 
	 * @param anz : um welche Anzahl soll gedreht werden
	 */
	protected  void changePol(int polNr, int anz){
		for(int i = 0; i<anz;i++){			
			if((polNr==1||polNr==2||polNr==4)){
				change4Tri(4*polNr+3,4*polNr+2,4*polNr+1,4*polNr);
			}else{
				change4Tri(4*polNr,4*polNr+1,4*polNr+2,4*polNr+3);
			}
			
		}
	}
	
	
	/**
	 * Alias für findCons(int p, int i) wobei i = 0; 
	 * Gibt den con des tri[p] zurück
	 * @param p : tri[p] für con
	 * @return index von con
	 */
	protected int findCons(int p){
		return findCons(p, 0);
	}
	
	/**
	 * Gibt den i-ten con des tri[p] zurück --> Wird benötigt, für die Grob Sortierung
	 * @param p : tri[p] für con
	 * @param i : den i-ten Pol
	 * @return index von con
	 */
	protected int findCons(int p, int i){
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
	 * @return int des i-ten Pol, -1 == ungültiger con, -2, ungütiges i (nur 0, 1 oder 2)
	 */
	protected int con2pol(int con,int i){
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
	protected int[] conToPos(int con){
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
	protected int gegenPol(int pol){
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
}