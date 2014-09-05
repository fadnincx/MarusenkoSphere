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

	//Kugel welche gelöst wird
	private Kugel k;
	
	//Zeigt, welche Felder schon gelöst sind
	protected boolean[] ok = new boolean[24];
	
	//ArrayList mit Lösungsweg
	private ArrayList<String> solvingWay = new ArrayList<String>();
	
	//Kugel die zu lösen ist
	private String sphereToSolve;
	
	/**
	 * Bei initialisieren des Objekts nichts tun
	 */
	public Solver(){}
	
	
	/**
	 * Fügt eine Kugeln zum Lösungsprozess hinzu
	 */
	private void addSteps(int pol, int anz, int modus){
		
		//Vollziehe die Drehung auf der Kugel
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
		
		//Die Aktion zur Lösungs-ArrayList hinzufügen
		solvingWay.add(k.getSphere(""+pol+anz+modus));
		
	}
	
	
	/**
	 * Gibt ArrayList mit Lösungsweg zum lösen der Kugel zurück
	 */
	protected ArrayList<String> solve(Kugel kugel){
		
		//Übernimmt die gegebene Kugel zum lösen
		k = kugel;
		
		//Speichert die Kugel als ausgangspunkt, damit diese nach den verschiedenen Strategien
		//zurück gesetzt werden kann
		sphereToSolve = k.getSphere("000");
		
		//Erstelle eine neue ArrayList, in welcher der schnellste Lösungsweg gespeichert wird
		ArrayList<String> bestSolvingWay = new ArrayList<String>();
		
		//Fülle die ersten 10000 Stellen mit dem Begriff "preSet", damit dies nicht der schnellste Weg ist eine Kugel zu lösen
		for(int i = 0; i<10000; i++){
			bestSolvingWay.add("preSet");
		}
		// TODO Anzahl Strategien = 2 und nicht 1
		//Anzahl Strategien, die durch gegangen werden
		int anzahlStrategien = 1;
		
		int besteStrategie = -1;
		
		//Gehe alle Strategien durch
		for(int i = 0; i<anzahlStrategien; i++){
			
			//Setzte die Kugel zurück in die Startposition
			k.fillKugelFromDebugString(sphereToSolve, false);
			
			//Setzte die StartKugel als erste Kugel
			solvingWay.add(k.getSphere("000"));
			
			//Erstelle Temporäre ArrayList mit der Lösung der Entsprechenden Strategie
			ArrayList<String> AL = strategieChooser(i);

			//TODO RemoveUnusedSteps
			//Entferne unnötige Schritte
			//RemoveUnusedSteps(AL);
			
			//Wenn die Lösung schneller ist, dann setze Strategie als neue bestSolvingWay
			if(AL.size()<bestSolvingWay.size()){
				bestSolvingWay = AL;
				besteStrategie = i;
			}
			
			//Setzte solvingWay zurück, damit nächste Strategie damit die Kugel lösen kann
			solvingWay = new ArrayList<String>();
			
		}
		
		//Die Anzahl an Schritten im bestSolvingWay
		int bestSolvingWayAnz = bestSolvingWay.size();
		
		//Gehe für alle durch
		for(int i = 0; i<bestSolvingWayAnz; i++){
			
			//Korrigiere den Step, welcher durch viele Manipulationen inzwischen korrupt ist
			bestSolvingWay.set(i, SphereUtils.getPureSphereCode(bestSolvingWay.get(i))+"n"+i+"n"+SphereUtils.getDrehungFromStringAsString(bestSolvingWay.get(i)));
			
		}
		
		//Verkleinert die bestSolvingWay auf das benötigte
		bestSolvingWay.trimToSize();
		
		//Print, welche Strategie beste ist
		String[] strategienName = new String[]{"StrategieStandard","StrategieOne"};
		System.out.println("Die Kugel wurde mit der "+strategienName[besteStrategie]+" erfolgreich gelöst");
		
		//Gib den schnellsten Lösungsweg zurück
		return bestSolvingWay;
		
		
	}
	
	/**
	 * startet die gewählte Strategie und gibt das Resulat zurück
	 */
	private ArrayList<String> strategieChooser(int strategie){
		
		//Switch für die Strategie Nummer
		switch(strategie){
		
			//Strategie 0
			case 0:
				return strategieStandard();
		
			//Strategie 1
			case 1:
				return strategieOne();

			//Wenn ungültige Strategie abgefragt wird, dann gib null zurück
			default:
				return null;
		}
		
	}
	
	/**
	 * Löst die Kugel gemäss der Standard-Taktik
	 */
	private ArrayList<String> strategieStandard(){
				
		//Array mit der Reihenfolge der Pole
		int[] pol = {0,1,4,2,3,5};
		
		//Gehe alle Pole durch
		for(int i = 0; i<6; i++){
			
			//Wenn der aktuele Pol nicht gelöst ist, dann lösen
			if(!SolveCheck.isPolSolved(pol[i], k)){
				
				
				//Prüfe, auf welche Seite gelöst werden soll
				if(strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+1)&&strategieStandardAreThis2Possible(pol[i]*4+2,  pol[i]*4+3)){
					System.out.println("Löse Pol "+pol[i]+" Position 0 und 1");
					strategieStandardSolve2Positions(pol[i]*4+0, pol[i]*4+1);
					System.out.println("Löse Pol "+pol[i]+" Position 2 und 3");
					strategieStandardSolve2Positions(pol[i]*4+2, pol[i]*4+3);
					break;
				}else
				if(strategieStandardAreThis2Possible(pol[i]*4+1,  pol[i]*4+2)&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+3)){
					System.out.println("Löse Pol "+pol[i]+" Position 1 und 2");
					strategieStandardSolve2Positions(pol[i]*4+1, pol[i]*4+2);
					System.out.println("Löse Pol "+pol[i]+" Position 0 und 3");
					strategieStandardSolve2Positions(pol[i]*4+0, pol[i]*4+3);
				}
				
			}
		}
		
		//Löse den Letzten Pol und gibt die Kugel zurück
		//return strategieOne();
		return solvingWay;
	}
	
	/**
	 * Löst 2 Positionen gemäss der Standart-Taktik
	 */
	private void strategieStandardSolve2Positions(int pos1, int pos2){
		
		if(strategieStandardAreThis2Possible(pos1,pos2)){
			int urPol = pos1/4;
			System.out.println("UrPol = "+urPol);
			
			int colorForPosition1 = k.con[SphereUtils.findCorrectConIndexFromTri(pos1)];
			int colorForPosition2 = k.con[SphereUtils.findCorrectConIndexFromTri(pos2)];
			System.out.println("Needed Colors, P1:"+pos1+" - "+colorForPosition1+" P2:"+pos2+" - "+colorForPosition2);
			
			boolean otherPartOfPolSolved = SphereUtils.otherPositionsOnPoleSolved(pos1, pos2, k);
			if(otherPartOfPolSolved){
				System.out.println("2. Teil des Pols");
			}else{
				System.out.println("1. Teil des Pols");
			}
			
			boolean color1OnUrPol = false;
			boolean color2OnUrPol = false;
			
			int hilfsPol = -1;
			
			if(!otherPartOfPolSolved){
				
				for(int i = 0; i<4; i++){
					
					if(!color1OnUrPol&&k.tri[urPol*4+i]==colorForPosition1){
						
						color1OnUrPol=true;
						System.out.println("Farbe 1 on UrPol");
					}
					
					if(!color2OnUrPol&&k.tri[urPol*4+i]==colorForPosition2){
						
						color2OnUrPol=true;
						System.out.println("Farbe 2 on UrPol");
						
					}					
					
				}
				
			}
			boolean[] polRing = SphereUtils.getPolBand(pos1,pos2);
			int[] polRanking = new int[6];
			
			for(int i=0; i<6; i++){
				
				if(i!=urPol&&!SolveCheck.isPolSolved(i, k)&&polRing[i]){
					
					polRanking[i]+=5;
					
					boolean color1 = false;
					boolean color2 = false;
					
					for(int j = 0; j<4; j++){
						
						if(otherPartOfPolSolved||!color1OnUrPol){
							
							if(!color1&&k.tri[i*4+j]==colorForPosition1){
								
								color1=true;
								polRanking[i]+=10;
								
							}
							
						}
						
						if(!color2&&k.tri[i*4+j]==colorForPosition2){
							
							color2=true;
							polRanking[i]+=10;
							
						}					
						
					}

				}
				
			}
			
			int anzPunkteForPol = 0;
			System.out.println("PolRanking "+Arrays.toString(polRanking));
			for(int i=0; i<6; i++){
				
				if(polRanking[i]>anzPunkteForPol){
					
					anzPunkteForPol = polRanking[i];
					hilfsPol = i;
					
				}
				
			}
			System.out.println("HilfsPol: "+hilfsPol);
			
			if(hilfsPol != -1){
				
				int[] options = SphereUtils.strategieStandartGetTurnOpt(pos1, pos2, hilfsPol);
				
				int[] positions = options[2]==1?new int[]{pos1,pos2}:new int[]{pos2,pos1};
				
				int[] colors = options[2]==1?new int[]{colorForPosition1,colorForPosition2}:new int[]{colorForPosition2,colorForPosition1};
				
				int[] positionNrOnUrPol = new int[]{-1,-1};
				
				int[] positionNrOnHilfsPol = new int[]{-1,-1};
				
				boolean[] positionsOnUrPol = new boolean[]{false,false};
				
				boolean[] positionsOnHilfsPol = new boolean[]{false,false};
				
				for(int i = 0; i<4; i++){
				
					if(!otherPartOfPolSolved&&!positionsOnUrPol[0]&&k.tri[urPol*4+i]==colors[0]){
						
						positionsOnUrPol[0]=true;
						
						positionNrOnUrPol[0]=i;
					
					}
					
					if(!positionsOnUrPol[1]&&k.tri[urPol*4+i]==colors[1]){
						
						positionsOnUrPol[1]=true;
						
						positionNrOnUrPol[1]=i;
						
					}
					
					if(!otherPartOfPolSolved&&!positionsOnHilfsPol[0]&&k.tri[hilfsPol*4+i]==colors[0]){
						
						positionsOnHilfsPol[0]=true;
						
						positionNrOnHilfsPol[0]=i;
					
					}
					
					if(!positionsOnHilfsPol[1]&&k.tri[hilfsPol*4+i]==colors[1]){
						
						positionsOnHilfsPol[1]=true;
						
						positionNrOnHilfsPol[1]=i;
						
					}
					
				}
				
				if(positionsOnUrPol[1]&&!positionsOnHilfsPol[1]){
					System.out.println("Farbe2 auf UrPol aber nicht auf HilfsPol");
					//Position 2 auf Hilfspol verschieben
					
					int posHilf = positionNrOnHilfsPol[0]==0?3:0;
					System.out.println("Exact - Pos2ToHPol ("+k.step+"): "+(urPol*4+positionNrOnUrPol[1])+", "+posHilf);
					System.out.println("Positionen bevor drehen: "+k.tri[(urPol*4+positionNrOnUrPol[1])]+";"+posHilf);
					changeExact2Positions((urPol*4+positionNrOnUrPol[1]), posHilf,true);
					System.out.println("Positionen nach drehen: "+k.tri[(urPol*4+positionNrOnUrPol[1])]+";"+posHilf);
					System.out.println("Exact end ("+k.step+")");
					positionsOnHilfsPol[1] = true;
					
				}
				
				if(positionsOnUrPol[0]){
					
					//Position 1 an korrekte position drehen
					
					positionNrOnUrPol[0]=-1;
					
					positionsOnUrPol[0]=false;
					
					for(int i = 0; i<4;i++){
						
						if(!otherPartOfPolSolved&&k.tri[positions[1]]==colors[0]){
							System.out.println("If pos 0 on urpol turn correct");
							addSteps(urPol, 1, 1);
						
						}
						
					}	
					
				}else{
					
					if(!positionsOnHilfsPol[0]){
						
						int changePosition = -1;
						
						for(int i = 0; i<6;i++){
						
							if(i!=urPol&&i!=hilfsPol&&!SolveCheck.isPolSolved(i, k)){
								
								for(int j = 0; j<4; j++){
									
									if(k.tri[i*4+j]==colors[0]&&changePosition==-1){
										changePosition=i*4+j;
									}
									
								}
								
							}
							
						}
						
						int hilfsPos = positionNrOnHilfsPol[1]==0?2:0;
						
						positionNrOnHilfsPol[0]=hilfsPos;
						System.out.println("Exact - Pos1ToHPol ("+k.step+"): "+changePosition+", "+(hilfsPol*4+hilfsPos));
						changeExact2Positions(changePosition, (hilfsPol*4+hilfsPos));
						System.out.println("Exact end ("+k.step+")");
						
						
						
					}
					
					//Position 1 von Hilfspol auf korrekte position drehen
					System.out.println("Exact - Pos1ToCorrectPos ("+k.step+"): "+(hilfsPol*4+positionNrOnHilfsPol[0])+", "+positions[1]);
					changeExact2Positions((hilfsPol*4+positionNrOnHilfsPol[0]), positions[1], true);
					System.out.println("Exact end ("+k.step+")");
					
				}
				
				if(!positionsOnHilfsPol[1]){
					
					int changePosition = -1;
					
					for(int i = 0; i<6;i++){
					
						if(i!=urPol&&i!=hilfsPol&&!SolveCheck.isPolSolved(i, k)){
							
							for(int j = 0; j<4; j++){
								
								if(k.tri[i*4+j]==colors[1]&&changePosition==-1){
									changePosition=i*4+j;
								}
								
							}
							
						}
						
					}
										
					positionNrOnHilfsPol[0]=hilfsPol*4;
					System.out.println("Exact - Pos2ToHPol ("+k.step+"): "+changePosition+", "+hilfsPol);
					changeExact2Positions(changePosition, hilfsPol*4);
					System.out.println("Exact end ("+k.step+")");
					positionNrOnHilfsPol[1]=0;
					
				}
				
				System.out.println("Exact - Pos2ToCorrectPol ("+k.step+"): "+(hilfsPol*4+positionNrOnHilfsPol[1])+", "+positions[1]);
				changeExact2Positions((hilfsPol*4+positionNrOnHilfsPol[1]), positions[1],true);
				System.out.println("Exact end ("+k.step+")");
				
				
				
				
				//Position 2 von Hilfspol auf korrekte position drehen
				
				
				
				
				
			}else{
				System.out.println("HilfsPol = -1");
			}
			
		}else{
			System.out.println("Lösen unmöglich von "+pos1+":"+pos2);
		}
		
		/*
		
		
		//TODO alter Teil der Funktion
		
		
		
		
		//Sofern dies möglich ist
		if(strategieStandardAreThis2Possible(pos1,pos2)){
			
			//Für beide Positionen die Angrenzenden Pole abfragen
			int[] pole1 = SphereUtils.getPoleForPosition(pos1);
			int[] pole2 = SphereUtils.getPoleForPosition(pos2);
			
			//Pol auf dem beide sind speichern
			int UrPol = pos1/4;
			
			//Array mit möglichen Polen --> Pol an den nur einer Grenz
			int[] poleMoeglich = new int[2]; 
			
			//Farben, welche an diesen Positionen benötigt werden
			int[] neededColors = new int[] {k.con[SphereUtils.findCorrectConIndexFromTri(pos1)],k.con[SphereUtils.findCorrectConIndexFromTri(pos2)]};
			
			//Variable um zu fragen, ob die beiden positionen überhaupt einen gemeinsamen Nachbarspol haben (Voraussetzung)
			boolean everSame = false;
			
			//Gehe bei der ersten Position alle durch
			for(int i = 0; i<2; i++){
				
				//Variable mit dem aktuellen Status
				boolean same = false;
				
				//Variable um bei ungleichheit die Nummer zu speichern
				int notSame = -1;
				
				//Gehe bei der 2 Position alle durch
				for(int j = 0; j<2;j++){
					
					//Wenn die beiden gleich sind
					if(pole1[i]==pole2[j]){
						
						//setzte aktuelle variable auf true
						same = true;
						
					//Ansonsten	
					}else{
						
						//Speichere die aktuelle Nummer
						notSame = j;
					}
					
				}
				
				//Wenn same falsch ist
				if(!same){
					
					//Die möglichen Pole speichern
					poleMoeglich[0]=pole1[i];
					poleMoeglich[1]=pole2[notSame];
					
					//Variable auf true setzten, damit grundvoraussetzung gegeben ist
					everSame = true;
				}
				
			}
			
			
			//Pol, mit dem der wechsel wirklich ausgeführt wird
			int poleToDo = -1;
			
			//Die Anzahl farben, welche auf diesen Pol verschoben werden müssen
			int anzNeededColors = -1;
			
			//ist grundvoraussetzung gebenen
			if(everSame){
				
				//Gehe die beiden Pole durch
				for(int i = 0; i<2; i++){
					
					//Wenn ein Pol noch nicht gelöst ist
					if(!SolveCheck.isPolSolved(poleMoeglich[i], k)){
						
						//Die benötigte Anzahl  = 0
						int aktAnzNeeded = 0;
						
						//Farbe für Position 1 gefunden
						boolean p1Found = false; 
						
						//Farbe für Position 2 gefunden
						boolean p2Found = false;
						
						//Gehe die 4 Positionen eines Pol durch
						for(int j = 0; j<4; j++){
							
							//Wenn die Farbe für Position 1 gefunden
							if(k.tri[poleMoeglich[i]*4+j]==neededColors[0]&&!p1Found){
								
								//Anzahl ++
								aktAnzNeeded++;
								
								//Position 1 gefunden = wahr
								p1Found = true;
								
							}
							
							//Wenn die Farbe für Position 2 gefunden
							if(k.tri[poleMoeglich[i]*4+j]==neededColors[1]&&!p2Found){
								
								//Anzahl ++
								aktAnzNeeded++;
								
								//Position 2 gefunden = wahr
								p2Found = true;
								
							}
							
						}
						
						//Wenn der Pol besser ist, als der vorhergehende
						if(aktAnzNeeded>anzNeededColors){
							
							//Pol, auf welchem durch geführt wird setzten
							poleToDo = poleMoeglich[i];
							
							//Anzahl farben setzten
							anzNeededColors = aktAnzNeeded;	
						}
						
					}
					
				}
				
			}

			//Wenn ein Pol gefunden wurde
			if(poleToDo!=-1){
				
				//Array mit dem Status, welche Pole bereits gelöst sind
				boolean[] okPole = new boolean[6];
				
				//Fülle das Array --> gehe für alle Pole durch
				for(int i = 0; i<6; i++){
					
					//Wenn Pol gelöst ist, dann soll er wahr gesetzt werden, sonst falsch
					okPole[i] = SolveCheck.isPolSolved(i, k);
					
				}
				

				//Variable, Farbe für Position 1 ist auf dem Pol vorhanden
				boolean color1OnPole = false;
				
				//Variable, Farbe für Position 2 ist auf dem Pol vorhanden
				boolean color2OnPole = false;
				
				//Variable, Position mit der Farbe für Position 1
				int color1Pos = -1;
				
				//Variable, Position mit der Farbe für Position 2
				int color2Pos = -1;
				
				
				//Array mit den beiden anderen Positionen auf dem Ursprünglichen Pol
				int[] positionenOnUrPol = SphereUtils.otherPositionsOnPole(pos1, pos2);
				
				//Wenn die beiden anderen Positionen auf dem Ursprünglichen Pol noch nicht gelöst wurden
				if(!SolveCheck.isPositionSolved(positionenOnUrPol[0], k)&&!SolveCheck.isPositionSolved(positionenOnUrPol[1], k)){
					
				}

				// TODO ist von Funktion übernommen worden
				

				//Gehe für alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Ist die Farbe für Position 1 vorhanden?
					if(k.tri[poleToDo*4+i]==neededColors[0]&&!color1OnPole){
						
						//Variable, Farbe für Position 1 vorhanden auf wahr setzen
						color1OnPole = true;
						
						//Aktuelle Position speichern
						color1Pos = poleToDo*4+i;
						
					}
					
					//Ist die Farbe für Position 2 vorhanden?
					if(k.tri[poleToDo*4+i]==neededColors[1]&&!color2OnPole){
						
						//Variable, Farbe für Position 2 vorhanden auf wahr setzen
						color2OnPole = true;
						
						//Aktuelle Position speichern
						color2Pos = poleToDo*4+i;
						
					}
					
				}
				
				//Wenn die Farbe für Position 1 nicht auf dem Pol vorhanden ist
				if(color1Pos==-1){
					
					//Gehe alle Positionen durch
					for(int i = 0; i<4; i++){
						
						//Position gefunden auf falsch
						boolean pFound = false;
						
						//Wenn nicht bereits die Position für Farbe 2 und Position nocht nicht gefunden
						if(poleToDo*4+i!=color2Pos&&poleToDo*4+i+1!=color2Pos&&!pFound){
							
							//Als Position von Farbe 1 speichern
							color1Pos = poleToDo*4+i;
							
						}
						
					}
					
				}
				
				//Gehe alle 1 Pole durch
				for(int i = 0; i<6; i++){
					
					//Wenn der Pol noch nicht gelöst ist und Farbe 1 noch nicht gefunden
					if(!okPole[i]&&!color1OnPole){
						
						//Gehe alle Positioinen durch
						for(int j = 0; j<4; j++){
							
							//Wenn die Farbe 1 gefunden
							if(k.tri[i*4+j]==neededColors[0]){
								
								//Verschiebe auf die vorherbestimmte Postion  
								changeExact2Positions(color1Pos, i*4+j);
								
								//Setzte Variable auf wahr, dass Position für Farbe 1 gelöst ist
								color1OnPole = true;
								
							}
							
						}
						
					}
					
				}
				
				//Setze die Positionen der Farben zurück
				color1Pos = -1;
				color2Pos = -1;
				color1OnPole = false;
				color2OnPole = false;
				
				//Gehe alle Positionen des pols erneut durch
				for(int i = 0; i<4; i++){
					
					//Wenn Farbe 1 gefunden
					if(k.tri[poleToDo*4+i]==neededColors[0]&&!color1OnPole){
						
						//Variable für Farbe 1 auf wahr
						color1OnPole = true;
						
						//Position speichern
						color1Pos = poleToDo*4+i;
						
					}
					
					//Wenn Farbe 2 gefunden
					if(k.tri[poleToDo*4+i]==neededColors[1]&&!color2OnPole){
						
						//Variable für Farbe 2 auf wahr
						color2OnPole = true;
						
						//Position speichern
						color2Pos = poleToDo*4+i;
						
					}
					
				}
				
				//Wenn Farbe 2 noch nicht gefunden
				if(color2Pos==-1){
					
					//Gehe alle Positionen durch
					for(int i = 0; i<4; i++){
						
						//Position gefunden auf falsch
						boolean pFound = false;
						
						//Wenn nicht bereits die Position für Farbe 1 und Position nocht nicht gefunden
						if(poleToDo*4+i!=color1Pos&&poleToDo*4+i+1!=color1Pos&&!pFound){
							
							//Als Position von Farbe 2 speichern
							color2Pos = poleToDo*4+i;
							
						}
						
					}
					
				}
				
				//Gehe alle 6 Pole durch
				for(int i = 0; i<6; i++){
					
					//Wenn der Pol noch nicht gelöst ist und Farbe 2 noch nicht gefunden
					if(!okPole[i]&&!color2OnPole){
						
						//Gehe alle Positioinen durch
						for(int j = 0; j<4; j++){
							
							//Wenn Farbe 2 gefunden
							if(k.tri[i*4+j]==neededColors[1]){
								
								//Verschiebe auf die vorherbestimmte Position
								changeExact2Positions(color2Pos, i*4+j);
								
								//Setzte Variable auf wahr, dass Position für Farbe 2 gelöst ist
								color2OnPole = true;
							}
							
						}
						
					}
					
				}
				
				// TODO Ende
				
				//Die Farben auf den Pol kriegen, sofern sie noch nicht vorhanden sind
				
				//Bekomme die Position, auf welche die Farben verschoben werden müssen, um zu passen
				int posOnPol = SphereUtils.strategieStandardGetPosOnPol(pos1, pos2, poleToDo);
				
				//Bekomme den Pol mit welchem gedreht wird
				int polDrehung = SphereUtils.strategieStandardGetPolForDrehung(pos1, pos2, poleToDo);
				
				//Bekomme die weiteren Optionen, mit welchen gedreht wird
				int[] optionens = SphereUtils.strategieStandartGetTurnOpt(pos1, pos2, poleToDo);
				
				//Prüfe, dass der Index 0 zu Position 1 und Index 1 zu Position 2 passt, ansonsten die beiden Positionen tauschen
				if(neededColors[0]==k.con[SphereUtils.findCorrectConIndexFromTri(pos2)]&&neededColors[1]==k.con[SphereUtils.findCorrectConIndexFromTri(pos1)]){
					
					//Dreieckstausch --> Variabel 1 in temp
					int temp = neededColors[0];
					
					//Variable1 mit 2 überschreiben
					neededColors[0] = neededColors[1];
					
					//Variable2 mit temp überschreiben
					neededColors[1] = temp;
					
				}
				
				
				//Speichert die benötigten Farben im Array
				//Wenn optiones[2]==2 ist, dann müssen die Werte in umgekehrter Reihenfolge übernommen werden
				int[] colors = (optionens[2]==2) ? new int[]{neededColors[1],neededColors[0]} : new int[]{neededColors[0],neededColors[1]};

				//Gehe maximal 4 mal durch 
				for(int i = 0; i<4; i++){
					
					//Wenn die benötigte Farbe nicht an der benötigten Position ist
					if(k.tri[posOnPol]!=colors[0]){
						
						//Drehe den Pol eins weiter
						addSteps(poleToDo, 1, 1);
						
					}
					
				}
				
				//Drehe die Halbe Kugel
				addSteps(polDrehung, optionens[0], 3);
				
				//Drehe den Pol, um die erste Farbe an die Position der 2 zu verschieben
				addSteps(UrPol, optionens[1], 1);
				
				//Drehe die Halbe Kugel zurück
				addSteps(polDrehung, 4-optionens[0], 3);
				

				//Gehe maximal 4 mal durch
				for(int i = 0; i<4; i++){
					
					//Wenn die benötigte Farbe nicht and der benötigten Position ist
					if(k.tri[posOnPol]!=colors[1]){
						
						//Drehe den Pol eins weiter
						addSteps(poleToDo, 1, 1);
						
					}
					
				}
				
				//Drehe die Halbe Kugel
				addSteps(polDrehung, optionens[0], 3);
				
				//Drehe den Pol, um beide Farben an die korrekte Position zu verschieben
				addSteps(UrPol, optionens[1], 1);
				
				//Drehe die Halbe Kugel zurück
				addSteps(polDrehung, 4-optionens[0], 3);
				
			}
		
	
		}else{
			System.out.println("Lösen der Positionen nicht möglich ("+pos1+","+pos2+")");
		}*/
		
	}
		
	/**
	 * Gibt zurück, ob das wechseln der beiden Positionen möglich ist
	 */
	private boolean strategieStandardAreThis2Possible(int pos1, int pos2){
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole1 = SphereUtils.getPoleForPosition(pos1);
		
		//Bekomme die beiden Pole, welche an die Position grenzen
		int[] pole2 = SphereUtils.getPoleForPosition(pos2);
		
		//Variable mit den beiden möglichen Polen
		int[] poleMoeglich = new int[2]; 
		
		//Variable um zu fragen, ob die beiden positionen überhaupt einen gemeinsamen Nachbarspol haben (Voraussetzung)
		boolean everSame = false;
		
		
		//Gehe bei der ersten Position alle durch
		for(int i = 0; i<2; i++){
			
			//Variable mit dem aktuellen Status
			boolean same = false;
			
			//Variable um bei ungleichheit die Nummer zu speichern
			int notSame = -1;
			
			//Gehe bei der 2 Position alle durch
			for(int j = 0; j<2;j++){
				
				//Wenn die beiden gleich sind
				if(pole1[i]==pole2[j]){
					
					//setzte aktuelle variable auf true
					same = true;
					
				//Ansonsten	
				}else{
					
					//Speichere die aktuelle Nummer
					notSame = j;
				}
				
			}
			
			//Wenn same falsch ist
			if(!same){
				
				//Die möglichen Pole speichern
				poleMoeglich[0]=pole1[i];
				poleMoeglich[1]=pole2[notSame];
				
				//Variable auf true setzten, damit grundvoraussetzung gegeben ist
				everSame = true;
			}
			
		}
		//ist grundvoraussetzung gebenen
		if(everSame){
			
			//Gehe die beiden Pole durch
			for(int i = 0; i<2; i++){
				
				//Wenn ein Pol noch nicht gelöst ist
				if(!SolveCheck.isPolSolved(poleMoeglich[i], k)){
					
					//Dann gibt wahr zurück
					return true;
					
				}
				
			}
			
		}	
		
		//Ansonsten gib falsch zurück
		return false;
		
	}
	
	/**
	 * Übernimmt das lösen der Kugel gemäss Strategie One
	 */
	private ArrayList<String> strategieOne(){
		
		//Array ok ganz mit false füllen
		Arrays.fill(ok, false); 
		
		//Array mit korrekten Werten füllen
		strategieOnecheckIfPositionOnKorrektPole();

		//Solange bis Array ok ganz true ist
		while(!SolveCheck.ArrayIsFullyOk(ok)){
			
			//Variable zum erneut Prüfen = true
			boolean recheck = true;
			
			//Gehe alle 24 Positionen durch
			for(int i = 0; i<24; i++){
				
				//Wenn recheck gefordert checke erneut
				if(recheck){
					
					//recheck auf falsch setzten
					recheck = false;
					
					//erneut Prüfen
					strategieOnecheckIfPositionOnKorrektPole();
					
				}
				
				//Wenn Position noch nicht gelöst
				if(!ok[i]){
					
					//Recheck gefordert, weil etwas geändert wird
					recheck = true;
					
					//Wenn keine Position gefunden wird, welche zum lösen dient, dann breche das Lösen ab und melde Error
					if(strategieOneFindPositionToSolve(i)==-1){
						Log.ErrorLog("Achtung Abbruch des Lösen");
						return solvingWay;
					}
					
					//Wechle die Positionen von i und dem mit findPos gefundenen Position
					change2Positions(i,strategieOneFindPositionToSolve(i));
					
					//Prüfe erneut
					strategieOnecheckIfPositionOnKorrektPole();
					
					//wenn nur noch 2 Positionen nicht gelöst sind, dann Sonderfall ausführen
					if(SphereUtils.getAnzFalseInArray(ok)==2){
						
						//Variable für die 1 Position
						int pos1 = -1;
						
						//Variable für die 2. Position
						int pos2 = -1;
						
						//Variable ob Position 1 gefunden wurde
						boolean setOne = false;
						
						//Gehe alle 24 Positionen durch
						for(int j = 0; j<24; j++){
							
							//Wenn Position nocht nicht gelöst
							if(!ok[j]){
								
								//Wenn Position 1 noch nicht gesetzt
								if(!setOne){
									
									//Position 1 setzten
									pos1 = j;
									
									//Variable, das Position 1 gesetzt wurden auf wahr stellen
									setOne = true;
									
								//Ansonsten	
								}else{
									
									//Position 2 setzten
									pos2 = j;
									
								}	
								
							}
							
						}
						
						//Löse die beiden Positionen
						change2Positions(pos1, pos2);
						
					}
					
				}
				
			}
			
		}
		//Array ok erneut zurück auf false setzen
		Arrays.fill(ok, false); 
		
		//Drehe Pole in Optimale Position
		turnPolToOptinalPosition();

		//Pol schneller lösen
		for(int i = 0; i<24;i++){
			int polI = i/4;
			int[] pos = SphereUtils.crossOverPos(i);			
			int j = polI*4+(((i%4)+1)%4);
			int pol2 = pos[0]/4;
			
			if(k.tri[i]==k.tri[pos[0]]&&k.tri[j]==k.tri[pos[1]]&&k.tri[i]==k.con[SphereUtils.findCorrectConIndexFromTri(j)]&&k.tri[j]==k.con[SphereUtils.findCorrectConIndexFromTri(i)]){
				int polDrehen = SphereUtils.getCrossOverPol(i);
				addSteps(polI, 1, 1);
				addSteps(pol2, 1, 1);
				addSteps(polDrehen, 1, 3);
				addSteps(polI, 2, 1);
				addSteps(polDrehen, 3, 3);
				addSteps(polI, 3, 1);
				addSteps(pol2, 3, 1);
			}
			
		}
		
		//Prüfe ob Kugel noch Korrekt ist, oder ob es einen Fehler gibt
		strategieOnecheckIfPositionOnKorrektPole();
		
		//Wenn keinen Fehler gefunden
		if(SolveCheck.ArrayIsFullyOk(ok)){
			
			//So lange die Kugel nicht gelöst ist...
			while(!SolveCheck.isKugelSolved(k)){
				
				//Gehe Jeden Pol durch
				for(int i = 0; i<6; i++){
					
					//Wenn Pol noch nicht gelöst, löse ihn
					if(!SolveCheck.isPolSolved(i,k)){		
						
						//Array mit werten, ob eine Position schon gelöst wurde, oder nicht
						boolean[] pOk = new boolean[4];
						
						//Gehe für jede Position durch
						for(int j = 0; j<4; j++){
							
							
							pOk[j] = SolveCheck.isPositionSolved(i*4+j,k);
							
						}
						
						//Solange Pol nicht gelöst, noch einen Anlauf wagen 
						while(!SolveCheck.isPolSolved(i,k)){
							
							//Gehe jede Position durch
							for(int j = 0; j<4; j++){
								
								//Wenn noch nicht korrekt
								if(!pOk[j]){
									
									//Finde das Verbindungsstück mit der selben Farbe heraus
									int con = SphereUtils.findCorrectConIndexFromTri(i*4+j);
									
									//Gehe für alle Positionen durch
									for(int l = 0; l<4; l++){
										
										//Wenn das Dreieck gefunden wurde, mit welchem getauscht werden muss
										if(k.tri[i*4+l]==k.con[con]&&l!=j){
											
											//Tausche die beiden Positionen
											change2PositionsOnOnePol(i*4+j, i*4+l);
										}
										
									}
									
									//Gehe alle Positionen durch
									for(int l = 0; l<4; l++){
										
										//Ist Position gelöst? Antwort in Array speichern
										pOk[l] = SolveCheck.isPositionSolved(i*4+l,k);
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}	
			
		}
		
		//Kugel zurück geben wobei noch unnötige schritte entfehrt werden
		return solvingWay;
	}
	
	/**
	 * Prüft welche Positionen auf dem korrekten Pol sind
	 */
	private void strategieOnecheckIfPositionOnKorrektPole(){
		
		// Gehe alle Pole durch
		for(int i = 0; i<6; i++){
			/**
			 * int[4] cons ==> Array in dem die 4 Con eines Pols gespeichert werden
			 * int[4] tris ==> Array in dem die 4 Tri eines Pols gespeichert werden
			 * boolean[4] allowCons ==> Ist bei entsprechendem Index true, wenn Con noch nicht verbraucht ist
			 */
			//Array mit den 4 Farbwerten der Verbindungsstücke
			int[] cons = new int[4];
			
			//Array mit Werten, welche Farbe schon gebraucht wurde und welche nicht
			boolean[] allowCons = new boolean[4];
			
			//Array mit den 4 Farbwerten der Dreiecken
			int[] tris = new int[4];

			
			//Arrays mit Daten aus Kugel füllen
			for(int j = 0; j<4; j++){	 
				
				//den Farbwert des passenden Con vom Index des Tri
				cons[j] = k.con[SphereUtils.findCorrectConIndexFromTri(i*4+j)];
				
				//Alle Farben sind zu begin noch erlaubt
				allowCons[j] = true;
				
				//Wert von tri bei Index Pol*4 + Position in Pol
				tris[j] = k.tri[i*4+j];
				
			}
			
			//Gehe nun die 4 Position eines Pols durch
			for(int j = 0; j<4; j++){
				
				//Variable mit info, ob Position schon neu gesetzt wurde
				boolean set = false;
				
				//Prüfe für alle zur Verfügung stehenden cons ob sie passen
				for(int k = 0; k<4; k++){
					
					//Wenn Position noch nicht gesetzt wurde
					//Wenn die Farbe noch erlaubt ist
					//Wenn die beiden Farben übereinstimmen
					if(!set&&allowCons[k]&&tris[j]==cons[k]){
						
						//Setzte die Position im Array ok auf wahr
						ok[i*4+j] = true;
						
						//Streiche die Farbe von der Liste der erlaubten
						allowCons[k] = false;
						
						//Auf true wechseln, dass Position schon gesetzt wurde
						set = true;
						
					}
					
				}
				
				//Wenn Position jetzt noch nicht neu gesetzt wurde, dann gehört das Dreieck nicht auf diesen Pol
				if(!set){
					
					//Setzte die Position im Array ok auf falsch
					ok[i*4+j] = false;
				}
				
			}
			
		}
		
	}
		
	/**
	 * Gibt zurück mit welchem Index die Position gelöst werden kann
	 */
	private int strategieOneFindPositionToSolve(int p){
		
		//Den Pol, auf welchem die gegebene Position liegt
		int urPol = p/4;
		
		//Finde den richtigen Connector für p
		int con = strategieOneReturnIndexOfConInSameColor(p);
		
		//Gibt die 3 möglichen Positionen des gefundenen Cons zurück
		int[] polPos = SphereUtils.conToPos(con);
		
		//Gehe für die drei möglichen Pole durch
		for(int i = 0; i<3; i++){
			
			//PolNr = Position / 4
			int polNr = polPos[i]/4;

			//Wenn Phase 1 bei diesem Pol noch nicht abgeschlossen wurde dann kommt er in frage und wird weiter untersucht
			if(!strategieOneCheckPoleHasAllColors(polNr)&&urPol!=polNr){
				
				//Bei dem Pol wird jede Stelle durch gegeangen
				for(int j = 0; j<4; j++){
					
					//Prüfe, ob Position noch nicht korrekt ist und dass die beiden Farben nicht Identisch sind
					if(!ok[polNr*4+j]&&k.tri[polNr*4+j]!=k.tri[p]){
						
						//Gibt den Tri index zurück, damit zwischen diesen Beiden gewechselt werden kann
						return polNr*4+j;
					}
					
				}
				
			}
			
		}
		
		//Bei einem Fehler gib -1 zurück
		return -1;
		
	}
	
	/**
	 * Gibt true zurück, wenn ein Pol alle benötigten Farben beinhaltet
	 */
	private boolean strategieOneCheckPoleHasAllColors(int polNr){
		
		//Array mit den Werten für tri
		int[] tris = new int[4];
		
		//Array mit den Werten von con
		int[] cons = new int[4];
		
		//Fülle Arrays mit Daten aus Kugel
		for(int i = 0; i<4; i++){
			tris[i]=k.tri[polNr*4+i];
			cons[i]=k.con[SphereUtils.findCorrectConIndexFromTri(polNr*4+i)];
		}

		//Sortiere die Arrays
		Arrays.sort(tris);
		Arrays.sort(cons);
		
		//Wenn die Arrays gleich sind, dann gib true zurück, sonst false
		return Arrays.equals(tris, cons) ? true:false;
		
	}
	
	/**
	 * Gibt den Index des Conns mit der selben Farbe wie die gegebene Position zurück
	 */
	private int strategieOneReturnIndexOfConInSameColor(int p){
		
		//Gehe alle 8 Connector durch
		for(int i = 0; i<8; i++){
			
			//Wenn die Farbe des gegebenen Tri gleich der Farbe des Con
			if(k.con[i]==k.tri[p]){
				
				//Gib den Index des Con zurück
				return i;
				
			}
			
		}
		
		//Wenn die Kugel kein Verbindungsstück dieser Farbe enthält, dann gib -1 zurück
		return -1;
	}
	
	/**
	 * Dreht eine Farbe an eine bestimmte Position auf dem Pol
	 */
	private void strategieOneTurnColorToPositionOnPol(int p, int zielPos){

		//Bekomme den Pol
		int pol = p/4;
		
		//Speichere die Farbe
		int color = k.tri[p];
		
		//Gehe maximal 4 mal durch
		for(int i = 0; i<4;i++){
			
			//Wenn Farbe noch nicht stimmt
			if(k.tri[pol*4+zielPos]!=color){
				
				//Eins weiter drehen
				addSteps(pol,1,1);
				
			}
			
		}
		
	}
	private void changeExact2Positions(int p1, int p2){
		changeExact2Positions(p1, p2, false);
	}
	private void changeExact2Positions(int p1, int p2,boolean overflow){
		//Überprüft die Positionen auf legale werte --> Min 0, Max 23	--> nicht den selben wert
				if(p1>=0&&p2>=0&&p1<=23&&p2<=23&&p1!=p2){
					
					//Wenn p1 grösser als p2
					if(p1>p2){
						
						//Variablen tauschen
						int temp = p1;
						p1 = p2;
						p2 = temp;
						
					}
					
					//Pol von p1 speichern
					int pol1 = p1/4;
					
					//Pol von p2 speichern
					int pol2 = p2/4;
									
					//wenn nicht auf dem selben Pol
					if(pol1!=pol2){
						
						//Variabel zur End Positionsbestimmung festlegen und mit Werten füllen, so dass nichts geändert wird 						
						int positionOnPol1 = -1;
						int positionOnPol2 = -1;
						int halbeKugelDrehenUm = 0;
						int polDrehenRechts = -1;
						int polDrehen = -1;
						
						
						switch(pol1){
							case 0:
								switch(pol2){
									case 1: positionOnPol1 = 3; positionOnPol2 = 6; halbeKugelDrehenUm = 1; polDrehenRechts = 5; polDrehen = 0; break;
									case 2: positionOnPol1 = 3; positionOnPol2 = 10; halbeKugelDrehenUm = 2; polDrehenRechts = 5; polDrehen = 0; break;
									case 3: positionOnPol1 = 3; positionOnPol2 = 13; halbeKugelDrehenUm = 1; polDrehenRechts = 5; polDrehen = 0; break;
									case 4: positionOnPol1 = 3; positionOnPol2 = 16; halbeKugelDrehenUm = 1; polDrehenRechts = 1; polDrehen = 0; break;
									case 5: positionOnPol1 = 0; positionOnPol2 = 22; halbeKugelDrehenUm = 1; polDrehenRechts = 3; polDrehen = 0; break;
								}
							case 1:
								switch(pol2){
									case 2: positionOnPol1 = 4; positionOnPol2 = 10; halbeKugelDrehenUm = 1; polDrehenRechts = 5; polDrehen = 1; break;
									case 3: positionOnPol1 = 4; positionOnPol2 = 13; halbeKugelDrehenUm = 2; polDrehenRechts = 5; polDrehen = 1; break;
									case 4: positionOnPol1 = 4; positionOnPol2 = 17; halbeKugelDrehenUm = 1; polDrehenRechts = 2; polDrehen = 1; break;
									case 5: positionOnPol1 = 7; positionOnPol2 = 23; halbeKugelDrehenUm = 1; polDrehenRechts = 0; polDrehen = 1; break;
								}
							case 2:
								switch(pol2){
									case 3: positionOnPol1 = 8; positionOnPol2 = 13; halbeKugelDrehenUm = 1; polDrehenRechts = 5; polDrehen = 2; break;
									case 4: positionOnPol1 = 8; positionOnPol2 = 18; halbeKugelDrehenUm = 1; polDrehenRechts = 3; polDrehen = 2; break;
									case 5: positionOnPol1 = 11; positionOnPol2 = 20; halbeKugelDrehenUm = 1; polDrehenRechts = 1; polDrehen = 2; break;
								}
							case 3:
								switch(pol2){
									case 4: positionOnPol1 = 15; positionOnPol2 = 19; halbeKugelDrehenUm = 1; polDrehenRechts = 0; polDrehen = 3; break;
									case 5: positionOnPol1 = 12; positionOnPol2 = 21; halbeKugelDrehenUm = 1; polDrehenRechts = 2; polDrehen = 3; break;
								}
							case 4:
								switch(pol2){
									case 5: positionOnPol1 = 18; positionOnPol2 = 21; halbeKugelDrehenUm = 2; polDrehenRechts = 1; polDrehen = 4; break;
								}
						}
						
						
						//Speichere die Farbe Position 1
						int color1 = k.tri[p1];
						int anzStep1 = 0;
						
						//Gehe maximal 4 mal durch
						for(int i = 0; i<4;i++){
							
							//Wenn Farbe noch nicht stimmt
							if(k.tri[positionOnPol1]!=color1){
								
								//Eins weiter drehen
								addSteps(pol1,1,1);
								anzStep1++;
							}
							
						}
						
						//Speichere die Farbe Position 2
						int color2 = k.tri[p1];
						int anzStep2 = 0;
						
						//Gehe maximal 4 mal durch
						for(int i = 0; i<4;i++){
							
							//Wenn Farbe noch nicht stimmt
							if(k.tri[positionOnPol2]!=color2){
								
								//Eins weiter drehen
								addSteps(pol2,1,1);
								anzStep2++;
							}
							
						}

						
						//Austauschen
						
						addSteps(polDrehenRechts, halbeKugelDrehenUm, 3);
						addSteps(polDrehen, 2, 1);
						addSteps(polDrehenRechts, 4-halbeKugelDrehenUm, 3);
						if(!overflow){
							addSteps(pol1, 4-anzStep1, 1);
							addSteps(pol2, 4-anzStep2, 1);
						}
						

						
						
					//Wenn keinen Pol gefunden	
					}else{
							
						//Wenn ja, dann entsprechende Funktion aufrufen
						change2PositionsOnOnePol(p1,p2);
							
					}
					
				}
				
	}
	
	
	/**
	 * Wechselt die beiden gegebenen Positionen
	 */
	private void change2Positions(int p1, int p2){
		
		//Überprüft die Positionen auf legale werte --> Min 0, Max 23	--> nicht den selben wert
		if(p1>=0&&p2>=0&&p1<=23&&p2<=23&&p1!=p2){
			
			//Wenn p1 grösser als p2
			if(p1>p2){
				
				//Variablen tauschen
				int temp = p1;
				p1 = p2;
				p2 = temp;
				
			}
			
			//Pol von p1 speichern
			int pol1 = p1/4;
			
			//Pol von p2 speichern
			int pol2 = p2/4;
		
			
			//Finde den Pol heraus um welchen gedreht wird
			int drehPol = SphereUtils.change2PolPositionPR(pol1,pol2);
			
			//Wenn gefunden
			if(drehPol >= 0){
				
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
				strategieOneTurnColorToPositionOnPol(p1,pos1);
				
				//Pol 2 zu korrekter Position drehen
				strategieOneTurnColorToPositionOnPol(p2,pos2);
				
				//Austauschen
				
				addSteps(drehPol, opt1, 3);
				addSteps(pol1, 1, 1);
				addSteps(drehPol, 4-opt1, 3);
				
			//Wenn keinen Pol gefunden	
			}else{
				
				//Prüfe ob Positionen auf dem Selben Pol sind
				if(pol1==pol2){
					
					//Wenn ja, dann entsprechende Funktion aufrufen
					change2PositionsOnOnePol(p1,p2);
					
				}
				
			}
			
		}
		
	}
		
	/**
	 * Tauscht 2 Positionen in der 2. Phase
	 */
	private void change2PositionsOnOnePol(int p1, int p2){
		
		//Wenn Position 1 grösser als Position 2
		if(p1>p2){
			
			//Speichere Position 1 als Temp
			int temp = p1;
			
			//Überschriebe Position 1 mit Position 2
			p1 = p2;
			
			//Überschreibe Position 2 mit Temp
			p2 = temp;
			
		}
		
		//Speichere den Pol auf dem sich beide befinden
		int pol = p1/4;
		
		//Prüfen, ob sich die beiden neben einander Befinden, oder nicht
		switch(p2-p1){
		// TODO --> Kommentierung
		case 1: case 3:
			int polOben = -1;
			int polRechts = -1;
			if((p1==12&&p2==13)||(p1==16&&p2==19)||(p1==4&&p2==5)||(p1==20&&p2==23)){
				polOben=0;
			}else
			if((p1==0&&p2==1)||(p1==8&&p2==9)||(p1==16&&p2==17)||(p1==20&&p2==21)){
				polOben=1;
			}else
			if((p1==6&&p2==7)||(p1==17&&p2==18)||(p1==14&&p2==15)||(p1==21&&p2==22)){
				polOben=2;
			}else
			if((p1==10&&p2==11)||(p1==18&&p2==19)||(p1==2&&p2==3)||(p1==22&&p2==23)){
				polOben=3;
			}else
			if((p1==0&&p2==3)||(p1==4&&p2==7)||(p1==8&&p2==11)||(p1==12&&p2==15)){
				polOben=4;
			}else
			if((p1==13&&p2==14)||(p1==1&&p2==2)||(p1==5&&p2==6)||(p1==9&&p2==10)){
				polOben=5;
			}
			
			polRechts = SphereUtils.change2PolPositionPR(pol,polOben);
			
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 1, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 1, 1);
			addSteps(polRechts, 1, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 1, 3);
			
			addSteps(pol, 1, 1);
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 1, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 1, 1);
			addSteps(polRechts, 1, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 3, 3);
			addSteps(polOben, 3, 1);
			addSteps(polRechts, 1, 3);
			addSteps(pol, 2, 1);
			break;
		case 2:
			int posOnPol2 = p2%4;
			int change2 = (posOnPol2+1)%4;
			int change = pol*4+change2;
			
			change2PositionsOnOnePol(p1,p1+1);
			change2PositionsOnOnePol(p2,change);
			for(int i = 0; i<4; i++){
				if(k.tri[p1]!=k.con[SphereUtils.findCorrectConIndexFromTri(p1)]){
					addSteps(pol, 1, 1);
				}
			}
			
			break;
		}

	}
	
	
	
	/**
	 * Ersetzt unnützliche mehrfach Drehungen durch eine einzige
	 */
	private ArrayList<String> RemoveUnusedSteps(ArrayList<String> arrayList){
		
		//Wenn ArrayList mehr als eine Kugel enthält
		if(arrayList.size()>1){
			
			//Gehe für jede Position durch
			for(int i = 0; i<arrayList.size()-2;i++){
				
				//Speichere den aktuellen Zustand der Kugel
				String sphere = SphereUtils.getPureSphereCode(arrayList.get(i));
				
				//Gehe jeden Status bis ans ende der Kugel durch
				for(int j = i+1; j<arrayList.size()-1;j++){
					
					//Wenn die Kugel nochmals genau gleich ist
					if(sphere==SphereUtils.getPureSphereCode(arrayList.get(j))){
						
						//Gehe für alle Schritte retour zurück
						for(int l = j; l>i; l--){
							
							//Entferne diese Schritte
							arrayList.remove(l);
							
						}
						
					}
					
				}
				
			}
			
			//Gehe für jede Position durch
			for(int i = 0; i<arrayList.size()-2;i++){
				
				//Nehme die Drehungen der nächsten beiden Stadien
				int[] dreh1 = SphereUtils.getDrehungFromStringAsIntArray(arrayList.get(i));
				int[] dreh2 = SphereUtils.getDrehungFromStringAsIntArray(arrayList.get(i+1));
				
				//Wenn auf gleichem Pol im Gleichen Modus gedreht wird
				if((dreh1[0]==dreh2[0])&&(dreh1[2]==dreh2[2])){
					
					//Beide drehungen Addieren und Modulo 4 nehmen
					int anz = dreh1[1]+dreh2[1];
					
					//Modulo 4 rechnen
					anz%=4;
					
					//Wenn Anzahl nicht gleich 0 dann ersetzte erstes Element mit neuer Drehung und lösche zweites
					if(anz!=0){
						
						//Setzte zweites durch den SphereCode + mit der neuen Drehung
						arrayList.set(i+1, SphereUtils.getPureSphereCode(arrayList.get(i+1))+"n"+i+"n"+dreh1[0]+""+(anz)+""+dreh1[2]);
						
						//Lösche zweites
						arrayList.remove(i);
						
					//Ansonsten
					}else{
						
						//Lösche beide
						//Zuerst i+1, damit dies durch entfernen von i zum neuen i wird...
						arrayList.remove(i+1);
						arrayList.remove(i);
						
					}
					
					//Gehe 2 Schritte zurück, um allfällige neue Überschneidungen zu entfernen
					i-=2;
					
				}
				
			}
			
		}
		
		//Gib arraylist zurück
		return arrayList;
		
	}
	
	/**
	 * Pol in beste Position drehen, da so viele viele Schritte vermieden werden können
	 */
	private void turnPolToOptinalPosition(){
		
		//Gehe alle Pole Durch
		for(int i = 0; i<6;i++){
			
			//Beste Position auf 0 setzen
			int best = 0;
			
			//Beste Anzahl auf 0 setzen
			int best_anz = 0;
			
			//jetztige Anzahl auf 0 setzen
			int jetzt_anz = 0;
			
			//Array mit den Werten für die Cons
			int[] cons = new int[4];		
			
			//Fülle Array mit Werten aus der Kugel
			for(int j = 0; j<4; j++){
				cons[j] = k.con[SphereUtils.findCorrectConIndexFromTri(i*4+j)];
			}
			
			//Gehe jede Möglichkeit
			for(int j = 0; j<4; j++){
				
				//Setzte jetztige Anzahl auf 0
				jetzt_anz = 0;
				
				//Gehe jede Position durch
				for(int l = 0; l<4;l++){
					
					//Wenn die Position stimmt
					if(k.tri[(i*4)+l]==k.con[SphereUtils.findCorrectConIndexFromTri((i*4+((j+l)%4)))]){
						
						//Jetztige Anzahl + 1
						jetzt_anz++;
						
					}
					
				}
				
				//Wenn jetztige Anzahl grösser als bisher beste Anzahl
				if(jetzt_anz>best_anz){
					
					//Setze Jetztige Anzahl als beste Anzahl
					best_anz = jetzt_anz;
					
					//Setze jetzt als best
					best = j;
					
				}
				
			}
			
			//Wenn best grösser als 0
			if(best>0){
				
				//Drehe den Pol um die gegebene Anzahl Schritten
				addSteps(i, best, 1);
				
			}
			
		}
		
	}
	
}
