package marusenkoSphereGUI;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class KugelSteuern {

	public static void Input(Manager m){
		if(Keyboard.isKeyDown(Keyboard.KEY_L)){		
			if(!m.BlockedKey.contains("l")){
				m.startSolve();
		    	m.BlockedKey.add("l");
		    	m.update(100);
		    	m.BlockedKey.remove("l");
	    	}
			//m.startSolve();	
			//m.d.log("Solved Kugel");		
			//m.kr.updateKugel(m.k);
			//m.d.log("Updated Kugel to Screen");
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
			//m.d.log("Esc pressed");
	        m.kr.end();
	    }
	    if(Display.isCloseRequested()) {                     // Exit if window is closed
	    	//m.d.log("Window closed");		//SolvingList = s.solve(k);
	    	//	SolvingList = k.SolvingList;
	    	m.kr.end();
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is Left Being Pressed?
	    	m.kr.drehen(-0.02f,0.0f);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is Right Being Pressed?
	    	m.kr.drehen(+0.02f,0.0f);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is Up Being Pressed?
	    	m.kr.drehen(0.0f,-0.02f);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is Down Being Pressed?
	    	m.kr.drehen(0.0f,+0.02f);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {          // Is R Being Pressed?
	    	m.kr.setDrehen(0.0f,0.0f,0.0f);
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_S)) {          // Is S Being Pressed?
	    	if(!m.BlockedKey.contains("s")){
	    		m.fillSphere();
		    	m.BlockedKey.add("s");
		    	m.update200();
		    	m.BlockedKey.remove("s");
		    	//m.d.log("Reset Kugel");
	    	}
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_X)) {          // Is S Being Pressed?
	    	if(!m.BlockedKey.contains("x")){
	    		m.oneStep();
		    	m.BlockedKey.add("x");
		    	m.update(100);
		    	m.BlockedKey.remove("x");
	    	}
	    	
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {          // Is S Being Pressed?
	    	if(!m.BlockedKey.contains("y")){
	    		m.backStep();
		    	m.BlockedKey.add("y");
		    	m.update(100);
		    	m.BlockedKey.remove("y");
	    	}
	    }
	   /* if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {          // Is Num0 Being Pressed?
	    	m.k.turnKugel(0, 1);
	    	m.update200();
	    	m.d.log("Num0 pressed");
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {          // Is Num1 Being Pressed?
	    	m.k.turnKugel(1, 1);
	    	m.update200();
	    	m.d.log("Num1 pressed");
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {          // Is Num2 Being Pressed?
	    	m.k.turnKugel(2, 1);
	    	m.update();
	    	m.d.log("Num2 pressed");
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {          // Is Num3 Being Pressed?
	    	m.k.turnKugel(3, 1);
	    	m.update200();
	    	m.d.log("Num3 pressed");
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {          // Is Num4 Being Pressed?
	    	m.k.turnKugel(4, 1); 
	    	m.update200();
	    	m.d.log("Num4 pressed");
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {          // Is Num5 Being Pressed?
	    	m.k.turnKugel(5, 1);
	    	m.update200();
	    	m.d.log("Num5 pressed");
	    } */   
	    m.renderKugel();
	}
}
