package marusenkoSphereGUI;

import marusenkoSphere.Settings;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * KeyboardMouseManager-Klasse
 * 
 * Verwaltet die ganzen Keyboard und Mausaktionen
 *
 */
public class KeyboardMouseManager {
	private static boolean lookPfeile = false;
	private static int position = -1;
	private static int lastX;
	private static int lastY;
	private static boolean last = false;
	/**
	 * Wird aufgerufen um den Input zu überprüfen
	 */
	protected static void Input(Manager m){	
		
		//Fenster in Ursprungsposition verschieben
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			
			//Fenster verschieben
			m.resetPosition();
	        
	    }
		
	    //Zu Kugel wechseln wenn K gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
	    if(Keyboard.isKeyDown(Keyboard.KEY_K)) {
	    	
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
	    if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
	    	
	    	//Wenn E nicht blockiert
	    	if(!m.blockedKey.contains("e")){
	    	
	    		//E blockieren
	    		m.blockedKey.add('e');
	    		
	    		//Modus wechseln
	    		m.changeToMode(3);
	    		
	    		//100ms warten
		    	m.sleep(100);
		    	
		    	//Blockierung aufheben
		    	m.blockedKey.remove("e");
	    	
	    	}
	    
	    }
	    
	    //Zu Devpanel wechsel wenn D gedrückt, zusätzlich 100ms blockieren, damit nicht mehrere Anschläge in einem gemacht werden
	    if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
	    	
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
	    	 
	    	//Experimentierfeature nur bei Debugmode
	    	if(Settings.debug()){
	    		
		    	if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
		    		//Speichere die x und y Koordinate der Maus im Fenster
			    	int x = Mouse.getX();
			    	int y = Mouse.getY();
		    		
		    		//Bekomme die Koordinaten der Maus im 3Dimensionalen System
		    		double[] mousePosIn3D = Editor.MouseIn3D(x,y);
		    		
		    		//Wenn auf Kugel geklickt
		    		if(!lookPfeile&&mousePosIn3D[0]*mousePosIn3D[0]<1.5&&mousePosIn3D[1]*mousePosIn3D[1]<1.5&&mousePosIn3D[2]*mousePosIn3D[2]<1.5){
		    			//System.out.println("");
		    			position = Editor.positionOnSphere(mousePosIn3D[0], mousePosIn3D[1], mousePosIn3D[2]);
		    			System.out.println(position);
		    			m.setNewPfeilID(position);
		    			
		    		}
		    	}else if(m.getPfeilID()!=-1){
		    		m.setNewPfeilID(-1);
		    		lookPfeile = false;
		    	}
		    	
		    	//Kugel verändern
		    	if(Mouse.isButtonDown(0)&&Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
		    		lookPfeile=true;
		    	}
		    	
	    	}
	    	
	    	//Drehen, wenn links gedrück ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
		
		    	//Rotiere nach Links
		    	m.changeRotationAngle(0, 1);
		    
		    }
		    
		    //Drehen, wenn rechts gerückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
		    
		    	//Rotiere nach Rechts
		    	m.changeRotationAngle(0, -1);
		    
		    }
		    
		    //Drehen, wenn oben gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
		    
		    	//Rotiere nach Oben
		    	m.changeRotationAngle(1, 0);
		   
		    }
		    
		    //Drehen, wenn Unten gedrückt ist
		    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
		    
		    	//Rotieren nach Unten
		    	m.changeRotationAngle(-1, 0);
		   
		    }
		    
		    //Animation zum lösen der Kugel bis am Schluss starten, wenn L gedrückt ist, zusätzlich L 100ms blockieren,  damit nicht mehrere Anschläge in einem gemacht werden
		    if(Keyboard.isKeyDown(Keyboard.KEY_L)){	
		    
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
		    if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
		    
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
		    if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
		    
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
		    if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {
		    	
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

	    //Wenn der 2D-Editor angezeigt wird
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
	    
	    }else
	    //Wenn 3D-Editor angezeigt wird
	    if(m.getDisplayMode() == 3){
	    	
	    	if(Mouse.isButtonDown(0)){
	    		
	    		if(!last){
	    			last = true;
	    			lastX = Mouse.getX();
			    	lastY = Mouse.getY();
			    	//Log.DebugLog("Do Editor...");
	    		}
	    		
	    	}else{
	    		if(last){
	    			if(lastX==Mouse.getX()&&lastY==Mouse.getY()){
	    				//Bekomme die Koordinaten der Maus im 3Dimensionalen System
	    	    		double[] mousePosIn3D = Editor.MouseIn3D(lastX,lastY);
	    	    		position = Editor.positionOnSphere(mousePosIn3D[0], mousePosIn3D[1], mousePosIn3D[2]);
	        			//System.out.println(position);	
	        			
	        			if(position>=0&&position<24){
	        
	        				m.editSphereTri(position);
	        			
	        			}else
	        			if(position>=30&&position<38){
	        			
	        				m.editSphereCon(position-30);
	        			
	        			}
	    			}
	    		}
	    		last = false;
	    	}
	    }
	
	}
	

}
