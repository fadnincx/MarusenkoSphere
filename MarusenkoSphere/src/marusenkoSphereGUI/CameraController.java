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
	private Vector3f rotation = new Vector3f(0,0,0);	
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
		
		//die Drehung wird framerate bereiniget umgesetzt
		rotation.x+=angleX*(60/fps);
		rotation.y+=angleY*(60/fps);
	}
	
	/**
	 * Setzte die Drehungen zurück in die Startposition
	 */
	protected void setToStartPosition(){
		rotation.x = 00;
		rotation.y = 0;
		rotation.z = 0;
	}
	
	/**
	 * Rufe die aktuelle Framerate vom Manager ab
	 */
	private void getFPSVonManager(){
		fps = Manager.getFPS();
	}
	
	
}
