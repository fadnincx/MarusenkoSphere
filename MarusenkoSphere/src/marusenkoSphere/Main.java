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
	
	//Bekomme die unveränderliche Variable, ob die Kugel gestartet werden soll
	private static boolean konsole = Settings.STARTKONSOLE;

	
	public static void main(String[] args){		 

		//Wenn Argument "ng" (NoGUI) übergeben worden ist, dann ohne GUI starten
		konsole = ((args.length>0&&args[0]=="ng")||konsole);
		
		//Erstelle eine neues Kugel Objekt  
		Kugel k = new Kugel();
		
		//Versuche das weitere Programm zu starten
		try{
			
			if(konsole){
			
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
