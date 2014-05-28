package marusenkoSphereKugel;

import java.util.ArrayList;
import java.util.Arrays;

import marusenkoSphere.Log;

/**
 * Solver-Datei 
 * 
 * Ist für das lösen der Kugel verantwortlich
 * 
 */
public class Solver {

	/**
    * Definiert die Kugel, welche gelöst wird   
    */
	protected Kugel k;
	protected boolean[] ok = new boolean[24];
	private final boolean consLog = false;
	private boolean end = false;
	private ArrayList<String> solvingWay = new ArrayList<String>();
	
	/**
	 * Initialisiert das Solver-Objekt
	 * @param k
	 */
	public Solver(){}
	
	/**
	 * Gibt den genauen Log in der Konsole aus, wenn entsprechenden Variabel true gesetzt ist
	 * Zum Debuggen --> Genau schauen, wann was macht
	 * 
	 * Zusätzlich wird noch in DebugLog geschrieben
	 * @param s
	 */
	private void consLog(String s){
		if(consLog){
			System.out.println(s);
			Log.DebugLog(s);
		}
	}
	
	/**
	 * Beendet das lösen --> Wenn zuviele Schritte braucht, was heissen muss, dass in Schlaufe fest hängt und Programm einen Fehler hat
	 */
	private void end(){
		end = true;
	}
	
	/**
	 * Fügt eine Kugeln zum Lösungsprozess hinzu
	 * @return : true, wenn Lösen Abgebrochen werden soll
	 */
	
	private boolean addSteps(int pol, int anz, int modus){
		//Erhöhe den Step
		k.step++;
		//Wenn Step zu gross, breche Ab und Gebe Error aus
		if(k.step>1000000){
			Log.ErrorLog(solvingWay.get(0));
			Log.ErrorLog("Zuviele Schritte n�tig!!!");
			return true;
		}
		//Füge Kugel zum Lösungsprozess hinzu
		solvingWay.add(k.getSphere(""+pol+anz+modus));
		
		return false;
	}
	
	
	/**
	 * Hauptfunktion welche für das lösen der Kugel verantwortlich ist
	 * @param k : Kugel welche gelöst werden soll
	 * @return : gibt ArrayList mit Lösungsweg zurück
	 */
	public ArrayList<String> solve(Kugel kugel){
		
		//Nimmt die übergebene Kugel zum lösen
		this.k = kugel;
		
		//Füge die Ausgangskugel als erste Kugel hinzu
		solvingWay.add(k.getSphere());
		
		//Array "ok" mit false füllen
		Arrays.fill(ok, false); 

		/**
		 * Phase 1
		 * 
		 * Geht solange, bis alle Dreiecke auf dem richtigen Pol sind , also Phase 1 abgeschlossen
		 */
		while(!SolveCheck.ArrayIsFullyOk(ok)){
			boolean recheck = true;
			for(int i = 0; i<24; i++){
				//Wenn recheck gefordert checke erneut
				if(recheck){
					recheck = false;
					checkIfP1IsOK();
				}
				//Wenn Position noch nicht gelöst
				if(!ok[i]){
					//Recheck gefordert
					recheck = true;
					//Wechle die Positionen von i und dem mit findPos gefundenen Dreieck
					consLog(i+" "+findPos(i));
					Solve1Phase(i,findPos(i));
					//Abbrechen, falls gefordert
					if(end){return solvingWay;}
				}
			}
		}
		/**
		 * Phase 2
		 * 
		 * Löst die Kugel zu ende
		 */
		consLog("Phase 2");
		//Array ok erneut zurück auf false setzen
		Arrays.fill(ok, false); 
		
		//Solange Kugel nicht gelösst
		while(!SolveCheck.isKugelSolved(k)){
			//Gehe Jeden Pol durch
			for(int i = 0; i<6; i++){
				//Wenn Pol noch nicht gelöst, löse ihn
				if(!SolveCheck.isPolSolved(i,k)){
					solvePol(i);
					//Abbrechen, falls gefordert
					if(end){return solvingWay;}
				}
			}
		}		
		
		//Gib die gelöste Kugel zurück
		return solvingWay;
		
	}//#END solve(Kugel kugel)
	
	
	
	/**
	 * Pr�ft in Phase1, welche Dreiecke OK sind und welche nicht
	 */
	private void checkIfP1IsOK(){
		// Gehe alle Pole durch
		for(int i = 0; i<6; i++){
			/**
			 * int[4] cons ==> Array in dem die 4 Con eines Pols gespeichert werden
			 * int[4] tris ==> Array in dem die 4 Tri eines Pols gespeichert werden
			 * boolean[4] allowCons ==> Ist bei entsprechendem Index true, wenn Con noch nicht verbraucht ist
			 */
			int[] cons = new int[4];
			//Arrays.fill(cons, -1); 
			boolean[] allowCons = new boolean[4];
			int[] tris = new int[4];

			//Array mit daten aus Kugel füllen
			for(int j = 0; j<4; j++){	 
				//Wert von con, Index wird gesucht zu tri Index Pol*4 + Position in Pol
				cons[j] = k.con[k.findCons(i*4+j)];
				//Alle sind zu beginn noch benutzbar
				allowCons[j] = true;
				//Wert von tri bei Index Pol*4 + Position in Pol
				tris[j] = k.tri[i*4+j];
			}
			//Gehe nun die 4 Position eines Pols durch
			for(int j = 0; j<4; j++){
				//boolean set sagt, ob Position schon neu gesetzt wurde
				boolean set = false;
				//Prüfe für alle zur Verfügung stehenden cons ob sie passen
				for(int k = 0; k<4; k++){
					/**
					 * Wenn Position noch nicht neu gesetzt wurde
					 * UND
					 * Wenn Con noch erlaubt ist
					 * UND
					 * Wenn tri und con �bereinstimmen
					 * 
					 * Dann setzte ok bei entsprechender Position = true
					 * Dann setzte allowCons bei entsprechender Position = false
					 * Dann setzte set = true, damit nicht erneut gesetzt wird
					 */
					if(!set&&allowCons[k]&&tris[j]==cons[k]){
						ok[i*4+j] = true;
						allowCons[k] = false;
						set = true;
					}
				}
				
				//Wenn Position jetzt noch nicht neu gesetzt wurde, dann ist Dreieck falsch und somit muss ok = false gesetzt werden
				if(!set){
					ok[i*4+j] = false;
				}
			}
		}
	}
		
	/**
	 * Funktion zum Finden der Position, an welche gewechselt werden muss
	 * @param p : Index welcher an jetziger Stelle falsch ist, welcher also verschoben werden will
	 * @return : gibt Index aus, mit welchem gewechselt werden soll
	 */
	private int findPos(int p){
		int urPol = p/4;
		consLog("UrPol: "+urPol);
		
		//Finde den richtigen Connector für p
		int con = findCon(p);
		consLog("Con: "+p+" - "+con);
		
		//Gehe für die drei müglichen Pole durch
		for(int i = 0; i<3; i++){
			
			//Finde die entsprechende PolNr
			int polNr = k.con2pol(con, i);
			consLog("Con-Pol: "+con+" - "+polNr);

			//Wenn Phase 1 bei diesem Pol noch nicht abgeschlossen wurde dann kommt er in frage und wird weiter untersucht
			if(!checkPoleEndPhase1(polNr)&&urPol!=polNr){
				
				//Bei dem Pol wird jede Stelle durch gegeangen
				consLog(polNr+" Pol nicht gel�st");
				for(int j = 0; j<4; j++){
					
					//Prüfe, ob Position noch nicht korrekt ist und dass die beiden Farben nicht Identisch sind
					if(!ok[polNr*4+j]&&k.tri[polNr*4+j]!=k.tri[p]){
						
						//Gibt den Tri index zurück, damit zwischen diesen Beiden gewechselt werden kann
						return polNr*4+j;
					}
				}
			}else{
				consLog(polNr+"Pol gel�st!");
			}
		}
		
		//Gibt -1 zurück, wenn kein Erfolg --> Sollte nicht auftreten
		return -1;
	}
	
	/**
	 * Prüft, ob Pol mit Phase 1 fertig ist
	 * @param polNr : Pol welcher geprüft werden soll
	 * @return : true wenn fertig, sonst false
	 */
	private boolean checkPoleEndPhase1(int polNr){
		/**
		 * Erstelle Arrays
		 * tris[4] enthält alle Tri eines Pols
		 * cons[4] enthält alle Con eines Pols 
		 */
		int[] tris = new int[4];
		int[] cons = new int[4];
		//Fülle Arrays mit Daten aus Kugel
		for(int i = 0; i<4; i++){
			tris[i]=k.tri[polNr*4+i];
			cons[i]=k.con[k.findCons(polNr*4+i)];
		}
		/**
		 * Sortiere die Arrays
		 * 
		 * Anschliessen prüfen, ob die Array identisch sind
		 * Je nach Ergebnis true oder false zurück geben
		 */
		Arrays.sort(tris);
		Arrays.sort(cons);
		if(Arrays.equals(tris,cons)){
			return true;
		}
		return false;
	}
	
	/**
	 * Connector in entsprechender Farbe suchen
	 * @param p : Index von tri entsprechender Farbe
	 * @return : gibt Connector Index aus bei Erfolg, bei Misserfolg -1
	 */
	private int findCon(int p){
		
		//Gehe alle 8 Connector durch
		for(int i = 0; i<8; i++){
			
			//Wenn Tri = Con dann gibt den Con Index zurü�ck
			if(k.con[i]==k.tri[p]){
				return i;
			}
		}
		
		//Wenn keine übereinstimmung gefunden werden konnte dann gibt -1 zurück
		return -1;
	}
	
	/**
	 * Notfall Funktion, damit Solver nicht abbricht, sollte nicht aufgerufen werden
	 * 
	 * Gibt einen Error aus! 
	 */
	private void change2Positions(int p1, int p2){
		
		//überprüft p1 und p2 auf Korrektheit ==> zugross, bzw zuklein werden ausgefiltert
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23){
			int temp = k.tri[p1];
			k.tri[p1] = k.tri[p2];
			k.tri[p2] = temp;
			
			Log.ErrorLog("Missbrauch von Funktion change2Position");
		}
	}
	/**
	 * Korrektur Funktion zum Drehen eines Pols, damit alle Pole mit einer Funktion in eine Einheitliche Richtung gedreht werden können
	 * @param polNr
	 * @param pos
	 * @return
	 */
	private int posPlus(int polNr, int pos){
		if(polNr==1||polNr==2||polNr==4){
			pos+=1;
			return pos%4;
			
		}else{
			 pos+=3;
			 return pos%4;
		}
	}
	/**
	 * Dreht einen Pol inklusive Korrektur
	 * @param p
	 * @param position
	 */
	private void turnTo(int p, int position){
		int pol = p/4;
		int pos = p%4;
		while(pos!=position){
			k.changePol(pol, 1);
			addSteps(pol,1,1);
			pos = posPlus(pol,pos);
		}
	}
	
	/**
	 * Wechsle 2 Stücke in Phase 1 und füge Schritte dem Lösungsweg hinzu;
	 * @param pol1
	 * @param polRechts
	 * @param s
	 */
	private void change1Phase(int pol1,int polRechts,int s){
		k.turnKugel(polRechts, s);
		addSteps(polRechts, s, 3);
		k.changePol(pol1, 1);
		addSteps(pol1, 1, 1);
		k.turnKugel(polRechts, 4-s);
		addSteps(polRechts, 4-s, 3);
	}
	
	/**
	 * Nimmt das wechseln von 2 Positionen für Phase 1 vor
	 * @param p1
	 * @param p2
	 */
	private void Solve1Phase(int p1, int p2){
		
		//überprüft p1 und p2 auf Korrektheit ==> zugross, bzw zuklein werden ausgefiltert
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23){
			
			//Wechsle so, dass p1 kleiner als p2
			if(p1>p2){
				int temp = p1;
				p1 = p2;
				p2 = temp;
			}
			//p1 kleiner als p2
			
			//Den Pol von p1 und p2 in Variabel schreiben
			int pol1 = p1/4;
			int pol2 = p2/4;
				
			//Finde den Rechten Pol heraus
			int polRechts = change2PolPositionPR(pol1,pol2);
			//Wenn gefunden
			if(polRechts>=0){
				//Variabel zur End Positionsbestimmung festlegen und mit Werten füllen, so dass nichts geändert wird 
				int pos1 = p1%4;
				int pos2 = p2%4;
				int opt1 = 1;
				
				//Individuell nach Pol diese Variablen �ndern
				switch(pol1){
				case 0:
					switch(pol2){
					case 1: pos1 = 3; pos2 = 2; opt1 = 1; break;
					case 2: pos1 = 3; pos2 = 2; opt1 = 2; break;
					case 3: pos1 = 1; pos2 = 3; opt1 = 1; break;
					case 4: pos1 = 2; pos2 = 1; opt1 = 1; break;	
					case 5: pos1 = 0; pos2 = 2; opt1 = 1; break;	
					}//#END switch pol1 --> 0
					break;	
				case 1:
					switch(pol2){
					case 2: pos1 = 0; pos2 = 2; opt1 = 1; break;
					case 3: pos1 = 0; pos2 = 1; opt1 = 2; break;
					case 4: pos1 = 1; pos2 = 2; opt1 = 1; break;	
					case 5: pos1 = 3; pos2 = 3; opt1 = 1; break;	
					}//#END switch pol1 --> 1
					break;	
				case 2:
					switch(pol2){
					case 3: pos1 = 0; pos2 = 1; opt1 = 1; break;
					case 4: pos1 = 1; pos2 = 3; opt1 = 1; break;	
					case 5: pos1 = 3; pos2 = 0; opt1 = 1; break;	
					}//#END switch pol1 --> 2
					break;	
				case 3:
					switch(pol2){
					case 4: pos1 = 2; pos2 = 0; opt1 = 1; break;	
					case 5: pos1 = 0; pos2 = 1; opt1 = 1; break;	
					}//#END switch pol1 --> 3
					break;	
				case 4:
					switch(pol2){
					case 5:
						pos1 = 3; pos2 = 0; opt1 = 2;
					}//#END switch pol1 --> 3
				break;	
				}//#END switch pol1
				
				//Pol 1 zu korrekter Position drehen 			
				turnTo(p1,pos1);
				
				//Pol 2 zu korrekter Position drehen
				turnTo(p2,pos2);
				
				//Austauschen
				change1Phase(pol1,polRechts,opt1);
			}else{
				consLog("Zukleines PolR "+p1+":"+pol1+" "+p2+":"+pol2);
			}
		}
	}
	
	/**
	 * Funktion zum lösen eines Pols
	 * @param polNr
	 */
	private void solvePol(int polNr){
		
		//Gehe für jede Position durch, ob gelöst oder nicht und speichere in Variable
		boolean[] pOk = new boolean[4];
		for(int i = 0; i<4; i++){
			if(SolveCheck.isPositionSolved(polNr*4+i,k)){
				pOk[i] = true;
			}else{
				pOk[i] = false;
			}
		}
		//Solange Pol nicht gelöst, noch einen Anlauf wagen 
		while(!SolveCheck.isPolSolved(polNr,k)){
			//Gehe jede Position durch
			for(int i = 0; i<4; i++){
				//Wenn noch nicht korrekt
				if(!pOk[i]){
					//Finde den Farbwert der Position heraus
					int tri = polNr*4+i;
					//Finde das dazu gehörige Verbindungsteil heraus
					int con = k.findCons(tri);
					//Finde Dreieck mit welchem getauscht werden muss und tausche dann
					for(int j = 0; j<4; j++){
						if(k.tri[polNr*4+j]==k.con[con]&&j!=i){
							change2PolPositions(tri, polNr*4+j);
						}
					}
					//Erneut prüfen, ob Pol gelöst
					for(int j = 0; j<4; j++){
						if(SolveCheck.isPositionSolved(polNr*4+j,k)){
							pOk[j] = true;
						}else{
							pOk[j] = false;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Tauscht 2 Positionen in der 2. Phase
	 * @param p1
	 * @param p2
	 */
	private void change2PolPositions(int p1, int p2){
		change2PolPositions(p1,p2,0);
	}
	/**
	 * Tauscht 2 Positionen in der 2. Phase inklusive Runde
	 * @param p1
	 * @param p2
	 * @param turn
	 */
	private void change2PolPositions(int p1, int p2, int turn){
		//Pol in Variable schreiben
		int pol = p1/4;
		//Pol Oben ermitteln
		int polO = change2PolPositionsD2(p1, p2);
		//Pol Rechts ermitteln
		int polR = change2PolPositionPR(pol, polO);
		//Sofern dies geht wechsle gemäss Schema
		if(polO != -1){
			change2Pol(pol,polO,polR);	
		}else{
			//Löst Speziallfälle --> 
			
			//Prüft ob übers Kreuz gelöst werden muss --> dann tue das
			if(isPolGegenUber(p1,p2)){
				solvePolGegenuber(p1,p2);
			}else{
				//Sonst Pol einmal drehen und dann erneut versuchen
				if(turn<4){
					k.changePol(p1/4, 1);
					turn++;
					change2PolPositions(p1,p2,turn);
				}else{
					//Wenns definitiv nicht geht Error ausgeben
					Log.ErrorLog(k.getSphere()+" \n Error - Muss sonst l�sen - P1: "+p1+"; P2: "+p2);
					change2Positions(p1, p2);
				}
			}
		}
	}
	
	/**
	 * Gibt Pol oben von 2 p's an
	 * @param p1 : P1
	 * @param p2 : P2
	 * @return
	 */
	private int change2PolPositionsD2(int p1, int p2){
		//Wechselt so dass p1 kleiner als p2
		if(p1>=p2){
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		//Gibt den Wert zurück
		if((p1==12&&p2==13)||(p1==16&&p2==19)||(p1==4&&p2==5)||(p1==20&&p2==23)){
			return 0;
		}else
		if((p1==0&&p2==1)||(p1==8&&p2==9)||(p1==16&&p2==17)||(p1==20&&p2==21)){
			return 1;
		}else
		if((p1==6&&p2==7)||(p1==17&&p2==18)||(p1==14&&p2==15)||(p1==21&&p2==22)){
			return 2;
		}else
		if((p1==10&&p2==11)||(p1==18&&p2==19)||(p1==2&&p2==3)||(p1==22&&p2==23)){
			return 3;
		}else
		if((p1==0&&p2==3)||(p1==4&&p2==7)||(p1==8&&p2==11)||(p1==12&&p2==15)){
			return 4;
		}else
		if((p1==13&&p2==14)||(p1==1&&p2==2)||(p1==5&&p2==6)||(p1==9&&p2==10)){
			return 5;
		}
		
		//Bei einem Fehler...
		return -1;
	}
	/**
	 * Gibt den Pol Rechts von pol1 an
	 * @param pol1: Pol
	 * @param pol2: Pol oben
	 * @return : Rechter Pol
	 */
	private int change2PolPositionPR(int pol1, int pol2){
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
	/**
	 * Prüfen ob übers Kreuz gelöst werden muss
	 * @param p1
	 * @param p2
	 * @return
	 */
	private boolean isPolGegenUber(int p1, int p2){
		//Wechsle so das p1 kleiner als p2
		if(p1>p2){
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		//Prüfe ob das so ist und es die Lösung bringt...
		if(p1+2 == p2&&(k.con[k.findCons(p1)]==k.tri[p2]||k.con[k.findCons(p2)]==k.tri[p1])){
			return true;
		}
		//Ansonsten gibt false zurück
		return false;
	}
	
	/**
	 * Macht die Wechselfunktion der Pole durch gemäss Schema
	 * @param pol : pol, auf welchem gewechselt wird
	 * @param polO : pol Oben davon
	 * @param polR : pol Rechts davon
	 */
	private void change2Pol(int pol,int polO, int polR){
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 1);
		if(addSteps(polO, 1, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		
		k.changePol(pol, 1);
		if(addSteps(pol, 1, 1)){end();}
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 1);
		if(addSteps(polO, 1, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		k.changePol(pol, 2);
		if(addSteps(pol, 2, 1)){end();}
	}
	
	/**
	 * Löse übers Kreuz
	 * @param p1
	 * @param p2
	 */
	private void solvePolGegenuber(int p1,int p2){
		//Wechseln so das p1 kleiner als p2
    	if(p1>p2){
    		int t = p1;
    		p1 = p2;
    		p2 = t;
    	}
		    
    	//Speichern der Alten Positionen
    	int p1_old = p1;
		int p2_old = p2;
		//Pol in Variabel schreiben
		int pol = p1/4;
		p2--;
		//p2ändern so das gelöst werden kann
		if(p2<pol*4){
			p2+=4;
		}
		
		//Pol Oben und Rechts finden
		int polO = change2PolPositionsD2(p1, p2);
		int polR = change2PolPositionPR(pol, polO);
		
		//Wechsle beide jetzt
		change2Pol(pol,polO,polR);
		

		//p2 zurück setzten, p1 verschieben
		p2 = p2_old;
		p1++;
		if(p1>pol*4+3){
			p1-=4;
		}
		//Pol Oben und Rechts finden
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		//Wechsle erneut
		change2Pol(pol,polO,polR);

    	
		//p1 zurücksetzten und p2 verschieben
    	p1 = p1_old;
		p2--;
		if(p2<pol*4){
			p2+=4;
		}
		//Pol Oben und Rechts finden
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		//Zum letzten Mal wechseln
		change2Pol(pol,polO,polR);
		
    	int end = 0;
    	//Prüfe ob Pol gelöst ist ansonsten drehe Pol bis gelöst
    	while(!SolveCheck.isPolSolved(pol,k)&&end<4){
    		k.changePol(pol, 1);
    		if(addSteps(pol, 1, 1)){end();}
    		end++;
    	}
		
		
	}
}
