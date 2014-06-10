package marusenkoSphereGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import marusenkoSphereKugel.Kugel;

public class Manager {

	public Kugel k;
	private Rendern rendern;
	private ControlPanel cp;
	protected ArrayList<String> BlockedKey = new ArrayList<String>();
	protected int displayMode = 0; //0 = Kugel, 1 = Editor, 2 = dev
	private int oldDisplayMode = 0; //0 = Kugel, 1 = Editor, 2 = dev
	private int selectedColor = 0;
	protected static double rotationSpeed = 3.0;
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
		
		//Initialisiere die Fenster (KugelRendern und ControlPanel)
		rendern = new Rendern(k,displayMode);
		cp = new ControlPanel(this,displayMode);
		
		//Fülle Kugel zufällig
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
			//KugelSteuern.Input(this);
			//Vergangene Zeit nachführen
			estimatedTime = System.nanoTime() - startTime;
		}
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
		rendern.updateKugel(k,displayMode);
	}
	/**
	 * Zeit die Gelöste Kugel an
	 */
	public void startSolve(){
		//k = SetToState.getKugelFromArrayList(k);
		//updateList();
		doQuetoEnd = true;
	}
	/**
	 * Füllt die Kugel zufällig
	 */
	public void fillSphere(){
	    k.FillKugelRandom();
		updateList();   
		doQuetoEnd = false;
	}
	/**
	 * Füllt die Kugel gemäss String s
	 * @param s : Kugel als String 
	 */
	public void fillSphere(String s){
		k.FillKugelFromString(s);
		updateList();
		doQuetoEnd = false;
	}
	/**
	 * Updatet die Stats Liste im ControlPanel
	 */
	public void updateList(){
		cp.updateKugelState(k.getStep(), SetToState.getSolvingLength(k.SolvingList)-1);
	}
	/**
	 * Geht im Lösungsprozess eine Position weiter
	 */
	public void oneStep(){
		k = SetToState.getKugelFromArrayList(k,k.getStep()+1);
		updateList();
	}
	/**
	 * Geht im Lösungsprozess eine Position zurück
	 */
	public void backStep(){
		k = SetToState.getKugelFromArrayList(k,k.getStep()-1);
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
	protected void changeToMode(int i){
		displayMode = i;
		cp.updateMode(displayMode);
		System.out.println("Mode: "+displayMode);
	}
	protected void changeModeEdior(){
		if(isSphereAllowed()){
			displayMode++;
			displayMode%=2;
			cp.updateMode(displayMode);
			System.out.println("Mode: "+displayMode);
		}
		if(displayMode==0){
			k.resetStep();
			fillSphere(k.getSphere());
		}
	}
	protected void enableDevPanel(){
		
		switch(displayMode){
		case 2:
			displayMode=oldDisplayMode;
			if(oldDisplayMode != displayMode){
				oldDisplayMode = displayMode;
			}
			cp.updateMode(displayMode);
			System.out.println("Mode: "+displayMode+" Old: "+oldDisplayMode);
			break;
		default:
			if(oldDisplayMode != displayMode){
				oldDisplayMode = displayMode;
			}
			displayMode=2;
			cp.updateMode(displayMode);
			System.out.println("Mode: "+displayMode+" Old: "+oldDisplayMode);
			break;
		}

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
	protected static double getRotationSpeed(){
		return rotationSpeed;
	}
	
	/**
	 * Nimmt das ändern der Drehung der Kugel vor
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
