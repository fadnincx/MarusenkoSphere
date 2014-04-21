package marusenkoSphereGUI;

import java.util.ArrayList;

import marusenkoSphereKugel.Kugel;

public class Manager {

	public Kugel k;
	public KugelRendern kr;
	public ControlPanel cp;
	public ArrayList<String> BlockedKey = new ArrayList<String>();
	
	/**
	 * Manager zum Lösen Verwalten der GUI und lösen der Kugel
	 * @param k : Kugel zum lösen
	 * @param d : DebugLog
	 * @param l : ErrorLog
	 */
	public Manager(Kugel k){
		//Übernehme Kugel und Logger von Main
		this.k = k;
		/*this.d = d;
		this.l = l;*/
		
		//Initialisiere die Fenster (KugelRendern und ControlPanel)
		kr = new KugelRendern(k);
		cp = new ControlPanel(this);
		
		//Fülle Kugel zufällig
		fillSphere();
		
		//In Endlosschleife auf Keyboard Input warten
		while(true){
			KugelSteuern.Input(this);
		}
	}
	
	/**
	 * Wartet bis time Millisekunden verstrichen sind
	 * @param time
	 */
	public void update(long time){
		//kr.updateKugel(k);
		
		//setze Startzeit
		long startTime = System.nanoTime();
		//Vergangene Zeit = jetzt - Startzeit
		long estimatedTime = System.nanoTime() - startTime;
		//Time * 1'000'000 da alles in Nanosekunden und nich in Millisekunden wie Input
		time*=1000000;
		//Gehe durch bis Zeit verstrichen sit
		while(estimatedTime<time){
			//Frage den Input ab --> Zeitvertreib
			KugelSteuern.Input(this);
			//Vergangene Zeit nachführen
			estimatedTime = System.nanoTime() - startTime;
		}
	}
	
	/**
	 * führt update(100) aus
	 */
	public void update(){
		update(100);
	}
	/**
	 * führt update(200) aus
	 */
	public void update200(){
		update(200);
	}
	/**
	 * Setzt die Kugel des Managers neu, und führt anschliessend alle Updatefunktionen aus
	 * @param k
	 */
	public void updateKugel(Kugel k){
		this.k = k;
		updateList();
		renderKugel();
	}
	/**
	 * Startet das Rendern der Kugel
	 */
	public void renderKugel(){
		kr.updateKugel(k);
	}
	/**
	 * Zeit die Gelöste Kugel an
	 */
	public void startSolve(){
		k = SetToState.getKugelFromArrayList(k);
		updateList();
	}
	/**
	 * Füllt die Kugel zufällig
	 */
	public void fillSphere(){
		k.FillKugelRandom();
		updateList();
	}
	/**
	 * Füllt die Kugel gemäss String s
	 * @param s : Kugel als String 
	 */
	public void fillSphere(String s){
		k.FillKugelFromString(s);
		updateList();
	}
	/**
	 * Updatet die Stats Liste im ControlPanel
	 */
	public void updateList(){
		cp.updateKugelState(k.steps, SetToState.getSolvingLength(k.SolvingList)-1);
	}
	/**
	 * Geht im Lösungsprozess eine Position weiter
	 */
	public void oneStep(){
		k = SetToState.getKugelFromArrayList(k,k.steps+1);
		updateList();
	}
	/**
	 * Geht im Lösungsprozess eine Position zurück
	 */
	public void backStep(){
		k = SetToState.getKugelFromArrayList(k,k.steps-1);
		 updateList();
	}
	/**
	 * Geht im Lösungsprozess zur gegebenen Position
	 * @param s
	 */
	public void setPos(String s){
		int i = Integer.parseInt(s);
		k = SetToState.getKugelFromArrayList(k,i);
		updateList();
	}
	
}
