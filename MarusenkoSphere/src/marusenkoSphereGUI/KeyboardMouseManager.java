package marusenkoSphereGUI;


import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * KeyboardMouseManager-Klasse
 * 
 * Verwaltet die ganzen Keyboard und Mausaktionen
 *
 */
public class KeyboardMouseManager {
	
	//Eine ArrayList in der "virtuell" Tasten gedrückt werden um mit den Buttons im Controlpanel die Tastatur zu ersetzen 
	protected static LinkedList<Character> pressedKey = new LinkedList<Character>();
	
	protected static void Input(Manager m){	
		
		//Programm beenden, wenn Esc gedrückt wurde oder wenn Fenster geschlossen wird
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)||Display.isCloseRequested()) {
	        m.exitProgramm();
	    }
		
	    //Zu Kugel wechseln wenn K gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
	    if(Keyboard.isKeyDown(Keyboard.KEY_K)||pressedKey.contains('k')) {
	    	//Wenn K nicht blockiert ist
	    	if(!m.blockedKey.contains("k")){
	    		//K blockieren
	    		m.blockedKey.add('k');
	    		//Modus wechseln
	    		m.changeToMode(0);
		    	//100ms warten
		    	m.sleep(100);
		    	//Blockierung aufheben
		    	m.blockedKey.remove("k");
	    	}
	    }
	    
	    //Zu Editor wechseln wenn E gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
	    if(Keyboard.isKeyDown(Keyboard.KEY_E)||pressedKey.contains('e')) {
	    	//Wenn E nicht blockiert
	    	if(!m.blockedKey.contains("e")){
	    		//E blockieren
	    		m.blockedKey.add('e');
	    		//Modus wechseln
	    		m.changeToMode(1);
	    		//100ms warten
		    	m.sleep(100);
		    	//Blockierung aufheben
		    	m.blockedKey.remove("e");
	    	}
	    }
	    
	    //Zu Devpanel wechsel wenn D gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
	    if(Keyboard.isKeyDown(Keyboard.KEY_D)||pressedKey.contains('d')) {
	    	//Wenn D nicht blockiert
	    	if(!m.blockedKey.contains("d")){
	    		//D blockieren
	    		m.blockedKey.add('d');
	    		//Modus wechseln
	    		m.changeToMode(2);
	    		//100ms warten
		    	m.sleep(100);
		    	//Blockierung aufheben
		    	m.blockedKey.remove("d");
	    	}
	    }
	    
	    //Wenn Kugel dargestellt wird (auch Dev-Modus)
	    if(m.getDisplayMode()==0||m.getDisplayMode()==2){
	    	
	    	//Drehen, wenn links gedrück ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
		    	//Rotiere nach Links
		    	m.changeRotationAngle(0, -1);
		    }
		    
		    //Drehen, wenn rechts gerückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
		    	//Rotiere nach Rechts
		    	m.changeRotationAngle(0, 1);
		    }
		    
		    //Drehen, wenn oben gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
		    	//Rotiere nach Oben
		    	m.changeRotationAngle(-1, 0);
		    }
		    
		    //Drehen, wenn Unten gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
		    	//Rotieren nach Unten
		    	m.changeRotationAngle(1, 0);
		    }
		    
		    //Animation zum lösen der Kugel bis am Schluss starten, wenn L gedrückt ist, zusätzlich L 100ms blockieren,  damit nicht mehrere Anschläge in einem gemacht werden
		    if(Keyboard.isKeyDown(Keyboard.KEY_L)||pressedKey.contains('l')){	
		    	//Wenn L nicht blockiert
				if(!m.blockedKey.contains("l")){
					//L blockieren
					m.blockedKey.add('l');
					//starte Animation
					m.changeRunAnimationToEnd();
			    	//100ms warten
			    	m.sleep(100);
			    	//Blockierung aufheben
			    	m.blockedKey.remove("l");
		    	}
			}
		    
		    //Kugel neu füllen, wenn S gedrückt ist, zusätzlich 200ms blockieren,  damit nicht mehrere Anschläge in einem gemacht werden
		    if(Keyboard.isKeyDown(Keyboard.KEY_S)||pressedKey.contains('s')) {
		    	//Wenn S nicht blockiert
		    	if(!m.blockedKey.contains("s")){
		    		//S blockieren
		    		m.blockedKey.add('s');
		    		//Kugel neu füllen
		    		m.fillSphere();
			    	//200ms warten
			    	m.sleep(200);
			    	//Blockierung aufheben
			    	m.blockedKey.remove("s");
		    	}
		    }
		    
		    //Einen Schritt weiter gehen, wenn X gedrückt, zusätzlich 100ms blockieren,  damit nicht mehrere Anschläge in einem gemacht werden
		    if(Keyboard.isKeyDown(Keyboard.KEY_X)||pressedKey.contains('x')) {
		    	//Wenn X nicht blockiert
		    	if(!m.blockedKey.contains("x")){
		    		//X blockieren
		    		m.blockedKey.add('x');
		    		//Aktion zu Queue hinzufügen
		    		m.addToSolvingQueue('x');
			    	//100ms warten
			    	m.sleep(100);
			    	//Blockierung aufheben
			    	m.blockedKey.remove("x");
		    	}
		    	
		    }
		    
		    //Einen Schritt zurück gehen, wenn Y gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
		    if(Keyboard.isKeyDown(Keyboard.KEY_Y)||pressedKey.contains('y')) {
		    	//Wenn Y nicht blockiert
		    	if(!m.blockedKey.contains("y")){
		    		//Y blockieren
		    		m.blockedKey.add('y');
		    		//Aktion zu Queue hinzufügen
		    		m.addToSolvingQueue('y');
			    	//100ms warten
			    	m.sleep(100);
			    	//Blockierung aufheben
			    	m.blockedKey.remove("y");
		    	}
		    }
		    
		    //Drehung zurück setzten wenn R gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
		    	//Setze die Drehung zurück
		    	m.resetRotationAngle();
		    }
		    
	    }else
	    //Wenn der Editor angezeigt wird
	    if(m.getDisplayMode() == 1){

	    	//Wenn links Klick aus geführt wird
	    	if(Mouse.isButtonDown(0)){
	    		
	    		//Speichere die x und y Koordinate der Maus im Fenster
		    	int x = Mouse.getX();
		    	int y = Mouse.getY();
	    		
	    		//Bekomme die Koordinaten der Maus im 3Dimensionalen System
	    		double[] mousePosIn3D = Editor.MouseIn3D(x,y);
	    		
	    		//Wenn die Tiefe Z <= 4 ist, dann ist dort ein Objekt--> also Muss Aktion statt finden
	    		if(mousePosIn3D[2]<=4){
	    			
	    			//Objekt auf welchem die Maus ist
	    			int objekt = Editor.onWhichField(mousePosIn3D[0],mousePosIn3D[1]);
	    			
	    			//Wenn objekt < 24 ist, dann ist es ein Dreieck sonst ein Verbindungsstück
	    					
	    			if(objekt>=0&&objekt<24){
	    				m.editSphereTri(objekt);
	    			}else
	    			if(objekt>=0&&objekt<32){
	    				m.editSphereCon(objekt-24);
	    			}
	    		}
	    	}
	    }
	pressedKey.clear();
	}
}
