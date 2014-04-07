package marusenkoSphere;

import marusenkoSphereKugel.Kugel;
import marusenkoSphereRender.KugelRendern;
import marusenkoSphereSolving.Solver;

public class Main {
	
	protected static final boolean KONSOLE = true;
	
	/**
	 * Main-Datei
	 * 
	 * Wird gestartet, initialisiert die Objekte und gibt diese dem Manager weiter
	 * 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args){
		//Debugging Logger
		Logger d = new Logger("debugLog");
		
		//Error Logger
		Logger l = new Logger("errorLog");
		 
		//Kugel
		Kugel k = new Kugel();
		
		if(!KONSOLE){
			//KugelRendern
			KugelRendern kr = new KugelRendern(k);
			
			k.FillKugelRandom();
			kr.updateKugel(k);
			
			Solver s = new Solver(l,d); 
	
			Manager m = new Manager(k,kr,s,d,l);
		}else{
			Solver s = new Solver(l,d); 
			
			Konsole c = new Konsole(k,s,d,l);
		}
		
		
		
	}
	
	
}
