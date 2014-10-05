package marusenkoSphereGUI;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import marusenkoSphereKugel.Kugel;

/**
 * Editor-Klasse
 * 
 * Diese Klasse enthält alle Funktionen welche zum Rendern des Editors im Kugelfenster benötigt werden. 
 * Zusätzlich auch jene Funktionen, welche zum Erkennen sind, auf Was geklickt wurde
 *
 *
 */
public class Editor {

	/**
	 * Funktion welche den Editor Rendert
	 */
	protected static boolean renderEditor(Kugel k){
		
    	//Löscht den gesammten Bereich, damit neu gerendert werden kann
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Setze die Position zum Rendern zurück 
        GL11.glLoadIdentity();

        //Verschiebe die 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
        GL11.glTranslatef(0.0f,0.0f,-13.0f);
        
        //Starte das Zeichnen der einzelnen Flächen der Kugel
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        //Rendert die ersten Vier Pole, welche als Kreise dargestellt werden
        //Pol hinten wird 2x gezeichnet (links und rechts)
        for(int i = 0; i<20; i++){
        	
        	//Negations Variablen
        	int ix = 1;
        	int iy = 1;
        	
        	//Verschiebungs Variablen
        	double x = 0;
        	double y = 0;
        	
        	//Rendere die 4(5) Pole welche als Kreis dargestellt werden 
        	
        	//Setzet Farbe Standartmässig auf Dreieck mit Index i --> Spezialfall 16-19 wird nachher noch geändert
        	//Gibt keinen Error, da diese Indizen auch noch existieren
        	Rendern.setColor(k.tri[i]);
        	
        	//ändere spezifisch die Variablen
        	switch(i){
	        	case 0:  ix =  1; iy =  1; x =  0; break;
	        	case 1:  ix =  1; iy = -1; x =  0; break;
	        	case 2:  ix = -1; iy = -1; x =  0; break;
	        	case 3:  ix = -1; iy =  1; x =  0; break;
	        	case 4:  ix = -1; iy =  1; x =  3; break;
	        	case 5:  ix = -1; iy = -1; x =  3; break;
	        	case 6:  ix =  1; iy = -1; x =  3; break;
	        	case 7:  ix =  1; iy =  1; x =  3; break;	
	        	case 8:  ix = -1; iy =  1; x =  6; break;
	        	case 9:  ix = -1; iy = -1; x =  6; break;
	        	case 10: ix =  1; iy = -1; x =  6; break;
	        	case 11: ix =  1; iy =  1; x =  6; break;	
	        	case 12: ix =  1; iy =  1; x = -3; break;
	        	case 13: ix =  1; iy = -1; x = -3; break;
	        	case 14: ix = -1; iy = -1; x = -3; break;
	        	case 15: ix = -1; iy =  1; x = -3; break;
	        	case 16: ix = -1; iy =  1; x = -6; Rendern.setColor(k.tri[8]);  break;
	        	case 17: ix = -1; iy = -1; x = -6; Rendern.setColor(k.tri[9]);  break;
	        	case 18: ix =  1; iy = -1; x = -6; Rendern.setColor(k.tri[10]); break;
	        	case 19: ix =  1; iy =  1; x = -6; Rendern.setColor(k.tri[11]); break;		
        	}
        	
        	//Rendere einen Vierteils Kreis aus Dreiecken
        	 for(int j = 0; j<90; j++){
             	GL11.glVertex2d(x,y);
             	GL11.glVertex2d((ix*Math.sin(Math.toRadians(j))+x),(iy*Math.cos(Math.toRadians(j))+y));
             	GL11.glVertex2d((ix*1.0+x),y);
             }
        }
        
        
        //Die Pole 4 und 5 werden hier extra gerendert
        for(int i = 0; i<8; i++){

        	//die NegationsVariable
        	int iy = 1;
        	
        	//Zentrale Positions Koordinaten
        	double zx = 0;
        	double zy = 5;
        	
        	//Koordinaten links und Rechts
        	double x1 = 0;
        	double x2 = 3;
        	
        	//Farbe Setzten
        	Rendern.setColor(k.tri[i+16]);
        	
        	//Spezifische Variablen setzen
        	switch(i){
	        	case 0: x1 =  0; x2 =  3; iy =  1; break;
	        	case 1: x1 =  3; x2 =  6; iy =  1; break;
	        	case 2: x1 = -6; x2 = -3; iy =  1; break;
	        	case 3: x1 = -3; x2 =  0; iy =  1; break;
	        	case 4: x1 =  0; x2 =  3; iy = -1; break;
	        	case 5: x1 =  3; x2 =  6; iy = -1; break;
	        	case 6: x1 = -6; x2 = -3; iy = -1; break;
	        	case 7: x1 = -3; x2 =  0; iy = -1; break;
        	}
        	
        	//Zentrale Position Negativieren falls nötig
        	zy*=iy;
        	
        	//Brechne die Position der Kreismitte 
        	double kx = (((x2-x1)/2)+x1);
        	double ky = 3*iy;
        	
        	//Rendere beidseitig bis auf 60Grad und Verbinde mit Zentralem Punkt
        	for(int j = 0; j<=60; j++){
              	GL11.glVertex2d(zx,zy);
              	GL11.glVertex2d(kx+(1.4*Math.sin(Math.toRadians(j))),ky-(iy*1.4*Math.cos(Math.toRadians(j))));
              	GL11.glVertex2d(kx+(1.4*Math.sin(Math.toRadians(j+1))),ky-(iy*1.4*Math.cos(Math.toRadians(j+1))));
              	
              	GL11.glVertex2d(zx,zy);
              	GL11.glVertex2d(kx-(1.4*Math.sin(Math.toRadians(j))),ky-(iy*1.4*Math.cos(Math.toRadians(j))));
              	GL11.glVertex2d(kx-(1.4*Math.sin(Math.toRadians(j+1))),ky-(iy*1.4*Math.cos(Math.toRadians(j+1))));
              
              }
        }
        
        
        //Rendern der Verbindungsstücke
        for(int i = 0; i<8; i++){
        	//Zentraler Punkt
        	double zx = 0.5;
        	double zy = 1.2;
        	//
        	double x1 = -1;
        	double x2 = 2;
        	int iy = 1;
        	Rendern.setColor(k.con[i]);
        	
        	switch(i){
	        	case 0:
	            	x1 = -6; x2 = -3; iy = 1;
	        		break;
	        	case 1:
	            	x1 = -3; x2 = 0; iy = 1;
	        		break;
	        	case 2:
	            	x1 = 0; x2 = 3; iy = 1;
	        		break;
	        	case 3:
	            	x1 = 3; x2 = 6; iy = 1;
	        		break;
	        	case 4:
	            	x1 = -6; x2 = -3; iy = -1;
	        		break;
	        	case 5:
	            	x1 = -3; x2 = 0; iy = -1;
	        		break;
	        	case 6:
	            	x1 = 0; x2 = 3; iy = -1;
	        		break;
	        	case 7:
	            	x1 = 3; x2 = 6;iy = -1;
	        		break;	
        	}
        	zx = (((x2-x1)/2)+x1);
        	zy *= iy;
        	
        	

        	for(int j = 0; j<90; j++){
             	GL11.glVertex2d(zx,zy);
             	GL11.glVertex2d((1.2*Math.sin(Math.toRadians(j))+x1),(iy*1.1*Math.cos(Math.toRadians(j))+0.1*iy));
             	GL11.glVertex2d((1.2*Math.sin(Math.toRadians(j+1))+x1),(iy*1.1*Math.cos(Math.toRadians(j+1))+0.1*iy));
             	
             	GL11.glVertex2d(zx,zy);
             	GL11.glVertex2d((-1.2*Math.sin(Math.toRadians(j))+x2),(iy*1.1*Math.cos(Math.toRadians(j))+0.1*iy));
             	GL11.glVertex2d((-1.2*Math.sin(Math.toRadians(j+1))+x2),(iy*1.1*Math.cos(Math.toRadians(j+1))+0.1*iy));
             }
        	GL11.glVertex2d(zx,zy);
         	GL11.glVertex2d((1.2*Math.sin(Math.toRadians(90))+x1),(iy*1.1*Math.cos(Math.toRadians(90))+0.1*iy));
         	GL11.glVertex2d((-1.2*Math.sin(Math.toRadians(90))+x2),(iy*1.1*Math.cos(Math.toRadians(90))+0.1*iy));
        }
        //Beende das Rendern
        GL11.glEnd(); 
		
        return true;
	}
	
	/**
	 * Methode um aus den 2D-Pixel Koordinaten der Maus die Koordinaten in der 3D-Welt bekommen
	 * @param x : Maus Position in x Richtung
	 * @param y : Maus Position in y Richtung
	 * @return : double[3] mit x, y und z Koordinate in 3D-Welt
	 */
	protected static double[] MouseIn3D(int x, int y){
		
		//Erstelle einen FloatBuffer mit der Grösse 16 (für 4x4 Matrix)
		FloatBuffer model = BufferUtils.createFloatBuffer(16);
		
		//Erstelle einen FloatBuffer mit der Grösse 16 (für 4x4 Matrix)
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		
		//Erstelle einen IntBuffer mit der Grösse 16 (für 4x4 Matrix)
		IntBuffer viewport = BufferUtils.createIntBuffer(16);

		
		//Schreibe die Modelview Matrix in den FloatBuffer model
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);
		
		//Schreibe die Projection Matrix in den FloatBuffer projection
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		
		//Schribe den Viewport in den IntBuffer projection
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		
	
		//Erstelle einen FloatBuffer mit Grösse 1 für die z Komponente
		FloatBuffer z = BufferUtils.createFloatBuffer(1);
		
		//Bekomme die Z-Achse
		GL11.glReadPixels(x, y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		//Erstelle einen FloatBuffer mit der Grösse 3 für die dreidimensionalen Koordinaten
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);
		
		//Berechne aus den Mauskoordinaten x, y, dem errechneten z und den Matrizen die ursprünglichen Koordinaten und speichere in FloatBuffer pos
		GLU.gluUnProject(x, y, z.get(0), model, projection, viewport, pos);
		
		//Gib ein double Array zurück, mit den dreidimensionalen Koordinaten
		return new double[] {pos.get(0),pos.get(1),pos.get(2)};
	}
	
	/**
	 * Methode die aus den x/y-Koordinaten sagt, auf welches Feld geklickt wurden
	 * @param x : x-Achse
	 * @param y : y-Achse
	 * @return : 0-23 Dreieck - 24-31 Verbindungsstücke als (0-7)+24
	 */
	protected static int onWhichField(double x, double y){
		//Ist es Pol 0?
		if(isInCircle(0,0,1,x,y)){
			//Welche Position auf Pol 0?
			return posInCircle(0,0,x,y);
		}else
		//Ist es Pol 1?
		if(isInCircle(3,0,1,x,y)){
			//Welche Position auf Pol 1?
			return 7-posInCircle(3,0,x,y);
		}else
		//Ist es Pol 2 (rechts)?
		if(isInCircle(6,0,1,x,y)){
			//Welche Position auf Pol 2 (rechts)?
			return 11-posInCircle(6,0,x,y);
		}else
		//Ist es Pol 2 (links)?
		if(isInCircle(-6,0,1,x,y)){
			//Welche Position auf Pol 2 (links)?
			return 11-posInCircle(-6,0,x,y);
		}else
		//Ist es Pol 3?
		if(isInCircle(-3,0,1,x,y)){
			//Welche Position auf Pol 3?
			return 12+posInCircle(-3,0,x,y);
		}else
		//Ist es Position 17?
		if(isInTriangle(0, 5, 4, 1.2, 20, 1.2, x, y)){
			return 17;
		}else
		//Ist es Position 16?
		if(isInTriangle(0, 5, 0, 1.2, 4, 1.2, x, y)){
			return 16;
		}else
		//Ist es Position 19?
		if(isInTriangle(0, 5, -4, 1.2, 0, 1.2, x, y)){
			return 19;
		}else
		//Ist es Position 18?
		if(isInTriangle(0, 5, -20, 1.2, -4, 1.2, x, y)){
			return 18;
		}else
		//Ist es Position 21?
		if(isInTriangle(0, -5, 4, -1.2, 20, -1.2, x, y)){
			return 21;
		}else
		//Ist es Position 20?
		if(isInTriangle(0, -5, 0, -1.2, 4, -1.2, x, y)){
			return 20;
		}else
		//Ist es Position 23?
		if(isInTriangle(0, -5, -4, -1.2, 0, -1.2, x, y)){
			return 23;
		}else
		//Ist es Position 22?
		if(isInTriangle(0, -5, -20, -1.2, -4, -1.2, x, y)){
			return 22;
		}else
		//Ist es Verbindungsstück 0?
		if(isInRectangle(-6,2,-3,0,x,y)){
			//Con 0
			return 24;
		}else
		//Ist es Verbindungsstück 1?
		if(isInRectangle(-3,2,0,0,x,y)){
			return 25;
		}else	
		//Ist es Verbindungsstück 2?
		if(isInRectangle(0,2,3,0,x,y)){
			return 26;
		}else
		//Ist es Verbingungsstück 3?
		if(isInRectangle(3,2,6,0,x,y)){
			return 27;
		}else
		//Ist es Verbingungsstück 4?
		if(isInRectangle(-6,-2,-3,0,x,y)){
			return 28;
		}else
		//Ist es Verbindungsstück 5?
		if(isInRectangle(-3,-2,0,0,x,y)){
			return 29;
		}else	
		//Ist es Verbindungsstück 6?
		if(isInRectangle(0,-2,3,0,x,y)){
			return 30;
		}else
		//Ist es Verbingungsstück 7?
		if(isInRectangle(3,-2,6,0,x,y)){
			return 31;
		}else{
		//Wenn es kein Feld ist, dann -1 zurück geben
			return -1;
		}
	}
		
	/**
	 * Funktion die Prüft, Ob die Position(X/Y) innerhalb des Kreises an Position(X/Y) mit gegebenem Radius befindet
	 * @param CircleX : Position Kreismitte auf X-Achse
	 * @param CircleY : Position Kreismitte auf Y-Achse
	 * @param CircleRadius : Radius des Kreises
	 * @param PositionX : Position welche zu Prüfen ist auf X-Achse
	 * @param PositionY : Position welche zu Prüfen ist auf Y-Achse
	 * @return
	 */
	private static boolean isInCircle(double CircleX, double CircleY, double CircleRadius, double PositionX, double PositionY){
		//Die zu prüfende Position wird minus der Verschiebung der Kreismitte gerechnet
		PositionX-=CircleX;
		PositionY-=CircleY;
		
		//Anwendung des Pythagoras
		//PositionX^2 + PositionY^2 muss kleiner Gleich des Radius^2 sein
		return (((PositionX*PositionX)+(PositionY*PositionY))<=(CircleRadius*CircleRadius)) ? true:false;
	}
	
	/**
	 * Funktion gibt zurück, in welchem Viertel eines Kreises die zu Prüfende Position lieft
	 * @param CircleX : Position der Kreismitte auf X-Achse
	 * @param CircleY : Position der Kreismitte auf Y-Achse
	 * @param PositionX : Zu prüfende Position auf X-Achse
	 * @param PositionY : Zu prüfende Position auf Y-Achse
	 * @return : Int von 0-3 für jeden Viertel --> -1 wenn ein Fehler auftritt
	 */
	private static int posInCircle(double CircleX, double CircleY, double PositionX, double PositionY){
		//Die zu prüfende Position wird minus der Verschiebung der Kreismitte gerechnet
		PositionX-=CircleX;
		PositionY-=CircleY;
		
		//Wenn X > 0, dann Position 0 oder 1
		if(PositionX>0){
			//Wenn Y > 0, dann 0, sonst 1 
			return PositionY>0 ? 0:1;
        //Sonst, Position 2 oder 3
		}else{
			//Wenn Y > 0, dann 3, sonst 2
			return PositionY>0 ? 3:2;
		}
	}
	
	/**
	 * Funktion welche mit Hilfe der Baryzentrischen Koordinaten prüft, ob ein Punkt innerhalb eines Dreieckes liegt oder nicht
	 * 	Baryzentrische Koordinaten --> http://en.wikipedia.org/wiki/Barycentric_coordinate_system_%28mathematics%29
	 * @param t1x : Dreieck 1.Punkt X-Achse
	 * @param t1y : Dreieck 1.Punkt Y-Achse
	 * @param t2x : Dreieck 2.Punkt X-Achse
	 * @param t2y : Dreieck 2.Punkt Y-Achse
	 * @param t3x : Dreieck 3.Punkt X-Achse
	 * @param t3y : Dreieck 3.Punkt Y-Achse
	 * @param x : Zu prüfende Position auf X-Achse
	 * @param y : Zu prüfende Position auf Y-Achse
	 */
	private static boolean isInTriangle(double t1x, double t1y, double t2x, double t2y, double t3x, double t3y, double x, double y){
		
		//Berechne Lamda 1-3 gemäss den Baryzentrischen Koordinaten
		double lamda1 = (((t2y-t3y)*(x-t3x))+((t3x-t2x)*(y-t3y)))/(((t2y-t3y)*(t1x-t3x))+((t3x-t2x)*(t1y-t3y)));
		double lamda2 = (((t3y-t1y)*(x-t3x))+((t1x-t3x)*(y-t3y)))/(((t2y-t3y)*(t1x-t3x))+((t3x-t2x)*(t1y-t3y)));
		double lamda3 = 1-lamda1-lamda2;
		
		
		//Wenn alle Lamdas grösse als 0 und kleiner als 1 sind, dann ist der Punkt im Dreieck
		return (lamda1>0&&lamda1<1&&lamda2>0&&lamda2<1&&lamda3>0&&lamda3<1) ? true:false;
	}	

	/**
	 * Prüft mit hilfe der Dreiecksfunktion, ob ein Punki in einem Rechteck liegt.
	 * @param r1x : Punkt 1 x
	 * @param r1y : Punkt 1 y
	 * @param r2x : Punkt diagonal zu 1 x
	 * @param r2y : Punkt diagonal zu 1 y
	 * @param x   : Position x
	 * @param y   : Position y
	 */
	private static boolean isInRectangle(double r1x, double r1y, double r2x, double r2y, double x, double y){
		return (isInTriangle(r1x,r1y,r2x,r2y,r1x,r2y,x,y)||isInTriangle(r1x,r1y,r2x,r2y,r2x,r1y,x,y)) ? true:false;
	}
}
