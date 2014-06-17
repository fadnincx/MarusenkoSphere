package marusenkoSphere;

import java.util.Arrays;
import java.util.List;

import marusenkoSphereGUI.Manager;
import marusenkoSphereKonsole.Konsole;
import marusenkoSphereKugel.Kugel;

/**
 * Main-Datei
 * 
 * Wird gestartet, initialisiert das Kugel-Objekt und gibt dieses dem Manager für die GUI oder der Konsole weiter
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
		
		
		//Wenn Argument "konsole", "console" oder "ng" bzw "-ng" (NoGUI) übergeben worden ist, dann mit keiner GUI starten 
		if(StartArgs.contains("konsole")||StartArgs.contains("console")||StartArgs.contains("ng")||StartArgs.contains("-ng")){
			konsole = true;
		}
		System.out.println("started");
		//Kugel
		Kugel k = new Kugel();
		//System.out.println("kugel");
		
		//Entscheide, ob GUI oder Konsole gestarted wird
		if(!konsole){
			//Starte GUI
			try{
				Manager m = new Manager(k);
			}catch(Exception e){
				e.printStackTrace();
			}
			
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
		// Gehe für jedes Element durch --> Variable l ist zu beginn gesetzt, damit nicht jedesmal die Länge neu berechnet werden muss, da sich diese nie ändert
		for(int i=0,l=StringList.size(); i<l; i++){
			//Ersetzte das Element an der Stelle i durch das klein geschriebene Element an der Stelle i
			StringList.set(i, StringList.get(i).toLowerCase());
		}
		return StringList;
		
	}
	
	
}
