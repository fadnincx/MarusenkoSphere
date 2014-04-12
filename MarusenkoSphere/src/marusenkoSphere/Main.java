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
		int i;
		try{
			i = Integer.parseInt(args[0].trim());
		}catch(Exception e){
			System.out.println("Konnte kein Input lesen");
			i = 0;
		}
		i--;
		String[] logPlace = new String[6];
		logPlace[0]="B:/";
		logPlace[1]="C:/users/marcel/Desktop/marusenkoLog/1/";
		logPlace[2]="D:/marusenkoLog/";
		logPlace[3]="C:/users/marcel/Desktop/marusenkoLog/2/";
		logPlace[4]="C:/users/marcel/Desktop/marusenkoLog/3/";
		logPlace[5]="C:/users/marcel/Desktop/marusenkoLog/4/";
		//Debugging Logger
		Logger d = new Logger(logPlace[i],"debugLog");
		
		//Error Logger
		Logger l = new Logger(logPlace[i],"errorLog");
		
		 
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
			
			Konsole c = new Konsole(k,s,d,l,logPlace[i]);
		}
		
		
		
	}
	
	
}
