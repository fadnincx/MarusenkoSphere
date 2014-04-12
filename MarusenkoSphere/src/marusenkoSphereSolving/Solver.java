package marusenkoSphereSolving;

import java.util.Arrays;

import marusenkoSphere.Logger;
import marusenkoSphereKugel.Kugel;
/**
 * Solver-Datei 
 * 
 * Ist für das lösen der Kugel verantwortlich
 * 
 */
public class Solver {

	/**
    * Definiert die Kugel, welche gelöst wird wird    
    */
	protected Kugel k;
	protected boolean[] ok = new boolean[24];
	protected Logger l;
	protected Logger d;
	//protected long steps;
	protected long totsteps;
	protected long solSteps;
	protected boolean stopSteps = false;
	private final boolean consLog = false;
	private boolean end = false;
	private String startSphere = "";
	
	/**
	 * Initialisiert das Solver-Objekt
	 * @param k
	 */
	public Solver(Logger l, Logger d){
		this.l = l;
		this.d = d;
		//steps = 0;
		totsteps = 0;
		solSteps = 0;
		stopSteps = false;
	}
	private void consLog(String s){
		if(consLog){
			System.out.println(s);
		}
	}
	private void end(){
		end = true;
	}
	private boolean addSteps(){
		return addSteps(1);
	}
	private boolean addSteps(int i){
		k.steps+=i;
		if(stopSteps&&k.steps>=solSteps){
			return true;
		}else{
			if(k.steps>10000){
				l.log(startSphere);
				l.log(k.getSphere(),"Zuviele Schritte nötig!!!");
				return true;
			}
			//System.out.println(k.steps);
			return false;
		}
		
	}
	public Kugel solve(Kugel kugel, long solSteps){
		this.solSteps = solSteps;
		stopSteps = true;
		return solve(k);
		
	}
	/**
	 * Hauptfunktion welche für das lösen der Kugel verantwortlich ist
	 * @param k : Kugel welche gelöst werden soll
	 * @return : gibt die gelöste Kugel zurück
	 */
	public Kugel solve(Kugel kugel){
		this.k = kugel;
		this.startSphere = k.getSphere();
		//this.steps = 0;
		//Array "ok" mit false füllen
		Arrays.fill(ok, false); 

		/**
		 * Phase 1
		 * 
		 * Geht solange, bis Phase 1 abgeschlossen ist
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
					//d.log(i+" "+findPos(i));
					//Wechle die Positionen von i und dem mit findPos gefundenen Dreieck
					consLog(i+" "+findPos(i));
					Solve1Phase(i,findPos(i));
					if(end){return k;}
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
				//Wenn Pol noch nicht gelösst, löse ihn
				if(!SolveCheck.isPolSolved(i,k)){
					solvePol(i);
					if(end){return k;}
				}
			}
		}		
		
		//Gib die gelöste Kugel zurück
		return k;
		
	}//#END solve(Kugel kugel)
	
	


	
	
	/**
	 * Prüft in Phase1, welche Dreiecke OK sind und welche nicht
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
					 * Wenn tri und con übereinstimmen
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
		/**
		 * Finde den richtigen Connector für p
		 */
		int con = findCon(p);
		consLog("Con: "+p+" - "+con);
		/**
		 * Gehe für die drei möglichen Pole durch
		 */
		for(int i = 0; i<3; i++){
			/**
			 * Finde die entsprechende PolNr
			 */
			int polNr = k.con2pol(con, i);
			consLog("Con-Pol: "+con+" - "+polNr);
			/**
			 * Wenn Phase 1 bei diesem Pol noch nicht abgeschlossen wurde dann kommt er in frage un wird weiter untersucht
			 */
			if(!checkPoleEndPhase1(polNr)&&urPol!=polNr){
				/**
				 * Bei dem Pol wird jede Stelle durch gegeangen
				 */
				consLog(polNr+" Pol nicht gelösst");
				for(int j = 0; j<4; j++){
					/**
					 * Prüfe, ob Position noch nicht korrekt ist und dass die beiden Farben nicht Identisch sind
					 */
					if(!ok[polNr*4+j]&&k.tri[polNr*4+j]!=k.tri[p]){
						/**
						 * Gibt den Tri index zurück, damit zwischen diesen Beiden gewechselt werden kann
						 */
						return polNr*4+j;
					}
				}
			}else{
				consLog(polNr+"Pol gelöst!");
			}
		}
		/**
		 * Gibt -1 zurück, wenn kein Erfolg --> Sollte nicht auftreten
		 */
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
		/**
		 * Fülle Arrays mit Daten aus Kugel
		 */
		for(int i = 0; i<4; i++){
			tris[i]=k.tri[polNr*4+i];
			cons[i]=k.con[k.findCons(polNr*4+i)];
		}
		/**
		 * Sortiere die Arrays
		 * 
		 * Anschliessen prüfen, ob die Array identisch sind
		 * Je nach ergebnis true oder false zurück geben
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
		/**
		 * Gehe alle 8 Connector durch
		 */
		for(int i = 0; i<8; i++){
			/**
			 * Wenn Tri = Con dann gibt den Con Index zurück
			 */
			if(k.con[i]==k.tri[p]){
				return i;
			}
		}
		/**
		 * Wenn keine übereinstimmung gefunden werden konnte dann gibt -1 zurück
		 */
		return -1;
	}
	/**
	 * Notfall Funktion, damit Solver nicht abbricht, sollte nicht aufgerufen werden
	 */
	private void change2Positions(int p1, int p2){
		/**
		 * Überprüft p1 und p2 auf Korrektheit ==> zugross, bzw zuklein werden ausgefiltert
		 */
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23){
			int temp = k.tri[p1];
			k.tri[p1] = k.tri[p2];
			k.tri[p2] = temp;
			//Solve1Phase(p1,p2);
			l.log("Missbrauch von Funktion change2Position");

		}
	}
	private int posPlus(int polNr, int pos){
		if(polNr==1||polNr==2||polNr==4){
			pos+=1;
			return pos%4;
			
		}else{
			 pos+=3;
			 return pos%4;
		}
	}
	private void turnTo(int p, int position){
		int pol = p/4;
		int pos = p%4;
		while(pos!=position){
			k.changePol(pol, 1);
			pos = posPlus(pol,pos);
		}
	}
	private void change1Phase(int pol1,int polRechts,int s){
		k.turnKugel(polRechts, s);
		k.changePol(pol1, 1);
		k.turnKugel(polRechts, 4-s);
	}
	private void Solve1Phase(int p1, int p2){
		/**
		 * Überprüft p1 und p2 auf Korrektheit ==> zugross, bzw zuklein werden ausgefiltert
		 */
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23){
			
			if(p1>p2){
				int temp = p1;
				p1 = p2;
				p2 = temp;
			}
			//p1 kleiner als p2
			
			int pol1 = p1/4;
			int pol2 = p2/4;
				
			int polRechts = change2PolPositionPR(pol1,pol2);
			if(polRechts>=0){

				switch(pol1){
				case 0:
					switch(pol2){
					case 1:
						turnTo(p1,3);
						turnTo(p2,2);
						change1Phase(pol1,polRechts,1);
						break;
					case 2:
						turnTo(p1,3);
						turnTo(p2,2);
						change1Phase(pol1,polRechts,2);
						break;
					case 3:
						turnTo(p1,1);
						turnTo(p2,3);
						change1Phase(pol1,polRechts,1);
						break;
					case 4:
						turnTo(p1,2);
						turnTo(p2,1);
						change1Phase(pol1,polRechts,1);
						break;	
					case 5:
						turnTo(p1,0);
						turnTo(p2,2);
						change1Phase(pol1,polRechts,1);
						break;	
					}//#END switch pol1 --> 0
					break;	
				case 1:
					switch(pol2){
					case 2:
						turnTo(p1,0);
						turnTo(p2,2);
						change1Phase(pol1,polRechts,1);
						break;
					case 3:
						turnTo(p1,0);
						turnTo(p2,1);
						change1Phase(pol1,polRechts,2);
						break;
					case 4:
						turnTo(p1,1);
						turnTo(p2,2);
						change1Phase(pol1,polRechts,1);
						break;	
					case 5:
						turnTo(p1,3);
						turnTo(p2,3);
						change1Phase(pol1,polRechts,1);
						break;	
					}//#END switch pol1 --> 1
					break;	
					
				case 2:
					switch(pol2){
					case 3:
						turnTo(p1,0);
						turnTo(p2,1);
						change1Phase(pol1,polRechts,1);
						break;
					case 4:
						turnTo(p1,1);
						turnTo(p2,3);
						change1Phase(pol1,polRechts,1);
						break;	
					case 5:
						turnTo(p1,3);
						turnTo(p2,0);
						change1Phase(pol1,polRechts,1);
						break;	
				}//#END switch pol1 --> 2
				break;	
				
				
				case 3:
					switch(pol2){
					case 4:
						turnTo(p1,2);
						turnTo(p2,0);
						change1Phase(pol1,polRechts,1);
						break;	
					case 5:
						turnTo(p1,0);
						turnTo(p2,1);
						change1Phase(pol1,polRechts,1);
						break;	
				}//#END switch pol1 --> 3
				break;	
				
				case 4:
					switch(pol2){
					case 5:
						turnTo(p1,3);
						turnTo(p2,0);
						change1Phase(pol1,polRechts,2);

				}//#END switch pol1 --> 3
				break;	
				}//#END switch pol1
				if(addSteps()){end();}
			}else{
				System.out.println("Zukleines PolR "+p1+":"+pol1+" "+p2+":"+pol2);
			}
		}
	}
	

	private void solvePol(int polNr){
		boolean[] pOk = new boolean[4];
		for(int i = 0; i<4; i++){
			if(SolveCheck.isPositionSolved(polNr*4+i,k)){
				pOk[i] = true;
				//System.out.println("Pos "+i+" OK");
			}else{
				pOk[i] = false;
				//System.out.println("Pos "+i+" false");
			}
		}
		//System.out.println("Made Ok");
		while(!SolveCheck.isPolSolved(polNr,k)){
		//	System.out.println("While");
			for(int i = 0; i<4; i++){
				if(!pOk[i]){
					int tri = polNr*4+i;
					int con = k.findCons(tri);
					for(int j = 0; j<4; j++){
						if(k.tri[polNr*4+j]==k.con[con]&&j!=i){
							change2PolPositions(tri, polNr*4+j);
						}
					}
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
	private void change2PolPositions(int p1, int p2){
		change2PolPositions(p1,p2,0);
	}
	private void change2PolPositions(int p1, int p2, int turn){
		int pol = p1/4;
		int polO = change2PolPositionsD2(p1, p2);
		int polR = change2PolPositionPR(pol, polO);
		//Dreifacher nötig
		if(polO != -1){
			change2Pol(pol,polO,polR);
		}else{
			if(isPolGegenUber(p1,p2)){
				solvePolGegenuber(p1,p2);
			}else{
				if(turn<4){
					k.changePol((int)p1/4, 1);
					turn++;
					change2PolPositions(p1,p2,turn);
				}else{
					l.log(k.getSphere(), "Error - Muss sonst lösen - P1: "+p1+"; P2: "+p2);
					/*System.out.println("Anderst");
					System.out.println(p1+" "+p2);
					update(k,kr,30000);*/
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
		if(p1>=p2){
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		
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
		
		
		return -1;
	}
	private boolean isPolGegenUber(int p1, int p2){
		if(p1>p2){
			int temp = p1;
			p1 = p2;
			p2 = temp;
		}
		if(p1+2 == p2&&(k.con[k.findCons(p1)]==k.tri[p2]||k.con[k.findCons(p2)]==k.tri[p1])){
			return true;
		}
		
		return false;
	}
	private void change2Pol(int pol,int polO, int polR){
		k.turnKugel(polR, 3);
		k.changePol(polO, 3);
		k.turnKugel(polR, 1);
		k.changePol(polO, 3);
		k.turnKugel(polR, 3);
		k.changePol(polO, 1);
		k.turnKugel(polR, 1);
		k.changePol(polO, 3);
		k.turnKugel(polR, 3);
		k.changePol(polO, 3);
		k.turnKugel(polR, 1);
		
		k.changePol(pol, 1);
		k.turnKugel(polR, 3);
		k.changePol(polO, 3);
		k.turnKugel(polR, 1);
		k.changePol(polO, 3);
		k.turnKugel(polR, 3);
		k.changePol(polO, 1);
		k.turnKugel(polR, 1);
		k.changePol(polO, 3);
		k.turnKugel(polR, 3);
		k.changePol(polO, 3);
		k.turnKugel(polR, 1);
		k.changePol(pol, 2);
		if(addSteps(24)){end();}
	}
	private void solvePolGegenuber(int p1,int p2){
    	if(p1>p2){
    		int t = p1;
    		p1 = p2;
    		p2 = t;
    	}
    	
    //	update(k,kr,500);
    			    	
    	int p1_old = p1;
		int p2_old = p2;
		int pol = p1/4;
		p2--;
		if(p2<pol*4){
			p2+=4;
		}
		
		
		int polO = change2PolPositionsD2(p1, p2);
		int polR = change2PolPositionPR(pol, polO);
		
		change2Pol(pol,polO,polR);
		if(addSteps()){end();}
		//update(k,kr,500);
		p2 = p2_old;
		p1++;
		if(p1>pol*4+3){
			p1-=4;
		}
		
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		change2Pol(pol,polO,polR);
		if(addSteps()){end();}
    	//update(k,kr,500);
    	
    	p1 = p1_old;
		p2--;
		if(p2<pol*4){
			p2+=4;
		}
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		change2Pol(pol,polO,polR);
		if(addSteps()){end();}
    	//update(k,kr,500);
    	int end = 0;
    	while(!SolveCheck.isPolSolved(pol,k)&&end<4){
    		k.changePol(pol, 1);
    		if(addSteps()){end();}
    		end++;
    	}
		
		
	}
}
