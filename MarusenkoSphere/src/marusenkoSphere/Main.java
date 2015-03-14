package marusenkoSphere;


import marusenkoSphereGUI.Manager;
import marusenkoSphereKonsole.Konsole;
import marusenkoSphereKugel.Kugel;

/**
 * Main-Datei
 * 
 * Wird von Java gestartet. Initialisiert das Kugel Objekt und startet anschliessend das weiterer Programm
 * 
 */
public class Main {

	public static void main(String[] args){		 

		
		//Erstelle eine neues Kugel Objekt  
		Kugel k = new Kugel();
		
		//Versuche das weitere Programm zu starten
		try{
			
			//Öffne Schliessen auf USB
			new CloseOnUSBKey();
			
			if(Settings.STARTKONSOLE){
			
				//Starte die Konsole
				new Konsole(k);
				
			}else{
				
				//Starte den Manager
				new Manager(k);
				
			}
			
		}catch(Exception e){
			
			//Wenn Fehler, dann ausgeben
			e.printStackTrace();
			
		}
		
	}	
	
}
