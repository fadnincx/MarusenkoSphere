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
	private Vector3f rotation = new Vector3f(20,45,00);	
	
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

	protected void turnRotationAngle(double angleX, double angleY){
		rotation.x+=angleX;
		rotation.y+=angleY;
	}
	
	/**
	 * Setzte die Drehungen zurück in die Startposition
	 */
	protected void setToStartPosition(){
		rotation.x = 20;
		rotation.y = 45;
		rotation.z = 0;
	}
	
	
}
