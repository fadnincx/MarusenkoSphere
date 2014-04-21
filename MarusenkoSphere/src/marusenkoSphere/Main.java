package marusenkoSphere;

import marusenkoSphereGUI.Manager;
import marusenkoSphereKonsole.Konsole;
import marusenkoSphereKugel.Kugel;

public class Main {
	
	protected static final boolean KONSOLE = false;
	
	/**
	 * Main-Datei
	 * 
	 * Wird gestartet, initialisiert die Objekte und gibt diese dem Manager weiter
	 * 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args){		 
		//Kugel
		Kugel k = new Kugel();
		
		if(!KONSOLE){
			Manager m = new Manager(k);
		}else{			
			Konsole c = new Konsole(k);
		}
		
		
		
	}
	
	
}
