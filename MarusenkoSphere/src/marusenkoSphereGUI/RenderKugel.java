package marusenkoSphereGUI;

import marusenkoSphere.Settings;
import marusenkoSphereKugel.Kugel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * RenderKugel - Datei
 * 
 * Enthält die Funktion, welche die Kugel effektiv Rendert
 *
 */
public class RenderKugel {
	
	/**
	 * Funktion, welche die Kugel Rendert. 
	 * Version 3: Mit Animation
	 * 
	 * @param k : Kugel
	 * @param cm : CameraManager für die Kamera
	 * @return : true wenn Erfolgreich
	 */
	protected static boolean render(Kugel k, CameraController cm, int pfeilID) {
		
    	/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefält werden
    	 * 
    	 * Hauptsächlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
		
    	//Löscht den gesammten Bereich, damit neu gerendert werden kann
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Setze die Position zum Rendern zurück 
        GL11.glLoadIdentity();

        //Kamera verschiebungen Machen
        cm.lookThrough();
        
        //Starte das Zeichnen der einzelnen Flächen der Kugel als Dreiecke
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        //Variable, um welchen Pol gedreht wird
        int drehePol = k.animationManager.getDrehPol();
        
        //Variable, ob die Halbe Kugel oder nur der Pol gedreht wird 
        int dreheMode = k.animationManager.getDrehModus();
        
		//Invertierungs Variabel auf x, y und z Achse --> für Negativ auf -1 ändern
		int ix = 1;
		int iy = 1;
		int iz = 1;
		
		//Positionierungs Variabeln --> Wenn Achsen Vertauscht werden
		int px = 0;
		int py = 1;
		int pz = 2;
		
		//Variablen legen fest auf welcher Achse was gedreht wird 
		// x = Positive Seite der X-Achse,  y = Positive Seite der Y-Achse,  z = Positive Seite der Z-Achse
		//mx = Negative Seite der X-Achse, my = Negative Seite der Y-Achse, mz = Negative Seite der Z-Achse
		// 1 = Nur der Pol wird gedreht, 3 = die halbe Kugel wird gedreht 
		boolean x1 = false;  boolean x3 = false;
		boolean mx1 = false; boolean mx3 = false;
		boolean y1 = false;  boolean y3 = false;
		boolean my1 = false; boolean my3 = false;
		boolean z1 = false;  boolean z3 = false;
		boolean mz1 = false; boolean mz3 = false;
		
		//Variablen welche Sagen in Welchem Winkel eine Seite gedreht ist
		double drehx = 0.0; double drehy = 0.0; double drehz = 0.0;
		
		//Zusätzliche Negativierungs Variablen, damit alle in die richtige Richtung drehen
		int neg = 1; int addNeg = 1;
		
		//Variabel definiert die Rotationsgeschwindikeit
		double rotation = k.animationManager.getRotationForFrame();
		
		//Wenn Sotiert wird
		if(rotation>0){
			
			//Wenn drehmodus == 1
			if(dreheMode==1){
				
				//Switche nach DrehPol
				switch(drehePol){
					case 0:  z1 = true; neg =  1; break;
					case 1:  x1 = true; neg =  1; break;
					case 2: mz1 = true; neg = -1; break;
					case 3: mx1 = true; neg = -1; break;
					case 4:  y1 = true; neg =  1; break;
					case 5: my1 = true; neg = -1; break;
				}
				
			}else
				
			//Wenn drehmodus == 3	
			if(dreheMode==3){
				
				//Switche nach DrehPol
				switch(drehePol){
					case 0:  z1 = true;  z3 = true; neg =  1; break;
					case 1:  x1 = true;  x3 = true; neg =  1; break;
					case 2: mz1 = true; mz3 = true; neg = -1; break;
					case 3: mx1 = true; mx3 = true; neg = -1; break;
					case 4:  y1 = true;  y3 = true; neg =  1; break;
					case 5: my1 = true; my3 = true; neg = -1; break;
				}
				
			}
			
		}
		
        //Wie genau die Kugel gerendert wird --> In wie gross/klein die Dreiecke sind
        int renderSteps = Settings.KUGELRENDERRASTER;//Nur 1, 2, 5, 10
        
        //Berechnungen welche nur einmal ausgeführt werden müssen
        
        //Array für die Werte, welche nur einmal berechnet werden
        double[][][][] preCalc = new double[90][90][6][3];
        
        //Gehe von 0 bis 90
        for(int j = 0; j<90; j+=renderSteps){
        	
        	//Gehe von 0 bis 90
			for(int l = 0; l<90; l+=renderSteps){
				
				//1. Dreieck 1. Korrdinate
				preCalc[j][l][0][0] = Math.cos(Math.toRadians(l))*Math.sin(Math.toRadians(j));
				preCalc[j][l][0][1] = Math.cos(Math.toRadians(l))*Math.cos(Math.toRadians(j));
				preCalc[j][l][0][2] = Math.sin(Math.toRadians(l));
				
				//1. Dreieck 2. Korrdinate
				preCalc[j][l][1][0] = Math.cos(Math.toRadians(l))*Math.sin(Math.toRadians(j+renderSteps));
				preCalc[j][l][1][1] = Math.cos(Math.toRadians(l))*Math.cos(Math.toRadians(j+renderSteps));
				preCalc[j][l][1][2] = Math.sin(Math.toRadians(l));
				
				//1. Dreieck 3. Korrdinate
				preCalc[j][l][2][0] = Math.cos(Math.toRadians(l+renderSteps))*Math.sin(Math.toRadians(j+renderSteps));
				preCalc[j][l][2][1] = Math.cos(Math.toRadians(l+renderSteps))*Math.cos(Math.toRadians(j+renderSteps));
				preCalc[j][l][2][2] = Math.sin(Math.toRadians(l+renderSteps));
						
				//2. Dreieck 1. Korrdinate
				preCalc[j][l][3][0] = preCalc[j][l][0][0];
				preCalc[j][l][3][1] = preCalc[j][l][0][1];
				preCalc[j][l][3][2] = preCalc[j][l][0][2];
				
				//2. Dreieck 2. Korrdinate
				preCalc[j][l][4][0] = Math.cos(Math.toRadians(l+renderSteps))*Math.sin(Math.toRadians(j));
				preCalc[j][l][4][1] = Math.cos(Math.toRadians(l+renderSteps))*Math.cos(Math.toRadians(j));
				preCalc[j][l][4][2] = Math.sin(Math.toRadians(l+renderSteps));
				
				//2. Dreieck 3. Korrdinate 
				preCalc[j][l][5][0] = preCalc[j][l][2][0];
				preCalc[j][l][5][1] = preCalc[j][l][2][1];
				preCalc[j][l][5][2] = preCalc[j][l][2][2];
				
			}
			
        }

		//Schleife um alle 24 Pol Viertel aus dem Berechneten auszu geben
		for(int i = 0; i<24; i++){
			
			//Setzte die Drehung zurück auf 0
			drehx = 0.0;
			drehy = 0.0;
			drehz = 0.0;
			
			//Setze die Farbe
			Rendern.setColor(k.tri[i]);
			
			//Definiere die Invertierungs Variablen und die Positionierungs Variablen für jeden Viertel neu. 
			//Zusätzlich noch die Farbe des Viertels Definieren
			switch(i){
			case 0:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 1:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 2:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 3:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 4:
				ix = 1; iy = 1; iz = 1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 5:
				ix = 1; iy = -1; iz =1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 6:
				ix = 1; iy =-1; iz =-1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 7:
				ix = 1; iy =1; iz = -1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 8:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 9:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 10:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 11:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 12:
				ix =-1; iy = 1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 13:
				ix =-1; iy =-1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 14:
				ix =-1; iy =-1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 15:
				ix =-1; iy = 1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 16:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(z3){  drehz=rotation; }else
				if(x3){  drehx=rotation; }
				break;
			case 17:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 18:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 19:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			//
			case 20:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 21:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 22:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 23:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			}
			
			//Füge alle Negationsvariablen zusammen
			drehx*=k.animationManager.getDrehRichtung();
			drehy*=k.animationManager.getDrehRichtung();
			drehz*=k.animationManager.getDrehRichtung();
			drehx*=neg;
			drehy*=neg;
			drehz*=neg;
			drehx*=addNeg;
			drehy*=addNeg;
			drehz*=addNeg;
						
			//Die x-Mal gleich Verwendeten Sinus/Cosinus in Variable speichern --> Ist effizienter als jedesmal Trigonometrie.sin() abzurufen
			double[][] sinCos = new double[3][2];
			sinCos[0][0] = Math.sin(Math.toRadians(drehx));
			sinCos[0][1] = Math.cos(Math.toRadians(drehx));
			sinCos[1][0] = Math.sin(Math.toRadians(drehy));
			sinCos[1][1] = Math.cos(Math.toRadians(drehy));
			sinCos[2][0] = Math.sin(Math.toRadians(drehz));
			sinCos[2][1] = Math.cos(Math.toRadians(drehz));
			
			//Dann den Viertel Rendern 
			for(int j = 0; j<90; j+=renderSteps){
				
    			for(int l = 50; l<90; l+=renderSteps){
    				
    				//1. Dreieck 1. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][0][px]*sinCos[2][1])+(iy*preCalc[j][l][0][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][0][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][0][py]*sinCos[2][1])-(ix*preCalc[j][l][0][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][0][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][0][pz]*sinCos[1][1])+(ix*preCalc[j][l][0][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][0][py]*sinCos[0][0]))
    								);
    				
    				//1. Dreieck 2. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][1][px]*sinCos[2][1])+(iy*preCalc[j][l][1][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][1][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][1][py]*sinCos[2][1])-(ix*preCalc[j][l][1][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][1][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][1][pz]*sinCos[1][1])+(ix*preCalc[j][l][1][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][1][py]*sinCos[0][0]))
    								);
    				
    				//1. Dreieck 3. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][2][px]*sinCos[2][1])+(iy*preCalc[j][l][2][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][2][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][2][py]*sinCos[2][1])-(ix*preCalc[j][l][2][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][2][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][2][pz]*sinCos[1][1])+(ix*preCalc[j][l][2][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][2][py]*sinCos[0][0]))
    								);
    				
    				//2. Dreieck 1. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][3][px]*sinCos[2][1])+(iy*preCalc[j][l][3][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][3][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][3][py]*sinCos[2][1])-(ix*preCalc[j][l][3][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][3][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][3][pz]*sinCos[1][1])+(ix*preCalc[j][l][3][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][3][py]*sinCos[0][0]))
    								);
    				
    				//2. Dreieck 2. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][4][px]*sinCos[2][1])+(iy*preCalc[j][l][4][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][4][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][4][py]*sinCos[2][1])-(ix*preCalc[j][l][4][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][4][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][4][pz]*sinCos[1][1])+(ix*preCalc[j][l][4][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][4][py]*sinCos[0][0]))
    								);
    				
    				//2. Dreieck 3. Koordinate
    				GL11.glVertex3d(
    						((((ix*preCalc[j][l][5][px]*sinCos[2][1])+(iy*preCalc[j][l][5][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalc[j][l][5][pz]*sinCos[1][0])), 
    						((((iy*preCalc[j][l][5][py]*sinCos[2][1])-(ix*preCalc[j][l][5][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalc[j][l][5][pz]*sinCos[0][0])), 
    						((((iz*preCalc[j][l][5][pz]*sinCos[1][1])+(ix*preCalc[j][l][5][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalc[j][l][5][py]*sinCos[0][0]))
    								);
    				
    			} 			
    			
       		}
			
		}
		
		
		//Rendern der Zwischenteile
		for(int i = 0; i<8; i++){
			
			//Setzet Drehung zurück auf 0
			drehx = 0;
			drehy = 0;
			drehz = 0;
			
			//Negations Variable
			int dn = 1;
			
			//Farbe setzten
			Rendern.setColor(k.con[i]);
			
			//Definiere die Variabeln und die Farbe für jedes Zwischenstück individuell
			switch(i){
			case 0:
				ix = -1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2; dn = 1;
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 1:
				ix = -1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;	 dn = -1;
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 2:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2; dn = 1;
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 3:
				ix = 1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2; dn = -1;
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 4:
				ix = -1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2; dn = -1;
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 5:
				ix = -1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2; dn = 1;
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 6:
				ix = 1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2; dn = -1;
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 7:
				ix = 1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2;	dn = 1;
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }else
				if(mz3){ drehz=rotation; }
				break;	
			}
			
			//Negativiere je nach Drehrichtung
			drehx*=k.animationManager.getDrehRichtung();
			drehy*=k.animationManager.getDrehRichtung();
			drehz*=k.animationManager.getDrehRichtung();
			
			//Zusätzliche Negationsvariable
			drehx*=dn;
			drehy*=dn;
			drehz*=dn;
			
			//Schreibe die oft benötigten Trigonometrischen Funktionen in Array --> Effizienter als jedesmal Trigonometrie.sin() abzurufen			
			double[][] sinCos = new double[3][2];
			sinCos[0][0] = Math.sin(Math.toRadians(drehx));
			sinCos[0][1] = Math.cos(Math.toRadians(drehx));
			sinCos[1][0] = Math.sin(Math.toRadians(drehy));
			sinCos[1][1] = Math.cos(Math.toRadians(drehy));
			sinCos[2][0] = Math.sin(Math.toRadians(drehz));
			sinCos[2][1] = Math.cos(Math.toRadians(drehz));
			
			//Rendere Rand des Zwischenstücks
			for(int j = 0; j<90; j+=renderSteps){
				
				for(int l = 40 ; l<50; l+=renderSteps){
					
					//Sofern es nicht zu einem Pol gehört
					if((Math.cos(Math.toRadians(j))*Math.sin(Math.toRadians(l)))<=Math.sin(Math.toRadians(50))&&(Math.cos(Math.toRadians(j-renderSteps))*Math.cos(Math.toRadians(l+renderSteps)))<=Math.sin(Math.toRadians(50))){
						
						//Jeden der Drei Ränder selbst  
						for(int m = 0; m<3; m++){
							
							//Achsenwelchsel Variablen
							int x = 0, y = 1,  z = 2;
							
							//Neu definieren
							switch(m){
								case 1: x = 1; y = 2; z = 0; break;
								case 2: x = 2; y = 0; z = 1; break;
							}
							
							//Die Je nach Drehung transformieren
							double zwi[][] = new double[6][3];
							
							zwi[0][0] = ((((preCalc[j][l][0][x]*sinCos[2][1])+(preCalc[j][l][0][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][0][z]*sinCos[1][0]));
							zwi[0][1] = ((((preCalc[j][l][0][y]*sinCos[2][1])-(preCalc[j][l][0][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][0][z]*sinCos[0][0]));
							zwi[0][2] = ((((preCalc[j][l][0][z]*sinCos[1][1])+(preCalc[j][l][0][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][0][y]*sinCos[0][0]));

							zwi[1][0] = ((((preCalc[j][l][1][x]*sinCos[2][1])+(preCalc[j][l][1][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][1][z]*sinCos[1][0]));
							zwi[1][1] = ((((preCalc[j][l][1][y]*sinCos[2][1])-(preCalc[j][l][1][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][1][z]*sinCos[0][0]));
							zwi[1][2] = ((((preCalc[j][l][1][z]*sinCos[1][1])+(preCalc[j][l][1][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][1][y]*sinCos[0][0]));

							zwi[2][0] = ((((preCalc[j][l][2][x]*sinCos[2][1])+(preCalc[j][l][2][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][2][z]*sinCos[1][0]));
							zwi[2][1] = ((((preCalc[j][l][2][y]*sinCos[2][1])-(preCalc[j][l][2][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][2][z]*sinCos[0][0]));
							zwi[2][2] = ((((preCalc[j][l][2][z]*sinCos[1][1])+(preCalc[j][l][2][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][2][y]*sinCos[0][0]));

							zwi[3][0] = ((((preCalc[j][l][3][x]*sinCos[2][1])+(preCalc[j][l][3][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][3][z]*sinCos[1][0]));
							zwi[3][1] = ((((preCalc[j][l][3][y]*sinCos[2][1])-(preCalc[j][l][3][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][3][z]*sinCos[0][0]));
							zwi[3][2] = ((((preCalc[j][l][3][z]*sinCos[1][1])+(preCalc[j][l][3][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][3][y]*sinCos[0][0]));

							zwi[4][0] = ((((preCalc[j][l][4][x]*sinCos[2][1])+(preCalc[j][l][4][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][4][z]*sinCos[1][0]));
							zwi[4][1] = ((((preCalc[j][l][4][y]*sinCos[2][1])-(preCalc[j][l][4][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][4][z]*sinCos[0][0]));
							zwi[4][2] = ((((preCalc[j][l][4][z]*sinCos[1][1])+(preCalc[j][l][4][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][4][y]*sinCos[0][0]));

							zwi[5][0] = ((((preCalc[j][l][5][x]*sinCos[2][1])+(preCalc[j][l][5][y]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][5][z]*sinCos[1][0]));
							zwi[5][1] = ((((preCalc[j][l][5][y]*sinCos[2][1])-(preCalc[j][l][5][x]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][5][z]*sinCos[0][0]));
							zwi[5][2] = ((((preCalc[j][l][5][z]*sinCos[1][1])+(preCalc[j][l][5][x]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][5][y]*sinCos[0][0]));

							
							//Je nach stück ausgeben
							GL11.glVertex3d(ix*zwi[0][px],iy*zwi[0][py],iz*zwi[0][pz]);
							GL11.glVertex3d(ix*zwi[1][px],iy*zwi[1][py],iz*zwi[1][pz]);
							GL11.glVertex3d(ix*zwi[2][px],iy*zwi[2][py],iz*zwi[2][pz]);
						
							GL11.glVertex3d(ix*zwi[3][px],iy*zwi[3][py],iz*zwi[3][pz]);
							GL11.glVertex3d(ix*zwi[4][px],iy*zwi[4][py],iz*zwi[4][pz]);
							GL11.glVertex3d(ix*zwi[5][px],iy*zwi[5][py],iz*zwi[5][pz]);
							
						}
						
					}
					
				}
				
	   		}
			
			
			//Lücke in den Verbinsungsstücken schliessen	
			for(int j = 30; j<50; j+=renderSteps){
				
				for(int l = 20; l<40;l+=renderSteps){
					
					//Falls nicht zu pol gehärt
					if((Math.cos(Math.toRadians(j))*Math.sin(Math.toRadians(l)))<=Math.sin(Math.toRadians(50))&&(Math.cos(Math.toRadians(j-renderSteps))*Math.cos(Math.toRadians(l+renderSteps)))<=Math.sin(Math.toRadians(50))){	
	    				
						//Werte berechnen
						double zwi[][] = new double[12][3];
	    				
	    				for(int m = 0; m<6; m++){
	    					zwi[m][0] = ((((preCalc[j][l][m][px]*sinCos[2][1])+(preCalc[j][l][m][py]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][m][pz]*sinCos[1][0]));
	    					zwi[m][1] = ((((preCalc[j][l][m][py]*sinCos[2][1])-(preCalc[j][l][m][px]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][m][pz]*sinCos[0][0]));
	    					zwi[m][2] = ((((preCalc[j][l][m][pz]*sinCos[1][1])+(preCalc[j][l][m][px]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][m][py]*sinCos[0][0]));
	    					zwi[m+6][0] = ((((preCalc[j][l][m][py]*sinCos[2][1])+(preCalc[j][l][m][pz]*sinCos[2][0]))*sinCos[1][1])-(preCalc[j][l][m][px]*sinCos[1][0]));
	    					zwi[m+6][1] = ((((preCalc[j][l][m][pz]*sinCos[2][1])-(preCalc[j][l][m][py]*sinCos[2][0]))*sinCos[0][1])+(preCalc[j][l][m][px]*sinCos[0][0]));
	    					zwi[m+6][2] = ((((preCalc[j][l][m][px]*sinCos[1][1])+(preCalc[j][l][m][py]*sinCos[1][0]))*sinCos[0][1])-(preCalc[j][l][m][pz]*sinCos[0][0]));
	    				}
	    				
	    				//Zeichnen
						GL11.glVertex3d(ix*zwi[0][0], iy*zwi[0][1], iz*zwi[0][2] );
						GL11.glVertex3d(ix*zwi[1][0], iy*zwi[1][1], iz*zwi[1][2] );
						GL11.glVertex3d(ix*zwi[2][0], iy*zwi[2][1], iz*zwi[2][2] );
						GL11.glVertex3d(ix*zwi[3][0], iy*zwi[3][1], iz*zwi[3][2] );
						GL11.glVertex3d(ix*zwi[4][0], iy*zwi[4][1], iz*zwi[4][2] );
						GL11.glVertex3d(ix*zwi[5][0], iy*zwi[5][1], iz*zwi[5][2] );
						GL11.glVertex3d(ix*zwi[6][0], iy*zwi[6][1], iz*zwi[6][2] );
						GL11.glVertex3d(ix*zwi[7][0], iy*zwi[7][1], iz*zwi[7][2] );
						GL11.glVertex3d(ix*zwi[8][0], iy*zwi[8][1], iz*zwi[8][2] );
						GL11.glVertex3d(ix*zwi[9][0], iy*zwi[9][1], iz*zwi[9][2] );
						GL11.glVertex3d(ix*zwi[10][0],iy*zwi[10][1],iz*zwi[10][2]);
						GL11.glVertex3d(ix*zwi[11][0],iy*zwi[11][1],iz*zwi[11][2]);
							
					}
					
				}
				
			}
			
		}
		
		//Beende das Rendern der Farben
        GL11.glEnd();
        GL11.glLineWidth(1.5f); 
        GL11.glBegin(GL11.GL_LINES);
        
        
        double[][][]preCalcLines = new double[3][90][6];
        
        for(int j =0; j<90; j++){
        	
        	preCalcLines[0][j][0] = Math.cos(Math.toRadians(50))*Math.sin(Math.toRadians(j));
        	preCalcLines[0][j][1] = Math.cos(Math.toRadians(50))*Math.cos(Math.toRadians(j));
        	preCalcLines[0][j][2] = Math.sin(Math.toRadians(50));
        	
        	preCalcLines[0][j][3] = Math.cos(Math.toRadians(50))*Math.sin(Math.toRadians(j+1));
        	preCalcLines[0][j][4] = Math.cos(Math.toRadians(50))*Math.cos(Math.toRadians(j+1));
        	preCalcLines[0][j][5] = Math.sin(Math.toRadians(50));
        	
		}
        
		for(int j = 0; j<40; j++){
			
			preCalcLines[1][j][0] = 0;
        	preCalcLines[1][j][1] = Math.sin(Math.toRadians(j));
        	preCalcLines[1][j][2] = Math.cos(Math.toRadians(j));
        	
        	preCalcLines[1][j][3] = 0;
        	preCalcLines[1][j][4] = Math.sin(Math.toRadians(j+1));
        	preCalcLines[1][j][5] = Math.cos(Math.toRadians(j));	
        	
        	preCalcLines[1][j+40][0] = Math.sin(Math.toRadians(j));
        	preCalcLines[1][j+40][1] = 0;
        	preCalcLines[1][j+40][2] = Math.cos(Math.toRadians(j));
        	
        	preCalcLines[1][j+40][3] = Math.sin(Math.toRadians(j+1));
        	preCalcLines[1][j+40][4] = 0;
        	preCalcLines[1][j+40][5] = Math.cos(Math.toRadians(j));	
        	
        	preCalcLines[2][0][0] = 0;
        	preCalcLines[2][0][1] = Math.sin(Math.toRadians(j));
        	preCalcLines[2][0][2] = Math.cos(Math.toRadians(j));
        	
        	preCalcLines[2][1][3] = 0;
        	preCalcLines[2][1][5] = Math.sin(Math.toRadians(j));
        	preCalcLines[2][1][4] = Math.cos(Math.toRadians(j));
        	
		}

        
        //Farbe auf Schwarz
        Rendern.setColor(8);
        
    	for(int i = 0; i<24; i++){
    		
    		drehx = 0;
    		drehy = 0;
    		drehz = 0;
    		
    		switch(i){
			case 0:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 1:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 2:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 3:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){  drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 4:
				ix = 1; iy = 1; iz = 1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 5:
				ix = 1; iy = -1; iz =1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 6:
				ix = 1; iy =-1; iz =-1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 7:
				ix = 1; iy =1; iz = -1;
				px = 2; py = 0; pz = 1;
				if(x1){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 8:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 9:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(x3){  drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 10:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 11:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){ drehz=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 12:
				ix =-1; iy = 1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			case 13:
				ix =-1; iy =-1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(z3){  drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 14:
				ix =-1; iy =-1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(my3){ drehy=rotation; }
				break;
			case 15:
				ix =-1; iy = 1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }else
				if(y3){  drehy=rotation; }
				break;
			//
			case 16:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(z3){  drehz=rotation; }else
				if(x3){  drehx=rotation; }
				break;
			case 17:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 18:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 19:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){  drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			//
			case 20:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			case 21:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(x3){  drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 22:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(mz3){ drehz=rotation; }
				break;
			case 23:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){ drehy=rotation; }else
				if(mx3){ drehx=rotation; }else
				if(z3){  drehz=rotation; }
				break;
			}
    		
    		//Füge alle Negationsvariablen zusammen
			drehx*=k.animationManager.getDrehRichtung();
			drehy*=k.animationManager.getDrehRichtung();
			drehz*=k.animationManager.getDrehRichtung();
			
			drehx*=neg;
			drehy*=neg;
			drehz*=neg;
			
			drehx*=addNeg;
			drehy*=addNeg;
			drehz*=addNeg;
    		
    		//Die x-Mal gleich Verwendeten Sinus/Cosinus in Variable speichern --> Ist effizienter als jedesmal Trigonometrie.sin() abzurufen
			double[][] sinCos = new double[3][2];
			sinCos[0][0] = Math.sin(Math.toRadians(drehx));
			sinCos[0][1] = Math.cos(Math.toRadians(drehx));
			sinCos[1][0] = Math.sin(Math.toRadians(drehy));
			sinCos[1][1] = Math.cos(Math.toRadians(drehy));
			sinCos[2][0] = Math.sin(Math.toRadians(drehz));
			sinCos[2][1] = Math.cos(Math.toRadians(drehz));
			
    		for(int j = 0; j<90; j++){
    			
    			//Strich 1. Koordinate
				GL11.glVertex3d(
						((((ix*preCalcLines[0][j][px]*sinCos[2][1])+(iy*preCalcLines[0][j][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalcLines[0][j][pz]*sinCos[1][0])), 
						((((iy*preCalcLines[0][j][py]*sinCos[2][1])-(ix*preCalcLines[0][j][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalcLines[0][j][pz]*sinCos[0][0])), 
						((((iz*preCalcLines[0][j][pz]*sinCos[1][1])+(ix*preCalcLines[0][j][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalcLines[0][j][py]*sinCos[0][0]))
								);
				//Strich 2. Koordinate
				GL11.glVertex3d(
						((((ix*preCalcLines[0][j][px+3]*sinCos[2][1])+(iy*preCalcLines[0][j][py+3]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalcLines[0][j][pz+3]*sinCos[1][0])), 
						((((iy*preCalcLines[0][j][py+3]*sinCos[2][1])-(ix*preCalcLines[0][j][px+3]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalcLines[0][j][pz+3]*sinCos[0][0])), 
						((((iz*preCalcLines[0][j][pz+3]*sinCos[1][1])+(ix*preCalcLines[0][j][px+3]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalcLines[0][j][py+3]*sinCos[0][0]))
								);
				
    		}
    		
    		for(int j = 0; j<80; j++){
    			//Strich 1. Koordinate
				GL11.glVertex3d(
						((((ix*preCalcLines[1][j][px]*sinCos[2][1])+(iy*preCalcLines[1][j][py]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalcLines[1][j][pz]*sinCos[1][0])), 
						((((iy*preCalcLines[1][j][py]*sinCos[2][1])-(ix*preCalcLines[1][j][px]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalcLines[1][j][pz]*sinCos[0][0])), 
						((((iz*preCalcLines[1][j][pz]*sinCos[1][1])+(ix*preCalcLines[1][j][px]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalcLines[1][j][py]*sinCos[0][0]))
								);
				
				//Strich 2. Koordinate
				GL11.glVertex3d(
						((((ix*preCalcLines[1][j][px+3]*sinCos[2][1])+(iy*preCalcLines[1][j][py+3]*sinCos[2][0]))*sinCos[1][1])-(iz*preCalcLines[1][j][pz+3]*sinCos[1][0])), 
						((((iy*preCalcLines[1][j][py+3]*sinCos[2][1])-(ix*preCalcLines[1][j][px+3]*sinCos[2][0]))*sinCos[0][1])+(iz*preCalcLines[1][j][pz+3]*sinCos[0][0])), 
						((((iz*preCalcLines[1][j][pz+3]*sinCos[1][1])+(ix*preCalcLines[1][j][px+3]*sinCos[1][0]))*sinCos[0][1])-(iy*preCalcLines[1][j][py+3]*sinCos[0][0]))
								);
				
    		}
    		
    	}       
    	
        GL11.glEnd();
        
        float[][] valuesPol = new float[3][3];
        float[][] valuesCon = new float[2][3];
        
        valuesPol[0][0] = 0.15f;valuesPol[0][1] = 0.5f;valuesPol[0][2] = 0.86f;
        valuesPol[1][0] = 0.28f;valuesPol[1][1] = 0.5f;valuesPol[1][2] = 0.85f;
        valuesPol[2][0] = 0.37f;valuesPol[2][1] = 0.42f;valuesPol[2][2] = 0.85f;
        
        valuesCon[0][0] = 0.45f;valuesCon[0][1] = 0.1f;valuesCon[0][2] = 0.9f;	
        valuesCon[1][0] = 0.57f;valuesCon[1][1] = 0.1f;valuesCon[1][2] = 0.83f;	
        
        
        if(pfeilID<30&&pfeilID>=0){
        	int invX = 1;
        	int invY = 1;
        	int invZ = 1;
        	int X1 = 0;
        	int Y1 = 1;
        	int Z1 = 2;
        	int X2 = 0;
        	int Y2 = 1;
        	int Z2 = 2;
        	switch(pfeilID){
        	case 0:
        		invX = 1; invY = 1; invZ = 1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 1:
        		invX = 1; invY = -1; invZ = 1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 2:
        		invX = -1; invY = -1; invZ = 1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 3:
        		invX = -1; invY = 1; invZ = 1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 4:
        		invX = 1; invY = 1; invZ = 1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 5:
        		invX = 1; invY = -1; invZ = 1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 6:
        		invX = 1; invY = -1; invZ = -1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;	
        	case 7:
        		invX = 1; invY = 1; invZ = -1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 8:
        		invX = 1; invY = 1; invZ = -1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;	
        	case 9:
        		invX = 1; invY = -1; invZ = -1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 10:
        		invX = -1; invY = -1; invZ = -1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 11:
        		invX = -1; invY = 1; invZ = -1;
        		X1 = 0; Y1 = 1; Z1 = 2;
        		X2 = 1; Y2 = 0; Z2 = 2;
        		break;
        	case 12:
        		invX = -1; invY = 1; invZ = 1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 13:
        		invX = -1; invY = -1; invZ = 1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 14:
        		invX = -1; invY = -1; invZ = -1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;	
        	case 15:
        		invX = -1; invY = 1; invZ = -1;
        		X1 = 2; Y1 = 0; Z1 = 1;
        		X2 = 2; Y2 = 1; Z2 = 0;
        		break;
        	case 16:
        		invX = 1; invY = 1; invZ = 1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 17:
        		invX = 1; invY = 1; invZ = -1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 18:
        		invX = -1; invY = 1; invZ = -1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 19:
        		invX = -1; invY = 1; invZ = 1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 20:
        		invX = 1; invY = -1; invZ = 1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 21:
        		invX = 1; invY = -1; invZ = -1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 22:
        		invX = -1; invY = -1; invZ = -1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	case 23:
        		invX = -1; invY = -1; invZ = 1;
        		X1 = 0; Y1 = 2; Z1 = 1;
        		X2 = 1; Y2 = 2; Z2 = 0;
        		break;	
        	}
        	 drawPfeil(new Vector3f(valuesPol[0][X1]*invX,valuesPol[0][Y1]*invY, valuesPol[0][Z1]*invZ),
             		new Vector3f(valuesPol[1][X1]*invX,valuesPol[1][Y1]*invY,valuesPol[1][Z1]*invZ), 
             		new Vector3f(valuesPol[2][X1]*invX,valuesPol[2][Y1]*invY,valuesPol[2][Z1]*invZ),0.081f, 5f);
             
             drawPfeil(new Vector3f(valuesPol[0][X2]*invX,valuesPol[0][Y2]*invY,valuesPol[0][Z2]*invZ),
             		new Vector3f(valuesPol[1][X2]*invX,valuesPol[1][Y2]*invY,valuesPol[1][Z2]*invZ), 
             		new Vector3f(valuesPol[2][X2]*invX,valuesPol[2][Y2]*invY,valuesPol[2][Z2]*invZ),0.081f, 5f);
        	
        }else
        if(pfeilID>=30){
        	int invX = 1;
        	int invY = 1;
        	int invZ = 1;
        	switch(pfeilID){
        	case 30:
        		invX = -1; invY = 1; invZ = -1;
            	break;
        	case 31:
        		invX = -1; invY = 1; invZ = 1;
            	break;
        	case 32:
        		invX = 1; invY = 1; invZ = 1;
            	break;	
        	case 33:
        		invX = 1; invY = 1; invZ = -1;
            	break;
        	case 34:
        		invX = -1; invY = -1; invZ = -1;
            	break;
        	case 35:
        		invX = -1; invY = -1; invZ = 1;
            	break;
        	case 36:
        		invX = 1; invY = -1; invZ = 1;
            	break;
        	case 37:
        		invX = 1; invY = -1; invZ = -1;
            	break;
        	}
        	
        	drawPfeil(new Vector3f(valuesCon[0][0]*invX,valuesCon[0][1]*invY,valuesCon[0][2]*invZ), 
            		new Vector3f(valuesCon[1][0]*invX,valuesCon[1][1]*invY,valuesCon[1][2]*invZ),0.081f, 5f);
            
            drawPfeil(new Vector3f(valuesCon[0][2]*invX,valuesCon[0][1]*invY,valuesCon[0][0]*invZ), 
            		new Vector3f(valuesCon[1][2]*invX,valuesCon[1][1]*invY,valuesCon[1][0]*invZ),0.081f, 5f);
            
            drawPfeil(new Vector3f(valuesCon[0][1]*invX,valuesCon[0][0]*invY,valuesCon[0][2]*invZ),
            		new Vector3f(valuesCon[1][1]*invX,valuesCon[1][0]*invY,valuesCon[1][2]*invZ),0.081f, 5f);
       
            drawPfeil(new Vector3f(valuesCon[0][1]*invX,valuesCon[0][2]*invY,valuesCon[0][0]*invZ),
            		new Vector3f(valuesCon[1][1]*invX,valuesCon[1][2]*invY,valuesCon[1][0]*invZ),0.081f, 5f);

            drawPfeil(new Vector3f(valuesCon[0][0]*invX,valuesCon[0][2]*invY,valuesCon[0][1]*invZ), 
            		new Vector3f(valuesCon[1][0]*invX,valuesCon[1][2]*invY,valuesCon[1][1]*invZ),0.081f, 5f);
            
            drawPfeil(new Vector3f(valuesCon[0][2]*invX,valuesCon[0][0]*invY,valuesCon[0][1]*invZ), 
            		new Vector3f(valuesCon[1][2]*invX,valuesCon[1][0]*invY,valuesCon[1][1]*invZ),0.081f, 5f);
        	
        	
        }
        
        
        
        //Test Pfeile Zeichnen
        
        
        //Pfeile für Pole drehen
   /*     drawPfeil(new Vector3f(0.15f, 0.5f, 0.86f),
        		new Vector3f(0.28f, 0.5f, 0.85f), 
        		new Vector3f(0.37f,0.42f,0.85f),0.081f, 5f);
        
        drawPfeil(new Vector3f(0.5f, 0.15f, 0.86f),
        		new Vector3f(0.5f, 0.28f, 0.85f), 
        		new Vector3f(0.42f,0.37f,0.85f),0.081f, 5f);
     
        //Pfeile für Kugel hälfte drehen
        
        drawPfeil(new Vector3f(0.45f, 0.1f, 0.9f), 
        		new Vector3f(0.57f,0.1f,0.83f),0.081f, 5f);
        
        drawPfeil(new Vector3f(0.9f, 0.1f, 0.45f), 
        		new Vector3f(0.83f,0.1f,0.57f),0.081f, 5f);
        
        drawPfeil(new Vector3f(0.1f, 0.45f, 0.9f),
        		new Vector3f(0.1f, 0.57f, 0.83f),0.081f, 5f);
   
        drawPfeil(new Vector3f(0.1f, 0.9f, 0.45f),
        		new Vector3f(0.1f, 0.83f, 0.57f),0.081f, 5f);

        drawPfeil(new Vector3f(0.45f, 0.9f, 0.1f), 
        		new Vector3f(0.57f, 0.83f, 0.1f),0.081f, 5f);
        
        drawPfeil(new Vector3f(0.9f,0.45f,  0.1f), 
        		new Vector3f( 0.83f,0.57f, 0.1f),0.081f, 5f);*/
        
        //Gib true zurück, wenn hier ankommt, dann alles Erfolgreich
        return true;
        
    }
	
	/**
	 * Für gerade Pfeile
	 */
	private static void drawPfeil(Vector3f pfeilSpitze, Vector3f pfeilEnde, float spitzengroesse, float strichdicke){
		
		//Erstelle neuen Vektor
		Vector3f pfeilMitte = new Vector3f();
		
		//Setze neuen Vektor als Addition der Vektoren für Anfang und Ende
		Vector3f.add(pfeilSpitze, pfeilEnde, pfeilMitte);
		
		//Dividiere durch 2 um die Selbe länge zu erhalten
		pfeilMitte.scale(0.5f);
		
		//Rufe Funktion für gebogene Pfeile auf
		drawPfeil(pfeilSpitze, pfeilMitte,pfeilEnde,spitzengroesse, strichdicke);
	}
	
	/**
	 * Für gebogene Pfeile
	 */
	private static void drawPfeil(Vector3f pfeilSpitze, Vector3f pfeilMitte, Vector3f pfeilEnde, float spitzengroesse, float strichdicke){
		
		//Starte das Zeichnen von Dreiecken
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		//Setzte die Farbe auf Schwarz
	    Rendern.setColor(8);
	    
	    //Erstelle einen Neuen Pfeil, welcher die Richtung der Spitze angibt
	    Vector3f direction = new Vector3f();
	    
	    //Subtrahiere vom Mittelpunkt die Spitze und erhalte den Vektor
	    Vector3f.sub(pfeilMitte, pfeilSpitze, direction);
	    
	    //Verändere den Vektor auf Länge 1
	    direction.normalise();
	    
	    //Verkleinere den Vektor auf die gegebene Spitzengrösse
	    direction.scale(spitzengroesse);
	        
	    //Pfeilspitze Zeichnen
	      
	    //Spitzen Punkt
	    GL11.glVertex3d(pfeilSpitze.x-direction.x,pfeilSpitze.y-direction.y,pfeilSpitze.z-direction.z);
	       
	    //Mache das Kreuzprodukt des Richtungsvektors mit dem Ortsvektor der Spitze um einen Sektrechten Vektor zu erhalten
	    Vector3f.cross(direction, pfeilSpitze, direction);
	    
	    //Vektor auf Länge 1 bringen
	    direction.normalise();
	    
	    //Vektor auf die Spitzengrösse skalieren
	    direction.scale(spitzengroesse);
	    
	    //Flügelpunkte der Pfeilspitze
	    GL11.glVertex3d(pfeilSpitze.x-direction.x,pfeilSpitze.y-direction.y,pfeilSpitze.z-direction.z);
	    GL11.glVertex3d(pfeilSpitze.x+direction.x,pfeilSpitze.y+direction.y,pfeilSpitze.z+direction.z);

	    //Beende das Zeichnen von Dreiecken
	    GL11.glEnd();
	    
	    //Setze die Liniendicke auf die gegebene Liniendicke
	    GL11.glLineWidth(strichdicke);
	    
	    //Starte das Zeichnen von Linien
	    GL11.glBegin(GL11.GL_LINE_STRIP);
	    
	    //Für jeden 100tel den Punkt berechnen gemäss den Bezierkurven
	    for(float t = 0.000f; t<1; t+=0.01){
	    	
	    	double xValue = ((1-t)*(1-t)*pfeilSpitze.x)+(2*t*(1-t)*pfeilMitte.x)+(t*t*pfeilEnde.x);	    	
	    	double yValue = ((1-t)*(1-t)*pfeilSpitze.y)+(2*t*(1-t)*pfeilMitte.y)+(t*t*pfeilEnde.y);	    
	    	double zValue = ((1-t)*(1-t)*pfeilSpitze.z)+(2*t*(1-t)*pfeilMitte.z)+(t*t*pfeilEnde.z);	    
	    	
	    	//Punkt zu Linie hinzufügen
	    	GL11.glVertex3d(xValue,yValue,zValue);
	    	
	    }
	    
	    //Zeichnen von Linien beenden
	    GL11.glEnd();
	    
	   /* 
	    GL11.glLineWidth(2f);
	    GL11.glBegin(GL11.GL_LINE_STRIP);
	    Rendern.setColor(4);
	    GL11.glVertex3d(pfeilSpitze.x,pfeilSpitze.y,pfeilSpitze.z);
	    GL11.glVertex3d(pfeilMitte.x,pfeilMitte.y,pfeilMitte.z);
	    GL11.glVertex3d(pfeilEnde.x,pfeilEnde.y,pfeilEnde.z);
	    GL11.glEnd();
	     /*  */
	}
	
}
