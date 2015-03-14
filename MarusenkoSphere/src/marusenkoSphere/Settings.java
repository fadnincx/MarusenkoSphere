package marusenkoSphere;

import java.io.File;

import javax.swing.JFrame;

/**
 * Settings-Klasse
 * Enthält diverse Einstellungen an einem Zentralen Ort, 
 * so dass diese nicht überall zusammen gesucht werden müssen
 *
 */
public class Settings {
	
	//Debugmodus
	public static final boolean DEBUGMODE = false;
	
	//Aktion bei Klick auf X
	public static final int SETCLOSE = JFrame.EXIT_ON_CLOSE;
	
	//Kioskmodus
	public static final boolean KIOSKMODE = false;
	
	//Titel des Fensters
	public static final String TITEL = "MarusenkoSphere";
	
	//Pfade, der im Kioskmodus zulässig ist
	public static final File[] RESTRICTEDPATH = new File[]{new File("C:/MarusenkoSphere/MarusenkoSphere")};
	
	//Pfad OnScreenKeyboard
	public static final String ONSCREENKEYBOARD = "C:/windows/system32/cmd /c C:/Windows/system32/osk.exe";
	
	//Ist Touchmodus Aktiv?
	public static boolean touchmode = true;
	
	//Konsole starten????
	public static final boolean STARTKONSOLE = false;

	//Pfad für Log-Dateien
	public static final String LOGPATH = "D:/marusenkoLog";
	
	//Gibt für jede Kugel den Spherecode aus
	public static final boolean PRINTSPHERECODE = false;
	
	//Raster dichte zum Kugelnrendern
	public static final int KUGELRENDERRASTER = 10; //Nur 1, 2, 5, 10
	
	
	//Gibt debugmodus zurück, damit kein Error weil final
	public static boolean debug(){
		return DEBUGMODE;
	}
	
	//Gibt close zurück, damit kein Error weil final
	public static int close(){
		return SETCLOSE;
	}
	
}
