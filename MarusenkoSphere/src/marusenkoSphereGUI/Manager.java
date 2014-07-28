package marusenkoSphereGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import marusenkoSphereKugel.Kugel;

public class Manager {

	protected Kugel k;
	private Rendern rendern;
	private ControlPanel cp;
	protected ArrayList<String> BlockedKey = new ArrayList<String>();
	protected int displayMode = 0; //0 = Kugel, 1 = Editor, 2 = dev
	private int selectedColor = 0;
	protected static double rotationSpeed = 3.0;
	protected static double fps = 30;
	protected Queue<String> toDoQueue = new LinkedList<String>();
	protected static boolean doQueue = false;
	protected static boolean doQuetoEnd = false;
	
	/**
	 * Manager zum Lösen Verwalten der GUI und lösen der Kugel
	 * @param k : Kugel zum lösen
	 * @param d : DebugLog
	 * @param l : ErrorLog
	 */
	public Manager(Kugel k){
		//übernehme Kugel von der Main-Datei
		this.k = k;
		//Berechne die Trigonimetrischen Funktionen bereits hier, damit diese später schon berechnet sind
		Trigonometrie.CalcTrigonometrie();
		//Fülle Kugel zufällig
		k.FillKugelRandom(); 
		//Initialisiere die Fenster (KugelRendern und ControlPanel)
		try{
			rendern = new Rendern(k,displayMode);
			cp = new ControlPanel(this,displayMode);
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		cp.updateKugelState(k.getStep(), k.SolvingList.size()-1);
		//In Endlosschleife auf Tastatureingabe warten
		while(true){
			KugelSteuern.Input(this);
		}
	}
	
	/**
	 * Wartet bis time Millisekunden verstrichen sind
	 * @param time
	 */
	protected void update(long time){
		//setze Startzeit
		long startTime = System.nanoTime();
		//Vergangene Zeit = jetzt - Startzeit
		long estimatedTime = System.nanoTime() - startTime;
		//Time * 1'000'000 da alles in Nanosekunden und nich in Millisekunden wie Input
		time*=1000000;
		//Gehe durch bis Zeit verstrichen sit
		while(estimatedTime<time){
			//Frage den Input ab --> Zeitvertreib
			//KugelSteuern.Input(this);
			//Vergangene Zeit nachführen
			estimatedTime = System.nanoTime() - startTime;
		}
	}
	/**
	 * Setzt die Kugel des Managers neu, und führt anschliessend alle Updatefunktionen aus
	 * @param k
	 */
	protected void updateKugel(Kugel k){
		this.k = k;
		updateList();
		renderKugel();
	}
	/**
	 * Startet das Rendern der Kugel
	 */
	protected void renderKugel(){
		rendern.updateKugel(k,displayMode);
	}
	/**
	 * Zeit die Gelöste Kugel an
	 */
	protected void startSolve(){
		doQuetoEnd = !doQuetoEnd;
	}
	/**
	 * Füllt die Kugel zufällig
	 */
	protected void fillSphere(){
		doQuetoEnd = false;
	    k.FillKugelRandom();
		updateList();   
	}
	/**
	 * Füllt die Kugel gemäss String s
	 * @param s : Kugel als String 
	 * @param solving : Soll Kugel gelöst werden
	 */
	protected void fillSphere(String s, boolean solving){
		k.FillKugelFromString(s, solving);
		updateList();
		doQuetoEnd = false;
	}
	protected void fillSphere(String s){
		fillSphere(s, true);
	}
	/**
	 * Updatet die Stats Liste im ControlPanel
	 */
	protected void updateList(){
		cp.updateKugelState(k.getStep(), k.SolvingList.size()-1);
	}
	/**
	 * Zu Position in Lösungsweg gehen
	 * @param i
	 */
	protected void goToStep(int i){
		k = SetToState.getKugelFromArrayList(k,i);
		updateList();
	}
	/**
	 * Geht im Lösungsprozess eine Position weiter
	 */
	protected void addOneStep(){
		 goToStep(k.getStep()+1);
	}
	/**
	 * Geht im Lösungsprozess eine Position zurück
	 */
	protected void subOneStep(){
		goToStep(k.getStep()-1);
	}
	protected void changeToMode(int i){
		//Wenn der Editor angezeigt wird, dann prüfen ob kugel richtig ist und neu gelöst werden soll
		if(displayMode==1){
			if(isSphereAllowed()){
				k.resetStep();
				fillSphere(k.getSphere());
			}
		}
		displayMode = i;
		cp.updateMode(displayMode);
		//System.out.println("Mode: "+displayMode);
	}
	protected int getSelectedColor(){
		return selectedColor;
	}
	protected void changeSelectedColor(int n){
		this.selectedColor = n;
	}
	protected void getRotationSpeedfromCp(){
		rotationSpeed = cp.getRotationSpeed()/10;
	}
	public static double getRotationSpeed(){
		return rotationSpeed;
	}
	public static double getFPS(){
		return fps;
	}
	/**
	 * Nimmt das ändern der Drehung der Kugel vor
	 * @param x
	 * @param y
	 * @param mode
	 */
	protected void rendernDrehen(float y, float x, float z, int mode){
		switch(mode){
		case 0: 
			if(x>=0.1f){
				rendern.cm.TurnUp(x*4);
			}
			if(x<=-0.1f){
				rendern.cm.TurnDown(x*4);
			}
			if(y>=-0.1f){
				rendern.cm.TurnLeft(y*4);
			}
			if(y<=0.1f){
				rendern.cm.TurnRight(y*4);
			}
			break;
		case 1:
			rendern.cm.SetToStartPosition();
			break;
		}
	}
	
	protected void editSphereTri(int n){
		k.tri[n] = selectedColor;
		updateSphere();
	}
	protected void editSphereCon(int n){
		k.con[n] = selectedColor;
		updateSphere();
	}
	private void updateSphere(){
		cp.updateAllowKugel(isSphereAllowed());
	}
	private boolean isSphereAllowed(){
		int[] checkTri = new int[24];
		int[] checkCon = new int[8];
		int[] referenceTri = new int [24];
		int[] referenceCon = new int[8];
		for(int i = 0; i<24;i++){
			checkTri[i] = k.tri[i];
			referenceTri[i] = i/3;
		}
		for(int i = 0; i<8;i++){
			checkCon[i] = k.con[i];
			referenceCon[i] = i;
		}
		Arrays.sort(checkTri);
		Arrays.sort(checkCon);
		if(Arrays.equals(checkTri,referenceTri)&&Arrays.equals(checkCon,referenceCon)){
			return true;
		}
		return false;
	}
	public void exitProgramm(){
		rendern.end();
	}
	
}
