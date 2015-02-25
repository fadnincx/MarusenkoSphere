package marusenkoSphere;

import javax.swing.JFrame;

/**
 * Settings-Klasse
 * Enthält diverse Einstellungen an einem Zentralen Ort, 
 * so dass diese nicht überall zusammen gesucht werden müssen
 *
 */
public class Settings {
	
	//Allgemein
	public static final boolean DEBUGMODE = false;
	public static final int SETCLOSE = JFrame.EXIT_ON_CLOSE;
	public static final boolean KIOSKMODE = false;
	public static final boolean TOUCHMODE = true;
	public static final String TITEL = "MarusenkoSphere";
	public static final boolean RESTRICTEDFILEMODE = false;
	
	//Main
	public static final boolean STARTKONSOLE = false;

	//Log
	public static final String LOGPATH = "D:/marusenkoLog";
	
	//Kugel
	public static final boolean PRINTSPHERECODE = false;
	
	//RendernKugel
	public static final int KUGELRENDERRASTER = 10; //Nur 1, 2, 5, 10
	
	
	public static boolean debug(){
		return DEBUGMODE;
	}
	public static int close(){
		return SETCLOSE;
	}
}
