package marusenkoSphere;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Main {

	/**
	 * Main-Datei
	 * 
	 * Wird gestartet, initialisiert die Objekte
	 * 
	 */
	public static void main(String[] args){
		/**
		 * Erstelle ein Kugel-Objekt
		 */
		Kugel k = new Kugel();
		
		/**
		 * Debugging Information, dass Kugel erfolgreich erstellt wurde
		 */
		System.out.println("Created Kugel");
		
		/**
		 * Erstelle das KugelRendern-Objekt, dies ist für die ganze Darstellung der Kugel verantwortlich
		 */
		KugelRendern kr = new KugelRendern(k);
		
		/**
		 * Debugging Information, dass KugelRendern erfolgreich erstellt wurde
		 */
		System.out.println("Created KugelRendern");
		
		/**
		 * Starte das Rendern der Kugel im Objekt KugelRendern
		 */
		kr.run();
		
		/**
		 * Debuggling Information, dass das starten Erfolgreich war
		 */
		System.out.println("Start Running");
		
		/**
		 * Befülle die Kugel mit Werten
		 * 
		 * k.FillKugelRandom(); für zufälliges Füllen der Kugel mit realistischen Werten
		 * 
		 * k.FillKugelFix(); für das füllen der Kugel mit fixen Werten welche in der Funktion definiert wurden, einfarbige Kugel
		 */
		//k.FillKugelRandom();
		k.FillKugelFix();
		
		/**
		 * Debugging Information, dass das füllen erfolgreich war
		 */
		System.out.println("Filled Random");
		
		/**
		 * Sende die geupdatete Kugel an das KugelRendern-Objekt, damit es dargestellt werden kann 
		 */
		kr.updateKugel(k);
		
		/**
		 * Debugging Information damit Update der Kugel erfolgreich an KugelRendern gesendet werden kann		
		 */
		System.out.println("Updated Kugel");
		
		/**
		 * Erstelle das Solver-Objekt welches Kugel lösen soll
		 */
		Solver s = new Solver(k);
		
		/**
		 * Debugging Information, dass Solver Initialisiert wurde
		 */
		System.out.println("Initialised Solver");
		
		/**
		 * Sende Kugel an Solver zum lösen
		 * 
		 * Dieser Sendet die gelöste Kugel zurück
		 */
		k = s.solve(k);
		
		/**
		 * Debugging Information, dass Kugel gelöst wurde
		 */
		System.out.println("Solved Kugel");
		
		/**
		 * Sende gelöste Kugel an KugelRendern
		 */
		kr.updateKugel(k);
		
		/**
		 * Debugging Information, dass Kugel geupdatet wurde
		 */
		System.out.println("Updated Kugel to Screen");
		
		/**
		 * 
		 * while(true)
		 * 
		 * zum drehen der Kugel
		 * 
		 */
	//	int counter =  0;
		while(true){
		//	counter++;
			/*if(counter == 1000){
				counter = 0;
				
				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			    	k.DrehenHorizontal(3);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			    	k.DrehenHorizontal(1);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			    	k.DrehenVertikal(1);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			    	k.DrehenVertikal(3);
			    }
			    
			    if(Keyboard.isKeyDown(Keyboard.KEY_J)){
			    	k.TurnPol(4,1);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_K)){
			    	k.TurnPol(4,3);
			    }
			    
			    if(Keyboard.isKeyDown(Keyboard.KEY_T)){
			    	k.HalbDrehenVertikal(1);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			    	k.HalbDrehenVertikal(3);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			    	k.HalbDrehenHorizontal(1);
			    }
			    if(Keyboard.isKeyDown(Keyboard.KEY_H)){
			    	k.HalbDrehenHorizontal(3);
			    }
				
			}*/
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
		        kr.end();
		    }
		    if(Display.isCloseRequested()) {                     // Exit if window is closed
		        kr.end();
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is F1 Being Pressed?
		    	kr.drehen(-0.02f,0.0f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is F1 Being Pressed?
		      	kr.drehen(+0.02f,0.0f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is F1 Being Pressed?
		      	kr.drehen(0.0f,-0.02f);
		    }
		    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is F1 Being Pressed?
		        kr.drehen(0.0f,+0.02f);
		    }

			kr.doing();

		}
	}
	
	
}
