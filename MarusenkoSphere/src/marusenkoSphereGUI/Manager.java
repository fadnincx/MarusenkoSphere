package marusenkoSphereGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

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
	
	protected KeyboardMouseManager kmm;
	
	//Menubar
	private JMenuBar menuBar;
	
	//Menu Datei
	private JMenu file;
	
	//Menupunkt Speichern
	private JMenuItem save;
	
	//Menupunkt öffnen
	private JMenuItem open;
	
	//Menupunkt Schliessen
	private JMenuItem exit;
	
	//Menu Hilfe
	private JMenu settings;
	
	//Menupunkt öffnen
	private JCheckBoxMenuItem touchscreen;

	//Menu Hilfe
	private JMenu help;
	
	//Menupunkt Hilfecenter
	private JMenuItem helpcenter;
	
	//Menupunkt Über
	private JMenuItem about;
	
	//Eine ArrayList mit Tasten die Blockiert sind
	//Verhindert, das durch normalen Tastenanschlag mehrere Aktionen ausgeführt werden
	protected ArrayList<Character> blockedKey = new ArrayList<Character>();
	
	//Gibt an, was dargestellt wird
	//0 = Kugel, 1 = Editor2D, 2 = Dev, 3 = Editor3D
	private int displayMode = 0; 
	
	//Gibt an, welche Farbe im Editor ausgewählt ist
	private volatile int selectedColor = 0;
	
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
	
	//Schritt, wenn Editor geöffnet wurde
	private int stepWhenGoesToEditor = 0;
	
	//Pfeil ID
	private int pfeilID = -1;
	
	public static volatile boolean mouseDown = false;
	public static volatile boolean requestMousePosition = false;
	
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
			
			//Starte den Splashscreen
			new Splash().run();

			//Initialisiere das Hauptfenster
			initMainFrame();
			
			//Wenn gewollt öffne den Kioskmode
			if(Settings.KIOSKMODE){
				new KioskBG();
				
			}
			
			//Öffne das Controlpanel
			cp = new ControlPanel(this);
			
			//Öffne das Rendernpanel
			rendern = new Rendern(k, this);
			
			kmm = new KeyboardMouseManager(this, cp);

		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		//Beende das Initialisieren des Hauptfensterns
		initMainFrameEnd();
	
		//Display.getInstange().isTouchscreenDevice();
		
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
			if(requestMousePosition){
				KeyboardMouseManager.mousePosition(this);
				requestMousePosition = false;
			}

		}
		  
	}
	
	private void initMainFrame() throws IOException{
		mainFrame = new JFrame(Settings.TITEL);
		//mainFrame = new JDialog();
		mainFrame.setTitle(Settings.TITEL);
		//mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
		//Lade das Icon
		Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
			
		//Setze das Icon
		mainFrame.setIconImage(icon);
				
		//Definiere die Grösse der Fenster
		mainFrame.setSize(1, 1);
				
		//Definiere was beim Schliessen des Fenster passieren soll --> Programm beenden
		mainFrame.setDefaultCloseOperation(Settings.SETCLOSE);
				
		//Setzte die Position der Fenster
		mainFrame.setLocationRelativeTo(null);

		//Setzte, dass die Fenstergrösse durch den Benutzer nicht veränderbar ist
		mainFrame.setResizable(false);
				
		//Setzte Layout = null
		mainFrame.setLayout(null);
		
		//Initialisiere die Menubar
		initMenuBar();
		
		mainFrame.addKeyListener(kmm);
		
		//Zeiche das Fenster neu
		mainFrame.repaint();
	}
	
	private void initMainFrameEnd(){

		//Solange Splash noch nicht geschlossen ist
		while(!Splash.isClose()){
			
			//Versuche den Thread Schlafen zu legen
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			
		}
		
		//Bekomme die Bildschirmdiemsionen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Setze die Grösse des Hauptfensterns
		mainFrame.setSize(995, 528);
		
		//Setze die Position des Fensters
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
		
		//Setzte Fenster sichtbar
		mainFrame.setVisible(true);
		
		mainFrame.getCursor();
		
		//Update die Anzeigt bezüglich der Anzahl Schritten 
		updateControlpanelInformations();
	}
	private void initMenuBar(){
		//Erstelle MenuBar
		menuBar = new JMenuBar();
		
		//Setze die Position der Menubar
		menuBar.setBounds(0, 0, 1000, 20);
		
		//Erstelle Datei Menu
		file = new JMenu("Datei");
		
		//Füge des der Menubar hinzu
		menuBar.add(file);
		
		//Erstellle Speichern Menu
		save = new JMenuItem("Speichern");
		
		//Füge es dem Menupunkt Datei hinzu
		file.add(save);
		
		//Füge Einen ActionListener hinzu
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Erstellle einen Dateidialog
				JFileChooser chooser;
				
				
			    //FileSystemView erstellen
				FileSystemView file = FileSystemView.getFileSystemView();
				
				//Wenn gewollt, Ordner einschränken
				if(Settings.RESTRICTEDFILEMODE){
					  file = new DirectoryRestrictedFileSystemView(new File(Settings.RESTRICTEDPATH));
    
				}

				//Der Dateidialog initialisieren
			    chooser = new JFileChooser(file){
					private static final long serialVersionUID = 7625169427404387627L;

					//Bei auswahl
					@Override
				    public void approveSelection(){
						
						//Bekomme die Datei
				        File f = getSelectedFile();
				        
				        //Wenn die Datei existiert
				        if(f.exists() && getDialogType() == SAVE_DIALOG){
				        	
				        	//Füge hinzu, dass beim Überschreiben eine Warnung erscheint mit einem Dialog
				            int result = JOptionPane.showConfirmDialog(this,"Die Datei existiert bereits, überschreiben?","Datei existiert bereits",JOptionPane.YES_NO_CANCEL_OPTION);
				            
				            //Je nach dem was gedrückt wurde Aktion ausführen
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
			    
			    //Der Dateidialog soll Speichern
			    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			    
			    //Die erlaubten Dateiendungen sind "mssf" (MarusenkoSphere SaveFile)
			    FileNameExtensionFilter Filter = new FileNameExtensionFilter("MarusenkoSphere: mssf", "mssf");
			    
			    //Blockiere alle Dateiendungen
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			    
			    //Füge die erlaubten wieder hinzu
			    chooser.setFileFilter(Filter);
			    
			    //Setzet einen Titel für den Dialog
			    chooser.setDialogTitle("Speichern unter...");
			    
			    //Mache den Dialog sichtbar
			    chooser.setVisible(true);

			    //bekomme das Resultat
			    int result = chooser.showSaveDialog(mainFrame);

			    //Wenn Datei gespeichert werden soll
			    if (result == JFileChooser.APPROVE_OPTION) {

			    	//Bekomme den Speicherort
			    	String pfad = chooser.getSelectedFile().toString();
			    	
			    	//Sofern die Datei nicht mit ".mssf" endet Endung hinzufügen
			        if (!pfad.endsWith(".mssf")){
			        	pfad += ".mssf";
			        }
			        
			        //Datei anlegen
		         	File sfile = new File(pfad);
		         	
		         	//Soferndateiendung Erlaubt ist, sollte der Fall sein speichern 
		            if (Filter.accept(sfile)){
		            	
		            	//Speichere die Kugel
		            	ImportExportSphere.save(sfile, k);
		            }else{
		            	String message = "Kugel konnte nicht als Datei gespeichert werden";
		    		    JOptionPane.showMessageDialog(new JDialog(), message, "Error",
		    		        JOptionPane.ERROR_MESSAGE);  
		            }
		            
		            //Dialog schloessen
		            chooser.setVisible(false);
	    
			    }
			    
			    
			}
		});
		
		//Füge Menupunkt Öffnen hinzu
		open = new JMenuItem("Öffnen");
		
		//Füge Menupunkt dem Menu DAtei hinzu
		file.add(open);
		
		//Erstelle Actionlistener
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				//Erstellle einen Dateidialog
				JFileChooser chooser;
			    
				
				
				//FileSystemView erstellen
				FileSystemView file = FileSystemView.getFileSystemView();
				
				//Wenn gewollt, Ordner einschränken
				if(Settings.RESTRICTEDFILEMODE){
					  file = new DirectoryRestrictedFileSystemView(new File(Settings.RESTRICTEDPATH));
    
				}


			    //Der Dateidialog initialisieren
			    chooser = new JFileChooser(file);
			    
			    //Der Dateidialog soll Öffnen
			    chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			    
			 	//Die erlaubten Dateiendungen sind "mssf" (MarusenkoSphere SaveFile)
			    FileNameExtensionFilter Filter = new FileNameExtensionFilter("MarusenkoSphere: mssf", "mssf");
			    
			    //Blockiere alle Dateiendungen
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			    
			    //Füge die erlaubten wieder hinzu
			    chooser.setFileFilter(Filter);
			    
			    //Setzet einen Titel für den Dialog
			    chooser.setDialogTitle("Öffnen...");
			    
			    //Mache den Dialog sichtbar
			    chooser.setVisible(true);

			    //bekomme das Resultat
			    int result = chooser.showOpenDialog(mainFrame);

			    //Wenn Datei geöffnet werden soll
			    if (result == JFileChooser.APPROVE_OPTION) {

			    	//Bekomme den Speicherort
			    	String pfad = chooser.getSelectedFile().toString();

			    	//Datei anlegen
		         	File sfile = new File(pfad);
		         	
		         	//Soferndateiendung Erlaubt ist, sollte der Fall sein speichern 
		            if (Filter.accept(sfile)){
		            	
		            	//Öffne die Datei
		            	ImportExportSphere.open(sfile, k);
		            	
		            	//Aktuallisiere das Controlpanel
		            	updateControlpanelInformations();
		            	
		            }else{
		            	//Error Meldung
		            	String message = "Datei konnte nicht geöffnet werden";
		    		    JOptionPane.showMessageDialog(new JDialog(), message, "Error",
		    		        JOptionPane.ERROR_MESSAGE);  
		            }
		            
		            //Dialog schliessen
		            chooser.setVisible(false);

				}
			    
			}
		});
		
		//Füge einen Seperator dem Menu Datei hinzu
		file.addSeparator();
		
		//Erstelle Menupunkt Beenden
		exit = new JMenuItem("Beenden");
		
		//Füge Menupunkt dem Menu Datei hinzu
		file.add(exit);
		
		//Erstelle Actionlistener
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Programm beenden
				if(Settings.close()!=JFrame.DO_NOTHING_ON_CLOSE){
					System.exit(0);
				}
				
			}
		});
		
		//Erstelle Menu Einstellungen
		settings = new JMenu("Einstellungen");
		
		//Füge Einstellungen der Menubar hinzu
		menuBar.add(settings);
		
		touchscreen = new JCheckBoxMenuItem("Touchscreen");
		
		settings.add(touchscreen);
		
		touchscreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.touchmode = !Settings.touchmode;
				touchscreen.setSelected(Settings.touchmode);
				changeTouchscreenMode();
			}
		});
		touchscreen.setSelected(Settings.touchmode);
		
		//Erstelle Menu Hilfe
		help = new JMenu("Hilfe");
		
		//Füge Menu der Menubar hinzu
		menuBar.add(help);
		
		
		//ERsztelle Menupunkt Hilfecenter
		helpcenter = new JMenuItem("Hilfe Center");
		
		//Füge Menupunkt dem Menu Hilfehinzu
		help.add(helpcenter);
		
		//Erstelle Actionlistener
		helpcenter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Öffne Helpcenter
				new Help(0);
			}
		});
		
		//erstelle Menupunkt Über
		about = new JMenuItem("Über");
		
		//Füge Menupunkt dem Menu HIlfehinzu
		help.add(about);
		
		//Füge Actionlistener hinzu
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Erstelle Einen Dialog
				JOptionPane.showMessageDialog(mainFrame,
					    "<html>Ein Programm zur Darstellung und Visualisierung der MarusenkoSphere<br><br>von Marcel Würsten<br><br><br>Im Rahmen der Maturaarbeit und der Projektarbeit für den <br>nationalen Wettbewerb von Schweizer Jugend Forscht erstellt.</html>",
					    Settings.TITEL+" - Über",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		if(Settings.close()==JFrame.DO_NOTHING_ON_CLOSE){
			exit.setEnabled(false);
			touchscreen.setEnabled(false);
		}
		
		
		//Füge Menubar dem Fensterhinzu
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
	 * Update das Controlpanel bezüglich TouchscreenModus
	 */
	protected void changeTouchscreenMode(){
		cp.updateTouchscreen();
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
		
		//Sicherheitsabfrage, da nur bei Debugmodus das Debugmenu geöffnet werden darf...
		if(i==2){
			if(!Settings.DEBUGMODE){
				i = displayMode;
			}
		}
		
		//Wenn der Editor angezeigt wurde, dann prüfen ob kugel korrekt und ob übernommen werden kann
		if((displayMode==1||displayMode==3)&&(i!=1&&i!=3)){
			//System.out.println("Close Editor");
			//Wenn Kugel korrekt, dann übernehmen
			//System.out.println(levelOfSphere());
			if(levelOfSphere()!=-1){
			
				
				k.sphereFromEditor(levelOfSphere());
				updateControlpanelInformations();
				
			}else{
				
				//Wenn nicht, dann zu ursprünglicher Kugel zurück kehren
				goToStep(stepWhenGoesToEditor);
			}
		}
		
		//Wenn neu der Editor
		if((i==1||i==3)&&(displayMode!=1&&displayMode!=3)){
			//System.out.println("Open Editor");
			//Setzte Step, damit im nachhinein wieder zu diesem zurück gekehrt werden kann
			stepWhenGoesToEditor = k.getStep();
			updateInformationToLegalityOfSphere();
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
		
		cp.updateInfoIsSphereAllowed(levelOfSphere()==5);
		
	}
	
	/**
	 * Gibt das Level der Kugel zurück
	 */
	private int levelOfSphere(){
		//Erstelle Array für alle aktuellen Tris
		int[] checkTri = new int[24];
				
		//Erstelle Array für alle aktuellen Cons
		int[] checkCon = new int[8];
		
		//Fülle das TriArray mit aktuellen Werten
		for(int i = 0; i<24;i++){
			checkTri[i] = k.tri[i];
		}
				
		//Fülle das ConArray mit aktuellen Werten
		for(int i = 0; i<8;i++){
			checkCon[i] = k.con[i];
		}
		
		Arrays.sort(checkTri);
		Arrays.sort(checkCon);
		
		int[] conAnz = new int[8];
		int[] triAnz = new int[8];
		
		for(int i = 0; i<8; i++){
			conAnz[checkCon[i]]++;
		}
		for(int i = 0; i<24; i++){
			triAnz[checkTri[i]]++;
		}
		
		Arrays.sort(conAnz);
		Arrays.sort(triAnz);
		
		
		/*System.out.println(Arrays.toString(conAnz));
		System.out.println(Arrays.toString(triAnz));
		System.out.println("");-*/
		/*if(conAnz[6]==4&&conAnz[7]==4&&triAnz[6]==12&&triAnz[7]==12){
			return 1;
		}else if(conAnz[7]==8&&triAnz[7]==4&&triAnz[6]==4&&triAnz[5]==4&&triAnz[4]==4&&triAnz[3]==4&&triAnz[2]==4){
			return 2;
		}else if(conAnz[7]==4&&conAnz[6]==4&&triAnz[7]==8&&triAnz[6]==8&&triAnz[5]==4&&triAnz[4]==4){
			return 3;
		}else if(conAnz[7]==2&&conAnz[6]==2&&conAnz[5]==2&&conAnz[4]==2&&triAnz[7]==4&&triAnz[6]==4&&triAnz[5]==4||triAnz[4]==4){
			return 4;
		}else*/ 
		if(conAnz[7]==1&&triAnz[7]==3&&triAnz[6]==3&&triAnz[5]==3&&triAnz[4]==3&&triAnz[3]==3&&triAnz[2]==3){
			return 5;
		}
		
		return -1;
	}
	
	/*
	 * Setze Level von Kugel neu
	 */
	protected void setToLevel(int i){
		k.setToLevel(i);
	}
	
	
	protected void resetPosition(){
		//Bekomme die Bildschirmdiemsionen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				
		//Setze die Position des Fensters
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
		
	}
	
	/**
	 * Wenn das Programm beendet werden soll
	 */
	protected void exitProgramm(){	
		 System.exit(0);

	}
	
}
