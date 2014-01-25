package marusenkoSphere;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Main {

	public static void main(String[] args){
		Kugel k = new Kugel();
		KugelRendern kr = new KugelRendern(k);
		kr.run();
		k.FillKugleRandom(8);
		System.out.println("Next Time");
		kr.updateKugel(k);
		int counter =  0;
		while(true){
			counter++;
			if(counter == 1000){
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
				
			}
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
		
		
		
		/*GUI GUI = new GUI(k);
		//k.ChangeColor(3,3);
		//GUI.drawKugleToFrame(k);
		k.FillKugleRandom(8);
		GUI.drawKugleToFrame(k);
		
		/*while(true){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Next Time");
		k.DrehenVertikal(1);
		GUI.drawKugleToFrame(k);
		}*/
		
	}
	
	
}
