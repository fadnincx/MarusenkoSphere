package marusenkoSphereSolving;

import java.util.Arrays;

import marusenkoSphere.Logger;
import marusenkoSphereKugel.Kugel;
import marusenkoSphereRender.KugelRendern;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 * Solver-Datei 
 * 
 * Ist f�r das l�sen der Kugel verantwortlich
 * 
 */
public class Solver {

	/**
    * Definiert die Kugel, welche gel�st wird wird    
    */
	protected Kugel k;
	protected KugelRendern kr;
	protected boolean[] ok = new boolean[24];
	protected Logger l = new Logger("errorLog");
	
	
	
	//long endTime = 200000000;
	long endTime = 0;
	
	/**
	 * Initialisiert das Solver-Objekt
	 * @param k
	 */
	public Solver(){}
	public static void update(Kugel k, KugelRendern kr, long time){
		/*try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		time*=1000000;
		long startTime = System.nanoTime();    
		long estimatedTime = System.nanoTime() - startTime;
		while(estimatedTime<time){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
		        kr.end();
		    }
		    if(Display.isCloseRequested()) {                     // Exit if window is closed
		        kr.end();
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is F1 Being Pressed?
		    	kr.drehen(-0.02f,0.0f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is F1 Being Pressed?
		      	kr.drehen(+0.02f,0.0f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is F1 Being Pressed?
		      	kr.drehen(0.0f,-0.02f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is F1 Being Pressed?
		        kr.drehen(0.0f,+0.02f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {          // Is F1 Being Pressed?
		        kr.setDrehen(0.0f,0.0f);
		    }
			kr.updateKugel(k);
			
			estimatedTime = System.nanoTime() - startTime;
		}
		
	}
	public static void update(Kugel k, KugelRendern kr){
		update(k,kr,200);
	}
	/**
	 * Hauptfunktion welche f�r das l�sen der Kugel verantwortlich ist
	 * @param k : Kugel welche gel�st werden soll
	 * @return : gibt die gel�ste Kugel zur�ck
	 */
	
	public Kugel solve(Kugel kugel, KugelRendern Kugelrendern){
		/**
		 * �bergebene Kugel als eigene Speichern
		 */
		this.k = kugel;
		this.kr = Kugelrendern;
		Arrays.fill(ok, false); 

		/**
		 * Phase 1
		 * 
		 * Geht solange, bis Phase 1 abgeschlossen ist
		 */
		while(!everythingOk()){
			//System.out.println("Again");
			boolean recheck = true;
			for(int i = 0; i<24; i++){
				if(recheck){
					recheck = false;
					checkIfP1IsOK();
				}
				if(!ok[i]){
					recheck = true;
					int changep = findPos(i);
					//System.out.println("Change: "+i+", "+changep);
					change2Positions(i,changep);
				}
				/**
				 * Steuerung der Kugel
				 */
				long startTime = System.nanoTime();    
				long estimatedTime = System.nanoTime() - startTime;
				while(estimatedTime<endTime){
					
					if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
				        kr.end();
				    }
				    if(Display.isCloseRequested()) {                     // Exit if window is closed
				        kr.end();
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is F1 Being Pressed?
				    	kr.drehen(-0.02f,0.0f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is F1 Being Pressed?
				      	kr.drehen(+0.02f,0.0f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is F1 Being Pressed?
				      	kr.drehen(0.0f,-0.02f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is F1 Being Pressed?
				        kr.drehen(0.0f,+0.02f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {          // Is F1 Being Pressed?
				        kr.setDrehen(0.0f,0.0f);
				    }
					kr.updateKugel(k);
					
					estimatedTime = System.nanoTime() - startTime;
				}
				/**
				 * Steuerung der Kugel Ende
				 */
			}
			
		}
		/**
		 * Phase 2
		 * 
		 * L�st die Kugel zu ende
		 */
		
		/**
		 * Setzt ok Array zur�ck 
		 */
		Arrays.fill(ok, false); 
		
		while(!isKugelSolved()){
			for(int i = 0; i<6; i++){
				if(!isPolSolved(i)){
					solvePol(i);
					//System.out.println("Solved Pol "+i);
				}
				/**
				 * Steuerung der Kugel
				 */
				long startTime = System.nanoTime();    
				long estimatedTime = System.nanoTime() - startTime;
				while(estimatedTime<endTime){
					
					if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
				        kr.end();
				    }
				    if(Display.isCloseRequested()) {                     // Exit if window is closed
				        kr.end();
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is F1 Being Pressed?
				    	kr.drehen(-0.02f,0.0f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is F1 Being Pressed?
				      	kr.drehen(+0.02f,0.0f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is F1 Being Pressed?
				      	kr.drehen(0.0f,-0.02f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is F1 Being Pressed?
				        kr.drehen(0.0f,+0.02f);
				    }
				    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {          // Is F1 Being Pressed?
				        kr.setDrehen(0.0f,0.0f);
				    }
					kr.updateKugel(k);
					
					estimatedTime = System.nanoTime() - startTime;
				}
				/**
				 * Steuerung der Kugel Ende
				 */
			}
			
		}		
		return k;
		
	}//#END solve(Kugel kugel)
	/**
	 * �berpr�ft, ob ende Phase 1 erreicht ist
	 * also Alle Dreiecke im richgigen Pol sind
	 * @return gibt true zur�ck, wenn erreicht, sonst false
	 */
	private boolean everythingOk(){
		/**
		 * Gehe alle Dreiecke durch
		 */
		for(int i = 0; i<24; i++){
			/**
			 * Wenn eines in ok als false deklariert, dann return false
			 */
			if(!ok[i]){
				return false;
			}
		}
		/**
		 * Wenn kein False dann return true
		 */
		return true;
	}
	/**
	 * Pr�ft in Phase1, welche Dreiecke OK sind und welche nicht
	 */
	private void checkIfP1IsOK(){
		/**
		 * Gehe das ganze Pol weise durch --> 6x
		 */
		for(int i = 0; i<6; i++){
			/**
			 * int[4] cons ==> Array in dem die 4 Con eines Pols gespeichert werden
			 * int[4] tris ==> Array in dem die 4 Tri eines Pols gespeichert werden
			 * boolean[4] allowCons ==> Ist bei entsprechendem Index true, wenn Con noch nicht verbraucht ist
			 */
			int[] cons = new int[4];
			boolean[] allowCons = new boolean[4];
			int[] tris = new int[4];
			/**
			 * Bef�lle die Array mit Daten aus der Kugel
			 */
			for(int j = 0; j<4; j++){
				/**
				 * Wert von con, Index wird gesucht zu tri Index Pol*4 + Position in Pol
				 */
				cons[j] = k.con[k.findCons(i*4+j)];
				/**
				 * Alle sind zu beginn noch benutzbar
				 */
				allowCons[j] = true;
				/**
				 * Wert von tri bei Index Pol*4 + Position in Pol
				 */
				tris[j] = k.tri[i*4+j];
			}
			/**
			 * Gehe nun die 4 Position eines Pols durch
			 */
			for(int j = 0; j<4; j++){
				/**
				 * boolean set sagt, ob Position schon neu gesetzt wurde
				 */
				boolean set = false;
				/**
				 * Pr�fe f�r alle zur Verf�gung stehenden cons ob sie passen
				 */
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
				/**
				 * Wenn Position jetzt noch nicht neu gesetzt wurde, dann ist Dreieck falsch und somit muss ok = false gesetzt werden
				 */
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
		/**
		 * Finde den richtigen Connector f�r p
		 */
		int con = findCon(p);
		/**
		 * Gehe f�r die drei m�glichen Pole durch
		 */
		for(int i = 0; i<3; i++){
			/**
			 * Finde die entsprechende PolNr
			 */
			int polNr = k.con2pol(con, i);
			/**
			 * Wenn Phase 1 bei diesem Pol noch nicht abgeschlossen wurde dann kommt er in frage un wird weiter untersucht
			 */
			if(!checkPoleEndPhase1(polNr)){
				/**
				 * Bei dem Pol wird jede Stelle durch gegeangen
				 */
				for(int j = 0; j<4; j++){
					/**
					 * Pr�fe, ob Position noch nicht korrekt ist und dass die beiden Farben nicht Identisch sind
					 */
					if(!ok[polNr*4+j]&&k.tri[polNr*4+j]!=k.tri[p]){
						/**
						 * Gibt den Tri index zur�ck, damit zwischen diesen Beiden gewechselt werden kann
						 */
						return polNr*4+j;
					}
				}
			}
		}
		/**
		 * Gibt -1 zur�ck, wenn kein Erfolg --> Sollte nicht auftreten
		 */
		return -1;
	}
	
	/**
	 * Pr�ft, ob Pol mit Phase 1 fertig ist
	 * @param polNr : Pol welcher gepr�ft werden soll
	 * @return : true wenn fertig, sonst false
	 */
	private boolean checkPoleEndPhase1(int polNr){
		/**
		 * Erstelle Arrays
		 * tris[4] enth�lt alle Tri eines Pols
		 * cons[4] enth�lt alle Con eines Pols 
		 */
		int[] tris = new int[4];
		int[] cons = new int[4];
		/**
		 * F�lle Arrays mit Daten aus Kugel
		 */
		for(int i = 0; i<4; i++){
			tris[i]=k.tri[polNr*4+i];
			cons[i]=k.findCons(polNr*4+i);
		}
		/**
		 * Sortiere die Arrays
		 * 
		 * Anschliessen pr�fen, ob die Array identisch sind
		 * Je nach ergebnis true oder false zur�ck geben
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
			 * Wenn Tri = Con dann gibt den Con Index zur�ck
			 */
			if(k.con[i]==k.tri[p]){
				return i;
			}
		}
		/**
		 * Wenn keine �bereinstimmung gefunden werden konnte dann gibt -1 zur�ck
		 */
		return -1;
	}
	/**
	 * Wechselt die Positionen von 2 Punkten
	 */
	private void change2Positions(int p1, int p2){
		/**
		 * �berpr�ft p1 und p2 auf Korrektheit ==> zugross, bzw zuklein werden ausgefiltert
		 */
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23){
			int temp = k.tri[p1];
			k.tri[p1] = k.tri[p2];
			k.tri[p2] = temp;

		}
	}
	
	/**
	 * Pr�ft, ob Kugel fertig gel�st wurde
	 * @return : wenn gel�st, dann true sonst false
	 */
	protected boolean isKugelSolved(){
		/**
		 * Pr�fe jeden Pol
		 */
		for(int i = 0; i<6; i++){
			if(!isPolSolved(i)){
				return false;
			}
		}
		
		return true;
	}//#END isKugelSolved
	
	/**
	 * �berpr�ft, ob ein Pole vollst�ndig gel�st ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zur�ck, wenn Pole vollst�ngig gel�sst ist
	 */
	protected boolean isPolSolved(int polNr){
		int anzKorr = 0;
		for(int i = 0; i<4; i++){
			if(isPositionSolved((polNr*4)+i)){
				anzKorr++;
			}
		}
		if(anzKorr==4){
			return true;
		}else{
			return false;
		}
	}//#END isPolSolved
	
	/**
	 * �berpr�ft, ob eine Position/Dreieck gel�st ist
	 * @param p : welches Dreieck gepr�ft werden soll
	 * @return : gibt true zur�ck, wenn gel�st
	 */
	private boolean isPositionSolved(int p){
		/**
		 * Pr�fe, ob tri mit con stimmt	
		 * wenn ja, Return true	
		 */
		if(k.tri[p]==k.con[k.findCons(p)]){
			return true;
		}
		/**
		 * Wenn nicht abgebrochen durch return true, dann return false
		 */
		return false;
	}//isPositionSolved

	private void solvePol(int polNr){
		boolean[] pOk = new boolean[4];
		for(int i = 0; i<4; i++){
			if(isPositionSolved(polNr*4+i)){
				pOk[i] = true;
				//System.out.println("Pos "+i+" OK");
			}else{
				pOk[i] = false;
				//System.out.println("Pos "+i+" false");
			}
		}
		//System.out.println("Made Ok");
		while(!isPolSolved(polNr)){
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
						if(isPositionSolved(polNr*4+j)){
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
		//Dreifacher n�tig
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
					l.log(k, "Error - Muss sonst l�sen - P1: "+p1+"; P2: "+p2);
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
	protected int change2PolPositionsD2(int p1, int p2){
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
	protected int change2PolPositionPR(int pol1, int pol2){
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
		if((pol1==1&&pol2==4)||(pol1==2&&pol2==5)||(pol1==4&&pol2==3)||(pol1==5&&pol2==1)){
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
	protected void change2Pol(int pol,int polO, int polR){
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
		//update(k,kr,500);
		p2 = p2_old;
		p1++;
		if(p1>pol*4+3){
			p1-=4;
		}
		
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		change2Pol(pol,polO,polR);
       
    	//update(k,kr,500);
    	
    	p1 = p1_old;
		p2--;
		if(p2<pol*4){
			p2+=4;
		}
		polO = change2PolPositionsD2(p1, p2);
		polR = change2PolPositionPR(pol, polO);
		
		change2Pol(pol,polO,polR);
       
    	//update(k,kr,500);
    	int end = 0;
    	while(!isPolSolved(pol)&&end<4){
    		k.changePol(pol, 1);
    		end++;
    	}
		
		
	}
}
