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
	public static final boolean KIOSKMODE = true;
	
	
	//Main
	public static final boolean STARTKONSOLE = false;

	//Log
	public static final String LOGPATH = "D:/marusenkoLog";
	
	//Kugel
	public static final boolean PRINTSPHERECODE = false;
	
	//RendernKugel
	public static final int KUGELRENDERRASTER = 10; //Nur 1, 2, 5, 10
	
	//Controlpanel
	public static final int VERSCHIEBEFENSTERX = 165;
	public static final int VERSCHIEBEFENSTERY = -245;
	
	//Mouse
	public static final double MOUSESENSITIVE = 0.1;
	
	
	
}
