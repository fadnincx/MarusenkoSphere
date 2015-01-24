package marusenkoSphereGUI;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
/**
 * CameraController-Klasse
 * 
 * Übernimmt die Drehungen der Kugel.
 *
 */
public class CameraController {
	
	//Vektor mit den 3 Winkeln um die Rotiert wird
	private Vector3f rotation = new Vector3f(20,50,0);
	
	//Aktuelle Framerate
	private double fps = 60;
	
	
	/**
	 * Funktion wird während des Renderprozesses aufgerufen um die Kugel entsprechend zu drehen
	 */
	protected void lookThrough(){
		
		//Verschiebe die Weltkoordinaten in Z-Richtung
  		GL11.glTranslatef(0.0f,0.0f,-4.0f);
		
		//Drehe um die gegebenen Winkel
		GL11.glRotatef(rotation.x,1.0f,0.0f,0.0f);
		GL11.glRotatef(rotation.y,0.0f,1.0f,0.0f);
	    GL11.glRotatef(rotation.z,0.0f,0.0f,1.0f);
	  
	}

	/**
	 * Funktion welche aufgerufen wird, um den Drehwinkel zu ändern
	 */
	protected void turnRotationAngle(double angleX, double angleY){	
		
		//die aktuelle Bildfrequenz wird abgerufen
		getFPSVonManager();
		
		//FPS bereinigen
		angleX*=(60/fps);
		angleY*=(60/fps);
				
		//die Drehung wird umgesetzt
		rotation.x+=angleX;
		rotation.y+=angleY;
		
		rotation.x%=360;
		rotation.y%=360;
	}
	
	/**
	 * Setzte die Drehungen zurück in die Startposition
	 */
	protected void setToStartPosition(){
		
		//Setze X-Achse
		rotation.x = 20;
		
		//Setze Y-Achse
		rotation.y = 50;
		
		//Setze Z-Achse
		rotation.z = 0;
		
	}
	public boolean negativeY(){
		return (rotation.y>180||rotation.y<-180);
	}
	public boolean negativeX(){
		return (rotation.x>180||rotation.x<-180);
	}
	public int negativeYI(){
		return negativeY()?-1:1;
	}
	public int negativeXI(){
		return negativeX()?-1:1;
	}
	
	/**
	 * Rufe die aktuelle Framerate vom Manager ab
	 */
	private void getFPSVonManager(){
		
		fps = Manager.getFPS();
		
	}	
	
}
