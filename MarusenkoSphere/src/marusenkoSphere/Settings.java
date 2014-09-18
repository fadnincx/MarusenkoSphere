package marusenkoSphere;

/**
 * Settings-Klasse
 * Enthält diverse Einstellungen an einem Zentralen Ort, 
 * so dass diese nicht überall zusammen gesucht werden müssen
 *
 */
public class Settings {
	
	//Allgemein
	public static final boolean DEBUGMODE = true;
	
	//Main
	public static final boolean STARTKONSOLE = false;

	//Log
	public static final String LOGPATH = "D:/marusenkoLog";
	
	//Kugel
	public static final boolean PRINTSPHERECODE = false;
	
	//RendernKugel
	public static final int KUGELRENDERRASTER = 10; //Nur 1, 2, 5, 10
}
