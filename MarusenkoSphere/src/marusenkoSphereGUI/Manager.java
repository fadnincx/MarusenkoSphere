package marusenkoSphereGUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import marusenkoSphere.Settings;
import marusenkoSphereKugel.ImportExportSphere;
import marusenkoSphereKugel.Kugel;

/**
 * Manager-Klasse
 * 
 * Übernimmt die ganze Verwaltung und Koordination der Grafischen Oberfläche
 *
 */
public class Manager {
	
	//Hauptfenster
	protected JFrame mainFrame;
	
	//Kugel die dargestellt wird
	private Kugel k;
	
	//Das Render Fenster
	private Rendern rendern;
	
	//Die Controlpanels
	private ControlPanel cp;
	

	private JMenuBar menuBar;
	
	private JMenu file;
	private JMenuItem save;
	private JMenuItem open;
	private JMenuItem exit;
	
	private JMenu settings;
	private JMenuItem mouseSensity;
	
	private JMenu help;
	private JMenuItem helpcenter;
	private JMenuItem about;
	
	
	
	//Eine ArrayList mit Tasten die Blockiert sind
	//Verhindert, das durch normalen Tastenanschlag mehrere Aktionen ausgeführt werden
	protected ArrayList<Character> blockedKey = new ArrayList<Character>();
	
	//Gibt an, was dargestellt wird
	//0 = Kugel, 1 = Editor2D, 2 = Dev, 3 = Editor3D
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
	
	private int pfeilID = -1;
	

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
			initMainFrame();
			if(Settings.KIOSKMODE){
				new KioskBG();
			}
			rendern = new Rendern(k, this);
			cp = new ControlPanel(this);
			KeyboardMouseManager.init();
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
			rendern.updateKugel(k,displayMode,pfeilID);
			
			cp.updateSolvingState(k.getStep(), runAnimationToEnd);
			
			//Wenn aktuelle Animation fertig ist, dann QueueManager aufrufen
			if(animationFinished){
				
				QueueManager.Queue(this);
				
		    }

		}
		  
	}
	private void initMainFrame() throws IOException{
		mainFrame = new JFrame("MarusenkoSphere");
		
		//Lade das Icon
		Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
			
		//Setze das Icon
		mainFrame.setIconImage(icon);
				
		//Definiere die Grösse der Fenster
		mainFrame.setSize(1000, 538);
				
		//Setze sichtbar
		mainFrame.setVisible(true);
				
		//Definiere was beim Schliessen des Fenster passieren soll --> Programm beenden
		mainFrame.setDefaultCloseOperation(Settings.SETCLOSE);
				
		//Setzte die Position der Fenster
		mainFrame.setLocationRelativeTo(null);

		//Setzte, dass die Fenstergrösse durch den Benutzer nicht veränderbar ist
		mainFrame.setResizable(false);
				
		//Setzte Layout = null
		mainFrame.setLayout(null);
		
		
		//Erstelle MenuBar
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1000, 20);
		
		
		file = new JMenu("Datei");
		menuBar.add(file);
		
		save = new JMenuItem("Speichern");
		file.add(save);
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser;
			    String pfad = System.getProperty("user.home");
			    File file = new File(pfad.trim());

			    chooser = new JFileChooser(pfad){
					private static final long serialVersionUID = 7625169427404387627L;

					@Override
				    public void approveSelection(){
				        File f = getSelectedFile();
				        if(f.exists() && getDialogType() == SAVE_DIALOG){
				            int result = JOptionPane.showConfirmDialog(this,"Die Datei existiert bereits, überschreiben?","Datei existiert bereits",JOptionPane.YES_NO_CANCEL_OPTION);
				            switch(result){
				                case JOptionPane.YES_OPTION:
				                    super.approveSelection();
				                    return;
				                case JOptionPane.NO_OPTION:
				                    return;
				                case JOptionPane.CLOSED_OPTION:
				                    return;
				                case JOptionPane.CANCEL_OPTION:
				                    cancelSelection();
				                    return;
				            }
				        }
				        super.approveSelection();
				    }     
			    };
			    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			    FileNameExtensionFilter Filter = new FileNameExtensionFilter("MarusenkoSphere: mssf", "mssf");
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			    chooser.setFileFilter(Filter);
			    chooser.setDialogTitle("Speichern unter...");
			    chooser.setVisible(true);

			    int result = chooser.showSaveDialog(mainFrame);

			    if (result == JFileChooser.APPROVE_OPTION) {

			    	pfad = chooser.getSelectedFile().toString();
			        if (!pfad.endsWith(".mssf")){
			        	pfad += ".mssf";
			        }
			         	file = new File(pfad);
			            if (Filter.accept(file)){
			            	ImportExportSphere.save(file, k);
			            	System.out.println(pfad + " kann gespeichert werden.");
			            }else{
			                System.out.println(pfad + " ist der falsche Dateityp.");
			            }
			            chooser.setVisible(false);
			        
			        chooser.setVisible(false); 
				}
			}
		});
		
		
		open = new JMenuItem("Öffnen");
		file.add(open);
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser;
			    String pfad = System.getProperty("user.home");
			    File file = new File(pfad.trim());

			    chooser = new JFileChooser(pfad);
			    chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			    FileNameExtensionFilter Filter = new FileNameExtensionFilter("MarusenkoSphere: mssf", "mssf");
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			    chooser.setFileFilter(Filter);
			    chooser.setDialogTitle("Öffnen...");
			    chooser.setVisible(true);

			    int result = chooser.showOpenDialog(mainFrame);

			    if (result == JFileChooser.APPROVE_OPTION) {

			    	pfad = chooser.getSelectedFile().toString();
			        if (!pfad.endsWith(".mssf")){
			        	pfad += ".mssf";
			        }
			         	file = new File(pfad);
			            if (Filter.accept(file)){
			            	ImportExportSphere.open(file, k);
			            	System.out.println(pfad + " kann geöffnet werden.");
			            	updateControlpanelInformations();
			            }else{
			                System.out.println(pfad + " ist der falsche Dateityp.");
			            }
			            chooser.setVisible(false);
			        
			        chooser.setVisible(false); 
				}
			}
		});
		
		
		file.addSeparator();
		exit = new JMenuItem("Beenden");
		file.add(exit);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		settings = new JMenu("Einstellungen");
		menuBar.add(settings);
		
		mouseSensity = new JMenuItem("Mausempfindlichkeit");
		settings.add(mouseSensity);
		mouseSensity.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane optionPane = new JOptionPane();
			    JSlider slider = getSlider(optionPane);
			    optionPane.setMessage(new Object[] { "Wähle Mausempfindlichkeit ", slider });
			    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
			    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			    JDialog dialog = optionPane.createDialog(mainFrame, "Mausempfindlichkeit");
			    dialog.setVisible(true);
			    System.out.println("Input: " + optionPane.getInputValue());
			    Settings.MOUSESENSITIVE = (int) optionPane.getInputValue();
			}
			    JSlider getSlider(final JOptionPane optionPane) {
			        JSlider slider = new JSlider();
			        slider.setMajorTickSpacing(1);
			        slider.setMinimum(1);
			        slider.setMaximum(50);
			        slider.setValue((int) (Settings.MOUSESENSITIVE));
			        slider.setPaintTicks(false);
			        slider.setPaintLabels(false);
			        ChangeListener changeListener = new ChangeListener() {
			          public void stateChanged(ChangeEvent changeEvent) {
			            JSlider theSlider = (JSlider) changeEvent.getSource();
			            if (!theSlider.getValueIsAdjusting()) {
			              optionPane.setInputValue(new Integer(theSlider.getValue()));
			            }
			          }
			        };
			        slider.addChangeListener(changeListener);
			        return slider;
			      }
			
		});
		
		
		help = new JMenu("Hilfe");
		menuBar.add(help);
		
		helpcenter = new JMenuItem("Hilfe Center");
		help.add(helpcenter);
		helpcenter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Help(0);
			}
		});
		
		about = new JMenuItem("Über");
		help.add(about);
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainFrame,
					    "<html>Ein Programm zur Darstellung und Visualisierung der MarusenkoSphere<br><br>von Marcel Würsten</html>",
					    Settings.TITEL+" - Über",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});

		mainFrame.add(menuBar);
		
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
	 * Setzte die Variable, dass die Kugel bis zum Ende gelöst wird falsch
	 */
	protected void stopAnimationToEnd(){
		
		runAnimationToEnd = false;
		
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
		if((displayMode==1||displayMode==3)&&(i!=1||i!=3)){
			
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
		if((i==1||i==3)&&(displayMode!=1||displayMode!=3)){
			
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
		
		return solvingQueue.size()>0;
		
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
	
	protected void setNewPfeilID(int ID){
		pfeilID = ID;
	}
	protected int getPfeilID(){
		return pfeilID;
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
	protected void resetPosition(){
		mainFrame.setLocationRelativeTo(null);
	}
	
	/**
	 * Wenn das Programm beendet werden soll
	 */
	protected void exitProgramm(){	
		 System.exit(0);

	}
	
}
