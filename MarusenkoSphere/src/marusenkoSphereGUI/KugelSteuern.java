package marusenkoSphereGUI;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class KugelSteuern {

	public static void Input(Manager m){
		
		//Programm beenden, wenn Esc gedrück
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
	        m.exitProgramm();
	    }
		//Programm beenden, wenn Fenster geschlossen wird
	    if(Display.isCloseRequested()) {                  
	    	m.exitProgramm();
	    }
	    //Drehen wenn links gedrück ist
	    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
	    	m.rendernDrehen(-0.1f,0.0f,0.0f,0);
	    }
	    //Drehen wenn rechts gerückt ist
	    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
	    	m.rendernDrehen(+0.1f,0.0f,0.0f,0);
	    }
	    //Drehen wenn oben gedrückt ist
	    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
	    	m.rendernDrehen(0.0f,-0.1f,0.0f,0);
	    }
	    //Drehen wenn Unten gedrückt ist
	    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
	    	m.rendernDrehen(0.0f,+0.1f,0.0f,0);
	    }
	    //Drehung zurück setzten wenn R gedrückt ist
	    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
	    	m.rendernDrehen(0.0f,0.0f,0.0f,1);
	    }
	    //Kugel lösen wenn L gedrückt is, zusätzlich L 100ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_L)){		
			if(!m.BlockedKey.contains("l")){
				m.startSolve();
		    	m.BlockedKey.add("l");
		    	m.update(100);
		    	m.BlockedKey.remove("l");
	    	}
		}
	    //Kugel neu füllen, wenn S gedrückt ist, zusätzlich 200ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
	    	if(!m.BlockedKey.contains("s")){
	    		m.fillSphere();
		    	m.BlockedKey.add("s");
		    	m.update(200);
		    	m.BlockedKey.remove("s");
	    	}
	    }
	    //Einen Schritt weiter gehen, wenn X gedrückt, zusätzlich 100ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
	    	if(!m.BlockedKey.contains("x")){
	    		m.oneStep();
		    	m.BlockedKey.add("x");
		    	m.update(100);
		    	m.BlockedKey.remove("x");
	    	}
	    	
	    }
	    //Einen Schritt zurück gehen, wenn Y gedrückt, zusätzlich 100ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {
	    	if(!m.BlockedKey.contains("y")){
	    		m.backStep();
		    	m.BlockedKey.add("y");
		    	m.update(100);
		    	m.BlockedKey.remove("y");
	    	}
	    }
	    
	    //Kugel neu Rendern
	    m.renderKugel();
	}
}
