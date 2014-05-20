package marusenkoSphereGUI;

import java.util.ArrayList;

import marusenkoSphereKugel.Kugel;

public class Manager {

	public Kugel k;
	private Rendern rendern;
	private ControlPanel cp;
	public ArrayList<String> BlockedKey = new ArrayList<String>();
	
	/**
	 * Manager zum L�sen Verwalten der GUI und l�sen der Kugel
	 * @param k : Kugel zum l�sen
	 * @param d : DebugLog
	 * @param l : ErrorLog
	 */
	public Manager(Kugel k){
		//�bernehme Kugel von der Main-Datei
		this.k = k;
		
		//Initialisiere die Fenster (KugelRendern und ControlPanel)
		rendern = new Rendern(k);
		cp = new ControlPanel(this);
		
		//F�lle Kugel zuf�llig
		fillSphere();
		
		//In Endlosschleife auf Tastatureingabe warten
		while(true){
			KugelSteuern.Input(this);
		}
	}
	
	/**
	 * Wartet bis time Millisekunden verstrichen sind
	 * @param time
	 */
	public void update(long time){
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
			//Vergangene Zeit nachf�hren
			estimatedTime = System.nanoTime() - startTime;
		}
	}
	/**
	 * Setzt die Kugel des Managers neu, und f�hrt anschliessend alle Updatefunktionen aus
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
		rendern.updateKugel(k);
	}
	/**
	 * Zeit die Gel�ste Kugel an
	 */
	public void startSolve(){
		k = SetToState.getKugelFromArrayList(k);
		updateList();
	}
	/**
	 * F�llt die Kugel zuf�llig
	 */
	public void fillSphere(){
		k.FillKugelRandom();
		updateList();
	}
	/**
	 * F�llt die Kugel gem�ss String s
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
		cp.updateKugelState(k.getStep(), SetToState.getSolvingLength(k.SolvingList)-1);
	}
	/**
	 * Geht im L�sungsprozess eine Position weiter
	 */
	public void oneStep(){
		k = SetToState.getKugelFromArrayList(k,k.getStep()+1);
		updateList();
	}
	/**
	 * Geht im L�sungsprozess eine Position zur�ck
	 */
	public void backStep(){
		k = SetToState.getKugelFromArrayList(k,k.getStep()-1);
		 updateList();
	}
	/**
	 * Geht im L�sungsprozess zur gegebenen Position
	 * @param s
	 */
	public void setPos(String s){
		int i = Integer.parseInt(s);
		k = SetToState.getKugelFromArrayList(k,i);
		updateList();
	}
	/**
	 * Nimmt das �ndern der Drehung der Kugel vor
	 * @param x
	 * @param y
	 * @param mode
	 */
	public void rendernDrehen(float y, float x, float z, int mode){
		switch(mode){
		case 0: 
			rendern.drehen(x,y,z);
			break;
		case 1:
			rendern.setDrehen(x,y,z);
			break;
		}
	}
	public void exitProgramm(){
		rendern.end();
	}
	
}
