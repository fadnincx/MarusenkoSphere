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
	protected ArrayList<String> solve2(Kugel kugel){
		k = kugel;
		solvingWay.add(k.getSphere("000"));
		
		return solvingWay;
	}
	protected ArrayList<String> solve3(Kugel kugel){
		k = kugel;
		solvingWay.add(k.getSphere("000"));
		return solvingWay;
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
		
		//Anzahl Strategien, die durch gegangen werden
		int anzahlStrategien = 2;
		
		//Gehe alle Strategien durch
		for(int i = 0; i<anzahlStrategien; i++){
			
			//Setzte die Kugel zurück in die Startposition
			k.fillKugelFromDebugString(sphereToSolve, false);
			
			//Setzte die StartKugel als erste Kugel
			solvingWay.add(k.getSphere("000"));
			
			//Erstelle Temporäre ArrayList mit der Lösung der Entsprechenden Strategie
			ArrayList<String> AL = strategieChooser(i);

			//Entferne unnötige Schritte
			RemoveUnusedSteps(AL);
			
			//Wenn Kugel nicht gelöst, dann ist es sicher nicht der schnellste Algorithmus
			if(!SolveCheck.isKugelSolved(k)){
				for(int j = 0; j<1000; j++){
					AL.add("notFinished");
				}
			}
			
			//Wenn die Lösung schneller ist, dann setze Strategie als neue bestSolvingWay
			if(AL.size()<bestSolvingWay.size()){
				bestSolvingWay = AL;
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
		int[] pol = {0,4,1,2,3,5};
		
		//Gehe alle Pole durch
		for(int i = 0; i<5; i++){
			
			//Wenn der aktuele Pol nicht gelöst ist, dann lösen
			if(!SolveCheck.isPolSolved(pol[i], k)){
				
				
				//Prüfe, auf welche Seite gelöst werden soll
				if(strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+1)&&strategieStandardAreThis2Possible(pol[i]*4+2,  pol[i]*4+3)){
					
					if(!SolveCheck.isPositionSolved(pol[i]*4+0, k)||!SolveCheck.isPositionSolved(pol[i]*4+1, k)){
						
						strategieStandardSolve2Positions(pol[i]*4+0, pol[i]*4+1);
						
					}
					
					if(!SolveCheck.isPositionSolved(pol[i]*4+2, k)||!SolveCheck.isPositionSolved(pol[i]*4+3, k)){
						
						strategieStandardSolve2Positions(pol[i]*4+2, pol[i]*4+3);
						
					}
					
				}else
				if(strategieStandardAreThis2Possible(pol[i]*4+1,  pol[i]*4+2)&&strategieStandardAreThis2Possible(pol[i]*4+0,  pol[i]*4+3)){

					if(!SolveCheck.isPositionSolved(pol[i]*4+1, k)||!SolveCheck.isPositionSolved(pol[i]*4+2, k)){
						
						strategieStandardSolve2Positions(pol[i]*4+1, pol[i]*4+2);
						
					}
					
					if(!SolveCheck.isPositionSolved(pol[i]*4+0, k)||!SolveCheck.isPositionSolved(pol[i]*4+3, k)){
						
						strategieStandardSolve2Positions(pol[i]*4+0, pol[i]*4+3);
						
					}
					
				}
				
			}
			
		}	
		
		//Löse den letzten Pol
		solveColorsOnPol();
		
		//Gib den Lösungsweg zurück
		return solvingWay;
		
	}
	
	
	/**
	 * Löst 2 Positionen gemäss der Standart-Taktik
	 */
	private void strategieStandardSolve2Positions(int pos1, int pos2){
	
		//Sofern dies überhaupt möglich ist...
		if(strategieStandardAreThis2Possible(pos1,pos2)){
			
			//UrsprungsPol
			int urPol = pos1/4;
			
			//Die benötigten Farben
			int colorForPosition1 = k.con[SphereUtils.findCorrectConIndexFromTri(pos1)];
			int colorForPosition2 = k.con[SphereUtils.findCorrectConIndexFromTri(pos2)];

			//Ist der andere Teil der Kugel bereits gelöst?
			boolean otherPartOfPolSolved = SolveCheck.otherPositionsOnPoleSolved(pos1, pos2, k);

			//Variablen ob Farben gefunden
			boolean color1OnUrPol = false;
			boolean color2OnUrPol = false;
			
			//Der Hilfspol = -1
			int hilfsPol = -1;
			
			//Sofern der andere Teil des Poles noch nicht gelöst ist
			if(!otherPartOfPolSolved){
				
				//Gehe alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Wenn Farbe 1 noch nicht gefunden und aktuelle Position die benötigte Farbe hat
					if(!color1OnUrPol&&k.tri[urPol*4+i]==colorForPosition1){
						
						//Farbe 1 auf UrsprungsPol vorhanden
						color1OnUrPol=true;

					}
					
					//Wenn Farbe 2 noch nicht gefunden und aktelle Position die benötigte Farbe hat
					if(!color2OnUrPol&&k.tri[urPol*4+i]==colorForPosition2){
						
						//Farbe 2 auf UrsprungsPol vorhanden
						color2OnUrPol=true;
						
					}					
					
				}
				
			}
			
			//Bsp für (0,1) [true][false][true][false][true][true]
			//Bsp für (0,3) [true][true][true][true][false][false]
			//Gibt Array mit 6 Indizen zurück, jeder sagt, ob der entsprechende Pol grundsätzlich geht
			boolean[] polRing = SphereUtils.getPolBand(pos1,pos2);
			
			//Array für den Vergleich der Pole
			int[] polRanking = new int[6];
			
			//Der Pol gegenüber
			int polGegenuber = SphereUtils.polGegenuber(urPol);
			
			//UrsprungsPol ist nicht als HilfsPol geeignet
			polRing[urPol]=false;
		
			//Pol gegenüber ist nicht als HilfsPol geeignet, weil es falsche drehungen gibt
			polRing[polGegenuber]=false;
			
			//Gehe alle Pole durch
			for(int i=0; i<6; i++){
				
				//Wenn Pol gegenüber, dann gibt es einen extra Punkt, dafür fehlen 4 beim PolRing
				if(i==polGegenuber){
					polRanking[i]++;
				}
				
				//Wenn nicht Urpol -> Weiter
				//Wenn nicht gelöst -> Weiter
				//Wenn auf Ring -> Weiter
				if(i!=urPol&&!SolveCheck.isPolSolved(i, k)&&polRing[i]){
					
					//Grund bedingung erfüllt --> +5 Punkte
					polRanking[i]+=5;
					
					//farben gefunden auf false
					boolean color1 = false;
					boolean color2 = false;
					
					//Evaluation für alle 4 Positionen eines Pols
					for(int j = 0; j<4; j++){
						
						if(!otherPartOfPolSolved&&color1OnUrPol){
							color1 = true;
							polRanking[i]+=10;
						}
						
						if(!color1&&k.tri[i*4+j]==colorForPosition1){
								
							color1=true;
							polRanking[i]+=10;
								
						}
						
						if(!color2&&k.tri[i*4+j]==colorForPosition2){
							
							color2=true;
							polRanking[i]+=10;
							
						}	
						
					}

				}
				
			}
			
			//Aktuelles Maximum
			int anzPunkteForPol = 0;
			
			//Gehe für alle 6 Pole durh
			for(int i=0; i<6; i++){
				
				//Wenn der Pol besser ist, als der bisherige
				if(polRanking[i]>anzPunkteForPol){
					
					//Dieser als aktuellen Hilfspol nehmen
					anzPunkteForPol = polRanking[i];
					hilfsPol = i;
					
				}
				
			}

			//Sofern ein Hilfspol gefunden wurde, kann nun der Pol gelöst werden
			if(hilfsPol != -1){
				
				//Bekomme die Option, ob das ganze umgekehrt sein muss
				int options = SphereUtils.strategieStandartGetTurnOpt(pos1, pos2, hilfsPol);
				
				//Wechsle Positionen falls nötig
				int[] positions = options==1?new int[]{pos1,pos2}:new int[]{pos2,pos1};
				
				//Wechsle Farben falls nötig
				int[] colors = options==1?new int[]{colorForPosition1,colorForPosition2}:new int[]{colorForPosition2,colorForPosition1};
				
				//Positionsnummern auf UrPol
				int[] positionNrOnUrPol = new int[]{-1,-1};
				
				//Positionsnummern auf Hilfspol
				int[] positionNrOnHilfsPol = new int[]{-1,-1};
				
				//Ist auf Urpol
				boolean[] positionsOnUrPol = new boolean[]{false,false};
				
				//Ist auf Hilfspol
				boolean[] positionsOnHilfsPol = new boolean[]{false,false};
				
				//Gehe alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Ist korrekte Farbe 2 auf Urpol?
					if(!positionsOnUrPol[1]&&k.tri[urPol*4+i]==colors[1]){
						
						//Setzte Ja
						positionsOnUrPol[1]=true;
						
						//Setzte Positionsnummer
						positionNrOnUrPol[1]=i;
						
					}
					
					//Ist korrekte Farbe 2 auf Hilfspol?
					if(!positionsOnHilfsPol[1]&&k.tri[hilfsPol*4+i]==colors[1]){
						
						//Setze Ja
						positionsOnHilfsPol[1]=true;
						
						//Setzte Positionsnummer
						positionNrOnHilfsPol[1]=i;
						
					}
					
				}
				
				//Wenn Farbe 2 auf Urpol, jedoch nicht auf Hilfspol
				if(positionsOnUrPol[1]&&!positionsOnHilfsPol[1]){
					
					//Position 2 auf Hilfspol verschieben
					
					//Bekomme die Drehinfos
					int posHilf = SphereUtils.verschiebeZuHilfPol(urPol*4+positionNrOnUrPol[1], hilfsPol);

					//Drehe die Beiden
					change2PosEqualDirect(posHilf,(urPol*4+positionNrOnUrPol[1]));

					//Setzet Farbe 2 auf Hilfspol = wahr
					positionsOnHilfsPol[1] = true;

				}
				
				//Gehe alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Wenn dies der erste Teil des Pols ist und Farbe 1 auf Urpol
					if(!otherPartOfPolSolved&&!positionsOnUrPol[0]&&k.tri[urPol*4+i]==colors[0]){
						
						//Setzte auf Ja
						positionsOnUrPol[0]=true;
						
						//Setzte Position
						positionNrOnUrPol[0]=i;
					
					}
					
					//Wenn Farbe 1 auf Hilfspol
					if(!positionsOnHilfsPol[0]&&k.tri[hilfsPol*4+i]==colors[0]){
						
						//Setzet auf Ja
						positionsOnHilfsPol[0]=true;
						
						//Setzte Position
						positionNrOnHilfsPol[0]=i;
					
					}
					
				}
				
				//Wenn Farbe 1 auf Urpol
				if(positionsOnUrPol[0]){
					
					//Setze auf false
					positionsOnUrPol[0]=false;
					
					//Gehe alle Positionen durch
					for(int i = 0; i<4;i++){
						
						//Wenn die Farbe 1 nicht an Position 2 ist
						if(k.tri[positions[1]]!=colors[0]){
							
							//Drehe den Pol
							addSteps(urPol, 1, 1);
						
						}
						
					}	
				
				//Wenn die Farbe 1 nicht auf dem Urpol liegt
				}else{
					
					//Wenn die Farbe 1 nicht auf dem Urpol liegt und erster Teil der Kugel
					if(!positionsOnHilfsPol[0]&&!otherPartOfPolSolved){
						
						//Setzet Variable auf -1
						int changePosition = -1;
						
						//Gehe alle 6 Pole durch
						for(int i = 0; i<6;i++){
						
							//Wenn ein Pol nicht gelöst ist und nicht der Urpol
							if(i!=urPol&&!SolveCheck.isPolSolved(i, k)){
								
								//Gehe alle 4 Positionen durch
								for(int j = 0; j<4; j++){
									
									//Wenn das erste mal, dass korrekte Farbe gefunden
									if(k.tri[i*4+j]==colors[0]&&changePosition==-1){
										
										//Setzet Position zum wechslen darauf
										
										changePosition=i*4+j;
										
									}
									
								}
								
							}
							
						}

						//Wechsle die Farbe auf Position 2
						change2PosEqualDirect(positions[1],changePosition);
						
					//Wenn die Farbe 1 nicht auf dem Hilfspol liegt	
					}else if(!positionsOnHilfsPol[0]){
						
						//Setzet Variable = -1
						int changePosition = -1;
						
						//Gehe alle 6 Pole durch
						for(int i = 0; i<6;i++){
						
							//Wenn Pol nicht gelöst
							if(!SolveCheck.isPolSolved(i, k)){
								
								//Gehe alle Positionen durch
								for(int j = 0; j<4; j++){
									
									//Wenn korrekte Farbe das erste mal gefunden
									if(k.tri[i*4+j]==colors[0]&&changePosition==-1){
										
										//setze Position zum wechslen
										changePosition=i*4+j;
										
									}
									
								}
								
							}
							
						}
						
						//Verschiebe die Farbe auf den Hilfspol
						change2PosEqualDirect(SphereUtils.verschiebeZuHilfPol(changePosition, hilfsPol),changePosition);
						
						//Setzte Position auf Hilfspol = false
						positionsOnHilfsPol[0]=false;
						
						//Gehe alle 4 Positionen durch
						for(int i = 0; i<4; i++){
							
							//Wenn noch nicht gefunden und korrekte Farbe
							if(!positionsOnHilfsPol[0]&&k.tri[hilfsPol*4+i]==colors[0]){
								
								//setzet ja
								positionsOnHilfsPol[0]=true;
								
								//Setze Positionsnummer
								positionNrOnHilfsPol[0]=i;
							
							}
							
						}
						
						//Verschiebe die Farbe vom Hilfspol auf die korrekte Position
						change2PosEqualDirect(positions[1],(hilfsPol*4+positionNrOnHilfsPol[0]));
						
					//Wenn Farbe 1 auf Hilfpol	
					}else{
						
						//Setzet Position auf Hilfspol = false
						positionsOnHilfsPol[0]=false;
						
						//Gehe alle 4 Positionen durch
						for(int i = 0; i<4; i++){
							
							//Wenn korrekte Farbe gefunden
							if(!positionsOnHilfsPol[0]&&k.tri[hilfsPol*4+i]==colors[0]){
								
								//Setzet auf ja
								positionsOnHilfsPol[0]=true;
								
								//Setzte Positionsnummer
								positionNrOnHilfsPol[0]=i;
							
							}
							
						}
					
						//Verschiebe Farbe vom Hilfpol auf die korrekte Position
						change2PosEqualDirect(positions[1],(hilfsPol*4+positionNrOnHilfsPol[0]));

					}
					
				}
				
				//Farbe 2 auf HilfsPol = false
				positionsOnHilfsPol[1]=false;
				
				//Gehe alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Wenn Farbe auf Hilfspol gefunden
					if(!positionsOnHilfsPol[1]&&k.tri[hilfsPol*4+i]==colors[1]){
						
						//Setzte auf ja
						positionsOnHilfsPol[1]=true;
						
						//Setze Positionsnummer
						positionNrOnHilfsPol[1]=i;
					
					}
					
				}
				
				//Wenn Farbe nicht auf Hilfspol gefunden
				if(!positionsOnHilfsPol[1]){
					
					//Setzet Position zum wechseln = -1
					int changePosition = -1;
					
					//Gehe alle 6 Pole durch
					for(int i = 0; i<6;i++){
					
						//Wenn Pol nicht gelöst und nicht Urpol
						if(i!=urPol&&!SolveCheck.isPolSolved(i, k)){
							
							//Gehe alle Positionen durch
							for(int j = 0; j<4; j++){
								
								//Wenn korrekte Farbe
								if(k.tri[i*4+j]==colors[1]&&changePosition==-1){
									
									//Speichere die Position zum wechslen
									changePosition=i*4+j;
									
								}
								
							}
							
						}
						
					}
					
					//Wenn Position zum wechslen gefunden wurde
					if(changePosition!=-1){
						
						//Setzte Position auf Hilfspol
						positionNrOnHilfsPol[0]=hilfsPol*4;

						//Wechsle die Beiden Farben
						change2PosEqualDirect(hilfsPol*4,changePosition);
						
						//Setze Psition auf Hilfspol = 0
						positionNrOnHilfsPol[1]=0;
						
					}
					
				}
				
				//Position auf Hilfpol = false
				positionsOnHilfsPol[1]=false;
				
				//Setzet Positionsnummer = -1
				positionNrOnHilfsPol[1] = -1;
				
				//Gehe alle 4 Positionen durch
				for(int i = 0; i<4; i++){
					
					//Wenn korrekte Position gefunden
					if(!positionsOnHilfsPol[1]&&k.tri[hilfsPol*4+i]==colors[1]){
						
						//Setzet auf ja
						positionsOnHilfsPol[1]=true;
						
						//Setez die Positionsnummer
						positionNrOnHilfsPol[1]=i;
					
					}
					
				}
				
				//Wenn Farbe gefunden
				if(positionNrOnHilfsPol[1]!=-1){
					
					//Veschiebe die Farbe auf den Urpol
					change2PosEqualDirect(positions[1],(hilfsPol*4+positionNrOnHilfsPol[1]));//overflow
					
				}
				
			}
		
		//Wenn nocht lösbar
		}else{
			
			//Gib Fehlermeldung aus
			System.err.println("Lösen unmöglich von "+pos1+" und "+pos2);
			
		}

		
	}
		
	/**
	 * Gibt zurück, ob das wechseln der beiden Positionen möglich ist
	 */
	private boolean strategieStandardAreThis2Possible(int pos1, int pos2){
		
		//Bekomme den Ring aus Polen
		boolean[] polRing = SphereUtils.getPolBand(pos1, pos2);
		
		//Definiere den Urpol
		int urPol = pos1/4;
		
		//Gehe alle 6 Positionen durch
		for(int i = 0; i<6; i++){
			
			//wenn Pol nicht gelöst, im PolRing und nicht der Urpol
			if(!SolveCheck.isPolSolved(i, k)&&polRing[i]&&i!=urPol){
				
				//Gibt true zurück
				return true;
				
			}
			
		}
		
		//Wenn kein Pol gefunden, dann gib false zurück
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
		while(!SolveCheck.arrayIsFullyOk(ok)){
			
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
			
			//Setzet PolI 
			int polI = i/4;
			
			//Bekomme die Infos zu CrossOver
			int[] pos = SphereUtils.crossOverPos(i);
			
			//Setzet die Position j
			int j = polI*4+(((i%4)+1)%4);
			
			//Setzet den Pol2
			int pol2 = pos[0]/4;
			
			//Wenn bereit zum drehen
			if(k.tri[i]==k.tri[pos[0]]&&k.tri[j]==k.tri[pos[1]]&&k.tri[i]==k.con[SphereUtils.findCorrectConIndexFromTri(j)]&&k.tri[j]==k.con[SphereUtils.findCorrectConIndexFromTri(i)]){
				
				//bekomme den Pol zum drehen
				int polDrehen = SphereUtils.getCrossOverPol(i);
				
				//Führe die Drehkombination aus
				
				//Drehen von Pol 1
				addSteps(polI, 1, 1);
				
				//Drehen von Pol 2
				addSteps(pol2, 1, 1);
				
				//Gegenverschieben
				addSteps(polDrehen, 1, 3);
				
				//Drehen auf Pol 1
				addSteps(polI, 2, 1);
				
				//Zurück verschieben
				addSteps(polDrehen, 3, 3);
				
				//Korrekt drehen Pol 1
				addSteps(polI, 3, 1);
				
				//Korrekt drehen Pol 2
				addSteps(pol2, 3, 1);
				
			}
			
		}
		
		//Sortiere die Farben auf den Polen
		solveColorsOnPol();
		
		//Kugel zurückgeben
		return solvingWay;
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
			if(!SolveCheck.areAllColorsOnPol(k,polNr)&&urPol!=polNr){
				
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
				for(int l = 0; l<4; l++){
					
					//Wenn Position noch nicht gesetzt wurde
					//Wenn die Farbe noch erlaubt ist
					//Wenn die beiden Farben übereinstimmen
					if(!set&&allowCons[l]&&tris[j]==cons[l]){
						
						//Setzte die Position im Array ok auf wahr
						ok[i*4+j] = true;
						
						//Streiche die Farbe von der Liste der erlaubten
						allowCons[l] = false;
						
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
	
	private void solveColorsOnPol(){
		strategieOnecheckIfPositionOnKorrektPole();
		
		//Wenn keinen Fehler gefunden
		if(SolveCheck.arrayIsFullyOk(ok)){
					
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
			
		}else{
			//Log.ErrorLog("Nicht alle Farben auf korrektem Pol");
		}
	}
	
	
	/**
	 * Dreht eine Farbe an eine bestimmte Position auf dem Pol
	 */
	private void turnColorToPositionOnPol(int p, int zielPos){

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
	/**
	 * 
	 * @param p1 : TO
	 * @param p2 : From
	 */
	private void change2PosEqualDirect(int p1, int p2){		
		int pol1 = p1/4;
		int pol2 = p2/4;
		if(pol1!=pol2){
			int[] values = SphereUtils.optionsChange2PosEqualDirect(p1, p2);
			
			//Drehung auf Pol2
			addSteps(pol2, values[0],1);
			
			//Drehung zu Pol
			addSteps(values[1], values[2],3);
			
			//Drehung auf Pol
			addSteps(pol1, values[3], 1);
			
			//Drehung zurück
			addSteps(values[1], 4-values[2],3);
			
		}else{
			//println("On Same Pol");
			change2PositionsOnOnePol(p1, p2);
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
				turnColorToPositionOnPol(p1,pos1);
				
				//Pol 2 zu korrekter Position drehen
				turnColorToPositionOnPol(p2,pos2);
				
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
		
		//Wenn sie nebeneinander sind
		case 1: case 3:
			
			//Setzet Pol Oben auf -1
			int polOben = -1;
			
			//Setze Pol Rechts auf -1
			int polRechts = -1;
			
			//Bekomme den Pol Oben aus der Tabelle
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
			
			//Bekomme den Pol Rechts
			polRechts = SphereUtils.change2PolPositionPR(pol,polOben);
			
			
			//Drehe gemäss folgenden Schema
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
			
		//Wenn sie sich übers kreuz befinden	
		case 2:
			
			//Bekomme die 4. Position
			int change = pol*4+(((p2%4)+1)%4);
			
			//Speichere die Farbe der 2. Position
			int color1 = k.tri[p1+1];
			
			//Speichere die Farbe der 4. Position
			int color2 = k.tri[change];
			
			//Rekursiver Aufruf zum Wechseln von Position 1 und 2
			change2PositionsOnOnePol(p1,p1+1);
			
			//Rekursiver Aufruf zum Wechseln von Position 3 und 4
			change2PositionsOnOnePol(p2,change);
			
			//Mache das ganze 4 mal
			for(int i = 0; i<4; i++){
				
				//Wenn die Farben noch nicht korrekt sind
				if(!(color1==k.tri[p1+1]&&color2==k.tri[change])){
					
					//Drehe den Pol einen Schritt weiter
					addSteps(pol, 1, 1);
					
				}
				
			}
			
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
			//Gehe für jede Position durch
			for(int i = 0; i<arrayList.size()-2;i++){
				
				//Speichere den aktuellen Zustand der Kugel
				int[] drehung = SphereUtils.getDrehungFromStringAsIntArray(arrayList.get(i));
				
				
				if(drehung[1]==0){
					arrayList.remove(i);
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
