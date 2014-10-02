package marusenkoSphereGUI;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
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
	private Matrix4f rotate = new Matrix4f();
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
				
				
		//Zurück auf Einheitsmatrix setzten
		rotate.setIdentity();
		rotate.rotate(rotation.x, new Vector3f(1f,0f,0f));
		rotate.rotate(rotation.y, new Vector3f(0f,1f,0f));
		rotate.rotate(rotation.z, new Vector3f(0f,0f,1f));
		
		
		Vector3f rotationsAchse = new Vector3f();
		rotationsAchse.x = (rotate.m01*rotate.m12)-((rotate.m11-1)*rotate.m02);
		rotationsAchse.y = (rotate.m10*rotate.m02)-((rotate.m00-1)*rotate.m12);
		rotationsAchse.z = ((rotate.m00-1)*(rotate.m11-1))-(rotate.m01*rotate.m10);
		
		float trA = rotate.m00+rotate.m11+rotate.m22;
		float alpha = (float) Math.acos((trA-1)/2);
		
		
		rotate.invert();
		/*
		 * Vec * A = (1/0/0)
		 * 
		 */
		
		Vector4f rotHoizontal = new Vector4f(1,0,0,0);
		Matrix4f.transform(rotate, rotHoizontal, rotHoizontal);
		Vector4f rotVertical = new Vector4f(0,1,0,0);
		Matrix4f.transform(rotate, rotVertical, rotVertical);
		
		round(rotHoizontal);
		round(rotVertical);
		
		//System.out.println(rotateHoizontal.toString());
		Vector3f rotateHoizontal = vector4fToVector3f(rotHoizontal);
		Vector3f rotateVertical = vector4fToVector3f(rotVertical);
		
		rotate.setIdentity();
		rotate.rotate(alpha, rotationsAchse);
		rotate.rotate((float) angleX, rotateHoizontal);
		rotate.rotate((float) angleY, rotateVertical);
		
		double theta = 0; 
		double psi = 0;
		double phi = 0;
		if(rotate.m20!=1&&rotate.m20!=-1){
			theta = -Math.asin(rotate.m20);
			psi = Math.atan2(rotate.m21/Math.cos(theta), rotate.m22/Math.cos(theta));
			phi = Math.atan2(rotate.m10/Math.cos(theta), rotate.m00/Math.cos(theta));
		}else{
			phi = 0;//anything
			if(rotate.m20==-1){
				theta = Math.PI/2;
				psi = phi + Math.atan2(rotate.m01, rotate.m02);
			}else{
				theta = -1*Math.PI/2;
				psi = -1*phi+Math.atan2(-1*rotate.m01, -1*rotate.m02);
			}
		}
		rotation.x = (float) theta;
		rotation.y = (float) psi;
		rotation.z = (float) phi;
		
		//System.out.println("");
		System.out.println(rotation.x+","+rotation.y+","+rotation.z);
		//System.out.println(rotateHoizontal.toString());
		//System.out.println(rotate.toString());
		
		
		//rotateHoizontal.normalise();
		//rotateVertical.normalise();
		
		
	/*	rotation.x+=(rotateHoizontal.x*angleX);
		rotation.y+=(rotateHoizontal.y*angleX);
		rotation.z+=(rotateHoizontal.z*angleX);
		
		rotation.x+=(rotateVertical.x*angleY);
		rotation.y+=(rotateVertical.y*angleY);
		rotation.z+=(rotateVertical.z*angleY);*/
		//die Drehung wird framerate bereiniget umgesetzt
		rotation.x+=angleX;
		rotation.y+=angleY;
	}
	
	/**
	 * Setzte die Drehungen zurück in die Startposition
	 */
	protected void setToStartPosition(){
		rotation.x = 0;
		rotation.y = 0;
		rotation.z = 0;
	}
	
	/**
	 * Rufe die aktuelle Framerate vom Manager ab
	 */
	private void getFPSVonManager(){
		fps = Manager.getFPS();
	}
	private Vector4f round(Vector4f v){
		v.x =(float) (Math.round(v.x*1000)/1000.0);
		v.y =(float) (Math.round(v.y*1000)/1000.0);
		v.z =(float) (Math.round(v.z*1000)/1000.0);
		v.w =(float) (Math.round(v.w*1000)/1000.0);
		
		return v;
	}
	private Vector3f vector4fToVector3f(Vector4f v){
		return new Vector3f(v.x,v.y,v.z);
	}
	
	
}
