package marusenkoSphere;

import marusenkoSphereKugel.Kugel;
import marusenkoSphereRender.KugelRendern;
import marusenkoSphereSolving.Solver;

public class Main {
	
	
	/**
	 * Main-Datei
	 * 
	 * Wird gestartet, initialisiert die Objekte und gibt diese dem Manager weiter
	 * 
	 */
	public static void main(String[] args){
		//Debugging Logger
		Logger d = new Logger("debugLog");
		
		//Error Logger
		Logger l = new Logger("errorLog");
		
		//Kugel
		Kugel k = new Kugel();
		
		//KugelRendern
		KugelRendern kr = new KugelRendern(k);
		
		k.FillKugelRandom();
		kr.updateKugel(k);
		
		Solver s = new Solver(); 
		
		@SuppressWarnings("unused")
		Manager m = new Manager(k,kr,s,d,l);
		
	}
	
	
}
