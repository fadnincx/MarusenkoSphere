package marusenkoSphereGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import marusenkoSphereKugel.Kugel;

/**
 * Manager-Klasse
 * 
 * Übernimmt die ganze Verwaltung und Koordination der Grafischen Oberfläche
 *
 */
public class Manager {

	//Kugel die dargestellt wird
	private Kugel k;
	
	// Das Render Fenster
	private Rendern rendern;
	
	//Das Controlpanel
	private ControlPanel cp;
	
	//Eine ArrayList mit Tasten die Blockiert sind
	//Verhindert, das durch normalen Tastenanschlag mehrere Aktionen ausgeführt werden
	protected ArrayList<Character> blockedKey = new ArrayList<Character>();
	
	//Gibt an, was dargestellt wird
	//0 = Kugel, 1 = Editor, 2 = Dev
	private int displayMode = 0; 
	
	//Gibt an, welche Farbe im Editor ausgewählt ist
	private int selectedColor = 0;
	
	//Gibt den die Animationsgeschwindigkeit an
	private static double animationSpeed = 1.0;
	
	//Gibt  die Framerate an
	private static double fps = 60;
	
	//Das Queue mit was gemacht werden soll
	// X = Schritt weiter, Y = Schritt zurück
	private Queue<Character> solvingQueue = new LinkedList<Character>();
	
	//Wenn eine Animation fertig ist, dann ist Variabel true
	private static boolean animationFinished = false;
	
	//Wenn lösen aktiv, dann nicht nach Queue, sondern bis zum ende lösen
	private static boolean runAnimationToEnd = false;
	
	private int stepWhenGoesToEditor = 0;

	/**
	 * Manager zum Lösen Verwalten der GUI und lösen der Kugel
	 * @param k : Kugel zum lösen
	 */
	public Manager(Kugel k){
		//Übernehme Kugel von der Main-Datei
		this.k = k;
		
		//Fülle Kugel zufällig
		k.fillRandom(); 	

		//Versuche die Fenster zu initialisieren (KugelRendern und ControlPanel)
		//Sonst wirf eine Exception
		try{
			rendern = new Rendern(k);
			cp = new ControlPanel(this);
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		//Update die Anzeigt bezüglich der Anzahl Schritten 
		updateControlpanelInformations();
		
		//Methode loop starten
		loop();
	}
	
	/**
	 * Funktion, welche endlos wiederholt wird
	 */
	private void loop(){
		
		//Endlos wiederholen
		while(true){
			
			//Frage die Tastatur und Mauseingaben ab
			KeyboardMouseManager.Input(this);
			
			//Rendere die Aktuelle Kugel
			rendern.updateKugel(k,displayMode);
			
			//Wenn aktuelle Animation fertig ist, dann QueueManager aufrufen
			if(animationFinished){
				QueueManager.Queue(this);
		    }
			
		}
		  
	}
	
	/**
	 * Wartet bis die gegebene Anzahl Millisekunden verstrichen sind
	 */
	protected void sleep(long time){
		
		//Damit bei einem Fehler dieser ausgegeben werden kann
		try{
			//Versuche den Thread zu pausieren
			Thread.sleep(time);
			
		//Wenn ein Fehler auftritt	
		}catch(InterruptedException e){
			
			//Gib den Fehler aus
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Ändert die Variable, dass die Kugel bis zum Ende gelöst wird
	 */
	protected void changeRunAnimationToEnd(){
		
		runAnimationToEnd = !runAnimationToEnd;
		
	}
	
	/**
	 * Füllt die Kugel zufällig
	 */
	protected void fillSphere(){
		
		//Stop, falls die Kugel zum Ende gelöst wird
		runAnimationToEnd = false;
		
		//Füllt die Kugel zufällig
	    k.fillRandom();

	    //Updated das Controlpanel
	    updateControlpanelInformations();   
	    
	}
	
	/**
	 * Füllt die Kugel vom DevPanel gemäss String
	 * @param s : Kugel als String 
	 * @param solving : Soll Kugel gelöst werden
	 */
	protected void fillSphereFromDevString(String s, boolean solving){
		
		//Beendet, dass Kugel zu ende gelöst wird
		runAnimationToEnd = false;
		
		//Fülle die Kugel gemäss String
		k.fillKugelFromDebugString(s, solving);
		
		//Update das Controlpanel
		updateControlpanelInformations();	
		
	}
	
	/**
	 * Updatet die Werte über die Kugel im Controlpanel
	 */
	protected void updateControlpanelInformations(){
		
		cp.updateSphereInfos(k.getStep(), k.getSolvingListSize()-1);
		
	}
	
	/**
	 * Zu Position X in Lösungsweg gehen
	 */
	protected void goToStep(int x){
		
		//Versuche zur gewünschten Positon zu gehen
		setKugelToPositionFromArrayList(x);
		
		//Aktuellisiere die Informationen auf dem Controlpanel
		updateControlpanelInformations();
		
	}
	
	/**
	 * Wird aufgerufen, wenn der Slider auf dem Controlpanel verändert wird
	 * (durch Benutzer und Programm)
	 * @param x
	 */
	protected void cpSliderChangeState(int x){
		
		//Bekomme bei welcher Position die Kugel aktuell ist
		int stepFromSphere = k.getStep();
		
		//Wenn es nicht die aktuelle Position ist
		if(x!=stepFromSphere){
			
			//Gehe zu der gewünschten Position
			goToStep(x);
			
		}
		
	}
	
	/**
	 * Gibt die Kugel zur Gegebenen Step zurück
	 */
	private void setKugelToPositionFromArrayList(int step){
		
		//Wenn die gewünschte Position kleiner als 0 ist
		if(step<0){
			
			//Setzet die gewünschte Position auf 0
			step = 0;
			
		//Wenn die gewünschte Position grösser als das Maximum ist	
		}else if(step>=k.getSolvingListSize()){
			
			//Dann setze die gewünschte Position auf das Maximum
			step = k.getSolvingListSize()-1;
			
		}
		
		//Wenn die Kugel zu ende gelöst wird und
		//Wenn die Kugel mit dieser Änderung am Ende angekommen ist
		if(runAnimationToEnd&&step==k.getSolvingListSize()-1){
			
			//Setzte lösen bis ans Ende auf falsch
			runAnimationToEnd = false;
			
		}
		
		//Verändere die Kugel nach der Liste
		k.setKugelToStateFromList(step);
		
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
	
	/**
	 * Wechselt den Modus, was angezeigt wird
	 *  0 = Kugel, 1 = Editor, 2 = Dev
	 */
	protected void changeToMode(int i){
		//Wenn der Editor angezeigt wurde, dann prüfen ob kugel korrekt und ob übernommen werden kann
		if(displayMode==1){
			
			//Wenn Kugel korrekt, dann übernehmen
			if(isSphereAllowed()){
				k.sphereFromEditor();
				updateControlpanelInformations();
			}else{
				
				//Wenn nicht, dann zu ursprünglicher Kugel zurück kehren
				goToStep(stepWhenGoesToEditor);
			}
		}
		
		//Wenn neu der Editor
		if(i==1){
			
			//Setzte Step, damit im nachhinein wieder zu diesem zurück gekehrt werden kann
			stepWhenGoesToEditor = k.getStep();
		}
		
		//Ändere den Modus
		displayMode = i;
		
		//Teile dem Controlpanel mit, dass der Modus gewechselt wurde
		cp.updateMode(displayMode);
	}
	
	/**
	 * Gibt die im Editor ausgewählte Farbe zurück
	 */
	protected int getSelectedColor(){
		return selectedColor;
	}
	
	/**
	 * Wechselt die ausgewählte Farbe des Editors zu Nummer n
	 */
	protected void changeSelectedColor(int n){
		selectedColor = n;
	}
	
	/**
	 *Gibt die aktuelle Animationsgeschwindigkeit zurück, welche im Controlpanel eingestellt ist
	 */
	public static double getAnimationsSpeed(){
		return animationSpeed;
	}
	
	/**
	 * Setzt die aktuelle Animationsgeschwindigkeit neu
	 */
	protected static void setAnimationSpeed(double value){
		animationSpeed = value/30;
	}
	
	/**
	 * Gibt die aktuelle Bildfrequenz zurück
	 */
	public static double getFPS(){
		return fps;
	}
	
	/**
	 * Gibt den aktuellen DisplayModus zurück
	 */
	protected int getDisplayMode(){
		return displayMode;
	}

	/**
	 * Gibt zurück, ob die Kugel bis zum Ende gelöst werden soll
	 */
	protected static boolean getRunAnimationToEnd(){
		return runAnimationToEnd;
	}
	
	/**
	 * Gibt zurück, ob die aktuelle Animation beendet ist
	 */
	protected static boolean getAnimationFinished(){
		return animationFinished;
	}
	
	/**
	 * Ändert den Status zur aktuellen Animation
	 */
	public  static void setAnimationFinished(boolean set){
		animationFinished = set;
	}
	
	/**
	 * Setzt die aktuelle Bildfrequenz
	 */
	protected static void setFPS(int newFPS){
		fps = newFPS;
	}
	
	/**
	 * Fügt ein char zum solvingQueue hinzu
	 * x = Schritt weiter, y = Schritt zurück
	 */
	protected void addToSolvingQueue(char s){
		solvingQueue.offer(s);
	}
	
	/**
	 * Gibt zurück, ob sich ein Element im SolvingQueue befindet
	 */
	protected boolean isElementInSolvingQueue(){
		return solvingQueue.size()>0 ? true:false;
	}
	
	/**
	 * Fragt das erste Element im SolvingQueue ab und löscht es
	 */
	protected char pollElementFromSolvingQueue(){
		return solvingQueue.poll();
	}
	
	/**
	 * Ändert den Rotationswinkel
	 * Veränderung in x und y Richtung angeben
	 */
	protected void changeRotationAngle(double x, double y){
		rendern.cm.turnRotationAngle(x, y);
	}
	
	/**
	 * Setzt die Rotationswinkel auf die Startposition zurück
	 */
	protected void resetRotationAngle(){
		rendern.cm.setToStartPosition();
	}
	
	/**
	 * Methode für Editor zum Verändern der Dreiecke der Kugel
	 */
	protected void editSphereTri(int n){
		//Ändern des Dreieckes
		k.tri[n] = selectedColor;
		
		//Aktuallisiere den Status im Controlpanel bezüglich legalität der Kugel
		updateInformationToLegalityOfSphere();
	}
	
	/**
	 * Methode für Editor zum Verändern der Verbindungsstücke der Kugel
	 */
	protected void editSphereCon(int n){
		//Ändern des Verbindungsstück
		k.con[n] = selectedColor;
		
		//Aktuallisiere den Status im Controlpanel bezüglich legalität der Kugel
		updateInformationToLegalityOfSphere();
	}
	
	/**
	 * Aktuallisiere den Status im Controlpanel bezüglich legalität der Kugel
	 */
	private void updateInformationToLegalityOfSphere(){
		cp.updateInfoIsSphereAllowed(isSphereAllowed());
	}
	
	/**
	 * Prüfe, ob Kugel legal ist
	 */
	private boolean isSphereAllowed(){
		
		//Erstelle Array für alle aktuellen Tris
		int[] checkTri = new int[24];
		
		//Erstelle Array für alle aktuellen Cons
		int[] checkCon = new int[8];
		
		//Erstelle Referenz Array für Tri
		int[] referenceTri = new int[] {0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7};
		
		//Erstelle Referenz Array für Con
		int[] referenceCon = new int[] {0,1,2,3,4,5,6,7};
		
		//Fülle das TriArray mit aktuellen Werten
		for(int i = 0; i<24;i++){
			checkTri[i] = k.tri[i];
		}
		
		//Fülle das ConArray mit aktuellen Werten
		for(int i = 0; i<8;i++){
			checkCon[i] = k.con[i];
		}
		
		//Sortiere die aktuellen Arrays, damit sie einfach verglichen werden können
		Arrays.sort(checkTri);
		Arrays.sort(checkCon);
		
		return Arrays.equals(checkTri,referenceTri)&&Arrays.equals(checkCon,referenceCon);
		
	}
	
	/**
	 * Wenn das Programm beendet werden soll
	 */
	protected void exitProgramm(){
		 System.exit(0);
	}
	
}
