package marusenkoSphereKugel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import marusenkoSphere.Settings;
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
	
	public int level = 5;
	
	/**
	 * Initialisiert den AnimationsManager und füllt die Kugel mit einer gelösten Kugel, ohne diese zu lösen
	 */
	public Kugel(){
		
		//Initialisieren des Animationsmanager
		animationManager = new AnimationsManager();
		
		//gelöste Kugel füllen
		setSphereToString("61023457054105722736143602615734n0n000");
		
	}
	
	/**
	 * Setzte die Kugel gemäss eines Debugstrings
	 * @param solving : ob Kugel gelöst werden soll
	 */
	public void fillKugelFromDebugString(String s,boolean solving){
		
		//Führe die Funktion ohne lösen der Kugel durch
		setSphereToString(s);
		
		//Wenn gelöst werden soll
		if(solving){
			
			//Löse die Kugel
			solveSphere();
			
		//Sonst lösche gesammte solvingList und füge nur aktuelle Kugel hinzu
		}else{
			
			step = 0;
			solvingList.clear();
			solvingList.add(getSphere("000"));
			
		}
		
	}
	
	/**
	 * Übernimmt die Kugel aus dem Editor
	 */
	public void sphereFromEditor(int level){
		//System.out.println(getSphere("000"));
		this.level = level;
		step = 0;
		fillKugelFromDebugString(getSphere("000"),true);
		
	}
	public void setToLevel(int i){
		switch(i){
		case 1:
			setSphereToString("03033030303030300303030330300303n0n000");
			break;
		case 2:
			setSphereToString("00000000111122225555444433337777n0n000");
			break;
		case 3:
			setSphereToString("33332222322332233223322311110000n0n000");
			break;
		case 4:
			setSphereToString("71347134331133444477117734713471n0n000");
			break;
		case 5:
			setSphereToString("56437201402640133175627543560172n0n000");
			break;
		default:
			System.out.println("Nicht existierendes Level");
		}
	}

	/**
	 * Übernimmt die Kugel aus der ArrayList zur gegebenen Step
	 */
	public void setKugelToStateFromList(int step){
		
		setSphereToString(solvingList.get(step));
		
	}
	
	/**
	 * Setzt den aktuellen Kugelstatus zum gegebenen String s
	 */
	private void setSphereToString(String s){
		
		//Trim den String um allfällige leerzeichen ab zu trennen
		s = s.trim();
		
		//Teile den String bei "n" auf ("n" ist das Trennzeichen im String)
		String[] sp = s.split("n");
		
		//Prüfe ob String der erwarteten Form entspricht, ansonsten ErrorLog
		//3 Sedimente, 1. 32 Zeichen Kugel, 2. Step, 3. 3Stelliger Drehcode 
		if(sp[0].length()==32&&sp.length==3&&sp[2].length()==3){
			
			//Gib dem AnimationsManager die neue Drehung
			animationManager.setNewDrehung(s,solvingList);
			
			//Setzte den aktuellen Status gemäss String
			for(int i = 0; i<8;i++){
				
				con[i]=Integer.parseInt(s.substring(i, i+1));
				
			}
			
			for(int i = 0; i<24;i++){
				
				tri[i]=Integer.parseInt(s.substring(i+8, i+9));
				
			}
			
			//Den Step aktuallisieren
			step = Integer.parseInt(sp[1]);
			
			//Wenn gewünscht, den aktuellen Kugel Code ausgeben
			if(Settings.PRINTSPHERECODE){
				
				System.out.println(getSphere("000"));
				
			}
			
		//Wenn der String nicht die erwartete Form hat
		}else{
			
			//Ausgeben, dass String nicht in ordnung ist, jedoch nichts weiters
			System.out.println("String ist nicht wie erwartet formatiert oder inkorrekt: '"+s+"'");
			
		}
		
	}
	
	/**
	 * Funktion zum zufälligen Füllen der Kugel mit Werten
	 */
	public void fillRandom(){
		
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
		
		//Der Step wird noch auf 0 gesetzt
		step = 0;
		
		//Löse die Kugel
		solveSphere();
	
	}
	
	
	/**
	 * Damit andere Klassen den aktuellen Step abfragen können
	 */
	public int getStep(){
		
		return step;
		
	}
	
	/**
	 * Gibt die länge der Lösungs-ArrayList zurück
	 */
	public int getSolvingListSize(){
		
		return solvingList.size();
		
	}
	
	/**
	 * Methode, welche die Kugel als String ausgibt
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
	 * Methode, welche das lösen aufruft und Lösung speichert
	 */
	private void solveSphere(){
		if(level==2){
			// Lösung des Lösens in SolvingList speichern
			solvingList = new Solver().solve2(this);
		}else if(level==3){
			// Lösung des Lösens in SolvingList speichern
			solvingList = new Solver().solve3(this);
		}else{
			// Lösung des Lösens in SolvingList speichern
			solvingList = new Solver().solve(this);
		}
		//Setzet die Kugel zurück in den Anfangsstatus
		setSphereToString(solvingList.get(0));
		
	}
	
	protected String returnSphereToFile(){
		String out = "1";
		for(int i = 0; i<24;i++){
			out=out+";"+tri[i];
		}
		for(int i = 0; i<8;i++){
			out=out+";"+con[i];
		}
		out=out+";"+step;
		int anz = solvingList.size();
		out=out+";"+anz;
		for(int i = 0; i<anz;i++){
			out=out+";"+solvingList.get(i);
		}
		return out;
	}
	protected boolean recieveSphereFromFile(String s){
		String[] sE = s.split(";");
		if(Integer.parseInt(sE[0])==1){
			for(int i = 0; i<24;i++){
				tri[i]=Integer.parseInt(sE[i+1]);
			}
			for(int i = 0; i<8;i++){
				con[i]=Integer.parseInt(sE[i+25]);
			}
			step = Integer.parseInt(sE[33]);
			int anz = Integer.parseInt(sE[34]);
			solvingList.clear();
			for(int i = 0; i<anz;i++){
				solvingList.add(sE[i+35]);
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	/**********************************************************************************************************************************************
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 *                                                                                                                                            *
	 * Mit den folgenden Funktionen kann die Kugel gedreht werden.                                                                                *
	 *                                                                                                                                            *
	 * Diese Funktionen werden nur beim Lösen aufgerufen                                                                                          *
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
	 */
	private void turn3Ring(int pole){
		
		//Dreht den "3. Ring" 
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
	 */
	private void turn2Ring(int pole){
		
		//Dreht den "2. Ring"
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
	 * Hilfsfunktion zum Drehen der Kugel --> Führt die Drehung des "3.Ring" aus
	 */
	private void change8Tri(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8){
		
		//Variablen werden paar Weise verschoben
		int zs1 = tri[p1];		int zs2 = tri[p2];
		tri[p1] = tri[p3];		tri[p2] = tri[p4];
		tri[p3] = tri[p5];		tri[p4] = tri[p6];
		tri[p5] = tri[p7];		tri[p6] = tri[p8];
		tri[p7] = zs1;			tri[p8] = zs2;
		
	}
	
	/**
	 * Hilfsfunktion zum Drehen der Kugel --> Führt die Drehung des "2.Ring" aus
	 */
	private void change4Con(int p1, int p2, int p3, int p4){
		
		//Variablen werden einfach verschoben
		int zs =con[p1];
		con[p1] = con[p2];
		con[p2] = con[p3];
		con[p3] = con[p4];
		con[p4] = zs;
		
	}

	/**
	 * Hilfsfunktion zum drehen eines Pols
	 */
	private void change4Tri(int p1, int p2, int p3, int p4){
		
		//Variablen werden einfach verschoben
		int zs = tri[p1];
		tri[p1] = tri[p2];
		tri[p2] = tri[p3];
		tri[p3] = tri[p4];
		tri[p4] = zs;
		
	}
	
	/**
	 * Dreht den gegebenen Pol im Gegenuhrzeigersinn um die gegebenen Anzahl schritte
	 */
	protected  void changePol(int polNr, int anz){
		
		//Für die gegebene Anzahl (1 = Gegenuhrzeigersinn, 3 = Uhrzeigersinn
		for(int i = 0; i<anz;i++){		
		
			//Korrigiere einen Fehler bei der Speicherung --> Pol 1, 2, 4 müssen anderst aufgerufen werden
			if((polNr==1||polNr==2||polNr==4)){
				
				//Rufe Hilfsfunktion zum drehen auf
				change4Tri(4*polNr+3,4*polNr+2,4*polNr+1,4*polNr);
			
			}else{
				
				//Rufe Hilfsfunktion zum drehen auf
				change4Tri(4*polNr,4*polNr+1,4*polNr+2,4*polNr+3);
			
			}
			
		}
		
	}
	
}