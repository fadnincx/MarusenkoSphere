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
	
	private static boolean konsole = Settings.STARTKONSOLE;

	public static void main(String[] args){		 

		//Wenn Argument "ng" (NoGUI) übergeben worden ist, dann mit keiner GUI starten
			konsole = ((args.length>0&&args[0]=="ng")||konsole) ? true:false;
		
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
			e.printStackTrace();
		}
	}	
}
