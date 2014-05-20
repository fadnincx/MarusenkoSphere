package marusenkoSphere;

import java.util.Arrays;
import java.util.List;

import marusenkoSphereGUI.Manager;
import marusenkoSphereKonsole.Konsole;
import marusenkoSphereKugel.Kugel;

/**
 * Main-Datei
 * 
 * Wird gestartet, initialisiert das Kugel-Objekt und gibt dieses dem Manager f�r die GUI oder der Konsole weiter
 * 
 */
public class Main {
	
	private static boolean konsole = false;
	
	@SuppressWarnings("unused")
	public static void main(String[] args){		 

		//Wandle die gegebenen Input Variablen in eine ArrayList um, da diese in diesem Fall einfacher zu handhaben ist
		List<String> StartArgs = Arrays.asList(args);
		
		//Schreibe die ganze ArrayList klein
		StartArgs = StringArrayListToLowerCase(StartArgs);
		
		
		//Wenn Argument "konsole", "console" oder "ng" bzw "-ng" (NoGUI) �bergeben worden ist, dann mit keiner GUI starten 
		if(StartArgs.contains("konsole")||StartArgs.contains("console")||StartArgs.contains("ng")||StartArgs.contains("-ng")){
			konsole = true;
		}
		
		//Kugel
		Kugel k = new Kugel();
		
		
		//Entscheide, ob GUI oder Konsole gestarted wird
		if(!konsole){
			//Starte GUI
			Manager m = new Manager(k);
		}else{			
			//Starte Konsole
			Konsole c = new Konsole(k);
		}
		
		
		
	}
	
	/**
	 * Funktion zum ganze String ArrayList klein zu schreiben
	 * @param s 
	 * @return
	 */
	private static List<String> StringArrayListToLowerCase(List<String> StringList){
		// Gehe f�r jedes Element durch --> Variable l ist zu beginn gesetzt, damit nicht jedesmal die L�nge neu berechnet werden muss, da sich diese nie �ndert
		for(int i=0,l=StringList.size(); i<l; i++){
			//Ersetzte das Element an der Stelle i durch das klein geschriebene Element an der Stelle i
			StringList.set(i, StringList.get(i).toLowerCase());
		}
		return StringList;
		
	}
	
	
}
