package marusenkoSphereGUI;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import marusenkoSphereKugel.Kugel;

/**
 * Diese Klasse enthält alle Funktionen welche zum Rendern des Editors im Kugelfenster benötigt werden. 
 * Zusätzlich auch jene Funktionen, welche zum Erkennen sind, auf Was geklickt wurde
 *
 *
 */
public class Editor {

	/**
	 * Funktion welche den Editor Rendert
	 * @param k
	 * @param rx
	 * @param ry
	 * @param rz
	 * @return
	 */
	protected static boolean renderEditor(Kugel k,float rx, float ry, float rz){
		/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefüllt werden
    	 * 
    	 * Hauptsächlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
		
    	//Löscht den gesammten Bereich, damit neu gerendert werden kann
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Setze die Position zum Rendern zurück 
        GL11.glLoadIdentity();

        //Verschiebe die 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
        GL11.glTranslatef(0.0f,0.0f,-13.0f);
        
        //Zum Drehen --> Normalerweise auskommentiert, kann zum Debugging aktiviert werden
        //GL11.glRotatef(ry,0.0f,1.0f,0.0f);
        //GL11.glRotatef(rx,1.0f,0.0f,0.0f);
        //GL11.glRotatef(rz,0.0f,0.0f,1.0f);
        
        //Starte das Zeichnen der einzelnen Fl�chen der Kugel
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        
        //Rendert die ersten Vier Pole, welche als Kreise dargestellt werden
        //Pol hinten wird 2x gezeichnet
        for(int i = 0; i<20; i++){
        	
        	//Negations Variablen
        	int ix = 1;
        	int iy = 1;
        	
        	//Verschiebungs Variablen
        	double x = 0;
        	double y = 0;
        	
        	//Setzet Farbe Standartmässig auf Dreieck mit Index i --> Spezialfall 16-19 wird nachher noch geändert
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
        	
        	//Zentrale Position Negativieren falls n�tig
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
        
        
        //Rendern der Verbindungsst�cke
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
            	x1 = -6; x2 = -3;
            	iy = 1;
        		break;
        	case 1:
            	x1 = -3; x2 = 0;
            	iy = 1;
        		break;
        	case 2:
            	x1 = 0; x2 = 3;
            	iy = 1;
        		break;
        	case 3:
            	x1 = 3; x2 = 6;
            	iy = 1;
        		break;
        	case 4:
            	x1 = -6; x2 = -3;
            	iy = -1;
        		break;
        	case 5:
            	x1 = -3; x2 = 0;
            	iy = -1;
        		break;
        	case 6:
            	x1 = 0; x2 = 3;
            	iy = -1;
        		break;
        	case 7:
            	x1 = 3; x2 = 6;
            	iy = -1;
        		break;
        		
        	}
        	zx = (((x2-x1)/2)+x1);
        	//-7.5 -4.5 / -1.5 / 1.5 / 4.5 / 7.5
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
	 * Methode um aus den 2D-Pixel Koordinaten der Maus die Koordinaten in der 3-D Welt bekommen
	 * @param x : Maus Position in x Richtung
	 * @param y : Maus Position in y Richtung
	 * @return : double[3] mit x, y und z Koordinate in 3D-Welt
	 */
	protected static double[] isMouseIn3D(float x, float y){
		
		FloatBuffer model = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);

		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		
		FloatBuffer z = BufferUtils.createFloatBuffer(1);
		GL11.glReadPixels((int) x, (int) y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);
		
		GLU.gluUnProject(x, y, z.get(0), model, projection, viewport, pos);
		
		//System.out.println("X: "+pos.get(0)+" Y: "+pos.get(1)+" Z: "+pos.get(2));
		
		double[] r = new double[3];
		r[0] = pos.get(0);
		r[1] = pos.get(1);
		r[2] = pos.get(2);

		return r;
	}
	/**
	 * Methode die aus den x/y-Koordinaten sagt, auf welches Feld geklickt wurden
	 * @param x : x-Achse
	 * @param y : y-Achse
	 * @return : 0-23 Dreieck - 24-31 Verbindungsst�cke als (0-7)+24
	 */
	protected static int onWhichField(double x, double y){
		if(isInCircle(0,0,1,x,y)){
			//Pol 0
			return posInCircle(0,0,x,y);
		}else
		if(isInCircle(3,0,1,x,y)){
			//Pol 1
			return 7-posInCircle(3,0,x,y);
		}else
		if(isInCircle(6,0,1,x,y)){
			//Pol 2 rechts
			return 11-posInCircle(6,0,x,y);
		}else
		if(isInCircle(-6,0,1,x,y)){
			//Pol 2 links
			return 11-posInCircle(-6,0,x,y);
		}else
		if(isInCircle(-3,0,1,x,y)){
			//Pol 3
			return 12+posInCircle(-3,0,x,y);
		}else
		if(isInTriangle(0, 5, 4, 1.2, 20, 1.2, x, y)){
			//Pos 17
			return 17;
		}else
		if(isInTriangle(0, 5, 0, 1.2, 4, 1.2, x, y)){
			//Pos 16
			return 16;
		}else
		if(isInTriangle(0, 5, -4, 1.2, 0, 1.2, x, y)){
			//Pos 19
			return 19;
		}else
		if(isInTriangle(0, 5, -20, 1.2, -4, 1.2, x, y)){
			//Pos 18
			return 18;
		}else
		if(isInTriangle(0, -5, 4, -1.2, 20, -1.2, x, y)){
			//Pos 21
			return 21;
		}else
		if(isInTriangle(0, -5, 0, -1.2, 4, -1.2, x, y)){
			//Pos 20
			return 20;
		}else
		if(isInTriangle(0, -5, -4, -1.2, 0, -1.2, x, y)){
			//Pos 23
			return 23;
		}else
		if(isInTriangle(0, -5, -20, -1.2, -4, -1.2, x, y)){
			//Pos 22
			return 22;
		}else
		if(isInRectangle(-6,2,-3,0,x,y)){
			//Con 0
			return 24;
		}else
		if(isInRectangle(-3,2,0,0,x,y)){
			//Con 1
			return 25;
		}else	
		if(isInRectangle(0,2,3,0,x,y)){
			//Con 2
			return 26;
		}else
		if(isInRectangle(3,2,6,0,x,y)){
			//Con 3
			return 27;
		}else
		if(isInRectangle(-6,-2,-3,0,x,y)){
			//Con 0
			return 28;
		}else
		if(isInRectangle(-3,-2,0,0,x,y)){
			//Con 1
			return 29;
		}else	
		if(isInRectangle(0,-2,3,0,x,y)){
			//Con 2
			return 30;
		}else
		if(isInRectangle(3,-2,6,0,x,y)){
			//Con 3
			return 31;
		}else
								
		return -1;
	}
	
	protected int onWitchConnector(double x, double y){
		return -1;
	}
	
	
	
	/**
	 * Funktion die Prüft, Ob die Position(X/Y) innerhalb des Kreises an Position(X/Y) mit gegebenem Radius befindet
	 * @param CircleX : Position Kreismitte auf X-Achse
	 * @param CircleY : Position Kreismitte auf Y-Achse
	 * @param CircleRadius : Radius des Kreises
	 * @param PositionX : Position welche zu Pr�fen ist auf X-Achse
	 * @param PositionY : Position welche zu Pr�fen ist auf Y-Achse
	 * @return
	 */
	private static boolean isInCircle(double CircleX, double CircleY, double CircleRadius, double PositionX, double PositionY){
		PositionX-=CircleX;
		PositionY-=CircleY;
		if(((PositionX*PositionX)+(PositionY*PositionY))<=(CircleRadius*CircleRadius)){
			return true;
		}
		return false;
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
		PositionX-=CircleX;
		PositionY-=CircleY;
		if(PositionX>0&&PositionY>0){
			return 0;
		}else
		if(PositionX>0&&PositionY<0){
			return 1;
		}else
		if(PositionX<0&&PositionY<0){
			return 2;
		}else
		if(PositionX<0&&PositionY>0){
			return 3;
		}
		return -1;
	}
	
	/**
	 * Funktion welche mit Hilfe der Baryzentrischen Koordinaten prüft, ob ein Punkt innerhalb eines Dreieckes liegt oder nicht
	 * 	Baryzentrische Koordinaten --> http://en.wikipedia.org/wiki/Barycentric_coordinate_system_%28mathematics%29
	 * @param TriangleX1 : Dreieck 1.Punkt X-Achse
	 * @param TriangleY1 : Dreieck 1.Punkt Y-Achse
	 * @param TriangleX2 : Dreieck 2.Punkt X-Achse
	 * @param TriangleY2 : Dreieck 2.Punkt Y-Achse
	 * @param TriangleX3 : Dreieck 3.Punkt X-Achse
	 * @param TriangleY3 : Dreieck 3.Punkt Y-Achse
	 * @param PositionX : Zu prüfende Position auf X-Achse
	 * @param PositionY : Zu prüfende Position auf Y-Achse
	 * @return
	 */
	private static boolean isInTriangle(double TriangleX1, double TriangleY1, double TriangleX2, double TriangleY2, double TriangleX3, double TriangleY3, double PositionX, double PositionY){
		double lamda1 = (((TriangleY2-TriangleY3)*(PositionX-TriangleX3))+((TriangleX3-TriangleX2)*(PositionY-TriangleY3)))/
				(((TriangleY2-TriangleY3)*(TriangleX1-TriangleX3))+((TriangleX3-TriangleX2)*(TriangleY1-TriangleY3)));
		double lamda2 = (((TriangleY3-TriangleY1)*(PositionX-TriangleX3))+((TriangleX1-TriangleX3)*(PositionY-TriangleY3)))/
				(((TriangleY2-TriangleY3)*(TriangleX1-TriangleX3))+((TriangleX3-TriangleX2)*(TriangleY1-TriangleY3)));
		double lamda3 = 1-lamda1-lamda2;
		if(lamda1>0&&lamda1<1&&lamda2>0&&lamda2<1&&lamda3>0&&lamda3<1){
			return true;
		}
		return false;
	}	

	private static boolean isInRectangle(double RectangleX1, double RectangleY1, double RectangleX2, double RectangleY2, double PositionX, double PositionY){
		if(isInTriangle(RectangleX1,RectangleY1,RectangleX2,RectangleY2,RectangleX1,RectangleY2,PositionX,PositionY)||
				isInTriangle(RectangleX1,RectangleY1,RectangleX2,RectangleY2,RectangleX2,RectangleY1,PositionX,PositionY)){
			return true;
		}
		return false;
	}
}
