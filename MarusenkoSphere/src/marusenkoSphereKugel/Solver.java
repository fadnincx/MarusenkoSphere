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
		switch(modus){
		case 1:
			k.changePol(pol, anz);
		break;
		case 3:
			k.turnKugel(pol, anz);
		break;
		}
		
		
		//Erhöhe den Step
		k.step++;
		//Wenn Step zu gross, breche Ab und Gebe Error aus
		if(k.step>10000){
			Log.ErrorLog(solvingWay.get(0));
			Log.ErrorLog("Zuviele Schritte nötig!!!");
			k.step-=10000;
			return true;
		}
		//Füge Kugel zum Lösungsprozess hinzu
		solvingWay.add(k.getSphere(""+pol+anz+modus));
		//System.out.println(k.getSphere(""+pol+anz+modus));
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
		String firstSphere = k.getSphere();
		ArrayList<String> bestSolvingWay = new ArrayList<String>();
		for(int i = 0; i<10000; i++){
			bestSolvingWay.add("preSet");
		}
		int anzahlStrategien = 2;
		for(int i = 0; i<anzahlStrategien; i++){
			k.FillKugelFromString(firstSphere, false);
			ArrayList<String> AL = strategieChooser(i);
			if(AL.size()<bestSolvingWay.size()){
				bestSolvingWay = AL;
			}
			solvingWay = new ArrayList<String>();
		}
		
		return bestSolvingWay;
		
		
	}//#END solve(Kugel kugel)
	private ArrayList<String> strategieChooser(int strategie){
		switch(strategie){
			case 0:
				return strategieStandard();
				
			case 1:
				return strategieOne();
			default:
				return null;
		}
		
	}
	private ArrayList<String> strategieStandard(){
		//Die Ursprüngliche Kugel speichern
		solvingWay.add(k.getSphere());
		//Gehe für alle Pole Durch
		//Damit Pol 4 vor 3 gelöst wird
		int[] pol = {0,1,4,2,3,5};
		for(int z = 0; z<1;z++){
		
			for(int i = 0; i<6; i++){
				//Wenn Pol nicht gelöst ist, dann weiter
				if(!SolveCheck.isPolSolved(pol[i], k)){
					/*boolean ok[] = {false, false, false, false};
					int anzahlSolved = 0;
					//Prüfen, welche Teile bereits gelöst sind
					for(int j = 0; j<4; j++){
						if(SolveCheck.isPositionSolved(pol[i]*4+j,k)){
							ok[j]=true;
							anzahlSolved++;
						}
					}
					//Wenn 3 gelöst sind, dann muss 1. hälfte gelöst werden
					/*if(anzahlSolved==3){
						int falsePosition = -1;
						for(int j = 0; j<4; j++){
							if(!ok[j]){
								falsePosition = j;
							}
						}
						if(strategieStandardAreThis2Possible(pol[i]*4+falsePosition, pol[i]*4+((falsePosition+1)%4))){
							//Lösen mit nummer Plus 
							strategieStandardSolv2Positions(pol[i]*4+falsePosition, pol[i]*4+((falsePosition+1)%4));
						}else
						if(strategieStandardAreThis2Possible(pol[i]*4+falsePosition, pol[i]*4+((falsePosition+3)%4))){
							//Lösen mit nummer minus
							strategieStandardSolv2Positions(pol[i]*4+falsePosition, pol[i]*4+((falsePosition+3)%4));
						}
	
						
					}else
					//Wenn 2 gelöst sind, dann muss je nach dem 1 oder 2 hälften gelöst werden
					if(anzahlSolved==2){
						//nur einer lösen
						if(ok[0]&&ok[1]&&strategieStandardAreThis2Possible(pol[i]*4+2,  pol[i]*4+3)){
							strategieStandardSolv2Positions(pol[i]*4+2, pol[i]*4+3);
						}else
						if(ok[2]&&ok[3]&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+1)){
							strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+1);
						}else
						if(ok[0]&&ok[3]&&strategieStandardAreThis2Possible(pol[i]*4+1,  pol[i]*4+2)){
							strategieStandardSolv2Positions(pol[i]*4+1, pol[i]*4+2);
						}else
						if(ok[1]&&ok[2]&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+3)){
							strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+3);
						}
						//beide lösen
						else{
							if(strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+1)&&strategieStandardAreThis2Possible(pol[i]*4+2,  pol[i]*4+3)){
								strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+1);
								strategieStandardSolv2Positions(pol[i]*4+2, pol[i]*4+3);
							}else
							if(strategieStandardAreThis2Possible(pol[i]*4+1,  pol[i]*4+2)&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+3)){
								strategieStandardSolv2Positions(pol[i]*4+1, pol[i]*4+2);
								strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+3);
							}
						}
					}
					//2 Hälften müssen gelöst werden
					else{*/
						if(strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+1)&&strategieStandardAreThis2Possible(pol[i]*4+2,  pol[i]*4+3)){
							strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+1);
							strategieStandardSolv2Positions(pol[i]*4+2, pol[i]*4+3);
						}else
						if(strategieStandardAreThis2Possible(pol[i]*4+1,  pol[i]*4+2)&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+3)){
							strategieStandardSolv2Positions(pol[i]*4+1, pol[i]*4+2);
							strategieStandardSolv2Positions(pol[i]*4+0, pol[i]*4+3);
						}else{
							//System.out.println("Error no Possibles");
						}
					//}
				}else{
					//System.out.println("Pol "+i+" gelöst");
				}
			}
		}
		boolean doStrategiePhase1 = false;
		for(int i = 0; i<6; i++){
			if(!checkPoleEndPhase1(i)){
				doStrategiePhase1 = true;
			}
		}
		if(doStrategiePhase1){
			strategieOne();
		}
		
		return strategieOnePhaseTwo();
		//return solvingWay;
	}
	private void strategieStandardSolv2Positions(int pos1, int pos2){
		//Wenn möglich
		if(strategieStandardAreThis2Possible(pos1,pos2)){
			//Die Beiden Pole für die Beiden Positionen abfragen
			int[] pole1 = getPoleForPosition(pos1);
			int[] pole2 = getPoleForPosition(pos2);
			//Pol auf dem beide sind
			int pol = pos1/4;
			//Array mit möglichen Polen
			int[] poleMoeglich = new int[2]; 
			int[] neededColors = new int[] {k.con[k.findCons(pos1)],k.con[k.findCons(pos2)]};
			boolean everSame = false;
			for(int i = 0; i<2; i++){
				boolean same = false;
				int notSame = -1;
				for(int j = 0; j<2;j++){
					if(pole1[i]==pole2[j]){
						same = true;
					}else{
						notSame = j;
					}
				}
				if(!same){
					poleMoeglich[0]=pole1[i];
					poleMoeglich[1]=pole2[notSame];
					everSame = true;
				}
			}
			//System.out.println("Pos1 "+pos1+" Pos2 "+pos2+"--> Pole Möglich: "+poleMoeglich[0]+"; "+poleMoeglich[1]);
			int poleToDo = -1;
			int anzNeededColors = -1;
			if(everSame){
				for(int i = 0; i<2; i++){
					if(!SolveCheck.isPolSolved(poleMoeglich[i], k)){
						int aktAnzNeeded = 0;
						boolean p1Found = false; 
						boolean p2Found = false;
						for(int j = 0; j<4; j++){
							if(k.tri[poleMoeglich[i]*4+j]==neededColors[0]&&!p1Found){
								aktAnzNeeded++;
								p1Found = true;
							}
							if(k.tri[poleMoeglich[i]*4+j]==neededColors[1]&&!p2Found){
								aktAnzNeeded++;
								p2Found = true;
							}
						}
						if(aktAnzNeeded>anzNeededColors){
							poleToDo = poleMoeglich[i];
							anzNeededColors = aktAnzNeeded;	
						}
						
						
					}
				}
				//System.out.println("Pol "+poleMoeglich[0]+": Farben "+k.tri[poleMoeglich[0]*4]+";"+k.tri[poleMoeglich[0]*4+1]+";"+k.tri[poleMoeglich[0]*4+2]+";"+k.tri[poleMoeglich[0]*4+3]);
				//System.out.println("Pol "+poleMoeglich[1]+": Farben "+k.tri[poleMoeglich[1]*4]+";"+k.tri[poleMoeglich[1]*4+1]+";"+k.tri[poleMoeglich[1]*4+2]+";"+k.tri[poleMoeglich[1]*4+3]);
				//System.out.println("Pol ToDo: "+poleToDo+" Farben: "+neededColors[0]+";"+neededColors[1]);
			}else{
				//System.out.println("Error bei Pol zu teilung");
			}
			if(poleToDo!=-1){
				//Die Farben auf den Pol kriegen
				strategieStandardGetOnPole(poleToDo, neededColors[0],neededColors[1]);	
				
				
				int posOnPol = strategieStandardGetPosOnPol(pos1, pos2, poleToDo);
				int polRechts = strategieStandardGetPolRechts(pos1, pos2, poleToDo);
				if(polRechts == -1){
					//System.out.println("Error - PolRechts = -1");	
				}
				if(posOnPol == -1){
					//System.out.println("Error - PosOnPol = -1");
				}
				//System.out.println("PolRechts "+polRechts+" posOnPol "+posOnPol);
				//System.out.println();
				
				
				int[] optionens = strategieStandartGetTurnOpt(pos1, pos2, poleToDo);
				//Farbe eins auf Bereitschaft drehen
				
				int[] colors = new int[]{neededColors[0],neededColors[1]};
				
				if(neededColors[0]==k.con[k.findCons(pos2)]&&neededColors[1]==k.con[k.findCons(pos1)]){
					int temp = neededColors[0];
					neededColors[0] = neededColors[1];
					neededColors[1] = temp;
				}
				
				
				if(optionens[2]==2){
					colors[0] = neededColors[1];
					colors[1] = neededColors[0];
				}
				
				
				
				for(int i = 0; i<4; i++){
					if(k.tri[posOnPol]!=colors[0]){
						addSteps(poleToDo, 1, 1);
					}
				}
				addSteps(polRechts, optionens[0], 3);
				addSteps(pol, optionens[1], 1);
				addSteps(polRechts, 4-optionens[0], 3);
				

				//Farbe eins auf Bereitschaft drehen
				for(int i = 0; i<4; i++){
					if(k.tri[posOnPol]!=colors[1]){
						addSteps(poleToDo, 1, 1);
					}
				}
				addSteps(polRechts, optionens[0], 3);
				addSteps(pol, optionens[1], 1);
				addSteps(polRechts, 4-optionens[0], 3);
				
			}else{
			//	System.out.println("Error: ToDo Pol = -1");
			}
			
			
			
			
		}else{
			//System.out.println("Error-NotPossible in Solve2Positions");
		}
	}
	private int[] strategieStandartGetTurnOpt(int pol1Pos1, int pol1Pos2, int pol2){
		int pol1 = pol1Pos1/4;
		ArrayList<Integer> ali = new ArrayList<Integer>();
		ali.add(pol1Pos1);
		ali.add(pol1Pos2);
		switch(pol1){
		case 0:
			switch(pol2){
				case 1: return (ali.contains(0)&&ali.contains(3)) ? new int[]{1,3,1}: new int[]{3,1,1};
				case 3: return (ali.contains(0)&&ali.contains(3)) ? new int[]{3,1,2}: new int[]{1,3,2};
				case 4: return (ali.contains(0)&&ali.contains(1)) ? new int[]{3,1,1}: new int[]{1,3,2};
				case 5: return (ali.contains(0)&&ali.contains(1)) ? new int[]{1,3,2}: new int[]{3,1,1};
				default: return new int[]{0,0,0};
			}
		case 1:
			switch(pol2){
				case 0: return (ali.contains(4)&&ali.contains(7)) ? new int[]{3,1,1}: new int[]{1,3,1};
				case 2: return (ali.contains(4)&&ali.contains(7)) ? new int[]{1,3,2}: new int[]{3,1,2};
				case 4: return (ali.contains(4)&&ali.contains(5)) ? new int[]{1,3,1}: new int[]{3,1,2};
				case 5: return (ali.contains(4)&&ali.contains(5)) ? new int[]{3,1,2}: new int[]{1,3,1};
				default: return new int[]{0,0,0};
			}
		case 2:
			switch(pol2){
				case 1: return (ali.contains(8)&&ali.contains(11)) ? new int[]{3,1,1}: new int[]{1,3,1};
				case 3: return (ali.contains(8)&&ali.contains(11)) ? new int[]{1,3,2}: new int[]{3,1,2};
				case 4: return (ali.contains(8)&&ali.contains(9)) ? new int[]{1,3,1}: new int[]{3,1,2};
				case 5: return (ali.contains(8)&&ali.contains(9)) ? new int[]{3,1,2}: new int[]{1,3,1};
				default: return new int[]{0,0,0};
			}
		case 3:
			switch(pol2){
				case 0: return (ali.contains(12)&&ali.contains(15)) ? new int[]{1,3,1}: new int[]{3,1,2};
				case 2: return (ali.contains(12)&&ali.contains(15)) ? new int[]{3,1,2}: new int[]{1,3,1};
				case 4: return (ali.contains(12)&&ali.contains(13)) ? new int[]{3,1,1}: new int[]{1,3,2};
				case 5: return (ali.contains(12)&&ali.contains(13)) ? new int[]{1,3,2}: new int[]{3,1,1};
				default: return new int[]{0,0,0};
			}
		case 4:
			switch(pol2){
				case 0: return (ali.contains(16)&&ali.contains(17)) ? new int[]{1,3,1}: new int[]{3,1,2};
				case 1: return (ali.contains(16)&&ali.contains(19)) ? new int[]{3,1,1}: new int[]{1,3,1};
				case 2: return (ali.contains(16)&&ali.contains(17)) ? new int[]{3,1,2}: new int[]{1,3,1};
				case 3: return (ali.contains(16)&&ali.contains(19)) ? new int[]{1,3,2}: new int[]{3,1,2};
				default: return new int[]{0,0,0};
			}
		case 5:
			switch(pol2){
			case 0: return (ali.contains(20)&&ali.contains(21)) ? new int[]{3,1,1}: new int[]{1,3,2};
			case 1: return (ali.contains(20)&&ali.contains(23)) ? new int[]{1,3,1}: new int[]{3,1,1};
			case 2: return (ali.contains(20)&&ali.contains(21)) ? new int[]{1,3,2}: new int[]{3,1,1};
			case 3: return (ali.contains(20)&&ali.contains(23)) ? new int[]{3,1,2}: new int[]{1,3,2};
			default: return new int[]{0,0,0};
			}
		default:
			return new int[]{0,0,0};
		}
	}
	private int strategieStandardGetPosOnPol(int pol1Pos1, int pol1Pos2, int pol2){
		int pol1 = pol1Pos1/4;
		ArrayList<Integer> ali = new ArrayList<Integer>();
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
	private int strategieStandardGetPolRechts(int pol1Pos1, int pol1Pos2, int pol2){
		int pol1 = pol1Pos1/4;
		ArrayList<Integer> ali = new ArrayList<Integer>();
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
	private void strategieStandardGetOnPole(int pol, int color1, int color2){
		boolean[] okPole = new boolean[6];
		for(int i = 0; i<6; i++){
			okPole[i] = SolveCheck.isPolSolved(i, k) ? true : false;
		}
		boolean color1OnPole = false;
		boolean color2OnPole = false;
		int color1Pos = -1;
		int color2Pos = -1;
		for(int i = 0; i<4; i++){
			if(k.tri[pol*4+i]==color1&&!color1OnPole){
				color1OnPole = true;
				color1Pos = i;
			}
			if(k.tri[pol*4+i]==color2&&!color2OnPole){
				color2OnPole = true;
				color2Pos = i;
			}
		}
		if(color1Pos==-1){
			for(int i = 0; i<4; i++){
				boolean done = false;
				if(i!=color2Pos&&!done){
					color1Pos = i;
				}
			}
		}

		//System.out.println("Status: "+color1OnPole+";"+color2OnPole);
		for(int i = 0; i<6; i++){
			//Wenn noch einen ungelösten anderen Pol ist, dann lösen
			if(!okPole[i]){
				//Für beide schauen, dass auf uhrsprungspol kommen...
				if(!color1OnPole){
					for(int j = 0; j<4; j++){
						if(k.tri[i*4+j]==color1){
							//, 
							Solve1Phase(pol*4+color1Pos, i*4+j);
							color1OnPole = true;
						}
					}
				}
			}
		}
		color1Pos = -1;
		color2Pos = -1;
		for(int i = 0; i<4; i++){
			if(k.tri[pol*4+i]==color1&&!color1OnPole){
				color1OnPole = true;
				color1Pos = i;
			}
			if(k.tri[pol*4+i]==color2&&!color2OnPole){
				color2OnPole = true;
				color2Pos = i;
			}
		}
		if(color2Pos==-1){
			for(int i = 0; i<4; i++){
				boolean done = false;
				if(i!=color1Pos&&!done){
					color2Pos = i;
				}
			}
		}
		for(int i = 0; i<6; i++){
			//Wenn noch einen ungelösten anderen Pol ist, dann lösen
			if(i!=pol&&i!=k.gegenPol(pol)&&!okPole[i]){
				//Für beide schauen, dass auf uhrsprungspol kommen...
				if(!color2OnPole){
					for(int j = 0; j<4; j++){
						if(k.tri[i*4+j]==color2){
							Solve1Phase(pol*4+color2Pos, i*4+j);
							color2OnPole = true;
						}
					}
				}
			}
		}
		//System.out.println("StatusEnde: "+color1OnPole+";"+color2OnPole);
		//.out.println("Pol "+pol+": Farben "+k.tri[pol*4]+";"+k.tri[pol*4+1]+";"+k.tri[pol*4+2]+";"+k.tri[pol*4+3]);
	}
	private boolean strategieStandardAreThis2Possible(int pos1, int pos2){
		int[] pole1 = getPoleForPosition(pos1);
		int[] pole2 = getPoleForPosition(pos2);
		int[] poleMoeglich = new int[2]; 
		boolean everSame = false;
		for(int i = 0; i<2; i++){
			boolean same = false;
			int notSame = -1;
			for(int j = 0; j<2;j++){
				if(pole1[i]==pole2[j]){
					same = true;
				}else{
					notSame = j;
				}
			}
			if(!same){
				poleMoeglich[0]=pole1[i];
				poleMoeglich[1]=pole2[notSame];
				everSame = true;
			}
		}
		if(everSame){
			for(int i = 0; i<2; i++){
				if(!SolveCheck.isPolSolved(poleMoeglich[i], k)){
					return true;
				}
			}
		}	
		return false;
	}
	private int[] getPoleForPosition(int pos){
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
	private ArrayList<String> strategieOne(){

		
		
		//Füge die Ausgangskugel als erste Kugel hinzu
		solvingWay.add(k.getSphere());
		//solvingWay.add(k.getSphere());
		
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
					if(findPos(i)==-1){
						Log.ErrorLog("Achtung Abbruch des Lösen");
						return solvingWay;
					}
					//consLog(i+" "+findPos(i));
					Solve1Phase(i,findPos(i));
					//Abbrechen, falls gefordert
					if(end){return solvingWay;}
					checkIfP1IsOK();
					if(getAnzFalseInArray(ok)==2){
						solveLast();
					}
				}
			}
		}
		//System.out.println("End Phase 1 bei "+k.step+" Schritten");
		return strategieOnePhaseTwo();
		
		//return solvingWay;
	}
	private ArrayList<String> strategieOnePhaseTwo(){
		/**
		 * Phase 2
		 * 
		 * Löst die Kugel zu ende
		 */
		consLog("Phase 2");
		//Array ok erneut zurück auf false setzen
		Arrays.fill(ok, false); 
		
		//Drehe Pole in Optimale Position
		turnPolToOptinalPosition();

		//Pol schneller lösen
		solveFasterOverCross();
		
		//Prüfe ob Kugel noch Korrekt ist, oder ob es einen Fehler gibt
		checkIfP1IsOK();
		if(SolveCheck.ArrayIsFullyOk(ok)){
			
			//So lange die Kugel nicht gelöst ist...
			while(!SolveCheck.isKugelSolved(k)){
				
				//Gehe Jeden Pol durch
				for(int i = 0; i<6; i++){
					
					//Wenn Pol noch nicht gelöst, löse ihn
					if(!SolveCheck.isPolSolved(i,k)){		
						solvePol(i);
						//Wenn zulang --> Fehler dann breche ab!
						if(end){return solvingWay;}
					}
				}
				consLog("Sphere not solved");
			}	
		}else{
			System.out.println("Nicht korrekte Kugel!!!");
			
		}
			
		//Gib die gelöste Kugel zurück
		
		return advancedStepRemover(advancedTurnMinimizer(RemoveUnusedSteps(solvingWay)));
		//return RemoveUnusedSteps(solvingWay);
	}
	
	private int getAnzFalseInArray(boolean[] array){
		int anz = array.length;
		int r = 0;
		for(int i = 0; i<anz; i++){
			if(!array[i]){
				r++;
			}
		}
		return r;
	}
	private boolean solveLast(){
		int pos1 = -1;
		int pos2 = -1;
		boolean setOne = false;
		for(int i = 0; i<24; i++){
			if(!ok[i]){
				if(!setOne){
					pos1 = i;
					setOne = true;
				}else{
					pos2 = i;
				}	
			}
		}
		Solve1Phase(pos1, pos2);
		
		return true;
	}
	
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
		
	/*	//Pol auf welchem der Falsche liegt
		int urPol = p/4;
		
		//Verbindungsstück an der stelle des Falschen
		int urCon = k.findCons(p);
		int zielCon = findCon(p);
		int[] posPol = new int[3];
		//System.out.println("Con = "+zielCon);
		int[]posPos = k.conToPos(zielCon);
		posPol[0] = posPos[0]/4;
		posPol[1] = posPos[1]/4;
		posPol[2] = posPos[2]/4;
		
		
	//	System.out.println("Pos: "+p+"("+k.tri[p]+") Con: "+zielCon+"("+k.con[zielCon]+") Pol: "+posPol[0]+", "+posPol[1]+", "+posPol[2]);
		
		//Bewerte die einzelnen Positionen
		
		int[][] posBewertung = new int[3][4];
		
		//Wenn Pol Ursprung, dann minus 1000
		for(int i = 0; i<3; i++){
			if(checkPoleEndPhase1(posPol[i])||urPol==posPol[i]){//checkPoleEndPhase1(posPol[i])||
				posBewertung[i][0] -= 1000; posBewertung[i][1] -= 1000; 
				posBewertung[i][2] -= 1000; posBewertung[i][3] -= 1000; 
			}
		}
		//Check durch gehen
		checkIfP1IsOK();
		
		//Wenn Position die Farbe des Ursprung hat, dann +100
		//Wenn Position noch nicht gelöst, dann + 5
		//Wenn Position nicht auf korrektem Pol, dann +20
		for(int i = 0; i<3; i++){
			for(int j = 0; j<4; j++){
				if(((posPol[i])*4)+j==posPol[0]||
						((posPol[i])*4)+j==posPol[1]||
						((posPol[i])*4)+j==posPol[2]){
					posBewertung[i][j] += 100;
				}
				if(ok[((posPol[i])*4)+j]){
					posBewertung[i][j] -= 300;
				}
				if(k.tri[(posPol[i])*4+j]==k.tri[p]){
					posBewertung[i][j] -= 300;
				}
				if(k.con[urCon]==k.tri[((posPol[i])*4)+j]){
					posBewertung[i][j] += 100;
				}
				if(!ok[((posPol[i])*4)+j]){
					posBewertung[i][j] +=50;
				}
			}
		}
		
		//Finde den Besten Tauschpartner
		
		int besterPartnerPunkte = 0;
		int besterPartnerPos = -1;
		
		for(int i = 0; i<3; i++){
			for(int j = 0; j<4; j++){
				if(posBewertung[i][j]>besterPartnerPunkte){
					besterPartnerPunkte = posBewertung[i][j];
					besterPartnerPos = (i*4)+j;
				}
			}
		}
	//	System.out.println("Bester Punkte "+besterPartnerPunkte+", Tri "+besterPartnerPos);
	//	System.out.println(k.getSphere());
	//	System.out.println(Arrays.deepToString(posBewertung));
	//	System.out.println(Arrays.toString(ok));
		return besterPartnerPos;
		
	
		
		
		
		
		
/*		*/int urPol = p/4;
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
				consLog(polNr+" Pol nicht gelöst");
				for(int j = 0; j<4; j++){
					
					//Prüfe, ob Position noch nicht korrekt ist und dass die beiden Farben nicht Identisch sind
					if(!ok[polNr*4+j]&&k.tri[polNr*4+j]!=k.tri[p]){
						
						//Gibt den Tri index zurück, damit zwischen diesen Beiden gewechselt werden kann
						return polNr*4+j;
					}
				}
			}else{
				consLog(polNr+"Pol gelöst!");
			}
		}
		
		//Gibt -1 zurück, wenn kein Erfolg --> Sollte nicht auftreten
		return -1;/**/
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
			
			//Wenn Tri = Con dann gibt den Con Index zurüück
			if(k.con[i]==k.tri[p]){
				return i;
			}
		}
		
		//Wenn keine übereinstimmung gefunden werden konnte dann gibt -1 zurück
		System.out.println(k.getSphere());
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
			/*int temp = k.tri[p1];
			k.tri[p1] = k.tri[p2];
			k.tri[p2] = temp;
			*/
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
			//k.changePol(pol, 1);
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
		//k.turnKugel(polRechts, s);
		addSteps(polRechts, s, 3);
		//k.changePol(pol1, 1);
		addSteps(pol1, 1, 1);
		//k.turnKugel(polRechts, 4-s);
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
				
				//Individuell nach Pol diese Variablen ändern
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
		while(!SolveCheck.isPolSolved(polNr,k)&&!end){
			//Gehe jede Position durch
			for(int i = 0; i<4; i++){
				//Wenn noch nicht korrekt
				if(!pOk[i]){
					//System.out.println("Pos "+i+" nicht gelöst");
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
					//k.changePol(p1/4, 1);
					turn++;
					change2PolPositions(p1,p2,turn);
				}else{
					//Wenns definitiv nicht geht Error ausgeben
					Log.ErrorLog(k.getSphere()+" \n Error - Muss sonst lösen - P1: "+p1+"; P2: "+p2);
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
	//	k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 1);
		if(addSteps(polO, 1, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		
		//k.changePol(pol, 1);
		if(addSteps(pol, 1, 1)){end();}
		//k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 1);
		if(addSteps(polO, 1, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 3);
		if(addSteps(polR, 3, 3)){end();}
		//k.changePol(polO, 3);
		if(addSteps(polO, 3, 1)){end();}
		//k.turnKugel(polR, 1);
		if(addSteps(polR, 1, 3)){end();}
	//	k.changePol(pol, 2);
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
    		//k.changePol(pol, 1);
    		if(addSteps(pol, 1, 1)){end();}
    		end++;
    	}
		
		
	}
	
	/**
	 * Ersetzt unnützliche mehrfach Drehungen durch eine einzige
	 * @param arrayList
	 * @return
	 */
	private ArrayList<String> RemoveUnusedSteps(ArrayList<String> arrayList){
		//Gehe jede position durch
		if(arrayList.size()>1){
			for(int i = 0; i<arrayList.size()-2;i++){
				//Nehme die Drehungen der nächsten beiden Stadien
				int[] dreh1 = k.SplitDrehungFromSphere(arrayList.get(i));
				int[] dreh2 = k.SplitDrehungFromSphere(arrayList.get(i+1));
				//Wenn auf gleichem Pol im Gleichen Modus gedreht wird
				if((dreh1[0]==dreh2[0])&&(dreh1[2]==dreh2[2])){
					//Beide drehungen Addieren und Modulo 4 nehmen
					int anz = dreh1[1]+dreh2[1];
					anz%=4;
					//Wenn Anzahl nicht gleich 0 dann ersetzte erstes Element mit neuer Drehung und lösche zweites
					if(anz!=0){
						arrayList.set(i, k.SphereWithoutDrehungAndStep(arrayList.get(i+1))+"n"+i+"n"+dreh1[0]+""+(anz)+""+dreh1[2]);
						arrayList.remove(i+1);
					}else{
						//Wenn Anzahl = 0, dann lösche beide
						arrayList.remove(i);
						arrayList.remove(i+1);
					}
					//Gehe 2 Schritte zurück, um allfällige neue überschneidungen zu entfernen
					i-=2;
				}else{
					//Wenn nicht auf gleichem Pol, bzw Modus, korrigiere den Step, da dieser nun verschoben ist
					arrayList.set(i, k.SphereWithoutDrehungAndStep(arrayList.get(i))+"n"+i+"n"+dreh1[0]+dreh1[1]+dreh1[2]);
				}
			}

			//Korrigiere auch die Letzte Kugel, wegen dem i und i+1 ist es nicht mögluch dies in der Schlaufe zu tun
			int[] dreh = k.SplitDrehungFromSphere(arrayList.get(arrayList.size()-2));
			arrayList.set(arrayList.size()-2, k.SphereWithoutDrehungAndStep(arrayList.get(arrayList.size()-2))+"n"+(arrayList.size()-2)+"n"+dreh[0]+""+dreh[1]+""+dreh[2]);
			dreh = k.SplitDrehungFromSphere(arrayList.get(arrayList.size()-1));
			arrayList.set(arrayList.size()-1, k.SphereWithoutDrehungAndStep(arrayList.get(arrayList.size()-1))+"n"+(arrayList.size()-1)+"n"+dreh[0]+""+dreh[1]+""+dreh[2]);
		}
		
		//Gib arraylist zurück
		return arrayList;
		
	}
	/**
	 * Pol in beste Position drehen, da so viele viele Schritte vermieden werden können
	 */
	private void turnPolToOptinalPosition(){
		for(int i = 0; i<6;i++){
			int best = 0;
			int best_anz = 0;
			int jetzt_anz = 0;
			int[] cons = new int[4];
			for(int j = 0; j<4; j++){
				cons[j] = k.con[k.findCons(i*4+j)];
			}
			for(int j = 0; j<4; j++){
				jetzt_anz = 0;
				for(int l = 0; l<4;l++){
					//int conIndex = (j+l)%4;
					if(k.tri[(i*4)+l]==k.con[k.findCons((i*4+l),j)]){
						jetzt_anz++;
					}
				}
				if(jetzt_anz>best_anz){
					best_anz = jetzt_anz;
					best = j;
				}
			}
			if(best>0){
			//	k.changePol(i, best);
				if(addSteps(i, best, 1)){end();}
			}
		}
	}
	private void solveFasterOverCross(){
		for(int i = 0; i<5; i++){
			for(int j = i+1; j<6; j++){
				//Wenn nicht gegenüber und beide nicht gelöst
				//System.out.println(i+" "+j);
				//System.out.println(polGegenuber(i));
				//System.out.println(SolveCheck.isPolSolved(i, k));
				//System.out.println(SolveCheck.isPolSolved(j, k));
				turnPolToOptinalPosition();
				if(j!=polGegenuber(i)&&!SolveCheck.isPolSolved(i, k)&&!SolveCheck.isPolSolved(j, k)){
					//System.out.println("Do cross accepted");
					//System.out.println("Anz Steps: "+k.step);
					int pr = change2PolPositionPR(i,j);
					int con1 = gemeinsameCons(i,j,0);
					int con2 = gemeinsameCons(i,j,1);
					int p1c1 = ConPol2Tri(con1, i);
					int p1c2 = ConPol2Tri(con2, i);
					int p2c1 = ConPol2Tri(con1, j);
					int p2c2 = ConPol2Tri(con2, j);
					//System.out.println("Cross c1:"+i+" c2:"+j+" c1:"+k.con[con1]+" c2:"+k.con[con2]+" p1c1:"+k.tri[p1c1]+" p1c2:"+k.tri[p1c2]+" p2c1:"+k.tri[p2c1]+" p2c2:"+k.tri[p2c2]);
					//Alles Legal
					if(p1c1!=-1&&p1c2!=-1&&p2c1!=-1&&p2c2!=-1){
						//Pol 1/2 bereit
						boolean p1ready = false;
						boolean p2ready = false;
						int goStep = 2;
						int doTurn = 3;
						int hpTurn = 1;
						//int p1r = 0;
						//int p2r = 0;
						int pol1 = i;
						//int pol2 = j;
						if((i==1&&j==2)||(i==0&&j==4)||(i==0&&j==5)||(i==1&&j==5)||(i==2&&j==5)||(i==3&&j==5)||(i==0&&j==1)||(i==1&&j==4)||(i==2&&j==3)||(i==3&&j==4)||(i==2&&j==4)||(i==0&&j==3)){
							doTurn = 1;
						}
						if((i==0&&j==3)){
							hpTurn = 3;
						}
						if(k.tri[p1c1]==k.con[con2]&&k.tri[p1c2]==k.con[con1]){
							p1ready = true;
						//	p1r = 1;
						}else
						if(k.tri[p1c1]==k.con[con1]&&k.tri[p1c2]==k.con[con2]){
							p1ready = true;
							goStep = 1;
							//p1r = 2;
						}else
						if(k.tri[p1c1]==k.con[con1]&&k.tri[i*4+(((p1c1%4)+3)%4)]==k.con[con2]){
						/*	k.changePol(i, 3);
							if(addSteps(i, 3, 1)){end();}*/
							p1ready = true;
							//p1r = 3;
						}else
						if(k.tri[p1c2]==k.con[con2]&&k.tri[i*4+(((p1c2%4)+1)%4)]==k.con[con1]){
							/*k.changePol(i, 1);
							if(addSteps(i, 1, 1)){end();}*/
							p1ready = true;
							//p1r = 4;
						}
						
						if(k.tri[p2c1]==k.con[con2]&&k.tri[p2c2]==k.con[con1]){
							p2ready = true;
							//p2r = 1;
						}else
						if(k.tri[p2c1]==k.con[con1]&&k.tri[p2c2]==k.con[con2]&&goStep==2){
							p2ready = true;
							goStep = 1;
							//p2r = 2;
						}else
						if(k.tri[p2c1]==k.con[con1]&&k.tri[j*4+(((p2c1%4)+3)%4)]==k.con[con2]){
						/*	k.changePol(j, 1);
							if(addSteps(j, 1, 1)){end();}*/
							p2ready = true;
							//p2r = 3;
						}else
						if(k.tri[p2c2]==k.con[con2]&&k.tri[j*4+(((p2c2%4)+1)%4)]==k.con[con1]){
							/*k.changePol(j, 3);
							if(addSteps(j, 3, 1)){end();}*/
							p2ready = true;
							//p2r = 4;
						}
						if(hpTurn==3){
							/*int temp = pol1;
							pol1 = pol2;
							pol2 = temp;*/
						}
						//System.out.println(p1ready+" "+p1r+" : "+p2ready+" "+p2r);
						//System.out.println("Anz vor ready Steps: "+k.step);
						if(p1ready&&p2ready){
							//System.out.println("Cross done");
							boolean isCorrect = false;
							boolean doNothing = false;
							while(!isCorrect){
								if((k.tri[p1c1]==k.con[con2]&&k.tri[p1c2]==k.con[con1])){
									isCorrect=true;
									goStep = 2;
								}else
								if((k.tri[p1c1]==k.con[con1]&&k.tri[p1c2]==k.con[con2])){
									isCorrect=true;
									goStep = 1;
								}else{
									//k.changePol(i, 1);
									if(addSteps(i, 1, 1)){end();}
								}
							}
						//	System.out.println("Anz Steps nach 1: "+k.step);
							
							isCorrect = false;
							while(!isCorrect){
								if((k.tri[p2c1]==k.con[con2]&&k.tri[p2c2]==k.con[con1])){
									isCorrect=true;
									goStep = 2;
								}else
								if((k.tri[p2c1]==k.con[con1]&&k.tri[p2c2]==k.con[con2])&&goStep==2){
									isCorrect=true;
									goStep = 1;
								}else
								if((k.tri[p2c1]==k.con[con1]&&k.tri[p2c2]==k.con[con2])){
									doNothing = true;
									isCorrect = true;
								}else{
									//k.changePol(j, 1);
									if(addSteps(j, 1, 1)){end();}
								}
							}
						//	System.out.println("Anz Steps nach 2: "+k.step);
							
							if(!doNothing){
								//Pol zurecht drehen
								//k.changePol(i, doTurn);
								if(addSteps(i, doTurn, 1)){end();}
								
								//k.changePol(j, 1);
								if(addSteps(j, 1, 1)){end();}
								
								//Kugel hin
								//k.turnKugel(pr, hpTurn);
								if(addSteps(pr, 1, 3)){end();}
								
								//Wechsel
								//k.changePol(pol1, goStep);
								if(addSteps(pol1, goStep, 1)){end();}
								
								//Kugel zurück
								//k.turnKugel(pr, ((hpTurn+2)%4));
								if(addSteps(pr, ((1+2)%4), 3)){end();}
								
								//Pol zurück
								//k.changePol(j, 3);
								if(addSteps(j, 3, 1)){end();}
								
								//k.changePol(i, ((doTurn+2)%4));
								if(addSteps(i, ((doTurn+2)%4), 1)){end();}
							}
							
							
						}
						
						
						
					}else{
						System.out.println("Error");
					}
					
					//System.out.println("Anz Steps: "+k.step);
					
					
					
				}else{
				//	System.out.println("Gegenüber oder gelöst");
				}
			}
		}
		//System.out.println("Done with CrossOver");
	}
	/**Welcher Pol ist gegenüber
	 * @param pol
	 * @return
	 */
	private int polGegenuber(int pol){
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
	private int gemeinsameCons(int pol1, int pol2, int i){
		if(pol1>pol2){
			/*int temp = pol1;
			pol1 = pol2;
			pol2 = temp;*/
			System.out.println("Error-->Klein später");
		}
		switch(pol1){
			case 0:
				switch(pol2){
					case 1:
						switch(i){
							case 0: return 2;
							case 1: return 6;
						}
					break;
					case 3:
						switch(i){
							case 0: return 1;
							case 1: return 5;
						}
					break;
					case 4:
						switch(i){
							case 0: return 1;
							case 1: return 2;
						}
					break;
					case 5:
						switch(i){
							case 0: return 5;
							case 1: return 6;
						}
					break;
				}
			break;
			case 1:
				switch(pol2){
					case 2:
						switch(i){
							case 0: return 3;
							case 1: return 7;
						}
					break;
					case 4:
						switch(i){
							case 0: return 2;
							case 1: return 3;
						}
					break;
					case 5:
						switch(i){
							case 0: return 6;
							case 1: return 7;
						}
					break;
				}
			break;
			case 2:
				switch(pol2){
					case 3:
						switch(i){
							case 0: return 0;
							case 1: return 4;
						}
					break;
					case 4:
						switch(i){
							case 0: return 3;
							case 1: return 0;
						}
					break;
					case 5:
						switch(i){
							case 0: return 7;
							case 1: return 4;
						}
					break;
				}
			break;
			case 3:
				switch(pol2){
					case 4:
						switch(i){
							case 0: return 0;
							case 1: return 1;
						}
					break;
					case 5:
						switch(i){
							case 0: return 4;
							case 1: return 5;
						}
					break;
				}
			break;
		}
		return -1;
	}
	private int ConPol2Tri(int con, int pol){
		switch(con){
		case 0:
			switch(pol){
				case 2: return 11;
				case 3: return 15;
				case 4: return 18;
			}
		break;
		case 1:
			switch(pol){
				case 0: return 3;
				case 3: return 12;
				case 4: return 19;
			}
		break;
		case 2:
			switch(pol){
				case 0: return 0;
				case 1: return 4;
				case 4: return 16;
			}
		break;
		case 3:
			switch(pol){
				case 1: return 7;
				case 2: return 8;
				case 4: return 17;
			}
		break;
		case 4:
			switch(pol){
				case 2: return 10;
				case 3: return 14;
				case 5: return 22;
			}
		break;
		case 5:
			switch(pol){
				case 0: return 2;
				case 3: return 13;
				case 5: return 23;
			}
		break;
		case 6:
			switch(pol){
				case 0: return 1;
				case 1: return 5;
				case 5: return 20;
			}
		break;
		case 7:
			switch(pol){
				case 1: return 6;
				case 2: return 9;
				case 5: return 21;
			}
		break;
		
		}
		return -1;
	}
	private ArrayList<String> advancedTurnMinimizer(ArrayList<String> arrayList){
		for(int i = 0; i<6;i++){
			int lastTurnPos = -1;
			int[] lastPos = new int[4];
			int anz = 0;
			Arrays.fill(lastPos,-1);
			for(int j = 0; j<arrayList.size()-1;j++){
				//pol, anz, modus
				int[] aktOpt = k.SplitDrehungFromSphere(arrayList.get(j));
				int[] akt = k.GetPolFromSphereString(arrayList.get(j),i);
				Arrays.sort(akt);
				Arrays.sort(lastPos);
				//Wenn aktueller Pol = i; Modus = 1
				if(aktOpt[0]==i&&aktOpt[2]==1){
					if(Arrays.equals(akt,lastPos)){
						anz+=aktOpt[1];
						anz%=4;
						arrayList.set(j, k.SphereWithoutDrehungAndStep(arrayList.get(j))+"n"+j+"n"+aktOpt[0]+""+(anz)+""+aktOpt[2]);
						arrayList.remove(lastTurnPos);
						lastPos = k.GetPolFromSphereString(arrayList.get(j),i);
						j-=3;
						lastTurnPos =-1;
						anz = 0;
						Arrays.fill(lastPos,-1);
					}else
					if(lastTurnPos==-1){
						lastTurnPos = j;
						anz+=aktOpt[1];
						lastPos = k.GetPolFromSphereString(arrayList.get(j),i);
					}else{
						lastTurnPos =-1;
						anz = 0;
						Arrays.fill(lastPos,-1);
					}
				}else{
					lastTurnPos =-1;
					anz = 0;
					Arrays.fill(lastPos,-1);
				}
			}
		}
		for(int i = 0; i<arrayList.size();i++){
			//Neu Nummerieren...
			arrayList.set(i,k.SphereWithoutDrehungAndStep(arrayList.get(i))+"n"+i+"n"+k.SplitDrehungFromSphereAsS(arrayList.get(i)));
			//System.out.println(arrayList.get(i));
		}
		return arrayList;
	}
	private ArrayList<String> advancedStepRemover(ArrayList<String> arrayList){
		for(int i = 0; i<arrayList.size()-2;i++){
			String sphere = k.SphereWithoutDrehungAndStep(arrayList.get(i));
			for(int j = i+1; j<arrayList.size()-1;j++){
				if(sphere==k.SphereWithoutDrehungAndStep(arrayList.get(j))){
					for(int l = j; l>i; l--){
						arrayList.remove(l);
					}
				}
			}
		}
		for(int i = 0; i<arrayList.size();i++){
			//Neu Nummerieren...
			arrayList.set(i,k.SphereWithoutDrehungAndStep(arrayList.get(i))+"n"+i+"n"+k.SplitDrehungFromSphereAsS(arrayList.get(i)));
			//System.out.println(arrayList.get(i));
		}
	
		
		
		
		
		return arrayList;
	}
}
