package marusenkoSphereGUI;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
	    //Zu Editor wechseln/zurück wenn E gedrückt, zusätzlich 100ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
	    	if(!m.BlockedKey.contains("e")){
	    		m.changeModeEdior();
	    		//System.out.println("e");
		    	m.BlockedKey.add("e");
		    	m.update(100);
		    	m.BlockedKey.remove("e");
	    	}
	    }
	   //Zu Devpanel wechseln/zurück wenn D gedrückt, zusätzlich 100ms blockieren, damit nicht doppelt passiert
	    if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
	    	if(!m.BlockedKey.contains("d")){
	    		m.enableDevPanel();
	    		//System.out.println("D");
		    	m.BlockedKey.add("d");
		    	m.update(100);
		    	m.BlockedKey.remove("d");
	    	}
	    }
	    //Wenn Kugel dargestellt wird
	    if(m.displayMode==0){
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
		  //Kugel lösen wenn L gedröckt is, zusätzlich L 100ms blockieren, damit nicht doppelt passiert
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
		    		m.toDoQueue.offer("x");
			    	m.BlockedKey.add("x");
			    	m.update(100);
			    	m.BlockedKey.remove("x");
		    	}
		    	
		    }
		    //Einen Schritt zurück gehen, wenn Y gedrückt, zusätzlich 100ms blockieren, damit nicht doppelt passiert
		    if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {
		    	if(!m.BlockedKey.contains("y")){
		    		m.toDoQueue.offer("y");
			    	m.BlockedKey.add("y");
			    	m.update(100);
			    	m.BlockedKey.remove("y");
		    	}
		    }
		    //Drehung zurück setzten wenn R gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
		    	m.rendernDrehen(0.0f,0.0f,0.0f,1);
		    }
	    }else
	    //Wenn der Editor angezeigt wird
	    if(m.displayMode == 1){
	    	
	    	//Speichere die x und y Koordinate der Maus im Fenster
	    	int x = Mouse.getX();
	    	int y = Mouse.getY();
	    	
	    	//Wenn links Klick aus geführt wird
	    	if(Mouse.isButtonDown(0)){//Links Klick
	    		
	    		//Bekomme die Koordinaten der Maus im 3Dimensionalen System
	    		double[] mousePosIn3D = Editor.isMouseIn3D(x,y);
	    		
	    		//Wenn die Tiefe Z = 0 ist, dann ist dort ein Objekt--> also Muss Aktion statt finden
	    		if(mousePosIn3D[2]<=4){
	    			
	    			//Objekt auf welchem die Maus ist
	    			int objekt = Editor.onWhichField(mousePosIn3D[0],mousePosIn3D[1]);
	    			
	    			//Wenn ein Dreieck ist
	    			if(objekt<24){
	    				m.editSphereTri(objekt);
	    			}else{
	    				m.editSphereCon(objekt-24);
	    			}
	    		}
	    	}
	    }
	    if(Manager.doQueue){
	    	QueueManager.Queue(m);
	    }
	    //Rotation in Statische Variabel schreiben und Kugel neu Rendern
	    m.getRotationSpeedfromCp();
		m.renderKugel();
	    
	}
}
