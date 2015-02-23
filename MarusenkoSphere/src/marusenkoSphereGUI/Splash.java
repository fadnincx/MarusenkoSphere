package marusenkoSphereGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Class um zu beginn einen Splashscreen anzuzeigen
 *
 */
public class  Splash{
	
	//Fenster
	private JFrame frame;
	
	//Variable um zu prüfen, ob Splash schon geschlossen wurde
	private volatile static boolean closed = true;
	
	private volatile static boolean askClosed = true;
	
	//Fortschrittsbar
	private JProgressBar bar;

	public Splash() throws IOException {
		
		//Zuerst sagen, dass Splash läuft
		closed = false;
		askClosed = false;
		
		//Lade das BIld
		ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/splash.png"))).getImage().getScaledInstance(640, 400, Image.SCALE_SMOOTH));
		
		//Lade das Icon
		Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
		
		//Erstelle das Fenster
		frame = new JFrame();
		
		//Setze das Icon
		frame.setIconImage(icon);

		//Erstelle die Fortschrittsbar
		bar= new JProgressBar(0,2000);
		
		//Setzte Fortschritt auf 0
		bar.setValue(0);
		
		//Erstelle Label
		JLabel label = new JLabel(img);
		
		//Label Position
		label.setBounds(0, 0, 640, 400);
		
		//Grösse des Fensters soll nicht veränderbar sein
		frame.setResizable(false);
		
		//Fenster soll undecorated sein
		frame.setUndecorated(true);
		
		//Grösse des Fensters festlegen
		frame.setSize(640,400);
		
		//Fenster sichtbarmachen
		frame.setVisible(true);
		
		//Focus ablehnen
		frame.setFocusable(false);
		frame.setFocusableWindowState(false);
		
		//Immer im vordergrund anzeigen
		frame.setAlwaysOnTop(true);
		
		//Bekomme die BIldschirmdimensionen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Setzte das Fenster in die Mitte
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		//Setzte die Grösse und Position des Fortschrittsbalken
		bar.setBounds(50, 300, 540, 30);
		
		//Setzte True das Fortschrittbalken text anzeigt
		bar.setStringPainted(true);
		
		//Füge Bilde dem Fenster hinzu
		frame.add(label);
		
		//Füge Fortschrittbar dem Fenster hinzu
		frame.add(bar);
		
	}
	
	//Methode um den Fortschrittsbalken zu laden
	public void run(){
		
		//In neuen Thread
		new Thread(){
			
			//Run Methode
		    public void run(){
		    	
		    	int wert = 0;
		    	long startTime = System.currentTimeMillis();
		    	
		    	while(wert<2000){
		    		//Die vergangene Zeit speichern
		  			wert  = (int) (System.currentTimeMillis()-startTime);	
		  			
		  			if(wert>1800&&!askClosed){
		  				wert = 1800;
		  			}
		  			
		  			
		  			//Neuer Wert im Ladebalken setzten
		  			bar.setValue(wert);
		  			
		  			//Verschiedene Texte anzeigen
		  			if(wert>1800){
		  				bar.setString("Starte GUI");
		  			}else
		  			if(wert>1500){
		  				bar.setString("Initialisiere Lösungsalgorithmen");
		  			}else
		  			if(wert>1200){
		  				bar.setString("Initialisiere OpenGL");
		  			}else
		  			if(wert>800){
		  				bar.setString("Initialisiere Fenster");
		  			}else
		  			if(wert>400){
		  				bar.setString("Initialisiere Kugel");
		  			}else{
		  				bar.setString("Starte Java");
		  			}
		  			
		    	}
		  		
		  		//Nach dieser Zeit 
		  		//Fenster schliessen
		  		frame.dispose();
		  		
		  		//Variable, dass splash geschlossen ist auf true setzen.
		  		closed = true;
		  		
		    }
		}.start();
	
	}
	
	//Funktion ob Splash schon geschlossen wurde
	public static boolean isClose(){
		askClosed = true;
		return closed;
	}
}
